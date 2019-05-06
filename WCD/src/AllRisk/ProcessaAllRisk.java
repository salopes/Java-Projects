package AllRisk;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.swing.JFrame;
import org.apache.commons.net.ftp.FTPClient;

public class ProcessaAllRisk {

	public static void main(String[] args) throws SocketException, IOException{
		int iRegControle=0, iOcorrencia=0, iRegFile=0, iControle=0;
		String sDataArq="", sDealer="", sDealerAux="", sSQL="", sDataSQL="";
		String sNomeArquivoFixo="", sNomeArquivoConnect="", sNomeArquivoAux="", sNomeArquivoAux2="";
		String sDiretorioENTRADA="", sDiretorioSAIDA = "", sDiretorioRedeAtiva = "", sDiretorioComandoSQL = "";
		
		ArrayList<AllRisk_Estrut> allRisk_Estrut = new ArrayList<AllRisk_Estrut>();
		ArrayList<SQLEstrut> sqlEstrut = new ArrayList<SQLEstrut>();
		ArrayList<RedeAtiva> redeAtiva = new ArrayList<RedeAtiva>();

		try{

			GregorianCalendar gc = new GregorianCalendar();
			Date dtINIFTP = gc.getTime();

	 		System.out.printf("%s\n",dtINIFTP);
	 		System.out.printf("INICIO FTP arquivos IMS!\n");
	 		System.out.printf("\n");
			
	    	 sDiretorioENTRADA    = "D:\\WCD\\AllRisk\\Entrada\\";
	    	 sDiretorioSAIDA      = "D:\\WCD\\AllRisk\\Saida\\";
	    	 sDiretorioRedeAtiva  = "D:\\WCD\\AllRisk\\RedeAtiva\\";
	    	 sDiretorioComandoSQL = "D:\\WCD\\AllRisk\\SQL\\";
			
			 FTPClient ftp = new FTPClient();
	         ftp.connect( "172.28.3.31" );
	         //System.out.println(ftp.getReplyString());
	         ftp.login( "ib00050", "112016as" );
	         //System.out.println(ftp.getReplyString());
	         ftp.setBufferSize(1024 * 1024);
	         ftp.enterLocalPassiveMode();
	         ftp.setAutodetectUTF8(true);
	         ftp.enterLocalPassiveMode();

	    	 File DeldiretorioELO1 = new File(sDiretorioENTRADA);
	    	 if (DeldiretorioELO1.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO1.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }
	    	 
	    	 File DeldiretorioELO2 = new File(sDiretorioSAIDA);
	    	 if (DeldiretorioELO2.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO2.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }
	    	 File DeldiretorioELO3 = new File(sDiretorioComandoSQL);
	    	 if (DeldiretorioELO3.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO3.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }

	    	 // J = quantidade de gera��es do arquivo no IMS - 1 mais recente (0)
	    	 
	         for(int j=0;j<1;j++){
	        	 if(j==0){
		        	 sNomeArquivoFixo = sDiretorioENTRADA + "AllRisk_" + j + ".txt"; 
		        	 sNomeArquivoConnect="'J109.D001.TR.FIASA.MOV.COMPLETO(" + j + ")'";
	        	 }
	        	 else{
	        		 iRegControle = (j *-1);
		        	 sNomeArquivoFixo = sDiretorioENTRADA + "AllRisk_" + j + ".txt"; 
		        	 sNomeArquivoConnect="'J109.D001.TR.FIASA.MOV.COMPLETO(" + iRegControle + ")'";
	        		 
	        	 }
		        // System.out.printf("FTP do arquivo [%s]\n", sNomeArquivoConnect);
		        // System.out.printf("Gravando do arquivo [%s]\n", sNomeArquivoFixo);

		         FileOutputStream fos = new FileOutputStream( sNomeArquivoFixo );
		         
	        	 if (ftp.retrieveFile( sNomeArquivoConnect , fos )){
		               //System.out.println("Download efetuado com sucesso!");
		               //System.out.println(ftp.getReplyString());
		               //System.out.println("retrieveFile?"+ftp.getReplyCode());
		         }
		         else{
		               //System.out.println ("Erro ao efetuar download do arquivo.");
		               //System.out.println("retrieveFile?"+ftp.getReplyCode());
		               //System.out.println(ftp.getReplyString());
		
		         }
	         }
	         
	         ftp.logout();
	         ftp.disconnect();

	 		Date dtFIMFTP = gc.getTime();
	 		//System.out.printf("\n");
	 		System.out.printf("%s\n",dtFIMFTP);
	 		System.out.printf("FIM FTP arquivos IMS!\n");
	 		System.out.printf("\n");
			
	 		Date dtINI = gc.getTime();
	 		System.out.printf("%s\n",dtINI);
	 		System.out.printf("INICIO Processamento!\n");

// Faz a leitura Rede Ativa			
	 		
			FileReader fileRedeAtiva = new FileReader(sDiretorioRedeAtiva + "RedeAtiva.txt");
			BufferedReader lerRedeAtiva = new BufferedReader(fileRedeAtiva);
			String linhaRedeAtiva = lerRedeAtiva.readLine(); // l� a primeira linha

			while (linhaRedeAtiva != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtiva RATV = new RedeAtiva(linhaRedeAtiva.substring(0, 5), linhaRedeAtiva.substring(6, 11));
				redeAtiva.add(RATV);
				linhaRedeAtiva = lerRedeAtiva.readLine(); // l� da segunda at� a �ltima linha
			}
			fileRedeAtiva.close();

			File arquivosELO[];
			File diretorioELO = new File(sDiretorioENTRADA);
			arquivosELO = diretorioELO.listFiles();
		
			for(int w = 0; w < arquivosELO.length; w++){
				sNomeArquivoAux = arquivosELO[w].getName();
				sNomeArquivoAux2 = sDiretorioENTRADA + sNomeArquivoAux;
						
				FileReader fileNameAllRisk = new FileReader(sNomeArquivoAux2);
				BufferedReader lerArqAllRisk = new BufferedReader(fileNameAllRisk);
				String linhaAllRisk = lerArqAllRisk.readLine(); // l� a primeira linha
				
				while (linhaAllRisk != null) {
					//System.out.printf(" Linha While [%s]\n", linha1);
					String tipoRegistro = linhaAllRisk.substring(0, 2);
					if (tipoRegistro.equals("00")){
						sDataArq = linhaAllRisk.substring(4, 12); 
					}
					else if (tipoRegistro.equals("11")){
							sDealer = linhaAllRisk.substring(36, 41);
							AllRisk_Estrut aR = new AllRisk_Estrut( sDataArq, sDealer, iOcorrencia, sDealer);
							allRisk_Estrut.add(aR);
					}
					linhaAllRisk = lerArqAllRisk.readLine(); // l� da segunda at� a �ltima linha
				} 
				
				fileNameAllRisk.close();
			}
		
			Collections.sort(allRisk_Estrut);
			
			for (AllRisk_Estrut AR : allRisk_Estrut){
				iControle++;
				for (RedeAtiva RA : redeAtiva){
					if (AR.sDealer.equals(RA.CODDEALER)){
						SQLEstrut sqlE = new SQLEstrut( AR.sData, AR.sDealer, iOcorrencia, RA.COD);
						sqlEstrut.add(sqlE);
						
					}
				}
			}
			
			Collections.sort(sqlEstrut);

			FileWriter fileNameOut = new FileWriter(sDiretorioComandoSQL + "INSERT.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			iControle=0;
						
			for (SQLEstrut SQLe : sqlEstrut){
				sDataSQL = "CONVERT(DATETIME,'" + SQLe.sData.substring(0,2) + "/" + SQLe.sData.substring(2,4) + "/" + SQLe.sData.substring(4,8) + "',103)";
				System.out.printf(" Dealer [%s] - [%s] - Data [%s]\n", SQLe.sDealer, SQLe.CHAVESORT, sDataSQL);
				
				if (iControle > 0 ){
					if (sDealerAux.equals(SQLe.CHAVESORT)){
						iOcorrencia++;
					}
					else{
						iOcorrencia++;
						sSQL = "INSERT INTO indicadorResultado ( Empresa_Codigo, Indicador_Codigo, IndicadorResultado_Valor, IndicadorResultado_BaseCalculo, IndicadorResultado_DataInicio, IndicadorResultado_DataFim, IndicadorResultado_Importado, IndicadorResultado_Validado) VALUES ( " + Integer.parseInt(sDealerAux) + ", 91, " + iOcorrencia + ", 0.00, " + sDataSQL + ", " + sDataSQL + ", 1.00 , 1.00 )";
						gravarArq.printf("%s\n", sSQL);
						sDealerAux = SQLe.CHAVESORT;
						iOcorrencia=0;
					}

				}
				else{
					//iOcorrencia++;
					sDealerAux = SQLe.CHAVESORT;
				}
				
				iControle++;
			}

			if(sqlEstrut.size() == iControle){
				iOcorrencia++;
				sSQL = "INSERT INTO indicadorResultado ( Empresa_Codigo, Indicador_Codigo, IndicadorResultado_Valor, IndicadorResultado_BaseCalculo, IndicadorResultado_DataInicio, IndicadorResultado_DataFim, IndicadorResultado_Importado, IndicadorResultado_Validado) VALUES ( " + Integer.parseInt(sDealerAux) + ", 91, " + iOcorrencia + ", 0.00, " + sDataSQL + ", " + sDataSQL + ", 1.00 , 1.00 )";
				gravarArq.printf("%s\n", sSQL);
			}
			
			fileNameOut.close();
			//System.out.printf("Quantidade de Pedidos Comerciais Lidos nos Sistemas:\n");
			
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
}
