package LotoFacil;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
//import Veiculos.SIM05000_ESTRUT;
import java.util.ArrayList;

public class AvaliaResult {

	public static void main(String[] args){
		int iControle1=0, iControle2=0, iControle3=0, iControle4=0, iControle5=0;
		String s1="0", s2="0", s3="0", s4="", s5="0";
		String sDezena="";

		ArrayList<LotoLinha_Estrut> lotoLinha_Estrut = new ArrayList<LotoLinha_Estrut>();
		//ArrayList<SIM05000_ESTRUT> sim05000_ESTRUT = new ArrayList<SIM05000_ESTRUT>();
		
		try{
			FileReader fileName = new FileReader("D:\\Lotofacil.txt");
			BufferedReader lerArq = new BufferedReader(fileName);
			
			String linha = lerArq.readLine(); // lê a primeira linha
			
			while (linha != null) {
				System.out.printf(" Linha While [%s] - Tamanho [%d]\n", linha, linha.length());
				for (int i = 0; i < linha.length(); i++) {
					sDezena = linha.substring(i, i+2);
					i++;
					System.out.printf("Dezena [%s]\n", sDezena);
					if(Integer.parseInt(sDezena)<6){ // Primeira linha
						System.out.printf("Dezena Linha 01 [%s]\n", sDezena);
						if(Integer.parseInt(sDezena)==1){
							iControle1 = iControle1 + 1;
						}
						else if(Integer.parseInt(sDezena)==2){
							iControle1 = iControle1 + 2;
						}
						else if(Integer.parseInt(sDezena)==3){
							iControle1 = iControle1 + 4;
						}
						else if(Integer.parseInt(sDezena)==4){
							iControle1 = iControle1 + 8;
						}
						else{
							iControle1 = iControle1 + 16;
						}
						
						s1 = iControle1 + "";
					}
					else if (Integer.parseInt(sDezena)<11){ // Segunda linha
						System.out.printf("Dezena Linha 02 [%s]\n", sDezena);
						if(Integer.parseInt(sDezena)==6){
							iControle2 = iControle2 + 1;
						}
						else if(Integer.parseInt(sDezena)==7){
							iControle2 = iControle2 + 2;
						}
						else if(Integer.parseInt(sDezena)==8){
							iControle2 = iControle2 + 4;
						}
						else if(Integer.parseInt(sDezena)==9){
							iControle2 = iControle2 + 8;
						}
						else{
							iControle2 = iControle2 + 16;
						}
						
						s2 = iControle2 + "";
						
					}
					else if (Integer.parseInt(sDezena)<16){ // Terceira linha
						System.out.printf("Dezena Linha 03 [%s]\n", sDezena);
						if(Integer.parseInt(sDezena)==11){
							iControle3 = iControle3 + 1;
						}
						else if(Integer.parseInt(sDezena)==12){
							iControle3 = iControle3 + 2;
						}
						else if(Integer.parseInt(sDezena)==13){
							iControle3 = iControle3 + 4;
						}
						else if(Integer.parseInt(sDezena)==14){
							iControle3 = iControle3 + 8;
						}
						else{
							iControle3 = iControle3 + 16;
						}
						
						s3 = iControle3 + "";
	
					}
					else if (Integer.parseInt(sDezena)<21){ // Quarta linha
						System.out.printf("Dezena Linha 04 [%s]\n", sDezena);
						if(Integer.parseInt(sDezena)==16){
							iControle4 = iControle4 + 1;
						}
						else if(Integer.parseInt(sDezena)==17){
							iControle4 = iControle4 + 2;
						}
						else if(Integer.parseInt(sDezena)==18){
							iControle4 = iControle4 + 4;
						}
						else if(Integer.parseInt(sDezena)==19){
							iControle4 = iControle4 + 8;
						}
						else{
							iControle4 = iControle4 + 16;
						}
						
						s4 = iControle4 + "";
						
					}
					else { //Quinta Linha
						System.out.printf("Dezena Linha 05 [%s]\n", sDezena);
						if(Integer.parseInt(sDezena)==21){
							iControle5 = iControle5 + 1;
						}
						else if(Integer.parseInt(sDezena)==22){
							iControle5 = iControle5 + 2;
						}
						else if(Integer.parseInt(sDezena)==23){
							iControle5 = iControle5 + 4;
						}
						else if(Integer.parseInt(sDezena)==24){
							iControle5 = iControle5 + 8;
						}
						else{
							iControle5 = iControle5 + 16;
						}
						
						s5 = iControle5 + "";
						
					}
						
				}
				
				LotoLinha_Estrut LOTOLINHA = new LotoLinha_Estrut( s1
										                         , s2
										                         , s3
										                         , s4
										                         , s5);
				lotoLinha_Estrut.add(LOTOLINHA);
				s1="0";
				s2="0";
				s3="0";
				s4="0";
				s5="0";
				iControle1=0;
				iControle2=0;
				iControle3=0;
				iControle4=0;
				iControle5=0;
				linha = lerArq.readLine(); // lê da segunda até a última linha
			} 
			
			//Collections.sort(sim05000_ESTRUT);

			FileWriter fileNameOut = new FileWriter("D:\\Lotofacil_OUT.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			//Grava Cabecalho Semanas
			for (LotoLinha_Estrut LLE : lotoLinha_Estrut){
				//System.out.println(LLE);
				gravarArq.printf("%s\n", LLE);
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
