package step2;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.eclipse.jdt.core.JavaCore;
import org.eclipse.jdt.core.dom.AST;
import org.eclipse.jdt.core.dom.ASTParser;
import org.eclipse.jdt.core.dom.CompilationUnit;
import org.eclipse.jdt.core.dom.MethodDeclaration;
import org.eclipse.jdt.core.dom.MethodInvocation;

import org.jgrapht.Graph;
import org.jgrapht.graph.DefaultDirectedGraph;
import org.jgrapht.graph.DefaultEdge;

public class Parser {
	
	public static final String projectPath = "C:\\Users\\Hp\\OneDrive\\Bureau\\ProjectsToParse\\EnseignantEtudiant";
	public static final String projectSourcePath = projectPath + "\\src";
	public static final String jrePath = "C:\\Program Files\\Java\\jre1.8.0_51\\lib\\rt.jar";
	private static Graph<String, DefaultEdge> callGraph = new DefaultDirectedGraph<>(DefaultEdge.class);
	
	public static void main(String[] args) throws IOException {

		// read java files
		final File folder = new File(projectSourcePath);
		ArrayList<File> javaFiles = listJavaFilesForFolder(folder);

		// for each file mean for each class
		for (File fileEntry : javaFiles) {
			String content = FileUtils.readFileToString(fileEntry);

			CompilationUnit parse = parse(content.toCharArray());

			buildCallGraph(parse);
		}
		// we will get: [nodes list, relations between nodes] in console
		printCallGraph();
		// here to show a window of call graph
		GraphVisualizer.displayGraph(callGraph);
	}

	// read all java files from specific folder
	public static ArrayList<File> listJavaFilesForFolder(final File folder) {
		ArrayList<File> javaFiles = new ArrayList<File>();
		for (File fileEntry : folder.listFiles()) {
			if (fileEntry.isDirectory()) {
				javaFiles.addAll(listJavaFilesForFolder(fileEntry));
			} else if (fileEntry.getName().contains(".java")) {
				// System.out.println(fileEntry.getName());
				javaFiles.add(fileEntry);
			}
		}

		return javaFiles;
	}

	// create AST
	private static CompilationUnit parse(char[] classSource) {
		ASTParser parser = ASTParser.newParser(AST.JLS4); // java +1.6
		parser.setResolveBindings(true);
		parser.setKind(ASTParser.K_COMPILATION_UNIT);
 
		parser.setBindingsRecovery(true);
 
		Map options = JavaCore.getOptions();
		parser.setCompilerOptions(options);
 
		parser.setUnitName("");
 
		String[] sources = { projectSourcePath }; 
		String[] classpath = {jrePath};
 
		parser.setEnvironment(classpath, sources, new String[] { "UTF-8"}, true);
		parser.setSource(classSource);
		
		return (CompilationUnit) parser.createAST(null); // create and parse
	}

	public static void buildCallGraph(CompilationUnit parse) {
	    MethodDeclarationVisitor visitor1 = new MethodDeclarationVisitor();
	    parse.accept(visitor1);
	    for (MethodDeclaration method : visitor1.getMethods()) {
	        MethodInvocationVisitor visitor2 = new MethodInvocationVisitor();
	        method.accept(visitor2);
	        for (MethodInvocation methodInvocation : visitor2.getMethods()) {
	            String caller = method.getName().toString();
	            String callee = methodInvocation.getName().toString();
	            callGraph.addVertex(caller);
	            callGraph.addVertex(callee);
	            callGraph.addEdge(caller, callee);
	        }
	    }
	}
	public static void printCallGraph() {
        System.out.println(callGraph.toString());
    }
}