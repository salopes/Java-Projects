package AccessDDU;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import javax.xml.rpc.holders.StringHolder;

import org.apache.axis.holders.DataHandlerHolder;

import AccessDDU.DduFileEx;
import AccessDDU.ddu_soap;
import AccessDDU.DirFiles_ExHolder;



/**
 * 			        /|
 * 			 \      ||
 * 			  \  /\ ||
 * 			   \/  \||
 *
 * Main interface.
 * Classe di interfaccia ai web services esposti dal DDU.
 *
 * @author Tullio Ferralis - While1 s.r.l.
 *
 * @version 2.0	-	20071119
 * @version 2.2 - 	20080125
 * @version 2.3	-	20080225
 * @version 2.4	-	20080318
 * @version 2.5	-	20080326
 */

public class DDU_Interface {

	private int lastError = 0;
	private Exception lastEx = null;

	/*
	 * Variabili istanza che verranno inizializzate con dati prelevati dal
	 * file di configurazione config.ini
	 */
	private String user					= null;
	private String password				= null;
	private String brand				= null;
	private String sincom				= null;
	private String certificationSystem	= null;
	private String market				= null;
	private String appl					= null;

	private String logFileName			= null;
	private String pathTmpDir			= null;		// path della directory temporanea in cui verranno salvati i file ricevuti
	private String urlWsConnect			= null;		// url web services connect
	private String urlWs				= null;		// url web services
	private String serviceID			= null;		// identificativo della connessione
	private String loginID				= null;		// identificativo login
	private String ticket				= null;

	private String buffSizeDw			= null;		// dimensione buffer per ricezione chunks (espressa in byte)
	private String buffSizeUp			= null;		// dimensione buffer per invio chunks (espressa in byte)

	private String buffSizeAttDw		= null;		// dimensione buffer per ricezione chunks in attachment (espressa in byte)
	private String buffSizeAttUp		= null;		// dimensione buffer per invio chunks in attachment (espressa in byte)

	private int intBuffSizeDw			= 0;		// dimensione buffer per ricezione chunks (espressa in byte)
	private int intBuffSizeUp			= 0;		// dimensione buffer per invio chunks (espressa in byte)

	private int intBuffSizeAttDw		= 0;		// dimensione buffer per ricezione chunks in attachment (espressa in byte)
	private int intBuffSizeAttUp		= 0;		// dimensione buffer per invio chunks in attachment (espressa in byte)



	private ddu_soap ddu			= null;



	/**
	 * DDU_Interface
	 *
	 * Costruttore.
	 * Inizializza le variabili istanza leggendo i dati dal file "config.ini"
	 *
	 * @param inputIniDir - Path assoluto della cartella in cui si trova il file "config.ini"
	 */
	public DDU_Interface(String inputIniDir) {

		this.lastEx = new Exception();

		/* prelevo i dati dal file "config.ini" e inizializzo il file di log */
		this.load_config(inputIniDir);

		ddu = new ddu_soap();

		/* set endpoint su ws Connect */
		int rc = ddu.ddu_soapInit(this.urlWsConnect);

		if (rc != 0) {

			this.lastError = DDU_Const_Error.URL_MALFORMED;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface", DDU_Const_Error.URL_MALFORMED, "MalformedURLException");

			return;
		}
	}



	/**
	 * getLastError
	 *
	 * Restituisce l'ultimo errore occorso
	 *
	 * @return lastError
	 */
	public int getLastError() {

		return this.lastError;
	}

	/**
	 * get_dduEx
	 *
	 * Restituisce l'ultima eccezione occorsa
	 *
	 * @return lastException
	 */
	public Exception get_dduEx() {

		return this.lastEx;
	}


	/**
	 * login
	 *
	 * Effettua il login al DDU chiamando i servizi dduConnect e dduLogin.
	 *
	 * @return 0 OK, -1 altrimenti
	 */
	public int login() {

		StringHolder _ticket		= new StringHolder();
		StringHolder _servID		= new StringHolder();
		StringHolder _logID			= new StringHolder();
		StringHolder _return		= new StringHolder();

		int rc = 0;

		try {

			/* chiamata al ws dduConnect, ottengo il serviceID */
			rc = ddu.dduConnect(this.user, this.password, this.certificationSystem, _ticket, _servID, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws */
				if ("0".equals(_return.value)) {

					/* set serviceID */
					this.serviceID = _servID.value;

					/* Save ticket */
					this.ticket = _ticket.value;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "dduConnectt", DDU_Const_Error.OK, "Ticket: "+this.ticket);

					/* set endpoint per altri ws ddu */
					rc = ddu.ddu_soapInit(this.urlWs);

					if (rc != 0) {

						this.lastError = DDU_Const_Error.URL_MALFORMED;

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface", DDU_Const_Error.URL_MALFORMED, "MalformedURLException");

						return -1;
					}

					/* trace */
					DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:login", DDU_Const_Error.OK, "dduConnect OK - serviceID: "+this.serviceID+" - return: "+_return.value);
				}
				else if ((Integer.parseInt(_ticket.value) < 0)){

					/* set lastError */
					this.lastError = DDU_Const_Error.TICKET_REQUEST_ERROR;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduConnect KO - serviceID: "+this.serviceID+" - ticket: "+_ticket.value+" - return: "+_return.value);

					/* return KO */
					return rc = -1;
				}
				else {

					/* set lastError */
					this.lastError = DDU_Const_Error.CONNECT;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduConnect KO - serviceID: "+this.serviceID+" - return: "+_return.value);

					/* return KO */
					return rc = -1;
				}

				/* "azzero" il valore di return prima di riutilizzarlo */
				_return.value = "";

				StringHolder _tck = new StringHolder();

				_tck.value = this.ticket;

				/* chiamata al servizio dduLogin, ottengo il loginID */
				rc = ddu.dduLogin(	_tck, this.serviceID, this.market, this.appl, this.brand, this.sincom, this.certificationSystem, _logID,
									_return);

				if (rc == 0) {

					/* verifico valore di ritorno del ws*/
					if ("0".equals(_return.value)) {

						/* set loginID*/
						this.loginID = _logID.value;

						/* Save new ticket */
						this.ticket = _tck.value;

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "dduLogin", DDU_Const_Error.OK, "Ticket: "+this.ticket);

						/* trace */
						DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:login", DDU_Const_Error.OK, "dduLogin OK - loginId: "+this.loginID+" - return: "+_return.value);
					}
					else {

						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0) {

							this.ticketError(_tck.value, "login", "dduLogin");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservice dduLogin */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = DDU_Const_Error.LOGIN;

							/* trace */
							DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - loginID: "+this.loginID+" - return: "+_return.value);

							this.lastEx = ddu.get_dduSoapEx();

							if (this.lastEx != null) {

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - dduEx: "+this.lastEx);
							}

							/* trace */
							DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - body_resp and lastStep: "+ddu.get_dduSoapString());

							/* return KO */
							return rc = -1;
						}
					}
				}
				/* ddu_soap Error */
				else {

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - dduSoapError: "+this.lastError);

					this.lastEx = ddu.get_dduSoapEx();

					if (this.lastEx != null) {

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - dduSoapError -dduEx: "+this.lastEx);
					}

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

					/* return KO */
					return rc = -1;
				}
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduConnect KO - dduSoapError: "+this.lastError);

				this.lastEx = ddu.get_dduSoapEx();

				if (this.lastEx != null) {

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduConnect KO - dduSoapError - dduEx: "+this.lastEx);
				}

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduConnect KO - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

				/* return KO */
				return rc = -1;
			}

		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.LOGIN_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "dduLogin KO - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* creo directory temporanea */
		rc = DDU_Int_File.mkdir(this.pathTmpDir);

		if (rc != 0) {

			/* set lastError */
			this.lastError = DDU_Const_Error.TMP_DIR;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:login", this.lastError, "Errore nella creazione della directory temporanea: "+this.pathTmpDir);
		}

		/* return 0 OK, else -1 */
		return rc;
	}



	/**
	 * logout
	 *
	 * Chiama il web service dduLogout
	 *
	 * @return 0 OK, -1 altrimenti
	 */
	public int logout() {

		int rc = 0;

		StringHolder _return	= new StringHolder();

		try {

			/* chiamata al ws dduLogout */
			rc = ddu.dduLogout(this.ticket, this.serviceID, this.loginID, _return);

			if ((rc != 0) || (!("0".equals(_return.value)))) {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:logout", this.lastError, "dduLogout KO - dduSoapError: "+this.lastError);
			}

		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.LOGOUT_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:logout", this.lastError, "dduLogout KO - Exception: "+e);

			return rc = -1;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:logout", this.lastError, "dduLogout OK");

		this.serviceID	= null;
		this.loginID	= null;

		/* return 0 OK, else -1 */
		return rc;
	}



	/**
	 * dir
	 *
	 * - Chiama il ws dduDir e restituisce la struttura DduFileEx.
	 *   Il ws dduDir restituisce al massimo 500 file, in questo caso � consigliabile fare il download dei file desiderati
	 *   e ripetere la chiamata alla dir.
	 *
	 * @param docType tipo dei file di cui prelevare le informazioni.
	 *
	 * @return la struttura DduFileEx(con massimo 500 elementi), null in caso di errore
	 */
	public DduFileEx[] dir(String docType, String dtStart, String dtEnd) {

		int rc = 0;

		StringHolder _return		= new StringHolder();
		DirFiles_ExHolder _dirFEH	= new DirFiles_ExHolder();
		StringHolder _tck = new StringHolder();

		_tck.value = this.ticket;

		/* DIR_TYPE_DOWNLODABLE */
		rc = ddu.dduDir(	_tck, this.serviceID, this.loginID, DDU_Const_Value.DIR_TYPE_DOWNLODABLE, "", docType, "", dtStart, dtEnd, DDU_Const_Value.DATE_FORMAT, _dirFEH, _return	);
		
		if (rc == 0) {

			/* verifico valore di ritorno del ws*/
			if(Integer.parseInt(_return.value) < 0) {

				int tck_int = 0;

				/* Verifico se l'errore � causato da ticket scaduto o errato */
				try {

					tck_int = Integer.parseInt(_tck.value);

				} catch (java.lang.NumberFormatException NFEx) {

					tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
				}

				if(tck_int < 0)
				{
					this.ticketError(_tck.value, "dir", "dduDir");

					/* return KO */
					return null;
				}
				/* L'errore � del webservices dduDir */
				else {

					/* Salvo comunque il ticket ottenuto */
					this.ticket = _tck.value;

					/* set lastError */
					this.lastError = DDU_Const_Error.DIR;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - return: "+_return.value);

					this.lastEx = ddu.get_dduSoapEx();

					if (this.lastEx != null) {

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - dduEx: "+this.lastEx);
					}

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - body_resp and lastStep: "+ddu.get_dduSoapString());

					/* return KO */
					return null;
				}
			}

			/* Save new ticket */
			this.ticket = _tck.value;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "dduDir", DDU_Const_Error.OK, "Ticket: "+this.ticket);

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:dir", DDU_Const_Error.OK, "dduDir OK: "+" - Lunghezza struttura: "+(_dirFEH.value).length+" - return: "+_return.value);

			/* Se non ci sono file restituisco null e lastError = 0 */
			if (_dirFEH.value.length == 1 && "".equals(_dirFEH.value[0].getFileid())) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:dir", DDU_Const_Error.OK, "dduDir OK: Non ci sono file da scaricare.");

				/* set lastError - NO ERROR!!! */
				this.lastError = DDU_Const_Error.OK;

				return null;
			}

			return _dirFEH.value;

		}
		/* ddu_soap Error */
		else {

			/* set lastError */
			this.lastError = ddu.get_dduSoapError();

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - dduSoapError: "+this.lastError);

			this.lastEx = ddu.get_dduSoapEx();

			if (this.lastEx != null) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - dduSoapError - dduEx: "+this.lastEx);
			}

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:Dir", this.lastError, "dduDir KO - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

			/* return KO */
			return null;
		}
	}




	/**
	 * download
	 *
	 * - effettua una serie di chiamate al ws dduRead per ricevere il file
	 * - decodifica il file e lo decomprime
	 *
	 * @param	dduFile		Oggetto che contiene tutti i dati relativi al file da scaricare
	 * @param	destPath	path assoluto della cartella in cui scaricare il file (comprensivo del nome del file)
	 *
	 * @return docId OK, else -1
	 */
	public int download(DduFileEx dduFile, String destPath) {

		String fileSeparator	=  System.getProperty("file.separator");

		int rc = 0;

		StringHolder _olength	= new StringHolder();
		StringHolder _buffer	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();

		try {

			_tck.value = this.ticket;

			/* chiamata al ws dduOpen, ottengo il fileID da utilizzare nella dduRead */
			rc = ddu.dduOpen(	_tck, this.serviceID, this.loginID, dduFile.getFileid(), this.appl, dduFile.getFileName(),
								"", DDU_Const_Value.ASCII, _return);

			if (rc == 0) {

        		/* verifico valore di ritorno del ws */
				if (Integer.parseInt(_return.value) < 0) {

					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "download", "dduOpen");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduOpen */
					else{

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.OPEN;
						}

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "Error - dduOpen - fileID: "+dduFile.getFileid()+" - return: "+_return.value);

						/* return KO */
						return rc = -1;
					}
				}

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "dduOpen OK - fileID: "+dduFile.getFileid()+" - return: "+_return.value);
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "download (dduOpen) KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}


		/* -- Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.OPEN_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "Error - dduOpen - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* creo file di destinazione nella directory temporanea aggiungento al nome del file i millisecondi correnti */
		Long currentTimeMillis	= new Long(System.currentTimeMillis());
		String ctm				= new String(currentTimeMillis.toString());
		File fd					= new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
		fd.deleteOnExit();

		if (fd == null) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", DDU_Const_Error.IO, "Errore creazione file temporaneo: "+dduFile.getFileid()+"_"+ctm);

			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

		} else {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 1, "DDU_Interface:download", DDU_Const_Error.OK, "Creato file temporaneo: "+dduFile.getFileid()+"_"+ctm);

			FileOutputStream fos	= null;
			PrintStream Output		= null;

			try {

				/* open destination file in append mode */
				fos		= new FileOutputStream(fd, true);
				Output	= new PrintStream(fos);


			/* -- Catch Exception -- */
			} catch (FileNotFoundException e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.FILE_NF;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "error open destination file in dduCreate - FileNotFoundException: "+e);

				/* return KO */
				return rc = -1;
			}

			try {

				int offset	= 0;

				_olength.value = this.buffSizeDw;

				/* get ticket */
				_tck.value = this.ticket;

				/* serie di chiamate al ws dduRead */
				while (Integer.parseInt(_olength.value) == this.intBuffSizeDw) {

					rc = ddu.dduRead(	_tck, this.serviceID, this.loginID,  dduFile.getFileid(), String.valueOf(offset), _olength,
										"", DDU_Const_Value.ASCII, _buffer, _return);

					if (rc == 0) {

						/* verifico valore di ritorno del ws */
						if (Integer.parseInt(_return.value) < 0) {

							/* Close local file */
							Output.close();
							fos.close();

							/* Verifico se l'errore � causato da ticket scaduto o errato */
							int tck_int = 0;

							/* Verifico se l'errore � causato da ticket scaduto o errato */
							try {

								tck_int = Integer.parseInt(_tck.value);

							} catch (java.lang.NumberFormatException NFEx) {

								tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
							}

							if(tck_int < 0)
							{
								this.ticketError(_tck.value, "download", "dduRead");

								/* return KO */
								return rc = -1;
							}
							/* L'errore � del webservices dduRead */
							else {

								/* Salvo comunque il ticket ottenuto */
								this.ticket = _tck.value;

								/* set lastError */
								this.lastError = error_map(Integer.parseInt(_return.value));

								if (this.lastError == -1) {

									this.lastError = DDU_Const_Error.READ;
								}

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "dduRead KO - byte ricevuti: "+_olength.value+" - return: "+_return.value);

								/* return KO */
								return rc = -1;
							}
						}

						/* verifico valore di ritorno del ws*/
						if ((_return.value).equals("0")  && !("0".equals(_olength.value))) {

							Output.print(_buffer.value);

							/*
							 * aggiorno l'offset, sommandogli il numero di byte ricevuti diviso 2 perch� sto lavorando con encoding
							 * quindi se ho ricevuto 100k sono in realt� 50k del file originale
							 */
							offset = offset + (Integer.parseInt(_olength.value) / 2);

							/* trace */
							DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "dduRead OK - byte ricevuti: "+_olength.value);
						}
					}
					/* ddu_soap Error */
					else {

						/* Close local file */
						Output.close();
						fos.close();

						/* set lastError */
						this.lastError = ddu.get_dduSoapError();

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "download (dduRead) KO - dduSoapError: "+this.lastError);

						/* return KO */
						return rc;
					}
				}

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "Terminato il download del file! docId: "+dduFile.getFileid());

				/* "azzero" il valore di return prima di riutilizzarlo */
				_return.value = "";

				_tck.value = this.ticket;

				/* close remote file - dduClose */
				rc = ddu.dduClose(_tck, this.serviceID, this.loginID,  dduFile.getFileid(), _return);

				if (rc == 0) {

	        		/* verifico valore di ritorno del ws */
					if (Integer.parseInt(_return.value) < 0) {

						/* Close local file */
						Output.close();
						fos.close();


						/* Verifico se l'errore � causato da ticket scaduto o errato */
						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "download", "dduClose");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduClose */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.CLOSE;
							}

							/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "error dduClose - return: "+_return.value);

							/* return KO */
							return rc = -1;
						}
					}

					/* Save new ticket */
					this.ticket = _tck.value;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "dduClose OK");
				}
				/* ddu_soap Error */
				else {

					/* Close local file */
					Output.close();
					fos.close();

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "dduClose - _return: "+_return.value);

					/* return KO */
					return rc;
				}


				/* Close local file */
			    Output.close();
			    fos.close();

			    /* get ticket */
			    _tck.value = this.ticket;

				/* chiamata al ws dduCommit */
				rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, dduFile.getFileid(), _return);

				if (rc == 0) {

					/* verifico valore di ritorno del ws*/
					if (!"0".equals(_return.value)) {

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "download", "dduCommit");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduCommit */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.COMMIT;
							}


			        		/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "dduCommit KO - return: "+_return.value);

			        		/* return KO */
			        		return rc = -1;
						}
					}

					/* Save new ticket */
					this.ticket = _tck.value;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "dduCommit OK");
				}
				/* ddu_soap Error */
				else {

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "dduCommit KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
				}


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.READ_EX;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "Error dduRead - Exception: "+e);

				/* return KO */
				return rc = -1;
			}

			/* decode file */
			rc = DDU_Int_File.decodeFile(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			if (rc != 0) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", DDU_Const_Error.DECODE, "Errore in fase di decodifica del file");

				/* set lastError */
				this.lastError = DDU_Const_Error.DECODE;

				/* return KO */
				return rc = -1;
			}

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "Decode ok");

			/* Decompress file */
			rc = DDU_Int_File.unZipFile(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			if (rc != 0) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", DDU_Const_Error.UNZIP, "Errore in fase di unzip del file");

				/* set lastError */
				this.lastError = DDU_Const_Error.UNZIP;

				/* return KO */
				return rc = -1;
			}

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, "Unzip ok");

			try {

				/* move file from tmpDir to destPath */
				File fdSrc = new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
				File fdDst = new File(destPath);
				fdSrc.renameTo(fdDst);

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download", DDU_Const_Error.OK, " move file from tmpDir to destPath, ok");


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.KO;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download", this.lastError, "move file from tmpDir to destPath, ko - Exception: "+e);

				/* return KO */
				return rc = -1;
			}
		}

		/* OK, return fileId */
		return Integer.parseInt(dduFile.getFileid());
	}



	/**
	 * upload
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduWrite per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 * @param destMarket	mercato destinazione
	 * @param destUser		user destinazione
	 *
	 * @return docId OK, else -1
	 */
	public int upload(String srcPath, String docType, String destMarket, String destUser, String destAppl) {

		String fileSeparator =  System.getProperty("file.separator");

		int rc		= 0;
		int index	= 0;

		StringHolder _docId		= new StringHolder();
		StringHolder _length	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();

		File fd;

       	File fd_temp = new File(srcPath);

       	if (!fd_temp.exists()) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

	       	/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "Source File Not Found: "+srcPath);

			/* return KO */
			return -1;
		}

		/* prelevo il nome del file dal path sorgente */
		String tmpFileName		= null;

		index		= srcPath.lastIndexOf(fileSeparator);
       	tmpFileName	= srcPath.substring(index+1);

		/* zip file - return name file with currentTime Millis */
       	tmpFileName = DDU_Int_File.zipFile(srcPath, this.pathTmpDir);

       	fd = new File(this.pathTmpDir+fileSeparator+tmpFileName);
       	fd.deleteOnExit();

		if (tmpFileName == null) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", DDU_Const_Error.GZIP, "gzip error");

			/* set lastError */
			this.lastError = DDU_Const_Error.GZIP;

			/* return KO */
			return rc = -1;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.GZIP, "gzip ok");

		/* encode file */
		rc = DDU_Int_File.encodeFile(this.pathTmpDir+fileSeparator+tmpFileName);

		if (rc != 0) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", DDU_Const_Error.ENCODE, "encode error");

			/* set lastError */
			this.lastError = DDU_Const_Error.ENCODE;

			/* return KO */
			return rc;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "encode ok");

		try {

			/* get ticket */
			_tck.value = this.ticket;

			/* chiamata al ws dduCreate, ottengo il fileID da utilizzare nella dduRead */
			rc = ddu.dduCreate(	_tck, this.serviceID, this.loginID, this.appl, docType, "", DDU_Const_Value.ASCII, destMarket, destAppl,
								destUser, _docId, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws */
				if (!(_return.value).equals("0")) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload", "dduCreate");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCreate */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.CREATE;
						}

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduCreate - fileId: "+_docId.value+" - return: "+_return.value);

						/* return KO */
						return rc = -1;
					}
				}

				/* Save new Ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "dduCreate OK - fileId: "+_docId.value);
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "dduCreate KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}


		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.CREATE;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduCreate - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		int amount			= 0;
		int offset			= 0;
		int numChunk		= 0;

		boolean oneChunk	= true;
		byte fileData[]		= null;

		Integer chunkLength = new Integer(0);
		FileInputStream fis	= null;

		try {
			/* File descriptor del file da inviare, compresso e codificato */

			/* prelevo la dimensione in byte del file */
			Long fileLength = new Long(fd.length());

			fis = new FileInputStream(this.pathTmpDir+fileSeparator+tmpFileName);

			/*
			 * se la dimensione del file � inferiore o uguale alla dimensione del chunck
			 * il file viene inviato in un unico chunck
			 */
			if (fileLength.intValue() <= this.intBuffSizeUp) {

				chunkLength	= new Integer(fileLength.intValue());

				numChunk	= 1;

			} else {

				/* calcolo il numero di chunk necessari per inviare il file */

				chunkLength	= new Integer(this.intBuffSizeUp);

				numChunk	= (fileLength.intValue() / chunkLength.intValue()) +1;

			}

			fileData = new byte[chunkLength.intValue()];

			/* get ticket */
			_tck.value = this.ticket;

			/* serie di chiamate al ws dduWrite */
	        while (oneChunk && ((amount = fis.read(fileData, 0, chunkLength.intValue())) != -1)) {

	        	numChunk--;

	        	_length.value = String.valueOf(amount);

	        	rc = ddu.dduWrite(	_tck, this.serviceID, this.loginID, _docId.value, String.valueOf(offset), new String(fileData).substring(0, amount),
	        						_length, "", DDU_Const_Value.ASCII, _return);

	        	if (rc == 0) {

	        		/* verifico valore di ritorno del ws */
					if (!(_return.value).equals("0")) {

						/* Close local file */
						fis.close();

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "upload", "dduWrite");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduWrite */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.WRITE;
							}

							/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduWrite - return: "+_return.value);

							/* return KO */
							return rc = -1;
						}
					}

		        	/* trace */
		        	DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "dduWrite OK - byte inviati: "+_length.value);

		        	/* se numChunk == 0 sto inviando l'ultimo chunk */
		        	if (numChunk == 0) {

						oneChunk = false;
					}

		        	/* aggiorno l'offset sommandogli il numero di byte inviati */
		            offset = offset + Integer.parseInt(_length.value);
				}
	        	/* ddu_soap Error */
	        	else {

	        		/* Close local file */
	        		fis.close();

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "upload (dduWrite) KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
	        	}
	        }

	        /* Close local file */
	        fis.close();

	        /* Save new Ticket */
	        this.ticket = _tck.value;

	        /* trace */
	        DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "End send! docId: "+_docId.value);


	    /* -- Catch Exception -- */
		} catch (FileNotFoundException fnf_e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduCreate - FileNotFoundException: "+fnf_e);

			/* return KO */
			return rc = -1;

		} catch (IOException io_e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduCreate - IOException: "+io_e);

			/* return KO */
			return rc = -1;

		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.KO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduCreate - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		try {

			/* "azzero" il valore di return prima di riutilizzarlo */
			_return.value = "";

			/* get ticket */
			_tck.value = this.ticket;

			/* chiamata al ws dduClose */
			rc = ddu.dduClose(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

        		/* verifico valore di ritorno del ws */
				if (!(_return.value).equals("0")) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload", "dduClose");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduClose */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.CLOSE;
						}

						/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error dduClose - return: "+_return.value);

						/* return KO */
						return rc = -1;
					}
				}

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "dduClose OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "dduClose - _return: "+_return.value);

				/* return KO */
				return rc;
			}

			_tck.value = this.ticket;

			/* chiamata al ws dduCommit */
			rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws*/
				if (!"0".equals(_return.value)) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload", "dduCommit");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCommit */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.COMMIT;
						}

		        		/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "dduCommit KO - return: "+_return.value);

		        		/* return KO */
		        		return rc = -1;
					}
				}

				/* Save new Ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload", DDU_Const_Error.OK, "dduCommit OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "upload (dduCommit) KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}


		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.CLOSE_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload", this.lastError, "error - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* Delete temporany file */
		fd.delete();

		/* OK, return fileId */
		return Integer.parseInt(_docId.value);
	}



	/**
	 * upload
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduWrite per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 *
	 * @return docId OK, else -1
	 */
	public int upload(String srcPath, String docType, String destMarket, String destUser, String destAppl, String sAux) {

		return upload(srcPath, docType, destMarket, destUser, destAppl);
	}

// Nl upload2dwn

	/**
	 * upload2dwn
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduWrite per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 * @param destMarket	mercato destinazione
	 * @param destUser		user destinazione
	 *
	 * @return docId OK, else -1
	 */
	public int upload2dwn(String srcPath, String docType, String destMarket, String destUser, String destAppl) {

		String fileSeparator =  System.getProperty("file.separator");

		int rc		= 0;
		int index	= 0;

		StringHolder _docId		= new StringHolder();
		StringHolder _length	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();

		File fd;

       	File fd_temp = new File(srcPath);

       	if (!fd_temp.exists()) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

	       	/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "Source File Not Found: "+srcPath);

			/* return KO */
			return -1;
		}

		/* prelevo il nome del file dal path sorgente */
		String tmpFileName		= null;

		index		= srcPath.lastIndexOf(fileSeparator);
       	tmpFileName	= srcPath.substring(index+1);

		/* zip file - return name file with currentTime Millis */
       	tmpFileName = DDU_Int_File.zipFile(srcPath, this.pathTmpDir);

       	fd = new File(this.pathTmpDir+fileSeparator+tmpFileName);
       	fd.deleteOnExit();

		if (tmpFileName == null) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", DDU_Const_Error.GZIP, "gzip error");

			/* set lastError */
			this.lastError = DDU_Const_Error.GZIP;

			/* return KO */
			return rc = -1;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.GZIP, "gzip ok");

		/* encode file */
		rc = DDU_Int_File.encodeFile(this.pathTmpDir+fileSeparator+tmpFileName);

		if (rc != 0) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", DDU_Const_Error.ENCODE, "encode error");

			/* set lastError */
			this.lastError = DDU_Const_Error.ENCODE;

			/* return KO */
			return rc;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "encode ok");

		try {

			/* get ticket */
			_tck.value = this.ticket;

			/* chiamata al ws dduCreate, ottengo il fileID da utilizzare nella dduRead */
			//rc = ddu.dduCreate(	_tck, this.serviceID, this.loginID, this.appl, docType, "", DDU_Const_Value.ASCII, destMarket, destAppl, destUser, _docId, _return);
			rc = ddu.dduCreate(	_tck, this.serviceID, this.loginID, this.appl, docType, "", DDU_Const_Value.ASCII, destMarket, destAppl, destUser, _docId, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws */
				if (!(_return.value).equals("0")) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload2dwn", "dduCreate");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCreate */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.CREATE;
						}

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduCreate - fileId: "+_docId.value+" - return: "+_return.value);

						/* return KO */
						return rc = -1;
					}
				}

				/* Save new Ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "dduCreate OK - fileId: "+_docId.value);
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "dduCreate KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}


		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.CREATE;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduCreate - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		int amount			= 0;
		int offset			= 0;
		int numChunk		= 0;

		boolean oneChunk	= true;
		byte fileData[]		= null;

		Integer chunkLength = new Integer(0);
		FileInputStream fis	= null;

		try {
			/* File descriptor del file da inviare, compresso e codificato */

			/* prelevo la dimensione in byte del file */
			Long fileLength = new Long(fd.length());

			fis = new FileInputStream(this.pathTmpDir+fileSeparator+tmpFileName);

			/*
			 * se la dimensione del file � inferiore o uguale alla dimensione del chunck
			 * il file viene inviato in un unico chunck
			 */
			if (fileLength.intValue() <= this.intBuffSizeUp) {

				chunkLength	= new Integer(fileLength.intValue());

				numChunk	= 1;

			} else {

				/* calcolo il numero di chunk necessari per inviare il file */

				chunkLength	= new Integer(this.intBuffSizeUp);

				numChunk	= (fileLength.intValue() / chunkLength.intValue()) +1;

			}

			fileData = new byte[chunkLength.intValue()];

			/* get ticket */
			_tck.value = this.ticket;

			/* serie di chiamate al ws dduWrite */
	        while (oneChunk && ((amount = fis.read(fileData, 0, chunkLength.intValue())) != -1)) {

	        	numChunk--;

	        	_length.value = String.valueOf(amount);

	        	rc = ddu.dduWrite(	_tck, this.serviceID, this.loginID, _docId.value, String.valueOf(offset), new String(fileData).substring(0, amount),
	        						_length, "", DDU_Const_Value.ASCII, _return);

	        	if (rc == 0) {

	        		/* verifico valore di ritorno del ws */
					if (!(_return.value).equals("0")) {

						/* Close local file */
						fis.close();

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "upload2dwn", "dduWrite");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduWrite */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.WRITE;
							}

							/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduWrite - return: "+_return.value);

							/* return KO */
							return rc = -1;
						}
					}

		        	/* trace */
		        	DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "dduWrite OK - byte inviati: "+_length.value);

		        	/* se numChunk == 0 sto inviando l'ultimo chunk */
		        	if (numChunk == 0) {

						oneChunk = false;
					}

		        	/* aggiorno l'offset sommandogli il numero di byte inviati */
		            offset = offset + Integer.parseInt(_length.value);
				}
	        	/* ddu_soap Error */
	        	else {

	        		/* Close local file */
	        		fis.close();

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "upload2dwn (dduWrite) KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
	        	}
	        }

	        /* Close local file */
	        fis.close();

	        /* Save new Ticket */
	        this.ticket = _tck.value;

	        /* trace */
	        DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "End send! docId: "+_docId.value);


	    /* -- Catch Exception -- */
		} catch (FileNotFoundException fnf_e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduCreate - FileNotFoundException: "+fnf_e);

			/* return KO */
			return rc = -1;

		} catch (IOException io_e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduCreate - IOException: "+io_e);

			/* return KO */
			return rc = -1;

		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.KO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduCreate - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		try {

			/* "azzero" il valore di return prima di riutilizzarlo */
			_return.value = "";

			/* get ticket */
			_tck.value = this.ticket;

			/* chiamata al ws dduClose */
			rc = ddu.dduClose(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

        		/* verifico valore di ritorno del ws */
				if (!(_return.value).equals("0")) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload2dwn", "dduClose");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduClose */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.CLOSE;
						}

						/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error dduClose - return: "+_return.value);

						/* return KO */
						return rc = -1;
					}
				}

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "dduClose OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "dduClose - _return: "+_return.value);

				/* return KO */
				return rc;
			}

			_tck.value = this.ticket;

			/* chiamata al ws dduCommit */
			rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws*/
				if (!"0".equals(_return.value)) {

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload2dwn", "dduCommit");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCommit */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.COMMIT;
						}

		        		/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "dduCommit KO - return: "+_return.value);

		        		/* return KO */
		        		return rc = -1;
					}
				}

				/* Save new Ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload2dwn", DDU_Const_Error.OK, "dduCommit OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "upload2dwn (dduCommit) KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}


		/* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.CLOSE_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload2dwn", this.lastError, "error - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* Delete temporany file */
		fd.delete();

		/* OK, return fileId */
		return Integer.parseInt(_docId.value);
	}

	/**
	 * upload
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduWrite per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 *
	 * @return docId OK, else -1
	 */
	public int upload2dwn(String srcPath, String docType, String destMarket, String destUser, String destAppl, String sAux) {

		return upload2dwn(srcPath, docType, destMarket, destUser, destAppl);
	}

	
// Novo upload2dwn	

	/**
	 * download_get
	 *
	 * - effettua una serie di chiamate al ws dduGet per ricevere il file
	 * - decodifica il file e lo decomprime
	 *
	 * @param dduFile		Oggetto che contiene tutti i dati relativi al file da scaricare
	 * @param destPath		path assoluto della cartella in cui scaricare il file (comprensivo del nome del file)
	 *
	 * @return docId OK, else -1
	 */
	public int download_get(DduFileEx dduFile, String destPath) {

		int rc = 0;
		int offset = 0;

		String fileSeparator	=  System.getProperty("file.separator");

		StringHolder _length		= new StringHolder();
		StringHolder _buffer		= new StringHolder();
		StringHolder _tck			= new StringHolder();
		StringHolder _return		= new StringHolder();

		/* creo file di destinazione nella directory temporanea aggiungento al nome del file i millisecondi correnti */
		Long currentTimeMillis	= new Long(System.currentTimeMillis());
		String ctm				= new String(currentTimeMillis.toString());
		File fd					= new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
		fd.deleteOnExit();

		if (fd == null) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", DDU_Const_Error.IO, "Errore creazione file temporaneo: "+dduFile.getFileid()+"_"+ctm);

			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

		} else {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 1, "DDU_Interface:download_get", DDU_Const_Error.OK, "Creato file temporaneo: "+dduFile.getFileid()+"_"+ctm);

			FileOutputStream fos	= null;
			PrintStream Output		= null;

			try {

				/* open destination file in append mode */
				fos		= new FileOutputStream(fd, true);
				Output	= new PrintStream(fos);


			/* -- Catch Exception -- */
			} catch (FileNotFoundException fnf_e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.FILE_NF;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "error download_get - FileNotFoundException: "+fnf_e);

				/* return KO */
				return rc = -1;

			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.KO;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "error download_get - Exception: "+e);

				/* return KO */
				return rc = -1;
			}

			try {

				String operation	= "start";
				_tck.value = this.ticket;
				_length.value		= this.buffSizeDw;

				/* serie di chiamate al ws dduGet */
				while (Integer.parseInt(_length.value) == this.intBuffSizeDw) {

					_length.value = "0";

					rc = ddu.dduGet(	_tck, this.serviceID, this.loginID, dduFile.getFileid(), this.appl, dduFile.getFileName(),
								    	"", DDU_Const_Value.ASCII, operation, this.buffSizeDw, _buffer, _length, _return);

					if (rc == 0) {

						/* Calcola nuovo offset */
						offset = offset + (Integer.parseInt(_length.value) / 2);

						operation = "next:" + offset;

			       		/* verifico valore di ritorno del ws */
						if (!(_return.value).equals("0")) {

							/* Close local file */
						    Output.close();
						    fos.close();

							/* Verifico se l'errore � causato da ticket scaduto o errato */
							int tck_int = 0;

							/* Verifico se l'errore � causato da ticket scaduto o errato */
							try {

								tck_int = Integer.parseInt(_tck.value);

							} catch (java.lang.NumberFormatException NFEx) {

								tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
							}

							if(tck_int < 0)
							{
								this.ticketError(_tck.value, "download_get", "dduGet");

								/* return KO */
								return rc = -1;
							}
							/* L'errore � del webservices dduGet */
							else {

								/* Salvo comunque il ticket ottenuto */
								this.ticket = _tck.value;

								/* set lastError */
								this.lastError = error_map(Integer.parseInt(_return.value));

								if (this.lastError == -1) {

									this.lastError = DDU_Const_Error.GET;
								}

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "dduGet KO - byte ricevuti: "+_length.value+" - return: "+_return.value);

								/* return KO */
								return rc = -1;
							}
						}

						/* verifico valore di ritorno del ws*/
						if ((_return.value).equals("0")  && !("0".equals(_length.value))) {

							Output.print(_buffer.value);

							/* trace */
							DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, "dduGet OK - byte ricevuti: "+_length.value);
						}
					}
					/* ddu_soap Error */
					else {

						/* Close local file */
					    Output.close();
					    fos.close();

						/* set lastError */
						this.lastError = ddu.get_dduSoapError();

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "dduGet KO - dduSoapError: "+this.lastError);

						/* return KO */
						return rc;
					}
				}

				 /* Save new ticket */
			    this.ticket = _tck.value;

				/* Close local file */
			    Output.close();
			    fos.close();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, "Terminato il download del file! docId: "+dduFile.getFileid());

				/* "azzero" il valore di return prima di riutilizzarlo*/
				_return.value = "";

				/* get ticket */
				_tck.value = this.ticket;

				/* chiamata al ws dduCommit */
				rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, dduFile.getFileid(), _return);

				if (rc == 0) {

					/* verifico valore di ritorno del ws*/
					if (!"0".equals(_return.value)) {

						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "download_get", "dduCommit");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduCommit */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.COMMIT;
							}

			        		/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "dduCommit KO - return: "+_return.value);

			        		/* return KO */
			        		return rc = -1;
						}
					}

					/* Save new ticket */
					this.ticket = _tck.value;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, "dduCommit OK");
				}
				/* ddu_soap Error */
				else {

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "dduCommit KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
				}


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.GET_EX;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", this.lastError, "dduGet KO - Exception: "+e);

				/* return KO */
				return rc = -1;
			}

			/* decode file */
			rc = DDU_Int_File.decodeFile(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			if (rc != 0) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", DDU_Const_Error.DECODE, "Errore in fase di decodifica del file");

				/* set lastError */
				this.lastError = DDU_Const_Error.DECODE;

				/* return KO */
				return rc = -1;
			}

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, "Decode ok");

			/* Decompress file */
			rc = DDU_Int_File.unZipFile(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			if (rc != 0) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get", DDU_Const_Error.UNZIP, "Errore in fase di unzip del file");

				/* set lastError */
				this.lastError = DDU_Const_Error.UNZIP;

				/* return KO */
				return rc = -1;
			}

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, "Unzip ok");

			try {
				/* move file */
				File fdSrc = new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
				File fdDst = new File(destPath);
				fdSrc.renameTo(fdDst);

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_get", DDU_Const_Error.OK, " move file from tmpDir to destPath, ok");


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.KO;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_get.", this.lastError, "move file from tmpDir to destPath, ko - Exception: "+e);

				/* return KO */
				return rc = -1;
			}
		}

		/* return docId OK */
		return Integer.parseInt(dduFile.getFileid());
	}



	/**
	 * upload_post
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduPost per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 * @param destMarket	mercato destinazione
	 * @param destUser		user destinazione
	 *
	 * @return docId OK, else -1
	 */
	public int upload_post(String srcPath, String docType, String destMarket, String destUser, String destAppl) {
		int rc = 0;

		String fileSeparator	= System.getProperty("file.separator");

		StringHolder _docId		= new StringHolder();
		StringHolder _length	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();

		File fd;

		String tmpFileName	= null;		// conterr� il nome del file dopo compressione e codifica

       	File fd_temp = new File(srcPath);

       	if (!fd_temp.exists()) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

	       	/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "Source File Not Found: "+srcPath);

			/* return KO */
			return -1;
		}

		/* prelevo il nome del file dal path sorgente */
		int index			= srcPath.lastIndexOf(fileSeparator);
       	tmpFileName			= srcPath.substring(index+1);

		/* zip file */
       	tmpFileName = DDU_Int_File.zipFile(srcPath, this.pathTmpDir);

       	fd = new File(this.pathTmpDir+fileSeparator+tmpFileName);
       	fd.deleteOnExit();

		if (tmpFileName == null) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", DDU_Const_Error.GZIP, "gzip error");

			/* set lastError */
			this.lastError = DDU_Const_Error.GZIP;

			/* return KO */
			return rc = -1;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_post", DDU_Const_Error.GZIP, "gzip ok");

		/* encode file */
		rc = DDU_Int_File.encodeFile(this.pathTmpDir+fileSeparator+tmpFileName);

		if (rc != 0) {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", DDU_Const_Error.ENCODE, "encode error");

			/* set lastError */
			this.lastError = DDU_Const_Error.ENCODE;

			/* return KO */
			return rc;
		}

		/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_post", DDU_Const_Error.OK, "encode ok");

		int amount			= 0;
		int numChunk		= 0;

		boolean oneChunk	= true;
		byte fileData[]		= null;

		Integer chunkLength	= new Integer(0);
		String operation	= "start";
		FileInputStream fis;

		try {
			Long fileLength	= new Long(fd.length());
			fis				= new FileInputStream(this.pathTmpDir+fileSeparator+tmpFileName);

			/*
			 * se la dimensione del file � inferiore o uguale alla dimensione del chunck
			 * il file viene inviato in un unico chunck
			 */
			if (fileLength.intValue() <= this.intBuffSizeUp) {

				chunkLength	= new Integer(fileLength.intValue());
				numChunk	= 1;
				operation	= "end:0";

			} else {

				/* calcolo il numero di chunk necessari per inviare il file */
				chunkLength	= new Integer(this.intBuffSizeUp);
				numChunk	= (fileLength.intValue() / chunkLength.intValue()) +1;

			}

			fileData = new byte[chunkLength.intValue()];

			_docId.value = "";
			_tck.value = this.ticket;

			/* serie di chiamate al ws dduPost */
	        while (oneChunk && ((amount = fis.read(fileData, 0, chunkLength.intValue())) != -1)) {

	        	/*  Devo decrementare numChunk in tutti i casi meno quello in cui operation == "end:0" (unico chunk) */
	        	if ("end:0".equals(operation)) {

					oneChunk	= false;
				}
	        	else numChunk--;

	        	_length.value = String.valueOf(amount);

	        	/* se numChunk == 0 sto inviando l'ultimo chunk */
	        	if (numChunk == 0) {

					operation	= "end";
					oneChunk	= false;
				}

	        	/* preparo la stringa da inviare */
	        	String sendStr = new String(fileData).substring(0, amount);

	        	rc = ddu.dduPost(	_tck, this.serviceID, this.loginID, _docId, this.appl, docType, "", DDU_Const_Value.ASCII, operation,
	        						String.valueOf(amount),	sendStr, destMarket, "", destUser, _length, _return);

	        	if (rc == 0) {

	        		/* verifico valore di ritorno del ws */
					if (!(_return.value).equals("0")) {

						/* Close local file */
						fis.close();

						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "upload_post", "dduPost");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduPost */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.POST;
							}

							/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "error dduPost - return: "+_return.value);

							/* return KO */
							return rc = -1;
						}
					}

		        	/* trace */
		        	DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_post", DDU_Const_Error.OK, "dduPost OK - byte inviati: "+_length.value);

		            operation = "next";
				}
	        	/* ddu_soap Error */
	        	else {

	        		/* Close local file */
	        		fis.close();

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "dduPost KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
				}
	        }

	        /* Save new ticket */
	        this.ticket = _tck.value;

	        /* Close local file */
	        fis.close();

	        /* trace */
	        DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_post", DDU_Const_Error.OK, "End send! docId: "+_docId.value);

			/* "azzero" il valore di return prima di riutilizzarlo*/
			_return.value = "";

			_tck.value = this.ticket;

			/* chiamata al ws dduCommit */
			rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws*/
				if (!"0".equals(_return.value)) {

					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload_post", "dduCommit");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCommit */
					else {

						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.COMMIT;
						}

		        		/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "dduCommit KO - return: "+_return.value);

		        		/* return KO */
		        		return rc = -1;
					}
				}

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_post", DDU_Const_Error.OK, "dduCommit OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "dduCommit KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}

		/* -- Catch Exception -- */
		} catch (FileNotFoundException fnf_e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "error dduPost - file not found - Exception: "+fnf_e);

			/* return KO */
			return rc = -1;

		} catch (IOException io_e) {


			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "error dduPost - IOException: "+io_e);

			/* return KO */
			return rc = -1;

		}  catch (Exception e) {


			/* set lastError */
			this.lastError = DDU_Const_Error.KO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_post", this.lastError, "error dduPost - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* Delete temporany file */
		fd.delete();

		/* return docId OK, else -1 */
		return Integer.parseInt(_docId.value);
	}



	/**
	 * upload_post
	 *
	 * - comprime il file e lo codifica
	 * - effettua una serie di chiamate al ws dduPost per inviare il file srcPath
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 *
	 * @return docId OK, else -1
	 */
	public int upload_post(String srcPath, String docType, String destMarket, String destUser, String destAppl, String sAux) {

		return upload_post(srcPath, docType, destMarket, destUser, destAppl);
	}


	/**
	 * download_getAtt
	 *
	 * - effettua il download del file in Mime Attachment chiamando il ws dduGetAtt
	 *
	 * @param dduFile
	 * @param destPath - path assoluto di destinazione (comprensivo del nome file)
	 *
	 * @return docId OK, else -1
	 */
	public int download_getAtt(DduFileEx dduFile, String destPath) {

		String fileSeparator =  System.getProperty("file.separator");

		int rc = 0;

		StringHolder _length	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();
		DataHandlerHolder _dh	= new DataHandlerHolder();

		/* creo file di destinazione nella directory temporanea aggiungento al nome del file i millisecondi correnti */
		Long currentTimeMillis	= new Long(System.currentTimeMillis());
		String ctm				= new String(currentTimeMillis.toString());
		File fd					= new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
		fd.deleteOnExit();

		if (fd == null) {

			/* set lastError */
			this.lastError = DDU_Const_Error.IO;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "Errore creazione file temporaneo: "+dduFile.getFileid()+" - Path file temporaneo: "+this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			return rc = -1;

		} else {

			/* trace */
			DDU_Int_Log.file(this.logFileName, 1, "DDU_Interface:download_getAtt", DDU_Const_Error.OK, "Creato file temporaneo: "+dduFile.getFileid());

			try {

				 FileOutputStream out = null;

				 out = new FileOutputStream(fd, true);

				 String operation	= "start";
				 String offset		= "0";

				 _tck.value = this.ticket;
				 _length.value		= this.buffSizeAttDw;

				 /* serie di chiamate al ws dduGetAtt */
				while (Integer.parseInt(_length.value) == this.intBuffSizeAttDw) {

					_length.value = "0";

					rc = ddu.dduGetAtt(	_tck, this.serviceID, this.loginID, dduFile.getFileid(), this.appl, dduFile.getFileName(),
										"",	DDU_Const_Value.BINARY, operation, this.buffSizeAttDw, _dh, _length, _return);

					if (rc == 0) {

						/* verifico valore di ritorno del ws */
						if (!"0".equals(_return.value)) {

							/* Close local file */
						    out.close();

							int tck_int = 0;

							/* Verifico se l'errore � causato da ticket scaduto o errato */
							try {

								tck_int = Integer.parseInt(_tck.value);

							} catch (java.lang.NumberFormatException NFEx) {

								tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
							}

							if(tck_int < 0)
							{
								this.ticketError(_tck.value, "download_getAtt", "dduGetAtt");

								/* return KO */
								return rc = -1;
							}
							/* L'errore � del webservices dduGetAtt */
							else {

								/* Salvo comunque il ticket ottenuto */
								this.ticket = _tck.value;

								/* set lastError */
								this.lastError = error_map(Integer.parseInt(_return.value));

								if (this.lastError == -1) {

									this.lastError = DDU_Const_Error.GET_ATT;
								}

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - byte ricevuti: "+_length.value+" - return: "+_return.value);

								this.lastEx = ddu.get_dduSoapEx();

								if (this.lastEx != null) {

									/* trace */
									DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - dduEx: "+this.lastEx);
								}

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - body_resp and lastStep: "+ddu.get_dduSoapString());

								/* return KO */
								return rc = -1;
							}
						}

						/* Se _return.value vale zero e lenght.value vale zero il file scaricato � esattamente della dimensione
						 * di un chunk, quindi adesso non devo scrivere altri dati - non entro nell'if */
						if ("0".equals(_return.value) && !("0".equals(_length.value))) {

							/* trace */
							DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_getAtt", DDU_Const_Error.OK, "dduGetAtt OK - byte ricevuti: "+_length.value+" - return: "+_return.value);

							int tmpLen	= Integer.parseInt(_length.value) + Integer.parseInt(offset);
							offset = String.valueOf(tmpLen);

						    operation = "next"+":"+offset;

						    System.out.println("\nNew Operation: "+operation);

						    (_dh.value).writeTo(out);
						}

					}
					/* ddu_soap Error */
					else {

						/* Close local file */
					    out.close();

						/* set lastError */
						this.lastError = ddu.get_dduSoapError();

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - dduSoapError: "+this.lastError);

						this.lastEx = ddu.get_dduSoapEx();

						if (this.lastEx != null) {

							/* trace */
							DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - dduSoapError - dduEx: "+this.lastEx);
						}

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

						/* return KO */
						return rc;
					}
				}

				/* Close local file */
				out.close();

				/* Save new ticket */
				this.ticket = _tck.value;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_getAtt", DDU_Const_Error.OK, "Terminato il download del file! docId: "+dduFile.getFileid());

				/* "azzero" il valore di return */
				_return.value = "";

				/* get ticket */
				_tck.value = this.ticket;

				/* chiamata al ws dduCommit */
				rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, dduFile.getFileid(), _return);

				if (rc == 0) {

					/* verifico valore di ritorno del ws*/
					if (!"0".equals(_return.value)) {

						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "download_getAtt", "dduCommit");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduCommit */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.COMMIT;
							}

							if (this.lastEx != null) {

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduCommit KO  - dduEx: "+this.lastEx);
							}

							/* trace */
							DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduCommit KO  - body_resp and lastStep: "+ddu.get_dduSoapString());

			        		/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_gettAtt", this.lastError, "dduCommit KO - Byte inviati: "+_length.value+" - return: "+_return.value);

			        		/* return KO */
			        		return rc = -1;
						}
					}

					/* Save new ticket */
					this.ticket = _tck.value;

					/* trace */
					DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_getAtt", DDU_Const_Error.OK, "dduCommit OK");
				}
				/* ddu_soap Error */
				else {

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					this.lastEx = ddu.get_dduSoapEx();

					if (this.lastEx != null) {

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduCommit KO  - dduSoapError - dduEx: "+this.lastEx);
					}

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduCommit KO  - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduCommit KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
				}


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.GET_ATT_EX;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "dduGetAtt KO - Exception: "+e);

				/* return KO */
				return rc = -1;
			}

			/* Decompress file */
			rc = DDU_Int_File.unZipFile(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

			if (rc != 0) {

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", DDU_Const_Error.UNZIP, "Errore in fase di unzip del file"+" - path del file: "+this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);

				/* set lastError */
				this.lastError = DDU_Const_Error.UNZIP;

				/* return KO */
				return rc = -1;
			}

			try {
				/* move file */
				File fdSrc = new File(this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm);
				File fdDst = new File(destPath);
				fdSrc.renameTo(fdDst);

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:download_getAtt", DDU_Const_Error.OK, " move file from tmpDir to destPath, ok");


			/* -- Catch Exception -- */
			} catch (Exception e) {

				/* set lastError */
				this.lastError = DDU_Const_Error.KO;

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:download_getAtt", this.lastError, "move file from tmpDir to destPath, Ko - Path file temp: "
								+ this.pathTmpDir+fileSeparator+dduFile.getFileid()+"_"+ctm +" - destPath: "+" destPath"+" - Exception: "+e);

				/* return KO */
				return rc = -1;
			}
		}

		/* return docId OK */
		return Integer.parseInt(dduFile.getFileid());
	}



	/**
	 * upload_postAtt
	 *
	 * - comprime il file da inviare
	 * - invia il file in Mime Attachment
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 * @param destMarket	mercato destinazione
	 * @param destUser		user destinazione
	 *
	 * @return docId OK, else -1
	 */
	public int upload_postAtt(String srcPath, String docType, String destMarket, String destUser) {

		String fileSeparator =  System.getProperty("file.separator");

		int rc = 0;

		StringHolder _docId		= new StringHolder();
		StringHolder _length	= new StringHolder();
		StringHolder _tck		= new StringHolder();
		StringHolder _return	= new StringHolder();

		File fd;

       	File fd_temp = new File(srcPath);

       	if (!fd_temp.exists()) {

			/* set lastError */
			this.lastError = DDU_Const_Error.FILE_NF;

	       	/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "Source File Not Found: "+srcPath);

			/* return KO */
			return rc = -1;
		}

		/* prelevo il nome del file dal path sorgente */
		String tmpFileName	= null;		// conterr� il nome del file dopo compressione

		int index	= srcPath.lastIndexOf(fileSeparator);
       	tmpFileName = srcPath.substring(index+1);

       	DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "Start gzip");

		/* zip file */
       	tmpFileName = DDU_Int_File.zipFile(srcPath, this.pathTmpDir);

       	fd = new File(this.pathTmpDir+fileSeparator+tmpFileName);
       	fd.deleteOnExit();

       	/* trace */
		DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "gzip ok");

		if (tmpFileName == null) {

			/* set lastError */
			this.lastError = DDU_Const_Error.GZIP;

	       	/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "gzip KO");

			/* return KO */
			return rc = -1;
		}

		int amount			= 0;
		int numChunk		= 0;

		boolean oneChunk	= true;
		byte fileData[]		= null;

		Integer chunkLength	= new Integer(0);
		String operation	= "start";
		FileInputStream fis	= null;

		try {
			Long fileLength	= new Long(fd.length());
			fis				= new FileInputStream(this.pathTmpDir+fileSeparator+tmpFileName);

			/*
			 * se la dimensione del file � inferiore o uguale alla dimensione del chunck
			 * il file viene inviato in un unico chunck
			 */
			if (fileLength.intValue() <= this.intBuffSizeAttUp) {

				chunkLength	= new Integer(fileLength.intValue());
				numChunk	= 1;
				operation	= "end:0";

			} else {

				/* calcolo il numero di chunk necessari per inviare il file */

				chunkLength	= new Integer(this.intBuffSizeAttUp);
				numChunk	= (fileLength.intValue() / chunkLength.intValue()) +1;

			}

			fileData = new byte[chunkLength.intValue()];

			_tck.value	 = this.ticket;
			_docId.value = "";

			/* trace */
			DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "Start send - fileLength: "+fileLength.toString());

	        while (oneChunk && ((amount = fis.read(fileData, 0, chunkLength.intValue())) != -1)) {

	        	/*  Devo decrementare numChunk in tutti i casi meno quello in cui operation == "end:0" (unico chunk) */
	        	if ("end:0".equals(operation)) {

					oneChunk	= false;
				}
	        	else numChunk--;

	        	_length.value = String.valueOf(amount);

	        	/* se numChunk == 0 sto inviando l'ultimo chunk */
	        	if (numChunk == 0) {

					operation	= "end";
					oneChunk	= false;
				}

	        	rc = ddu.dduPostAtt(	_tck, fileData, this.serviceID, this.loginID, _docId, this.appl, docType, "", DDU_Const_Value.APP_OCT,
	        							operation, String.valueOf(amount), destMarket, "", destUser, _length, _return	);

	        	if (rc == 0) {

		        	/* verifico valore di ritorno del ws*/
					if (!"0".equals(_return.value)) {

						/* Close local file */
						fis.close();

						int tck_int = 0;

						/* Verifico se l'errore � causato da ticket scaduto o errato */
						try {

							tck_int = Integer.parseInt(_tck.value);

						} catch (java.lang.NumberFormatException NFEx) {

							tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
						}

						if(tck_int < 0)
						{
							this.ticketError(_tck.value, "upload_postAtt", "dduPostAtt");

							/* return KO */
							return rc = -1;
						}
						/* L'errore � del webservices dduPostAtt */
						else {

							/* Salvo comunque il ticket ottenuto */
							this.ticket = _tck.value;

							/* set lastError */
							this.lastError = error_map(Integer.parseInt(_return.value));

							if (this.lastError == -1) {

								this.lastError = DDU_Const_Error.POST_ATT;
							}


			        		/* trace */
			        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - Byte inviati: "+_length.value+" - return: "+_return.value);

			        		this.lastEx = ddu.get_dduSoapEx();

							if (this.lastEx != null) {

								/* trace */
								DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - dduEx: "+this.lastEx);
							}

							/* trace */
							DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - body_resp and lastStep: "+ddu.get_dduSoapString());

			        		/* return KO */
			        		return rc = -1;
						}
					}

					/* trace */
		        	DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "dduPostAtt OK - byte inviati: "+_length.value+" - return: "+_return.value);

		        	operation = "next";
				}
	        	/* ddu_soap Error */
	        	else {

	        		/* Close local file */
	        		fis.close();

					/* set lastError */
					this.lastError = ddu.get_dduSoapError();

					this.lastEx = ddu.get_dduSoapEx();

					if (this.lastEx != null) {

						/* trace */
						DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - dduSoapError - dduEx: "+this.lastEx);
					}

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - dduSoapError -body_resp and lastStep: "+ddu.get_dduSoapString());

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - dduSoapError: "+this.lastError);

					/* return KO */
					return rc;
				}
	        }

	        /* Close local file */
	        fis.close();

	        /* Save new ticket */
	        this.ticket = _tck.value;

	        /* trace */
	        DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "End send! docId: "+_docId.value);

			/* "azzero" il valore di return prima di riutilizzarlo*/
			_return.value = "";

			/* get ticket */
			_tck.value = this.ticket;

			/* chiamata al ws dduCommit */
			rc = ddu.dduCommit(_tck, this.serviceID, this.loginID, _docId.value, _return);

			if (rc == 0) {

				/* verifico valore di ritorno del ws*/
				if (!"0".equals(_return.value)) {

					int tck_int = 0;

					/* Verifico se l'errore � causato da ticket scaduto o errato */
					try {

						tck_int = Integer.parseInt(_tck.value);

					} catch (java.lang.NumberFormatException NFEx) {

						tck_int = 0;	// se viene sollevata l'eccezione il ticket � alfanumerico, quindi l'errore non � dovuto al ticket
					}

					if(tck_int < 0)
					{
						this.ticketError(_tck.value, "upload_postAtt", "dduCommit");

						/* return KO */
						return rc = -1;
					}
					/* L'errore � del webservices dduCommit */
					else {
						/* Salvo comunque il ticket ottenuto */
						this.ticket = _tck.value;

						/* set lastError */
						this.lastError = error_map(Integer.parseInt(_return.value));

						if (this.lastError == -1) {

							this.lastError = DDU_Const_Error.COMMIT;
						}

		        		/* trace */
		        		DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduCommit KO - return: "+_return.value);

		        		/* return KO */
		        		return rc = -1;
					}
				}

				/* trace */
				DDU_Int_Log.file(this.logFileName, 2, "DDU_Interface:upload_postAtt", DDU_Const_Error.OK, "dduCommit OK");
			}
			/* ddu_soap Error */
			else {

				/* set lastError */
				this.lastError = ddu.get_dduSoapError();

				this.lastEx = ddu.get_dduSoapEx();

				if (this.lastEx != null) {

					/* trace */
					DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduCommit KO  - dduSoapError - dduEx: "+this.lastEx);
				}

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduCommit KO  - dduSoapError - body_resp and lastStep: "+ddu.get_dduSoapString());

				/* trace */
				DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduCommit KO - dduSoapError: "+this.lastError);

				/* return KO */
				return rc;
			}

	    /* -- Catch Exception -- */
		} catch (Exception e) {

			/* set lastError */
			this.lastError = DDU_Const_Error.POST_ATT_EX;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:upload_postAtt", this.lastError, "dduPostAtt KO - Exception: "+e);

			/* return KO */
			return rc = -1;
		}

		/* Delete temporany file */
		fd.delete();

        /* Save new ticket */
        this.ticket = _tck.value;

		/* return docId OK */
		return Integer.parseInt(_docId.value);
	}



	/**
	 * upload_postAtt
	 *
	 * - comprime il file da inviare
	 * - invia il file in Mime Attachment
	 *
	 * @param srcPath		path assoluto del file da inviare
	 * @param docType		tipo del file
	 *
	 * @return docId OK, else -1
	 */
	public int upload_postAtt(String srcPath, String docType) {

		return upload_postAtt(srcPath, docType, "", "");
	}


	/* Private methods */

	/**
	 * load_config
	 *
	 * - inizializza le variabili istanza con i dati contenuti nel file "config.ini"
	 * - inizializza il file di log
	 *
	 * @param inputIniDir	path assoluto della cartella che contiene il file di configurazione
	 *
	 * @return	void
	 */
	private void load_config(String inputIniDir) {

		/* sezione Dir */
		this.pathTmpDir		= iniFile.getPrivateProfileString(inputIniDir, "Dir", "Tmp", ".", DDU_Const_Value.INI_FILE);

		/* sezione Login */
		this.user			= iniFile.getPrivateProfileString(inputIniDir, "Login", "User", "", DDU_Const_Value.INI_FILE);
		this.password		= iniFile.getPrivateProfileString(inputIniDir, "Login", "Password", "", DDU_Const_Value.INI_FILE);
		this.brand			= iniFile.getPrivateProfileString(inputIniDir, "Login", "Brand", "", DDU_Const_Value.INI_FILE);
		this.sincom			= iniFile.getPrivateProfileString(inputIniDir, "Login", "Sincom", "", DDU_Const_Value.INI_FILE);
		this.market			= iniFile.getPrivateProfileString(inputIniDir, "Login", "Market", "", DDU_Const_Value.INI_FILE);
		this.appl			= iniFile.getPrivateProfileString(inputIniDir, "Login", "Appl", "", DDU_Const_Value.INI_FILE);
		this.certificationSystem	= iniFile.getPrivateProfileString(inputIniDir, "Login", "CertificationSystem", "", DDU_Const_Value.INI_FILE);

		/* sezione Parameters */
		this.urlWsConnect	= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "UrlWsConnect", "", DDU_Const_Value.INI_FILE);
		this.urlWs			= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "UrlWs", "", DDU_Const_Value.INI_FILE);
		this.buffSizeDw		= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "SizeDw", "0", DDU_Const_Value.INI_FILE);
		this.buffSizeUp		= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "SizeUp", "0", DDU_Const_Value.INI_FILE);
		this.buffSizeAttDw	= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "SizeAttDw", "0", DDU_Const_Value.INI_FILE);
		this.buffSizeAttUp	= iniFile.getPrivateProfileString(inputIniDir, "Parameters", "SizeAttUp", "0", DDU_Const_Value.INI_FILE);

		this.intBuffSizeDw		= Integer.parseInt(this.buffSizeDw);
		this.intBuffSizeUp		= Integer.parseInt(this.buffSizeUp);
		this.intBuffSizeAttDw	= Integer.parseInt(this.buffSizeAttDw);
		this.intBuffSizeAttUp	= Integer.parseInt(this.buffSizeAttUp);



		/*
		 * Inizializzo il file di log indicando il livello (prelevato dal file "config.ini")
		 *
		 * LOG_ALL		= 0;
		 * LOG_DEBUG	= 1;
		 * LOG_INFO		= 2;
		 * LOG_ERROR	= 3;
		 *
		 * Se passo un valore maggiore di 3 il file di log non viene mai creato
		 */
		Long currentTimeMillis	= new Long(System.currentTimeMillis());
		this.logFileName		= "ddu_" + new String(currentTimeMillis.toString()) + ".log";

		int logLevel = Integer.parseInt(iniFile.getPrivateProfileString(inputIniDir, "Parameters", "LogLevel", "", DDU_Const_Value.INI_FILE));
		DDU_Int_Log.init(inputIniDir, logLevel);
	}

	/**
	 * ticketError
	 *
	 * - setta la variabile lastError e scrive sul file di log
	 *
	 * @param ticket	valore restituito dal controllo del ticket
	 * @param method	metodo in cui si � verificato l'errore sul ticket
	 * @param ws		web services in cui si � verificato l'errore sul ticket
	 *
	 * @return void
	 */
	private void ticketError(String ticket, String method, String ws) {

		/* Ticket scaduto */
		if ((Integer.parseInt(ticket) == -1)){

			/* set lastError */
			this.lastError = DDU_Const_Error.TICKET_ELAPSED;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:"+method, this.lastError, ws+" KO - Ticket elapsed");
		}
		/* Ticket non valido */
		else if ((Integer.parseInt(ticket) == -2)){

			/* set lastError */
			this.lastError = DDU_Const_Error.TICKET_WRONG;

			/* trace */
			DDU_Int_Log.file(this.logFileName, 3, "DDU_Interface:"+method, this.lastError, ws+" KO - Ticket errato");
		}
	}

	private int error_map(int error) {

		switch (error) {

			case -1:
				return DDU_Const_Error.ERR_FILEID_NOT_FOUND;

			case -2:
				return DDU_Const_Error.ERR_FILENAME_NOT_FOUND;

			case -3:
				return DDU_Const_Error.ERR_RECV_UNDEFINED;

			case -4:
				return DDU_Const_Error.ERR_RECV_NOT_ALLOWED;

			case -5:
				return DDU_Const_Error.ERR_TEMP_DISTR_LIST;

			case -6:
				return DDU_Const_Error.ERR_TEMP_DISTR_LIST_CRITERIA;

			case -7:
				return DDU_Const_Error.ERR_FILE_ENQUEUE;

			case -8:
				return DDU_Const_Error.ERR_SET_FILE_STATUS;

			case -9:
				return DDU_Const_Error.ERR_SNDR_NOT_ALLOWED;

			case -10:
				return DDU_Const_Error.ERR_SNDR_UNDEFINED;

			case -11:
				return DDU_Const_Error.ERR_SNDR_UNDEFINED;

			case -201:
				return DDU_Const_Error.CONST_CAUSERR_DBERR;

			case -202:
				return DDU_Const_Error.CONST_CAUSERR_BLOBERR;

			case -203:
				return DDU_Const_Error.CONST_CAUSERR_BADPAR;

			case -204:
				return DDU_Const_Error.CONST_CAUSERR_PROGERR;

			case -205:
				return DDU_Const_Error.CONST_CAUSERR_FILERRR;

			default:
				return -1;
		}
	}
}