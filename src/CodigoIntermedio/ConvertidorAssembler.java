package CodigoIntermedio;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import org.omg.CORBA.portable.InputStream;

public class ConvertidorAssembler {

	ControladorTercetos controladorTercetos;
	File arch;
	
	public ConvertidorAssembler( ControladorTercetos controladorTercetos ) {
		this.controladorTercetos = controladorTercetos;
	}
	
	public void generarAssembler () throws IOException{
		arch = new File("salida.asm");
		controladorTercetos.generarAssembler();
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

}
