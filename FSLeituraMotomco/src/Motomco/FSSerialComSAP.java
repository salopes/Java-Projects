//============================================================================
// Name        : FSSerialComLeitura.java
// Author      : Salomao Lopes
// Version     :
// Copyright   : Your copyright notice
// Description : Program to Conecct SAP and call RFC
//============================================================================

package Motomco;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;

import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
import com.sap.conn.jco.JCoFunction;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class FSSerialComSAP {

	// For Windows add sapjco3.dll  in C:\Windows\System32
    static String DST1 = "SAPConf";
    
    
    public void CarregaDadosSAP(String ASHOST, String SYSNR, String CLIENT, String USER, String PASSWD, String LANG, String ROUTER){

    	//System.out.printf("Carregando Dados do INI: %s - %s - %s \n", ASHOST, SYSNR, CLIENT, USER);
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, ASHOST);
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR,  SYSNR);
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, CLIENT);
        connectProperties.setProperty(DestinationDataProvider.JCO_USER,   USER);
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, PASSWD);
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG,   LANG);
        connectProperties.setProperty(DestinationDataProvider.JCO_SAPROUTER, ROUTER);

        createDestinationDataFile1(DST1, connectProperties);
        //connectProperties.setProperty(DestinationDataProvider.JCO_POOL_CAPACITY, "3");
        //connectProperties.setProperty(DestinationDataProvider.JCO_PEAK_LIMIT, "10");
        //createDestinationDataFile(DESTINATION_NAME2, connectProperties);
    }
	
    static void createDestinationDataFile1 (String destinationName, Properties connectProperties)
    {
        File destCfg = new File(destinationName+".jcoDestination");
        try
        {
            FileOutputStream fos = new FileOutputStream(destCfg, false);
            connectProperties.store(fos, "for tests only !");
            fos.close();
        }
        catch (Exception e)
        {
            throw new RuntimeException("Unable to create the destination files", e);
        }
    }
    
    public void conectaSAP(String s, String tp) throws JCoException {
    	
        JCoDestination destination = JCoDestinationManager.getDestination(DST1);
        //System.out.println("Attributes:");
        //System.out.println(destination.getAttributes());
        //System.out.println();
		
		JCoFunction function = destination.getRepository().getFunction("ZCPF_MOTOMCO");
		function.getImportParameterList().setValue("IV_DADOS", s);
		function.getImportParameterList().setValue("IV_OWN_HOST", destination.getAttributes().getHost());
		function.getImportParameterList().setValue("IV_USER", destination.getAttributes().getUser());
		function.getImportParameterList().setValue("IV_TP_BALANCA", tp);
		
		try {
			function.execute(destination);
			//System.out.println(function.getExportParameterList().getString("E_CARRNAME"));
			//System.out.println("CONECTEI no SAP");
			}
		
		catch (JCoException ex) {
			if (ex.getKey().equals("MOTOMCO_NOT_FOUND")) {
				System.out.println("N�o foi possivel enviarda dados para o SAP");
			}
		}
		System.out.println();
	}
	
}