package AllRisk;

import java.lang.Comparable;

public class AllRisk_Estrut implements Comparable <AllRisk_Estrut> {

	String  sData="";
	String  sDealer="";
	Integer iOcorrencia=0;
	String CHAVESORT;
	
	public AllRisk_Estrut() {
		// TODO Auto-generated constructor stub
	}
	
	public AllRisk_Estrut(String sData, String sDealer, Integer iOcorrencia, String CHAVESORT) {
		this.sData        = sData ;
		this.sDealer      = sDealer ;
		this.iOcorrencia  = iOcorrencia ;
		this.CHAVESORT    = CHAVESORT;
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

	public Integer getiOcorrencia() {
		return iOcorrencia;
	}

	public void setiOcorrencia(Integer iOcorrencia){
	    this.iOcorrencia = iOcorrencia;
	}
	
	public String getCHAVESORT() {
	    return CHAVESORT;
	}

	public void setCHAVESORT(String CHAVESORT) {
	    this.CHAVESORT = CHAVESORT;
	}	
	
	@Override
	public int compareTo(AllRisk_Estrut order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return sData + ";" + sDealer + ";" + iOcorrencia;
	}
}
