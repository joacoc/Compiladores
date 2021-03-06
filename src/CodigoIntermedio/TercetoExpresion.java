package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;
import AnalizadorLexico.TokenMatriz;

public class TercetoExpresion extends Terceto {
	
	public final static String MOV = "MOV";
	public final static String ADD = "ADD";
	public final static String SUB = "SUB";
	public final static String MULT = "IMUL";
	public final static String DIV = "DIV";
	private String registroAux1;
	private String registroAux2;
	
	

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
		return "IMUL";
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
			String registro1 = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
			this.setRegistro(registro1);
			

			if(elementos.get(1).getNombreVar().startsWith("mat")){
//				assembler = verificarMatriz((TokenMatriz)elementos.get(1).t,controladorTercetos);
				assembler = MOV + " " + registro1 +", " + elementos.get(1).getNombreVar()  + "["+controladorTercetos.getRegMatriz(1)+"]\n";
				controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(1));	
			}else
				assembler = MOV + " " + registro1 +", " + elementos.get(1).getNombreVar()  +'\n';
			
			String registro2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());

			if(elementos.get(2).getNombreVar().startsWith("mat")){
//				assembler += verificarMatriz((TokenMatriz)elementos.get(2).t,controladorTercetos);
				String regMatrizAux = controladorTercetos.getRegMatriz(2);
				if(regMatrizAux==null)
					assembler += MOV + " " + registro2 +", " + elementos.get(2).getNombreVar() + "["+controladorTercetos.getRegMatriz(1)+"]\n";
				else
					assembler += MOV + " " + registro2 +", " + elementos.get(2).getNombreVar() + "["+regMatrizAux+"]\n";
				
				controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(1));				
				controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(2));
			}else
				assembler += MOV + " " + registro2 +", " + elementos.get(2).getNombreVar()  + '\n';
			
			assembler += hacerConversiones(registro1, registro2);
			registro1 = registroAux1;
			registro2 = registroAux2;
			assembler = assembler + opAssembler + " " + registro1 + ", " + registro2 + '\n';
			if ((elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ) || (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) )
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
			else
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableI);
			controladorTercetos.liberarRegistro(registro2);
		}
		else
		//caso 2: (OP, registro, variable)
		if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
			this.setRegistro(terceto1.getRegistro());// se usa el del primer terceto.
			String registro1 = terceto1.getRegistro();
			String registro2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken() );
			
			if(elementos.get(2).getNombreVar().startsWith("mat")){
				assembler = MOV + " " + registro2 +", " + elementos.get(2).getNombreVar()  + "[" +controladorTercetos.getRegMatriz(1) +"]\n";
				controladorTercetos.liberarRegistro(controladorTercetos.getRegMatriz(1));
			}else
				assembler = MOV + " " + registro2 +", " + elementos.get(2).getNombreVar()  + '\n';
			
			
			assembler += hacerConversiones(registro1, registro2);
			registro1 = registroAux1;
			registro2 = registroAux2;
			assembler = assembler + opAssembler + " " + registro1 + ", " + registro2 + '\n';
			
			if ((terceto1.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableL) ) || (elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) )
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
			else
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableI);
			controladorTercetos.liberarRegistro(registro2);
		}
		else
		//caso 3: (OP, registro, registro)
		if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
			String registro1 = terceto1.getRegistro();
			String registro2 = terceto2.getRegistro();
			this.setRegistro(registro1);// se usa el del primer terceto.
			
			assembler += hacerConversiones(registro1, registro2);
			registro1 = registroAux1;
			registro2 = registroAux2;
			assembler = assembler + opAssembler + " " + registro1 + ", " + registro2 + '\n';
			if ((terceto1.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableL) ) || (terceto2.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableL)) )
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
			else
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableI);
			controladorTercetos.liberarRegistro(registro2);
		}
		
		if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){

			//caso 4: (OP, variable, registro)
			String registro2 = terceto2.getRegistro();
			
			String registro1 = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
			this.setRegistro(registro1);// se usa el del primer terceto.

			if(elementos.get(1).getNombreVar().startsWith("mat")){//Nunca va a entrar aca xq son registros
				assembler = MOV + " " + registro1 +", " + elementos.get(1).getNombreVar()  +"["+controladorTercetos.getRegMatriz(1) +"]\n";
			}else
				assembler = MOV + " " + registro1 +", " + elementos.get(1).getNombreVar()  + '\n';

			assembler += hacerConversiones(registro1, registro2);
			registro1 = registroAux1;
			registro2 = registroAux2;
			assembler = assembler + opAssembler + " " + registro1 + ", " + registro2 + '\n';
			
			if ( ( elementos.get(1).getToken().getTipo().equals(AnalizadorLexico.variableL) ) || (terceto2.getTerceto(0).getToken().getTipo().equals(AnalizadorLexico.variableL) ) )
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableL);
			else
				elementos.get(0).getToken().setTipo(AnalizadorLexico.variableI);
			controladorTercetos.liberarRegistro(registro2);
		}
		
		if(getAssemblerErrores()!=null)
			//ver porque hay que reubicarlo porquen en div va antes y en suma desp
			assembler = assembler + getAssemblerErrores(); 
		
		return assembler;
	}

	private String hacerConversiones(String registro1, String registro2) {
		registroAux1 = registro1;
		registroAux2 = registro2;
		String assembler = "";
		
		if ( (elementos.get(1).getToken().getTipo()!=null && elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableI) ) && (elementos.get(2).getToken().getTipo()!= null && elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableL)) ){
			assembler = assembler + "MOV"  + " " + "AX" + ", " + registro1 + '\n';
			assembler = assembler + "CWDE" + '\n';
			controladorTercetos.liberarRegistro(registro1);
			elementos.get(1).getToken().setTipo(AnalizadorLexico.variableL);
			registroAux1 = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
			this.setRegistro(registroAux1);
			assembler = assembler + "MOV"  + " " + registroAux1 + ", " + "EAX" + '\n';
			elementos.get(1).getToken().setTipo(AnalizadorLexico.variableI);
			return assembler;
		}
		else
			if ( (elementos.get(1).getToken().getTipo()!=null && elementos.get(1).getToken().getTipo().equals( AnalizadorLexico.variableL) ) && (elementos.get(2).getToken().getTipo()!=null && elementos.get(2).getToken().getTipo().equals(AnalizadorLexico.variableI)) ){
				assembler = assembler + "MOV"  + " " + "AX" + ", " + registro2 + '\n';
				assembler = assembler + "CWDE" + '\n';
				controladorTercetos.liberarRegistro(registro2);
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableL);
				registroAux2 = controladorTercetos.getProxRegLibre(elementos.get(2).getToken());
				this.setRegistro(registroAux1);
				assembler = assembler + "MOV"  + " " + registroAux2 + ", " + "EAX" + '\n';
				elementos.get(2).getToken().setTipo(AnalizadorLexico.variableI);
				return assembler;
			}
		return assembler;
	}

	public static String verificarMatriz(TokenMatriz tokenMatriz, ControladorTercetos controladorTercetos){
		String assembler = "";
		assembler = assembler + "CMP " + controladorTercetos.getTerceto(controladorTercetos.getNumTercetoActual()-1).getRegistro()+ "," +(tokenMatriz.getColumnas()*tokenMatriz.getFilas()*4) + "\n";
		assembler = assembler + "JG " + ConvertidorAssembler.labelFueraRango +"\n" ;
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
