package CodigoIntermedio;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.util.ArrayList;

import org.omg.CORBA.portable.InputStream;

import AnalizadorLexico.TablaSimbolos;

public class ConvertidorAssembler {

	public static final String labelDivCero = "LabelDivCero";
	public static final String labelPerdida = "LabelERRORPERDIDA:";
	public static final String labelOverflow = "LabelOverflow";
	public static final String labelFueraRango = "LabelFueraRango";
	
	static ControladorTercetos controladorTercetos;
	static TablaSimbolos tablaSimb;
	static File arch;
	
	public ConvertidorAssembler( ControladorTercetos controladorTercetos ) throws IOException {
		this.controladorTercetos = controladorTercetos;
		tablaSimb = null;
	}
	
	public void setTablaSimb(TablaSimbolos tablaSimb) {
		this.tablaSimb = tablaSimb;
		controladorTercetos.setTablaSimbolos(tablaSimb);
	}
	
	public void generarAssembler () throws IOException{
		//controladorTercetos.generarAssembler();
		arch = new File("salida.asm");
		writeFile1();
/*
<<<<<<< HEAD
//		PrintWriter p = new PrintWriter(new FileWriter(arch));
=======
		File arch = new File("salida.asm");
//		PrintWriter p = new PrintWriter(new FileWriter(arch));
		//Imprimir codigo assembler
>>>>>>> 13b611176a32f60c504bf4667c19008a9f6c0711

		String comc = "cmd /c .\\masm32\\bin\\ml /c /Zd /coff salida.asm";
		Process ptasm32 = Runtime.getRuntime().exec(comc);
		java.io.InputStream is =  ptasm32.getInputStream();
		*/
	}
	
	public String generarArchivo(){
		return controladorTercetos.generarAssembler();
	}
	
	public static void writeFile1() throws IOException {
		FileOutputStream fos = new FileOutputStream(arch);
	 
		BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(fos));
		
		
		bw.write(".386" + '\n' 
				+ ".model flat, stdcall" + '\n'
				+ "option casemap :none" + '\n'
				+ "include \\masm32\\include\\windows.inc" + '\n'
				+ "include \\masm32\\include\\kernel32.inc" + '\n'
				+ "include \\masm32\\include\\user32.inc" + '\n'
				+ "includelib \\masm32\\lib\\kernel32.lib" + '\n'
				+ "includelib \\masm32\\lib\\user32.lib" + '\n'
				+ '\n' +".data" + '\n');	
		String data = tablaSimb.getAssembler() ;
		data = data + controladorTercetos.getPrintsAssembler();
		data = data + "DividirCero db \"Error al dividir por cero!\", 0" + '\n';
		data = data + "errorPerdida db \"Hay perdida de informacion a la hora de realizar una asignacion\", 0" + '\n';
		data = data + "FueraRango db \"Se intento acceder a una posicion de la matriz fuera del rango!\", 0" + '\n';
		data = data + "matrix dw 0" +"\n";
		data = data + '\n' + ".code"+ "\n";
		
		//matrix es una variable auxiliar para las matrices.
		//le puse este nombre xq es una palabra reservada.
		//entonces me aseguro de nunca verla en el assembler (espero)
		
		bw.write( data );
		
		//Inicia el codigo
		String code = "start:" + '\n' + (String) controladorTercetos.generarAssembler(); 

		code = code + "invoke ExitProcess, 0" + '\n';

		bw.write( code );
		String errores = getErroresRunTime();
		bw.write(errores);
		bw.write( "end start" );

		bw.close();
	}

	private static String getErroresRunTime() {
		String errores = labelDivCero + ":" + '\n';
		errores = errores + "invoke MessageBox, NULL, addr DividirCero, addr DividirCero, MB_OK" + '\n';
		errores = errores + "invoke ExitProcess, 0" + '\n';
		errores = errores + labelPerdida + ":" + '\n';
		errores = errores + "invoke MessageBox, NULL, addr errorPerdida, addr errorPerdida, MB_OK" + '\n';
		errores = errores + "invoke ExitProcess, 0" + '\n';
		errores = errores + labelFueraRango + ":" + '\n';
		errores = errores + "invoke MessageBox, NULL, addr FueraRango, addr FueraRango, MB_OK" + '\n';
		errores = errores + "invoke ExitProcess, 0" + '\n';
		return errores;
	}

}
