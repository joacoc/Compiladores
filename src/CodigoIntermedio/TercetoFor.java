package CodigoIntermedio;

public class TercetoFor extends Terceto {

	String tipoSalto;
	public static final String etiquetaSaltoIncondicional = "JMP";
	
	
	public TercetoFor(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public void setTipoSalto(String tipoSalto){
		if(tipoSalto== "<=")
			this.tipoSalto = "JG";
		else
			if(tipoSalto.equals("="))
				this.tipoSalto = "JNE";
			else
				if(tipoSalto.equals(">="))
					this.tipoSalto = "JL";
				else
					if(tipoSalto.equals(">"))
						this.tipoSalto = "JLE";
					else
						if(tipoSalto.equals("<"))
							this.tipoSalto = "JGE";
	};

	public String getAssembler() {
		// TODO Auto-generated method stub
		String assembler = "";
		
	
		//Terceto izq. 
		String operador = elementos.get(0).getNombreVar();
		
		if (operador == controladorTercetos.BF){
			assembler = assembler + tipoSalto + " Label" + elementos.get(2).getNombreVar() + '\n';
			controladorTercetos.addLabelPendiente( Integer.parseInt(elementos.get(2).getNombreVar() ) );
		}
		else{
			//BI	
			assembler = "JMP Label" + elementos.get(1).getNombreVar() + '\n';
		}
		return assembler;
	}
}
