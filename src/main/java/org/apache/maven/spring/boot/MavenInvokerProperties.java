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

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationRequest.CheckSumPolicy;
import org.apache.maven.shared.invoker.InvocationRequest.ReactorFailureBehavior;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.util.StringUtils;

/**
 * Maven Invoker 参数配置
 * 
 * @author ： <a href="https://github.com/vindell">vindell</a>
 */
@ConfigurationProperties(MavenInvokerProperties.PREFIX)
public class MavenInvokerProperties {

	public static final String PREFIX = "maven.invoker";
	
	String DEFAULT_LOCAL_REPO_ID = "local";

    static String userHome = System.getProperty( "user.home" );

    static File userMavenConfigurationHome = new File( userHome, ".m2" );

    static File defaultUserLocalRepository = new File( userMavenConfigurationHome, "repository" );

	
	/**
	 * Set the value of the {@code also-make} argument. {@code true} if the argument
	 * {@code also-make} was specified, otherwise {@code false}
	 */
	private boolean alsoMake;
	/**
	 * Set the value of the {@code also-make-dependents} {@code true} if the
	 * argument {@code also-make-dependents} was specified, otherwise {@code false}
	 */
	private boolean alsoMakeDependents;
	/**
	 * By default, Maven is executed in batch mode. This mean no interaction with
	 * the Maven process can be done. <code>true</code> if Maven should be executed
	 * in batch mode, <code>false</code> if Maven is executed in interactive mode.
	 */
	private boolean batchMode;
	/**
	 * Set the debug mode of the Maven invocation. By default, Maven is executed in
	 * normal mode. if Maven should be executed in debug mode, <code>false</code> if
	 * the normal mode should be used.
	 */
	private boolean debug;
	/**
	 * Set the path to the global settings for the Maven invocation.
	 * 
	 * The path to the global settings for the Maven invocation or <code>null</code>
	 * to load the global settings from the default location.
	 */
	private String globalSettings;
	/**
	 * Alternate path for the global toolchains file <b>Note. This is available
	 * since Maven 3.3.1</b>
	 * 
	 * The path to the custom global toolchains file or <code>null</code> to load
	 * the global toolchains from the default location.
	 */
	private String globalToolchains;
	/**
	 * Set the checksum mode of the Maven invocation. The checksum mode, one of
	 * {@link CheckSumPolicy#Warn} and {@link CheckSumPolicy#Fail}.
	 */
	private CheckSumPolicy globalChecksumPolicy = CheckSumPolicy.Warn;
	/**
	 * Set the path to the base directory of the Java installation used to run
	 * Maven.
	 * 
	 * The path to the base directory of the Java installation used to run Maven or
	 * <code>null</code> to use the default Java home.
	 */
	private String javaHome;
	/**
	 * Set the path to the base directory of the local repository to use for the
	 * Maven invocation. The path to the base directory of the local repository or
	 * <code>null</code> to use the location from the <code>settings.xml</code>.
	 */
	private String localRepository;
	/**
	 * can either be a file relative to ${maven.home}/bin/ or an absolute file. ie:
	 * ${maven.home}/bin/mvn.cmd or ${maven.home}/bin/mvn
	 */
	private String mavenExecutable;
	/**
	 * Sets the path to the base directory of the Maven installation used to invoke
	 * Maven. This parameter may be left unspecified to use the default Maven
	 * installation which will be discovered by evaluating the system property
	 * <code>maven.home</code> and the environment variable <code>M2_HOME</code>.
	 */
	private String mavenHome;
	/**
	 * The value of the <code>MAVEN_OPTS</code> environment variable or
	 * <code>null</code> if not set.
	 */
	private String mavenOpts;
	/**
	 * Maven Repository collection;
	 */
	private Map<String, String> mavenRepositorys = new HashMap<String, String>();
	/**
	 * Indicates whether Maven should check for plugin updates. By default, plugin
	 * updates are not suppressed. <code>true</code> if plugin updates should be
	 * suppressed, <code>false</code> otherwise.
	 */
	private boolean nonPluginUpdates;
	/**
	 * Set the network mode of the Maven invocation. By default, Maven is executed
	 * in online mode. <code>true</code> if Maven should be executed in offline
	 * mode, <code>false</code> if the online mode is used.
	 */
	private boolean offline;
	/**
	 * Set the system properties for the Maven invocation. The system properties for
	 * the Maven invocation or <code>null</code> if not set.
	 */
	private Properties properties;
	/**
	 * Set the (unqualified) filename of the POM for the Maven invocation.
	 * <code>null</code>. Otherwise, the base directory is assumed to contain a POM
	 * with this name. By default, a file named <code>pom.xml</code> is used. The
	 * (unqualified) filename of the POM for the Maven invocation or
	 * <code>null</code> if not set.
	 */
	private String pomFilename;
	/**
	 * The profiles for the Maven invocation or <code>null</code> if not set.
	 */
	private List<String> profiles;
	/**
	 * A list of specified reactor projects to build instead of all projects. A
	 * project can be specified by [groupId]:artifactId or by its relative path.
	 */
	private List<String> projects;
	/**
	 * Set the failure mode of the Maven invocation. By default, the mode
	 * {@link ReactorFailureBehavior#FailFast} is used.
	 * 
	 * The failure mode, one of {@link ReactorFailureBehavior#FailFast},
	 * {@link ReactorFailureBehavior#FailAtEnd} and
	 * {@link ReactorFailureBehavior#FailNever}.
	 */
	private ReactorFailureBehavior reactorFailureBehavior = ReactorFailureBehavior.FailFast;
	/**
	 * The recursion behavior of a reactor invocation. By default, Maven will
	 * recursive the build into sub modules. <code>true</code> if sub modules should
	 * be build, <code>false</code> otherwise.
	 */
	private boolean recursive = true;
	/**
	 * The value of {@code resume-from}
	 */
	private String resumeFrom;
	/**
	 * Indicates whether the environment variables of the current process should be
	 * propagated to the Maven invocation. By default, the current environment
	 * variables are inherited by the new Maven invocation. <code>true</code> if the
	 * environment variables should be propagated, <code>false</code> otherwise.
	 */
	private boolean shellEnvironmentInherited = true;
	/**
	 * The exception output mode of the Maven invocation. By default, Maven will not
	 * print stack traces of build exceptions. <code>true</code> if Maven should
	 * print stack traces, <code>false</code> otherwise.
	 */
	private boolean showErrors;
	/**
	 * The show version behavior (-V option)
	 */
	private boolean showVersion;
	/**
	 * The environment variables for the Maven invocation or <code>null</code> if
	 * not set.
	 */
	private Map<String, String> shellEnvironments;
	/**
	 * Thread count, for instance 2.0C where C is core multiplied Equivalent of -T or --threads
	 * note: available since Maven3
	 */
	private int threads = 1;
	/**
	 * Indicates whether Maven should enforce an update check for plugins and
	 * snapshots. By default, no update check is performed. <code>true</code> if
	 * plugins and snapshots should be updated, <code>false</code> otherwise.
	 */
	private boolean updateSnapshots;
	/**
	 * Set the path to the user settings for the Maven invocation. The path to the
	 * user settings for the Maven invocation or <code>null</code> to load the user
	 * settings from the default location.
	 */
	private String userSettings;

	public boolean isAlsoMake() {
		return alsoMake;
	}

	public void setAlsoMake(boolean alsoMake) {
		this.alsoMake = alsoMake;
	}

	public boolean isAlsoMakeDependents() {
		return alsoMakeDependents;
	}

	public void setAlsoMakeDependents(boolean alsoMakeDependents) {
		this.alsoMakeDependents = alsoMakeDependents;
	}

	public boolean isBatchMode() {
		return batchMode;
	}

	public void setBatchMode(boolean batchMode) {
		this.batchMode = batchMode;
	}

	public boolean isDebug() {
		return debug;
	}

	public void setDebug(boolean debug) {
		this.debug = debug;
	}

	public String getGlobalSettings() {
		return globalSettings;
	}

	public void setGlobalSettings(String globalSettings) {
		this.globalSettings = globalSettings;
	}

	public String getGlobalToolchains() {
		return globalToolchains;
	}

	public void setGlobalToolchains(String globalToolchains) {
		this.globalToolchains = globalToolchains;
	}

	public CheckSumPolicy getGlobalChecksumPolicy() {
		return globalChecksumPolicy;
	}

	public void setGlobalChecksumPolicy(CheckSumPolicy globalChecksumPolicy) {
		this.globalChecksumPolicy = globalChecksumPolicy;
	}

	public String getJavaHome() {
		return javaHome;
	}

	public void setJavaHome(String javaHome) {
		this.javaHome = javaHome;
	}

	public String getLocalRepository() {
		return localRepository;
	}

	public void setLocalRepository(String localRepository) {
		this.localRepository = localRepository;
	}

	public String getMavenExecutable() {
		return mavenExecutable;
	}

	public void setMavenExecutable(String mavenExecutable) {
		this.mavenExecutable = mavenExecutable;
	}

	public String getMavenHome() {
		return mavenHome;
	}

	public void setMavenHome(String mavenHome) {
		this.mavenHome = mavenHome;
	}

	public String getMavenOpts() {
		return mavenOpts;
	}

	public void setMavenOpts(String mavenOpts) {
		this.mavenOpts = mavenOpts;
	}

	public Map<String, String> getMavenRepositorys() {
		return mavenRepositorys;
	}

	public void setMavenRepositorys(Map<String, String> mavenRepositorys) {
		this.mavenRepositorys = mavenRepositorys;
	}

	public boolean isNonPluginUpdates() {
		return nonPluginUpdates;
	}

	public void setNonPluginUpdates(boolean nonPluginUpdates) {
		this.nonPluginUpdates = nonPluginUpdates;
	}

	public boolean isOffline() {
		return offline;
	}

	public void setOffline(boolean offline) {
		this.offline = offline;
	}

	public Properties getProperties() {
		return properties;
	}

	public void setProperties(Properties properties) {
		this.properties = properties;
	}

	public String getPomFilename() {
		return pomFilename;
	}

	public void setPomFilename(String pomFilename) {
		this.pomFilename = pomFilename;
	}

	public List<String> getProfiles() {
		return profiles;
	}

	public void setProfiles(List<String> profiles) {
		this.profiles = profiles;
	}

	public List<String> getProjects() {
		return projects;
	}

	public void setProjects(List<String> projects) {
		this.projects = projects;
	}

	public ReactorFailureBehavior getReactorFailureBehavior() {
		return reactorFailureBehavior;
	}

	public void setReactorFailureBehavior(ReactorFailureBehavior reactorFailureBehavior) {
		this.reactorFailureBehavior = reactorFailureBehavior;
	}

	public boolean isRecursive() {
		return recursive;
	}

	public void setRecursive(boolean recursive) {
		this.recursive = recursive;
	}

	public String getResumeFrom() {
		return resumeFrom;
	}

	public void setResumeFrom(String resumeFrom) {
		this.resumeFrom = resumeFrom;
	}

	public boolean isShellEnvironmentInherited() {
		return shellEnvironmentInherited;
	}

	public void setShellEnvironmentInherited(boolean shellEnvironmentInherited) {
		this.shellEnvironmentInherited = shellEnvironmentInherited;
	}

	public boolean isShowErrors() {
		return showErrors;
	}

	public void setShowErrors(boolean showErrors) {
		this.showErrors = showErrors;
	}

	public boolean isShowVersion() {
		return showVersion;
	}

	public void setShowVersion(boolean showVersion) {
		this.showVersion = showVersion;
	}

	public Map<String, String> getShellEnvironments() {
		return shellEnvironments;
	}

	public void setShellEnvironments(Map<String, String> shellEnvironments) {
		this.shellEnvironments = shellEnvironments;
	}
	
	public int getThreads() {
		return threads;
	}

	public void setThreads(int threads) {
		this.threads = threads;
	}

	public boolean isUpdateSnapshots() {
		return updateSnapshots;
	}

	public void setUpdateSnapshots(boolean updateSnapshots) {
		this.updateSnapshots = updateSnapshots;
	}

	public String getUserSettings() {
		return userSettings;
	}

	public void setUserSettings(String userSettings) {
		this.userSettings = userSettings;
	}

	public InvocationRequest newRequest() {

		InvocationRequest request = new DefaultInvocationRequest();

		request.setAlsoMake(this.isAlsoMake());
		request.setAlsoMakeDependents(this.isAlsoMakeDependents());
		request.setBatchMode(this.isBatchMode());
		request.setDebug(this.isDebug());
		request.setGlobalChecksumPolicy(this.getGlobalChecksumPolicy());
		// Sets the path to the global settings for the Maven invocation. Equivalent of -gs and --global-settings
		if (StringUtils.hasText(this.getGlobalSettings())) {
			request.setGlobalSettingsFile(new File(this.getGlobalSettings()));
		}
		// Sets the alternate path for the global toolchains file Equivalent of -gt or --global-toolchains
		if (StringUtils.hasText(this.getGlobalToolchains())) {
			request.setGlobalToolchainsFile(new File(this.getGlobalToolchains()));
		}
		// Sets the path to the base directory of the Java installation used to run Maven.
		if (StringUtils.hasText(this.getJavaHome())) {
			request.setJavaHome(new File(this.getJavaHome()));
		}
		// Sets the path to the base directory of the local repository to use for the Maven invocation.
		if (StringUtils.hasText(this.getLocalRepository())) {
			File localRepositoryDirectory = new File(this.getLocalRepository());
			if (localRepositoryDirectory.exists() && localRepositoryDirectory.isDirectory()) {
				request.setLocalRepositoryDirectory(localRepositoryDirectory);
			} else {
				localRepositoryDirectory.mkdir();
				request.setLocalRepositoryDirectory(localRepositoryDirectory);
			}
		} else {
			request.setLocalRepositoryDirectory(defaultUserLocalRepository);
		}
		// Sets the value of the MAVEN_OPTS environment variable.
		if (StringUtils.hasText(this.getMavenOpts())) {
			request.setMavenOpts(this.getMavenOpts());
		}
		request.setNonPluginUpdates(this.isNonPluginUpdates());
		request.setOffline(this.isOffline());
		request.setProfiles(this.getProfiles());
		request.setProjects(this.getProjects());
		request.setProperties(this.getProperties());
		request.setReactorFailureBehavior(this.getReactorFailureBehavior());
		request.setRecursive(this.isRecursive());
		// Resume reactor from specified project. Equivalent of -rf or --resume-from
		if (StringUtils.hasText(this.getResumeFrom())) {
			request.setResumeFrom(this.getResumeFrom());
		}
		request.setShellEnvironmentInherited(this.isShellEnvironmentInherited());
		request.setShowErrors(this.isShowErrors());
		request.setShowVersion(this.isShowVersion());
		request.setThreads(String.valueOf(this.getThreads()));
		request.setUpdateSnapshots(this.isUpdateSnapshots());
		// Sets the path to the user settings for the Maven invocation. Equivalent of -s and --settings
		if (StringUtils.hasText(this.getUserSettings())) {
			request.setUserSettingsFile(new File(this.getUserSettings()));
		}

		return request;
	}
	
}
