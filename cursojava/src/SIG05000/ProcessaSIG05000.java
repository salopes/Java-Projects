package SIG05000;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

import java.util.ArrayList;

public class ProcessaSIG05000 {

	public static void main(String[] args) {
		String codDealer="";
		int iControle=0;
		String chaveSort = "";
		String chaveSort2 = "";

		ArrayList<Estrutura> sim05000_WEEK = new ArrayList<Estrutura>();
		
		try{
			FileReader fileName = new FileReader("D:\\ELO_BASE.txt");
			//FileReader fileName = new FileReader("D:\\ELO_FINAL.txt");
			BufferedReader lerArq = new BufferedReader(fileName);
			String linha = lerArq.readLine(); // lê a primeira linha
			
			while (linha != null) {
				System.out.printf("Linha [%s]\n", linha);
				String tpReg = linha.substring(0, 7);
				//String tpReg = linha.substring(0, 5);
				System.out.printf("tpReg [%s]\n", tpReg);
				
				if(tpReg.equals("S32@FTT")){
				//if(tpReg.equals("OP01L")){

					if(iControle==1){
						Estrutura WEK = new Estrutura( chaveSort2);
						sim05000_WEEK.add(WEK);	
						iControle=0;
//						chaveSort2 = "";
//						chaveSort = "";
					}
					
					codDealer = linha.substring(7, 13);
					//codDealer = linha.substring(5, 11);
					System.out.printf("codDealer [%s]\n", codDealer);
						
					chaveSort = "OP01L"+codDealer+"0888########160808SIG05000#####1608080900########SIG0500022L##############00#############";
					chaveSort2 = ";###########CL99L"+codDealer+"00888########160808SIG0500000000000000000000000##############000000################000000";
					
					System.out.printf("chaveSort [%s]\n", chaveSort);
						
					Estrutura WEK = new Estrutura( chaveSort);
					sim05000_WEEK.add(WEK);	
					iControle++;
					
				}
				Estrutura WEK = new Estrutura( linha);
				sim05000_WEEK.add(WEK);
				
				linha = lerArq.readLine(); // lê da segunda até a última linha
			} 
			Estrutura WEK = new Estrutura( chaveSort2);
			sim05000_WEEK.add(WEK);	
			
			//Collections.sort(sim05000_ESTRUT);

			FileWriter fileNameOut = new FileWriter("D:\\ELO_FINAL.txt");
			//FileWriter fileNameOut = new FileWriter("D:\\ELO_FINAL2.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			//Grava FdP
			for (Estrutura SIM : sim05000_WEEK){
				//System.out.println(SIM);
				gravarArq.printf("%s\n", SIM);
			}
			
			fileName.close();
			fileNameOut.close();
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//System.out.println();
	}


}
