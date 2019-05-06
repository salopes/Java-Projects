package CSI;

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

public class ProcessaCSI {

	public static void main(String[] args) throws SocketException, IOException{
		int iControle=0, iBase=0;
		String sDealer="", sDealerAux="", sSQL="", sDataSQL="", sMesSQL="";
		String tipoRegistro="", sHOt="", sTMedio="", sCSISErv="", sCSI="", sDataArq="", sKPI="", sValor="";

		String sNomeArquivoAux="", sNomeArquivoAux2="";
		String sDiretorioENTRADA="", sDiretorioSAIDA = "", sDiretorioRedeAtiva = "", sDiretorioComandoSQL = "";
		
		ArrayList<CSI_Estrut> csi_Estrut = new ArrayList<CSI_Estrut>();
		ArrayList<SQLEstrutCSI> sqlEstrutCSI = new ArrayList<SQLEstrutCSI>();
		ArrayList<RedeAtivaCSI> redeAtivaCSI = new ArrayList<RedeAtivaCSI>();

		try{

			GregorianCalendar gc = new GregorianCalendar();
			Date dtINIFTP = gc.getTime();

			//System.out.printf("\n");
	 		System.out.printf("%s\n",dtINIFTP);
	 		System.out.printf("INICIO Prepara��o ambiente!\n");
	 		System.out.printf("\n");

	    	 sDiretorioENTRADA    = "D:\\WCD\\CSI\\Entrada\\";
	    	 sDiretorioSAIDA      = "D:\\WCD\\CSI\\Saida\\";
	    	 sDiretorioRedeAtiva  = "D:\\WCD\\CSI\\RedeAtiva\\";
	    	 sDiretorioComandoSQL = "D:\\WCD\\CSI\\SQL\\";
			
    	 
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

	    	Date dtFIMFTP = gc.getTime();
	 		//System.out.printf("\n");
	 		System.out.printf("%s\n",dtFIMFTP);
	 		System.out.printf("FIM Prepara��o ambiente!\n");
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
				RedeAtivaCSI RATV = new RedeAtivaCSI(linhaRedeAtiva.substring(0, 5), linhaRedeAtiva.substring(6, 11));
				redeAtivaCSI.add(RATV);
				linhaRedeAtiva = lerRedeAtiva.readLine(); // l� da segunda at� a �ltima linha
			}
			fileRedeAtiva.close();

			File arquivosELO[];
			File diretorioELO = new File(sDiretorioENTRADA);
			arquivosELO = diretorioELO.listFiles();
//7722		
			for(int w = 0; w < arquivosELO.length; w++){
				sNomeArquivoAux = arquivosELO[w].getName();
				sNomeArquivoAux2 = sDiretorioENTRADA + sNomeArquivoAux;
						
				FileReader fileNameCSI = new FileReader(sNomeArquivoAux2);
				BufferedReader lerArqCSI = new BufferedReader(fileNameCSI);
				String linhaCSI = lerArqCSI.readLine(); // l� a primeira linha
				//int wCont=0;
				while (linhaCSI != null) {
					//if (wCont == 7722){
					//	System.out.printf(" Agora [%d]\n", wCont);
					//}
					//wCont++;

					//System.out.printf(" Linha While [%s]\n", linha1);
					tipoRegistro = linhaCSI.substring(0, 8);
					sDealer      = linhaCSI.substring(8, 13);
					sHOt         = linhaCSI.substring(14, 18);
					sTMedio      = linhaCSI.substring(18, 22);
					sCSISErv     = linhaCSI.substring(22, 29);
					sCSI         = linhaCSI.substring(29, 36);
					sDataArq     = linhaCSI.substring(36, 46);
						
					CSI_Estrut CSI = new CSI_Estrut(tipoRegistro, sDealer, sHOt, sTMedio, sCSISErv, sCSI, sDataArq, sDealer);
					csi_Estrut.add(CSI);
						
					linhaCSI = lerArqCSI.readLine(); // l� da segunda at� a �ltima linha
				} 
				
				fileNameCSI.close();
			}
		
			//Collections.sort(csi_Estrut);
			
			for (CSI_Estrut AR : csi_Estrut){
				iControle++;
				for (RedeAtivaCSI RA : redeAtivaCSI){
					if (AR.sDealer.equals(RA.CODDEALER)){
						iControle++;

						if (AR.stipoRegistro.equals("Servicos")){
							SQLEstrutCSI sqlS1 = new SQLEstrutCSI( AR.sData, AR.sDealer, "65", AR.sHOt, RA.COD); // IVPS-04
							sqlEstrutCSI.add(sqlS1);
							SQLEstrutCSI sqlS2 = new SQLEstrutCSI( AR.sData, AR.sDealer, "66", AR.sTMedio, RA.COD); // IVPS-05
							sqlEstrutCSI.add(sqlS2);
							SQLEstrutCSI sqlS3 = new SQLEstrutCSI( AR.sData, AR.sDealer, "102", AR.sCSISErv, RA.COD); // IVTD-03
							sqlEstrutCSI.add(sqlS3);
							SQLEstrutCSI sqlS4 = new SQLEstrutCSI( AR.sData, AR.sDealer, "86", AR.sCSI, RA.COD); // CSI VENDAS
							sqlEstrutCSI.add(sqlS4);
							break;

						}
						else{
							SQLEstrutCSI sqlV1 = new SQLEstrutCSI( AR.sData, AR.sDealer, "92", AR.sHOt, RA.COD); // ISPS-04
							sqlEstrutCSI.add(sqlV1);
							SQLEstrutCSI sqlV2 = new SQLEstrutCSI( AR.sData, AR.sDealer, "93", AR.sTMedio, RA.COD); // ISPS-05
							sqlEstrutCSI.add(sqlV2);
							SQLEstrutCSI sqlV3 = new SQLEstrutCSI( AR.sData, AR.sDealer, "87", AR.sCSISErv, RA.COD); // ISG-04
							sqlEstrutCSI.add(sqlV3);
							SQLEstrutCSI sqlV4 = new SQLEstrutCSI( AR.sData, AR.sDealer, "71", AR.sCSI, RA.COD); // CSI SERVICOS
							sqlEstrutCSI.add(sqlV4);
							break;
						}
					}
				}
			}
			
			//Collections.sort(sqlEstrutCSI);

			FileWriter fileNameOut = new FileWriter(sDiretorioComandoSQL + "INSERT.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			iControle=0;

			sSQL="";
			for (SQLEstrutCSI SQLe : sqlEstrutCSI){
				sDataSQL = "CONVERT(DATETIME,'" + SQLe.sData + "',103)";
				System.out.printf(" Dealer [%s] - Data [%s] - KPI [%s] - Valor [%s]\n", SQLe.sDealer, sDataSQL, SQLe.sKPI, SQLe.sValor);
				
				sDealerAux = SQLe.CHAVESORT;
				sKPI = SQLe.sKPI;
				sValor = SQLe.sValor;
				iBase=0;
				
				sSQL = "INSERT INTO indicadorResultado ( Empresa_Codigo, Indicador_Codigo, IndicadorResultado_Valor, IndicadorResultado_BaseCalculo, IndicadorResultado_DataInicio, IndicadorResultado_DataFim, IndicadorResultado_Importado, IndicadorResultado_Validado) VALUES ( " + Integer.parseInt(sDealerAux) + ", " + Integer.parseInt(sKPI) + ", " + sValor + ", " + iBase + ", " + sDataSQL + ", " + sDataSQL + ", 1.00 , 0.00 )";
				gravarArq.printf("%s\n", sSQL);
				
				iControle++;
			}
			
			fileNameOut.close();
			//System.out.printf("Quantidade de Pedidos Comerciais Lidos nos Sistemas:\n");
			
			FileWriter fileNameOutPROC = new FileWriter(sDiretorioComandoSQL + "PROC.txt");
			PrintWriter gravarArqPROC = new PrintWriter(fileNameOutPROC);

			sSQL="";
			sSQL = "DECLARE @Xml varchar(max)";
			gravarArqPROC.printf("%s\n", sSQL);
			
			for (RedeAtivaCSI RA : redeAtivaCSI){
		
				sDealerAux = RA.COD;
				//int iMes=12;
				
				for (int iMes=1; iMes<13; iMes++){
					sSQL = "execute PRC_ProcessarIndicador " + Integer.parseInt(sDealerAux) + ", 2016, " + iMes + ", 'VEN', @Xml out";
					gravarArqPROC.printf("%s\n", sSQL);
					sSQL = "execute PRC_ProcessarIndicador " + Integer.parseInt(sDealerAux) + ", 2016, " + iMes + ", 'SER', @Xml out";
					gravarArqPROC.printf("%s\n", sSQL);
				}
			}
			
			sSQL = "SELECT cast(@Xml as Xml)";
			gravarArqPROC.printf("%s\n", sSQL);

			fileNameOutPROC.close();
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
}
