package woped_dependency_visualizer;

import java.util.ArrayList;

import org.apache.log4j.Logger;

import woped_dependency_visualizer.analyzer.WopedStaticAnalyzer;
import woped_dependency_visualizer.analyzer.WopedProjectAnalyzer;
import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.visualizer.WopedVisualizer;

public class App {
	private static Logger LOGGER = Logger.getLogger(App.class);
	public static void main(String args[]) {
		LOGGER.debug("Start App");
		WopedProjectAnalyzer a = new WopedProjectAnalyzer();
		ArrayList<WopedProjectFolder> projects = a.analyze(""); //Woped Project-Folder
		
		WopedVisualizer v = new WopedVisualizer();
		v.visualize(projects);
		
		WopedStaticAnalyzer s = new WopedStaticAnalyzer();
		s.addDependencyCountToDependencies(projects);
	}
}
