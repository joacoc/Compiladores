package Calculadora;

import java.util.ArrayList;

import AnalizadorLexico.Token;

public class Suma implements Calculador{

	//	Matriz permitidos se utiliza para saber que tipo de token puedo utilizar con otro tipo 
	//			 int float string
	//	int		true true false
	//	float	true true false
	//	string  false false false
	
	public Suma(){
	};
	
	public Token calcular(Token t1, Token t2){
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
	};
}
