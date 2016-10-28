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

import com.d4dl.model.Campaign;
import io.swagger.annotations.Api;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 *
 */

@RepositoryRestResource
@EnableSwagger2
@Api(value = "campaign", description = "A campaign is designed by an agent and contains a list of leads paid for by the agent that match the lead criteria")
public interface CampaignRepository extends PagingAndSortingRepository<Campaign, Long> {

}


