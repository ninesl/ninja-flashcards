package flashcards.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import flashcards.model.LoginDTO;
import flashcards.model.RegisterUserDTO;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.annotation.PostConstruct;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * ACTUAL CONTROLLER TESTS - Tests that actually call the REST endpoints
 * and create real users through the controllers!
 */
@SpringBootTest
@AutoConfigureWebMvc
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.datasource.username=sa",
    "spring.datasource.password=",
    "spring.datasource.schema=classpath:test-schema.sql",
    "spring.datasource.initialization-mode=always"
})
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
public class ActualControllerTest {

    @Autowired
    private WebApplicationContext context;
    
    @Autowired
    private ObjectMapper objectMapper;
    
    private MockMvc mockMvc;
    
    @PostConstruct
    public void setup() {
        mockMvc = MockMvcBuilders
                .webAppContextSetup(context)
                .apply(springSecurity())
                .build();
    }

    @Test
    public void testActualUserRegistrationThroughController() throws Exception {
        // unique username to avoid conflicts
        String username = "actualuser" + System.currentTimeMillis();
        String password = "testpass123";
        
        RegisterUserDTO registerDTO = new RegisterUserDTO();
        registerDTO.setUsername(username);
        registerDTO.setPassword(password);
        registerDTO.setConfirmPassword(password);
        registerDTO.setRole("ROLE_USER");
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());
        
        LoginDTO loginDTO = new LoginDTO();
        loginDTO.setUsername(username);
        loginDTO.setPassword(password);
        
        MvcResult loginResult = mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(loginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists())
                .andExpect(jsonPath("$.user.username").value(username))
                .andReturn();
        
        String responseBody = loginResult.getResponse().getContentAsString();
        String jwtToken = objectMapper.readTree(responseBody).get("token").asText();
        String userId = objectMapper.readTree(responseBody).get("user").get("id").asText();
        
        mockMvc.perform(get("/api/user/" + userId)
                .header("Authorization", "Bearer " + jwtToken))
                .andExpect(status().isOk())
                .andExpect(content().string(username));
    }
    
    @Test
    public void testDuplicateUserRegistration() throws Exception {
        String username = "duplicateuser" + System.currentTimeMillis();
        
        RegisterUserDTO registerDTO = new RegisterUserDTO();
        registerDTO.setUsername(username);
        registerDTO.setPassword("password123");
        registerDTO.setConfirmPassword("password123");
        registerDTO.setRole("ROLE_USER");
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().is4xxClientError());
    }
    
    @Test
    public void testWrongPasswordLogin() throws Exception {
        String username = "passwordtest" + System.currentTimeMillis();
        String correctPassword = "correctpass123";
        String wrongPassword = "wrongpass123";
        
        RegisterUserDTO registerDTO = new RegisterUserDTO();
        registerDTO.setUsername(username);
        registerDTO.setPassword(correctPassword);
        registerDTO.setConfirmPassword(correctPassword);
        registerDTO.setRole("ROLE_USER");
        
        mockMvc.perform(post("/api/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registerDTO)))
                .andExpect(status().isCreated());
                
        LoginDTO wrongLoginDTO = new LoginDTO();
        wrongLoginDTO.setUsername(username);
        wrongLoginDTO.setPassword(wrongPassword);
        
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(wrongLoginDTO)))
                .andExpect(status().isUnauthorized());
        
        LoginDTO correctLoginDTO = new LoginDTO();
        correctLoginDTO.setUsername(username);
        correctLoginDTO.setPassword(correctPassword);
        
        mockMvc.perform(post("/api/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(correctLoginDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.token").exists());
    }
}