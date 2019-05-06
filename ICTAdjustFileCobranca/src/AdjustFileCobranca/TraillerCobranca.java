package AdjustFileCobranca;

public class TraillerCobranca {

	String IDENTIFICADOR;
	String DADOS;
	String FILLER;
	public TraillerCobranca() {
		// TODO Auto-generated constructor stub
	}
	
	public TraillerCobranca(String IDENTIFICADOR, String DADOS, String FILLER) {
		this.IDENTIFICADOR  = IDENTIFICADOR ;
		this.DADOS   = DADOS  ; 
		this.FILLER   = FILLER  ;
	}

	public String getIDENTIFICADOR() {
		return IDENTIFICADOR;
	}

	public void setIDENTIFICADOR(String IDENTIFICADOR){
	    this.IDENTIFICADOR = IDENTIFICADOR;
	}

	public String getDADOS() {
		return DADOS;
	}

	public void setDADOS(String DADOS) {
		this.DADOS = DADOS;
	}

	public String getFILLER() {
		return FILLER;
	}

	public void setFILLER(String FILLER) {
		this.FILLER = FILLER;
	}
	
	@Override
	public String toString() {
		return IDENTIFICADOR + DADOS + FILLER;
	}
	
}
