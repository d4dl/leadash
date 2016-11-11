package com.d4dl.controller.rest;

import com.d4dl.data.BidRepository;
import com.d4dl.model.Bid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.rest.webmvc.PersistentEntityResourceAssembler;
import org.springframework.data.rest.webmvc.RepositoryRestController;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@RepositoryRestController
public class BidController extends HALInterceptingController {

    @Autowired
    BidRepository bidRepository;

    @RequestMapping(method = RequestMethod.GET, value = "/bids")
    public @ResponseBody
    Resources<?> getBids(Pageable pageable, PersistentEntityResourceAssembler assembler) throws Exception {
        Page<Bid> bids = bidRepository.findAll(pageable);
        //This is where the wrapping magic happens so you can do stuff and still send hal with links back.
        //I pulled this out of another project and haven't tested it.

        Resources<?> result = createResourceResponse(Bid.class, pageable, assembler, bids);
        return result;
    }

}
