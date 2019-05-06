package ListPRSUP;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

import ListPRSUP.RedeAtiva;
import ListPRSUP.ListPRSUP_Estrut;
import ListPRSUP.Registro;

public class ListPRSUP {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String sUF="", sTxtArq="", sArqENTRADA="", sArqSAIDA="";
		
		ArrayList<RedeAtiva> redeAtiva = new ArrayList<RedeAtiva>();
		ArrayList<ListPRSUP_Estrut> listPRSUP = new ArrayList<ListPRSUP_Estrut>();
		ArrayList<Registro> registroList = new ArrayList<Registro>();
		
		try{
			
			System.out.printf("Inicio do Programa\n");
			FileReader fileRedeAtivaTam = new FileReader("D:\\LISTPRSUP\\RedeAtiva\\RedeAtiva.txt");
			BufferedReader lerRedeAtivaTam = new BufferedReader(fileRedeAtivaTam);
			String linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê a primeira linha

			while (linhaRedeAtivaTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtiva RATV = new RedeAtiva(linhaRedeAtivaTam.substring(0, 7), linhaRedeAtivaTam.substring(7, 9));
				redeAtiva.add(RATV);
				linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê da segunda até a última linha
			}
			fileRedeAtivaTam.close();

			FileReader fileListPRSUP = new FileReader("D:\\LISTPRSUP\\Entrada\\LPP_S_CHB.txt");
			BufferedReader lerListPRSUPTam = new BufferedReader(fileListPRSUP);
			System.out.printf("   Incio Leitura Arquivo LISTPRSUP\n");
			String linhaListPRSUPTam = lerListPRSUPTam.readLine(); // lê a primeira linha

			while (linhaListPRSUPTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				
				String tipoRegistro = linhaListPRSUPTam.substring(0, 7);
				//System.out.printf("[%s]\n", tpReg);
				if (tipoRegistro.equals("@HEADER")){
					sUF = linhaListPRSUPTam.substring(19, 21);
					System.out.printf("      Achou o HEADER da UF [%s]\n", sUF);
				}
				else if (tipoRegistro.equals("$TRAILL")){
					FileWriter fileNameOut = new FileWriter("D:\\LISTPRSUP\\Saida\\" + sUF);
					PrintWriter gravarArq = new PrintWriter(fileNameOut);
					System.out.printf("      Incio Gravacao Arquivo [%s]\n", sUF);
					
					//Grava Header
					for (Registro HD : registroList){
						//System.out.println(HD);
						gravarArq.printf("%-187s\n", HD);
					}
					
					fileNameOut.close();
					System.out.printf("      Fim Gravacao Arquivo [%s]\n", sUF);
					System.out.printf("----------------------------------------------------\n");
					sUF="";
					registroList.clear();
				}
				else{
					Registro LPRSUP = new Registro(linhaListPRSUPTam.substring(1, 188));
					registroList.add(LPRSUP);
				}
				linhaListPRSUPTam = lerListPRSUPTam.readLine(); // lê da segunda até a última linha
			}
			fileListPRSUP.close();
			System.out.printf("   Fim Leitura Arquivo LISTPRSUP\n");

//
			File fileEntrada = new File("D:\\LISTPRSUP\\Saida\\");
			
			if (fileEntrada.exists() && fileEntrada.isDirectory()) {
				String[] nomesArquivoEntrada = fileEntrada.list();

				for (int i = 0; i < nomesArquivoEntrada.length; i++) {
					sTxtArq = nomesArquivoEntrada[i];
					
					//Grava Header
					for (RedeAtiva RA : redeAtiva){
						//System.out.println(HD);
						if (RA.UF.equals(sTxtArq)){
							sArqENTRADA = "D:\\LISTPRSUP\\Saida\\" + sTxtArq;
							sArqSAIDA = "D:\\LISTPRSUP\\LinkEntry\\" + RA.CODDEALER;
							//System.out.printf("      Incio Gravacao Dealer [%s]\n", RA.CODDEALER);

							File arquivoOrigem = new File(sArqENTRADA);
							File arquivoSaida = new File(sArqSAIDA);
							copyFile(arquivoOrigem, arquivoSaida);
						}
					}
				}
			}
			
			System.out.printf("Fim do Programa\n");
			
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//System.out.println();

	}
	/**
     * Copia arquivos de um local para o outro 
     * @param origem - Arquivo de origem 
     * @param destino - Arquivo de destino 
     * @param overwrite - Confirmação para sobrescrever os arquivos 
     * @throws IOException 
     */
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
