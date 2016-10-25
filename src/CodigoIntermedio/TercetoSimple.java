package CodigoIntermedio;

import java.io.ObjectInputStream.GetField;

import AnalizadorLexico.Token;

public class TercetoSimple {
	Token t;
	
	public TercetoSimple(Token t) {
		this.t= t;
	}
	
	public String imprimirTerceto() {
		char c = t.getNombre().charAt(0);
		if (Character.isDigit(c))
			return "[" + t.getNombre() + "]";
		else
			return t.getNombre();
	}
	
	public Token getToken() {
		return t;
	}
}
