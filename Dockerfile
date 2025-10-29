# Build Vue frontend (from vue/Dockerfile)
FROM node:16-alpine AS vue-build
WORKDIR /app
COPY vue/package*.json ./
RUN npm install --legacy-peer-deps
COPY vue/ ./
RUN npm run build

# Build Java backend (from java/Dockerfile)  
FROM maven:3-eclipse-temurin-11-alpine AS java-build
WORKDIR /app
COPY java/pom.xml .
RUN mvn dependency:go-offline -B
COPY java/src ./src
RUN mvn clean package

# Final runtime - use postgres:alpine as base (from docker-compose.yml)
FROM postgres:alpine

# Install Java and nginx
RUN apk add --no-cache openjdk11-jre nginx

# PostgreSQL setup (from docker-compose.yml)
ENV POSTGRES_DB=final_capstone
ENV POSTGRES_USER=final_capstone_owner
ENV POSTGRES_PASSWORD=finalcapstone

# Copy database initialization scripts (from docker-compose.yml)
COPY database/schema.sql /docker-entrypoint-initdb.d/01-schema.sql
COPY database/sample-data.sql /docker-entrypoint-initdb.d/02-sample-data.sql

# Copy Java backend (from java/Dockerfile)
COPY --from=java-build /app/target/*.jar /app/backend.jar

# Copy nginx frontend (from vue/Dockerfile)
WORKDIR /usr/share/nginx/html
COPY --from=vue-build /app/dist/ .
COPY vue/nginx.conf /etc/nginx/nginx.conf

EXPOSE 80

# Start all services (using postgres image's proper entrypoint)
CMD /usr/local/bin/docker-entrypoint.sh postgres & \
    SPRING_DATASOURCE_URL=jdbc:postgresql://localhost:5432/final_capstone \
    SPRING_DATASOURCE_USERNAME=final_capstone_owner \
    SPRING_DATASOURCE_PASSWORD=finalcapstone \
    java -jar /app/backend.jar --spring.profiles.active=docker & \
    nginx -g "daemon off;"
