package woped_dependency_visualizer.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class WopedProjectFolder {
	private List<WopedProjectFolderDependency> dependenciesIntern;
	private List<String> dependenciesExtern;
	private String name;
	private File folder;
	private List<WopedProjectPackage> projectPackages;

	public WopedProjectFolder(File folder) {
		dependenciesIntern = new ArrayList<WopedProjectFolderDependency>();
		dependenciesExtern = new ArrayList<String>();
		projectPackages = new ArrayList<WopedProjectPackage>();
		this.name = folder.getName();
		this.folder = folder;
	}

	public WopedProjectFolderDependency getDependencyToProjectFolder(WopedProjectFolder wpf) {
		for (WopedProjectFolderDependency dep : dependenciesIntern) {
			if (dep.getDependency().equals(wpf)) {
				return dep;
			}
		}
		return null;
	}

	public File getFolder() {
		return this.folder;
	}

	public List<WopedProjectFolderDependency> getDependenciesIntern() {
		return dependenciesIntern;
	}

	public void addDependenciesIntern(WopedProjectFolderDependency dependencyIntern) {
		this.dependenciesIntern.add(dependencyIntern);
	}

	public List<String> getDependenciesExtern() {
		return dependenciesExtern;
	}

	public void addDependenciesExtern(String dependencyExtern) {
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

	public List<WopedProjectPackage> getProjectPackages() {
		return projectPackages;
	}

	public void addProjectPackage(WopedProjectPackage projectPackage) {
		this.projectPackages.add(projectPackage);
	}
}
