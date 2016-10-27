package CodigoIntermedio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.omg.CORBA.portable.InputStream;

public class ConvertidorAssembler {

	ControladorTercetos controladorTercetos;
	ArrayList<String>registros; //ver el tipo
	File arch;
	
	public ConvertidorAssembler( ControladorTercetos controladorTercetos ) {
		this.controladorTercetos = controladorTercetos;
//		for (String s: registros)
//			s =
	}
	
	public void generarAssembler () throws IOException{
		arch = new File("salida.asm");
		generarArchivo();
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
	

	public String generarArchivo() {
		ArrayList<Terceto> tercetos = controladorTercetos.getTercetos();
		String assembler = "";
		for ( Terceto t: tercetos ){
			t.setControladorTercetos(controladorTercetos);
			assembler = assembler + t.getAssembler();
		}
		return assembler;
	}
}
