package DDU;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import DDU.VendasDiretas;
import DDU.ProcessaVD;
import java.util.ArrayList;
import java.util.Collections;

public class Cobranca {

	public static void main(String[] args) {

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

			FileReader fileRedeAtivaTam = new FileReader("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\REDEATIVA.txt");
			BufferedReader lerRedeAtivaTam = new BufferedReader(fileRedeAtivaTam);
			String linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // l� a primeira linha

			while (linhaRedeAtivaTam != null) {
				//System.out.printf("%s\n", linhaRedeAtivaTam);
				RedeAtiva RATV = new RedeAtiva( linhaRedeAtivaTam);
				
				redeAtiva.add(RATV);
				linhaRedeAtivaTam = lerRedeAtivaTam.readLine(); // l� da segunda at� a �ltima linha
			}
			fileRedeAtivaTam.close();

			
			FileReader fileNameTam = new FileReader("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\SAL0.txt");
			BufferedReader lerArqTam = new BufferedReader(fileNameTam);
			String linhaTam = lerArqTam.readLine(); // l� a primeira linha

			FileWriter fileNameTamOut = new FileWriter("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\SAL1.txt");
			PrintWriter gravarArqTam = new PrintWriter(fileNameTamOut);

			while (linhaTam != null) {
				//System.out.printf("%s\n", linhaTam);
				gravarArqTam.printf("%-301s\n", linhaTam);
				linhaTam = lerArqTam.readLine(); // l� da segunda at� a �ltima linha
			}
			fileNameTam.close();
			fileNameTamOut.close();

			FileWriter fileNameLixo = new FileWriter("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\LIXO.txt");
			PrintWriter gravarArqLixo = new PrintWriter(fileNameLixo);

			FileReader fileName = new FileReader("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\SAL1.txt");
			BufferedReader lerArq = new BufferedReader(fileName);
			String linha = lerArq.readLine(); // l� a primeira linha
			
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
				
				
				linha = lerArq.readLine(); // l� da segunda at� a �ltima linha
			} 
			
			iTotLidos = (headerCobranca.size() + traillerCobranca.size() + iTotRegistros);
			iTotDpl = iDplVD + iDplVI + iDplVG + iDplVP + iDplDP + iDplDA + iDplEL + iDplVL;

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

			Collections.sort(vendasDiretas);
			Collections.sort(vendasInternet);
			Collections.sort(vendasGoverno);
			Collections.sort(veiculosPendentes);
			Collections.sort(dividasPecas);
			Collections.sort(dividasAutos);
			Collections.sort(entregaLiberada);
			Collections.sort(vendasLeasing);
			
			FileWriter fileNameOut = new FileWriter("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 98\\SAL2.txt");
			PrintWriter gravarArq = new PrintWriter(fileNameOut);
			
			//Grava Header
			for (HeaderCobranca HD : headerCobranca){
				//System.out.println(HD);
				gravarArq.printf("%-301s\n", HD);
			}
			
			//Grava VendasDiretas
			for (VendasDiretas VD : vendasDiretas){
				//System.out.println(VD);
				gravarArq.printf("%-301s\n", VD);
			}

			//Grava VendasInternet
			for (VendasInternet VI : vendasInternet){
				//System.out.println(VI);
				gravarArq.printf("%-301s\n", VI);
			}

			//Grava VendasGoverno
			for (VendasGoverno VG : vendasGoverno){
				//System.out.println(VG);
				gravarArq.printf("%-301s\n", VG);
			}

			//Grava VeiculosPendentes
			for (VeiculosPendentes VP : veiculosPendentes){
				//System.out.println(VD);
				gravarArq.printf("%-301s\n", VP);
			}

			//Grava DividasPecas
			for (DividasPecas DP : dividasPecas){
				//System.out.println(DP);
				gravarArq.printf("%-301s\n", DP);
			}

			//Grava DividasAutos
			for (DividasAutos DA : dividasAutos){
				//System.out.println(DA);
				gravarArq.printf("%-301s\n", DA);
			}

			//Grava EntregaLiberada
			for (EntregaLiberada EP : entregaLiberada){
				//System.out.println(EP);
				gravarArq.printf("%-301s\n", EP);
			}

			//Grava VendasLeasing
			for (VendasLeasing VL : vendasLeasing){
				//System.out.println(VL);
				gravarArq.printf("%-301s\n", VL);
			}
			
			//Grava Trailler
			for (TraillerCobranca TL : traillerCobranca){
				//System.out.println(TL);
				gravarArq.printf("%-301s\n", TL);
			}
			
			// Consultar: vendasDiretas.contains("algo");
			// Remover: vendasDiretas.remove(contat);
			
			fileName.close();
			fileNameOut.close();
			fileNameLixo.close();
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
		//System.out.println();
	}
		
}
