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

import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.spring.boot.ext.MavenInvokerTemplate;
import org.apache.maven.spring.boot.ext.MavenResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	
	@Autowired
	private MavenInvokerTemplate mavenInvokerTemplate;
	
	@PostConstruct
	private void init() throws MavenInvocationException {
		
		InvocationResult result1 = mavenInvokerTemplate.install(MavenResource.parse("D:\\p6spy-3.8.1.jar", "p6spy:p6spy:3.8.1-xx"));
		System.out.println("ExitCode:" + result1.getExitCode());
		System.out.println("Exception:" + result1.getExecutionException());
		
		//mavenInvokerTemplate.execute(basedir, goals);
		//mavenInvokerTemplate.execute(basedir, goals);
		
	}
	
	public static void main(String[] args) throws Exception {
		SpringApplication.run(Application.class, args);
	}

}
