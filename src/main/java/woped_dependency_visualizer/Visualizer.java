package woped_dependency_visualizer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultEdge;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class Visualizer {
	private ArrayList<String> foldersToIgnore;
	private ArrayList<WopedProjectFolder> wopedProjects;

	public Visualizer() {
		foldersToIgnore = new ArrayList<String>();
		wopedProjects = new ArrayList<WopedProjectFolder>();

		foldersToIgnore.add(".settings");
		foldersToIgnore.add(".idea");
		foldersToIgnore.add(".git"); // <-- Wird warum auch immer als Directory angesehen und nicht als File
		
		foldersToIgnore.add("WoPeD-ProjectFiles");
		foldersToIgnore.add("WoPeD-TranslationEditor");
	}

	public void doSth(String wopedProjectDir) {
		createWopedProjects(wopedProjectDir);
		System.out.println("Anzahl der WopedProjektFolder: " + wopedProjects.size());
		analyzeDependencies();
		
		ListenableGraph<String, DefaultEdge> graph = WopedGraphUtil.buildGraph(getWopedProjectFolderForName("WoPeD-Starter"));
		
		JGraphXAdapterDemo viewer = new JGraphXAdapterDemo(graph);
		viewer.init();
	}

	private void analyzeDependencies() {
		for (WopedProjectFolder wpf : wopedProjects) {
			
			File pwfIml = new File(wpf.getFile().getAbsoluteFile() + "\\" + wpf.getName() + ".iml");
			
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(pwfIml);
				NodeList orderEntries = doc.getElementsByTagName("orderEntry");
				
				for(int i = 0; i < orderEntries.getLength(); i++) {
					Node orderEntry = orderEntries.item(i);
					if(orderEntry.getAttributes().getNamedItem("type").getTextContent().equals("module")) {
						if(getWopedProjectFolderForName(orderEntry.getAttributes().getNamedItem("module-name").getTextContent()) != null) {
							wpf.addDependenciesIntern(getWopedProjectFolderForName(orderEntry.getAttributes().getNamedItem("module-name").getTextContent()));
						}
					}
				}
				
			} catch (SAXException | ParserConfigurationException | IOException e) {
				e.printStackTrace();
			}
		}
	}

	private void createWopedProjects(String wopedProjectDir) {
		File wopedProjektFile = new File(wopedProjectDir);
		File[] files = wopedProjektFile.listFiles();
		for (File f : files) {
			if (!foldersToIgnore.contains(f.getName())) {
				if (f.isDirectory()) {
					WopedProjectFolder wpf = new WopedProjectFolder(f);
					wopedProjects.add(wpf);
				}
			}
		}
	}
	
	private WopedProjectFolder getWopedProjectFolderForName(String name) {
		for(WopedProjectFolder wpf : wopedProjects) {
			if(wpf.getName().equals(name)) {
				return wpf;
			}
		}
		return null;
	}
}
