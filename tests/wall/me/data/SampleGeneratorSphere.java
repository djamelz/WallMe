package wall.me.data;

import java.io.PrintWriter;

/**
 * GŽnre un fichier de test sous forme de portion de sphre
 * !! ne pas utiliser (pas conforme au format attendu, l'ordre d'imbrication des boucles alpha et beta est inversŽ)
 * 
 * @author tvial
 *
 */
public class SampleGeneratorSphere {
	public static void main(String[] args) throws Exception {
		String fileName = "/tmp/sample.txt";
		if (args.length >= 1) {
			fileName = args[0];
		}
		
		System.out.println("Generating " + fileName);
		PrintWriter pw = new PrintWriter(fileName);
		
		for (int beta = 10; beta < 60; beta++) {
			for (int alpha = 0; alpha < 120; alpha++) {
				int distance = 300;
				pw.println(alpha + ";" + beta + ";" + distance);
			}
		}
		
		pw.close();
		System.out.println("Done");
	}
}
