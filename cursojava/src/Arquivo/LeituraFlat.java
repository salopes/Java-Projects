package Arquivo;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class LeituraFlat {
	public static void main(String[] args ) {

		Scanner scan = null;
		
		try{

			File fileName = new File("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 3\\IF_BR_01_315_20160617-165216-898 - SALOMAO.TXT");
			scan = new Scanner(fileName);

			while(scan.hasNextLine()){
				
			}
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	
	}
}
/*
		//Scanner file = new Scanner(new FileReader("arquivo.txt")).useDelimiter("\\||\\n");
		Scanner file = new Scanner(new File("arquivo.txt"));
		while (file.hasNext()) {
			String nome = file.next();
			String cidade = file.next();
			String compras = file.next();
			// int num = file.nextInt();
			System.out.println(nome);
			System.out.println(cidade);
			System.out.println(compras);
		}
		
*/
