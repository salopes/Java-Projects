package Arquivo;

import java.nio.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import javax.swing.JOptionPane;

public class Leitura {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		Path caminho = Paths.get("D:/users/f18867b/Documents/TODO List/SAP AR FULL/War/IF_BR_01_315_20160612-111618-045-Cop2.TXT");
		try{
			
			byte[] texto = Files.readAllBytes(caminho);
			String leitura = new String(texto);
			
			JOptionPane.showMessageDialog(null, leitura);
			
		} catch (Exception erro){
			
		}
		

	}

}
