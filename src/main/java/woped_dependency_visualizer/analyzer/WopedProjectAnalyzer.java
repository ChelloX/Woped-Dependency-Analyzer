package woped_dependency_visualizer.analyzer;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import woped_dependency_visualizer.utils.WopedProjectFolder;

public class WopedProjectAnalyzer {
	private ArrayList<String> foldersToIgnore;

	public WopedProjectAnalyzer() {
		foldersToIgnore = new ArrayList<String>();

		foldersToIgnore.add(".settings");
		foldersToIgnore.add(".idea");
		foldersToIgnore.add(".git"); // <-- Wird warum auch immer als Directory angesehen und nicht als File

		foldersToIgnore.add("WoPeD-ProjectFiles");
		foldersToIgnore.add("WoPeD-TranslationEditor");
		foldersToIgnore.add("bin");
	}

	public ArrayList<WopedProjectFolder> analyze(String wopedProjectDir) {
		ArrayList<WopedProjectFolder> wopedProjects = createWopedProjects(wopedProjectDir);
		addDependenciesToProjects(wopedProjects);
		return wopedProjects;
	}

	private ArrayList<WopedProjectFolder> createWopedProjects(String wopedProjectDir) {
		ArrayList<WopedProjectFolder> wopedProjects = new ArrayList<WopedProjectFolder>();
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
		return wopedProjects;
	}
	
	private ArrayList<WopedProjectFolder> addDependenciesToProjects(ArrayList<WopedProjectFolder> wopedProjects) {
		for (WopedProjectFolder wpf : wopedProjects) {

			File pwfIml = new File(wpf.getFile().getAbsoluteFile() + "\\" + wpf.getName() + ".iml");

			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder;
			try {
				dBuilder = dbFactory.newDocumentBuilder();
				Document doc = dBuilder.parse(pwfIml);
				NodeList orderEntries = doc.getElementsByTagName("orderEntry");

				for (int i = 0; i < orderEntries.getLength(); i++) {
					Node orderEntry = orderEntries.item(i);
					if (orderEntry.getAttributes().getNamedItem("type").getTextContent().equals("module")) {
						if (getWopedProjectFolderForName(wopedProjects,
								orderEntry.getAttributes().getNamedItem("module-name").getTextContent()) != null) {
							wpf.addDependenciesIntern(getWopedProjectFolderForName(wopedProjects,
									orderEntry.getAttributes().getNamedItem("module-name").getTextContent()));
						}
					}
				}

			} catch (SAXException | ParserConfigurationException | IOException e) {
				e.printStackTrace();
			}
		}
		
		return wopedProjects;
	}

	private WopedProjectFolder getWopedProjectFolderForName(ArrayList<WopedProjectFolder> projects, String name) {
		for (WopedProjectFolder wpf : projects) {
			if (wpf.getName().equals(name)) {
				return wpf;
			}
		}
		return null;
	}
}
