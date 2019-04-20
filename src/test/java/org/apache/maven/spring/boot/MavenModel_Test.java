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
import java.io.FileFilter;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Enumeration;
import java.util.Scanner;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;

import org.apache.maven.model.Model;
import org.apache.maven.model.io.xpp3.MavenXpp3Reader;
import org.junit.Test;

/**
 * TODO
 * 
 * @author ： <a href="https://github.com/vindell">vindell</a>
 */

public class MavenModel_Test {

	@Test
	public void testTheVersionClass() {
		System.out.println("Implementation Title:" + this.getClass().getPackage().getImplementationTitle());
		System.out.println("Implementation Vendor:" + this.getClass().getPackage().getImplementationVendor());
		System.out.println("Implementation Version:" + this.getClass().getPackage().getImplementationVersion());
		System.out.println("Specification Tile:" + this.getClass().getPackage().getSpecificationTitle());
		System.out.println("Specification Vendor:" + this.getClass().getPackage().getSpecificationVendor());
		System.out.println("Specification Version:" + this.getClass().getPackage().getSpecificationVersion());
	}

	static class jarFilter implements FileFilter {
		public boolean accept(File pathName) {
			String upcase = pathName.getName().toUpperCase();
			return upcase.endsWith(".JAR");
		}
	}
	
	@Test
	public void testname() throws Exception {

		MavenXpp3Reader reader = new MavenXpp3Reader();

		try (ZipFile zipFile = new ZipFile("E:\\tianyin-dsb-plugin-demo-1.0.0-SNAPSHOT.jar")) {
			Enumeration<? extends ZipEntry> entries = zipFile.entries();
			while (entries.hasMoreElements()) {
				ZipEntry entry = entries.nextElement();
				//System.out.println(entry.getName());
				if (entry.getName().endsWith("pom.xml")) {

					InputStream input = zipFile.getInputStream(entry);
					Model model = reader.read(new InputStreamReader(input));
					
					
					
					System.out.println("Id :" + model.getId());
					System.out.println("GroupId :" + model.getGroupId());
					System.out.println("ArtifactId :" + model.getArtifactId());
					System.out.println("ArtifactId :" + model.getArtifactId());
					System.out.println("Version :" + model.getVersion());

					System.out.println("Parent GroupId :" + model.getParent().getGroupId());
					System.out.println("Parent ArtifactId :" + model.getParent().getArtifactId());
					System.out.println("Parent Version :" + model.getParent().getVersion());
					
				}
			}
		} catch (IOException ex) {
			System.out.println(ex.toString());
		}

		/*
		 * Model model;
		 * 
		 * 
		 * 
		 * if ((new File("pom.xml")).exists()) model = reader.read(new
		 * FileReader("pom.xml")); else model = reader.read(new
		 * InputStreamReader(this.getClass().getResourceAsStream(
		 * "/META-INF/maven/de.scrum-master.stackoverflow/aspectj-introduce-method/pom.xml"
		 * ))); System.out.println(model.getId());
		 * System.out.println(model.getGroupId());
		 * System.out.println(model.getArtifactId());
		 * System.out.println(model.getVersion());
		 */

	}
}
