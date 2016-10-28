/*
 * Copyright 2015 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.d4dl.data;

import com.d4dl.model.Bid;
import com.d4dl.model.Lead;
import com.d4dl.model.UserAccount;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

/**
 */
@RepositoryRestResource
public interface BidRepository extends PagingAndSortingRepository<Bid, Long> {
    List<Lead> findByUserAccount(@Param("userAccount") UserAccount user);
    List<Bid> findByLead(@Param("lead") Lead lead);
}
