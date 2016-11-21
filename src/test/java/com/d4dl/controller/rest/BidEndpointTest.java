package com.d4dl.controller.rest;

import com.d4dl.model.Bid;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.embedded.LocalServerPort;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.net.URL;
import java.util.List;
import java.util.Map;

/**
 * tests for the bid rest endpoint
 */
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class BidEndpointTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate testRestTemplate;


    private String baseUrl;

    @Before
    public void setUp() throws Exception {
        this.baseUrl = new URL("http://localhost:" + port + "/").toString();
    }


    @Test
    public void getHello() throws Exception {

        Bid bid1 = new Bid();
        bid1.setAmount(111.00);
        bid1.setNotes("this is bid 1");

        ResponseEntity<Bid> bid1Resp = new TestRestTemplate().postForEntity(baseUrl + "/api/bids", bid1 , Bid.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, bid1Resp.getStatusCode());
        assertEquals("should have correct response code", 111.00, (Object)bid1Resp.getBody().getAmount());
        assertEquals("should have correct response code", "this is bid 1", bid1Resp.getBody().getNotes());

        Bid bid2 = new Bid();
        bid2.setAmount(222.00);
        bid2.setNotes("this is bid 2");

        ResponseEntity<Bid> bid2Resp = new TestRestTemplate().postForEntity(baseUrl + "/api/bids", bid2 , Bid.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, bid2Resp.getStatusCode());
        assertEquals("should have correct response code", 222.00, (Object)bid2Resp.getBody().getAmount());
        assertEquals("should have correct response code", "this is bid 2", bid2Resp.getBody().getNotes());

        ResponseEntity<Map> response = testRestTemplate.getForEntity(baseUrl.toString() + "/api/bids", Map.class);

        List bids = (List)((Map)response.getBody().get("_embedded")).get("bids");
        assertEquals("bids should be returned", 2 ,bids.size());
    }


}