package RecupNF;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class RecupNF {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int iTotRegistros=0;
		String sRadical="";
		try{

			FileReader fileNameTam = new FileReader("D:\\users\\f18867b\\Documents\\TODO List\\Recupera��o de NF Pe�as\\Dezembro\\SAL0.txt");
			BufferedReader lerArqTam = new BufferedReader(fileNameTam);
			String linhaTam = lerArqTam.readLine(); // l� a primeira linha

			FileWriter fileNameTamOut = new FileWriter("D:\\users\\f18867b\\Documents\\TODO List\\Recupera��o de NF Pe�as\\Dezembro\\SAL1.txt");
			PrintWriter gravarArqTam = new PrintWriter(fileNameTamOut);

			while (linhaTam != null) {
				if (linhaTam.matches("^[0-9]*$")){
					sRadical = linhaTam.substring(0, 8);
				}
				else{
					sRadical = linhaTam;
				}
				gravarArqTam.printf("%s\n", sRadical);
				linhaTam = lerArqTam.readLine(); // l� da segunda at� a �ltima linha
				iTotRegistros++;
				//System.out.printf("Linha [%04d]: [%s]\n", iTotRegistros, sRadical);
			}
			System.out.printf("Total de Registros Lidos: [%04d]\n", iTotRegistros);
			fileNameTam.close();
			fileNameTamOut.close();
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}

	}

}
