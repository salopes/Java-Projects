package CommercialOrders;

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
import java.util.Locale;

import org.apache.commons.net.ftp.FTPClient;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.forms.widgets.FormToolkit;

import com.ibm.icu.text.DateFormat;
import com.ibm.icu.text.SimpleDateFormat;

public class AuditoriaPedidos {
	public static Text txtSemana;
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	public static Text txtResult;
	private static Button btnAuditarPedidos;
	private static Button btnNovo;
	private static Button btnSair;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) throws SocketException, IOException{
		Display display = Display.getDefault();
		Shell coAuditFrame = new Shell();
		coAuditFrame.setSize(1055, 532);
		coAuditFrame.setText("Auditoria de Pedidos");
		coAuditFrame.setLayout(null);
				
		Composite composite = new Composite(coAuditFrame, SWT.BORDER);
		composite.setBounds(10, 10, 186, 92);
		
		Label lblSemanaDeProcessamento = new Label(composite, SWT.NONE);
		lblSemanaDeProcessamento.setBounds(10, 10, 151, 15);
		lblSemanaDeProcessamento.setText("Semana de Processamento:");
		
		txtSemana = new Text(composite, SWT.BORDER);
		txtSemana.setBounds(10, 31, 38, 21);
		txtSemana.setTextLimit(2);
		
		Label lblUseOFormato = new Label(composite, SWT.NONE);
		lblUseOFormato.setBounds(10, 60, 166, 15);
		lblUseOFormato.setText("Informe o N\u00FAmero da Semana");
		
		Composite composite_1 = new Composite(coAuditFrame, SWT.BORDER);
		composite_1.setBounds(207, 10, 822, 474);
		
		Label lblResultadoDoProcessamento = new Label(composite_1, SWT.NONE);
		lblResultadoDoProcessamento.setBounds(10, 10, 167, 15);
		lblResultadoDoProcessamento.setText("Resultado do Processamento:");
		
		txtResult = formToolkit.createText(composite_1, "New Text", SWT.MULTI);
		txtResult.setEditable(false);
		txtResult.setText("");
		txtResult.setBounds(10, 31, 792, 429);
		
		btnAuditarPedidos = formToolkit.createButton(coAuditFrame, "Auditar Pedidos", SWT.NONE);
		btnAuditarPedidos.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtResult.setText("");
				processaArquivos();
			}
		});
		btnAuditarPedidos.setBounds(10, 117, 111, 25);
		
		btnNovo = formToolkit.createButton(coAuditFrame, "Novo", SWT.NONE);
		btnNovo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtSemana.setText("");
				txtResult.setText("");
				txtSemana.setFocus();
			}
		});
		btnNovo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnNovo.setBounds(10, 148, 111, 25);
		
		btnSair = formToolkit.createButton(coAuditFrame, "Sair", SWT.NONE);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.exit(0);
			}
		});
		btnSair.setBounds(10, 459, 111, 25);
		
		Button btnSobre = formToolkit.createButton(coAuditFrame, "Sobre", SWT.NONE);
		btnSobre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtSemana.setText("");
				txtResult.setText("");
				txtResult.append("Auditor de Recebimento de Pedidos Comerciais\n");
				txtResult.append("Criado pela Equipe ICT Comercial\n");
				txtResult.append("\nObjetivo:\n");
				txtResult.append("Verificar se os Pedidos Comerciais criados no Link.e.Entry, foram enviados para o IMS,\n");
				txtResult.append("evitando as distorções de pedidos entre os dois sistemas\n");
				txtResult.append("\nversão: 1.0.0.1\n");
				txtResult.append("Data: 02/11/2016\n");
				txtResult.append("\n");
				txtResult.append("\n");
				txtResult.append("Copyright 2016 AARS Inc. Todos os direitos reservados.\n");
				txtSemana.setFocus();
			}
		});
		btnSobre.setBounds(10, 179, 111, 25);

		
		coAuditFrame.open();
		coAuditFrame.layout();
		while (!coAuditFrame.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

	protected static void processaArquivos() {
		int iTamLinha=0, iLeitura=0, iLeituraLixo=0, iRegControle=0, iPedidosOK=0, iPedComFind=0, iPedComNOFind=0, iPedComFind2=0, iPedComNOFind2=0, iRegDoneLidos=0, iRegDoneLidos2=0;
		int iTotPCLK=0, iDplPCLK=0;
		int iTotPCIMS=0, iDplPCIMS=0;
		int iTotPCIMS2=0, iDplPCIMS2=0;
		String sLinhaLeitura="", sGravLinha="", sSemanaProc="", sControle="";
		String sNomeArquivoFixo="", sNomeArquivoConnect="", sNomeArquivoAux="", sNomeArquivoAux2="", sNomeArquivoTRF="", sNomeArquivoOUT="", sNomeArquivoOUTLIXO="", sNomeArquivoDONE="", sNomeArquivoLINK="", sNomeArquivoLINKFND="", sNomeArquivoLINKNFD="", sNomeArquivoLINKFND2="", sNomeArquivoLINKNFD2="";
		String sDiretorioELOIMS="", sDiretorioELOTRF="", sDiretorioELOOUT="", sDiretorioELODONE="", sDiretorioLink="";
		String PedCom="", PedCom2 = "";
		char chLinha;
		Boolean bStatus = false, bStatus2 = false, bProcessa = false;
		long diferencaSegundos=0;
		SimpleDateFormat sdi= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

		ArrayList<PedidosLink> pedidosLink = new ArrayList<PedidosLink>();
		ArrayList<PedidoIMS> pedidoIMS = new ArrayList<PedidoIMS>();
		Date dt1 = new Date();

		try{
	        sSemanaProc = txtSemana.getText();
	        txtResult.setText("");
	        	        
			if (!sSemanaProc.matches("^[0-9]*$")){
				txtResult.append("Favor informar a Semana de forma numerica");
				txtSemana.setText("");
				txtSemana.setFocus();
			}
			else if(sSemanaProc.isEmpty()){
				txtResult.append("Favor informar a Semana de forma numerica");
				txtSemana.setText("");
				txtSemana.setFocus();
			}
			else{
				
				if(sSemanaProc.length()==1){
					sSemanaProc = "S0" + sSemanaProc;
				}
				else{
					sSemanaProc = "S" + sSemanaProc;
				}

				txtResult.setText("");
				
				txtResult.append(sdi.format(dt1));
				txtResult.append("\nINICIO Processamento: Semana [" + sSemanaProc + "]\n\n");
				
				 FTPClient ftp = new FTPClient();
		         ftp.connect( "172.28.3.31" ); 
		         ftp.login( "FTPICTC", "TCPICTC1" );
		         if(ftp.getReplyCode() == 230){
		        	 bProcessa = true;
		         }
		         else{
		        	 bProcessa = false;
		         }
		         
		         if(bProcessa){
		        	 
			         System.out.println(ftp.getReplyString());
			         
			         ftp.setBufferSize(1024 * 1024);
			         ftp.enterLocalPassiveMode();
			         ftp.setAutodetectUTF8(true);
			         ftp.enterLocalPassiveMode();
		
			    	 sDiretorioELOIMS = "D:\\SIG0500\\IMS\\";
			    	 sDiretorioELOTRF = "D:\\SIG0500\\TRF\\";
			    	 sDiretorioELOOUT = "D:\\SIG0500\\OUT\\";
			    	 sDiretorioELODONE = "D:\\SIG0500\\DONE\\";
			    	 sDiretorioLink = "D:\\SIG0500\\LINK\\";
		
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
		
				         FileOutputStream fos = new FileOutputStream( sNomeArquivoFixo );
				         
			        	 if (ftp.retrieveFile( sNomeArquivoConnect , fos )){
				               //System.out.println(ftp.getReplyString());
			        		 bProcessa= true;
				         }
				         else{
				               //System.out.println(ftp.getReplyString());
				        	 bProcessa = false;
				         }
			         }
			         
			         ftp.logout();
			         ftp.disconnect();
				         
			        if (bProcessa){
						sNomeArquivoLINK = sDiretorioLink + "PedidosLink.txt";
						FileReader filePedidosLink = new FileReader(sNomeArquivoLINK);
						BufferedReader lerPedidosLink = new BufferedReader(filePedidosLink);
						String linhalerPedidosLink = lerPedidosLink.readLine(); // lê a primeira linha
			
						while (linhalerPedidosLink != null) {
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
						iTotPCLK=0;
						
						File arquivosELO[];
						File diretorioELO = new File(sDiretorioELOIMS);
						arquivosELO = diretorioELO.listFiles();
					
						for(int w = 0; w < arquivosELO.length; w++){
							sNomeArquivoAux = arquivosELO[w].getName();
							sNomeArquivoAux2 = sDiretorioELOIMS + sNomeArquivoAux;
									
							FileReader fileNameIN1 = new FileReader(sNomeArquivoAux2);
							BufferedReader lerArq1 = new BufferedReader(fileNameIN1);
			
							sNomeArquivoTRF = sDiretorioELOTRF + "TRF_" + sNomeArquivoAux;
							
							FileWriter fileNameOut1 = new FileWriter(sNomeArquivoTRF);
							PrintWriter gravarArq1 = new PrintWriter(fileNameOut1);
			
							String linha1 = lerArq1.readLine(); // lê a primeira linha
							
							while (linha1 != null) {
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
			
							sNomeArquivoOUT = sDiretorioELOOUT + "OUT_" + sLinhaLeitura.substring(1, 16) + "_" + sNomeArquivoAux;
							sNomeArquivoOUTLIXO = sDiretorioELOOUT + "OUT_LIXO_" + sLinhaLeitura.substring(1, 16) + "_" + sNomeArquivoAux;
			
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
											sGravLinha = sLinhaLeitura.substring(i+32, i+41);
											gravarArq3.printf("SEMANA: [%s] - Pedido Comercial [%s]\n", sControle, sGravLinha);
										}
									}
									else{
										sControle = sLinhaLeitura.substring(i-3, i);
									}
								}
								Collections.sort(pedidoIMS);
							}
							
							iPedidosOK = iPedidosOK + iLeitura;
							iLeitura=0;
							iLeituraLixo=0;
							sControle="";
			
							fileNameIN2.close();
							fileNameOut2.close();
							fileNameOut3.close();
						}
			
						Collections.sort(pedidoIMS);
						iTotPCIMS=0;
			
						txtResult.append("Quantidade de Pedidos Comerciais Lidos:\n");
						txtResult.append("1. Qtd de Pedidos Comerciais existentes no LINK: [" + pedidosLink.size() + "]\n");
						txtResult.append("2. Qtd de Pedidos Comerciais existentes no  IMS: [" + pedidoIMS.size() + "]\n" );
						
						sNomeArquivoDONE = sDiretorioELODONE + "DONE_ELO.txt";
			
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
			
						txtResult.append("\nAnalise dos Pedidos:\n");
						txtResult.append("Dos [" + pedidoIMS.size() + "] Pedidos Comerciais existentes no IMS :\n");
						txtResult.append("    Encontrei [" +  iPedComFind + "] pedidos gerados no LINK\n");
						if(iPedComNOFind>0){
							txtResult.append("    ATENCAO: Existem [" +  iPedComNOFind + "] pedidos no IMS, que não estão no LINK\n");	
						}
						
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
			
						txtResult.append("\nDos [" + pedidosLink.size() + "] Pedidos Comerciais existentes no LINK:\n");
						txtResult.append("    Encontrei [" +  iPedComFind2 + "] pedidos que foram enviados para o IMS\n");
						if(iPedComNOFind2>0){
							txtResult.append("    ATENCAO: Existem [" +  iPedComNOFind2 + "] que formam enviados do LINK para o IMS, mas NÃO CHEGARAM!\n");
						}
						
						if(pedidoIMS.size() > pedidosLink.size()){
							txtResult.append("\n***** ATENCAO *****\n");
							txtResult.append("Verifique a diferença de [" + (pedidoIMS.size() - pedidosLink.size()) + "] Pedidos Comerciais existentes no IMS e, que NÃO EXISTENTES no LINK\n");
							txtResult.append("  1. Esses Pedidos estão listados no arquvio [" + sNomeArquivoLINKNFD + "]\n");
							txtResult.append("  2. Verifique no Link.e.Entry Report se os pedidos estão criados\n");
							txtResult.append("Causas provaveis:\n");
							txtResult.append("  1. Sua Lista de Pedidos do Link.e.Entry pode estar DESATUALIZADA\n");
							txtResult.append("     1.1 Verifique o arquivo [" + sNomeArquivoLINK + "]\n");
							txtResult.append("  2. O Dealer do Pedido pode NAO ESTAR CONTIDO na lista e Dealers no Link.e.Entry - Nao aparecendo na lista de Pedidos\n\n");
						}
						else if(pedidosLink.size() > pedidoIMS.size()){
							txtResult.append("\n***** ATENCAO *****\n");
							txtResult.append("Existe a diferença de [" + (pedidosLink.size() - pedidoIMS.size()) + "] Pedidos Comerciais existentes no LINK que NÃO CHEGARAM no IMS\n");
							txtResult.append("Esses Pedidos estão listados no arquvio [" + sNomeArquivoLINKNFD2 + "]. Verifique no Link.e.Entry Report se os pedidos estão criados\n");
							txtResult.append("Causas provaveis:\n");
							txtResult.append("  1. Podem ainda não ter sido Transmistidos - Verificar URGENTE com a ITALIA (Geoticket)\n\n");
						}
						else{
							txtResult.append("\n***** SUCESSO *****\n");
							txtResult.append("A QTD de Pedidos Comerciais entre o LINK e o IMS estão IGUAIS\n\n");
						}
						SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date dt2 = new Date();
					
						txtResult.append(sdf.format(dt2));
						txtResult.append("\nFIM Processamento: Semana [" + sSemanaProc + "]\n\n");

			        }
			        else{
						SimpleDateFormat sdf1= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date dt2 = new Date();
						
						txtResult.append("\n***** ERRO *****\n");
						txtResult.append("Não foi possivel efetuar o FTP dos arquivos no IMS\n");
						txtResult.append("INFORMAR ao ICT\n\n");
						
						txtResult.append(sdf1.format(dt2));
						txtResult.append("\nFIM Processamento: Semana [" + sSemanaProc + "]\n\n");
			        }
		         }
		         else{
						SimpleDateFormat sdf2= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
						Date dt2 = new Date();
						
						txtResult.append("\n***** ERRO *****\n");
						txtResult.append("Fala de conexão no Servidor FTP: USUARIO/SENHA inválidos\n");
						txtResult.append("INFORMAR ao ICT\n\n");
						
						txtResult.append(sdf2.format(dt2));
						txtResult.append("\nFIM Processamento: Semana [" + sSemanaProc + "]\n\n");
		         }
		         SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		         Date dt2 = new Date();
			        
			     diferencaSegundos = (dt2.getTime() - dt1.getTime()) / (1000);
				 txtResult.append("Tempo de Processamento: [" + diferencaSegundos + "] segundos");
	         }
		         			
		} catch (IOException  e) {
			e.getMessage();
			SimpleDateFormat sde= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			Date dte = new Date();

			txtResult.append("Erro no processamento: [" + e.getMessage() + "]\n");
			txtResult.append("INFORMAR ao ICT\n\n");
		
			txtResult.append(sde.format(dte));
			txtResult.append("\nFIM Processamento: Semana [" + sSemanaProc + "]\n\n");
	        diferencaSegundos = (dte.getTime() - dt1.getTime()) / (1000);
			txtResult.append("Tempo de Processamento: [" + diferencaSegundos + "] segundos");
		}
	}
}

