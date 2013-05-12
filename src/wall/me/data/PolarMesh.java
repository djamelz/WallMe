package wall.me.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Maillage de points en coord polaires
 * @author tvial
 *
 */
public class PolarMesh {
	// Longueur des arcs beta (en nb d'�chantillons) - constante sur le mesh
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
	 * Ajoute un �chantillon au mesh, i.e. � la colonne en cours (et d�marre une nouvelle colonne si n�cessaire)
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
			// Une colonne sur 2 est invers�e (0..180 -> 180..0)
			// Dans ce cas-l� on la retourne car on a besoin de raccrocher les bandes par les �chantillons voisins (rep�r�s par indices)
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
	
	/**
	 * Nb d'�chantillons total
	 * @return
	 */
	public int getNPoints() {
		return range * columns.size();
	}
}
