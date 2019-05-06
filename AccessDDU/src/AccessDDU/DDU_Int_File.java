package AccessDDU;

// Standard imports
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 *
 * @author While1 s.r.l.
 * 
 * @version 2.0	-	20071119
 * @version 2.2 - 	20080125
 * @version 2.3	-	20080225
 * @version 2.4	-	20080318
 * @version 2.5	-	20080326
 */

public class DDU_Int_File implements Cloneable {

	// Constants
	private static final int BUFFER = 4096;

	private String id;			// id file
	private String nomeFile;	// nome file
	private String DocAppl;		// applicazione
	private String tipoFile;	// tipo file
	
	protected String compress;	// flag compress
	protected String encoding;	// flag encoding
	
	private String path;

	/**
	 *
	 * Implements clone feature
	 *
	 * @return Cloned object
	 *
	 * @throws CloneNotSupportedException As defined in clone methods
	 *
	 */
	protected Object clone() throws CloneNotSupportedException {
		return super.clone();
	}

	/**
	 * Costruttore - inizializza la struttura dati
	 */
	public DDU_Int_File() {
		this.id = null;
		this.nomeFile = null;
		this.DocAppl = null;
		this.tipoFile = null;
		this.path = null;
		this.compress = null;
		this.encoding = null;
	}
	
	/**
	 * Costruttore con parametri di input
	 * 
	 * @param docid
	 * @param docname
	 * @param docappl
	 * @param doctype
	 * @param compress
	 * @param encoding
	 */
	public DDU_Int_File(String docid, String docname, String docappl, String doctype, String compress, String encoding) {
		this.id = docid;
		this.nomeFile = docname;
		this.DocAppl = docappl;
		this.tipoFile = doctype;
		this.compress = compress;
		this.encoding = encoding;
	}
	
	/**
	 * 
	 * @return id - identificativo del file
	 */
	public String getid(){
		return this.id;
	}
	
	/**
	 * 
	 * @return nomeFile - nome file
	 */
	public String getnomeFile(){
		return this.nomeFile;
	}
	
	/**
	 * 
	 * @return tipoAppl - tipo applicazione
	 */
	public String gettipoAppl(){
		return this.DocAppl;
	}
	
	/**
	 * 
	 * @return tipoFile - tipo file
	 */
	public String gettipoFile(){
		return this.tipoFile;
	}
	
	/**
	 * 
	 * @return path
	 */
	protected String getpath(){
		return this.path;
	}
	
	/**
	 * 
	 * @param id
	 */
	protected void setid(String id){
		this.id = id;
	}
	
	/**
	 * 
	 * @param nomeFile
	 */
	protected void setnomeFile(String nomeFile){
		this.nomeFile = nomeFile;
	}
	
	/**
	 * 
	 * @param tipoAppl
	 */
	protected void settipoApll(String tipoAppl){
		this.DocAppl = tipoAppl;
	}
	
	/**
	 * 
	 * @param tipoFile
	 */
	protected void settipoFile(String tipoFile){
		this.tipoFile = tipoFile;
	}
	
	/**
	 * 
	 * @param path
	 */
	protected void setpath(String path){
		this.path = path;
	}

	/**
	 *
	 * Decompress a given file in this one.
	 *
	 * @param srcFile source file info
	 *
	 * @return 0 OK else error
	 */
	protected static int unZipFile(String srcFile) {

		FileInputStream fis = null;
		GZIPInputStream gs;
		FileOutputStream fos;
		BufferedOutputStream dest = null;
		BufferedInputStream bis = null;
		byte data[] = new byte[BUFFER];
		boolean iOpen = false, oOpen = false;
		int rc = 0;
		int count;

		try {

			/* Open files */
			fis = new FileInputStream(srcFile);
			gs = new GZIPInputStream(fis, data.length);
			bis = new BufferedInputStream(gs);

			iOpen = true;

			fos = new FileOutputStream(srcFile+"_unz");
			dest = new BufferedOutputStream(fos, BUFFER);

			oOpen = true;

			/* Read and write */
			while ((count = bis.read(data, 0, data.length)) != -1) {

				dest.write(data, 0, count);
			}

		} catch (Exception e) {
			rc = -1;
		}

		if (iOpen) {
			try {
				fis.close();
			} catch (Exception e) {	}
		}

		if (oOpen) {
			try {
				dest.flush();
				dest.close();
			} catch (Exception e) {	}
		}
		
		try {
			File fd = new File(srcFile);
			fd.delete();
			fd = new File(srcFile+"_unz");
			fd.renameTo(new File(srcFile));
		} catch (Exception e) {	}

		return (rc);
	}

	/**
	 *
	 * Decompress given file
	 *
	 * @param sourcePath
	 * @param destinationPath
	 *
	 * @return nomeFile con suffisso millisecondi correnti se OK, null altrimenti
	 */
	protected static String zipFile(String sourcePath, String destinationPath) {

		String fileSeparator =  System.getProperty("file.separator");
		int index = sourcePath.lastIndexOf(fileSeparator);
		Long currentTimeMillis = new Long(System.currentTimeMillis());
		String ctm = new String(currentTimeMillis.toString());
		
		String fileName = (sourcePath.substring(index+1))+"_"+ctm;
		
		boolean iOpen = false, oOpen = false;
		
		BufferedInputStream origin = null;
		FileOutputStream dest = null;
		GZIPOutputStream out = null;
		byte data[] = new byte[BUFFER];
		FileInputStream fi = null;
		int count;

		try {

			/* Source file */
			fi = new FileInputStream(sourcePath);
			origin = new BufferedInputStream(fi, BUFFER);
			iOpen = true;

			/* Destination file */
			dest = new FileOutputStream(destinationPath+fileSeparator+fileName);
			out = new GZIPOutputStream(new BufferedOutputStream(dest));
			oOpen = true;

			/* Write data */
			while ((count = origin.read(data, 0, BUFFER)) != -1) {
				out.write(data, 0, count);
			}

			/* Close files */
			origin.close();
			out.close();

		} catch (Exception e) {
			return null;
		}

		if (iOpen) {
			try {
				fi.close();
			} catch (Exception e) {
			}
		}

		if (oOpen) {
			try {
				dest.flush();
				dest.close();
			} catch (Exception e) {	}
		}

		return (fileName);
	}

	/**
	 *
	 * Decode input file. Decode means to translate
	 * byte values expressed in ASCII form into
	 * binary. I.e. "3132" -> 0x310x32.
	 *
	 * @param srcFile source file info
	 *
	 * @return 0 OK else error
	 */
	protected static int decodeFile(String srcFile) {
		
		FileReader fr=null;							// Input file reader
		FileOutputStream fos;						// Output file
		DataOutputStream dest=null;					// Output file
		char data[] = new char[2];					// Char couple containing value
		boolean iOpen = false, oOpen = false;		// Output flags
		int rc = 0;									// Return code
		int count;									// Read characters number
		int value;									// Converted value
		byte [] oBuff = new byte[128*1024];			// Buffered output 
		int i=0;									// Output buffer index
		char digit;									// Single digit

		try {
			/* Open files */
			fr = new FileReader(srcFile);

			iOpen = true;

			fos = new FileOutputStream(srcFile+"_dec");
			dest = new DataOutputStream(fos);

			oOpen = true;


			/* Read and write */
			while((count = fr.read(data, 0, 2)) != -1) {

				if (count == 1) {
					rc = -1;
					break;
				}

				/* Compute value */
				value = 0;

				digit = data[0];
				if ((digit <= '9') && (digit >= '0')) {
					value = 16 * (digit - '0');
				} else if ((digit <= 'f') && (digit >= 'a')) {
					value = 16 * (digit - 'a' + 10);
				} else if ((digit <= 'F') && (digit >= 'A')) {
					value = 16 * (digit - 'A' + 10);
				}

				digit = data[1];
				if ((digit <= '9') && (digit >= '0')) {
					value += (digit - '0');
				} else if ((digit <= 'f') && (digit >= 'a')) {
					value += (digit - 'a' + 10);
				} else if ((digit <= 'F') && (digit >= 'A')) {
					value += (digit - 'A' + 10);
				}

				oBuff[i] = (byte)value;

				i++;

				/* Completed buffer ? */
				if (i == oBuff.length) {
					dest.write(oBuff, 0, i);
					i=0;
				}
			}

			/* Is there any byte to write ? */
			if (i > 0) {
				dest.write(oBuff, 0, i);
			}

		} catch (Exception e) {
			rc = -1;
		}

		if (iOpen) {
			try {
				fr.close();
			} catch (Exception e) {
			}
		}

		if (oOpen) {
			try {
				dest.flush();
				dest.close();
			} catch (Exception e) {
			}
		}
		
		try {
			File fd = new File(srcFile);
			fd.delete();
			fd = new File(srcFile+"_dec");
			fd.renameTo(new File(srcFile));
		} catch (Exception e) {	}
		return (rc);
	}

	/**
	 *
	 * Encode input file. Encode means to translate
	 * byte values expressed in binary form into ASCII form.
	 * I.e. 0x310x32 -> "3132".
	 *
	 * @param srcFile source file info
	 *
	 * @return 0 OK else error
	 */
	protected static int encodeFile(String srcFile) {

		FileWriter fw=null;							// Output file writer
		FileInputStream fis = null;					// Input file
		DataInputStream source=null;				// Input file
		boolean iOpen = false, oOpen = false;		// Output flags
		int value;									// Binary value
		int rc = 0;									// Return code
		String hVal;								// Hexadecimal value
		byte [] rBuff = new byte[65536];			// Read buffer
		int tmpInt;									// Temporary int
		int i;										// Generic counter
		boolean eof = false;						// Eof flag

		try {

			fis = new FileInputStream(srcFile);
			source = new DataInputStream(fis);

			iOpen = true;

			fw = new FileWriter(srcFile+"_enc");

			oOpen = true;
			
			while(!eof) {

				value = source.read(rBuff);

				if (value < rBuff.length) {
					eof = true;
				}
				
				for (i=0; i<value; i++) {

					tmpInt = (int)rBuff[i];

					if (tmpInt < 0) {
						tmpInt = 256 - (-rBuff[i]);
					}

					hVal = Integer.toHexString(tmpInt);

					if (tmpInt < 16) {
						hVal = "0" + hVal;
					}

					fw.write(hVal);
				}
			}
			

		} catch (Exception e) {
			rc = -1;
		}

		if (iOpen) {
			try {
				fis.close();
			} catch (Exception e) {	
				rc = -1;
			}
		}

		if (oOpen) {
			try {
				fw.flush();
				fw.close();
			} catch (Exception e) {	
				rc = -1;				
			}
		}
		
		try {
			File fd = new File(srcFile);
			fd.delete();
			fd = new File(srcFile+"_enc");
			fd.renameTo(new File(srcFile));
		} catch (Exception e) {	}
		return (rc);
	}
	

	/**
	 * 
	 * Crea la directory temporanea passata come parametro se non già esistente
	 * 
	 * @param path - path assoluto della directory da creare
	 * @return - 0 OK, else -1
	 */
	protected static int mkdir(String path) {

		String Dir = path;
		int rc = 0; // return code

		try {
			File fdir = new File(Dir);

			if (!fdir.isDirectory()) {

				boolean success = fdir.mkdirs();

		    	if (success) {

		    		return 0;
				} else {

					return -1;
				}
			}

		} catch (Exception e) {
			
			rc = -1;
		}
		return rc;
	}
	
	
	
	/**
	 * 
	 * Elimina il contenuto della directory passata come parametro e, 
	 * se tot == true, elimina la directory stessa.
	 * 
	 * @param dir - path assoluto della directory da eliminare
	 * @param tot - se true viene eliminata anche la directory, se false viene eliminato solo il contenuto
	 * 
	 * 
	 * @return true OK, else false
	 */
    protected static boolean delDir(File dir, boolean tot) {

    	boolean success = true;

    	if (dir.isDirectory()) {

    		String[] contenuto = dir.list();
    		
    		if (contenuto.length > 0) {

      	    	for (int i = 0; i < contenuto.length; i++) {
      	  	    	
      	    		success = delDir(new File(dir, contenuto[i]), false);
    	        
      	    		if (!success) { return false; }
      	    	}
			}
    	}
    	else {
    		
    		success = dir.delete();
    		
    		return success;
    	}

    	if (tot == true) {

    		return dir.delete();
		}

    	return success;
  }
}
