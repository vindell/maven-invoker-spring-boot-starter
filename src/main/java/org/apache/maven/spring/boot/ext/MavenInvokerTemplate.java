/*
 * Copyright (c) 2018, hiwepy (https://github.com/hiwepy).
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
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.apache.maven.spring.boot.MavenInvokerProperties;
import org.codehaus.plexus.util.xml.pull.XmlPullParserException;
import org.springframework.util.Assert;

/**
 *	 基于Maven Invoker的Maven build实现，依赖于本机环境中的Maven环境
 * 	@author ： <a href="https://github.com/hiwepy">hiwepy</a>
 */
public class MavenInvokerTemplate {

	private InvocationOutputHandler outputHandler;
	private InvocationOutputHandler errorHandler;
	private Invoker mavenInvoker;
	private MavenInvokerProperties properties;
	private MavenXpp3Reader modelReader = new MavenXpp3Reader();
	
	public MavenInvokerTemplate(InvocationOutputHandler outputHandler, InvocationOutputHandler errorHandler,
			Invoker mavenInvoker, MavenInvokerProperties invokerProperties) {
		this.outputHandler = outputHandler;
		this.errorHandler = errorHandler;
		this.mavenInvoker = mavenInvoker;
		this.properties = invokerProperties;
	}
	
	public InvocationResult install(String filepath, String coordinates) throws MavenInvocationException {
		Assert.notNull(coordinates, "coordinates must not be null");
		return this.install(MavenResource.parse(filepath, coordinates));
	}
	
	public InvocationResult install(MavenResource resource) throws MavenInvocationException {
		
		InvocationRequest request = properties.newRequest();
		request.setErrorHandler(errorHandler);
		request.setOutputHandler(outputHandler);

		request.setGoals(Arrays.asList("install:install-file", "-Dfile=" + resource.getFilepath(), "-DgroupId=" + resource.getGroupId(),
				"-DartifactId=" + resource.getArtifactId(), "-Dversion=" + resource.getVersion(), "-Dpackaging=" + resource.getExtension(),
				"-DgeneratePom=" + resource.isGeneratePom(), "-DcreateChecksum=" + resource.isCreateChecksum()));

		return mavenInvoker.execute(request);
		
	}
	
	public InvocationResult deploy(String filepath, String coordinates, String repositoryUrl, String repositoryId) throws MavenInvocationException {
		Assert.notNull(coordinates, "coordinates must not be null");
		MavenResource resource = MavenResource.parse(filepath, coordinates);
		resource.setRepositoryId(repositoryId);
		resource.setRepositoryUrl(repositoryUrl);
		return this.deploy(resource);
	}

	public InvocationResult deploy(MavenResource resource) throws MavenInvocationException {

		InvocationRequest request = properties.newRequest();
		request.setErrorHandler(errorHandler);
		request.setOutputHandler(outputHandler);

		request.setGoals(Arrays.asList("deploy:deploy-file", "-DgroupId=" + resource.getGroupId(),
				"-DartifactId=" + resource.getArtifactId(), "-Dversion=" + resource.getVersion(),
				"-Dpackaging=" + resource.getExtension(), "-Dfile=" + resource.getFilepath(),
				"-Durl=" + resource.getRepositoryUrl(), "-DrepositoryId=" + resource.getRepositoryId()));
		
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
	
	public Model readModel(File file) throws XmlPullParserException, IOException {
		try (
			ZipFile zipFile = new ZipFile(file)) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				//System.out.println(entry.getName());
				if (entry.getName().endsWith("pom.xml")) {
					InputStream input = zipFile.getInputStream(entry);
					Model model = modelReader.read(new InputStreamReader(input));
					return model;
				}
			}
		} 
		throw new IOException("Not a maven project, unable to parse version information.");
	}
	
}
