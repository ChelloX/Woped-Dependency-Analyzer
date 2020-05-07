package woped_dependency_visualizer.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WopedProjectPackage {
	private File folder;
	private WopedProjectPackage parentPackage;
	private List<WopedProjectPackage> subPackage;
	private List<File> javaFiles;
	private List<File> nonJavaFiles;
	private String name;
	
	public WopedProjectPackage(File folder, String name) {
		javaFiles = new ArrayList<File>();
		nonJavaFiles = new ArrayList<File>();
		subPackage = new ArrayList<WopedProjectPackage>();
		this.folder = folder;
		this.name = name;
	}
	
	public String toString() {
		return  getName();
	}

	public File getFolder() {
		return folder;
	}

	public void setFolder(File folder) {
		this.folder = folder;
	}

	public List<WopedProjectPackage> getSubPackage() {
		return subPackage;
	}

	public void addSubPackage(WopedProjectPackage subPackage) {
		this.subPackage.add(subPackage);
	}

	public List<File> getJavaFiles() {
		return javaFiles;
	}

	public void addJavaFile(File javaFile) {
		this.javaFiles.add(javaFile);
	}

	public List<File> getNonJavaFiles() {
		return nonJavaFiles;
	}

	public void addNonJavaFiles(File nonJavaFile) {
		this.nonJavaFiles.add(nonJavaFile);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public WopedProjectPackage getParentPackage() {
		return parentPackage;
	}

	public void setParentPackage(WopedProjectPackage parentPackage) {
		this.parentPackage = parentPackage;
	}
}
