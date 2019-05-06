package Ajusta;

public class RedeAtiva {

	String CODDEALER;
	
	public RedeAtiva() {
		// TODO Auto-generated constructor stub
	}
	
	public RedeAtiva(String CODDEALER) {
		this.CODDEALER  = CODDEALER ;
	}
	
	public String getCODDEALER() {
		return CODDEALER;
	}

	public void setCODDEALER(String CODDEALER){
	    this.CODDEALER = CODDEALER;
	}

	@Override
	public String toString() {
		return CODDEALER;
	}
}
