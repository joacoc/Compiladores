package CodigoIntermedio;

import java.util.ArrayList;

public abstract class Terceto {

	public static final String reg1 = "R1";
	public static final String reg2 = "R2";
	public static final String reg3 = "R3";
	public static final String reg4 = "R4";
	
	protected ArrayList<TercetoSimple> elementos;
	protected int numeroTerceto;
	protected ControladorTercetos controladorTercetos;
	String registro;
	


	
	public Terceto(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		elementos = new ArrayList<TercetoSimple>();
		elementos.add(izq);
		elementos.add(medio);
		elementos.add(der);
		this.numeroTerceto = numeroTerceto;
		
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
	}
	
	public void setControladorTercetos(ControladorTercetos controladorTercetos) {
		this.controladorTercetos = controladorTercetos;
	}

	public abstract String getAssembler();
}
