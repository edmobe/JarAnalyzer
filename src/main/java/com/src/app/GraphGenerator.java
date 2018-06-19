package com.src.app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.util.SortedMap;
import java.util.SortedSet;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTable;

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
	private JFrame graphFrame;
	private String center;
	private String dependencies;
	private JPanel graphPanel = new JPanel();
	private JPanel dependenciesPanel = new JPanel();

	public GraphGenerator(String filename, JFrame toFrame, SortedMap<String, SortedSet<String>> mapped, String depend) {
		this.toMap = mapped;
		this.graphFrame = toFrame;
		this.center = filename;
		this.dependencies = depend;
		try {
			graphFrame.getContentPane().add(dependenciesPanel);
			graphFrame.getContentPane().add(graphPanel);
		} catch(java.lang.NullPointerException e){
			e.printStackTrace();
		}
	}
	
	public void generateDependenciesGraph() throws IOException {
		if (dependencies == null) {
			Graph<String,String> g = new DirectedSparseMultigraph<String, String>();
			g.addVertex(center);
			
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
			dependenciesPanel.setBounds(15, 61, 1148, 867);
			graphFrame.getContentPane().add(dependenciesPanel);
			try {
				//dependenciesPanel.setVisible(true);
				graphPanel.setVisible(false);
				graphFrame.getContentPane().remove(graphPanel);
			}
			catch(java.lang.NullPointerException e){
				//e.printStackTrace();
			}
			
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
		
		
		String[] columns = {"Clase","Grado saliente","Grado entrante"};
		Object[][] rows = new Object[length][3];
		int i = 0;
		for ( String key : this.toMap.keySet() ) {
			rows[i][0] = key;
			rows[i][1] = g.outDegree(key);
			rows[i][2] = g.inDegree(key);
			i++;
		}
		
		JPanel tablePanel = new JPanel();
		JTable table = new JTable(rows,columns);
		tablePanel.setPreferredSize(new Dimension(400,867));
		tablePanel.setBounds(1148, 61, 400, 867);
		tablePanel.setVisible(true);
		tablePanel.add(table.getTableHeader(), BorderLayout.PAGE_START);
		tablePanel.add(table, BorderLayout.CENTER);
		
		
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

		graphPanel = new JPanel();
		graphPanel.add(cv);
		graphPanel.setBackground(new Color(255, 255, 255));
		graphPanel.setBounds(15, 61, 1148, 867);
		graphFrame.getContentPane().add(graphPanel);
		graphFrame.getContentPane().add(tablePanel);
		try {
			//graphPanel.setVisible(true);
			dependenciesPanel.setVisible(false);
			graphFrame.getContentPane().remove(dependenciesPanel);
		}
		catch(java.lang.NullPointerException e){
			//e.printStackTrace();
		}
	}
	
	

}