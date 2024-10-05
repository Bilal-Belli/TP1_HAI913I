package step1;

import org.eclipse.jdt.core.dom.*;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;

public class MetricsVisitor extends ASTVisitor {

    private ArrayList<ClassMetrics> classMetricsList = new ArrayList<>();
    private HashSet<String> packages = new HashSet<>();
    
    @Override
    public boolean visit(TypeDeclaration node) {
        ClassMetrics classMetrics = new ClassMetrics(node.getName().toString());

        // Visit fields (attributes)
        FieldDeclaration[] fields = node.getFields();
        classMetrics.attributeCount = fields.length;

        // Visit methods
        MethodDeclaration[] methods = node.getMethods();
        for (MethodDeclaration method : methods) {
            int methodLineCount = countLines(method);
            int parameterCount = method.parameters().size();

            MethodMetrics methodMetrics = new MethodMetrics(method.getName().toString(), methodLineCount, parameterCount);
            classMetrics.addMethod(methodMetrics);
        }

        classMetricsList.add(classMetrics);
        return super.visit(node);
    }
    @Override
    public boolean visit(PackageDeclaration node) {
        packages.add(node.getName().toString());
        return super.visit(node);
    }

    public int getPackageCount() {
        return packages.size();
    }

    public ArrayList<ClassMetrics> getClassMetricsList() {
        return classMetricsList;
    }

    private int countLines(ASTNode node) {
        return node.getLength(); // Or use a more precise method
    }
}