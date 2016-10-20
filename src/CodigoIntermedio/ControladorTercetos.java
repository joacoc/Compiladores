package CodigoIntermedio;

import java.util.ArrayList;

public class ControladorTercetos {
	
	private ArrayList<Terceto> tercetos ;
	
	public ControladorTercetos() {
		tercetos = new ArrayList<Terceto>();
	}
	
	public String imprimirTercetos() {
		String cadena="Tercetos: \n";
		for (Terceto t: tercetos )
			cadena= cadena + t.imprimirTerceto() + '\n';
		return cadena;
	}
	
	public void addTerceto(Terceto t){
		tercetos.add(t);
	}
	
	public int getProxNumero(){
		return tercetos.size()+1;
	}

}
