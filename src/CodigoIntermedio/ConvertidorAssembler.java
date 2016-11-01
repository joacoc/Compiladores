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

	static ControladorTercetos controladorTercetos;
	static TablaSimbolos tablaSimb;
	static File arch;
	
	public ConvertidorAssembler( ControladorTercetos controladorTercetos ) throws IOException {
		this.controladorTercetos = controladorTercetos;
		tablaSimb = null;
	}
	
	public void setTablaSimb(TablaSimbolos tablaSimb) {
		this.tablaSimb = tablaSimb;
	}
	
	public void generarAssembler () throws IOException{
		//controladorTercetos.generarAssembler();
		arch = new File("salida.asm");
		writeFile1();

//		PrintWriter p = new PrintWriter(new FileWriter(arch));
		//Imprimir codigo assembler

//		String comc = "cmd /c .\\masm32\\bin\\ml /c /Zd /coff salida.asm ";
//		Process ptasm32 = Runtime.getRuntime().exec(comc);
//		InputStream is = (InputStream) ptasm32.getInputStream();
//
//		String coml = "cmd /c \\masm32\\bin\\Link /SUBSYSTEM:CONSOLE salida.obj ";
//		Process ptlink32 = Runtime.getRuntime().exec(coml);
//		InputStream is2 = (InputStream) ptlink32.getInputStream();


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
				+ ".data" + '\n');	
		String data = tablaSimb.getAssembler() + '\n' + ".code"+ "\n";
		bw.write( data );
		String code = "start:" + '\n' + (String) controladorTercetos.generarAssembler(); 
		bw.write( code );

		bw.close();
	}

}
