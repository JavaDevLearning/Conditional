package ru.netology.springbootdemo;


import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;

import static org.junit.jupiter.api.Assertions.assertEquals;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SpringBootDemoApplicationTests {

    @Autowired
    TestRestTemplate testRestTemplate;


    private final GenericContainer<?> devApp = new GenericContainer<>("devapp:1.0")
            .withExposedPorts(8080);
    private final GenericContainer<?> prodApp = new GenericContainer<>("prodapp:1.0")
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

        ResponseEntity<String> entityDevApp = testRestTemplate.getForEntity
                ("http://localhost:" + devAppPort + "/profile", String.class);
        ResponseEntity<String> entityProdApp = testRestTemplate.getForEntity
                ("http://localhost:" + prodAppPort + "/profile", String.class);

        String expectedDev = entityDevApp.getBody();
        String expectedProd = entityProdApp.getBody();


        assertEquals(expectedDev, "Current profile is dev");
        assertEquals(expectedProd, "Current profile is production");

    }

}
