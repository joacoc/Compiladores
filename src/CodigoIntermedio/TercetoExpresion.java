package CodigoIntermedio;

public class TercetoExpresion extends Terceto {
	
	public final static String MOV = "MOV";
	public final static String ADD = "ADD";
	public final static String SUB = "SUB";
	public final static String MULT = "MULT";
	public final static String DIV = "DIV";
	
	

	public TercetoExpresion(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
		super(izq, medio, der, numeroTerceto);
	}
	
	private boolean esConmutativo(String operador){
		return ( (operador == "+") || (operador == "*") );		
	}
	
	private String convertirOperador(String op){
		if (op == "+") return "ADD";
		if (op == "-") return "SUB";
		if (op == "/") return "DIV";
		return "MULT";
	}
	
	public String getAssembler() {
		String assembler = "";
		String operador = elementos.get(0).getToken().getNombre();
		Terceto terceto1 = null;
		if (!elementos.get(1).esToken())
			terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getToken().getNombre() ) );
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getToken().getNombre() ) );
		String opAssembler = convertirOperador(operador);
		
		//caso 1: (OP, variable, variable)
		if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			String registro = controladorTercetos.getProxRegLibre();
			this.setRegistro(registro);
			assembler = MOV + " " + registro +", " + elementos.get(1).getToken().getNombre()  + '\n'; 
			assembler = assembler + opAssembler + " " + registro + ", " + elementos.get(2).getToken().getNombre() + '\n';
		}
		else
		//caso 2: (OP, registro, variable)
		if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			this.setRegistro(terceto1.getRegistro());// se usa el del primer terceto.
			assembler = opAssembler + " " + terceto1.getRegistro() + " ," + elementos.get(1).getToken().getNombre()+ '\n';
		}
		else
		//caso 3: (OP, registro, registro)
		if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			this.setRegistro( terceto1.getRegistro() );
			assembler = opAssembler + " " + terceto1.getRegistro() + " , " + terceto2.getRegistro() + '\n';
		}
		//caso 4: (OP, registro, registro)
		if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			if ( esConmutativo(operador) ){
				String registro = controladorTercetos.getProxRegLibre();
				assembler = opAssembler + " " +  registro + ", " + elementos.get(1).getToken().getNombre()+ '\n'; // lo mismo, ver si es r1
				this.setRegistro(registro);
			}
			else{
				this.setRegistro(terceto2.getRegistro());
				assembler = "MOV R2," + elementos.get(1).getToken().getNombre() + '\n';
				assembler = assembler + opAssembler + " "+ terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';//mirar desp tambien
			}
				
		}
		return assembler;
	}

}
