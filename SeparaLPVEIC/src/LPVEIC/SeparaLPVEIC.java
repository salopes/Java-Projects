package LPVEIC;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Date;

public class SeparaLPVEIC {

	public static void main(String[] args) {
		String sDealer="", sArqENTRADA="", sArqSAIDA="";
		
		ArrayList<Registro> registroList = new ArrayList<Registro>();
		
		
		try{
			
			System.out.printf("Inicio do Programa\n");

			sArqENTRADA="D:\\LPVEIC\\LPVEIC_10012017.txt";
			FileReader fileLPVEIC = new FileReader(sArqENTRADA);
			BufferedReader lerLPVEICTam = new BufferedReader(fileLPVEIC);
			System.out.printf("   Incio Leitura Arquivo LISTPRSUP [%s]\n", sArqENTRADA);
			String linhaLPVEICTam = lerLPVEICTam.readLine(); // lê a primeira linha


			while (linhaLPVEICTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);

				String tipoRegistro = linhaLPVEICTam.substring(0, 7);
				//System.out.printf("[%s]\n", tpReg);
				if (tipoRegistro.equals("@HEADER")){
					sDealer = linhaLPVEICTam.substring(19, 24);
					//System.out.printf("      Achou o HEADER da Dealer [%s]\n", sDealer);
					//System.out.printf("      Incio Gravacao Arquivo [%s]\n", sDealer);
				}
				else if (tipoRegistro.equals("$TRAILL")){
					//System.out.printf("      Fim Gravacao Arquivo [%s]\n", sDealer);
					sArqSAIDA="D:\\LPVEIC\\SAIDA\\LPVEIC_10012017_" + sDealer + ".txt";
					BufferedWriter fileNameOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sArqSAIDA),"UTF-8"));
					//FileWriter fileNameOut = new FileWriter(sArqSAIDA);
					PrintWriter gravarArq = new PrintWriter(fileNameOut);

					for(Registro RG : registroList){
						gravarArq.printf("%-111s\r\n", RG);
					}
					
					//File arquivoOrigem = new File(sArqWORK);
					//File arquivoSaida = new File(sArqSAIDA);
					//copyFile(arquivoOrigem, arquivoSaida);
					fileNameOut.close();

					//System.out.printf("      Fim Gravacao Arquivo [%s]\n", sDealer);
					//System.out.printf("----------------------------------------------------\n");
					sDealer="";
					registroList.clear();
					
				}
				else{
					Registro LPVEIC = new Registro(linhaLPVEICTam);
					registroList.add(LPVEIC);
				}
					
				linhaLPVEICTam = lerLPVEICTam.readLine(); // lê da segunda até a última linha
			}
			fileLPVEIC.close();
			System.out.printf("   Fim Leitura Arquivo LISTPRSUP\n");
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//System.out.println();

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
