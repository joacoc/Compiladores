package Calculadora;

import AnalizadorLexico.Token;

public class Resta implements Calculador {

	@Override
	public Token calcular(Token t1, Token t2, Boolean allow) {
		Token t = null;

		t = new Token("aux",ID);
		t.setValor(t1.getValor()-t2.getValor());
		return t;
	}

}
