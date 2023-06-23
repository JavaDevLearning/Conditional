package ru.netology.springbootdemo;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.actuate.web.exchanges.HttpExchange;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Testcontainers;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;

    private  final GenericContainer<?> devApp = new GenericContainer<>("devapp:0.0.1")
            .withExposedPorts(8080);
    private  final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:0.0.1")
            .withExposedPorts(8081);


    @BeforeEach
      void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        Integer devAppPort = devApp.getMappedPort(8080);
        Integer prodAppPort = prodApp.getMappedPort(8081);

        ResponseEntity<String> entityDevApp = testRestTemplate.getForEntity("http://localhost:" + devAppPort, String.class);
        ResponseEntity<String> entityProdApp = testRestTemplate.getForEntity("http://localhost:" + prodAppPort, String.class);

        System.out.println("DevApp: " + entityDevApp.getBody());
        System.out.println("ProdApp: " + entityProdApp.getBody());
    }

}
