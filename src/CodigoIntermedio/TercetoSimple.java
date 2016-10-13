package CodigoIntermedio;

import AnalizadorLexico.Token;

public class TercetoSimple extends Terceto {
	Token t;
	
	public TercetoSimple(){
		
	}
	
	public TercetoSimple(Token t) {
		this.t=t;
	}

	public String imprimirTerceto() {
		if (t == null)
			return String.valueOf(numeroTerceto);  
		else
			return t.getNombre();
	}
}
