package woped_dependency_visualizer.visualizer;

import java.awt.Dimension;

import javax.swing.JFrame;

import org.jgrapht.ext.JGraphXAdapter;
import org.jgrapht.graph.DefaultEdge;

import com.mxgraph.layout.mxCompactTreeLayout;
import com.mxgraph.swing.mxGraphComponent;

public class MxGraphViewer {
	JGraphXAdapter<String, DefaultEdge> graphXAdapter;
	
	public MxGraphViewer(JGraphXAdapter<String, DefaultEdge> jGraphXAdapter) {
		this.graphXAdapter = jGraphXAdapter;
	}

	public void visualize() {
		JFrame jf = new JFrame();
		jf.setTitle("Woped-Dependency-Visualizer");
		
		mxGraphComponent component = new mxGraphComponent(graphXAdapter);
		component.setConnectable(false);
		component.getGraph().setAllowDanglingEdges(false);
		
		mxCompactTreeLayout layout = new mxCompactTreeLayout(graphXAdapter);
		layout.setLevelDistance(100);
		layout.execute(graphXAdapter.getDefaultParent());
		
		
		jf.add(component);
		jf.setVisible(true);
		jf.setSize(new Dimension(1500, 500));
	}

}
