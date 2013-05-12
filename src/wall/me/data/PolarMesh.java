package wall.me.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Maillage de points en coord polaires
 * @author tvial
 *
 */
public class PolarMesh {
	// Poids pour le lissage
	// W1 : point courant, W2 : points au-dessus et sur les côtés, W3 : points en diagonale
	// La normalisation est faite implicitement (pas besoin d'avoir W1 + W2 + W3 == 1)
	private static final double W1 = 3.0;
	private static final double W2 = 2.0;
	private static final double W3 = 1.0;
	
	// Longueur des arcs beta (en nb d'échantillons) - constante sur le mesh
	private int range;
	
	// "Colonnes" (une colonne par valeur de alpha)
	private List<PolarSample[]> columns;
	
	// Colonne en cours de remplissage
	private int currentFilling;
	private PolarSample[] currentCol;
	
	public PolarMesh(int range) {
		this.range = range;
		columns = new ArrayList<PolarSample[]>();
		currentCol = null;
	}
	
	/**
	 * Ajoute un échantillon au mesh, i.e. à la colonne en cours (et démarre une nouvelle colonne si nécessaire)
	 * @param sample
	 */
	public void add(PolarSample sample) {
		if (currentCol == null) {
			currentCol = new PolarSample[range];
			columns.add(currentCol);
			currentFilling = 0;
		}
		
		currentCol[currentFilling++] = sample;
		if (currentFilling >= range) {
			// Une colonne sur 2 est inversée (0..180 -> 180..0)
			// Dans ce cas-là on la retourne car on a besoin de raccrocher les bandes par les échantillons voisins (repérés par indices)
			if (currentCol[0].beta > currentCol[1].beta) {
				for (int i = 0; i < range / 2; i++) {
					PolarSample t = currentCol[i];
					currentCol[i] = currentCol[range - 1 - i];
					currentCol[range - 1 - i] = t;
				}
			}
			currentCol = null;
		}
	}
	
	public List<PolarSample[]> getColumns() {
		return columns;
	}
	
	public PolarMesh smooth() {
		PolarMesh smoothed = new PolarMesh(range);
		for (int i = 0; i < columns.size(); i++) {
			PolarSample[] column = columns.get(i);
			PolarSample[] smoothedCol = new PolarSample[range];
			for (int j = 0; j < column.length; j++) {
				PolarSample ps;
				if (i == 0 || i == columns.size() - 1 || j == 0 || j == column.length - 1) {
					ps = column[j];
				}
				else {
					PolarSample[] col1 = columns.get(i - 1);
					PolarSample[] col2 = columns.get(i + 1);
					double alpha = column[j].alpha;
					double beta = column[j].beta;
					double distance =
							(W1 * column[j].distance +
							W2 * column[j - 1].distance +
							W2 * column[j + 1].distance +
							W2 * col1[j].distance +
							W2 * col2[j].distance +
							W3 * col1[j - 1].distance +
							W3 * col1[j + 1].distance +
							W3 * col2[j - 1].distance +
							W3 * col2[j + 1].distance) / (W1 + 4.0 * W2 + 4.0 * W3);
							
					ps = new PolarSample(alpha, beta, distance);
				}
				smoothedCol[j] = ps;
			}
			smoothed.columns.add(smoothedCol);
		}
		
		return smoothed;
	}
	
	/**
	 * Nb d'échantillons total
	 * @return
	 */
	public int getNPoints() {
		return range * columns.size();
	}
}
