package CodigoIntermedio;

import java.util.ArrayList;

public class Terceto {
	
	private ArrayList<TercetoSimple> elementos;
	protected int numeroTerceto;
	
	public Terceto() {
		elementos = new ArrayList<TercetoSimple>();
	}
	
	public String imprimirTerceto(){
		String terceto = numeroTerceto + "  (";
		for (int i = 0 ; i< elementos.size(); i++){
			terceto = terceto + elementos.get(i).imprimirTerceto();
			if (i != elementos.size()-1)
				terceto = terceto + ", ";
			else
				terceto = terceto + ")";
		}
		return terceto;
	}
}
