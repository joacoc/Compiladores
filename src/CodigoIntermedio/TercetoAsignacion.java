package CodigoIntermedio;

public class TercetoAsignacion extends Terceto{

	public TercetoAsignacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	public String getAssembler() {
		String assembler = "";
		if ( elementos.get(2).esToken() ) 
			assembler = "MOV " + elementos.get(1).getToken().getNombre() + " " + elementos.get(2).getToken().getNombre()+ '\n';
		else{
			Terceto terceto = controladorTercetos.getTerceto(elementos.get(2).getNumeroTerceto() );
			assembler = "MOV " + elementos.get(1).getToken().getNombre() + terceto.getRegistro() + '\n';
			controladorTercetos.liberarRegistro( terceto.getRegistro() );
			
		}
		return assembler;
	}
}
