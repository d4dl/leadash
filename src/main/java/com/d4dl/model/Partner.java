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
package com.d4dl.model;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;

/**
 *
 */
@Data
@Entity
public class Partner extends BaseEntity {

	private String firstName;
	private String lastName;
	private String description;

	private @ManyToOne
	UserAccount user;

	private Partner() {}

	public Partner(String firstName, String lastName, String description, UserAccount user) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.description = description;
		this.user = user;
	}
}
