package woped_dependency_visualizer;

import java.util.ArrayList;

import woped_dependency_visualizer.analyzer.StaticAnalyzer;
import woped_dependency_visualizer.analyzer.WopedProjectAnalyzer;
import woped_dependency_visualizer.utils.WopedProjectFolder;
import woped_dependency_visualizer.visualizer.WopedVisualizer;

public class App {
	public static void main(String args[]) {
		WopedProjectAnalyzer a = new WopedProjectAnalyzer();
		ArrayList<WopedProjectFolder> projects = a.analyze(""); //Woped Project-Folder
		
		WopedVisualizer v = new WopedVisualizer();
		v.visualize(projects); 
	}
}
