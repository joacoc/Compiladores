package AnalizadorLexico;

import java.util.ArrayList;
import java.util.Hashtable;



public class TablaSimbolos {
	
	public static final String ochoBits = "dw";
	public static final String tipoPrint = "db";
	public static final String dieciseisBits = "dd";
	public int cantPrints = 0;

	//La tabla de simbolos se almacena en una hash
	//*
	//*
	//*		Check. YACC? 
	//*
	//*
	private Hashtable<String, Token> tSimb;

	ArrayList<Token> prints = new ArrayList<Token>();

	
	//Se almacenan los usos de cada token, p.e. indicando el uso del identificador en el programa (variable,
	//nombre de función, nombre de parámetro, nombre de arreglo, nombre de programa, etc.).
	private Hashtable<String, String> tUsos;
	
	public TablaSimbolos(){
		tSimb = new Hashtable<>();
		tUsos = new Hashtable<>();
	}
	
	public void addSimbolo( Token t){
		tSimb.put(t.getNombre(), t);
	}
	
	public void addUso(String var, String uso){
		tUsos.put(var, uso);
	};
	

	public String getUso(String var){
		if (tUsos.contains(var))
			return tUsos.get(var);
		else 
			return "";
	};
	
	//Me confirma si el token es agregable a la tabla de simbolos.
	public boolean es_Agregable( Token t){
		if( //t.getUso() == AnalizadorLexico.ID || comento el identificador porque lo agregamos en la gramatica
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
	
	public Token getToken(String nombre){
		if (tSimb.containsKey(nombre))
			return tSimb.get(nombre);
		else
			return null;
	}

	public void borrarSimbolo(String nombre) {
		if (tSimb.remove(nombre)!= null)
			System.out.println("anda" );
	};
	
	public String getAssembler (){
		ArrayList<Token> tokens = getTokens();
		String assembler = "";
		for (Token t: tokens){
			if  ( (t.getUso() != AnalizadorLexico.CTEI) && (t.getUso() != AnalizadorLexico.CTEL) ){
				String tipoAssembler = getTipoAssember(t);
				
				//Si es un comentario es distinto ya que se hace print1 + el assembler xq
				// t.getNombre() retornaria "*comentario*" entre comillas y generaria un error en el ASM
				if(t.getUso() != AnalizadorLexico.MULTI_LINEA)
//					assembler = assembler + tipoAssembler + '\n';
//				else
					assembler = assembler + t.getNombre()+ " " + tipoAssembler + '\n';
			}
		}
		return assembler;
	}

	public String getTipoAssember(Token t) {
		String tipo = "";
		AnalizadorLexico analizador = new AnalizadorLexico(null, null); //es para usar las constantes
		
		if ( t.getTipo() == analizador.variableI )
			tipo = ochoBits;
		else
			if ( t.getTipo() == analizador.variableL )
				tipo = dieciseisBits;	
			else
				if( t.getUso() == analizador.MULTI_LINEA){
					//Se lleva una cuenta de la posicion del print para luego 
					//coordinar con los tercetos la posicion.
					tipo = "print" +String.valueOf(prints.indexOf(t)+1) +" " +tipoPrint +" " +t.nombre  +"," ;
				}
		return tipo + " 0";//inicializo en cero todas las variables
	}

	public ArrayList<Token> getPrints() {
		ArrayList<Token> tokens =getTokens();
		for (Token t: tokens){
			if ( ( t.getUso() == AnalizadorLexico.MULTI_LINEA) && (!estaPrint(t) ) )
				prints.add(t);
		}
		return prints;
	}
	
	private boolean estaPrint(Token token){
		for (Token t:prints)
			if ( t.getNombre() == token.getNombre() )
				return true;
		return false;
	}
	
}