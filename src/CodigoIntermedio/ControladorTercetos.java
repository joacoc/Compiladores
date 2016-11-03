package CodigoIntermedio;

import java.io.Writer;
import java.util.ArrayList;
import java.util.Vector;

import AnalizadorLexico.Token;

public class ControladorTercetos {
	
	public static final String BF = "BF";
	public static final String BI = "BI";
	public static final int cantReg = 4;
	
	private ArrayList<Terceto> tercetos ;
	private ArrayList<Integer> pila ;
	ArrayList<Boolean>registros; //ver el tipo
	private ArrayList<Integer> labelPendientes; // por el tema del if

	private int cantPrint = 0;
	
	
	public ControladorTercetos() {
		tercetos = new ArrayList<Terceto>();
		pila = new ArrayList<Integer>();
		registros = new ArrayList<Boolean>(cantReg);
		registros.add(false);
		registros.add(false);
		registros.add(false);
		registros.add(false);
		labelPendientes = new ArrayList<Integer>(); 
	}
	
	public String imprimirTercetos() {
		String cadena="Tercetos: \n";
		for (Terceto t: tercetos )
			cadena= cadena + t.imprimirTerceto() + '\n';
		return cadena;
	}
	
	public void addTerceto(int index, Terceto t){
		tercetos.add(index, t);
	}
	
	public int borrarLabelPendiente() {
		int l = labelPendientes.get( labelPendientes.size()-1 );
		labelPendientes.remove( labelPendientes.size()-1 );
		return l;
	}
	
	public void addTerceto(Terceto t){
		tercetos.add(t);
	}
	
	public int getProxNumero(){
		return tercetos.size()+1;
	}
	
	public void addLabelPendiente(int labelPendiente) {
		this.labelPendientes.add( labelPendiente );
		System.out.println(labelPendientes.size() );
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
		pila.add(new Integer(tercetos.size()) );
	}
	
	public void desapilarFor(){
		Terceto nuevo = tercetos.get(tercetos.size()-1);
		TercetoSimple add = new TercetoSimple(new Token( String.valueOf(pila.remove(pila.size()-1)) ) );
		nuevo.setElemento(1, add);
	}

	public boolean errorControlFOR(Token t1, Token t2){
		char c = t2.getNombre().charAt(0);
		System.out.println("c:"+c);
		System.out.println( "esto" + tercetos.size() )  ;
		if (Character.isDigit(c)){
			//es un terceto	
			return  ( errorControlFOR(t1, tercetos.get(Integer.parseInt(t2.getNombre())-1).getTerceto(1).getToken() ) ) ;
					
		}
		else	
			return ( t1.getNombre() != t2.getNombre() );
	}

	public ArrayList<Terceto> getTercetos() {
		return tercetos;
	}
	
	public Terceto getTerceto (int index) {
		return tercetos.get(index-1);
	}
	
	public String getProxRegLibre(){
		for (int i =0; i< registros.size(); i++)
			if ( !registros.get(i) ){ 
				//esta libre y lo ocupamos		
				if ( registros.get(0) == false ) {
					registros.set(i, true);
					return Terceto.reg1;
					}
				else
					if ( registros.get(1) == false ) {
						registros.set(i, true);
						return Terceto.reg2;
						}
					else
						if ( registros.get(2) == false ){
							registros.set(i, true);
							return Terceto.reg3;
							}
				return Terceto.reg4;
			}
		//estan todos los registros ocupados
		return " ";
	}
	
	public void liberarRegistro (String registro){
		int index = 0;
		if (registro == Terceto.reg1)  index = 0;
		if (registro == Terceto.reg2)  index = 1;
		if (registro == Terceto.reg3)  index = 2;
		if (registro == Terceto.reg4)  index = 3;
		registros.set(index, new Boolean(false));//paso a estado libre el registro en la pos index
	}

	public String generarAssembler() {
		ArrayList<String> assembler_l = new ArrayList<>();
		String assembler = "";
		
		int i = 1; //numero de terceto para colocar el label
		
		for ( Terceto t: tercetos ){
			
			t.setControladorTercetos(this);
			assembler = assembler + t.getAssembler();
			//assembler_l.add(t.getAssembler());
			
			i++;
			if ( (!labelPendientes.isEmpty()) && ( i == labelPendientes.get(labelPendientes.size()-1) ) ){
				assembler = assembler + "Label" + String.valueOf(labelPendientes.get(labelPendientes.size()-1)) + '\n';
				//assembler_l.add("Label"+String.valueOf(labelPendientes.get(labelPendientes.size()-1))+"\n");
				borrarLabelPendiente();
			}
		}
		return assembler;
	}
	
	//Informa que se agrego un terceto de print y modifica el ultimo terceto para coordinar
	//el nombre con el .datat del ASM
	public void addPrint(){
		cantPrint++;
		((TercetoPrint)tercetos.get(tercetos.size()-1)).setPrint(String.valueOf(cantPrint));
	}
}
