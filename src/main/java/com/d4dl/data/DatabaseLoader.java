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

import com.d4dl.model.Partner;
import com.d4dl.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 */

@Component
public class DatabaseLoader implements CommandLineRunner {

	private final PartnerRepository partners;
	private final UserAccountRepository users;
	private final LeadRepository leadRepository;

	@Autowired
	public DatabaseLoader(LeadRepository leadRepository,
						  PartnerRepository partnerRepository,
						  UserAccountRepository userRepository) {

		this.partners = partnerRepository;
		this.leadRepository = leadRepository;
		this.users = userRepository;
	}

	@Override
	public void run(String... strings) throws Exception {

		UserAccount justin = new UserAccount("justin", "justin", "ROLE_MANAGER");
		justin.setRoles(new String[]{"ROLE_MANAGER", "ORDER_PROCESSOR"});

        UserAccount user = new UserAccount("beck", "beck", "ROLE_MANAGER");
        user.setRoles(new String[]{"ROLE_MANAGER", "ORDER_PROCESSOR"});
        UserAccount myself = this.users.save(user);

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("justin", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));

		this.partners.save(new Partner("Frodo", "Baggins", "ring bearer", myself));
		this.partners.save(new Partner("Bilbo", "Baggins", "burglar", myself));
		this.partners.save(new Partner("Gandalf", "the Grey", "wizard", myself));

		SecurityContextHolder.getContext().setAuthentication(
			new UsernamePasswordAuthenticationToken("oliver", "doesn't matter",
				AuthorityUtils.createAuthorityList("ROLE_MANAGER")));
		//this.partners.save(new Employee("Samwise", "Gamgee", "gardener", oliver));
		//this.partners.save(new Employee("Merry", "Brandybuck", "pony rider", oliver));
		//this.partners.save(new Employee("Peregrin", "Took", "pipe smoker", oliver));

		SecurityContextHolder.clearContext();
	}
}
