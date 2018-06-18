package com.src.app;

import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

import javax.swing.JFrame;
import javax.swing.JPanel;

import edu.uci.ics.jung.algorithms.layout.CircleLayout;
import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.SparseMultigraph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.samples.SimpleGraphDraw;
import edu.uci.ics.jung.visualization.BasicTransformer;
import edu.uci.ics.jung.visualization.BasicVisualizationServer;
import edu.uci.ics.jung.visualization.control.EditingModalGraphMouse;
import edu.uci.ics.jung.visualization.control.GraphMouseListener;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;

public class GraphGenerator {
	private SortedMap<String, SortedSet<String>> toMap;
	private JFrame graphFrame;
	private String center;
	private String dependencies;

	public GraphGenerator(String filename, JFrame toFrame, SortedMap<String, SortedSet<String>> mapped, String depend) {
		this.toMap = mapped;
		this.graphFrame = toFrame;
		this.center = filename;
		this.dependencies = depend;
	}
	
	public void generateDependenciesGraph() throws IOException {
		if (dependencies == null) {
			Graph<String,String> g = new DirectedSparseMultigraph<String, String>();
			g.addVertex(center);
			
			FRLayout clayout=new FRLayout(g);
			Layout<String, String> layout = clayout;
			layout.setSize(new Dimension(1548,867)); // sets the initial size of the space
			BasicVisualizationServer<String, String> vv = new BasicVisualizationServer<String,String>(layout);
			vv.setPreferredSize(new Dimension(1548,867)); //Sets the viewing area size
			 
			vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
			vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);


			JPanel dependenciesPanel = new JPanel();
			dependenciesPanel.add(vv);
			dependenciesPanel.setBackground(new Color(255, 255, 255));
			dependenciesPanel.setBounds(15, 61, 1548, 867);
			graphFrame.getContentPane().add(dependenciesPanel);
		}
	}
	
	public void generateClassGraph() throws IOException {
		Graph<String,String> g = new DirectedSparseMultigraph<String, String>();
		for ( String key : this.toMap.keySet() ) {
			for(String duo : this.toMap.get(key)) {
				try {
					g.addEdge(key+duo, key,duo, EdgeType.DIRECTED);
				}
				catch(java.lang.IllegalArgumentException e){
					e.printStackTrace();
				}
			}
		}
		FRLayout clayout=new FRLayout(g);
		Layout<String, String> layout = clayout;
		layout.setSize(new Dimension(1548,867)); // sets the initial size of the space
		BasicVisualizationServer<String, String> cv = new BasicVisualizationServer<String,String>(layout);
		cv.setPreferredSize(new Dimension(1548,867)); //Sets the viewing area size
		 
		cv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		cv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);

		JPanel graphPanel = new JPanel();
		graphPanel.add(cv);
		graphPanel.setBackground(new Color(255, 255, 255));
		graphPanel.setBounds(15, 61, 1548, 867);
		graphFrame.getContentPane().add(graphPanel);
	}

}