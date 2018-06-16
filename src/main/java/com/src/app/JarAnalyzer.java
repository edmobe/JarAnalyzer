package com.src.app;

import java.io.File;
import java.util.SortedMap;
import java.util.SortedSet;

import org.jboss.tattletale.analyzers.JarScanner;
import org.jboss.tattletale.core.Archive;

public class JarAnalyzer {

	private Archive selectedJar;
	private SortedMap<String, SortedSet<String>> jarList;

	public JarAnalyzer() {

	}

	public void openJar(String dir) throws Exception {
		File jarFile = new File(dir);
		JarScanner jarScanner = new JarScanner();
		Archive archive = jarScanner.scan(jarFile);
		jarList = archive.getClassDependencies();
		System.out.println("DONE!");
	}

	/*
	 * public Jar getSelectedJar() { return selectedJar; }
	 * 
	 * public void setSelectedJar(Jar selectedJar) { this.selectedJar = selectedJar;
	 * }
	 * 
	 * public LinkedList<Jar> getJarList() { return JarList; }
	 * 
	 * public void setJarList(LinkedList<Jar> jarList) { JarList = jarList; }
	 */

}
