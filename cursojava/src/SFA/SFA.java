package SFA;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.io.File;

public class SFA{
	public static void main(String[] args){
		
		String chaveSort="", sDiretorioELOIMS="", sNomeArquivoIN="", sNomeArquivoWRK="", sNomeArquivoOUT="";
		int iDpl=0;
		
		ArrayList<SFA_Estrut> sfa = new ArrayList<SFA_Estrut>();
		
		try{
			
			
			File arquivosELO[];
			sDiretorioELOIMS="D:\\ArquivosSFA\\IN\\";
			File diretorioELO = new File(sDiretorioELOIMS);
			arquivosELO = diretorioELO.listFiles();
	
			for(int w = 0; w < arquivosELO.length; w++){
				sNomeArquivoIN = sDiretorioELOIMS + arquivosELO[w].getName();
				FileReader fileNameTam = new FileReader(sNomeArquivoIN);
				BufferedReader lerArqTam = new BufferedReader(fileNameTam);
				String linhaTam = lerArqTam.readLine(); // lê a primeira linha

				sNomeArquivoWRK="D:\\ArquivosSFA\\WRK\\" + arquivosELO[w].getName() + "_WRK";
				FileWriter fileNameTamOut = new FileWriter(sNomeArquivoWRK);
				PrintWriter gravarArqTam = new PrintWriter(fileNameTamOut);

				while (linhaTam != null) {
					//System.out.printf("%s\n", linhaTam);
					gravarArqTam.printf("%-128s\n", linhaTam);
					linhaTam = lerArqTam.readLine(); // lê da segunda até a última linha
				}
				fileNameTam.close();
				lerArqTam.close();
				gravarArqTam.close();
				
				fileNameTamOut.close();
				gravarArqTam.close();

				FileReader fileName = new FileReader(sNomeArquivoWRK);
				BufferedReader lerArq = new BufferedReader(fileName);
				String linha = lerArq.readLine(); // lê a primeira linha
			
				while (linha != null) {
					chaveSort = linha;
					SFA_Estrut sf = new SFA_Estrut(linha, chaveSort);
				
					if (sfa.size() == 0){
						sfa.add(sf);	
					}else{
						iDpl=0;
	
						for (int i=0; i<sfa.size(); i++) {
							if (sfa.get(i).getChaveSort().equals(chaveSort)){
								iDpl=1;
								break;
							}
						}
						
						if (iDpl==0){
							sfa.add(sf);
						}
	
					}
					linha = lerArq.readLine();
				}
				fileName.close();
				lerArq.close();

				sNomeArquivoOUT="D:\\ArquivosSFA\\OUT\\" + arquivosELO[w].getName() + "_OK";
				System.out.println(sNomeArquivoOUT);
			
				FileOutputStream FOS = new FileOutputStream(sNomeArquivoOUT);
				OutputStreamWriter OSW = new OutputStreamWriter(FOS,"UTF-8");
				BufferedWriter fileNameOut = new BufferedWriter(OSW); 
				PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
				for (SFA_Estrut SF : sfa){
					//System.out.println(HD);
					gravarArq.printf("%-128s\n", SF);
				}

				sfa.clear();			
				gravarArq.close();
				fileNameOut.close();
				OSW.close();
				FOS.close();
			}
			System.out.println("Fim de Processamento");
			
			
		}catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
			
		
	}
}