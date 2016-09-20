package AnalizadorLexico;

public class MatrizTE {
	
	static final String error1  = "Error al declarar un identificador. Los caracteres validos son letras, digitos y _";
	static final String error2  = "Error al declarar el operador <. Se esperaba un digito o una letra despues del operador <";
	static final String error3  = "Error al declarar el operador !. Se espera el simbolo = seguido del simbolo !";
	static final String error4  = "Error al declarar un identificador. Los caracteres validos son letras, digitos y _";
	static final String error5  = "Error al declarar el operador <. Se esperaba un digito,una letra o un = despues del operador <";
	static final String error6  = "Error al declarar el operador !. Se espera el simbolo = seguido del simbolo !";
	static final String error7  = "Error: Si desea usar una constante, debe usar '_i' ó '_l'";
	static final String error8  = "Caracter invalido";
	static final String error9  = "Error al declarar el operador >. Se esperaba un digito,una letra o un = despues del operador >";
	static final String error10 = "Se esperaba salto de linea despues del +";
	static final String error11 = "Falta un segundo & para comentar";
	static final String error12 = "Error: Si desea realizar una asignacion, luego de ':' debe seguir '='";
	static final String error13 = "Error: se esperaba un digito para definir una constante";
	
	static final int col=23;
	static final String compilacion= "Error de compilacion";
	static final String warning= "Warning";
	
	TablaSimbolos tablaSimb;
	CeldaAbs matTrans[][];// = {
			// l, d, _, i, l, +, /, *, +, -, :, >, <, !, ", &, @, F, C, otro, nl, b-tab
				/*
				,,,,,,,,,,,,,,,,,,,,,,					//	Estado 0	Lauti
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 1	Joaco	
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 2	Joaco
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 3	Juanchi	
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 4	Juanchi
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 5	Joaco
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 6	Joaco	
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 7	Lauti
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 8	Juanchi
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 9	Juanchi	
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 10 	Joaco
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 11	Lauti	
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 12	Lauti
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 13	Juanchi
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 14	Juanchi
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 15	Joaco
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 16	Lauti
				,,,,,,,,,,,,,,,,,,,,,,					// 	Estado 17	Lauti
				*/
		//};
	public int getColumna(Character c){
		if ( c.isDigit(c) )
			return 1;
		if ( c.charValue()== '_')
			return 2;
		if ( c.charValue()== 'i')
			return 3;
		if ( c.charValue()== 'l')
			return 4;
		if ( c.charValue()== '+')
			return 5;
		if (  ( c.charValue()== '/') || ( c.charValue()== '*') || (c.charValue()== '[') ||
			  (c.charValue()== ']') ||
			  ( c.charValue()== '(') || ( c.charValue()== ')') || ( c.charValue()== '{') ||
			  ( c.charValue()== '}') || ( c.charValue()== ',') || ( c.charValue()== ';') )
			return 6;
		if ( c.charValue()== '=')
			return 7;
		if ( c.charValue()== '-')
			return 8;
		if ( c.charValue()== ':')
			return 9;
		if ( c.charValue()== '>')
			return 10;
		if ( c.charValue()== '<')
			return 11;
		if ( c.charValue()== '!')
			return 12;
		if ( c.charValue()== '"')
			return 13;
		if ( c.charValue()== '&')
			return 14;
		if ( c.charValue()== '@')
			return 15;
		if ( c.charValue()== 'F')
			return 16;
		if ( c.charValue()== 'C')
			return 17;
		if ( c.charValue()== '\n')//hay que ver si este es el salto de linea
			return 19;
		if ( ( c.charValue()== ' ' ) || (c.charValue() ==  '	' ) )// agregar tab
			return 20;
		if ( c.charValue()== '$') //ver como es fin de archivo
			return 21;
		if ( c.isLetter(c) ) //que pregunte primero las letras particulares
			return 0;
		return 18;// si no entra a ninguno es caracter "OTRO"
	}
	
	public MatrizTE(TablaSimbolos ts) {
		// TODO Auto-generated constructor stub
		matTrans = new CeldaAbs[col][col];
		tablaSimb = ts; //despues pasar por parametros
		matTrans[0][2] = new Celda(2);
		fila0();
		fila1();
		fila2();
		fila3();
		fila4();
		fila5();
		fila6();
		fila7();
		fila8();
		fila9();
		fila10();
		fila11();
		fila12();
		fila13();
		fila14();
		fila15();
		fila16();
		fila17();
		fila18();
		fila19();
		fila20();
		fila21();
		fila22();
	}
	
	public CeldaAbs getCelda(int fila, int col) {
		return matTrans[fila][col];
	}
	
	public TablaSimbolos getTablaSimbolos(){
		return tablaSimb;
	}

	public void fila0(){
		//Estado inicial
		
		matTrans[0][0]= new Celda(1);
		matTrans[0][1]= new CeldaAS(-2,tablaSimb,new Error (error7,compilacion));
		matTrans[0][2]= new Celda(2);
		matTrans[0][3]= new Celda(1);
		matTrans[0][4]= new Celda(1);
		matTrans[0][5]= new Celda(20);
		matTrans[0][6]= new Celda(20);
		matTrans[0][7]= new Celda(20);
		matTrans[0][8]= new Celda(5);
		matTrans[0][9]= new Celda(6);
		matTrans[0][10]= new Celda(7);
		matTrans[0][11]= new Celda(8);
		matTrans[0][12]= new Celda(9);
		matTrans[0][13]= new Celda(10);
		matTrans[0][14]= new Celda(12);
		matTrans[0][15]= new CeldaAS(-2,tablaSimb,new Error (error8,compilacion));
		matTrans[0][16]= new Celda(1);
		matTrans[0][17]= new Celda(1);
		matTrans[0][18]= new CeldaAS(-2,tablaSimb,new Error (error8,compilacion));
		matTrans[0][19]= new Celda(0);
		matTrans[0][20]= new Celda(0);
		matTrans[0][21]= new CeldaAS(-1, tablaSimb);
	}
	
	public void fila1(){
		//Variable
		
		for(int i = 0; i<5; i++)
			matTrans[1][i] = new Celda(1);
		
		for(int i = 5; i<16; i++)
			matTrans[1][i] = new CeldaAS(-1,tablaSimb);
			
		
		for(int i = 16; i<18; i++)
			matTrans[1][i] = new Celda(1);
		
		for(int i = 18; i<22; i++)
			matTrans[1][i] = new CeldaAS(-1,tablaSimb);
		
	}
	
	public void fila2(){
		
		for(int i = 0; i<3; i++)
			matTrans[2][i] = new CeldaAS(-2, tablaSimb, new Error(error7,compilacion));
		
		matTrans[2][3] = new Celda(3);
		matTrans[2][4] = new Celda(4);
		
		for(int i = 5; i<22; i++)
			matTrans[2][i] = new CeldaAS(-2, tablaSimb, new Error(error7,compilacion));
	}
	
	private void fila3(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[3][i]= new CeldaAS(-2,tablaSimb, new Error(error13, compilacion));
		}		

		matTrans[3][1]= new Celda(21);
		matTrans[3][8]= new Celda(18);
	}
	
	private void fila4(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[4][i]= new CeldaAS(-2,tablaSimb, new Error(error13, compilacion));
		}		
		matTrans[4][1]= new Celda(22);
		matTrans[4][8]= new Celda(19);
	}
	
	
	public void fila5(){
		for(int i = 0; i<22; i++)
			matTrans[5][i] = new CeldaAS(-1, tablaSimb);
		
		matTrans[5][8] = new Celda(20); 
	}

	public void fila6(){
		// : = 
		
		for(int i = 0; i<7; i++)
			matTrans[6][i] = new CeldaAS(-2, tablaSimb, new Error(error12,compilacion));
		
		matTrans[6][7] = new Celda(20);
		
		for(int i = 8; i<col; i++)
			matTrans[6][i] = new CeldaAS(-2, tablaSimb, new Error(error12,compilacion));
		

	}

	public void fila7() {
		for(int i =0; i<col; i++)
			matTrans[7][i] = new CeldaAS(-1,tablaSimb);
		matTrans[7][7] = new Celda(20);
	}
	
	private void fila8(){
		for (int i = 0; i<col; i++ ){
			if ( i != 7)
				matTrans[8][i]= new CeldaAS(-1,tablaSimb);
			else
				matTrans[8][7]= new Celda(20);
		}		
	}
	
	private void fila9(){
		for (int i = 0; i<col; i++ ){
			if ( i != 7)
				matTrans[9][i]= new CeldaAS(-2,tablaSimb,new Error(error3,compilacion));
			else
				matTrans[9][7]= new Celda(20);
		}		
	}
	
	public void fila10(){
		//Comentario entre " "
		
		for(int i = 0; i<5; i++)
			matTrans[10][i] = new Celda(10);
		
		matTrans[10][5] = new Celda(11);
		
		for(int i = 6; i<21; i++)
			matTrans[10][i] = new Celda(10);

		matTrans[10][13] = new Celda(20);
		matTrans[10][21] = new CeldaAS(-2,tablaSimb,new Error(error10,compilacion));
		
	}
	
	public void fila11(){
		
		for(int i = 0; i<19; i++)
			matTrans[11][i] = new CeldaAS(-2,tablaSimb,new Error(error10,compilacion));
		
		matTrans[11][19] = new Celda(10);
		matTrans[11][20] = new CeldaAS(-2,tablaSimb,new Error(error10,compilacion));
		matTrans[11][21] = new CeldaAS(-2,tablaSimb,new Error(error10,compilacion));
	}
	
	public void fila12(){
		for(int i = 0; i<14; i++)
			matTrans[12][i] = new CeldaAS(-2,tablaSimb,new Error(error11,compilacion));
		
		matTrans[12][14] = new Celda(13);
		for(int i = 15; i<col; i++)
			matTrans[12][i] = new CeldaAS(-2,tablaSimb,new Error(error11,compilacion));
	}

	
	private void fila13(){
		for (int i = 0; i<col; i++ ){
			if ( i == 15)
				matTrans[13][i]= new Celda(14);
			else
				if ( ( i == 19) || (i == 21) )
					matTrans[13][i]= new CeldaAS(-1, tablaSimb);
				else
					matTrans[13][i]= new Celda(17);
		}	
	}
	
	private void fila14(){
		for (int i = 0; i<col; i++ ){
			if ( ( i == 19) || (i == 21) )
				matTrans[14][i]= new CeldaAS(-1, tablaSimb);
			else
				matTrans[14][i]= new Celda(17);
		}		
		matTrans[14][16]= new Celda(15);
		matTrans[14][17]= new Celda(16);
	}
	
	private void fila15(){
		
		for (int i = 0; i<col; i++ )
			matTrans[15][i]= new Celda(17);
		
		matTrans[15][19]= new CeldaAS(-1, tablaSimb);
		matTrans[15][21]= new CeldaAS(-1, tablaSimb);
	}
	
	private void fila16(){
		for (int i = 0; i<col; i++ )
			if ( ( i == 19) || (i == 21) )
				matTrans[16][i]= new CeldaAS(-1, tablaSimb);
			else
				matTrans[16][i]= new Celda(17);
	}
	
	private void fila17(){
		for (int i = 0; i<col; i++ )
			if ( ( i == 19) || (i == 21) )
				matTrans[17][i]= new CeldaAS(-1, tablaSimb);
			else
				matTrans[17][i]= new Celda(17);
	}
	
	private void fila18(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[18][i]= new CeldaAS(-2,tablaSimb, new Error(error13, compilacion));
			else
				matTrans[18][1]= new Celda(21);
		}		
	}
	
	private void fila19(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[19][i]= new CeldaAS(-2,tablaSimb, new Error(error13, compilacion));
			else
				matTrans[19][1]= new Celda(22);
		}		
	}
	
	//Fila 20 ??
	
	private void fila20(){
		for (int i = 0; i<col; i++ )
			matTrans[20][i]= new CeldaAS(-1,tablaSimb);
	}
	
	private void fila21(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[21][i]= new CeldaAS(-1,tablaSimb);
			else
				matTrans[21][1]= new Celda(21);
		}		
	}
	
	private void fila22(){
		for (int i = 0; i<col; i++ ){
			if ( i != 1)
				matTrans[22][i]= new CeldaAS(-1,tablaSimb);
			else
				matTrans[22][1]= new Celda(22);
		}
	}
}
