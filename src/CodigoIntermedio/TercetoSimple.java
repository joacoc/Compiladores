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
	
	public boolean esToken(){
		char c = t.getNombre().charAt(0);
		return (!Character.isDigit(c));
	}
	
	public int getNumeroTerceto(){
		return ( Integer.parseInt( t.getNombre() ) );
		
	}
}
