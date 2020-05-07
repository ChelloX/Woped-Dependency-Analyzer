package woped_dependency_visualizer.utils;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultListenableGraph;

import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.data.WopedProjectFolderDependency;
import woped_dependency_visualizer.visualizer.MyEdge;

public class WopedGraphUtil {

	public static ListenableGraph<String, MyEdge> buildGraph(WopedProjectFolder wpf) {
		DefaultListenableGraph<String, MyEdge> g = new DefaultListenableGraph<>(
				new DefaultDirectedGraph<>(MyEdge.class));
		List<WopedProjectFolder> alreadySeen = new ArrayList<WopedProjectFolder>();
		buildGraphRecursive(g, wpf, alreadySeen);

		return g;
	}

	private static void buildGraphRecursive(DefaultListenableGraph<String, MyEdge> g, WopedProjectFolder wpf, List<WopedProjectFolder> alreadySeen) {
		
		g.addVertex(wpf.getName());

		alreadySeen.add(wpf);

		for (WopedProjectFolderDependency wpfDep : wpf.getDependenciesIntern()) {
			WopedProjectFolder wpfChild = wpfDep.getDependency();
			g.addVertex(wpfChild.getName());
			g.addEdge(wpf.getName(), wpfChild.getName());
			if (!alreadySeen.contains(wpfChild)) {
				buildGraphRecursive(g, wpfChild, alreadySeen);
			}
		}
	}
}
