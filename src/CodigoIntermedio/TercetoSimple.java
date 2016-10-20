package CodigoIntermedio;

import AnalizadorLexico.Token;

public class TercetoSimple {
	Token t;
	
	public TercetoSimple(Token t) {
		this.t=(Token) t;
	}
	
	public String imprimirTerceto() {
		char c = t.getNombre().charAt(0);
		if (Character.isDigit(c))
			return "[" + t.getNombre() + "]";
		else
			return t.getNombre();
	}
}
