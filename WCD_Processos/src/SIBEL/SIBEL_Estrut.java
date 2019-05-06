package SIBEL;

public class SIBEL_Estrut {

	String sDealer="";
	String sAceitacao="";
	String sSLA="";
	String sTotal="";
	String sData="";
	
	public SIBEL_Estrut() {
		// TODO Auto-generated constructor stub
	}
	
	public SIBEL_Estrut(String sDealer, String sAceitacao, String sSLA, String sTotal, String sData) {
		this.sDealer       = sDealer;
		this.sAceitacao    = sAceitacao;
		this.sSLA          = sSLA;
		this.sTotal        = sTotal;
		this.sData         = sData;
	}
	
	public String getsDealer() {
		return sDealer;
	}

	public void setsDealer(String sDealer){
	    this.sDealer = sDealer;
	}
	
	public String getsAceitacao() {
		return sAceitacao;
	}

	public void setsAceitacao(String sAceitacao){
	    this.sAceitacao = sAceitacao;
	}

	public String getsSLA() {
		return sSLA;
	}

	public void setsSLA(String sSLA){
	    this.sSLA = sSLA;
	}
	
	public String getsTotal() {
		return sTotal;
	}

	public void setsTotal(String sTotal){
	    this.sTotal = sTotal;
	}
	

	public String getsData() {
		return sData;
	}

	public void setsData(String sData){
	    this.sData = sData;
	}

	
	@Override
	public String toString() {
		return sDealer + ";" + sAceitacao + ";" + sSLA + ";" + sTotal + ";" + sData;
	}
}
