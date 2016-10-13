package Calculadora;

import AnalizadorLexico.Token;

public class Division implements Calculador{

		@Override
		public Token calcular(Token t1, Token t2) {
			Token t = null;
			
			if(t1.getTipo().equals("integer")){
				if(t2.getTipo().equals("integer")){
					//Seteo el tipo integer
				}
				else
					if(t2.getTipo().equals("longint")){
						//Seteo el tipo long
					}
			}else
				if(t1.getTipo().equals("longint")){
					if(t2.getTipo().equals("integer")){
						//Seteo el tipo long
					}
					else
						if(t2.getTipo().equals("longint")){
							//Seteo el tipo long
						}
				}
			
			//Caso contrario es un String == Error.
			
			return t;
		}
}
