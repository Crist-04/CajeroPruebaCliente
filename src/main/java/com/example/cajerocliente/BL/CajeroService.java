package com.example.cajerocliente.BL;

import com.example.cajerocliente.ML.Cajero;
import com.example.cajerocliente.ML.Result;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class CajeroService {
    
    public static final String urlBase = "http://localhost:8080";
    
    public List<Cajero> getAll() {
        try {
            String url = urlBase + "/api/cajero/all";
            
            RestTemplate restTemplate = new RestTemplate();
            Object response = restTemplate.getForObject(url, Object.class);
            
            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(response, new TypeReference<List<Cajero>>(){});
            
        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
    
    public Result rellenar(int idUsuario, int idCajero, int idDenominacion, int cantidad) {
        Result result = new Result();
        try {
            String url = urlBase + "/api/cajero/rellenar?idUsuario=" + idUsuario + 
                        "&idCajero=" + idCajero + "&idDenominacion=" + idDenominacion + 
                        "&cantidad=" + cantidad;
            
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
}