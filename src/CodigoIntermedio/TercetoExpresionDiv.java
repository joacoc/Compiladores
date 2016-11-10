package CodigoIntermedio;

import AnalizadorLexico.AnalizadorLexico;
import AnalizadorLexico.Token;

public class TercetoExpresionDiv extends TercetoExpresion{


		public TercetoExpresionDiv(TercetoSimple izq, TercetoSimple medio,	TercetoSimple der, int numeroTerceto) {
			super(izq, medio, der, numeroTerceto);
		}
		
		public String getAssembler() {
			String assembler = "";
			Terceto terceto1 = null;
			if (!elementos.get(1).esToken())
				terceto1 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(1).getNombreVar() ) );
			Terceto terceto2 = null;
			if (!elementos.get(2).esToken())
				terceto2 = controladorTercetos.getTerceto( Integer.parseInt( elementos.get(2).getNombreVar() ) );
			String opAssembler = "DIV";
			
			//caso 1: (OP, variable, variable)
			if ( ( elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
				
				controladorTercetos.OcuparRegistro(reg4Integer);
				controladorTercetos.OcuparRegistro(reg3Integer);
				String registroDX = "";
				String registroAX = "";
				
				if (elementos.get(1).getToken().getTipo() == AnalizadorLexico.variableI){	
					//dividendo en DX
					registroDX = this.reg4Integer;
					this.setRegistro(registroDX);
					//divisor en AX
					registroAX = this.reg3Integer;
					this.setRegistro(registroAX);
				}
				else{
					//dividendo en DX
					registroDX = this.reg4Long;
					this.setRegistro(registroDX);
					//divisor en AX
					registroAX = this.reg3Long;
					this.setRegistro(registroAX);
				
				}

				assembler = MOV + " " + registroAX +", " + elementos.get(1).getNombreVar()  + '\n';
				assembler = assembler + "CWD" + '\n';

				String registro = controladorTercetos.getProxRegLibre(elementos.get(1).getToken());
				this.setRegistro(registro);
				assembler = assembler + opAssembler + " " + registro + '\n';
			}
//			else
//			//caso 2: (OP, registro, variable)
//			if ( ( !elementos.get(1).esToken() ) && ( elementos.get(2).esToken() ) ){
//				this.setRegistro(terceto1.getRegistro());// se usa el del primer terceto.
//				assembler = opAssembler + " " + terceto1.getRegistro() + " ," + elementos.get(1).getNombreVar()+ '\n';
//			}
//			else
//			//caso 3: (OP, registro, registro)
//			if ( ( !elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
//				this.setRegistro( terceto1.getRegistro() );
//				assembler = opAssembler + " " + terceto1.getRegistro() + " , " + terceto2.getRegistro() + '\n';
//			}
//			//caso 4: (OP, registro, registro)
//			if ( ( elementos.get(1).esToken() ) && ( !elementos.get(2).esToken() ) ){
//				if ( esConmutativo(operador) ){
//					String registro = controladorTercetos.getProxRegLibre();
//					assembler = opAssembler + " " +  registro + ", " + elementos.get(1).getNombreVar()+ '\n'; // lo mismo, ver si es r1
//					this.setRegistro(registro);
//				}
//				else{
//					String registro = controladorTercetos.getProxRegLibre();
//					this.setRegistro(registro); //verificar puedo haber flashado.
//					assembler = MOV + " " + registro + ", " + elementos.get(1).getNombreVar() + '\n';
//					assembler = assembler + opAssembler + " "+ terceto1.getRegistro() + ", " + terceto2.getRegistro() + '\n';//mirar desp tambien
//				}
//					
//			}
//			assembler = assembler + getAssemblerErrorDivCero(); //ver porque hay que reubicarlo porquen en div va antes y en suma desp
			return assembler;
		}

		private String getAssemblerErrorDivCero() {
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
