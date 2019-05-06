package AccessDDU;

/**
* Constanti
*
* @author Tullio Ferralis While1 s.r.l.
*
* @version 2.0	-	20071119
* @version 2.2 - 	20080125
* @version 2.3	-	20080225
* @version 2.4	-	20080318
* @version 2.5	-	20080326
*/

public class DDU_Const_Error {

	/** {@value} No Error*/
	public static final int OK								= 0;			// No error
	
	/** {@value} Ticket elapsed */
	public static final int TICKET_ELAPSED					= 10;			// Ticket elapsed
	
	/** {@value} Ticket wrong */
	public static final int TICKET_WRONG					= 11;			// Ticket wrong
	
	/** {@value} Errore nella richiesta delle user info associate al ticket */
	public static final int USER_INFO_ERROR					= 12;			// Errore nella richiesta delle user info associate al ticket

	/** {@value} Errore nella richiesta del ticket */
	public static final int TICKET_REQUEST_ERROR			= 13;			// Errore nella richiesta del ticket


	/** {@value}Generic error*/
	public static final int KO								= 199;			// Generic error

	/** {@value} File non trovato*/
	public static final int FILE_NF							= 101;			// File non trovato

	/** {@value} Errore nella creazione della directory tmp*/
	public static final int TMP_DIR							= 102;			// Errore nella creazione della directory tmp

	/** {@value} Errore in fase di compressione del file*/
	public static final int GZIP   		 					= 103;			// Errore in fase di compressione del file

	/** {@value} Errore in fase di decompressione del file*/
	public static final int UNZIP   						= 104;			// Errore in fase di decompressione del file

	/** {@value} Errore in fase di codifica del file*/
	public static final int ENCODE  						= 107;			// Errore in fase di codifica del file

	/** {@value} Errore in fase di decodifica del file*/
	public static final int DECODE  						= 108;			// Errore in fase di decodifica del file

	/** {@value} UrlMalformed*/
	public static final int URL_MALFORMED 		 			= 109;			// UrlMalformed

	/** {@value} Errore di IO nelle operazioni su file*/
	public static final int IO					  			= 110;			// Errore di IO nelle operazioni su file



	/*
	 * Errori dei web services
	 */

	/** {@value} Errore nell'esecuzione di dduConnect*/
	public static final int CONNECT							= 120;			// Errore nell'esecuzione di dduConnect

	/** {@value} Errore nell'esecuzione di dduLogin*/
	public static final int LOGIN							= 121;			// Errore nell'esecuzione di dduLogin

	/** {@value} Errore nell'esecuzione di dduLogout*/
	public static final int LOGOUT							= 122;			// Errore nell'esecuzione di dduLogout

	/** {@value} Errore nell'esecuzione di dduDirNele*/
	public static final int DIR_NELE						= 123;			// Errore nell'esecuzione di dduDirNele

	/** {@value} Errore nell'esecuzione di dduDirChunk*/
	public static final int DIR_CHUNK						= 124;			// Errore nell'esecuzione di dduDirChunk
	
	/** {@value} Errore nell'esecuzione di dduDir*/
	public static final int DIR								= 125;			// Errore nell'esecuzione di dduDir
	
	/** {@value} Errore nell'esecuzione di dduPost*/
	public static final int POST							= 126;			// Errore nell'esecuzione di dduPost
	
	/** {@value} Errore nell'esecuzione di dduGet*/
	public static final int GET								= 127;			// Errore nell'esecuzione di dduGet
	
	/** {@value} Errore nell'esecuzione di dduOpen*/
	public static final int OPEN							= 128;			// Errore nell'esecuzione di dduOpen
	
	/** {@value} Errore nell'esecuzione di dduRead*/
	public static final int READ							= 129;			// Errore nell'esecuzione di dduRead
	
	/** {@value} Errore nell'esecuzione di dduWrite*/
	public static final int WRITE							= 130;			// Errore nell'esecuzione di dduRead
	
	/** {@value} Errore nell'esecuzione di dduCreate*/
	public static final int CREATE							= 131;			// Errore nell'esecuzione di dduCreate
	
	/** {@value} Errore nell'esecuzione di dduClose*/
	public static final int CLOSE							= 132;			// Errore nell'esecuzione di dduClose
	
	/** {@value} Errore nell'esecuzione di dduGetAtt*/
	public static final int GET_ATT							= 133;			// Errore nell'esecuzione di dduGetAtt
	
	/** {@value} Errore nell'esecuzione di dduPostAtt*/
	public static final int POST_ATT						= 134;			// Errore nell'esecuzione di dduPostAtt
	
	/** {@value} Errore nell'esecuzione di dduGetGroupList*/
	public static final int GET_GROUP_LIST					= 135;			// Errore nell'esecuzione di dduGetGroupList
	
	/** {@value} Errore nell'esecuzione di dduGetGroupFileList*/
	public static final int GET_GROUP_FILE_LIST				= 136;			// Errore nell'esecuzione di dduGetGroupFileList
	
	/** {@value} Errore nell'esecuzione di dduCommit*/
	public static final int COMMIT							= 137;			// Errore nell'esecuzione di dduCommit
	
	/** {@value} Errore del server */
	public static final int SERVER_ERROR					= 150;			// Errore del server
	
	/** {@value} Connection timed out */
	public static final int CONNECTION_TIMED_OUT			= 151;			// Connection timed out
	
	
	
	/*
	 * Codici di errore relativi agli errori restituiti da DDU 
	 */

	/* --- ERRORI DB --- */
	
	/** {@value} Db Error */
	public static final int CONST_CAUSERR_DBERR				= 201;
	
	/** {@value} Bolb Error */
	public static final int CONST_CAUSERR_BLOBERR			= 202;
	
	/** {@value} Bad Parameter */
	public static final int CONST_CAUSERR_BADPAR			= 203;
	
	/** {@value} Prog Error */
	public static final int CONST_CAUSERR_PROGERR			= 204;
	
	/** {@value} File Error */
	public static final int CONST_CAUSERR_FILERRR			= 205;
	
	/* --- ERRORI A LIVELLO APPLICATIVO --- */
	
	/** {@value} FileiD Not Found */
	public static final int ERR_FILEID_NOT_FOUND			= 301;
	
	/** {@value} FileName Not Found */
	public static final int ERR_FILENAME_NOT_FOUND			= 302;
	
	/** {@value} ERR_RECV_UNDEFINED */
	public static final int ERR_RECV_UNDEFINED				= 303;
	
	/** {@value} ERR_RECV_NOT_ALLOWED */
	public static final int ERR_RECV_NOT_ALLOWED			= 304;
	
	/** {@value} ERR_TEMP_DISTR_LIST */
	public static final int ERR_TEMP_DISTR_LIST				= 305;
	
	/** {@value} ERR_TEMP_DISTR_LIST_CRITERIA */
	public static final int ERR_TEMP_DISTR_LIST_CRITERIA	= 306;
	
	/** {@value} ERR_FILE_ENQUEUE */
	public static final int ERR_FILE_ENQUEUE				= 307;
	
	/** {@value} ERR_SET_FILE_STATUS */
	public static final int ERR_SET_FILE_STATUS				= 308;
	
	/** {@value} ERR_SNDR_NOT_ALLOWED */
	public static final int ERR_SNDR_NOT_ALLOWED			= 309;
	
	/** {@value} ERR_SNDR_UNDEFINED */
	public static final int ERR_SNDR_UNDEFINED				= 310;
	
	/** {@value} ERR_FILE_DISABLED */
	public static final int ERR_FILE_DISABLED				= 311;
	
	
	
	/*
	 * Metodi in cui è stata sollevata l'eccezione
	 */

	/** {@value} Exception nell'esecuzione di dduConnect*/
	public static final int CONNECT_EX						= 420;			// Exception nell'esecuzione di dduConnect

	/** {@value} Exception nell'esecuzione di dduLogin*/
	public static final int LOGIN_EX						= 421;			// Exception nell'esecuzione di dduLogin

	/** {@value} Exception nell'esecuzione di dduLogout*/
	public static final int LOGOUT_EX						= 422;			// Exception nell'esecuzione di dduLogout

	/** {@value} Exception nell'esecuzione di dduDirNele*/
	public static final int DIR_NELE_EX						= 423;			// Exception nell'esecuzione di dduDirNele

	/** {@value} Exception nell'esecuzione di dduDirChunk*/
	public static final int DIR_CHUNK_EX					= 424;			// Exception nell'esecuzione di dduDirChunk
	
	/** {@value} Exception nell'esecuzione di dduDir*/
	public static final int DIR_EX							= 425;			// Exception nell'esecuzione di dduDir
	
	/** {@value} Exception nell'esecuzione di dduPost*/
	public static final int POST_EX							= 426;			// Exception nell'esecuzione di dduPost
	
	/** {@value} Exception nell'esecuzione di dduGet*/
	public static final int GET_EX							= 427;			// Exception nell'esecuzione di dduGet
	
	/** {@value} Exception nell'esecuzione di dduOpen*/
	public static final int OPEN_EX							= 428;			// Exception nell'esecuzione di dduOpen
	
	/** {@value} Exception nell'esecuzione di dduRead*/
	public static final int READ_EX							= 429;			// Exception nell'esecuzione di dduRead
	
	/** {@value} Exception nell'esecuzione di dduWrite*/
	public static final int WRITE_EX						= 430;			// Exception nell'esecuzione di dduRead
	
	/** {@value} Exception nell'esecuzione di dduCreate*/
	public static final int CREATE_EX						= 431;			// Exception nell'esecuzione di dduCreate
	
	/** {@value} Exception nell'esecuzione di dduClose*/
	public static final int CLOSE_EX						= 432;			// Exception nell'esecuzione di dduClose
	
	/** {@value} Exception nell'esecuzione di dduGetAtt*/
	public static final int GET_ATT_EX						= 433;			// Exception nell'esecuzione di dduGetAtt
	
	/** {@value} Exception nell'esecuzione di dduPostAtt*/
	public static final int POST_ATT_EX						= 434;			// Exception nell'esecuzione di dduPostAtt
	
	/** {@value} Exception nell'esecuzione di dduGetGroupList*/
	public static final int GET_GROUP_LIST_EX				= 435;			// Exception nell'esecuzione di dduGetGroupList
	
	/** {@value} Exception nell'esecuzione di dduGetGroupFileList*/
	public static final int GET_GROUP_FILE_LIST_EX			= 436;			// Exception nell'esecuzione di dduGetGroupFileList
	
	/** {@value} Exception nell'esecuzione di dduCommit*/
	public static final int COMMIT_EX						= 437;			// Exception nell'esecuzione di dduCommit
}
