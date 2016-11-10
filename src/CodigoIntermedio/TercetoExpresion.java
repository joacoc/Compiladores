package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

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
		String operador = elementos.get(0).getNombreVar();
		Terceto terceto1 = null;
		if (!elementos.get(1).esToken())
			terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
		Terceto terceto2 = null;
		if (!elementos.get(2).esToken())
			terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
		String opAssembler = convertirOperador(operador);
		
		//caso 1: (OP, variable, variable)
		if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
			this.setRegistro(registro);
			assembler = MOV + " " + registro +", " + elementos.get(1).getNombreVar()  + '\n'; 
			assembler = assembler + opAssembler + " " + registro + ", " + elementos.get(2).getNombreVar() + '\n';
		}
		else
		//caso 2: (OP, registro, variable)
		if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			this.setRegistro(terceto1.getRegistro());// se usa el del primer terceto.
			assembler = opAssembler + " " + terceto1.getRegistro() + " ," + elementos.get(1).getNombreVar()+ '\n';
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
				String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
				assembler = opAssembler + " " +  registro + ", " + elementos.get(1).getNombreVar()+ '\n'; // lo mismo, ver si es r1
				this.setRegistro(registro);
			}
			else{
				String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
				this.setRegistro(registro); //verificar puedo haber flashado.
				assembler = MOV + " " + registro + ", " + elementos.get(1).getNombreVar() + '\n';
				assembler = assembler + opAssembler + " "+ terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';//mirar desp tambien
			}
				
		}
		
		if(getAssemblerErrores()!=null)
			assembler = assembler + getAssemblerErrores(); //ver porque hay que reubicarlo porquen en div va antes y en suma desp
		
		return assembler;
	}

	private String getAssemblerErrores() {
		if ( elementos.get(0).getNombreVar() == "/" ){
			AnalizadorLexico al = new AnalizadorLexico(null, null);
			String igual = "=";
			TercetoComparacion tc = new TercetoComparacion( new TercetoSimple( new Token("=", (int) igual.charAt(0) ) ) , new TercetoSimple(elementos.get(2).getToken() ), new TercetoSimple(new Token( "_i0", al.CTEI ) ), 0);
			String assembler = tc.getAssembler() ;
			assembler = assembler + "JE LabelDivCero" + '\n';
			return assembler;
		}
		return null;
	}

}
