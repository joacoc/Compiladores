package CodigoIntermedio;

import java.util.ArrayList;
import java.util.Vector;

import AnalizadorLexico.Token;

public class ControladorTercetos {
	
	public static final String BF = "BF";
	public static final String BI = "BI";
	
	private ArrayList<Terceto> tercetos ;
	private ArrayList<Integer> pila ;
	
	public ControladorTercetos() {
		tercetos = new ArrayList<Terceto>();
		pila = new ArrayList<Integer>();

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
	
	public String numeroTercetoString(){
		return String.valueOf(tercetos.size());
	}
	
	public void apilar(){
		pila.add(new Integer(tercetos.size()-1) );
	}
	
	public void desapilar(){
		int tercetoMod = pila.get(pila.size()-1);
		pila.remove(pila.size()-1);
		Terceto nuevo = tercetos.get(tercetoMod);
		TercetoSimple add = new TercetoSimple(new Token( String.valueOf(tercetos.size()+1) ) );
		if (nuevo.getTerceto(1) == null){System.out.println("entra aca");
			nuevo.setElemento(1, add);}
		else
			nuevo.setElemento(2, add);
		tercetos.set(tercetoMod, nuevo);
	}
	
	public void apilarFor(){
		pila.add(new Integer(tercetos.size()+1) );
	}
	
	public void desapilarFor(){
		Terceto nuevo = tercetos.get(tercetos.size()-1);
		TercetoSimple add = new TercetoSimple(new Token( String.valueOf(pila.remove(pila.size()-1)) ) );
		nuevo.setElemento(1, add);
	
	}

}
