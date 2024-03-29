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
package com.d4dl.controller;

import com.d4dl.data.UserAccountRepository;
import com.d4dl.model.Partner;
import com.d4dl.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.core.annotation.HandleBeforeCreate;
import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 *
 */

@Component
@RepositoryEventHandler(Partner.class)
public class SpringDataRestEventHandler {

	private final UserAccountRepository userRepository;

	@Autowired
	public SpringDataRestEventHandler(UserAccountRepository userRepository) {
		this.userRepository = userRepository;
	}

	@HandleBeforeCreate
	public void applyUserInformationUsingSecurityContext(Partner partner) {

		String name = SecurityContextHolder.getContext().getAuthentication().getName();
		UserAccount user = this.userRepository.findByName(name);
		if (user == null) {
			UserAccount newUserAccount = new UserAccount();
			newUserAccount.setName(name);
			newUserAccount.setRoles(new String[]{"ROLE_MANAGER"});
			user = this.userRepository.save(newUserAccount);
		}
		partner.setUser(user);
	}
}

