package com.d4dl.controller.rest;

import com.d4dl.model.UserAccount;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class UserAccountController {


    @RequestMapping(method = RequestMethod.PUT, value = "/accounts/{id}/addFunds")
    public @ResponseBody
    ResponseEntity<UserAccount> getProducers() {
        return ResponseEntity.ok(new UserAccount());
    }

}
