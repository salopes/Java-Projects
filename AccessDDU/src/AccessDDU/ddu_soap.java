package AccessDDU;

import java.io.ByteArrayInputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Iterator;
import java.util.Vector;

import javax.xml.rpc.holders.StringHolder;
import javax.xml.soap.AttachmentPart;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.Name;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPBodyElement;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPFactory;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;


import javax.xml.namespace.QName;
import javax.xml.soap.SOAPHeader;
import javax.xml.soap.SOAPHeaderElement;
import javax.xml.soap.SOAPMessage;

import AccessDDU.DDU_Const_Error;

import org.apache.axis.holders.DataHandlerHolder;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;


/**
 * ddu_soap.
 * Stub di chiamata ai web services.
 * 
 * @author Tullio Ferralis - While1 s.r.l.
 *
 * @version 2.0	-	20071119
 * @version 2.2 - 	20080125
 * @version 2.3	-	20080225
 * @version 2.4	-	20080318
 * @version 2.5	-	20080326
 */

public class ddu_soap {
	
	private int lastStep = 0;
	private String  lastBodyResp = "";
	private int dduSoapError = 0;
	private Exception dduSoapEx = null;
	
	private URL endpoint = null;
	
	/**
	 * get_dduSoapError
	 * 
	 * Restituisce l'ultimo errore occorso
	 * 
	 * @return lastError
	 */
	public int get_dduSoapError() {
		
		return this.dduSoapError;
		
	}
	
	/**
	 * get_dduSoapEx
	 * 
	 * Restituisce l'ultimo errore occorso
	 * 
	 * @return lastException
	 */
	public Exception get_dduSoapEx() {
		
		return this.dduSoapEx;
	}
	
	/**
	 * get_dduSoapString
	 * 
	 * Restituisce l'ultimo errore occorso
	 * 
	 * @return lastError
	 */
	public String get_dduSoapString() {
		
		return this.lastBodyResp+" - "+this.lastStep;
		
	}
	
	
	/**
	 * ddu_soapInit
	 * 
	 * @param endpoint
	 * 
	 * @return	0 OK, else -1
	 */
	public int ddu_soapInit(String endpoint) {
		
		int rc = 0;
		
		try {
			
			/* set endpoint */
			this.endpoint = new URL(endpoint);
			
		} catch (MalformedURLException e) {
			
			rc = -1;
		}
		
		/* return 0 OK, else -1 */
		return rc;
	}
	
	/**
	 * set_endpoint
	 * 
	 * @param endpoint
	 * 
	 * @return 0 OK, else -1
	 */
	public int set_endpoint(String endpoint) {

		int rc = 0;
		
		try {
			
			/* set endpoint */
			this.endpoint = new URL(endpoint);
			
		} catch (MalformedURLException e) {
			
			rc = -1;
		}

		/* return 0 OK, else -1 */
		return rc;		
	}
	
	/**
	 * get_endpoint
	 * 
	 * @return endpoint of web servicrs
	 */
	public URL get_endpoint() {
		return this.endpoint;
	}
	
	
	
	/**
	 * dduConnect
	 * 
	 * @param user
	 * @param password
	 * @param certificationSystem
	 * @param _ticket
	 * @param _servID
	 * @param _return
	 * 
	 * @return	0 OK, else -1
	 */
	public int dduConnect(String user, String password, String certificationSystem, StringHolder _ticket, StringHolder _servID, StringHolder _return) {
		
		int rc = 0;
		
		try {
			
			SOAPFactory soapFactory;
			
			soapFactory = SOAPFactory.newInstance();

		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();

			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 

			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
			
			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduConnect",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);

		    be.addChildElement("User").addTextNode(user).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Password").addTextNode(password).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("CertificationSystem").addTextNode(certificationSystem).setAttribute("xsi:type","xsd:string");

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);				    

		    Name bodyName_resp = soapFactory.createName("dduConnectResponse",
														"ns1",
														"urn:ddu");
		    
		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {

			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
					
					if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {
						_servID.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					
					i++;
			    }
			}

		    connection.close();



		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.CONNECT;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.CONNECT;
			rc = -1;
		}

		return rc;
	}
	
	
	
	/**
	 * dduLogin
	 * 
	 * @param _ticket
	 * @param serviceID
	 * @param market
	 * @param appl
	 * @param brand
	 * @param sincom
	 * @param certificationSystem
	 * @param _logID
	 * @param _return
	 * 
	 * @return	0 OK, else -1
	 */
	public int dduLogin(StringHolder _ticket, String serviceID, String market, String appl, String brand, String sincom, String certificationSystem, 
						StringHolder _logID, StringHolder _return) {

		int rc = 0;
		
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();
			
			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
			

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduLogin",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("ServiceId").addTextNode(serviceID).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Market").addTextNode(market).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Application").addTextNode(appl).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Brand").addTextNode(brand).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Sincom").addTextNode(sincom).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("CertificationSystem").addTextNode(certificationSystem).setAttribute("xsi:type","xsd:string");

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();

		    SOAPConnection connection = connFactory.createConnection();

		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);

		    Name bodyName_resp = soapFactory.createName("dduLoginResponse",
														"ns1",
														"urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);

			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();

			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();

			    int i = 0;
			    
			    while(ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
			    	
			    	if (i == 0) {
			    		_ticket.value =((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	else if (i == 1) {
			    		_logID.value =((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	else if (i == 2) {
			    		_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	
			    	i++;
			    }
		    }

		    connection.close();

		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.LOGIN;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.LOGIN;
			rc = -1;
		}

		return rc;
	}
	
	
	
	
	/**
	 * dduLogout
	 * 
	 * @param ticket
	 * @param serviceID
	 * @param loginID
	 * 
	 * @return	0 OK, else -1
	 */
	public int dduLogout(String ticket, String serviceID, String loginID, StringHolder _return) throws SOAPException {
		
		int rc = 0;
		
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduLogout",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);

		    be.addChildElement("Ticket").addTextNode(ticket).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("ServiceId").addTextNode(serviceID).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("LoginID").addTextNode(loginID).setAttribute("xsi:type","xsd:string");

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);				    

		    Name bodyName_resp = soapFactory.createName("dduLogoutResponse",
														"ns1",
														"urn:ddu");
		    
			SOAPBody body_resp = response.getSOAPBody();
			
			this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
				
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
					
					if (i == 0) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					
					i++;
			    }
			}

		    connection.close();

		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.LOGOUT;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.LOGOUT;
			rc = -1;
		}
		
		return rc;
	}
	
	
	
	
	/**
	 * dduDir
	 * 
	 * @param _ticket
	 * @param serviceID
	 * @param loginID
	 * @param dirTypes
	 * @param fileGroup
	 * @param fileName
	 * @param dateStart
	 * @param dateEnd
	 * @param dateFormat
	 * @param _dirFileEx
	 * @param _return
	 * 
	 * @return	0 OK, else -1
	 */
	public int dduDir(	StringHolder _ticket, String serviceID, String loginID, String dirTypes, String fileGroup, String fileName, String status,
						String dateStart, String dateEnd, String dateFormat, AccessDDU.DirFiles_ExHolder _dirFileEx, StringHolder _return) {

		int rc = 0;

		Vector vet 	= new Vector();
		DduFileEx file_ex;

		try {

			SOAPFactory soapFactory = SOAPFactory.newInstance();

		    MessageFactory factory = MessageFactory.newInstance();

		    SOAPMessage message = factory.createMessage();

			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 

			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduDir",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);

		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("ServiceId").addTextNode(serviceID).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("LoginID").addTextNode(loginID).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DirTypes").addTextNode(dirTypes).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("FileGroup").addTextNode(fileGroup).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("FileName").addTextNode(fileName).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Status").addTextNode(status).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DateStart").addTextNode(dateStart).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DateEnd").addTextNode(dateEnd).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DateFormat").addTextNode(dateFormat).setAttribute("xsi:type","xsd:string");

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();

		    SOAPConnection connection = connFactory.createConnection();

		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);

		    Name bodyName_resp = soapFactory.createName("dduDirResponse",
														"ns1",
														"urn:ddu");
		    
		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {

			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);

			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();

			    int i = 0;
			    int numOfFile = 0;

			    while(ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
			    	
			    	if(i == 0) {
			    		
			    		_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	else if(i == 1) {

				    	Iterator files = sElem.getChildElements();

				    	while(files.hasNext()) {

						    int k = 0;

				    		SOAPElement file = (SOAPElement) files.next();

				    		NodeList childNodes = file.getChildNodes();

				    		file_ex = new DduFileEx();

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setMarket("");
				    		}
				    		else file_ex.setMarket(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;
				    		
				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setUser("");
				    		}
				    		else file_ex.setUser(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;
				    		
				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setFileid("");
				    		}
				    		else file_ex.setFileid(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setFileName("");
				    		}
				    		else file_ex.setFileName(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setDescFile("");
				    		}
				    		else file_ex.setDescFile(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setStatoFile("");
				    		}
				    		else file_ex.setStatoFile(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setSize("");
				    		}
				    		else file_ex.setSize(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setDataCreazione("");
				    		}
				    		else file_ex.setDataCreazione(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
								
				    			file_ex.setEstensione("");
							}
				    		else file_ex.setEstensione(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setChecksum("");
				    		}
				    		else file_ex.setChecksum(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setControlledPath("");
				    		}
				    		else file_ex.setControlledPath(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setWorkingMode("");
				    		}
				    		else file_ex.setWorkingMode(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setDataAcquisizione("");
				    		}
				    		else file_ex.setDataAcquisizione(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setCodCausale("");
				    		}
				    		else file_ex.setCodCausale(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setDescCausale("");
				    		}
				    		else file_ex.setDescCausale(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNumRecFisici("");
				    		}
				    		else file_ex.setNumRecFisici(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNumRecLogici("");
				    		}
				    		else file_ex.setNumRecLogici(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNumLineaErrore("");
				    		}
				    		else file_ex.setNumLineaErrore(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNumColonnaErrore("");
				    		}
				    		else file_ex.setNumColonnaErrore(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNomeCampo("");
				    		}
				    		else file_ex.setNomeCampo(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setNumDownload("");
				    		}
				    		else file_ex.setNumDownload(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setCompress("");
				    		}
				    		else file_ex.setCompress(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		if (((childNodes.item(k)).getFirstChild()) == null) {
				    			
				    			file_ex.setEncoding("");
				    		}
				    		else file_ex.setEncoding(((childNodes.item(k)).getFirstChild()).getNodeValue());
				    		k++;

				    		vet.add(file_ex);

				    		numOfFile++;
				    	}
			    	}
			    	else if(i == 2) {
			    		_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}

			    	i++;
			    }
			    
			    _dirFileEx.value = new DduFileEx[numOfFile];
			    
			    for (int j = 0; j < numOfFile; j++) {
					
			    	_dirFileEx.value[j] = new DduFileEx();
			    	_dirFileEx.value[j] = (DduFileEx) vet.elementAt(j);
				}
			    
		    }

		    connection.close();

		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.DIR;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.DIR;
			rc = -1;
		}

		return rc;
	}
	
	
	
	/**
	 * dduGetAtt
	 * 
	 * @param _ticket
	 * @param serviceId
	 * @param loginId
	 * @param docId
	 * @param docAppl
	 * @param docType
	 * @param compress
	 * @param encoding
	 * @param operation
	 * @param chunkLength
	 * @param _dh
	 * 
	 * @param _length
	 * @param _return
	 * 
	 * @return	0 OK, else -1
	 */
    public int dduGetAtt(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, java.lang.String docId, 
    						java.lang.String docAppl, java.lang.String docType, java.lang.String compress, java.lang.String encoding, 
    						java.lang.String operation, java.lang.String chunkLength, DataHandlerHolder _dh,
    						javax.xml.rpc.holders.StringHolder _length,	javax.xml.rpc.holders.StringHolder _return) {
        
    	int rc = 0;
    	
    	try {
    		
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
	
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduGetAtt",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Operation").addTextNode(operation).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ChunkLength").addTextNode(chunkLength).setAttribute("xsi:type","xsd:string");
		    
		    message.saveChanges();
	
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    SOAPMessage response = null;

		    /* call service */
		    response = connection.call(message, this.endpoint);

		    Name bodyName_resp = soapFactory.createName("dduGetAttResponse",
														"ns1",
														"urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {

			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);

			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();

			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();

			    int i = 0;

			    while (ElementsIterator.hasNext()) {

			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

			    	if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
			    	else if (i == 1) {
						_length.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }

			    if (Integer.parseInt(_return.value) == 0) {
					
				    /* get attachments */
				    Iterator attachments = response.getAttachments();

				    AttachmentPart part = (AttachmentPart) attachments.next();

				    _dh.value = part.getDataHandler();
				}
		    }

		    connection.close();
		    
		    
		    
		/* -- Catch Exception -- */
	  	} catch (SOAPException se) {
	  		
	  		this.dduSoapError = DDU_Const_Error.GET_ATT;
	  		rc = -1;
	  		
	  	} catch (Exception e) {
	  		
	  		this.dduSoapError = DDU_Const_Error.GET_ATT;
			rc = -1;
		}
		
		return rc;
    }



    /**
     * dduPostAtt
     * 
     * @param fileData
     * @param serviceId
     * @param loginId
     * @param _docId
     * @param docAppl
     * @param docType
     * @param compress
     * @param encoding
     * @param operation
     * @param chunkLength
     * @param destMarket
     * @param destApplic
     * @param destUser
     * 
     * @param _length
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduPostAtt(	javax.xml.rpc.holders.StringHolder _ticket, byte[] fileData, java.lang.String serviceId, java.lang.String loginId, 
    						javax.xml.rpc.holders.StringHolder _docId, java.lang.String docAppl, java.lang.String docType, 
    						java.lang.String compress, java.lang.String encoding, java.lang.String operation, java.lang.String chunkLength, 
    						java.lang.String destMarket, java.lang.String destApplic, java.lang.String destUser, 
    						javax.xml.rpc.holders.StringHolder _length, javax.xml.rpc.holders.StringHolder _return) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();

		    MessageFactory factory = MessageFactory.newInstance();

		    SOAPMessage message = factory.createMessage();

			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduPostAtt",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(_docId.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Operation").addTextNode(operation).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ChunkLength").addTextNode(chunkLength).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestMarket").addTextNode(destMarket).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestApplic").addTextNode(destApplic).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestUser").addTextNode(destUser).setAttribute("xsi:type","xsd:string");
		    
		    message.saveChanges();
		    
		    /* add attachment */
		    AttachmentPart attPart = message.createAttachmentPart();

		    attPart.setMimeHeader("Content-Type", "application/octet-stream; name="+"while1");
		    
		    attPart.setMimeHeader("Content-Disposition", "attachment; filename="+"while1");
		    
		    attPart.setContentType("application/octet-string");

		    attPart.setContent(new ByteArrayInputStream(fileData), "application/octet-string");
	
		    message.addAttachmentPart(attPart);
	
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    this.lastStep = 1;
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
		    
		    this.lastStep = 2;

		    Name bodyName_resp;

			bodyName_resp = soapFactory.createName("dduPostAttResponse",
			   									   "ns1",
												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
		    	this.lastStep = 10;

			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);

			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
					
			    	if (i == 0) {
			    		
			    		this.lastStep = 11;
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
			    	else if (i == 1) {
			    		
			    		this.lastStep = 12;
						_docId.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						
						this.lastStep = 13;
						_length.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 3) {
						
						this.lastStep = 14;
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					
					i++;
			    }
		    }

		    connection.close();
	    
		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.POST_ATT;
			this.dduSoapEx = new Exception();
			this.dduSoapEx = se;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.POST_ATT;
			this.dduSoapEx = new Exception();
			this.dduSoapEx = e;
			rc = -1;
		}
		
		return rc;
    }
    
    
    
    /**
     * dduOpen
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docId
     * @param docAppl
     * @param docType
     * 
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduOpen(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, java.lang.String docId, 
    					java.lang.String docAppl, java.lang.String docType, java.lang.String compress, java.lang.String encoding, 
    					javax.xml.rpc.holders.StringHolder _return) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduOpen",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");
	
		    message.saveChanges();
		    
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
		    Name bodyName_resp;
	
			bodyName_resp = soapFactory.createName("dduOpenResponse",
			   									   "ns1",
												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {

			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

					if (i == 0) {

						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {

						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }
		    }
		    
		    connection.close();
	    
		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.OPEN;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.OPEN;
			rc = -1;
		}
    	
    	return rc;
    }
    
    
    
    /**
     * dduRead
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docId
     * @param offset
     * @param _length
     * @param compress
     * @param encoding
     * 
     * @param _buffer
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduRead(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, java.lang.String docId,
    					java.lang.String offset, javax.xml.rpc.holders.StringHolder _length, java.lang.String compress, java.lang.String encoding, 
    					javax.xml.rpc.holders.StringHolder _buffer,	javax.xml.rpc.holders.StringHolder _return) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduRead",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Offset").addTextNode(offset).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Length").addTextNode(_length.value).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");

		    message.saveChanges();

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
		    Name bodyName_resp;
	
			bodyName_resp = soapFactory.createName("dduReadResponse",
			   									   "ns1",
												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {

			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

					if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {
						_buffer.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						_length.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 3) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }
		    }

		    connection.close();
	    
		    
		    
		/* -- Catch Exception -- */
		} catch (SOAPException se) {
			
			this.dduSoapError = DDU_Const_Error.READ;
			rc = -1;
			
		} catch (Exception e) {
			
			this.dduSoapError = DDU_Const_Error.READ;
			rc = -1;
		}
    	
    	return rc;
    }
    
    
    
    /**
     * dduCreate
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docAppl
     * @param docType
     * @param compress
     * @param encoding
     * @param destMarket
     * @param destApplic
     * @param destUser
     * 
     * @param _docId
     * @param _return
     * 
     * @return	0 OK, else -1
     */
        public int dduCreate(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, 
        						java.lang.String docAppl, java.lang.String docType, java.lang.String compress, java.lang.String encoding, 
        						java.lang.String destMarket, java.lang.String destApplic, java.lang.String destUser, 
        						javax.xml.rpc.holders.StringHolder _docId, javax.xml.rpc.holders.StringHolder _return) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
		    
			MessageFactory factory = MessageFactory.newInstance();
		    
			SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduCreate",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestMarket").addTextNode(destMarket).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestApplic").addTextNode(destApplic).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DestUser").addTextNode(destUser).setAttribute("xsi:type","xsd:string");

		    message.saveChanges();
		    
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
		    Name bodyName_resp;
	
			bodyName_resp = soapFactory.createName("dduCreateResponse",
			   									   "ns1",
												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {

			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

					if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {
						_docId.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }
		    }
		    
		    connection.close();
	    
		} catch (SOAPException se) {
			this.dduSoapError = DDU_Const_Error.CREATE;
			rc = -1;
		} catch (Exception e) {
			this.dduSoapError = DDU_Const_Error.CREATE;
			rc = -1;
		}
    	
    	return rc;
    }
    
    
    
    /**
     * dduWrite
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docId
     * @param offset
     * @param buffer
     * @param _length
     * @param compress
     * @encoding encoding
     * 
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduWrite(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, 
    						java.lang.String docId, java.lang.String offset, java.lang.String buffer, javax.xml.rpc.holders.StringHolder _length, 
    						java.lang.String compress, java.lang.String encoding, javax.xml.rpc.holders.StringHolder _return) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 

			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduWrite",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Offset").addTextNode(offset).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Buffer").addTextNode(buffer).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Length").addTextNode(_length.value).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");

		    message.saveChanges();
		    
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
		    Name bodyName_resp;
	
			bodyName_resp = soapFactory.createName("dduWriteResponse",
			   									   "ns1",
												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {

			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

					if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {
						_length.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 2) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }
		    }

		    connection.close();
	    
		} catch (SOAPException se) {
			this.dduSoapError = DDU_Const_Error.WRITE;
			rc = -1;
		} catch (Exception e) {
			this.dduSoapError = DDU_Const_Error.WRITE;
			rc = -1;
		}
    	
    	return rc;
    }



    /**
     * dduClose
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docId
     * 
     * @return	0 OK, else -1
     */
    public int dduClose(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, 
    						java.lang.String docId, javax.xml.rpc.holders.StringHolder _return	) {
    	
    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");
	
			SOAPBody body = env.getBody();
	
		    Name bodyName = soapFactory.createName("dduClose",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");

		    message.saveChanges();
		    
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
		    Name bodyName_resp;
	
			bodyName_resp = soapFactory.createName("dduCloseResponse",
			   									   "ns1",
												   "urn:ddu");
			
			SOAPBody body_resp = response.getSOAPBody();
			
			this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);
		    
		    if (rc == 0) {
				
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();
				
			    int i = 0;
			    
			    while (ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();

					if (i == 0) {
						_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}
					else if (i == 1) {
						_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
					}

					i++;
			    }
			}

		    connection.close();
	    
		} catch (SOAPException se) {
			this.dduSoapError = DDU_Const_Error.CLOSE;
			rc = -1;
		} catch (Exception e) {
			this.dduSoapError = DDU_Const_Error.CLOSE;
			rc = -1;
		}
    	
    	return rc;
    }
    
    
    
    /**
     * dduGet
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param docId
     * @param docAppl
     * @param docType
     * @param compress
     * @param encoding
     * @param operation
     * @param chunkLength
     * 
     * @param _buffer
     * @param _length
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduGet(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, java.lang.String docId, 
    					java.lang.String docAppl, java.lang.String docType, java.lang.String compress, java.lang.String encoding, 
    					java.lang.String operation,	java.lang.String chunkLength, javax.xml.rpc.holders.StringHolder _buffer, 
    					javax.xml.rpc.holders.StringHolder _length, javax.xml.rpc.holders.StringHolder _return	) {

    	int rc = 0;
    	
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();
			
		    MessageFactory factory = MessageFactory.newInstance();
		    
		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();
	
			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduGet",
								    			   "ns",
								    			   "urn:ddu");
	
		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");
			
		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocId").addTextNode(docId).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("Operation").addTextNode(operation).setAttribute("xsi:type","xsd:string");
	
		    be.addChildElement("ChunkLength").addTextNode(chunkLength).setAttribute("xsi:type","xsd:string");
		    
		    message.saveChanges();
		    
		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
//		    Name bodyName_resp;
//	
//			bodyName_resp = soapFactory.createName("dduGetResponse",
//			   									   "ns1",
//												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Node firstChild = body_resp.getFirstChild();
			    
			    _ticket.value	= ((firstChild.getChildNodes()).item((0)).getFirstChild()).getNodeValue();
			    
			    _buffer.value	= ((firstChild.getChildNodes()).item((1)).getFirstChild()).getNodeValue();
			    
			    _length.value	= ((firstChild.getChildNodes()).item((2)).getFirstChild()).getNodeValue();
			    
			    _return.value	= ((firstChild.getChildNodes()).item((3)).getFirstChild()).getNodeValue();
		    }
		    
		    connection.close();
	    
		} catch (SOAPException se) {
			this.dduSoapError = DDU_Const_Error.GET;
			rc = -1;
		} catch (Exception e) {
			this.dduSoapError = DDU_Const_Error.GET;
			rc = -1;
		}
    	
    	return rc;
    }
    
    
    
    /**
     * dduPost
     * 
     * @param _ticket
     * @param serviceId
     * @param loginId
     * @param _docId
     * @param docAppl
     * @param docType
     * @param compress
     * @param encoding
     * @param operation
     * @param chunkLength
     * @param buffer
     * @param destMarket
     * @param destApplic
     * @param destUser
     * 
     * @param _length
     * @param _return
     * 
     * @return	0 OK, else -1
     */
    public int dduPost(	javax.xml.rpc.holders.StringHolder _ticket, java.lang.String serviceId, java.lang.String loginId, 
    					javax.xml.rpc.holders.StringHolder _docId, java.lang.String docAppl, java.lang.String docType, java.lang.String compress, 
    					java.lang.String encoding, java.lang.String operation, java.lang.String chunkLength, java.lang.String buffer, 
    					java.lang.String destMarket, java.lang.String destApplic, java.lang.String destUser, 
    					javax.xml.rpc.holders.StringHolder _length, javax.xml.rpc.holders.StringHolder _return	) {

    	int rc = 0;
    	
		try {

			SOAPFactory soapFactory = SOAPFactory.newInstance();

		    MessageFactory factory = MessageFactory.newInstance();

		    SOAPMessage message = factory.createMessage();

			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduPost",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("ServiceId").addTextNode(serviceId).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("LoginId").addTextNode(loginId).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DocId").addTextNode(_docId.value).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DocAppl").addTextNode(docAppl).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocType").addTextNode(docType).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Compress").addTextNode(compress).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Encoding").addTextNode(encoding).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Operation").addTextNode(operation).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("ChunkLength").addTextNode(chunkLength).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("Buffer").addTextNode(buffer).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DestMarket").addTextNode(destMarket).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DestApplic").addTextNode(destApplic).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("DestUser").addTextNode(destUser).setAttribute("xsi:type","xsd:string");

		    message.saveChanges();

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);
	
//		    Name bodyName_resp;
//	
//			bodyName_resp = soapFactory.createName("dduPostResponse",
//			   									   "ns1",
//												   "urn:ddu");

		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();

		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    NodeList nodes = body_resp.getChildNodes();

			    NodeList childNodes = nodes.item((0)).getChildNodes();

			    NodeList nodeList = (childNodes.item(0)).getChildNodes();

			    _ticket.value = nodeList.item((0)).getNodeValue();

			    nodeList = (childNodes.item(1)).getChildNodes();

			    _docId.value = nodeList.item((0)).getNodeValue();

			    nodeList = (childNodes.item(2)).getChildNodes();

			    _length.value = nodeList.item((0)).getNodeValue();
			    
			    nodeList = (childNodes.item(3)).getChildNodes();
			    
			    _return.value = nodeList.item((0)).getNodeValue();
		    }
		    
		    connection.close();
	    
		} catch (SOAPException se) {
			this.dduSoapError = DDU_Const_Error.POST;
			rc = -1;
		} catch (Exception e) {
			this.dduSoapError = DDU_Const_Error.POST;
			rc = -1;
		}
    	
    	return rc;
    }

	
	
	/**
	 * dduCommit
	 * 
	 * @param _ticket
	 * @param serviceID
	 * @param loginID
	 * @param docID
	 * 
	 * @param _return
	 * 
	 * @return	0 OK, else -1
	 */
	public int dduCommit(	StringHolder _ticket, String serviceID, String loginID, String docID, StringHolder _return	) {
		
		int rc = 0;
		
		try {
			
			SOAPFactory soapFactory = SOAPFactory.newInstance();

		    MessageFactory factory = MessageFactory.newInstance();

		    SOAPMessage message = factory.createMessage();
		    
			SOAPPart sp = message.getSOAPPart();

			SOAPEnvelope env = sp.getEnvelope();

			/* set Namespace */
			env.addNamespaceDeclaration("xsi", "http://www.w3.org/2001/XMLSchema-instance");
			env.addNamespaceDeclaration("xsd", "http://www.w3.org/2001/XMLSchema"); 
			
			/* set encoding */
			env.setEncodingStyle("http://schemas.xmlsoap.org/soap/encoding/");

			SOAPBody body = env.getBody();

		    Name bodyName = soapFactory.createName("dduCommit",
								    			   "ns",
								    			   "urn:ddu");

		    SOAPBodyElement be = body.addBodyElement(bodyName);
		    
		    be.addChildElement("Ticket").addTextNode(_ticket.value).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("ServiceId").addTextNode(serviceID).setAttribute("xsi:type","xsd:string");

		    be.addChildElement("LoginId").addTextNode(loginID).setAttribute("xsi:type","xsd:string");
		    
		    be.addChildElement("DocId").addTextNode(docID).setAttribute("xsi:type","xsd:string");

		    SOAPConnectionFactory connFactory = SOAPConnectionFactory.newInstance();
		    
		    SOAPConnection connection = connFactory.createConnection();
		    
		    /* call service */
		    SOAPMessage response = connection.call(message, this.endpoint);				    

		    
		    Name bodyName_resp = soapFactory.createName("dduCommitResponse",
														"ns1",
														"urn:ddu");
		    
		    SOAPBody body_resp = response.getSOAPBody();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    this.lastBodyResp = body_resp.toString();
		    
		    /* check response */
		    rc = this.checkResponse(body_resp);

		    if (rc == 0) {
		    	
			    Iterator ReturnedBodyElementIterator = body_resp.getChildElements(bodyName_resp);
			    
			    SOAPBodyElement ReturnedBodyElement = (SOAPBodyElement)ReturnedBodyElementIterator.next();
			    
			    Iterator ElementsIterator = ReturnedBodyElement.getChildElements();

			    int i = 0;
			    
			    while(ElementsIterator.hasNext()) {
			    	
			    	SOAPElement sElem = (SOAPElement)ElementsIterator.next();
			    	
			    	if (i == 0) {
			    		_ticket.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	else if (i == 1) {
			    		_return.value = ((sElem.getChildNodes()).item(0)).getNodeValue();
			    	}
			    	
			    	i++;
			    }
		    }

		    connection.close();

		} catch (SOAPException se) {
			
			
			this.dduSoapError = DDU_Const_Error.COMMIT;
			this.dduSoapEx = new Exception();
			this.dduSoapEx = se;
			rc = -1;
			
		} catch (Exception e) {
			
			
			this.dduSoapError = DDU_Const_Error.COMMIT;
			this.dduSoapEx = new Exception();
			this.dduSoapEx = e;
			rc = -1;
		}

		return rc;
	}


    /**
     * Verifica la response e, in caso di errore, setta this.dduSoapError
     * 
     * @param b_resp	SOAPBody response da verificare
     * 
     * @return	0 OK, else -1
     */
    private int checkResponse(SOAPBody b_resp) {
    	
    	int rc = 0;
    	
	    NodeList childNodes = b_resp.getChildNodes();
	    Node item = childNodes.item(0);
	    Node fault_0 = (item.getChildNodes()).item(0);
	    Node fault_1 = (item.getChildNodes()).item(1);
	    
	    this.lastStep = 3;
	    
	    if (fault_0 != null && (fault_0.getNodeName()).equals("faultstring")) {
	    	
	    	this.lastStep = 4;
			
	    	String cto = "Connection timed out";

	    	/* Se la stringa cto è presente in fault ottengo un indice maggiore di -1 */
	    	if ((fault_1.toString()).indexOf(cto) > -1) {
	    		
	    		this.lastStep = 5;
				
	    		rc = -1;
		    	this.dduSoapError = DDU_Const_Error.CONNECTION_TIMED_OUT;
			}
	    	/* errore generico */
	    	else {
	    		
	    		this.lastStep = 6;
	    		
		    	rc = -1;
		    	this.dduSoapError = DDU_Const_Error.SERVER_ERROR;
	    	}
		}
	    
	    if (fault_1 != null && (fault_1.getNodeName()).equals("faultstring")) {
	    	
	    	this.lastStep = 7;
			
	    	String cto = "Connection timed out";

	    	/* Se la stringa cto è presente in fault ottengo un indice maggiore di -1 */
	    	if ((fault_1.toString()).indexOf(cto) > -1) {
	    		
	    		this.lastStep = 8;
				
	    		rc = -1;
		    	this.dduSoapError = DDU_Const_Error.CONNECTION_TIMED_OUT;
			}
	    	/* errore generico */
	    	else {
	    		
	    		this.lastStep = 9;
	    		
		    	rc = -1;
		    	this.dduSoapError = DDU_Const_Error.SERVER_ERROR;
	    	}
		}
	    
    	return rc;
    }
}
