package com.coaudit.elo;

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
import com.coaudit.elo.COAuditFrame;
import javax.swing.JFrame;
import org.apache.commons.net.ftp.FTPClient;

public class ProcessaArquivos {

	public static void main(String[] args) throws SocketException, IOException{
		int iTamLinha=0, iLeitura=0, iLeituraLixo=0, iRegControle=0, iPedidosOK=0, iPedComFind=0, iPedComNOFind=0, iPedComFind2=0, iPedComNOFind2=0, iRegDoneLidos=0, iRegDoneLidos2=0;
		int iTotPCLK=0, iDplPCLK=0;
		int iTotPCIMS=0, iDplPCIMS=0;
		int iTotPCIMS2=0, iDplPCIMS2=0;
		String sLinhaLeitura="", sGravLinha="", sSemanaProc="", sControle="";
		String sNomeArquivoFixo="", sNomeArquivoConnect="", sNomeArquivoAux="", sNomeArquivoAux2="", sNomeArquivoTRF="", sNomeArquivoOUT="", sNomeArquivoOUTLIXO="", sNomeArquivoDONE="", sNomeArquivoLINK="", sNomeArquivoLINKFND="", sNomeArquivoLINKNFD="", sNomeArquivoLINKFND2="", sNomeArquivoLINKNFD2="";
		String sDiretorioELOIMS="", sDiretorioELOTRF="", sDiretorioELOOUT="", sDiretorioELODONE="", sDiretorioLink="";
		String PedCom="", PedCom2 = "";
		char chLinha;
		Boolean bStatus = false, bStatus2 = false;

		ArrayList<PedidosLink> pedidosLink = new ArrayList<PedidosLink>();
		ArrayList<PedidoIMS> pedidoIMS = new ArrayList<PedidoIMS>();

		try{

			COAuditFrame coAuditFrame = new COAuditFrame();
			coAuditFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			coAuditFrame.setSize(960, 520);
			coAuditFrame.setVisible(true);
			coAuditFrame.setLocationRelativeTo(null);

	        //sSemanaProc="S33";
	        sSemanaProc = coAuditFrame.semanaField.getText();
			
			GregorianCalendar gc = new GregorianCalendar();
			Date dtINIFTP = gc.getTime();
			
			coAuditFrame.resultField.setSize(0, 250);
			coAuditFrame.resultField.append(dtINIFTP.toString());
			
			//System.out.printf("%s\n",dtINIFTP);
			//System.out.printf("INICIO FTP arquivos IMS!\n");
			
	//Efetua a conexao no FTP IMS 		
			 FTPClient ftp = new FTPClient();
	         ftp.connect( "172.28.3.31" );
	         //System.out.println(ftp.getReplyString());
	         ftp.login( "FTPICTC", "TCPICTC1" );
	         //System.out.println(ftp.getReplyString());
	         ftp.setBufferSize(1024 * 1024);
	         ftp.enterLocalPassiveMode();
	         ftp.setAutodetectUTF8(true);
	         ftp.enterLocalPassiveMode();

// Configura os Drirtorios de Trabalho         
	         
	    	 sDiretorioELOIMS = "D:\\SIG0500\\IMS\\";
	    	 sDiretorioELOTRF = "D:\\SIG0500\\TRF\\";
	    	 sDiretorioELOOUT = "D:\\SIG0500\\OUT\\";
	    	 sDiretorioELODONE = "D:\\SIG0500\\DONE\\";
	    	 sDiretorioLink = "D:\\SIG0500\\LINK\\";

	// Limpa diretorios de Trabalho
	    	 File DeldiretorioELO1 = new File(sDiretorioELOIMS);
	    	 if (DeldiretorioELO1.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO1.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }
	    	 
	    	 File DeldiretorioELO2 = new File(sDiretorioELOTRF);
	    	 if (DeldiretorioELO2.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO2.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }
	    	 File DeldiretorioELO3 = new File(sDiretorioELOOUT);
	    	 if (DeldiretorioELO3.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO3.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }
	    	 File DeldiretorioELO4 = new File(sDiretorioELODONE);
	    	 if (DeldiretorioELO4.isDirectory()) {
	    	 	File[] sun = DeldiretorioELO4.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }    	 

	// Efetua os FTP dos arquivos no Mainframe
				
	         for(int j=0;j<30;j++){
	        	 if(j==0){
		        	 sNomeArquivoFixo = sDiretorioELOIMS + "sig05000_" + j + ".txt"; 
		        	 sNomeArquivoConnect="'J409.C000.SIG05000.DCS(" + j + ")'";
	        	 }
	        	 else{
	        		 iRegControle = (j *-1);
		        	 sNomeArquivoFixo = sDiretorioELOIMS + "sig05000_" + j + ".txt"; 
		        	 sNomeArquivoConnect="'J409.C000.SIG05000.DCS(" + iRegControle + ")'";
	        		 
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
	 		//System.out.printf("%s\n",dtFIMFTP);
	 		//System.out.printf("FIM FTP arquivos IMS!\n");
	 		//System.out.printf("\n");
			
	 		Date dtINI = gc.getTime();
	 		//System.out.printf("%s\n",dtINI);
	 		//System.out.printf("INICIO Processamento!\n");
	 		coAuditFrame.resultField.append("\nINICIO Processamento!\n\n");

// Faz a leitura dos pedidos Existentes no Link.e.Entry (arguivo gerado) e guarda em estrutura			

			sNomeArquivoLINK = sDiretorioLink + "PedidosLink.txt";
			FileReader filePedidosLink = new FileReader(sNomeArquivoLINK);
			BufferedReader lerPedidosLink = new BufferedReader(filePedidosLink);
			String linhalerPedidosLink = lerPedidosLink.readLine(); // lê a primeira linha

			while (linhalerPedidosLink != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				PedidosLink PCLK = new PedidosLink( linhalerPedidosLink);
				
				if (pedidosLink.size() == 0){
					pedidosLink.add(PCLK);	
					iTotPCLK++;
				}
				else{
					iDplPCLK=0;
					for (int ivd = 0; ivd < pedidosLink.size(); ivd++) {
						if ( pedidosLink.get(ivd).getPedidoComercial().equals(linhalerPedidosLink)){
							iDplPCLK=1;
							break;
						}
					}
					
					if (iDplPCLK==0){
						pedidosLink.add(PCLK);	
						iTotPCLK++;
					}
				}
				linhalerPedidosLink = lerPedidosLink.readLine(); // lê da segunda até a última linha
			}
			filePedidosLink.close();
			Collections.sort(pedidosLink);
			//System.out.printf("iTotPCLK [%04d]\n", iTotPCLK);
			iTotPCLK=0;
			
			File arquivosELO[];
			File diretorioELO = new File(sDiretorioELOIMS);
			arquivosELO = diretorioELO.listFiles();
		
			for(int w = 0; w < arquivosELO.length; w++){
			   //leia arquivos[w];
				sNomeArquivoAux = arquivosELO[w].getName();
				sNomeArquivoAux2 = sDiretorioELOIMS + sNomeArquivoAux;
						
				FileReader fileNameIN1 = new FileReader(sNomeArquivoAux2);
				BufferedReader lerArq1 = new BufferedReader(fileNameIN1);
	
				sNomeArquivoTRF = sDiretorioELOTRF + "TRF_" + sNomeArquivoAux;
				//System.out.printf("Trabalhando arquivo %d [%s]\n", w, sNomeArquivoTRF);
				
				FileWriter fileNameOut1 = new FileWriter(sNomeArquivoTRF);
				PrintWriter gravarArq1 = new PrintWriter(fileNameOut1);
	
				String linha1 = lerArq1.readLine(); // lê a primeira linha
				
				while (linha1 != null) {
					//System.out.printf(" Linha While [%s]\n", linha1);
					gravarArq1.printf("%s", linha1);
	
					linha1 = lerArq1.readLine(); // lê da segunda até a última linha
				} 
				
				fileNameIN1.close();
				fileNameOut1.close();
	
				FileReader fileNameIN2 = new FileReader(sNomeArquivoTRF);
				BufferedReader lerArq2 = new BufferedReader(fileNameIN2);
				
				String linha2 = lerArq2.readLine(); // lê a primeira linha
				sLinhaLeitura = linha2.replaceAll(" ", "");
				iTamLinha = sLinhaLeitura.length();
				
				//System.out.printf("Data e Hora do Arquivo [%s]\n", sLinhaLeitura.substring(1, 16));

				sNomeArquivoOUT = sDiretorioELOOUT + "OUT_" + sLinhaLeitura.substring(1, 16) + "_" + sNomeArquivoAux;
				sNomeArquivoOUTLIXO = sDiretorioELOOUT + "OUT_LIXO_" + sLinhaLeitura.substring(1, 16) + "_" + sNomeArquivoAux;

				//System.out.printf("Trabalhando arquivo %d [%s]\n", w, sNomeArquivoOUT);

				FileWriter fileNameOut2 = new FileWriter(sNomeArquivoOUT);
				PrintWriter gravarArq2 = new PrintWriter(fileNameOut2);

				FileWriter fileNameOut3 = new FileWriter(sNomeArquivoOUTLIXO);
				PrintWriter gravarArq3 = new PrintWriter(fileNameOut3);
			
				for (int i=0; i<iTamLinha; i++){
					chLinha = sLinhaLeitura.charAt(i);
					if (chLinha == '@'){
						String tpReg = sLinhaLeitura.substring(i, i+4);
						if (!tpReg.equals("@FTT")){
							if(sControle.equals(sSemanaProc)){
								iLeitura++;
								//System.out.printf("Pedido Comercial %d [%s]\n", iLeitura, sLinhaLeitura.substring(i+32, i+41));
								//sGravLinha = sLinhaLeitura.substring(i, i+41);
								sGravLinha = sLinhaLeitura.substring(i+32, i+41);
								gravarArq2.printf("%s\n", sGravLinha);
								
								PedidoIMS PCIMS = new PedidoIMS( sGravLinha);
								if (pedidoIMS.size() == 0){
									pedidoIMS.add(PCIMS);	
									iTotPCIMS++;
								}
								else{
									iDplPCIMS=0;
									for (int ivd = 0; ivd < pedidoIMS.size(); ivd++) {
										if ( pedidoIMS.get(ivd).getPedidoComercial().equals(sGravLinha)){
											iDplPCIMS=1;
											//System.out.printf("IMS Duplicado [%s]\n", PCIMS);
											break;
										}
									}
									
									if (iDplPCIMS==0){
										pedidoIMS.add(PCIMS);	
										iTotPCIMS++;
									}
								}
							}
							else{
								iLeituraLixo++;
								//System.out.printf("Pedido Comercial %d [%s]\n", iLeitura, sLinhaLeitura.substring(i+32, i+41));
								sGravLinha = sLinhaLeitura.substring(i+32, i+41);
								gravarArq3.printf("SEMANA: [%s] - Pedido Comercial [%s]\n", sControle, sGravLinha);
							}
						}
						else{
							//System.out.printf("Semana [%s]\n", sLinhaLeitura.substring(i-3, i));
							sControle = sLinhaLeitura.substring(i-3, i);
						}
					}
					Collections.sort(pedidoIMS);
				}
				
				//System.out.printf("Arquivo [%s] , Pedido Comerciais [%04d],  Pedidos Lixo [%04d]\n", sLinhaLeitura.substring(1, 16) + "_" + sNomeArquivoAux, iLeitura, iLeituraLixo );
				iPedidosOK = iPedidosOK + iLeitura;
				iLeitura=0;
				iLeituraLixo=0;
				sControle="";
	
				fileNameIN2.close();
				fileNameOut2.close();
				fileNameOut3.close();
			}

			Collections.sort(pedidoIMS);
			//System.out.printf("iTotPCIMS [%04d]\n", iTotPCIMS);
			iTotPCIMS=0;

			//System.out.printf("Quantidade de Pedidos Comerciais Lidos nos Sistemas:\n");
			//System.out.printf("Qtd de Pedidos Comerciais existentes no LINK: [%04d]\n", pedidosLink.size() );
			//System.out.printf("Qtd de Pedidos Comerciais existentes no  IMS: [%04d]\n", pedidoIMS.size() );
			

			coAuditFrame.resultField.append("Quantidade de Pedidos Comerciais Lidos nos Sistemas:\n");
			coAuditFrame.resultField.append("Qtd de Pedidos Comerciais existentes no LINK: [%04d]" + pedidosLink.size());
			coAuditFrame.resultField.append("\nQtd de Pedidos Comerciais existentes no  IMS: [%04d]" + pedidoIMS.size() );
			
//Compara o arquivo do Link com o Arquivo do IMS

			sNomeArquivoDONE = sDiretorioELODONE + "DONE_ELO.txt";
			//System.out.printf("sNomeArquivoDONE [%s]\n", sNomeArquivoDONE);
	
			FileWriter fileNameOut4 = new FileWriter(sNomeArquivoDONE);
			PrintWriter gravarArq4 = new PrintWriter(fileNameOut4);

			for(int s=0; s<pedidoIMS.size(); s++)
			{
				PedidoIMS PCIMS2 = new PedidoIMS(pedidoIMS.get(s).PedidoComercial);

				if (pedidoIMS.size() == 0){
					gravarArq4.printf("%s\n", pedidoIMS.get(s).PedidoComercial);
					iTotPCIMS2++;
				}
				else{
					iDplPCIMS2=0;
					for (int ivd = 0; ivd < pedidoIMS.size(); ivd++) {
						if ( pedidoIMS.get(ivd).getPedidoComercial().equals(PCIMS2)){
							iDplPCIMS2=1;
							break;
						}
					}
					
					if (iDplPCIMS2==0){
						gravarArq4.printf("%s\n", pedidoIMS.get(s).PedidoComercial);
						iTotPCIMS2++;
					}
				}
			}

			sNomeArquivoLINKFND = sDiretorioELODONE + "DONE_ENCONTRADO_IMS.txt";	
			FileWriter fileNamePedComFIND = new FileWriter(sNomeArquivoLINKFND);
			PrintWriter gravarArqFIND = new PrintWriter(fileNamePedComFIND);

			sNomeArquivoLINKNFD = sDiretorioELODONE + "DONE_NAO_ENCONTRADO_IMS.txt";
			FileWriter fileNamePedComNOFIND = new FileWriter(sNomeArquivoLINKNFD);
			PrintWriter gravarArqNOFIND = new PrintWriter(fileNamePedComNOFIND);
			
			for (int h=0; h<pedidoIMS.size(); h++){
				iRegDoneLidos++;
				bStatus = false;
				PedCom = pedidoIMS.get(h).getPedidoComercial();
				for (int m=0; m<pedidosLink.size(); m++){
					if (pedidosLink.get(m).getPedidoComercial().equals(PedCom)){
						bStatus = true;
						break;
					}
				}

				if (bStatus){
					iPedComFind++;
					gravarArqFIND.printf("%s\n", PedCom);
					bStatus = false;
				}
				else{
					iPedComNOFind++;
					gravarArqNOFIND.printf("%s\n", PedCom);
					bStatus = false;
				}
 			} 

			//System.out.printf("Analise dos Pedidos:\n");
			//System.out.printf("Dos [%04d] Pedidos Comerciais existentes no IMS  : [%04d] foram ENCONTRADOS no LINK,  [%04d] NAO forma ENCONTRADOS no LINK\n", pedidoIMS.size(), iPedComFind, iPedComNOFind );
			
			coAuditFrame.resultField.append( "\nAnalise dos Pedidos:\n");
			coAuditFrame.resultField.append( "Dos [%04d] Pedidos Comerciais existentes no IMS  : " + pedidoIMS.size() + "]/n");
			coAuditFrame.resultField.append( "    foram ENCONTRADOS no LINK, [" +  iPedComFind + "] NAO forma ENCONTRADOS no LINK [" + iPedComNOFind + "]/n");
			
			fileNameOut4.close();
			fileNamePedComFIND.close();
			fileNamePedComNOFIND.close();

			sNomeArquivoLINKFND2 = sDiretorioELODONE + "DONE_ENCONTRADO_LINK.txt";
			FileWriter fileNamePedComFIND2 = new FileWriter(sNomeArquivoLINKFND2);
			PrintWriter gravarArqFIND2 = new PrintWriter(fileNamePedComFIND2);

			sNomeArquivoLINKNFD2 = sDiretorioELODONE + "DONE_NAO_ENCONTRADO_LINK.txt";
			FileWriter fileNamePedComNOFIND2 = new FileWriter(sNomeArquivoLINKNFD2);
			PrintWriter gravarArqNOFIND2 = new PrintWriter(fileNamePedComNOFIND2);
			
			
// Compara os pedidos do Link com os pedidos do IMS - Identifica os faltantes.
			for (int h2=0; h2<pedidosLink.size(); h2++){
				iRegDoneLidos2++;
				bStatus2 = false;
				PedCom2 = pedidosLink.get(h2).getPedidoComercial();
				for (int m2=0; m2<pedidoIMS.size(); m2++){
					if (pedidoIMS.get(m2).getPedidoComercial().equals(PedCom2)){
						bStatus2 = true;
						break;
					}
				}

				if (bStatus2){
					iPedComFind2++;
					gravarArqFIND2.printf("%s\n", PedCom2);
					bStatus2 = false;
				}
				else{
					iPedComNOFind2++;
					gravarArqNOFIND2.printf("%s\n", PedCom2);
					bStatus2 = false;
				}
			} 

			fileNamePedComFIND2.close();
			fileNamePedComNOFIND2.close();

			//System.out.printf("Dos [%04d] Pedidos Comerciais existentes no LINK : [%04d] foram ENCONTRADOS no IMS ,  [%04d] NAO forma ENCONTRADOS no IMS\n", pedidosLink.size(), iPedComFind2, iPedComNOFind2 );
			coAuditFrame.resultField.append( "Dos [%04d] Pedidos Comerciais existentes no LINK : " + pedidosLink.size() + "]/n");
			coAuditFrame.resultField.append( "    foram ENCONTRADOS no IMS , [" +  iPedComFind2 + "] NAO forma ENCONTRADOS no IMS  [" + iPedComNOFind2 + "]/n");
			
			if(pedidoIMS.size() > pedidosLink.size()){
				//System.out.printf("\n***** ATENCAO *****\n");
				//System.out.printf("Existe a diferença de [%04d] Pedidos Comerciais existentes no IMS\n", (pedidoIMS.size() - pedidosLink.size()) );
				//System.out.printf("Consultar os Pedidos do arquvio [%s] No Link.e.Entry Report\n", sNomeArquivoLINKNFD );
				//System.out.printf("Causas provaveis:\n");
				//System.out.printf("  1. Sua Lista de Pedidos do Link.e.Entry pode estar DESATUALIZADA\n");
				//System.out.printf("     1.1 Verifica o arquivo [%s]\n", sNomeArquivoLINK);
				//System.out.printf("  2. O Dealer do Pedido pode NAO ESTAR CONTIDO na lista e Dealers no Link.e.Entry - Nao aparecendo na lista de Pedidos\n");
				
				coAuditFrame.resultField.append("\n***** ATENCAO *****\n");
				coAuditFrame.resultField.append("Existe a diferença de [" + (pedidoIMS.size() - pedidosLink.size()) + "] Pedidos Comerciais existentes no IMS\n");
				coAuditFrame.resultField.append("Consultar os Pedidos do arquvio [" + sNomeArquivoLINKNFD + "] No Link.e.Entry Report\n");
				coAuditFrame.resultField.append("Causas provaveis:\n");
				coAuditFrame.resultField.append("  1. Sua Lista de Pedidos do Link.e.Entry pode estar DESATUALIZADA\n");
				coAuditFrame.resultField.append("     1.1 Verifica o arquivo [" + sNomeArquivoLINK + "]\n");
				coAuditFrame.resultField.append("  2. O Dealer do Pedido pode NAO ESTAR CONTIDO na lista e Dealers no Link.e.Entry\n");
				coAuditFrame.resultField.append("      - Nao aparecendo na lista de Pedidos\n");
			}
			else if(pedidosLink.size() > pedidoIMS.size()){
				//System.out.printf("\n***** ATENCAO *****\n");
				//System.out.printf("Existe a diferença de [%04d] Pedidos Comerciais existentes no LINK\n", (pedidoIMS.size() - pedidosLink.size()) );
				//System.out.printf("Consultar os Pedidos do arquvio [%s] No Link.e.Entry Report\n", sNomeArquivoLINKNFD2 );
				//System.out.printf("Causas provaveis:\n");
				//System.out.printf("  1. Podem ainda não ter sido Transmistidos - Verificar URGENTE com a ITALIA (Geoticket)\n");
				//System.out.printf("  2. Sua Lista de Pedidos do Link.e.Entry pode está DESATUALIZADA\n");
				//System.out.printf("     2.1 Verifica o arquivo [%s]\n", sNomeArquivoLINK);
				//System.out.printf("  3. O Dealer do Pedido pode NAO ESTA CONTIDO na lista e Dealers no Link.e.Entry - Nao aparecendo na lista de Pedidos\n");

				coAuditFrame.resultField.append("\n***** ATENCAO *****\n");
				coAuditFrame.resultField.append("Existe a diferença de [" + (pedidosLink.size() - pedidoIMS.size()) + "] Pedidos Comerciais existentes no IMS\n");
				coAuditFrame.resultField.append("Consultar os Pedidos do arquvio [" + sNomeArquivoLINKNFD2 + "] No Link.e.Entry Report\n");
				coAuditFrame.resultField.append("Causas provaveis:\n");
				coAuditFrame.resultField.append("  1. Podem ainda não ter sido Transmistidos - Verificar URGENTE com a ITALIA (Geoticket)\n");
				coAuditFrame.resultField.append("  2. Sua Lista de Pedidos do Link.e.Entry pode estar DESATUALIZADA\n");
				coAuditFrame.resultField.append("     2.1 Verifica o arquivo [" + sNomeArquivoLINK + "]\n");
				coAuditFrame.resultField.append("  3. O Dealer do Pedido pode NAO ESTAR CONTIDO na lista e Dealers no Link.e.Entry\n");
				coAuditFrame.resultField.append("      - Nao aparecendo na lista de Pedidos\n");
			}
			else{
				//System.out.printf("\n***** SUCESSO *****\n");
				//System.out.printf("A QTD de Pedidos Comerciais entre o LINK e o IMS estão IGUAIS\n");
				//System.out.printf("\n");
				
				coAuditFrame.resultField.append("\n***** SUCESSO *****\n");
				coAuditFrame.resultField.append("A QTD de Pedidos Comerciais entre o LINK e o IMS estão IGUAIS\n\n");
			}

			Date dtFIM= gc.getTime();
			//System.out.printf("%s\n",dtFIM);
			//System.out.printf("FIM Processamento!\n");
			
			coAuditFrame.resultField.append(dtFIM.toString());
			coAuditFrame.resultField.append("\nFIM Processamento!\n");
			
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}
}

