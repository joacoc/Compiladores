package CodigoIntermedio;

public class TercetoComparacion extends Terceto {
	
	public final static String CMP = "CMP";
	public static final String etiquetaIgual = "JE";
	public static final String etiquetaDistinto = "JNE";

	public TercetoComparacion(TercetoSimple izq, TercetoSimple medio, TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
		// TODO Auto-generated constructor stub
	}

	public String getAssembler() {
		String assembler = "";
		String operador = elementos.get(0).getNombreVar();
		Terceto terceto1 = null;
		if (!elementos.get(1).esToken())
			terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
		
		
		//caso 1: (OP, variable, variable)
		if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			assembler = assembler + CMP + " " +  elementos.get(1).getNombreVar() + ", " + elementos.get(2).getNombreVar()+ '\n';
		}
		else
		//caso 2: (OP, registro, variable)
		if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			assembler =  CMP + " "  + terceto1.getRegistro() +", " + elementos.get(2).getNombreVar()+ '\n';
			controladorTercetos.liberarRegistro(terceto1.getRegistro());
		}
		else
		//caso 3: (OP, registro, registro)
		if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			assembler = CMP  + " " + terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';
			controladorTercetos.liberarRegistro(terceto1.getRegistro());
			controladorTercetos.liberarRegistro(terceto2.getRegistro());
		}
		//caso 4: (OP, variable, registro)
		if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
				assembler = CMP + " " + elementos.get(1).getNombreVar()+ " ,R1" + '\n'; ///cambiar R1
				controladorTercetos.liberarRegistro(terceto2.getRegistro());
		}
		return assembler;
	}

}
