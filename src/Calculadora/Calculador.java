package Calculadora;

import java.util.ArrayList;

import AnalizadorLexico.Token;

public interface Calculador {
	
	//	Matriz permitidos se utiliza para saber que tipo de token puedo utilizar con otro tipo 
	//			 int float string
	//	int
	//	float
	//	string
	Token calcular(Token t1, Token t2);
}
