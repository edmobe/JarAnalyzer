package com.src.app;

import java.io.File;
import java.util.SortedMap;
import java.util.SortedSet;

import org.jboss.tattletale.analyzers.JarScanner;
import org.jboss.tattletale.core.Archive;

public class JarAnalyzer {

	private Archive selectedJar;
	private SortedMap<String, SortedSet<String>> jarList;
	private String jarManifest = null;

	public JarAnalyzer() {

	}

	public void openJar(String dir) throws Exception {
		File jarFile = new File(dir);
		JarScanner jarScanner = new JarScanner();
		Archive archive = jarScanner.scan(jarFile);
		jarList = archive.getClassDependencies();
		if(archive.hasManifestKey("Class-Path")) {
			jarManifest = archive.getManifestValue("Class-Path");
		}
		//System.out.println(jarList.toString());
		//System.out.println(jarList.keySet());
//		System.out.println(jarManifest);
//		System.out.println(archive.hasManifestKey("Class-Path"));
//		for ( String key : jarList.keySet() ) {
//		    System.out.println( key );
//		    System.out.println( jarList.get(key));
//		}
		System.out.println("DONE!");
	}

	public String getJarManifest() {return jarManifest;}

	public void setJarManifest(String jarManifest) {this.jarManifest = jarManifest;}

	public Archive getSelectedJar() { return selectedJar; }
	  
	public void setSelectedJar(Archive selectedJar) { this.selectedJar = selectedJar;}
	 
	public SortedMap<String, SortedSet<String>> getJarList() { return jarList; }
	 
	public void setJarList(SortedMap<String, SortedSet<String>> jarList) { this.jarList = jarList; }
	 

}