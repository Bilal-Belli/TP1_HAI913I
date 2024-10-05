package step1;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class MetricsCalculator {

    public static void calculateMetrics(ArrayList<ClassMetrics> classMetricsList, int totalPackages) {
    	// taille du vecteur = nombre total de classes
        int totalClasses = classMetricsList.size();
        // la somme du nombre de méthodes de tous les classes
        int totalMethods = classMetricsList.stream().mapToInt(c -> c.methodCount).sum();
        // somme total des attributs dans chaque classe
        int totalAttributes = classMetricsList.stream().mapToInt(c -> c.attributeCount).sum();
        // somme des lignes de chaque classe
        int totalLinesOfCode = classMetricsList.stream().mapToInt(c -> c.lineCount).sum();

        // calcul du nombre moyene de méthodes par classe (reel)
        double avgMethodsPerClass = (double) totalMethods / totalClasses;
        // calcul du nombre moyene de lignes par methode (reel)
        double avgLinesPerMethod = (double) totalLinesOfCode / totalMethods;
        // calcul du nombre moyen de attributs par class (reel)
        double avgAttributesPerClass = (double) totalAttributes / totalClasses;

        System.out.println("Total Classes: " + totalClasses);
        System.out.println("Total Lines: " + totalLinesOfCode);
        System.out.println("Total Methods: " + totalMethods);
        System.out.println("Total Packages: " + totalPackages);
        System.out.println("Average Methods per Class: " + avgMethodsPerClass);
        System.out.println("Average Lines of Code per Method: " + avgLinesPerMethod);
        System.out.println("Average Attributes per Class: " + avgAttributesPerClass);      
        // Top 10% classes by method count
        classMetricsList.sort(Comparator.comparingInt(c -> c.methodCount));
        ArrayList<ClassMetrics> top10PercentByMethods = new ArrayList<>(classMetricsList.subList((int) (0.9 * totalClasses), totalClasses));
        ArrayList<String> top10PercentByMethodsNames = new ArrayList<>();
        for (ClassMetrics cm : top10PercentByMethods) {
        	top10PercentByMethodsNames.add(cm.className);
        }
        System.out.println("Top 10% classes by method count: " + top10PercentByMethodsNames);
        // Top 10% classes by attribute count
        classMetricsList.sort(Comparator.comparingInt(c -> c.attributeCount));
        ArrayList<ClassMetrics> top10PercentByAttributes = new ArrayList<>(classMetricsList.subList((int) (0.9 * totalClasses), totalClasses));
        ArrayList<String> top10PercentByAttributesNames = new ArrayList<>();
        for (ClassMetrics cm : top10PercentByAttributes) {
            top10PercentByAttributesNames.add(cm.className);
        }
        System.out.println("Top 10% classes by attribute count: " + top10PercentByAttributesNames);
        // Find intersection of the two
        ArrayList<String> intersection = new ArrayList<>(top10PercentByMethodsNames);
        intersection.retainAll(top10PercentByAttributesNames);
        System.out.println("Find intersection of the two: " + intersection);
        // Classes with more than X methods
        int X = 2; // Can be parameterized
        //ArrayList<ClassMetrics> classesWithMoreThanXMethods = new ArrayList<>();
        ArrayList<String> classesWithMoreThanXMethods = new ArrayList<>();
        for (ClassMetrics cm : classMetricsList) {
            if (cm.methodCount > X) {
                classesWithMoreThanXMethods.add(cm.className);
            }
        }
        System.out.println("Classes with more than 2 methods: " + classesWithMoreThanXMethods);
        // Top 10% of methods with the highest number of lines of code per class
        for (ClassMetrics classMetrics : classMetricsList) {
            List<MethodMetrics> sortedMethods = classMetrics.methods.stream()
                .sorted(Comparator.comparingInt(m -> -m.lineCount)) // Sort in descending order
                .collect(Collectors.toList());
            int top10PercentCount = (int) Math.ceil(0.1 * sortedMethods.size());
            List<MethodMetrics> top10PercentMethods = sortedMethods.subList(0, top10PercentCount);
            System.out.println("Top 10% of methods with the highest number of lines of code for Class ["+classMetrics.className+"] :");
            for (MethodMetrics mm : top10PercentMethods) {
                System.out.println("Method: " + mm.methodName + ", Lines of Code: " + mm.lineCount);
            }
        }
        // Max parameters in any method
        int maxParameters = classMetricsList.stream()
                .flatMap(c -> c.methods.stream())
                .mapToInt(m -> m.parameterCount)
                .max()
                .orElse(0);
        System.out.println("Max Parameters in any method: " + maxParameters);
    }
}