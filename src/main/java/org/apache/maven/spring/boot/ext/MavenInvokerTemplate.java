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
package org.apache.maven.spring.boot.ext;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.spring.boot.MavenInvokerProperties;
import org.springframework.util.StringUtils;

/**
 * 基于Maven Invoker的Maven build实现，依赖于本机环境中的Maven环境
 * 
 * @author ： <a href="https://github.com/vindell">vindell</a>
 */
public class MavenInvokerTemplate {

	private InvocationOutputHandler outputHandler;
	private InvocationOutputHandler errorHandler;
	private Invoker mavenInvoker;
	private MavenInvokerProperties properties;

	public MavenInvokerTemplate(InvocationOutputHandler outputHandler, InvocationOutputHandler errorHandler,
			Invoker mavenInvoker, MavenInvokerProperties invokerProperties) {
		this.outputHandler = outputHandler;
		this.errorHandler = errorHandler;
		this.mavenInvoker = mavenInvoker;
		this.properties = invokerProperties;
	}

	public InvocationResult install(String file, String groupId, String artifactId, String version, String packaging,
			boolean generatePom, boolean createChecksum) throws MavenInvocationException {
		return this.install(null, file, groupId, artifactId, version, packaging, generatePom, createChecksum);
	}

	public InvocationResult install(String basedir, String file, String groupId, String artifactId, String version,
			String packaging, boolean generatePom, boolean createChecksum) throws MavenInvocationException {

		InvocationRequest request = properties.newRequest();
		request.setErrorHandler(errorHandler);
		request.setOutputHandler(outputHandler);
		
		if (StringUtils.hasText(basedir)) {
			request.setBaseDirectory(new File(basedir));
		}

		request.setGoals(Arrays.asList("install:install-file", "-Dfile=" + file, "-DgroupId=" + groupId,
				"-DartifactId=" + artifactId, "-Dversion=" + version, "-Dpackaging=" + packaging,
				"-DgeneratePom=" + generatePom, "-DcreateChecksum=" + createChecksum));

		return mavenInvoker.execute(request);
	}

	public InvocationResult deploy(String file, String groupId, String artifactId, String version, String packaging,
			String url, String repositoryId) throws MavenInvocationException {
		return this.deploy(null, file, groupId, artifactId, version, packaging, url, repositoryId);
	}

	public InvocationResult deploy(String basedir, String file, String groupId, String artifactId, String version,
			String packaging, String url, String repositoryId) throws MavenInvocationException {

		InvocationRequest request = properties.newRequest();
		request.setErrorHandler(errorHandler);
		request.setOutputHandler(outputHandler);
		if (StringUtils.hasText(basedir)) {
			request.setBaseDirectory(new File(basedir));
		}

		request.setGoals(Arrays.asList("deploy:deploy-file", "-DgroupId=" + groupId, "-DartifactId=" + artifactId,
				"-Dversion=" + version, "-Dpackaging=" + packaging, "-Dfile=" + file, "-Durl=" + url,
				"-DrepositoryId=" + repositoryId));

		return mavenInvoker.execute(request);
	}

	public InvocationResult execute(File basedir, String... goals) throws MavenInvocationException {

		InvocationRequest request = properties.newRequest();
		request.setErrorHandler(errorHandler);
		request.setOutputHandler(outputHandler);

		request.setBaseDirectory(basedir);
		request.setGoals(Arrays.asList(goals));

		return mavenInvoker.execute(request);
	}

	public InvocationResult execute(String basedir, String... goals) throws MavenInvocationException {
		return this.execute(new File(basedir), goals);
	}

}
