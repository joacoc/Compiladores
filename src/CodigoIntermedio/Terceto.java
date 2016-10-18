package CodigoIntermedio;

import java.util.ArrayList;

public class Terceto {
	
	private ArrayList<TercetoSimple> elementos;
	private int numeroTerceto;
	
	public Terceto(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		elementos = new ArrayList<TercetoSimple>();
		elementos.add(izq);
		elementos.add(medio);
		elementos.add(der);
		this.numeroTerceto = numeroTerceto;
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
