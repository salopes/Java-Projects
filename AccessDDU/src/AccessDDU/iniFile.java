package AccessDDU;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;

/**
 *
 * Interface for ini files in standard windows format.
 *
 * @author While1 s.r.l.
 * @version 0.0
 *
 */

public class iniFile {

	/*
	 * Private variables
	 */
	private static String fileName;
	private static String secName;
	private static String keyName;
	private static String addStr; //for WritePrivateProfileString
	private static String defStr; //for GetPrivateProfileString

	//private static String iniDir = System.getProperty("user.home") + System.getProperty("file.separator") + DDU_Const_Value.INI_FILE_DIR;
	private static String iniDir = "";

	private static RandomAccessFile raf;
	private static RandomAccessFile tFile;


	/**
	 *
	 * Write a string on ini file.
	 *
	 * @param sn Section
	 * @param kn Key to set
	 * @param as Value to impose
	 * @param fn ini file name
	 *
	 * @return true if ok
	 */
	synchronized protected static boolean writePrivateProfileString(String sn, String kn, String as, String fn) {

		fileName = iniDir + System.getProperty("file.separator") + fn;
		secName = sn;
		keyName = kn;
		addStr = as;
		String str,temp="";
		boolean flag=false;
		int no;
		String tmpFile = "";

		/* Compose tmpFile name */
		String osName = System.getProperty("os.name" );

		if (osName.equals( "Windows NT" ) ||
			osName.equals( "Windows 95" ) ||
			osName.equals( "Windows 2000" ) ||
			osName.equals( "Windows XP" )) {

			tmpFile = "c:";
		} else if( osName.equals( "Linux" ) ) {

			tmpFile = "/tmp";

		}

		tmpFile += System.getProperty("file.separator") + "ddutmp" + System.currentTimeMillis();

		try {
			File f = new File(tmpFile);
			if(f.exists()) {
				f.delete();
			}

			raf = new RandomAccessFile(fileName,"rw");
			tFile = new RandomAccessFile(tmpFile,"rw");

			str = raf.readLine();
			while(str != null) {

				tFile.writeBytes(str + "\r\n");
				str = str.trim();

				if (str.length() == 0) {
					str = raf.readLine();
					continue;
				}

				if((str.charAt(0) == '[') && (str.charAt(str.length()-1) == ']')) {

					if(str.substring(1,str.length()-1).equalsIgnoreCase(secName)) {

						A:while((str = raf.readLine())!= null) {

							if(str.trim().charAt(0) != '[') {
								str = str.trim();
								no=str.indexOf('=');
								temp=str.substring(0,no);
								if(temp.equalsIgnoreCase(keyName)) {
									temp = temp + "=" + addStr + "\r\n";
									tFile.writeBytes(temp);

									flag = true;
								} else
									tFile.writeBytes(str + "\r\n");
							} else {

								// New section
								break A;
							}
						} // while
						//This is to write new keyname and its vaues
						if(!flag) {
							temp = keyName+"="+addStr+"\r\n";
							tFile.writeBytes(temp);
							// System.out.println("It could not find Keyname");
							flag=true;
						}
					} else {
						str = raf.readLine();
					}
				} else {
					str = raf.readLine();
				}

			} //while str!=null

			if(!flag) {
				tFile.writeBytes("["+secName+"]"+"\r\n");
				temp = keyName+"="+addStr+"\r\n";
				tFile.writeBytes(temp);
				// System.out.println("It couldn't find "+secName);    //tFile.writeBytes(str + "2\r\n");
			}

			tFile.close();
			raf.close();
		} catch(IOException e) {
                        // System.out.println(e);
                        // e.printStackTrace();
		}

		File f1 = new File(fileName);
		File f2 = new File(tmpFile);
		if(!f1.delete()) {
			// System.out.println("It could not delete"+fileName);
		}

		flag = f2.renameTo(new File(fileName));

		return flag;
	}

	/**
	 *
	 * Reads a value from ini file.
	 *
	 * @param sn Section name
	 * @param kn Key name
	 * @param ds Default value
	 * @param fn File name
	 *
	 * @return String with value or default value (if not found)
	 */
	synchronized protected static String getPrivateProfileString(String sn, String kn, String ds, String fn) {

		fileName = iniDir  + System.getProperty("file.separator") + fn;
        secName = sn;
		keyName = kn;
		defStr = ds;
		String str;
		String temp="";
		int no;

		try {
			raf = new RandomAccessFile(fileName,"r");
			str = raf.readLine();
			A: while(str != null) {

				str = str.trim();
				if (str.length() > 0) {

					if((str.charAt(0) == '[') && (str.charAt(str.length()-1) == ']')) {

						if(str.substring(1,str.length()-1).equalsIgnoreCase(secName)) {
							while((str = raf.readLine())!= null) {
								if((str.trim().charAt(0)) != '[') {
									str = str.trim();
									no = str.indexOf('=');

									if (no == -1) {
										break A;
									}

									temp=str.substring(0,no);
									if(temp.equalsIgnoreCase(keyName)) {
										temp = str.substring(no+1);
										// System.out.println("Value found " + str + "  " +temp);
										break A;
									}
								} else {
									break A;
								}
								temp="";

							} // while
						}
					}
				}
				str = raf.readLine();
			} //while str!=null
			raf.close();
		} catch(IOException e) {
                        // System.out.println(e);
                        // e.printStackTrace();
		}

		if(temp=="") {
			return defStr;
		} else {
			return temp;
		}
	}

	/**
	 *
	 * Reads a value from ini file.
	 *
	 * @param sn Section name
	 * @param kn Key name
	 * @param ds Default value
	 * @param fn File name
	 *
	 * @return String with value or default value (if not found)
	 */
	synchronized protected static String getPrivateProfileString(String inputIniDir, String sn, String kn, String ds, String fn) {

            iniDir = inputIniDir;

            return(getPrivateProfileString(sn, kn, ds, fn));
    }
	
	//for testing
//	public static void main(String[] args) {
//		// boolean b = writePrivateProfileString("SecV","abc3","RKVarma2","c:\\tmp\\rkv.ini");
//		boolean b = writePrivateProfileString(args[0],args[1],args[2],"c:\\tmp\\rkv.ini");
//		System.out.println("Value found " + b);
//		// String str = getPrivateProfileString("SecV","abc3","Default","c:\\tmp\\rkv.ini");
//		String str = getPrivateProfileString(args[3],args[4],args[5],"c:\\tmp\\rkv.ini");
//		System.out.println("Value found " + str);
//
//	}
}
