package com.src.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.SortedMap;
import java.util.SortedSet;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import edu.uci.ics.jung.algorithms.layout.FRLayout;
import edu.uci.ics.jung.algorithms.layout.Layout;
import edu.uci.ics.jung.graph.DirectedSparseMultigraph;
import edu.uci.ics.jung.graph.Graph;
import edu.uci.ics.jung.graph.util.EdgeType;
import edu.uci.ics.jung.visualization.decorators.ToStringLabeller;
import edu.uci.ics.jung.visualization.picking.PickedState;
import edu.uci.ics.jung.visualization.renderers.Renderer.VertexLabel.Position;
import edu.uci.ics.jung.visualization.VisualizationViewer;
import edu.uci.ics.jung.visualization.control.DefaultModalGraphMouse;
import edu.uci.ics.jung.visualization.control.ModalGraphMouse.Mode;

public class GraphGenerator {
	private SortedMap<String, SortedSet<String>> toMap;
	private JPanel graphFrame;
	private String center;
	private String dependencies;
	private JPanel graphPanel = new JPanel();
	private JPanel dependenciesPanel = new JPanel();
	private TableModel model;
	private JTable table;
	private JScrollPane sPane;
	private JPanel tablePanel = new JPanel();

	public GraphGenerator(String filename, JPanel toFrame, SortedMap<String, SortedSet<String>> mapped, String depend) {
		this.toMap = mapped;
		this.graphFrame = toFrame;
		this.center = filename;
		this.dependencies = depend;
		try {
			graphFrame.add(dependenciesPanel);
			graphFrame.add(graphPanel);
		} catch(java.lang.NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void generateDependenciesGraph() throws IOException {
		Graph<String,String> g = new DirectedSparseMultigraph<String, String>();
		g.addVertex(center);
		if (dependencies != null) {
			String[] split = dependencies.split("\\s+");
			for (int i = 0; i< split.length;i++) {
				g.addVertex(split[i]);
				g.addEdge(center+split[i], center,split[i], EdgeType.DIRECTED);
			}
		}
		
		Collection<String> h =g.getVertices();
		Object[] keyArray = h.toArray();
		
		
		String[] columns = {"Clase","Grado saliente","Grado entrante"};
		Object[][] rows = new Object[h.size()][3];
		
		for (int i = 0; i < h.size(); i++) {
			rows[i][0] = keyArray[i];
			rows[i][1] = g.outDegree((String) keyArray[i]);
			rows[i][2] = g.inDegree((String) keyArray[i]);
		}
		
	    
	    
	    model = new DefaultTableModel(rows,columns);
	    table = new JTable(model);
	    sPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    sPane.setPreferredSize(new Dimension(400, 867));
	    //sPane.getViewport().add(table);
	    tablePanel.setVisible(false);
	    tablePanel = new JPanel();
	    tablePanel.setPreferredSize(new Dimension(400, 867));
	    tablePanel.setBounds(1148, 0, 400, 867);
	    tablePanel.add(sPane);
	    
		
		FRLayout clayout=new FRLayout(g);
		Layout<String, String> layout = clayout;
		layout.setSize(new Dimension(1148,867)); // sets the initial size of the space
		VisualizationViewer<String, String> vv = new VisualizationViewer<String,String>(layout);
		vv.setPreferredSize(new Dimension(1148,867)); //Sets the viewing area size
		 
		vv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		vv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
		final PickedState<String> pickedState = vv.getPickedVertexState();
		
		pickedState.addItemListener(new ItemListener() {
			 
		    public void itemStateChanged(ItemEvent e) {
		    Object subject = e.getItem();
		        // The graph uses Integers for vertices.
		        if (subject instanceof String) {
		            String vertex = (String) subject;
		            if (pickedState.isPicked(vertex)) {
		                //System.out.println("Vertex " + vertex + " is now selected");'
		            	
		            } else {
		            	try {
							generateClassGraph();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		                //System.out.println("Vertex " + vertex+ " no longer selected");
		            }
		        }
		    }
		});

		// Set the mouse to "picking" mode so that vertices and edges can be selected.
		DefaultModalGraphMouse<String, String> modalMouse = new DefaultModalGraphMouse<String, String>();
		modalMouse.setMode(Mode.PICKING);
		vv.setGraphMouse(modalMouse);

		dependenciesPanel = new JPanel();
		dependenciesPanel.add(vv);
		dependenciesPanel.setBackground(new Color(255, 255, 255));
		dependenciesPanel.setBounds(0, 0, 1148, 867);
		graphFrame.add(dependenciesPanel);
		graphFrame.add(tablePanel);
		try {
			//dependenciesPanel.setVisible(true);
			graphPanel.setVisible(false);
			graphFrame.remove(graphPanel);
		}
		catch(java.lang.NullPointerException e){
			//e.printStackTrace();
		}
		
	}
	
	public void generateClassGraph() throws IOException {
		Graph<String,String> g = new DirectedSparseMultigraph<String, String>();
		int length = 0;
		for ( String key : this.toMap.keySet() ) {
			length++;
			for(String duo : this.toMap.get(key)) {
				try {
					g.addEdge(key+duo, key,duo, EdgeType.DIRECTED);
				}
				catch(java.lang.IllegalArgumentException e){
					e.printStackTrace();
				}
			}
		}
		
		Collection<String> h =g.getVertices();
		Object[] keyArray = h.toArray();
		
		
		String[] columns = {"Clase","Grado saliente","Grado entrante"};
		Object[][] rows = new Object[h.size()][3];
		
		for (int i = 0; i < h.size(); i++) {
			rows[i][0] = keyArray[i];
			rows[i][1] = g.outDegree((String) keyArray[i]);
			rows[i][2] = g.inDegree((String) keyArray[i]);
		}
		
	    
	    
	    model = new DefaultTableModel(rows,columns);
	    table = new JTable(model);
	    sPane = new JScrollPane(table,JScrollPane.VERTICAL_SCROLLBAR_ALWAYS,JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
	    sPane.setPreferredSize(new Dimension(400, 867));
	    //sPane.getViewport().add(table);
	    tablePanel.setVisible(false);
	    tablePanel = new JPanel();
	    tablePanel.setPreferredSize(new Dimension(400, 867));
	    tablePanel.setBounds(1148, 0, 400, 867);
	    tablePanel.add(sPane);
	    
		
		
		//se genera el layout
		FRLayout clayout=new FRLayout(g);
		Layout<String, String> layout = clayout;
		layout.setSize(new Dimension(1148,867)); // sets the initial size of the space
		VisualizationViewer<String, String> cv = new VisualizationViewer<String,String>(layout);
		cv.setPreferredSize(new Dimension(1148,867)); //Sets the viewing area size
		 
		cv.getRenderContext().setVertexLabelTransformer(new ToStringLabeller());
		cv.getRenderer().getVertexLabelRenderer().setPosition(Position.CNTR);
		
		//se crea el selector de vertices
		final PickedState<String> pickedState = cv.getPickedVertexState();
		
		pickedState.addItemListener(new ItemListener() {
			 
		    public void itemStateChanged(ItemEvent e) {
		    Object subject = e.getItem();
		        // The graph uses Integers for vertices.
		        if (subject instanceof String) {
		            String vertex = (String) subject;
		            if (pickedState.isPicked(vertex)) {
		                //System.out.println("Vertex " + vertex + " is now selected");
		            	
		            } else {
		                //System.out.println("Vertex " + vertex+ " no longer selected");
		            	try {
							generateDependenciesGraph();
						} catch (IOException e1) {
							e1.printStackTrace();
						}
		            }
		        }
		    }
		});

		// Set the mouse to "picking" mode so that vertices and edges can be selected.
		DefaultModalGraphMouse<String, String> modalMouse = new DefaultModalGraphMouse<String, String>();
		modalMouse.setMode(Mode.PICKING);
		cv.setGraphMouse(modalMouse);
		
		//System.out.println(tablePanel.isVisible());
		
		graphPanel = new JPanel();
		graphPanel.add(cv);
		graphPanel.setBackground(new Color(255, 255, 255));
		graphPanel.setBounds(0, 0, 1148, 867);
		graphFrame.add(graphPanel);
		graphFrame.add(tablePanel);
		
		try {
			//graphPanel.setVisible(true);
			dependenciesPanel.setVisible(false);
			graphFrame.remove(dependenciesPanel);
		}
		catch(java.lang.NullPointerException e){
			//e.printStackTrace();
		}
	}
	
	

}