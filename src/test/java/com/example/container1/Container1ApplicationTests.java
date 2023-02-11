package com.example.container1;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.testcontainers.containers.GenericContainer;
import org.testcontainers.junit.jupiter.Container;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class Container1ApplicationTests {
    @Autowired
    TestRestTemplate restTemplate;

    @Container
    private static final GenericContainer<?> devApp = new GenericContainer<>("devapp")
            .withExposedPorts(8080);
    @Container
    private static final GenericContainer<?> prodApp = new GenericContainer<>("prodapp")
            .withExposedPorts(8081);

    @BeforeAll
    public static void setUp() {
        devApp.start();
        prodApp.start();
    }

    @Test
    void contextLoads() {
        ResponseEntity<String> devAppEntity = restTemplate.getForEntity("http://localhost:" +
                devApp.getMappedPort(8080) + "/profile", String.class);
        System.out.println(devAppEntity.getBody());

        ResponseEntity<String> prodAppEntity = restTemplate.getForEntity("http://localhost:"
                + prodApp.getMappedPort(8081) + "/profile", String.class);
        System.out.println(prodAppEntity.getBody());

        Assertions.assertEquals("Current profile is dev", devAppEntity.getBody());
        Assertions.assertEquals("Current profile is production", prodAppEntity.getBody());
    }

}
