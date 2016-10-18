package CodigoIntermedio;

import java.util.ArrayList;

public class ControladorTercetos {
	
	private ArrayList<Terceto> tercetos ;
	
	public ControladorTercetos() {
		tercetos = new ArrayList<Terceto>();
	}
	
	public void imprimirTercetos() {
		for (Terceto t: tercetos )
			t.imprimirTerceto();
	}
	
	public void addTerceto(Terceto t){
		tercetos.add(t);
	}

}
