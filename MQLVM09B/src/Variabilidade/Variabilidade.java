package Variabilidade;

import java.io.ByteArrayInputStream; 
import java.io.IOException; 
import java.net.URL; 
import java.util.Properties; 
 
import org.xml.sax.InputSource; 
import org.xml.sax.SAXException; 

import org.apache.commons.net.ftp.FTPClient;

import java.lang.*;
import java.lang.ClassLoader;
import java.io.*;
import java.net.*;
import java.net.URLClassLoader;
import java.net.URLConnection;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.URLPermission;
import java.net.URLStreamHandler;

import com.ibm.mq.*;
import com.ibm.mq.MQC;
import com.ibm.mq.MQEnvironment;
import com.ibm.mq.MQException;
import com.ibm.mq.MQGetMessageOptions;
import com.ibm.mq.MQMessage;
import com.ibm.mq.MQPutMessageOptions;
import com.ibm.mq.MQQueue;
import com.ibm.mq.MQQueueManager;
import com.ibm.mq.constants.MQConstants;
import com.ibm.mq.jmqi.*;

public class Variabilidade {
	// code identifier
	//static final String sccsid = "@(#) MQMBID sn=p000-L120604 su=_H-IvIK4nEeGko6IWl3MDhA pn=MQJavaSamples/wmqjava/MQSample.java";
	// define the name of the QueueManager
	private static final String qManager = "M002"; // PRD "M001"
	// and define the name of the Queue
	private static final String qName = "S409O062";

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
			
			String sHostname = "172.28.3.33"; // HOM "172.28.3.33" - PRD "172.28.3.31" 
			String sChannel  = "CH_CLIENT_QMM002_01"; // HOM "CH_CLIENT_QMM002_01" - PRD "CH_CLIENT_QMM001_01" 
			int iPort = 1414;
			int iCCSID = 850; //850 - 1208;
			
	        MQEnvironment.hostname = sHostname; 
	        MQEnvironment.channel  = sChannel; 
	        MQEnvironment.port = iPort;
	          //MQEnvironment.properties.put(MQC.TRANSPORT_PROPERTY, MQC.TRANSPORT_MQSERIES);
	          //MQEnvironment.userID = "WW00000E"; 
	          //MQEnvironment.password = "WW00000E";
	        MQEnvironment.CCSID = iCCSID;
	        
	        // Create a connection to the QueueManager
	        System.out.println("Connecting to queue manager: " + qManager);
	        MQQueueManager qMgr = new MQQueueManager(qManager);

	        // Set up the options on the queue we wish to open
	        int openOptions = MQConstants.MQOO_INPUT_AS_Q_DEF | MQConstants.MQOO_OUTPUT;

	        // Now specify the queue that we wish to open and the open options
	        System.out.println("Accessing queue: " + qName);
	        MQQueue queue = qMgr.accessQueue(qName, openOptions);
	        if(queue.isOpen()){
	        	System.out.println("Connected at queue: " + qName);
	        }
	        
	        // Define a simple WebSphere MQ Message ...
	        MQMessage sndMessage = new MQMessage();
	        //sndMessage.writeInt(300);
	        
	        String msgTextInput = "T4090621                                                                                                       343300158U600002A601025082876                                                                                 1523401311                                                            ";
	        //sndMessage.writeInt(msgTextInput.length());
	        
	        // ... and write some text in UTF8 format
	        sndMessage.writeUTF(msgTextInput);

	        // Specify the default put message options
	        MQPutMessageOptions pmo = new MQPutMessageOptions();

	        // Put the message to the queue
	        System.out.println("Sending a message...");
	        System.out.println("The message INPUT: [" + msgTextInput + "]");
	        queue.put(sndMessage, pmo);
	        
	        
	        // to receive the data
	        MQMessage rcvMessage = new MQMessage();
	        //rcvMessage.writeInt(msgTextInput.length());

	        MQGetMessageOptions gmo = new MQGetMessageOptions();
	        
	        queue.get(rcvMessage, gmo);

	        // And display the message text...
	        String msgText = rcvMessage.readUTF();
	        System.out.println("The message OUTPUT: [" + msgText + "]");

	        // Close the queue
	        System.out.println("Closing the queue");
	        queue.close();

	        // Disconnect from the QueueManager
	        System.out.println("Disconnecting from the Queue Manager");
	        qMgr.disconnect();
	        System.out.println("Done!");
	    } catch (MQException ex) {
	        ex.printStackTrace();
	        System.out.println("A WebSphere MQ Error occured : Completion Code " + ex.completionCode
	                + " Reason Code " + ex.reasonCode);


	    } catch (java.io.IOException ex) {
	        System.out.println("An IOException occured whilst writing to the message buffer: " + ex);
	    }
	    return;
	}
}
