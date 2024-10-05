package test;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {
		Enseignant[] tabEnseignant = new Enseignant[2];// une declaration des deux tableaux au lieu de un seul
		Etudiant[] tabEtudiant = new Etudiant[3];

		for (int i = 0; i < 2; i++) {// le remplissage du premier tableau d'apres l'utilisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrer le nom ens: ");
			String nom = scanner.nextLine();
			System.out.print("Enter le prenom: ");
			String prenom = scanner.nextLine();
			System.out.print("Enter l'age': ");
			int age = scanner.nextInt();
			System.out.print("Enter num_sec_soc: ");
			long num_sec_soc = scanner.nextLong();
			System.out.print("Enter modules: ");
			String[] modules = new String[3];
			for (int j = 0; j < 3; j++) {
				modules[j] = scanner.next();
			}
			tabEnseignant[i] = new Enseignant(nom, prenom, age, num_sec_soc, modules);
		}
		for (int i = 0; i < 3; i++) {// le remplissage du deusieme tableau d'apres l'utilisateur
			Scanner scanner = new Scanner(System.in);
			System.out.print("Entrer le nom etu: ");
			String nom = scanner.nextLine();
			System.out.print("Enter le prenom: ");
			String prenom = scanner.nextLine();
			System.out.print("Enter l'age': ");
			int age = scanner.nextInt();
			System.out.print("Enter matricule: ");
			int matricule = scanner.nextInt();
			System.out.print("Enter notes: ");
			double[] notes = new double[3];
			for (int j = 0; j < 3; j++) {
				notes[j] = scanner.nextDouble();
			}
			tabEtudiant[i] = new Etudiant(nom, prenom, age, matricule, notes);
		}
		
		for (int i = 0; i < 2; i++) {// la boucle de l'affichage du premier tableau
			tabEnseignant[i].afficher();
		}
		for (int i = 0; i < 3; i++) {// la boucle de l'affichage du deusieme tableau
			tabEtudiant[i].afficher();
		}
		
		tabEnseignant[0].compareTo(); // car c'est un tableau static
		tabEtudiant[0].compareTo(); // car c'est un tableau static

	}
}