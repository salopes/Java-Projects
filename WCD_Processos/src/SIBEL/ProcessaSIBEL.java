package SIBEL;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.net.SocketException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.JFrame;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


public class ProcessaSIBEL {

	public static void main(String[] args) throws SocketException, IOException{
		int iControle=0, iBase=0;
		String sDealer="", sDealerAux="", sSQL="", sDataSQL="", sMesSQL="";
		String tipoRegistro="", sHOt="", sTMedio="", sSIBELSErv="", sSIBEL="", sDataArq="", sKPI="", sValor="";

		String sNomeArquivoAux="", sNomeArquivoAux2="";
		String sDiretorioENTRADA="", sDiretorioSAIDA = "", sDiretorioRedeAtiva = "", sDiretorioComandoSQL = "";
		
		DecimalFormat df2 = new DecimalFormat("#.00");
		DecimalFormat df0 = new DecimalFormat("#");
		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		
		ArrayList<SIBEL_Estrut> sibel_Estrut = new ArrayList<SIBEL_Estrut>();
		ArrayList<SQLEstrutSIBEL> sqlEstrutSIBEL = new ArrayList<SQLEstrutSIBEL>();
		ArrayList<RedeAtivaSIBEL> redeAtivaSIBEL = new ArrayList<RedeAtivaSIBEL>();

		try{

			GregorianCalendar gc = new GregorianCalendar();
			Date dtINIFTP = gc.getTime();

			//System.out.printf("\n");
	 		System.out.printf("%s\n",dtINIFTP);
	 		System.out.printf("INICIO Preparação ambiente!\n");
	 		System.out.printf("\n");
	    	 sDiretorioENTRADA    = "D:\\WCD\\SIBEL\\Entrada\\";
	    	 sDiretorioSAIDA      = "D:\\WCD\\SIBEL\\Saida\\";
	    	 sDiretorioRedeAtiva  = "D:\\WCD\\SIBEL\\RedeAtiva\\";
	    	 sDiretorioComandoSQL = "D:\\WCD\\SIBEL\\SQL\\";
			
    	 
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
	 		System.out.printf("FIM Preparação ambiente!\n");
	 		System.out.printf("\n");
			
	 		Date dtINI = gc.getTime();
	 		System.out.printf("%s\n",dtINI);
	 		System.out.printf("INICIO Processamento!\n");

// Faz a leitura Rede Ativa			
	 		
			FileReader fileRedeAtiva = new FileReader(sDiretorioRedeAtiva + "RedeAtiva.txt");
			BufferedReader lerRedeAtiva = new BufferedReader(fileRedeAtiva);
			String linhaRedeAtiva = lerRedeAtiva.readLine(); // lê a primeira linha

			while (linhaRedeAtiva != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtivaSIBEL RATV = new RedeAtivaSIBEL(linhaRedeAtiva.substring(0, 5), linhaRedeAtiva.substring(6, 11));
				redeAtivaSIBEL.add(RATV);
				linhaRedeAtiva = lerRedeAtiva.readLine(); // lê da segunda até a última linha
			}
			fileRedeAtiva.close();
			
			File arquivosELO[];
			File diretorioELO = new File(sDiretorioENTRADA);
			arquivosELO = diretorioELO.listFiles();

			for(int w = 0; w < arquivosELO.length; w++){
				sNomeArquivoAux = arquivosELO[w].getName();
				sNomeArquivoAux2 = sDiretorioENTRADA + sNomeArquivoAux;

				System.out.printf("Planilhas: [%s]\n", sNomeArquivoAux2);

				FileInputStream fisPlanilha = null;
				fisPlanilha = new FileInputStream(sNomeArquivoAux2);
				
				XSSFWorkbook workbook = new XSSFWorkbook(fisPlanilha);
				XSSFSheet sheet = workbook.getSheetAt(0);
				
				Iterator<Row> rowIterator = sheet.iterator();
				int linha=1;
				boolean flag=true;
				
				while(rowIterator.hasNext() && flag){
					Row row =  rowIterator.next();
					
					//Faz a leitura das Celulas da linha
					Iterator<Cell> cellIterator = row.iterator();

					SIBEL_Estrut SIBEL = new SIBEL_Estrut();
					sibel_Estrut.add(SIBEL);
				
					linha++;
				
					while(cellIterator.hasNext()){
						Cell cell = cellIterator.next();
						
						if(!cell.toString().isEmpty()){

							switch (cell.getColumnIndex()) {
	                        case 0:
	                        	SIBEL.setsDealer(String.valueOf(cell.getNumericCellValue()));
	                              break;
	                        case 1:
	                        	SIBEL.setsAceitacao(String.valueOf(df2.format(cell.getNumericCellValue())));
	                              break;
	                        case 2:
	                        	SIBEL.setsSLA(String.valueOf(df2.format(cell.getNumericCellValue())));
	                              break;
	                        case 3:
	                        	SIBEL.setsTotal(String.valueOf(df0.format(cell.getNumericCellValue())));
	                              break;
	                        case 4:
	                           	SIBEL.setsData(sdf.format(cell.getDateCellValue()));
	                            break;
	                        }
							
						}	
						else{
							flag = false;
						}
					}
					
				}

				fisPlanilha.close();
				workbook.close();
		
			}

			FileWriter fileNameLixo = new FileWriter(sDiretorioComandoSQL + "LIXO.txt");
			PrintWriter gravarArqLixo = new PrintWriter(fileNameLixo);
			
			for (SIBEL_Estrut AR : sibel_Estrut){
				iControle++;
				for (RedeAtivaSIBEL RA : redeAtivaSIBEL){
					String sDealerSIB    = AR.sDealer;
					sDealerSIB = sDealerSIB.replace(".0", "");

					if (sDealerSIB.equals(RA.CODDEALER)){
						iControle++;
						
						String sAceitacao = AR.sAceitacao;
						String sSLA       = AR.sSLA;
						String sTotal     = AR.sTotal;

						sAceitacao = sAceitacao.replace(",", ".");
						sSLA       = sSLA.replace(",", ".");
						sTotal     = sTotal.replace(",", ".");
						
						SQLEstrutSIBEL sqlS1 = new SQLEstrutSIBEL( AR.sData, sDealerSIB, "101", sAceitacao, RA.COD); // ISPS-07
						sqlEstrutSIBEL.add(sqlS1);
						SQLEstrutSIBEL sqlS2 = new SQLEstrutSIBEL( AR.sData, sDealerSIB, "115", sSLA, RA.COD); // ISPS-08
						sqlEstrutSIBEL.add(sqlS2);
						SQLEstrutSIBEL sqlS3 = new SQLEstrutSIBEL( AR.sData, sDealerSIB, "67", sTotal, RA.COD); // ISPS-06
						sqlEstrutSIBEL.add(sqlS3);

					}
					else{
						gravarArqLixo.printf("%s\n", sDealerSIB);
					}
				}
			}

			fileNameLixo.close();
			
			FileWriter fileNameOut = new FileWriter(sDiretorioComandoSQL + "INSERT.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);

		
			iControle=0;

			sSQL="";
			for (SQLEstrutSIBEL SQLe : sqlEstrutSIBEL){
				sDataSQL = "CONVERT(DATETIME,'" + SQLe.sData + "',103)";
				//System.out.printf(" Dealer [%s] - Data [%s] - KPI [%s] - Valor [%s]\n", SQLe.sDealer, sDataSQL, SQLe.sKPI, SQLe.sValor);
				
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
			
			for (RedeAtivaSIBEL RA : redeAtivaSIBEL){
		
				sDealerAux = RA.COD;
				//int iMes=12;
				
				for (int iMes=1; iMes<13; iMes++){
					sSQL = "execute PRC_ProcessarIndicador " + Integer.parseInt(sDealerAux) + ", 2016, " + iMes + ", 'SER', @Xml out";
					gravarArqPROC.printf("%s\n", sSQL);
				}
			}
			
			sSQL = "SELECT cast(@Xml as Xml)";
			gravarArqPROC.printf("%s\n", sSQL);

			fileNameOutPROC.close();
			
	 		Date dtINIP = gc.getTime();
	 		System.out.printf("%s\n",dtINIP);
	 		System.out.printf("FIM Processamento!\n");
	 		
		
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
}
