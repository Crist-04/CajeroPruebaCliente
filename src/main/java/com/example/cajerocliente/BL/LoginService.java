package com.example.cajerocliente.BL;

import com.example.cajerocliente.ML.LoginRequest;
import com.example.cajerocliente.ML.LoginResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class LoginService {

    private final RestTemplate restTemplate = new RestTemplate();
    private static final String urlLogin = "http://localhost:8080/api/login";

    public LoginResponse login(String username, String password) {
        LoginRequest loginRequest = new LoginRequest(username, password);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<LoginRequest> entity = new HttpEntity<>(loginRequest, headers);

        ResponseEntity<LoginResponse> response = restTemplate.postForEntity(urlLogin, entity, LoginResponse.class);

        return response.getBody();
    }

}
