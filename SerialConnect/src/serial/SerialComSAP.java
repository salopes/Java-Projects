package serial;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Properties;
//import java.util.concurrent.CountDownLatch;

//import com.sap.conn.jco.AbapException;
//import com.sap.conn.jco.JCoContext;
//import com.sap.conn.jco.JCoCustomDestination;
import com.sap.conn.jco.JCoDestination;
import com.sap.conn.jco.JCoDestinationManager;
import com.sap.conn.jco.JCoException;
//import com.sap.conn.jco.JCoField;
import com.sap.conn.jco.JCoFunction;
//import com.sap.conn.jco.JCoFunctionTemplate;
//import com.sap.conn.jco.JCoStructure;
//import com.sap.conn.jco.JCoTable;
import com.sap.conn.jco.ext.DestinationDataProvider;

public class SerialComSAP {

	// For Windows add sapjco3.dll  in C:\Windows\System32
	
    static String DST1 = "SAPConf";
    
    static {
    	
        Properties connectProperties = new Properties();
        connectProperties.setProperty(DestinationDataProvider.JCO_ASHOST, "192.168.225.30");
        connectProperties.setProperty(DestinationDataProvider.JCO_SYSNR, "00");
        connectProperties.setProperty(DestinationDataProvider.JCO_CLIENT, "200");
        connectProperties.setProperty(DestinationDataProvider.JCO_USER, "SLOPES");
        connectProperties.setProperty(DestinationDataProvider.JCO_PASSWD, "Samhei@01");
        connectProperties.setProperty(DestinationDataProvider.JCO_LANG, "pt");
        
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
    
    public void conectaSAP(String s) throws JCoException {
    	    	
		System.out.println("Enviando para o SAP o <s>: " + s);
		
        JCoDestination destination = JCoDestinationManager.getDestination(DST1);
        System.out.println("Attributes:");
        System.out.println(destination.getAttributes());
        System.out.println();
		
		//JCoDestination destination = JCoDestinationManager.getDestination("SerialFrameSAPConf.jcoDestionation");
		
		JCoFunction function = destination.getRepository().getFunction("ZCPF_MOTOMCO");
		function.getImportParameterList().setValue("I_DADOS", s);
		
		try {
			function.execute(destination);
			//System.out.println(function.getExportParameterList().getString("E_CARRNAME"));
			System.out.println("CONECTEI no SAP");
			}
		
		catch (JCoException ex) {
			if (ex.getKey().equals("CARR_NOT_FOUND")) {
				System.out.println("Não foi possivel enviarda dados para o SAP");
			}
		}
		System.out.println();
	}
	
}
