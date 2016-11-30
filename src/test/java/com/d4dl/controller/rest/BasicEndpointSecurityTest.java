package com.d4dl.controller.rest;

import com.d4dl.model.Bid;
import com.d4dl.model.Lead;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * tests for the lead rest endpoint
 */
public class BasicEndpointSecurityTest extends BaseEndpointTest {

    @Test
    public void testEndpointSecurity() throws Exception {
        // unauthenticated
        Bid bid1 = new Bid();
        bid1.setAmount(111.00);
        bid1.setNotes("this is bid 1");
        ResponseEntity<Bid> bid1Resp = doEntityPost("bids", bid1, Bid.class);
        assertEquals("should have correct response code", HttpStatus.CREATED, bid1Resp.getStatusCode());

        // now try a protected endpoint
        Lead lead1 = new Lead();
        lead1.setName("lead 1 name");
        lead1.setLeadStatus(Lead.LeadStatus.QUOTED);
        try {
            ResponseEntity<Lead> lead1Resp = doEntityPost("leads", lead1, Lead.class);
        } catch (Exception e) {
            // expected since not logged in
        }

        setUser("jamie", "jamie");
        ResponseEntity<Lead> lead1Resp = doEntityPost("leads", lead1, Lead.class);


        assertEquals("should have correct response code", HttpStatus.CREATED, lead1Resp.getStatusCode());
    }

}
