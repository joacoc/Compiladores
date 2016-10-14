package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;


public class TablaSimbolos {
	//La tabla de simbolos se almacena en una hash
	//*
	//*
	//*		Check. YACC? 
	//*
	//*
	private Hashtable<String, Token> tSimb;
	public TablaSimbolos(){
		tSimb = new Hashtable<>();
	}
	
	public void addSimbolo( Token t){
		tSimb.put(t.getNombre(), t);
	}
	
	//Me confirma si el token es agregable a la tabla de simbolos.
	public boolean es_Agregable( Token t){
		if(t.getUso() == AnalizadorLexico.ID ||
				t.getUso() == AnalizadorLexico.CTEI ||
				t.getUso() == AnalizadorLexico.CTEL || 
				t.getUso() == AnalizadorLexico.MULTI_LINEA)
			return true;
		else
			return false;
	}
	
	public ArrayList<Token> getTokens(){
		return new ArrayList<>(tSimb.values());
	}
	
	public boolean existe(String nombre){
		return tSimb.containsKey(nombre);
	}
}