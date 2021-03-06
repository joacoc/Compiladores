 package CodigoIntermedio;

import java.util.ArrayList;

public abstract class Terceto {

	public static final String reg2Integer = "BX";
	public static final String reg2Long = "EBX";
	public static final String reg1Integer = "CX";
	public static final String reg1Long = "ECX";
	public static final String reg3Integer = "AX";
	public static final String reg3Long = "EAX";
	public static final String reg4Integer = "DX";
	public static final String reg4Long = "EDX";
	
	protected ArrayList<TercetoSimple> elementos;
	protected int numeroTerceto;
	protected ControladorTercetos controladorTercetos;
	String registro;
	String registro_usado;
	int posicion;

	public Terceto(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		elementos = new ArrayList<TercetoSimple>();
		elementos.add(izq);
		elementos.add(medio);
		elementos.add(der);
		this.numeroTerceto = numeroTerceto;
		
	}
	
	public void setPosicionTerceto(int pos){
		posicion = pos;
	}
	
	public int getPosicionTerceto(){
		return posicion;
	}
	
	public String imprimirTerceto(){
		String terceto = numeroTerceto + "  (";
		for (int i = 0 ; i< elementos.size(); i++){
			if (elementos.get(i) != null)
				terceto = terceto + elementos.get(i).imprimirTerceto();
			else
				terceto = terceto + " - ";
			if (i != elementos.size()-1)
				terceto = terceto + ", ";
			else
				terceto = terceto + ")";
		}
		return terceto;
	}
	
	public void setElemento(int index, TercetoSimple t){
		elementos.set(index, t);
	}
	
	public TercetoSimple getTerceto(int index){
		if (elementos.get(index)==null)
			return null;
		else
			return elementos.get(index);
	}
	
	public String getRegistro() {
		return registro;
	}
	
	public void setRegistro(String registro) {
		this.registro = registro;
		this.registro_usado = registro;
	}
	
	public void setControladorTercetos(ControladorTercetos controladorTercetos) {
		this.controladorTercetos = controladorTercetos;
	}

	public abstract String getAssembler();
}
