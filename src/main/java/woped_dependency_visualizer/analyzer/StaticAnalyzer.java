package woped_dependency_visualizer.analyzer;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

import com.github.javaparser.StaticJavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.ImportDeclaration;

import woped_dependency_visualizer.utils.WopedProjectFolder;

public class StaticAnalyzer {

	public void analyze(ArrayList<WopedProjectFolder> projects) {
		try {
			CompilationUnit cu = StaticJavaParser.parse(new File("F:\\Daniel\\DHBW\\6.Semester\\Integrationsseminar\\001DEV\\woped-sf\\woped-code\\WoPeD-Starter\\src\\org\\woped\\starter\\RunWoPeD.java"));
			List<ImportDeclaration> imports = cu.findAll(ImportDeclaration.class);
			for(ImportDeclaration i : imports) {
				System.out.println("Import: " + i.getNameAsString());
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
}
