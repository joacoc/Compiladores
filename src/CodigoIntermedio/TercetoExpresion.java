package CodigoIntermedio;

public class TercetoExpresion extends Terceto {
	


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
			this.setRegistro(this.reg1);
			assembler = "MOV R1, " + elementos.get(1).getToken().getNombre()  + '\n'; //no se si siempre es R1
			assembler = assembler + opAssembler + " R1, " + elementos.get(2).getToken().getNombre() + '\n';
		}
		else
		//caso 2: (OP, registro, variable)
		if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			this.setRegistro(this.reg1);
			assembler = opAssembler + " " + terceto1.getRegistro() + " ," + elementos.get(1).getToken().getNombre()+ '\n';
		}
		else
		//caso 3: (OP, registro, registro)
		if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			this.setRegistro(this.reg1);
			assembler = opAssembler + " " + terceto1.getRegistro() + " , " + terceto2.getRegistro() + '\n';
		}
		//caso 4: (OP, registro, registro)
		if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			if ( esConmutativo(operador) ){
				assembler = opAssembler + " " +  reg1 + ", " + elementos.get(1).getToken().getNombre()+ '\n'; // lo mismo, ver si es r1
				this.setRegistro(reg1);
			}
			else{
				this.setRegistro(reg2);
				assembler = "MOV R2," + elementos.get(1).getToken().getNombre() + '\n';
				assembler = assembler + opAssembler + " "+ terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';//mirar desp tambien
			}
				
		}
		return assembler;
	}

}
