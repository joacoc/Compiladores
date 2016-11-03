package CodigoIntermedio;

public class TercetoFor extends Terceto {

	String tipoSalto;
	public static final String etiquetaSaltoIncondicional = "JMP";
	
	
	public TercetoFor(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public void setTipoSalto(String tipoSalto){
		if(tipoSalto== "<=")
			this.tipoSalto = "JBE";
		else
			if(tipoSalto.equals("="))
				this.tipoSalto = "JE";
			else
				if(tipoSalto.equals(">="))
					this.tipoSalto = "JGE";
				else
					if(tipoSalto.equals(">"))
						this.tipoSalto = "JG";
					else
						if(tipoSalto.equals("<"))
							this.tipoSalto = "JB";
	};

	public String getAssembler() {
		// TODO Auto-generated method stub
		String assembler = "";
		
	
		//Terceto izq. 
		String operador = elementos.get(0).getToken().getNombre();
		//Terceto del medio == condicion; 
		
		//Cuando termina el for salta
		if (operador == controladorTercetos.BF){
			assembler = tipoSalto + " Label" + elementos.get(2).getToken().getNombre() + '\n';
			controladorTercetos.addLabelPendiente( Integer.parseInt(elementos.get(2).getToken().getNombre() ) );
		}
		else{
			//BI	
			assembler = "JUMP Label" + elementos.get(1).getToken().getNombre() + '\n';
			assembler = assembler + "Label" + elementos.get(1).getToken().getNombre() + ":	" + '\n';
		}
		return assembler;
	}
}
