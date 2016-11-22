package com.d4dl.controller.rest;

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import javax.transaction.Transactional;
import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * tests for the bid rest endpoint
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BaseEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    protected String baseUrl;


    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/").toString();
    }


    // JGBTODO: this annotation isn't working, I don't think transaction management is working properly
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected <T> ResponseEntity<T> doEntityPost(String endpoint, T instance, Class<T> clazz) {
        return new TestRestTemplate().postForEntity(baseUrl + "/api/" + endpoint, instance, clazz);
    }


    protected <T> List<T> doEntityGet(String endpoint, Class<T> clazz) throws Exception {
        ResponseEntity<Map> response = testRestTemplate.getForEntity(baseUrl.toString() + "/api/" + endpoint, Map.class);
        List<Map> bidMaps = (List<Map>)((Map)response.getBody().get("_embedded")).get(endpoint);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBids = mapper.writeValueAsString(bidMaps);

        JavaType jacksonType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> resultList = mapper.readValue(jsonBids, jacksonType);

        return resultList;
    }

}
