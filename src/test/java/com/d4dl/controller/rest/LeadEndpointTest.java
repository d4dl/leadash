package com.d4dl.controller.rest;

import com.d4dl.model.Lead;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * tests for the lead rest endpoint
 */
public class LeadEndpointTest extends BaseEndpointTest {

    @Test
    public void testLead() throws Exception {
        Lead lead1 = new Lead();
        lead1.setName("lead 1 name");
        lead1.setLeadStatus(Lead.LeadStatus.QUOTED);

        ResponseEntity<Lead> lead1Resp = doEntityPost("leads", lead1, Lead.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, lead1Resp.getStatusCode());
        assertEquals("should have correct response code", "lead 1 name", lead1Resp.getBody().getName());
        assertEquals("should have correct response code", Lead.LeadStatus.QUOTED, lead1Resp.getBody().getLeadStatus());

        Lead lead1read = doEntityGet("leads", 1, Lead.class);
        assertEquals("should have correct response code", "lead 1 name", lead1read.getName());

        Lead lead2 = new Lead();
        lead2.setName("lead 2 name");
        lead2.setLeadStatus(Lead.LeadStatus.SOLD);

        ResponseEntity<Lead> lead2Resp = doEntityPost("leads", lead2, Lead.class);

        assertEquals("should have correct response code", HttpStatus.CREATED, lead2Resp.getStatusCode());
        assertEquals("should have correct response code", "lead 2 name", lead2Resp.getBody().getName());
        assertEquals("should have correct response code", Lead.LeadStatus.SOLD, lead2Resp.getBody().getLeadStatus());

        List<Lead> leads = doEntityGet("leads", Lead.class);

        assertEquals("leads should be returned", 2, leads.size());
        assertEquals("should have correct first lead", "lead 1 name", leads.get(0).getName());
        assertEquals("should have correct second lead", "lead 2 name", leads.get(1).getName());

        // AGBTODO: actually, it looks like if the post is missing fields, the response won't include them... weird
        // I posted through the swagger UI using their full template and got an ID back. However, then I only posted
        // a couple fields and the post worked, but the response was missing the id field.
        assertEquals("lead id 1", 0, leads.get(0).getId());
        assertEquals("lead id 2", 0, leads.get(1).getId());

    }

}
