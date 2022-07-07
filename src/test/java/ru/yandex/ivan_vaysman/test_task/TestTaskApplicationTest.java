package ru.yandex.ivan_vaysman.test_task;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.net.URI;

@SpringBootTest
class TestTaskApplicationTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(TestTaskApplicationTest.class);
    private static final String LOGIN_POMOFOCUS = "https://pomofocus.io/login-with-email";
    private static final String UPDATE_POMOFOCUS = "https://pomofocus.io/api/user/update";

    @Autowired
    private RestTemplateBuilder restTemplateBuilder;

    @Test
    void testVariable(){
        RestTemplate restTemplate = restTemplateBuilder.build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        String jsonAuthReq = """
               {
               "email":"al20ivan@yandex.ru",
               "password":"passroot"
               }
                """;

        String jsonUpdateReq = """
                {
                "name":"Pomofocus User"
                }
                """;

        HttpEntity<String> requestAuth = new HttpEntity<>(jsonAuthReq, headers);

        ResponseEntity<String> responseAuth = restTemplate.postForEntity(URI.create(LOGIN_POMOFOCUS), requestAuth, String.class);
        Assertions.assertEquals(responseAuth.getStatusCode(), HttpStatus.OK);

        String token = null;

        try {
            JSONObject jsonObject = new JSONObject(responseAuth.getBody());
            token = jsonObject.getString("token");
        } catch (JSONException e) {
            LOGGER.error("can't deserialize token from response");
        }

        Assertions.assertNotNull(token);

        headers.set("Authorization", token);
        HttpEntity<String> requestUpdate = new HttpEntity<>(jsonUpdateReq, headers);

        ResponseEntity<String> updateWithAuth = restTemplate.postForEntity(URI.create(UPDATE_POMOFOCUS), requestUpdate, String.class);
        Assertions.assertEquals(updateWithAuth.getStatusCode(), HttpStatus.OK);
    }
}