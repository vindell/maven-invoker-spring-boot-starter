package org.apache.maven.spring.boot;

import java.io.File;
import java.util.Arrays;

import org.apache.maven.shared.invoker.DefaultInvocationRequest;
import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationRequest;
import org.apache.maven.shared.invoker.InvocationResult;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.MavenInvocationException;
import org.junit.Test;

/**
 * https://www.sourcetrail.com/blog/how_to_integrate_maven_into_your_own_java_tool/
 */
public class MavenInvoker_Test {

	//@Test
	public void testInstall() throws MavenInvocationException {

		InvocationRequest request = new DefaultInvocationRequest();

		request.setLocalRepositoryDirectory(new File("E:\\Java\\.m2\\repository"));

		request.setBaseDirectory(new File("D:\\"));
		request.setGoals(Arrays.asList("install:install-file", "-Dfile=p6spy-3.8.1.jar", "-DgroupId=p6spy", "-DartifactId=p6spy",
						"-Dversion=3.8.1", "-Dpackaging=jar", "-DgeneratePom=true", "-DcreateChecksum=true"));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File("D:\\Java\\maven\\apache-maven-3.5.3"));
		InvocationResult result = invoker.execute(request);
		
		System.out.println("ExitCode:" + result.getExitCode()); // 0 成功，1 失败
		System.out.println("Exception:" + result.getExecutionException());

	}
	
	@Test
	public void testDeploy() throws MavenInvocationException {
		
		InvocationRequest request = new DefaultInvocationRequest();

		request.setLocalRepositoryDirectory(new File("E:\\Java\\.m2\\repository"));

		request.setBaseDirectory(new File("D:\\"));
		request.setGoals(Arrays.asList("deploy:deploy-file", "-Dfile=p6spy-3.8.1.jar", "-DgroupId=p6spy", "-DartifactId=p6spy",
						"-Dversion=3.8.1", "-Dpackaging=jar", "-Durl=http://127.0.0.1:8081/repository/maven-releases/", "-DrepositoryId=nexus-releases"));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File("D:\\Java\\maven\\apache-maven-3.5.3"));
		InvocationResult result = invoker.execute(request);

		System.out.println("ExitCode:" + result.getExitCode());
		System.out.println("Exception:" + result.getExecutionException());

	}

	//@Test
	public void testExecute() throws MavenInvocationException {

		InvocationRequest request = new DefaultInvocationRequest();

		request.setLocalRepositoryDirectory(new File("E:\\Java\\.m2\\repository"));

		//request.setBaseDirectory(new File("D:\\project-dir"));
		request.setGoals(Arrays.asList("clean", "install"));

		Invoker invoker = new DefaultInvoker();
		invoker.setMavenHome(new File("D:\\Java\\maven\\apache-maven-3.5.3"));
		InvocationResult result = invoker.execute(request);

		System.out.println("ExitCode:" + result.getExitCode());
		System.out.println("Exception:" + result.getExecutionException());
		
	} 

}
