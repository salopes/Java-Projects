package AccessDDU;

/**
 * DduFile_Ex.java
 * 
 * @author Tullio Ferralis - While1 s.r.l.
 *
 * @version 2.0	-	20071119
 * @version 2.2 - 	20080125
 * @version 2.3	-	20080225
 * @version 2.4	-	20080318
 * @version 2.5	-	20080326
 */

public class DduFileEx  {

	private java.lang.String c_market;
	
	private java.lang.String c_user;
	
	private java.lang.String c_file_id;

    private java.lang.String c_nomefile;

    private java.lang.String desc_file;

    private java.lang.String c_stato_file;

    private java.lang.String n_size;

    private java.lang.String d_creazione;

    private java.lang.String c_estensione;

    private java.lang.String c_md5_checksum;

    private java.lang.String c_controlledpath;

    private java.lang.String c_working_mode;

    private java.lang.String d_acquisizione;
    
    private java.lang.String c_causale;

    private java.lang.String desc_causale;

    private java.lang.String n_rec_fisici;

    private java.lang.String n_rec_logici;

    private java.lang.String n_linea_errore;

    private java.lang.String n_colonna_errore;

    private java.lang.String c_nome_campo;

    private java.lang.String n_download;

    private java.lang.String c_compress;

    private java.lang.String c_encoding;

    public DduFileEx() {
    }

    public DduFileEx(
    		java.lang.String c_market,
    		java.lang.String c_user,
    	    java.lang.String c_file_id,
    	    java.lang.String c_nomefile,
    	    java.lang.String desc_file,
    	    java.lang.String c_stato_file,
    	    java.lang.String n_size,
    	    java.lang.String d_creazione,
    	    java.lang.String c_estensione,
    	    java.lang.String c_md5_checksum,
    	    java.lang.String c_controlledpath,
    	    java.lang.String c_working_mode,
    	    java.lang.String d_acquisizione,
    	    java.lang.String c_causale,
    	    java.lang.String desc_causale,
    	    java.lang.String n_rec_fisici,
    	    java.lang.String n_rec_logici,
    	    java.lang.String n_linea_errore,
    	    java.lang.String n_colonna_errore,
    	    java.lang.String c_nome_campo,
    	    java.lang.String n_download,
    	    java.lang.String c_compress,
    	    java.lang.String c_encoding) {
    	
    		this.c_market			= c_market;
    		this.c_user				= c_user;
			this.c_file_id			= c_file_id;
			this.c_nomefile			= c_nomefile;
			this.desc_file			= desc_file;
			this.c_stato_file		= c_stato_file;
			this.n_size				= n_size;
			this.d_creazione			= d_creazione;
			this.c_estensione		= c_estensione;
			this.c_md5_checksum		= c_md5_checksum;
			this.c_controlledpath	= c_controlledpath;
			this.c_working_mode		= c_working_mode;
			this.d_acquisizione		= d_acquisizione;
			this.c_causale			= c_causale;
			this.desc_causale		= desc_causale;
			this.n_rec_fisici		= n_rec_fisici;
			this.n_rec_logici		= n_rec_logici;
			this.n_linea_errore		= n_linea_errore;
			this.n_colonna_errore	= n_colonna_errore;
			this.c_nome_campo		= c_nome_campo;
			this.n_download			= n_download;
			this.c_compress			= c_compress;
			this.c_encoding			= c_encoding;
    }


    
    /**
     * Gets the c_market value for this DduFile_Ex.
     * 
     * @return c_market
     */
    public java.lang.String getMarket() {
        return c_market;
    }


    /**
     * Sets the c_market value for this DduFile_Ex.
     * 
     * @param market
     */
    public void setMarket(java.lang.String c_market) {
        this.c_market = c_market;
    }
    
    

    /**
     * Gets the c_user value for this DduFile_Ex.
     * 
     * @return c_user
     */
    public java.lang.String getUser() {
        return c_user;
    }


    /**
     * Sets the c_user value for this DduFile_Ex.
     * 
     * @param c_user
     */
    public void setUser(java.lang.String c_user) {
        this.c_user = c_user;
    }
    
    
    
    /**
     * Gets the fileId value for this DduFile_Ex.
     * 
     * @return c_file_id
     */
    public java.lang.String getFileid() {
        return c_file_id;
    }


    /**
     * Sets the fileId value for this DduFile_Ex.
     * 
     * @param c_file_id
     */
    public void setFileid(java.lang.String c_file_id) {
        this.c_file_id = c_file_id;
    }


    /**
     * Gets the file name value for this DduFile_Ex.
     * 
     * @return c_nomefile
     */
    public java.lang.String getFileName() {
        return c_nomefile;
    }


    /**
     * Sets the file name value for this DduFile_Ex.
     * 
     * @param c_nomefile
     */
    public void setFileName(java.lang.String c_nomefile) {
        this.c_nomefile = c_nomefile;
    }


    /**
     * Gets the desc_file value for this DduFile_Ex.
     * 
     * @return desc_file
     */
    public java.lang.String getDescFile() {
        return desc_file;
    }


    /**
     * Sets the desc_file value for this DduFile_Ex.
     * 
     * @param desc_file
     */
    public void setDescFile(java.lang.String desc_file) {
        this.desc_file = desc_file;
    }


    /**
     * Gets the c_stato_file value for this DduFile_Ex.
     * 
     * @return c_stato_file
     */
    public java.lang.String getStatoFile() {
        return c_stato_file;
    }


    /**
     * Sets the c_stato_file value for this DduFile_Ex.
     * 
     * @param c_stato_file
     */
    public void setStatoFile(java.lang.String c_stato_file) {
        this.c_stato_file = c_stato_file;
    }


    /**
     * Gets the n_size value for this DduFile_Ex.
     * 
     * @return n_size
     */
    public java.lang.String getSize() {
        return n_size;
    }


    /**
     * Sets the n_size value for this DduFile_Ex.
     * 
     * @param n_size
     */
    public void setSize(java.lang.String n_size) {
        this.n_size = n_size;
    }


    /**
     * Gets the d_creazione value for this DduFile_Ex.
     * 
     * @return d_creazione
     */
    public java.lang.String getDataCreazione() {
        return d_creazione;
    }


    /**
     * Sets the d_creazione value for this DduFile_Ex.
     * 
     * @param d_creazione
     */
    public void setDataCreazione(java.lang.String d_creazione) {
        this.d_creazione = d_creazione;
    }


    /**
     * Gets the c_estensione value for this DduFile_Ex.
     * 
     * @return c_estensione
     */
    public java.lang.String getEstensione() {
        return c_estensione;
    }


    /**
     * Sets the c_estensione value for this DduFile_Ex.
     * 
     * @param c_estensione
     */
    public void setEstensione(java.lang.String c_estensione) {
        this.c_estensione = c_estensione;
    }


    /**
     * Gets the c_md5_checksum value for this DduFile_Ex.
     * 
     * @return c_md5_checksum
     */
    public java.lang.String getChecksum() {
        return c_md5_checksum;
    }


    /**
     * Sets the c_md5_checksum value for this DduFile_Ex.
     * 
     * @param c_md5_checksum
     */
    public void setChecksum(java.lang.String c_md5_checksum) {
        this.c_md5_checksum = c_md5_checksum;
    }
    
    
    /**
     * Gets the c_controlledpath value for this DduFile_Ex.
     * 
     * @return c_controlledpath
     */
    public java.lang.String getControlledPath() {
        return c_controlledpath;
    }


    /**
     * Sets the c_controlledpath value for this DduFile_Ex.
     * 
     * @param c_controlledpath
     */
    public void setControlledPath(java.lang.String c_controlledpath) {
        this.c_controlledpath = c_controlledpath;
    }


    /**
     * Gets the c_working_mode value for this DduFile_Ex.
     * 
     * @return c_working_mode
     */
    public java.lang.String getWorkingMode() {
        return c_working_mode;
    }


    /**
     * Sets the c_working_mode value for this DduFile_Ex.
     * 
     * @param c_working_mode
     */
    public void setWorkingMode(java.lang.String c_working_mode) {
        this.c_working_mode = c_working_mode;
    }


    /**
     * Gets the d_acquisizione value for this DduFile_Ex.
     * 
     * @return d_acquisizione
     */
    public java.lang.String getDataAcquisizione() {
        return d_acquisizione;
    }


    /**
     * Sets the d_acquisizione value for this DduFile_Ex.
     * 
     * @param d_acquisizione
     */
    public void setDataAcquisizione(java.lang.String d_acquisizione) {
        this.d_acquisizione = d_acquisizione;
    }


    /**
     * Gets the c_causale value for this DduFile_Ex.
     * 
     * @return c_causale
     */
    public java.lang.String getCodCausale() {
        return c_causale;
    }


    /**
     * Sets the c_causale value for this DduFile_Ex.
     * 
     * @param c_causale
     */
    public void setCodCausale(java.lang.String c_causale) {
        this.c_causale = c_causale;
    }
    
    
    
    /**
     * Gets the desc_causale  for this DduFile_Ex.
     * 
     * @return desc_causale
     */
    public java.lang.String getDescCausale() {
        return desc_causale;
    }


    /**
     * Sets the desc_causale value for this DduFile_Ex.
     * 
     * @param desc_causale
     */
    public void setDescCausale(java.lang.String desc_causale) {
        this.desc_causale = desc_causale;
    }


    /**
     * Gets the n_rec_fisici value for this DduFile_Ex.
     * 
     * @return n_rec_fisici
     */
    public java.lang.String getNumRecFisici() {
        return n_rec_fisici;
    }


    /**
     * Sets the n_rec_fisici value for this DduFile_Ex.
     * 
     * @param c_nomefile
     */
    public void setNumRecFisici(java.lang.String n_rec_fisici) {
        this.n_rec_fisici = n_rec_fisici;
    }


    /**
     * Gets the n_rec_logici value for this DduFile_Ex.
     * 
     * @return desc_file
     */
    public java.lang.String getNumRecLogici() {
        return n_rec_logici;
    }


    /**
     * Sets the n_rec_logici value for this DduFile_Ex.
     * 
     * @param n_rec_logici
     */
    public void setNumRecLogici(java.lang.String n_rec_logici) {
        this.n_rec_logici = n_rec_logici;
    }


    /**
     * Gets the n_linea_errore value for this DduFile_Ex.
     * 
     * @return n_linea_errore
     */
    public java.lang.String getNumLineaErrore() {
        return n_linea_errore;
    }


    /**
     * Sets the n_linea_errore value for this DduFile_Ex.
     * 
     * @param n_linea_errore
     */
    public void setNumLineaErrore(java.lang.String n_linea_errore) {
        this.n_linea_errore = n_linea_errore;
    }


    /**
     * Gets the n_colonna_errore value for this DduFile_Ex.
     * 
     * @return n_colonna_errore
     */
    public java.lang.String getNumColonnaErrore() {
        return n_colonna_errore;
    }


    /**
     * Sets the n_colonna_errore value for this DduFile_Ex.
     * 
     * @param n_colonna_errore
     */
    public void setNumColonnaErrore(java.lang.String n_colonna_errore) {
        this.n_colonna_errore = n_colonna_errore;
    }


    /**
     * Gets the c_nome_campo value for this DduFile_Ex.
     * 
     * @return c_nome_campo
     */
    public java.lang.String getNomeCampo() {
        return c_nome_campo;
    }


    /**
     * Sets the c_nome_campo value for this DduFile_Ex.
     * 
     * @param c_nome_campo
     */
    public void setNomeCampo(java.lang.String c_nome_campo) {
        this.c_nome_campo = c_nome_campo;
    }


    /**
     * Gets the n_download value for this DduFile_Ex.
     * 
     * @return n_download
     */
    public java.lang.String getNumDownload() {
        return n_download;
    }


    /**
     * Sets the n_download value for this DduFile_Ex.
     * 
     * @param n_download
     */
    public void setNumDownload(java.lang.String n_download) {
        this.n_download = n_download;
    }


    /**
     * Gets the c_compress value for this DduFile_Ex.
     * 
     * @return c_compress
     */
    public java.lang.String getCompress() {
        return c_compress;
    }


    /**
     * Sets the c_compress value for this DduFile_Ex.
     * 
     * @param c_compress
     */
    public void setCompress(java.lang.String c_compress) {
        this.c_compress = c_compress;
    }


    /**
     * Gets the c_encoding value for this DduFile_Ex.
     * 
     * @return c_encoding
     */
    public java.lang.String getEncoding() {
        return c_encoding;
    }


    /**
     * Sets the c_encoding value for this DduFile_Ex.
     * 
     * @param c_encoding
     */
    public void setEncoding(java.lang.String c_encoding) {
        this.c_encoding = c_encoding;
    }
    
}
