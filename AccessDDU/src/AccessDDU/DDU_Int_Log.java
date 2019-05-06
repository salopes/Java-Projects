package AccessDDU;

import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * Logger class to be used by all the applications involved
 * in DDU project.
 *
 * @author Roberto Giai Meniet - While1 s.r.l.
 *
 * @version 0.2
 *
 */
public class DDU_Int_Log {

	/* Constants */
	public static final int DDULOG_ALL		= 0;
	public static final int DDULOG_DEBUG	= 1;
	public static final int DDULOG_INFO		= 2;
	public static final int DDULOG_ERROR	= 3;

	// private static final long MAX_DISTANCE	= 600000;		// Max distance between logd 1190126427671

	/* Globals */
	static boolean inited	= false;
	static int logLevel = DDULOG_ALL;			// Desired minimum level to log
	static boolean onFile = true;
	static File fd = null;

	static Object syncObject = new Object();	// Sync object
	static long lastLog = 0;					// Last time of log
	static String dirLog = null;				// Log directory
	static String pathLog = null;				// Log directory


	/**
 	*
 	* Class constructor. Does nothing.
 	*
 	*/
	protected DDU_Int_Log()
	{
	}


	/**
 	*
 	* Initialize log for future actions.
 	* Crea la directory temporanea per i file di log, prelevandone il path dal file di configurazione.
	* @param level desired level (see constants)
	*
 	*/
	protected static void init(String inputIniDir, int level) {

		String tmp;								// Temporary string

		// creo la directory di log
		if (!inputIniDir.equals(pathLog)) {
			
			pathLog = iniFile.getPrivateProfileString("Dir", "Log", ".", DDU_Const_Value.INI_FILE);
			
			File pl = new File(pathLog);
			
			if (!pl.exists()) {
				
				DDU_Int_File.mkdir(pathLog);
			}
			
			
			tmp = iniFile.getPrivateProfileString("Parameters", "LogLevel", ".", DDU_Const_Value.INI_FILE);
			if (tmp != null) {
				logLevel = Integer.parseInt(tmp);
			}
		}

		/* Set inited flag */
		inited = true;
	}


	/**
 	*
 	* Logs info message.
 	*
 	* @param message Message to print on selected device/file
 	*/
	protected static void info(String message) {

		/* Skip if log level
		if (logLevel > DDULOG_INFO) {
			return;
		}

		/* Check init state */
		if (inited == false) {
			init(pathLog, DDULOG_ALL);
		}
    }


	/**
 	*
 	* Logs error message.
 	*
 	* @param message Message to print on selected device/file
 	*/
	protected static void error(String message) {

		System.out.println(new Date(System.currentTimeMillis()) + " - error: " + message);
    }


	/**
 	*
 	* Logs error message.
 	*
 	* @param message Message to print on selected device/file
 	* @param t Throwable info of an exception
 	*/
	protected static void error(String message, Throwable t) {

		System.out.println(new Date(System.currentTimeMillis()) + " - error: " + message);
    }


	/**
 	*
 	* Logs debug message.
 	*
 	* @param message Message to print on selected device/file
 	*/
	protected static void debug(String message) {

		System.out.println(new Date(System.currentTimeMillis()) + " - debug: " + message);
    }



	/**
	 *
	 * Logs on File what is passed in arguments.
	 *
	 * @param level Error level
	 * @param source Name of source/class/task that calls this method
	 * @param code Error code
	 * @param descr Description of what to log
	 * @param file File id
	 * @param market File market
	 * @param owner File owner
	 * @param type File type
	 * @param path File path
	 * @param table Table involved
	 *
	 */
	protected static void file(String logFileName, int level, String source, int code, String descr,
					long file, String type, String path) {

		long curTime;				// Current time

		/* Check init state */
		if (inited == false) {
			init(pathLog, DDULOG_ALL);
		}

		/* Check level */
		if (level < logLevel) {
			return;
		}

		try {

			/* Verify time from last log */
			curTime = System.currentTimeMillis();

			/* Open file */
			//fd = new File(pathLog + System.getProperty("file.separator") + "log");

			FileOutputStream fos = new FileOutputStream(pathLog + System.getProperty("file.separator") + logFileName, true);
		    PrintStream Output = new PrintStream(fos);

		    SimpleDateFormat sdf= new SimpleDateFormat("dd/MM/yyyy hh:mm:ss");
		    String msg = sdf.format(new java.util.Date()); 

//		    String msg = Long.toString(curTime);
		    msg = msg + " " + source + " " + descr;
		    Output.println(msg);
		    Output.close();
		    fos.close();

			/* Save current time */
			lastLog = curTime;

		} catch (Exception e) {
			error("DDUlog: Cannot log on file (" + source + " " + descr + ") - exception " + e);
		}
	}


	/**
	 *
	 * Same as db, with a numeric code (defined in DDUErrCode class).
	 *
	 * @param level Error level
	 * @param source Name of source/class/task that calls this method
	 * @param code Error code
	 * @param descr Description of what to log
	 */
	protected static void file(String logFileName, int level, String source, int code, String descr) {

		/* Redirect to string code version */
		file(logFileName, level, source, code, descr, 0, "", "");
	}


	/**
	 *
	 * Sets log level dinamically.
	 *
	 * @param level Error level
	 *
	 * @return previous value
	 */
	protected static int setLevel(int newValue) {

		int oldValue = logLevel;

		switch (newValue) {

			case DDULOG_DEBUG:
			case DDULOG_INFO:
			case DDULOG_ERROR:

				/* Set to new value */
				logLevel = newValue;
				break;


			default:

				/* Invalid */
				break;

		}

		return(oldValue);
	}


	/**
	 *
	 * Returns current log level.
	 *
	 * @return current value
	 */
	protected static int getLevel() {
		return(logLevel);
	}
}
