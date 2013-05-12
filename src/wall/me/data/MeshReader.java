package wall.me.data;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;

import wall.me.utils.Angles;

/**
 * Lecture d'un fichier de capture
 * Format : liste de triplets alpha;beta;distance
 * - alpha = longitude (0..180)
 * - beta = latitude (0..180, 90 étant la verticale)
 * - distance = mesure en cm sur les coordonnées (alpha, beta)
 * 
 * Les angles sont convertis en radians à la lecture
 * L'ordre des triplets est important : alpha en boucle extérieure, beta en boucle intérieure (ascendant ou descendant)
 * Le nb de paires beta;distance doit être le même pour tous les alpha (cf. getAlphaRange())
 * 
 * @author tvial
 *
 */
public class MeshReader {
	private int alphaRange = -1;
	private BufferedReader reader;

	// Raw string or @fileName
	private String source;
	
	public MeshReader(String source) throws Exception {
		this.source = source;
		open();
	}
	
	private void open() throws Exception {
		if (source.charAt(0) == '@') {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(source.substring(1))));
		}
		else {
			reader = new BufferedReader(new StringReader(source));
		}
	}
	
	private void reopen() throws Exception {
		reader.close();
		open();
	}
	
	/**
	 * Calcule la taille des échantillons
	 * @return
	 * @throws Exception
	 */
	public int getAlphaRange() throws Exception {
		
		if (alphaRange == -1) {
			double alpha = nextSample().alpha;
			double oldAlpha;
			alphaRange = 0;

			do {
				oldAlpha = alpha;
				alpha = nextSample().alpha;
				alphaRange++;
			} while (alpha == oldAlpha);
			reopen();
		}
		
		return alphaRange;
	}
	
	/**
	 * Ramène l'échantillon suivant
	 * @return
	 * @throws Exception
	 */
	public PolarSample nextSample() throws Exception {
		String line = reader.readLine();
		if (line == null) {
			return null;
		}
		
		String[] fields = line.split(";");
		
		// Pour captureX, X < 5 (manquent des indices)
//		if ("180".equals(fields[1]) || "0".equals(fields[1])) {
//			line = reader.readLine();
//			if (line == null) {
//				return null;
//			}
//			fields = line.split(";");
//		}
		
		// Pour capture5 (dernière colonne incomplète)
//		if ("174".equals(fields[0])) {
//			return null;
//		}
		
		if (fields.length != 3) {
			return null;
		}
		
		return new PolarSample(Angles.rad(Double.parseDouble(fields[0])), Angles.rad(Double.parseDouble(fields[1])), Double.parseDouble(fields[2]));
	}
	
	public void dispose() throws Exception {
		reader.close();
	}

}
