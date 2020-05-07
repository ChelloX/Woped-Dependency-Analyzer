package woped_dependency_visualizer.utils;

import java.util.ArrayList;
import java.util.List;

import org.jgrapht.ListenableGraph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.DefaultListenableGraph;

public class WopedGraphUtil {

	public static ListenableGraph<String, DefaultEdge> buildGraph(WopedProjectFolder wpf) {
		DefaultListenableGraph<String, DefaultEdge> g = new DefaultListenableGraph<>(
				new DefaultDirectedGraph<>(DefaultEdge.class));
		List<WopedProjectFolder> alreadySeen = new ArrayList<WopedProjectFolder>();
		buildGraphRecursive(g, wpf, alreadySeen);

		return g;
	}

	private static void buildGraphRecursive(DefaultListenableGraph<String, DefaultEdge> g, WopedProjectFolder wpf, List<WopedProjectFolder> alreadySeen) {
		
		g.addVertex(wpf.getName());

		alreadySeen.add(wpf);

		for (WopedProjectFolder wpfChild : wpf.getDependenciesIntern()) {
			g.addVertex(wpfChild.getName());
			g.addEdge(wpf.getName(), wpfChild.getName());

			if (!alreadySeen.contains(wpfChild)) {
				buildGraphRecursive(g, wpfChild, alreadySeen);
			}
		}
	}
}
