# maven-invoker-spring-boot-starter
Spring Boot Starter For Maven Invoker

### 说明

 > 基于 maven-invoker 的 Spring Boot Starter 实现

1. 整合 maven-invoker

### Maven

``` xml
<dependency>
	<groupId>com.github.hiwepy</groupId>
	<artifactId>maven-invoker-spring-boot-starter</artifactId>
	<version>$1.0.3.RELEASE</version>
</dependency>
```

### Sample

```java

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

```
