package com.yogeshprasad.restgrpc.example.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import java.util.List;

@RestController
public class BookRestController {

    @Autowired
    private Environment env;

    @RequestMapping("/test_rest")
    public List<Book> testVerbose() {
        String serverHost = env.getProperty("BOOK_SERVICE_HOST", "localhost");
        HttpEntity<List<Book>> entity = new HttpEntity<>(TestDataUtil.getRestTestData(), null);

        ResponseEntity<List<Book>> responseEntity =
            new RestTemplate().exchange("http://" + serverHost + ":8081/book",
                    HttpMethod.POST, entity, new ParameterizedTypeReference<List<Book>>() {});

        return responseEntity.getBody();
    }

    @RequestMapping("/test_rest/compact")
    public String testCompact() {
        testVerbose();
        return "Rest success response from V2";
    }
}
