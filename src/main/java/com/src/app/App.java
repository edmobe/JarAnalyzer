package com.src.app;

import java.awt.EventQueue;
import java.awt.FileDialog;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;
import java.awt.Font;

public class App {

	private JFrame frame;
	private JarAnalyzer analyzer;

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
	 * 
	 * @throws Exception
	 */
	public App() {
		analyzer = new JarAnalyzer();
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.getContentPane().setBackground(new Color(95, 158, 160));
		frame.setBounds(100, 100, 1600, 1000);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(255, 255, 255));
		panel.setBounds(15, 61, 1548, 867);
		frame.getContentPane().add(panel);
		panel.setLayout(null);

		final JLabel lblNoJarsAdded = new JLabel("No Jars added yet :(");
		lblNoJarsAdded.setHorizontalAlignment(SwingConstants.CENTER);
		lblNoJarsAdded.setFont(new Font("Tahoma", Font.PLAIN, 30));
		lblNoJarsAdded.setBackground(Color.WHITE);
		lblNoJarsAdded.setBounds(15, 394, 1518, 64);
		panel.add(lblNoJarsAdded);
		
		final JButton btnZoomIn = new JButton("Zoom in");
		btnZoomIn.setBounds(175, 15, 115, 30);
		frame.getContentPane().add(btnZoomIn);
		btnZoomIn.setEnabled(false);
		btnZoomIn.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZoomIn.setBackground(Color.GRAY);
		btnZoomIn.setForeground(Color.WHITE);
		btnZoomIn.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		final JButton btnZoomOut = new JButton("Zoom out");
		btnZoomOut.setBounds(305, 15, 128, 30);
		frame.getContentPane().add(btnZoomOut);
		btnZoomOut.setEnabled(false);
		btnZoomOut.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnZoomOut.setBackground(Color.GRAY);
		btnZoomOut.setForeground(Color.WHITE);
		btnZoomOut.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
			}
		});

		JButton btnNewButton = new JButton("Add new Jar");
		btnNewButton.setFont(new Font("Tahoma", Font.PLAIN, 20));
		btnNewButton.setBackground(Color.GRAY);
		btnNewButton.setForeground(Color.WHITE);
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				FileDialog fd = new FileDialog(frame, "Choose a file", FileDialog.LOAD);
				fd.setDirectory(System.getProperty("user.dir"));
				fd.setFile("*.jar");
				fd.setVisible(true);
				String filename = fd.getFile();
				if (filename == null)
					System.out.println("User cancelled the choice");
				else {
					try {
						analyzer.openJar(fd.getDirectory() + filename);
						lblNoJarsAdded.setText("Opened: " + filename);
						btnZoomIn.setEnabled(true);
						btnZoomOut.setEnabled(true);
					} catch (Exception e1) {
						e1.printStackTrace();
					}
				}

			}
		});
		btnNewButton.setBounds(15, 15, 145, 30);
		frame.getContentPane().add(btnNewButton);
	}

}
