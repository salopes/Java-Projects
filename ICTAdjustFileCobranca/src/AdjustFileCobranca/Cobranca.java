package AdjustFileCobranca;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
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

import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.nio.channels.FileChannel;

import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.MultiPartEmail;
import org.apache.commons.mail.SimpleEmail;
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

import AdjustFileCobranca.DividasAutos;
import AdjustFileCobranca.DividasPecas;
import AdjustFileCobranca.EntregaLiberada;
import AdjustFileCobranca.HeaderCobranca;
import AdjustFileCobranca.ProcessaDA;
import AdjustFileCobranca.ProcessaDP;
import AdjustFileCobranca.ProcessaEL;
import AdjustFileCobranca.ProcessaVD;
import AdjustFileCobranca.ProcessaVG;
import AdjustFileCobranca.ProcessaVI;
import AdjustFileCobranca.ProcessaVL;
import AdjustFileCobranca.ProcessaVP;
import AdjustFileCobranca.RedeAtiva;
import AdjustFileCobranca.TraillerCobranca;
import AdjustFileCobranca.VeiculosPendentes;
import AdjustFileCobranca.VendasDiretas;
import AdjustFileCobranca.VendasGoverno;
import AdjustFileCobranca.VendasInternet;
import AdjustFileCobranca.VendasLeasing;
import org.eclipse.wb.swt.SWTResourceManager;

public class Cobranca {
	private static final FormToolkit formToolkit = new FormToolkit(Display.getDefault());
	public static Text txtResult;
	private static Button btnProcessarArquivo;
	private static Button btnSair;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) throws SocketException, IOException{
		Display display = Display.getDefault();
		Shell coAuditFrame = new Shell();
		coAuditFrame.setSize(854, 532);
		coAuditFrame.setText("Processamento Arquivo COBRANCA");
		coAuditFrame.setLayout(null);
		
		Composite composite_1 = new Composite(coAuditFrame, SWT.BORDER);
		composite_1.setBounds(238, 10, 590, 474);
		
		Label lblResultadoDoProcessamento = new Label(composite_1, SWT.NONE);
		lblResultadoDoProcessamento.setBounds(10, 10, 167, 15);
		lblResultadoDoProcessamento.setText("Resultado do Processamento:");
		
		txtResult = formToolkit.createText(composite_1, "New Text", SWT.MULTI);
		txtResult.setFont(SWTResourceManager.getFont("Courier New", 9, SWT.NORMAL));
		txtResult.setEditable(false);
		txtResult.setText("");
		txtResult.setBounds(10, 27, 566, 433);
		
		btnProcessarArquivo = formToolkit.createButton(coAuditFrame, "Processar Arquivo", SWT.NONE);
		btnProcessarArquivo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtResult.setText("");
				processaCobranca();
			}
		});
		btnProcessarArquivo.setBounds(10, 10, 222, 25);
		
		btnSair = formToolkit.createButton(coAuditFrame, "Sair", SWT.NONE);
		btnSair.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				System.exit(0);
			}
		});
		btnSair.setBounds(10, 459, 222, 25);
		
		Button btnSobre = formToolkit.createButton(coAuditFrame, "Sobre", SWT.NONE);
		btnSobre.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
			}
		});
		btnSobre.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseDown(MouseEvent e) {
				txtResult.setText("");
				txtResult.append("Validador de Arquivo de Cobranca\n");
				txtResult.append("Criado pela Equipe ICT Comercial\n");
				txtResult.append("\nObjetivo:\n");
				txtResult.append("Verificar se arquivo de COBRANCA está com os dados persistente,\n");
				txtResult.append("evitando a interrupção do processamento do mesmo no Link.e.Entry\n");
				txtResult.append("\nversão: 1.0.0.0\n");
				txtResult.append("Data: 05/11/2016\n");
				txtResult.append("\n");
				txtResult.append("\n");
				txtResult.append("Copyright 2016 AARS Inc. Todos os direitos reservados.\n");
			}
		});
		btnSobre.setBounds(10, 41, 222, 25);

		
		coAuditFrame.open();
		coAuditFrame.layout();
		while (!coAuditFrame.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

// Procssa Cobranca	
	protected static void processaCobranca() {
		int iTotRegistros=0;
		int iTotLidos=0;
		int iTotLixos=0;
		int iDpl=0;
		int iTotDpl=0;
		int iDplVD=0;
		int iDplVI=0;
		int iDplVG=0;
		int iDplVP=0;
		int iDplDP=0;
		int iDplDA=0;
		int iDplEL=0;
		int iDplVL=0;
		int iErrValid=0;
		int iErrCodDealer=0;
		long diferencaSegundos=0, diferencaSegundosGeral=0;
		String sDirENTRADA="", sDirSAIDA="", sDirREDEATIVA="", sDirLIXO="";
		String sRedeAtiva="", sTxtArq="", sTxtArq1="", sTxtArq2="", sTxtLixo="";

		ArrayList<RedeAtiva> redeAtiva = new ArrayList<RedeAtiva>();
		ArrayList<HeaderCobranca> headerCobranca = new ArrayList<HeaderCobranca>();
		ArrayList<VendasDiretas> vendasDiretas = new ArrayList<VendasDiretas>();
		ArrayList<VendasInternet> vendasInternet = new ArrayList<VendasInternet>();
		ArrayList<VendasGoverno> vendasGoverno = new ArrayList<VendasGoverno>();
		ArrayList<VeiculosPendentes> veiculosPendentes = new ArrayList<VeiculosPendentes>();
		ArrayList<DividasPecas> dividasPecas = new ArrayList<DividasPecas>();
		ArrayList<DividasAutos> dividasAutos = new ArrayList<DividasAutos>();
		ArrayList<EntregaLiberada> entregaLiberada = new ArrayList<EntregaLiberada>();
		ArrayList<VendasLeasing> vendasLeasing = new ArrayList<VendasLeasing>();
		ArrayList<TraillerCobranca> traillerCobranca = new ArrayList<TraillerCobranca>();
			
		try{

			Date dtG1 = new Date();
			SimpleDateFormat sdG= new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
			SimpleDateFormat sdf= new SimpleDateFormat("yyyyMMdd_HHmmss");
			
			sDirENTRADA = "D:\\PROCESSACOBRANCA\\ENTRADA\\";
	    	sDirSAIDA = "D:\\PROCESSACOBRANCA\\SAIDA\\";
	    	sDirREDEATIVA = "D:\\PROCESSACOBRANCA\\REDEATIVA\\";
	    	sDirLIXO = "D:\\PROCESSACOBRANCA\\LIXO\\";
			
	    	sRedeAtiva = sDirREDEATIVA + "REDEATIVA.txt";
			
			FileReader fileRedeAtivaTam = new FileReader(sRedeAtiva);
			BufferedReader lerRedeAtivaTam = new BufferedReader(fileRedeAtivaTam);
			String linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê a primeira linha

			while (linhaRedeAtivaTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtiva RATV = new RedeAtiva( linhaRedeAtivaTam);
				
				redeAtiva.add(RATV);
				linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // lê da segunda até a última linha
			}
			fileRedeAtivaTam.close();

			txtResult.setText("");
			txtResult.append("\n-------------- INICIO DE PROCESSAMENTO --------------");
			txtResult.append("\n--------------   " + sdG.format(dtG1) + "   --------------\n");
			
			File fileEntrada = new File(sDirENTRADA);
			
			if (fileEntrada.exists() && fileEntrada.isDirectory()) {
				String[] nomesArquivoEntrada = fileEntrada.list();

				for (int i = 0; i < nomesArquivoEntrada.length; i++) {
					sTxtArq = nomesArquivoEntrada[i];
					sTxtArq1 = "Aux1_" + sTxtArq;
					sTxtArq2 = sdf.format(dtG1) + "_CORRIGIDO_" + sTxtArq;
					sTxtLixo = sdf.format(dtG1) + "_LIXO_" + sTxtArq;

					Date dt1 = new Date();
					txtResult.append("\nProcessamento do Arquivo: [" +  sTxtArq + "]\n\n");
		
					File arquivoOrigem = new File(sDirENTRADA + sTxtArq);
					File arquivoSaida = new File(sDirSAIDA + "PROCESSADOS\\" + sdf.format(dtG1) + "_" + sTxtArq);
					copyFile(arquivoOrigem, arquivoSaida);

					
					FileReader fileNameTam = new FileReader(sDirENTRADA + sTxtArq);
					BufferedReader lerArqTam = new BufferedReader(fileNameTam);
					String linhaTam = lerArqTam.readLine(); // lê a primeira linha
		
					FileWriter fileNameTamOut = new FileWriter(sDirENTRADA + sTxtArq1);
					PrintWriter gravarArqTam = new PrintWriter(fileNameTamOut);

					while (linhaTam != null) {
						//System.out.printf("%s\n", linhaTam);
						gravarArqTam.printf("%-301s\n", linhaTam);
						linhaTam = lerArqTam.readLine(); // lê da segunda até a última linha
					}
					fileNameTam.close();
					fileNameTamOut.close();

					FileWriter fileNameLixo = new FileWriter(sDirLIXO + sTxtLixo);
					PrintWriter gravarArqLixo = new PrintWriter(fileNameLixo);

					FileReader fileName = new FileReader(sDirENTRADA + sTxtArq1);
					BufferedReader lerArq = new BufferedReader(fileName);
					String linha = lerArq.readLine(); // lê a primeira linha
			
					while (linha != null) {
						//System.out.printf("%s\n", linha);
				
						String tpReg = linha.substring(0, 19);
						//System.out.printf("[%s]\n", tpReg);
						
						String tipoRegistro = tpReg.trim();
						//System.out.printf("[%s]\n", tipoRegistro);
								
						if (tipoRegistro.equals("Verzeichnis:  /usr/") || tipoRegistro.equals("Nome:         IF_BR") || tipoRegistro.equals("-------------------")){
							gravarArqLixo.printf("LIXO: %-301s\n", linha);
							iErrCodDealer++;
							iTotLixos++;
						}
						else if (tipoRegistro.equals("HEADER")){
							HeaderCobranca hD = new HeaderCobranca( linha.substring(0, 20)
		                                                          , linha.substring(20, 34)
		                                                          , linha.substring(34, 301));
							headerCobranca.add(hD);
						}
						else if (tipoRegistro.equals("TRAILLER")){
							String sTotRegistros = String.format("%08d", iTotRegistros);
							//String sTotRegistros = String.format("%08d", vendasDiretas.size());
							TraillerCobranca tL = new TraillerCobranca( linha.substring(0, 20)
		                                                              , sTotRegistros
		                                                              , linha.substring(28, 301));
							traillerCobranca.add(tL);
		
						}
						else{
								Boolean bStatus = false;
								String codDealer = linha.substring(20, 28);
								for (int k=0; k<redeAtiva.size(); k++){
									if (redeAtiva.get(k).getCODDEALER().equals(codDealer)){
										bStatus = true;
										break;
									}
								}
								
								if (bStatus){
									String chaveSort = "";
									if (tipoRegistro.equals("VENDAS DIRETAS")){
										ProcessaVD processaVD = new ProcessaVD(linha);
										if(processaVD.iRetVd==1){
											chaveSort = (linha.substring(62, 70) + linha.substring(102, 111) + linha.substring(20, 28));
											VendasDiretas vD = new VendasDiretas( linha.substring(0, 20)
						                                                        , linha.substring(20, 28)
							                                                    , linha.substring(28, 30)
							                                                    , linha.substring(30, 60)
							                                                    , linha.substring(60, 62)
							                                                    , linha.substring(62, 70)
							                                                    , linha.substring(70, 100)
							                                                    , linha.substring(100, 102)
							                                                    , linha.substring(102, 111)
							                                                    , linha.substring(111, 122)
							                                                    , linha.substring(122, 136)
							                                                    , linha.substring(136, 144)
							                                                    , linha.substring(144, 152)
							                                                    , linha.substring(152, 160)
							                                                    , linha.substring(160, 168)
							                                                    , linha.substring(168, 173)
							                                                    , linha.substring(173, 181)
							                                                    , linha.substring(181, 189)
							                                                    , linha.substring(189, 219)
							                                                    , linha.substring(219, 234)
							                                                    , linha.substring(234, 242)
							                                                    , linha.substring(242, 244)
							                                                    , linha.substring(244, 274)
							                                                    , linha.substring(274, 288)
							                                                    , linha.substring(288, 289)
							                                                    , linha.substring(289, 301)
							                                                    , (linha.substring(62, 70) + linha.substring(102, 111) + linha.substring(20, 28)));
			
											if (vendasDiretas.size() == 0){
												vendasDiretas.add(vD);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ivd = 0; ivd < vendasDiretas.size(); ivd++) {
													if ( vendasDiretas.get(ivd).getCHAVESORT().equals(chaveSort)){
														iDplVD++;
														iDpl=1;
														break;
													}
												}
												
												if (iDpl==0){
														vendasDiretas.add(vD);
														iTotRegistros++;
												}
												else{
													//System.out.printf("Duplicado: [%s]\n", linha);
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}											
										}
										else{
											//System.out.printf("%s\n", processaVD.sRetVd);
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaVD.sRetVd); 
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("VENDAS INTERNET")){
										ProcessaVI processaVI = new ProcessaVI(linha);
										if(processaVI.iRetVd==1){
											chaveSort = (linha.substring(62, 70) + linha.substring(109, 118) + linha.substring(20, 28));
											VendasInternet vI = new VendasInternet( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,60)
													, linha.substring(60,62)
													, linha.substring(62,70)
													, linha.substring(70,100)
													, linha.substring(100,109)
													, linha.substring(109,118)
													, linha.substring(118,129)
													, linha.substring(129,143)
													, linha.substring(143,151)
													, linha.substring(151,159)
													, linha.substring(159,167)
													, linha.substring(167,175)
													, linha.substring(175,180)
													, linha.substring(180,188)
													, linha.substring(188,196)
													, linha.substring(196,226)
													, linha.substring(226,241)
													, linha.substring(241,255)
													, linha.substring(255,256)
													, linha.substring(256,301)
													, (linha.substring(62, 70) + linha.substring(109, 118) + linha.substring(20, 28)));
				
											if (vendasInternet.size() == 0){
												vendasInternet.add(vI);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ivi = 0; ivi < vendasDiretas.size(); ivi++) {
													if ( vendasInternet.get(ivi).getCHAVESORT().equals(chaveSort)){
														iDplVI++;
														iDpl=1;
														break;
													}
												}
												
												if (iDpl==0){
													vendasInternet.add(vI);
													iTotRegistros++;
												}
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
			
											}											
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaVI.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("VENDAS GOVERNO")){
										ProcessaVG processaVG = new ProcessaVG(linha);
										if(processaVG.iRetVd==1){
											chaveSort = (linha.substring(62, 70) + linha.substring(102, 111) + linha.substring(20, 28));
											VendasGoverno vG = new VendasGoverno( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,60)
													, linha.substring(60,62)
													, linha.substring(62,70)
													, linha.substring(70,100)
													, linha.substring(100,102)
													, linha.substring(102,111)
													, linha.substring(111,122)
													, linha.substring(122,136)
													, linha.substring(136,144)
													, linha.substring(144,152)
													, linha.substring(152,160)
													, linha.substring(160,168)
													, linha.substring(168,183)
													, linha.substring(183,191)
													, linha.substring(191,193)
													, linha.substring(193,223)
													, linha.substring(223,224)
													, linha.substring(224,301)
													, (linha.substring(62, 70) + linha.substring(102, 111) + linha.substring(20, 28)));
			
											if (vendasGoverno.size() == 0){
												vendasGoverno.add(vG);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ivg = 0; ivg < vendasGoverno.size(); ivg++) {
													if ( vendasGoverno.get(ivg).getCHAVESORT().equals(chaveSort)){
														iDplVG++;
														iDpl=1;
														break;
													}
												}
			
												if (iDpl==0){
													vendasGoverno.add(vG);
													iTotRegistros++;
												}
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}											
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n",  linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaVG.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("VEICULOS PENDENTES")){
										ProcessaVP processaVP = new ProcessaVP(linha);
										if(processaVP.iRetVd==1){
											chaveSort = (linha.substring(62, 70) + linha.substring(100, 109) + linha.substring(20, 28));
											VeiculosPendentes vP = new VeiculosPendentes( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,60)
													, linha.substring(60,62)
													, linha.substring(62,70)
													, linha.substring(70,100)
													, linha.substring(100,109)
													, linha.substring(109,120)
													, linha.substring(120,134)
													, linha.substring(134,142)
													, linha.substring(142,301)
													, (linha.substring(62, 70) + linha.substring(100, 109) + linha.substring(20, 28)));
			
											if (veiculosPendentes.size() == 0){
												veiculosPendentes.add(vP);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ivp = 0; ivp < veiculosPendentes.size(); ivp++) {
													if ( veiculosPendentes.get(ivp).getCHAVESORT().equals(chaveSort)){
														iDplVP++;
														iDpl=1;
														break;
													}
												}
											}
											if (iDpl==0){
												veiculosPendentes.add(vP);
												iTotRegistros++;
											}
											else{
												gravarArqLixo.printf("DPL: %-301s\n", linha);
												iTotLixos++;
											}
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaVP.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("DIVIDAS DE PECAS")){
										ProcessaDP processaDP = new ProcessaDP(linha);
										if(processaDP.iRetVd==1){
											chaveSort = (linha.substring(20, 28) + linha.substring(60, 69));
											DividasPecas dP = new DividasPecas( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,60)
													, linha.substring(60,69)
													, linha.substring(69,77)
													, linha.substring(77,85)
													, linha.substring(85,99)
													, linha.substring(99,102)
													, linha.substring(102,105)
													, linha.substring(105,301)
													, (linha.substring(20, 28) + linha.substring(60, 69) + linha.substring(85,99)));
				
											if (dividasPecas.size() == 0){
												dividasPecas.add(dP);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int idp = 0; idp < dividasPecas.size(); idp++) {
													if ( dividasPecas.get(idp).getCHAVESORT().equals(chaveSort)){
														iDplDP++;
														iDpl=1;
														break;
													}
												}
												
												if (iDpl==0){
													dividasPecas.add(dP);
													iTotRegistros++;
												}
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}											
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaDP.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("DIVIDAS DE AUTOS")){
										ProcessaDA processaDA = new ProcessaDA(linha);
										if(processaDA.iRetVd==1){
											chaveSort = (linha.substring(20, 28) + linha.substring(60, 69));
											DividasAutos dA = new DividasAutos( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,60)
													, linha.substring(60,69)
													, linha.substring(69,80)
													, linha.substring(80,94)
													, linha.substring(94,102)
													, linha.substring(102,110)
													, linha.substring(110,118)
													, linha.substring(118,126)
													, linha.substring(126,301)
													, (linha.substring(20, 28) + linha.substring(60, 69) + linha.substring(80,94)));
			
											if (dividasAutos.size() == 0){
												dividasAutos.add(dA);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ida = 0; ida < dividasAutos.size(); ida++) {
													if ( dividasAutos.get(ida).getCHAVESORT().equals(chaveSort)){
														iDplDA++;
														iDpl=1;
														break;
													}
												}
												
												if (iDpl==0){
													dividasAutos.add(dA);
													iTotRegistros++;
												}
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}											
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaDA.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("ENTREGA LIBERADA")){
										ProcessaEL processaEL = new ProcessaEL(linha);
										if(processaEL.iRetVd==1){
											chaveSort = (linha.substring(60, 68)+ linha.substring(100, 109) + linha.substring(20, 28));
											EntregaLiberada eL = new EntregaLiberada( linha.substring(0, 20)
												, linha.substring(20,28)
												, linha.substring(28,30)
												, linha.substring(30,60)
												, linha.substring(60,68)
												, linha.substring(68,70)
												, linha.substring(70,100)
												, linha.substring(100,109)
												, linha.substring(109,120)
												, linha.substring(120,134)
												, linha.substring(134,142)
												, linha.substring(142,150)
												, linha.substring(150,158)
												, linha.substring(158,301)
												, (linha.substring(60, 68)+ linha.substring(100, 109) + linha.substring(20, 28)));
			
											if (entregaLiberada.size() == 0){
												entregaLiberada.add(eL);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int iel = 0; iel < entregaLiberada.size(); iel++) {
													if ( entregaLiberada.get(iel).getCHAVESORT().equals(chaveSort)){
														iDplEL++;
														iDpl=1;
														break;
													}
												}
												
												if (iDpl==0){
													entregaLiberada.add(eL);
													iTotRegistros++;
												}								
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}											
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaEL.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
									else if (tipoRegistro.equals("VENDAS LEASING")){
										ProcessaVL processaVL = new ProcessaVL(linha);
										if(processaVL.iRetVd==1){
											chaveSort = (linha.substring(20, 28) + linha.substring(30, 39));
											VendasLeasing vL = new VendasLeasing( linha.substring(0, 20)
													, linha.substring(20,28)
													, linha.substring(28,30)
													, linha.substring(30,39)
													, linha.substring(39,50)
													, linha.substring(50,58)
													, linha.substring(58,72)
													, linha.substring(72,301)
													, (linha.substring(20, 28) + linha.substring(30, 39)));
											if (vendasLeasing.size() == 0){
												vendasLeasing.add(vL);	
												iTotRegistros++;
											}
											else{
												iDpl=0;
												for (int ivl = 0; ivl < vendasLeasing.size(); ivl++) {
													if ( vendasLeasing.get(ivl).getCHAVESORT().equals(chaveSort)){
														iDplVL++;
														iDpl=1;
														break;
													}
												}
												if (iDpl==0){
													vendasLeasing.add(vL);
													iTotRegistros++;
												}
												else{
													gravarArqLixo.printf("DPL: %-301s\n", linha);
													iTotLixos++;
												}
											}
										}
										else{
											gravarArqLixo.printf("PST: %-301s\n", linha);
											gravarArqLixo.printf("ERRO PST: %s\n", processaVL.sRetVd);
											iErrValid++;
											iTotLixos++;
										}
									}
								}
								else{
									gravarArqLixo.printf("DLR FORA REDE ATIVA: %-301s\n", linha);
									iErrCodDealer++;
									iTotLixos++;
								}
							}
			
							linha = lerArq.readLine(); // lê da segunda até a última linha
						} 
			
						iTotLidos = (headerCobranca.size() + traillerCobranca.size() + iTotRegistros);
						iTotDpl = iDplVD + iDplVI + iDplVG + iDplVP + iDplDP + iDplDA + iDplEL + iDplVL;
/*
						System.out.printf("Total de Registros %-23s: [%05d]\n", "HEADER"                 , headerCobranca.size());
						System.out.printf("Total de Registros %-23s: [%05d]\n", "VENDAS DIRETAS"         , vendasDiretas.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP VENDAS DIRETAS"     , iDplVD);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "VENDAS INTERNET"        , vendasInternet.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP VENDAS INTERNET"    , iDplVI);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "VENDAS GOVERNO"         , vendasGoverno.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP VENDAS GOVERNO"     , iDplVG);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "VEICULOS PENDENTES"     , veiculosPendentes.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP VEICULOS PENDENTES" , iDplVP);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "DIVIDAS DE PECAS"       , dividasPecas.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP DIVIDAS DE PECAS"   , iDplDP);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "DIVIDAS DE AUTOS"       , dividasAutos.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP DIVIDAS DE AUTOS"   , iDplDA);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "ENTREGA LIBERADA"       , entregaLiberada.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP ENTREGA LIBERADA"   , iDplEL);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "VENDAS LEASING"         , vendasLeasing.size());
						System.out.printf("                   %-23s: [%05d]\n", "DLP VENDAS LEASING"     , iDplVL);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "TRAILLER"               , traillerCobranca.size());
						System.out.printf("Total de Registros %-23s: [%05d]\n", "REGISTROS LOGICOS"      , iTotRegistros);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "REGISTROS FISICOS"      , iTotLidos);
						System.out.printf("Total de Registros %-23s: [%05d]\n", "REGISTROS LIXO"         , iTotLixos);
						System.out.printf("                   %-23s: [%05d]\n", "LIXO Duplicados"        , iTotDpl);
						System.out.printf("                   %-23s: [%05d]\n", "LIXO CodDealer"         , iErrCodDealer);
						System.out.printf("                   %-23s: [%05d]\n", "LIXO Persistencia"      , iErrValid);
*/
		
						String sTxtLog="", sValLog="";
						
						sTxtLog = String.format("%-25s", "HEADER");
						sValLog = String.format("%08d", headerCobranca.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "VENDAS DIRETAS");
						sValLog = String.format("%08d", vendasDiretas.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP VENDAS DIRETAS");
						sValLog = String.format("%08d", iDplVD);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "VENDAS INTERNET" );
						sValLog = String.format("%08d", vendasInternet.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP VENDAS INTERNET");
						sValLog = String.format("%08d", iDplVI);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "VENDAS GOVERNO");
						sValLog = String.format("%08d", vendasGoverno.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP VENDAS GOVERNO");
						sValLog = String.format("%08d", iDplVG);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "VEICULOS PENDENTES");
						sValLog = String.format("%08d", veiculosPendentes.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP VEICULOS PENDENTES");
						sValLog = String.format("%08d", iDplVP);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "DIVIDAS DE PECAS");
						sValLog = String.format("%08d", dividasPecas.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP DIVIDAS DE PECAS");
						sValLog = String.format("%08d", iDplDP);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "DIVIDAS DE AUTOS");
						sValLog = String.format("%08d", dividasAutos.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP DIVIDAS DE AUTOS");
						sValLog = String.format("%08d", iDplDA);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "ENTREGA LIBERADA");
						sValLog = String.format("%08d", entregaLiberada.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP ENTREGA LIBERADA");
						sValLog = String.format("%08d", iDplEL);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "VENDAS LEASING");
						sValLog = String.format("%08d", vendasLeasing.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "DLP VENDAS LEASING");
						sValLog = String.format("%08d", iDplVL);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "TRAILLER");
						sValLog = String.format("%08d", traillerCobranca.size());
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "REGISTROS LOGICOS");
						sValLog = String.format("%08d", iTotRegistros);
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "REGISTROS FISICOS");
						sValLog = String.format("%08d", iTotLidos);
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-25s", "REGISTROS LIXO");
						sValLog = String.format("%08d", iTotLixos);
						txtResult.append("  Total de Registros " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "LIXO Duplicados");
						sValLog = String.format("%08d", iTotDpl);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "LIXO CodDealer");
						sValLog = String.format("%08d", iErrCodDealer);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
			
						sTxtLog = String.format("%-23s", "LIXO Persistencia");
						sValLog = String.format("%08d", iErrValid);
						txtResult.append("                       " + sTxtLog + "[" + sValLog + "]\n");
						
						txtResult.append("\nFim Processamento: [" +  sTxtArq + "]\n");
						
						Date dt2 = new Date();
					    
						diferencaSegundos = (dt2.getTime() - dt1.getTime()) / (1000);
						txtResult.append("Tempo de Processamento: [" + diferencaSegundos + "] segundos\n");
						txtResult.append("\n--------------------------------------------------");
									
						Collections.sort(vendasDiretas);
						Collections.sort(vendasInternet);
						Collections.sort(vendasGoverno);
						Collections.sort(veiculosPendentes);
						Collections.sort(dividasPecas);
						Collections.sort(dividasAutos);
						Collections.sort(entregaLiberada);
						Collections.sort(vendasLeasing);
			
						//FileWriter fileNameOut = new FileWriter(sDirSAIDA + "\\CORRIGIDOS\\" + sTxtArq2);
						BufferedWriter fileNameOut = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(sDirSAIDA + "\\CORRIGIDOS\\" + sTxtArq2),"UTF-8")); 
						PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
						// O OutputStreamWriter cria as quebras de linha
						// No Unix, o separador de linha (Line Feed) é representado como "\n"
						// No Mac, o separador de linha (Line Feed) é representado como "\r"
						// No Windows (Carriage Return + Line Feed) é representado como "\r\n".
						
						//Grava Header
						for (HeaderCobranca HD : headerCobranca){
							//System.out.println(HD);
							gravarArq.printf("%-301s\r\n", HD);
						}
			
						//Grava VendasDiretas
						for (VendasDiretas VD : vendasDiretas){
							//System.out.println(VD);
							gravarArq.printf("%-301s\r\n", VD);
						}

						//Grava VendasInternet
						for (VendasInternet VI : vendasInternet){
							//System.out.println(VI);
							gravarArq.printf("%-301s\r\n", VI);
						}
			
						//Grava VendasGoverno
						for (VendasGoverno VG : vendasGoverno){
							//System.out.println(VG);
							gravarArq.printf("%-301s\r\n", VG);
						}
			
						//Grava VeiculosPendentes
						for (VeiculosPendentes VP : veiculosPendentes){
							//System.out.println(VD);
							gravarArq.printf("%-301s\r\n", VP);
						}

						//Grava DividasPecas
						for (DividasPecas DP : dividasPecas){
							//System.out.println(DP);
							gravarArq.printf("%-301s\r\n", DP);
						}
			
						//Grava DividasAutos
						for (DividasAutos DA : dividasAutos){
							//System.out.println(DA);
							gravarArq.printf("%-301s\r\n", DA);
						}
			
						//Grava EntregaLiberada
						for (EntregaLiberada EP : entregaLiberada){
							//System.out.println(EP);
							gravarArq.printf("%-301s\r\n", EP);
						}
			
						//Grava VendasLeasing
						for (VendasLeasing VL : vendasLeasing){
							//System.out.println(VL);
							gravarArq.printf("%-301s\r\n", VL);
						}
						
						//Grava Trailler
						for (TraillerCobranca TL : traillerCobranca){
							//System.out.println(TL);
							gravarArq.printf("%-301s\r\n", TL);
						}
				
						// Consultar: vendasDiretas.contains("algo");
						// Remover: vendasDiretas.remove(contat);

						sTxtArq = "";

						//Apagar arquivo de origem
						arquivoOrigem.delete();
						
						fileName.close();
						fileNameOut.close();
						fileNameLixo.close();
						
						//Apaga os ArrayList
						headerCobranca.removeAll(headerCobranca);
						vendasDiretas.removeAll(vendasDiretas);
						vendasInternet.removeAll(vendasInternet);
						vendasGoverno.removeAll(vendasGoverno);
						veiculosPendentes.removeAll(veiculosPendentes);
						dividasPecas.removeAll(dividasPecas);
						dividasAutos.removeAll(dividasAutos);
						entregaLiberada.removeAll(entregaLiberada);
						vendasLeasing.removeAll(vendasLeasing);
						traillerCobranca.removeAll(traillerCobranca);
						
						iTotRegistros = 0;
						
						//envia o arquivo por email
						String sAnexo = sDirSAIDA + "\\CORRIGIDOS\\" + sTxtArq2;
						//enviaEmailFile (sAnexo, true);
				}
			}
			
			File DeldiretorioEntrada = new File(sDirENTRADA);
	    	 if (DeldiretorioEntrada.isDirectory()) {
	    	 	File[] sun = DeldiretorioEntrada.listFiles();
	    	 	for (File toDelete : sun) {
	    	 		toDelete.delete();
	    	 	}
	    	 }   
	    	 
				Date dtG2 = new Date();
			    
				diferencaSegundosGeral = (dtG2.getTime() - dtG1.getTime()) / (1000);
				txtResult.append("\n-------------- FIM DE PROCESSAMENTO --------------");
				txtResult.append("\nTempo Total de Processamento: [" + diferencaSegundosGeral + "] segundos");
	    	 
		} catch (IOException e) {
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
    
    public static void enviaEmailFile (String sAnexo, boolean flag) throws EmailException {
    
	    
	 // cria o anexo.
	    EmailAttachment attachment = new EmailAttachment();
	    attachment.setPath(sAnexo); //caminho da imagem
	    attachment.setDisposition(EmailAttachment.ATTACHMENT);
	    attachment.setDescription("Arquivo COBRANCA");
	    attachment.setName("COBRANCA");

	    // Cria a mensagem de e-mail.
	    

//mail.seudominio.com.br (*)
//smtp.seudominio.com.br (*)
 
    	if(flag){
    	    SimpleEmail email = new SimpleEmail();
    	    email.setHostName("mail.fcagroup.com"); // o servidor SMTP para envio do e-mail
    	    email.addTo("salomao.lopes@fcagroup.com", "Salomao Lopes"); //destinatario
    	    email.setFrom("salomao.lopes@fcagroup.com", "Salomao Lopes"); //remetente
    	    email.setSubject("IF_BR_01_315 - Transfer in 16h (Italy)"); //Assunto
    	    email.setMsg("Aqui está a Imagem anexada ao e-mail"); //conteudo do e-mail
    	    email.setMsg("\n Parte 2");
    	    email.setMsg("\n Parte 3");
    	    email.send();// envia o e-mail

    	}
    	else{
    	    MultiPartEmail email = new MultiPartEmail();
    	    email.setHostName("mail.fcagroup.com"); // o servidor SMTP para envio do e-mail
    	    email.addTo("salomao.lopes@fcagroup.com", "Salomao Lopes"); //destinatario
    	    email.setFrom("salomao.lopes@fcagroup.com", "Salomao Lopes"); //remetente
    	    email.setSubject("IF_BR_01_315 - Transfer in 16h (Italy)"); //Assunto
    	    email.setMsg("Aqui está a Imagem anexada ao e-mail"); //conteudo do e-mail
    	    email.setMsg("\n Parte 2");
    	    email.setMsg("\n Parte 3");
    	    email.attach(attachment); // adiciona o anexo à mensagem
    	    email.send();// envia o e-mail

    	}
	    
    }
}
