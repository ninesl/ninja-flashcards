FROM node:16-alpine AS build
WORKDIR /app
COPY package*.json ./
RUN npm install --legacy-peer-deps
COPY . .
RUN npm run build

FROM nginx:alpine
WORKDIR /usr/share/nginx/html
COPY --from=build /app/dist/ .
COPY nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
