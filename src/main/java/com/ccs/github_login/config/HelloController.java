package com.ccs.github_login.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Controller
public class HelloController {

    @Autowired
    RestTemplate restTemplate;

    @GetMapping("/index.html")
    public String index(){
        return "index";
    }

    @GetMapping("/authorization_code")
    private String authorization_code(String code) {

        Map<String, String> map = new HashMap<>();
        map.put("client_id", "d46f1dd5f74d73f22e38");
        map.put("client_secret", "321066332a22a236c49a98607ad8b75456f92a6c");
        map.put("code", code);
        map.put("state", "javaboy");
        map.put("redirect_uri", "http://localhost:8080/authorization_code");

        Map<String, String> resp = restTemplate.postForObject("https://github.com/login/oauth/access_token", map, Map.class);
        System.out.println("resp = " + resp);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "token " + resp.get("access_token"));
        HttpEntity<Map> httpEntity = new HttpEntity<>(headers);
        ResponseEntity<Map> responseEntity = restTemplate.exchange("https://api.github.com/user", HttpMethod.GET, httpEntity, Map.class);
        System.out.println("responseEntity.getBody() = " + responseEntity.getBody());
        return "forward:/index.html";
    }

}
