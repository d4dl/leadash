package com.d4dl.controller.rest;

import com.d4dl.model.Bid;
import org.junit.Test;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * tests for the bid rest endpoint
 */
public class BidEndpointTest extends BaseEndpointTest {


    @Test
    public void testBid() throws Exception {

        Bid bid1 = new Bid();
        bid1.setAmount(111.00);
        bid1.setNotes("this is bid 1");

        ResponseEntity<Bid> bid1Resp = doEntityPost("bids", bid1, Bid.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, bid1Resp.getStatusCode());
        assertEquals("should have correct response code", 111.00, (Object)bid1Resp.getBody().getAmount());
        assertEquals("should have correct response code", "this is bid 1", bid1Resp.getBody().getNotes());

        // JGBTODO: we aren't getting generated IDs because it doesn't seem that transaction management is being configured properly

        Bid bid2 = new Bid();
        bid2.setAmount(222.00);
        bid2.setNotes("this is bid 2");

        ResponseEntity<Bid> bid2Resp = doEntityPost("bids", bid2 , Bid.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, bid2Resp.getStatusCode());
        assertEquals("should have correct response code", 222.00, (Object)bid2Resp.getBody().getAmount());
        assertEquals("should have correct response code", "this is bid 2", bid2Resp.getBody().getNotes());


        List<Bid> bids = doEntityGet("bids", Bid.class);

        assertEquals("bids should be returned", 2 ,bids.size());
        assertEquals("should have correct first bid", 111.00, (Object)bids.get(0).getAmount());
        assertEquals("should have correct first bid", 222.00, (Object)bids.get(1).getAmount());

        bids.get(0).getId();


    }


}
