package org.apache.maven.spring.boot;

import java.io.File;

import org.apache.maven.shared.invoker.DefaultInvoker;
import org.apache.maven.shared.invoker.InvocationOutputHandler;
import org.apache.maven.shared.invoker.Invoker;
import org.apache.maven.shared.invoker.InvokerLogger;
import org.apache.maven.shared.invoker.PrintStreamHandler;
import org.apache.maven.shared.invoker.SystemOutHandler;
import org.apache.maven.shared.invoker.SystemOutLogger;
import org.apache.maven.spring.boot.ext.MavenInvokerTemplate;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

@Configuration
@ConditionalOnClass({ DefaultInvoker.class })
@EnableConfigurationProperties({ MavenInvokerProperties.class })
public class MavenInvokerAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public InvocationOutputHandler outputHandler() {
		return new SystemOutHandler();
	}

	@Bean
	@ConditionalOnMissingBean
	public InvocationOutputHandler errorHandler() {
		return new PrintStreamHandler(System.err, false);
	}

	@Bean
	@ConditionalOnMissingBean
	public InvokerLogger invokerLogger() {
		return new SystemOutLogger();
	}

	@Bean
	@ConditionalOnMissingBean
	public Invoker mavenInvoker(InvocationOutputHandler outputHandler, InvocationOutputHandler errorHandler,
			InvokerLogger invokerLogger, MavenInvokerProperties properties) {

		Invoker invoker = new DefaultInvoker();

		// Sets the handler used to capture the error output from the Maven build.
		invoker.setErrorHandler(errorHandler);
		// Sets the path to the base directory of the local repository to use for the
		// Maven invocation.
		if (StringUtils.hasText(properties.getLocalRepository())) {
			File localRepositoryDirectory = new File(properties.getLocalRepository());
			if (localRepositoryDirectory.exists() && localRepositoryDirectory.isDirectory()) {
				invoker.setLocalRepositoryDirectory(localRepositoryDirectory);
			} else {
				localRepositoryDirectory.mkdir();
				invoker.setLocalRepositoryDirectory(localRepositoryDirectory);
			}
		} else {
			invoker.setLocalRepositoryDirectory(MavenInvokerProperties.defaultUserLocalRepository);
		}
		// Sets the logger used by this invoker to output diagnostic messages.
		invoker.setLogger(invokerLogger);
		//
		if (StringUtils.hasText(properties.getMavenExecutable())) {
			invoker.setMavenExecutable(new File(properties.getMavenExecutable()));
		}
		// Sets the path to the base directory of the Maven installation used to invoke
		// Maven.
		if (StringUtils.hasText(properties.getMavenHome())) {
			invoker.setMavenHome(new File(properties.getMavenHome()));
		}
		// Sets the handler used to capture the standard output from the Maven build.
		invoker.setOutputHandler(outputHandler);

		return invoker;
	}

	@Bean
	public MavenInvokerTemplate mavenInvokerTemplate(InvocationOutputHandler outputHandler,
			InvocationOutputHandler errorHandler, Invoker mavenInvoker, MavenInvokerProperties invokerProperties) {
		return new MavenInvokerTemplate(outputHandler, errorHandler, mavenInvoker, invokerProperties);
	}

}
