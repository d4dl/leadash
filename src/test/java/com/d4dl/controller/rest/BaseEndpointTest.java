package com.d4dl.controller.rest;

import com.d4dl.model.Bid;
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

    protected String baseUrl;
    protected String username;
    protected String password;


    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/").toString();
    }


    protected void setUser(String username, String password) {
        this.username = username;
        this.password = password;
    }


    public TestRestTemplate getTestRestTemplate() {
        if (username != null) {
            return new TestRestTemplate(username, password);
        } else {
            return new TestRestTemplate();
        }
    }


    // JGBTODO: actually, transaction are working... this annotation may be unnecessary
    @Transactional(Transactional.TxType.REQUIRES_NEW)
    protected <T> ResponseEntity<T> doEntityPost(String endpoint, T instance, Class<T> clazz) {
        return getTestRestTemplate().postForEntity(baseUrl + "/api/" + endpoint, instance, clazz);
    }


    protected <T> List<T> doEntityGet(String endpoint, Class<T> clazz) throws Exception {
        String url = baseUrl.toString() + "api/" + endpoint;
        ResponseEntity<Map> response = getTestRestTemplate().getForEntity(url, Map.class);
        List<Map> bidMaps = (List<Map>)((Map)response.getBody().get("_embedded")).get(endpoint);

        ObjectMapper mapper = new ObjectMapper();
        String jsonBids = mapper.writeValueAsString(bidMaps);

        JavaType jacksonType = mapper.getTypeFactory().constructCollectionType(List.class, clazz);
        List<T> resultList = mapper.readValue(jsonBids, jacksonType);

        return resultList;
    }


    protected <T> T doEntityGet(String endpoint, long id, Class<T> clazz) throws Exception {
        String url = baseUrl.toString() + "api/" + endpoint + "/" + id;
        ResponseEntity<Map> response = getTestRestTemplate().getForEntity(url, Map.class);
        Object respBody = response.getBody();

        ObjectMapper mapper = new ObjectMapper();
        String jsonBids = mapper.writeValueAsString(respBody);

        return mapper.readValue(jsonBids, clazz);
    }

}
