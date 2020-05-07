package woped_dependency_visualizer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WopedProjectFolder {
	private List<WopedProjectFolder> dependenciesIntern;
	private List<String> dependenciesExtern;
	private String name;
	private File file;
	
	public WopedProjectFolder(File file) {
		dependenciesIntern = new ArrayList<WopedProjectFolder>();
		dependenciesExtern = new ArrayList<String>();
		this.name = file.getName();
		this.file = file;
	}
	
	public File getFile() {
		return this.file;
	}

	public List<WopedProjectFolder> getDependenciesIntern() {
		return dependenciesIntern;
	}

	public void addDependenciesIntern(WopedProjectFolder dependencyIntern) {
		this.dependenciesIntern.add(dependencyIntern);
	}

	public List<String> getDependenciesExtern() {
		return dependenciesExtern;
	}

	public void addDependenciesExtern(String dependencyExtern ) {
		this.dependenciesExtern.add(dependencyExtern);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public String toString() {
		return "WopedProjectFolder: " + getName();
	}
}
