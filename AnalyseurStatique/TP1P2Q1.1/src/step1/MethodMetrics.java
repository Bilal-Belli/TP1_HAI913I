package step1;

public class MethodMetrics {
	public String methodName;
    public int lineCount;
    public int parameterCount;

    public MethodMetrics(String methodName, int lineCount, int parameterCount) {
        this.methodName = methodName;
        this.lineCount = lineCount;
        this.parameterCount = parameterCount;
    }
}
