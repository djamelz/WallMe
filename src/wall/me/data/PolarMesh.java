package wall.me.data;

import java.util.ArrayList;
import java.util.List;

/**
 * Maillage de points en coord polaires
 * @author tvial
 *
 */
public class PolarMesh {
	// Longueur des arcs beta (en nb d'Žchantillons) - constante sur le mesh
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
	 * Ajoute un Žchantillon au mesh, i.e. ˆ la colonne en cours (et dŽmarre une nouvelle colonne si nŽcessaire)
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
			// Une colonne sur 2 est inversŽe (0..180 -> 180..0)
			// Dans ce cas-lˆ on la retourne car on a besoin de raccrocher les bandes par les Žchantillons voisins (repŽrŽs par indices)
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
	 * Nb d'Žchantillons total
	 * @return
	 */
	public int getNPoints() {
		return range * columns.size();
	}
}
