package CodigoIntermedio;

import AnalizadorLexico.Token;

public class TercetoSimple {
	Token t;
	int referenciaTerceto; //tiene el numero de terceto por si es compuesto
	
	public TercetoSimple(Token t) {
		this.t=t;
	}

	public TercetoSimple(int rt){
		referenciaTerceto = rt;
	}
	
	public String imprimirTerceto() {
		if (t == null)
			return String.valueOf(referenciaTerceto);  
		else
			return t.getNombre();
	}
}
