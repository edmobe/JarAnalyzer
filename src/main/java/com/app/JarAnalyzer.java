package com.app;

import java.io.IOException;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.src.jars.Jar;
import com.sun.org.apache.bcel.internal.classfile.ClassParser;
import com.sun.org.apache.bcel.internal.classfile.JavaClass;

public class JarAnalyzer {
	
	private Jar selectedJar;
	private LinkedList<Jar> JarList;
	
	public JarAnalyzer() {
		
	}
	
	public void openJar(String jarFile) throws IOException {
		JarFile jar = new JarFile(jarFile);
		System.out.println(jar);
	}

	public Jar getSelectedJar() {
		return selectedJar;
	}

	public void setSelectedJar(Jar selectedJar) {
		this.selectedJar = selectedJar;
	}

	public LinkedList<Jar> getJarList() {
		return JarList;
	}

	public void setJarList(LinkedList<Jar> jarList) {
		JarList = jarList;
	}
	

}
