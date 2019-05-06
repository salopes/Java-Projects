package ListPRSUP;

public class RedeAtiva {

	String CODDEALER;
	String UF;
	
	public RedeAtiva() {
		// TODO Auto-generated constructor stub
	}
	
	public RedeAtiva(String CODDEALER, String UF) {
		this.CODDEALER  = CODDEALER ;
		this.UF  = UF ;
	}
	
	public String getCODDEALER() {
		return CODDEALER;
	}

	public String getUF() {
		return UF;
	}
	
	public void setCODDEALER(String CODDEALER){
	    this.CODDEALER = CODDEALER;
	}

	public void setUF(String UF){
	    this.UF = UF;
	}
	
	@Override
	public String toString() {
		return CODDEALER + ";" + UF;
	}
}
