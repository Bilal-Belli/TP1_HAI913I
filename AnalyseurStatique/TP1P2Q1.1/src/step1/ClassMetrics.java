package step1;

import java.util.ArrayList;

public class ClassMetrics {
	public String className;
    public int methodCount;
    public int attributeCount;
    public int lineCount;
    public ArrayList<MethodMetrics> methods = new ArrayList<>();
    
    public ClassMetrics(String className) {
        this.className = className;
    }

    public void addMethod(MethodMetrics methodMetrics) {
        methods.add(methodMetrics);
        methodCount++;
        lineCount += methodMetrics.lineCount;
    }
}
