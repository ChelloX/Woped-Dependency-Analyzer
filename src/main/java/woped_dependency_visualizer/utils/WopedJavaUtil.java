package woped_dependency_visualizer.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.log4j.Logger;

import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.data.WopedProjectPackage;

public class WopedJavaUtil {
	private static Logger LOGGER = Logger.getLogger(WopedJavaUtil.class);
	HashMap<String, WopedProjectFolder> javaClassProjectFolderMap;
	HashMap<WopedProjectFolder, ArrayList<String>> projectFolderJavaClassMap;

	public WopedJavaUtil(List<WopedProjectFolder> wopedProjects) {
		javaClassProjectFolderMap = new HashMap<String, WopedProjectFolder>();
		projectFolderJavaClassMap = new HashMap<WopedProjectFolder, ArrayList<String>>();

		for (WopedProjectFolder wpf : wopedProjects) {
			for (WopedProjectPackage wpp : wpf.getProjectPackages()) {
				buildJavaClassStructure(wpf, wpp);
			}
		}
	}

	public WopedProjectFolder getProjectFolderForClass(String className) {
		return javaClassProjectFolderMap.get(className);
	}
	
	public ArrayList<String> getClassesForFolder(WopedProjectFolder projectFolder) {
		return projectFolderJavaClassMap.get(projectFolder);
	}

	private void buildJavaClassStructure(WopedProjectFolder wopedProjectFolder, WopedProjectPackage projectPackage) {
		for (File javaFile : projectPackage.getJavaFiles()) {
			String classPath = buildClassPath(projectPackage, javaFile);
			javaClassProjectFolderMap.put(classPath, wopedProjectFolder);

			ArrayList<String> classList = projectFolderJavaClassMap.get(wopedProjectFolder);
			if (classList != null) {
				classList.add(classPath);
			} else {
				classList = new ArrayList<String>();
				classList.add(classPath);
			}
			projectFolderJavaClassMap.put(wopedProjectFolder, classList);
		}

		for (WopedProjectPackage p : projectPackage.getSubPackage()) {
			buildJavaClassStructure(wopedProjectFolder, p);
		}
	}

	private String buildClassPath(WopedProjectPackage projectPackage, File javaFile) {
		String path = "";
		WopedProjectPackage temp = projectPackage;
		LOGGER.debug("Build classpath for " + javaFile.getName());
		do {
			if (!path.equals("")) {
				path = temp.getName() + "." + path;
			} else {
				path = temp.getName();
			}
			temp = temp.getParentPackage();
		} while (temp != null);
		path = path + "." + javaFile.getName().replace(".java", "");

		return path;
	}

}
