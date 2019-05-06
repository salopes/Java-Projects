package CSI;

import java.lang.Comparable;

public class SQLEstrutCSI implements Comparable <SQLEstrutCSI> {
	String sData="";
	String sDealer="";
	String sKPI="";
	String sValor="";
	String CHAVESORT;
	
	public SQLEstrutCSI() {
		// TODO Auto-generated constructor stub
	}
	
	public SQLEstrutCSI(String sData, String sDealer, String sKPI, String sValor, String CHAVESORT) {
		this.sData     = sData;
		this.sDealer   = sDealer;
		this.sKPI      = sKPI;
		this.sValor    = sValor;
		this.CHAVESORT = CHAVESORT;
	}
	
	public String getsData() {
		return sData;
	}

	public void setsData(String sData){
	    this.sData = sData;
	}

	public String getsDealer() {
		return sData;
	}

	public void setsDealer(String sDealer){
	    this.sDealer = sDealer;
	}

	public String getsValor() {
		return sValor;
	}

	public void setsValor(String sValor){
	    this.sValor = sValor;
	}

	public String getsKPI() {
		return sKPI;
	}

	public void setsKPI(String sKPI){
	    this.sKPI = sKPI;
	}
	
	public String getCHAVESORT() {
	    return CHAVESORT;
	}

	public void setCHAVESORT(String CHAVESORT) {
	    this.CHAVESORT = CHAVESORT;
	}	
	
	@Override
	public int compareTo(SQLEstrutCSI order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return sData + ";" + sDealer + ";" + sKPI + ";" + sValor;
	}
}
