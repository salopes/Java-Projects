//============================================================================
// Name        : FSLeituraMotomco.java
// Author      : Salomao Lopes
// Version     :
// Copyright   : Your copyright notice
// Description : Main program to start setting and read Serial Ports
//============================================================================

package Motomco;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import Motomco.FSSerialComLeitura;
import Motomco.FSSerialDadosINI;
import gnu.io.SerialPortEvent;

public class FSLeituraMotomco {
	
	// java -jar C:\Users\SALOMAO\Documents\desenv_losango\java_essencial\FSLeituraMotomco\bin\FSLeituraMotomcos.jar C:\ms0\ini\FSLeituraMotomco.ini
	
	public static FSSerialDadosINI INI = new FSSerialDadosINI();
	public static FSSerialComSAP SCSAP = new FSSerialComSAP();
	
	public static void lerArquivoIni(String sfileName) {
	    try {
	    	
	    	int tamLinha=0;
	    	int posIgual=0;
	    	String linha="", sValor="", sBusca="";
	    	String NOMEPORTA= "", RATE = "", TIMEOUT = "",  ASHOST = "", SYSNR = "", CLIENT = "", USER = "", PASSWD = "", LANG = "", TP_BALANCA = "";
	    	
	        FileReader arq = new FileReader(sfileName);
	        BufferedReader lerArq = new BufferedReader(arq);
	        linha = lerArq.readLine();
	        while (linha != null) {
	        	//System.out.printf("%s\n", linha);
	        	posIgual = linha.indexOf('=');
	        	tamLinha = linha.length();
	        	
	        	if (posIgual>0) {
		        	sBusca = linha.substring(0, posIgual+1);
		        	sBusca = sBusca.replaceAll(" ","");
	        	}
	        	else {
	        		sBusca = linha;
	        	}

	        	//System.out.printf("[%s]\n", sBusca);
	        	
	        	switch (sBusca) {
		        	case "nomePorta=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("nomePorta = [%s]\n", sValor);
			        	NOMEPORTA = sValor;
			        	
		        		break;
		        	case "baudRate=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("baudRate = [%s]\n", sValor);
			        	RATE = sValor;

		        		break;
		        	case "timeOut=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("timeOut = [%s]\n", sValor);
			        	TIMEOUT = sValor;

		        		break;
/*		        	case "ASHOST=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("ASHOST = [%s]\n", sValor);
			        	ASHOST = sValor;

		        		break;
		        	case "SYSNR=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("SYSNR = [%s]\n", sValor);
			        	SYSNR = sValor;

		        		break;
		        	case "CLIENT=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("CLIENT = [%s]\n", sValor);
			        	CLIENT = sValor;

		        		break;
		        	case "USER=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("USER = [%s]\n", sValor);
			        	USER = sValor;

		        		break;
		        	case "PASSWD=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("PASSWD = [%s]\n", sValor);
			        	PASSWD = sValor;

		        		break; 
		        	case "LANG=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("LANG = [%s]\n", sValor);
			        	LANG = sValor;

		        		break; */
		        	case "TP_BALANCA=":
			        	sValor = linha.substring((posIgual+1), tamLinha);
			        	sValor = sValor.replaceAll(" ","");
			        	//System.out.printf("TP_BALANCA sValor = [%s]\n", sValor);
			        	TP_BALANCA = sValor;
		        		break;

		        	default:
		        		//System.out.println("N�o eh uma entrada valida no INI");
		        		//System.out.println("");
		        		break;
	        	}
	          
	          linha = lerArq.readLine(); 
	        }
	        arq.close();
	        
	        //FSSerialDadosINI FSINI = new FSSerialDadosINI(NOMEPORTA, RATE, TIMEOUT,  ASHOST, SYSNR, CLIENT, USER, PASSWD, LANG);
	        
	        INI.setNOMEPORTA(NOMEPORTA);
	        INI.setRATE(RATE);
	        INI.setTIMEOUT(TIMEOUT);
	        INI.setTP_BALANCA(TP_BALANCA);

	        //INI.setASHOST(ASHOST);
	        //INI.setSYSNR(SYSNR);
	        //INI.setCLIENT(CLIENT);
	        //INI.setUSER(USER);
	        //INI.setPASSWD(PASSWD);
	        //INI.setLANG(LANG);
	        
	        //Tem que criar as variaveis de ambientes abaixo:
	        INI.setASHOST(System.getenv().get("ASHOST_SAP"));
	        INI.setSYSNR(System.getenv().get("SYSNR_SAP"));
	        INI.setCLIENT(System.getenv().get("CLIENT_SAP"));
	        INI.setUSER(System.getenv().get("USER_SAP"));
	        INI.setPASSWD(System.getenv().get("PWD_SAP"));
	        INI.setLANG(System.getenv().get("LANG_SAP"));
	        INI.setROUTER(System.getenv().get("ROUTER_SAP"));
	        	        
	      } catch (IOException e) {
	          System.err.printf("Erro na abertura do arquivo: %s.\n",
	            e.getMessage());
	      }
	   
	      System.out.println();
	}
	
	public static void main(String[] args) {
		
		String sfileName = "";
			
		if (args.length < 1) {
		   System.out.println("Informar Arquivo INI.");
		   System.exit(1);
		}
		else {
			//System.out.printf("%s\n", args[0]);
			sfileName = args[0];
			lerArquivoIni(sfileName);
		}
		
		System.out.printf("Carregueis dados INI\n");
		
       	SCSAP.CarregaDadosSAP(INI.getASHOST(), INI.getSYSNR(), INI.getCLIENT(), INI.getUSER(), INI.getPASSWD(), INI.getLANG(), INI.getROUTER());
		FSSerialComLeitura leitura = new FSSerialComLeitura(INI.getNOMEPORTA(), Integer.parseInt(INI.getRATE()), Integer.parseInt(INI.getTIMEOUT()), INI.getTP_BALANCA());

		System.out.printf("Carregueis dados SAP\n");

		leitura.HabilitarLeitura();
		System.out.printf("Carregueis dados SAP\n");
	    leitura.ObterIdDaPorta();
	    System.out.printf("ID Porta\n");
	    leitura.AbrirPorta();
	    System.out.printf("Porta OK\n");
	    leitura.LerDados();
	    try {
	        Thread.sleep(1000);
	    } catch (InterruptedException ex) {
	        System.out.println("Erro na Thread: " + ex);
	    }
	}
}
