package com.app;

import java.awt.EventQueue;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class App {

	private JFrame frame;
	private JarAnalyzer analyzer;
	private final JLabel lblEstoyTrabajandoPara = new JLabel("Estoy trabajando para tener la interfaz lista lo antes posible. Atte: Ed :)");

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					App window = new App();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public App() {
		analyzer = new JarAnalyzer();
		try {
			analyzer.openJar("Jars/google-gson-stream-2.2.1.jar.zip");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 1000, 600);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		lblEstoyTrabajandoPara.setHorizontalAlignment(SwingConstants.LEFT);
		panel.add(lblEstoyTrabajandoPara);
	}

}
