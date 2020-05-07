package woped_dependency_visualizer.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import woped_dependency_visualizer.data.WopedProjectPackage;
import woped_dependency_visualizer.utils.WopedJavaUtil;
import woped_dependency_visualizer.data.WopedProjectFolder;
import woped_dependency_visualizer.data.WopedProjectFolderDependency;

public class WopedStaticAnalyzer {
	private static Logger LOGGER = Logger.getLogger(WopedStaticAnalyzer.class);

	public void addDependencyCountToDependencies(ArrayList<WopedProjectFolder> projects) {
		for (WopedProjectFolder wpf : projects) {
			LOGGER.debug("Static analyze " + wpf.getName());
			File srcFolder = new File(wpf.getFolder() + "\\src");
			if (srcFolder.exists()) {
				LOGGER.debug("Build package structure for: " + wpf.getName());

				for (File firstSrcLevel : srcFolder.listFiles()) {
					if (firstSrcLevel.isDirectory()) {
						wpf.addProjectPackage(buildPackageAndFileStructure(firstSrcLevel, null));
					} else {
						LOGGER.debug("Found non-directory file in /src/ for " + wpf.getName());
					}
				}

			} else {
				LOGGER.debug("SrcFolder does not exits for: " + wpf.getName());
			}
		}

		WopedJavaUtil javaUtil = new WopedJavaUtil(projects);

		for (WopedProjectFolder wpf : projects) {
			if (javaUtil.getClassesForFolder(wpf) != null) {
				for (String classPath : javaUtil.getClassesForFolder(wpf)) {
					try {

						String sourcePath = wpf.getFolder().getAbsolutePath() + "\\src\\" + classPath.replace(".", "\\")
								+ ".java";

						CompilationUnit cu = StaticJavaParser.parse(new File(sourcePath));
						List<ImportDeclaration> imports = cu.findAll(ImportDeclaration.class);
						for (ImportDeclaration i : imports) {
							WopedProjectFolder importProjectFolder = javaUtil
									.getProjectFolderForClass(i.getNameAsString());

							if (importProjectFolder != null) {
								if (!importProjectFolder.equals(wpf)) {
									WopedProjectFolderDependency dependency = wpf
											.getDependencyToProjectFolder(importProjectFolder);

									if (dependency == null) {
										LOGGER.error("Found import but no dependency");
									} else {
										dependency.setCount(dependency.getCount() + 1);
										LOGGER.debug("Increment dependency count from " + wpf.getName() + " to "
												+ dependency.getDependency().getName() + " Now: "
												+ dependency.getCount());
									}
								}
							}
						}
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
				}
			}
		}
		printOnConsole(projects);
	}

	private void printOnConsole(ArrayList<WopedProjectFolder> projects) {
		for(WopedProjectFolder wpf : projects) {
			for(WopedProjectFolderDependency dep : wpf.getDependenciesIntern()) {
				LOGGER.info("Dependency from " + wpf.getName() + " to " + dep.getDependency().getName() + " with count: " + dep.getCount());
			}
		}
	}

	private WopedProjectPackage buildPackageAndFileStructure(File file, WopedProjectPackage parent) {
		WopedProjectPackage p = new WopedProjectPackage(file, file.getName());
		LOGGER.debug("Created package " + p.getName());
		if (parent != null) {
			LOGGER.debug("Set " + parent.getName() + " as parent for " + p.getName());
			p.setParentPackage(parent);
		}
		for (File child : file.listFiles()) {
			if (child.isFile()) {
				if (child.getName().endsWith(".java")) {
					LOGGER.debug("Add " + child.getName() + " as Java-File to " + p.getName());
					p.addJavaFile(child);
				} else {
					LOGGER.debug("Add " + child.getName() + " as non-Java-File to " + p.getName());
					p.addNonJavaFiles(child);
				}
			} else if (child.isDirectory()) {
				p.addSubPackage(buildPackageAndFileStructure(child, p));
			} else {
				LOGGER.debug("Found strange file/folder in " + file.getAbsolutePath());
			}
		}

		return p;
	}
}
