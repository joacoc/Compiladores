package CodigoIntermedio;

public class TercetoIf extends Terceto {
	
	private String tipoSalto;
	public static final String etiquetaSaltoIncondicional = "JMP";


	
	public TercetoIf(TercetoSimple izq, TercetoSimple medio, TercetoSimple der,	int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
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
		String assembler = "";
		String operador = elementos.get(0).getToken().getNombre();
		
		if (operador == controladorTercetos.BF){
			assembler = tipoSalto + " Label" + elementos.get(2).getToken().getNombre() + '\n';
			controladorTercetos.addLabelPendiente( Integer.parseInt(elementos.get(2).getToken().getNombre() ) );
		}
		else{
			assembler = etiquetaSaltoIncondicional + " Label" + elementos.get(1).getToken().getNombre() + '\n';
			assembler = assembler + "Label" + String.valueOf( controladorTercetos.borrarLabelPendiente() ) + '\n';
			controladorTercetos.addLabelPendiente( Integer.parseInt( elementos.get(1).getToken().getNombre() ) );
		}
		return assembler;
	}
}
