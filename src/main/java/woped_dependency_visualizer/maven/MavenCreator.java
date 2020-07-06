package woped_dependency_visualizer.maven;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.data.WopedProjectFolderDependency;

public class MavenCreator {

	public void create(ArrayList<WopedProjectFolder> projects, String path) throws Exception {
		for (WopedProjectFolder wpf : projects) {
			createMavenProject(wpf, path);
		}
	}

	private void createMavenProject(WopedProjectFolder wpf, String path) throws Exception {
		wpf.getName();
		File pomVorlage = new File(
				"F:\\Daniel\\DHBW\\6.Semester\\Integrationsseminar\\001DEV\\001temp\\pomVorlage.xml");

		File f1 = new File(path + "\\" + wpf.getName());
		File f2 = new File(f1 + "\\src");
		File f3 = new File(f2 + "\\main");
		File f4 = new File(f3 + "\\java");
		File f5 = new File(f4 + "\\org");
		File f6 = new File(f5 + "\\woped");
		File f7 = new File(f6 + "\\" + wpf.getName());

		File f8 = new File(f2 + "\\test");
		File f9 = new File(f8 + "\\java");
		File f10 = new File(f9 + "\\org");
		File f11 = new File(f10 + "\\woped");
		File f12 = new File(f11 + "\\" + wpf.getName());

		f1.mkdir();
		f2.mkdir();
		f3.mkdir();
		f4.mkdir();
		f5.mkdir();
		f6.mkdir();
		f7.mkdir();
		f8.mkdir();
		f9.mkdir();
		f10.mkdir();
		f11.mkdir();
		f12.mkdir();

		File pom = new File(f1 + "\\pom.xml");

		FileUtils.copyFile(pomVorlage, pom);
		editPom(wpf, pom);
	}

	private void editPom(WopedProjectFolder wpf, File pom) throws Exception {
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder;
		dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(pom);
		
		Node artifactId = doc.getElementsByTagName("artifactId").item(0);
		artifactId.setTextContent(wpf.getName());
		
		Node name = doc.getElementsByTagName("name").item(0);
		name.setTextContent(wpf.getName());
		
		Node dependencies =doc.getElementsByTagName("dependencies").item(0);
		
		
		for(WopedProjectFolderDependency dep : wpf.getDependenciesIntern()) {
			WopedProjectFolder wpfDep = dep.getDependency();
			Element dependencyElement = doc.createElement("dependency");
			Element groupdIdElement = doc.createElement("groupId");
			groupdIdElement.setTextContent("org.woped");
			Element artifactIdElement = doc.createElement("artifactId");
			artifactIdElement.setTextContent(wpfDep.getName());
			Element versionElement = doc.createElement("version");
			versionElement.setTextContent("0.0.1-SNAPSHOT");
			
			dependencyElement.appendChild(groupdIdElement);
			dependencyElement.appendChild(artifactIdElement);
			dependencyElement.appendChild(versionElement);
			
			dependencies.appendChild(dependencyElement);
		}
		
		TransformerFactory tf = TransformerFactory.newInstance();
		Transformer transformer = tf.newTransformer();
		DOMSource source = new DOMSource(doc);
		StreamResult sr = new StreamResult(pom);
		transformer.transform(source, sr);
	}

}
