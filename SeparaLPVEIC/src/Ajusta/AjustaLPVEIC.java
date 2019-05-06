package Ajusta;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;


public class AjustaLPVEIC {

	public static void main(String[] args) {
		String sDealer="", sArqENTRADA="", sArqSAIDA="", sRedeAtiva="", tipoRegistro="";
		
		ArrayList<RedeAtiva> redeAtiva = new ArrayList<RedeAtiva>();
		ArrayList<Registro> registroList = new ArrayList<Registro>();
		
		try{
			
			System.out.printf("Inicio do Programa\n");

			sRedeAtiva="D:\\LPVEIC\\RedeAtiva\\RedeAtiva.txt";
			System.out.printf("\t>> Incio Leitura Arquivo [%s]\n", sRedeAtiva);

			FileReader fileRedeAtivaTam = new FileReader(sRedeAtiva);
			BufferedReader lerRedeAtivaTam = new BufferedReader(fileRedeAtivaTam);
			String linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê a primeira linha

			while (linhaRedeAtivaTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtiva RATV = new RedeAtiva(linhaRedeAtivaTam);
				redeAtiva.add(RATV);
				linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê da segunda até a última linha
			}
			fileRedeAtivaTam.close();
			System.out.printf("\t<< Fim Leitura Arquivo [%s]\n", sRedeAtiva);

			sArqENTRADA="D:\\LPVEIC\\LPVEIC_10012017.txt";
			FileReader fileLPVEIC = new FileReader(sArqENTRADA);
			BufferedReader lerLPVEICTam = new BufferedReader(fileLPVEIC);

			sArqSAIDA="D:\\LPVEIC\\SAIDA\\LPVEIC_10012017_OK.txt";
			BufferedWriter fileNameOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sArqSAIDA),"UTF-8"));
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			
			System.out.printf("\t>> Incio Leitura Arquivo [%s]\n", sArqENTRADA);
			String linhaLPVEICTam = lerLPVEICTam.readLine(); // lê a primeira linha

			boolean flag=false;

			while (linhaLPVEICTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				
				tipoRegistro = linhaLPVEICTam.substring(0, 7);
				sDealer = linhaLPVEICTam.substring(19, 24);

				if (tipoRegistro.equals("@HEADER")){
					for (RedeAtiva RA : redeAtiva){
						if (RA.CODDEALER.equals(sDealer)){
							flag=true;
							break;
						}
						else{
							flag=false;
						}
					}
				}
				
				if(flag){
					Registro LPVEIC = new Registro(linhaLPVEICTam);
					registroList.add(LPVEIC);
				}

					
				linhaLPVEICTam = lerLPVEICTam.readLine(); // lê da segunda até a última linha
			}
			
			for(Registro RG : registroList){
				gravarArq.printf("%-111s\r\n", RG);
			}
			System.out.printf("\t<< Fim Leitura Arquivo [%s]\n", sArqENTRADA);
			System.out.printf("\t-- Arquivo de Saida Gerado[%s]\n", sArqSAIDA);
			fileNameOut.close();
			registroList.clear();
			fileLPVEIC.close();
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//System.out.println();
		System.out.printf("Fim do Programa\n");

	}

    public static void copyFile (File origem, File destino) throws IOException {
        Date date = new Date();
        InputStream in = new FileInputStream(origem);
        OutputStream out = new FileOutputStream(destino);           
        // Transferindo bytes de entrada para saída
        byte[] buffer = new byte[1024];
        int lenght;
        while ((lenght= in.read(buffer)) > 0) {
            out.write(buffer, 0, lenght);
        }
        in.close();
        out.close();
        Long time = new Date().getTime() - date.getTime();
        //System.out.println("Saiu copy"+time);
    }
	
}
