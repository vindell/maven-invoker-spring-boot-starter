/*
 * Copyright (c) 2018, vindell (https://github.com/vindell).
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.apache.maven.spring.boot;

import javax.annotation.PostConstruct;

import org.apache.maven.spring.boot.ext.MavenInvokerTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableMavenInvoker
@SpringBootApplication
public class Application {
	
	@Autowired
	private MavenInvokerTemplate mavenInvokerTemplate;
	
	@PostConstruct
	private void init() {
		
		//mavenInvokerTemplate.deploy(file, groupId, artifactId, version, packaging, url, repositoryId);
		//mavenInvokerTemplate.deploy(basedir, file, groupId, artifactId, version, packaging, url, repositoryId);
		
		//mavenInvokerTemplate.execute(basedir, goals);
		//mavenInvokerTemplate.execute(basedir, goals);
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
