package Calculadora;

import AnalizadorLexico.Token;

public class Division implements Calculador{

		@Override
		public Token calcular(Token t1, Token t2) {
			Token t = null;

			t = new Token("aux",ID);
			t.setValor(t1.getValor()/t2.getValor());
			return t;
		}
}
