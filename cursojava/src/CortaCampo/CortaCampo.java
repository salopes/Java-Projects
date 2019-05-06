package CortaCampo;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class CortaCampo {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int iTotRegistros=0;
		String sRadical="";
		try{

			FileReader fileNameTam = new FileReader("D:\\MIDAFULL");
			BufferedReader lerArqTam = new BufferedReader(fileNameTam);
			String linhaTam = lerArqTam.readLine(); // lê a primeira linha

			FileWriter fileNameTamOut = new FileWriter("D:\\MIDAFULL_OUT.txt");
			PrintWriter gravarArqTam = new PrintWriter(fileNameTamOut);

			while (linhaTam != null) {
				sRadical = linhaTam.substring(39, 45);
				gravarArqTam.printf("%s\n", sRadical);
				linhaTam = lerArqTam.readLine(); // lê da segunda até a última linha
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
