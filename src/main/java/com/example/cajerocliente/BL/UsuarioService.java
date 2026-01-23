package com.example.cajerocliente.BL;

import com.example.cajerocliente.ML.Result;
import com.example.cajerocliente.ML.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class UsuarioService {
    
    public static final String urlBase = "http://localhost:8080";
    
    public Result login(String username, String password) {
        Result result = new Result();
        try {
            String url = urlBase + "/api/usuario/login";
            
            RestTemplate restTemplate = new RestTemplate();
            
            // Crear headers
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
            
            // Crear body con los parámetros
            MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
            body.add("username", username);
            body.add("password", password);
            
            // Crear la petición con headers y body
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body, headers);
            
            // Hacer POST
            ResponseEntity<Result> response = restTemplate.postForEntity(url, request, Result.class);
            
            if (response.getBody() != null && response.getBody().correct) {
                ObjectMapper mapper = new ObjectMapper();
                Usuario usuario = mapper.convertValue(response.getBody().object, Usuario.class);
                
                result.correct = true;
                result.object = usuario;
                result.errorMessage = "Login exitoso";
            } else {
                result.correct = false;
                result.errorMessage = response.getBody() != null ? response.getBody().errorMessage : "Error de conexión";
            }
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error: " + ex.getMessage();
            result.ex = ex;
            ex.printStackTrace();
        }
        
        return result;
    }
    
    public Result retiro(int idUsuario, int idCajero, int monto) {
        Result result = new Result();
        try {
            String url = urlBase + "/api/usuario/retiro?idUsuario=" + idUsuario + "&idCajero=" + idCajero + "&monto=" + monto;
            
            RestTemplate restTemplate = new RestTemplate();
            Result response = restTemplate.postForObject(url, null, Result.class);
            
            result = response != null ? response : result;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error: " + ex.getMessage();
            result.ex = ex;
        }
        
        return result;
    }
    
    public Result consultarSaldo(int idUsuario) {
        Result result = new Result();
        try {
            String url = urlBase + "/api/usuario/saldo/" + idUsuario;
            
            RestTemplate restTemplate = new RestTemplate();
            Result response = restTemplate.getForObject(url, Result.class);
            
            result = response != null ? response : result;
            
        } catch (Exception ex) {
            result.correct = false;
            result.errorMessage = "Error: " + ex.getMessage();
            result.ex = ex;
        }
        
        return result;
    }
}