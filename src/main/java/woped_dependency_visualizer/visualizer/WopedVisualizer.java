package woped_dependency_visualizer.visualizer;

import java.io.StringWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import org.jgrapht.Graph;
import org.jgrapht.ListenableGraph;
import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.nio.DefaultAttribute;
import org.jgrapht.nio.dot.DOTExporter;
import org.jgrapht.nio.Attribute;

import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.utils.WopedGraphUtil;

public class WopedVisualizer {
	private ArrayList<WopedProjectFolder> wopedProjects;

	public void visualize(ArrayList<WopedProjectFolder> projects, String centralPgk) {
		this.wopedProjects = projects;
		ListenableGraph<String, MyEdge> graph = WopedGraphUtil
				.buildGraph(getWopedProjectFolderForName(centralPgk));

		exportGraph(graph);

		MxGraphViewer v = new MxGraphViewer(new JGraphXAdapter<String, MyEdge>(graph));
		v.visualize();
	}

	private void exportGraph(Graph<String, MyEdge> graph) {
		 DOTExporter<String, MyEdge> exporter =
		            new DOTExporter<>(v -> v.toString().replace("-", "_"));
		        exporter.setVertexAttributeProvider((v) -> {
		            Map<String, Attribute> map = new LinkedHashMap<>();
		            map.put("label", DefaultAttribute.createAttribute(v.toString()));
		            return map;
		        });
		        Writer writer = new StringWriter();
		        exporter.exportGraph(graph, writer);
		        
		        System.out.println("Copy/Paste to https://dreampuf.github.io/GraphvizOnline/");
		        System.out.println(writer.toString());
	}


	private WopedProjectFolder getWopedProjectFolderForName(String name) {
		for (WopedProjectFolder wpf : wopedProjects) {
			if (wpf.getName().equals(name)) {
				return wpf;
			}
		}
		return null;
	}
}
