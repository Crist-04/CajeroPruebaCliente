package com.example.cajerocliente.BL;

import com.example.cajerocliente.ML.Denominacion;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class DenominacionService {

    public static final String urlBase = "http://localhost:8080";

    public List<Denominacion> getAll() {
        try {
            String url = urlBase + "/api/denominacion/all";

            RestTemplate restTemplate = new RestTemplate();
            Object response = restTemplate.getForObject(url, Object.class);

            ObjectMapper mapper = new ObjectMapper();
            return mapper.convertValue(response, new TypeReference<List<Denominacion>>() {
            });

        } catch (Exception ex) {
            return new ArrayList<>();
        }
    }
}
