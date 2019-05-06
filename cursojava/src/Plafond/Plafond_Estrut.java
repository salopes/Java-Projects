package Plafond;

import java.lang.Comparable;

public class Plafond_Estrut implements Comparable <Plafond_Estrut> {

	String codDealer;
	String FDP;
	String pIndustrial;
	String codStatus;
	String detStatus;
	String lOpcionais;
	String pComercial;
    
	public Plafond_Estrut() {
		// TODO Auto-generated constructor stub
	}
	
	public Plafond_Estrut(String codDealer, String FDP, String pIndustrial, String codStatus, String detStatus, String lOpcionais, String pComercial){
		
		this.codDealer      = codDealer;
		this.FDP            = FDP;      
		this.pIndustrial    = pIndustrial;   
		this.codStatus      = codStatus; 
		this.detStatus      = detStatus; 
		this.lOpcionais     = lOpcionais; 
		this.pComercial     = pComercial; 
	}
	

	public String getcodDealer() {
		return codDealer;
	}

	public void setcodDealer(String codDealer) {
		this.codDealer = codDealer;
	}

	public String getFDP() {
		return FDP;
	}

	public void setFDP(String FDP) {
		this.FDP = FDP;
	}

	public String getpIndustrial() {
		return pIndustrial;
	}

	public void setpIndustriala(String pIndustrial) {
		this.pIndustrial = pIndustrial;
	}
	
	public String getcodStatus() {
		return codStatus;
	}

	public void setcodStatus(String codStatus) {
		this.codStatus = codStatus;
	}

	public String getdetStatus() {
		return detStatus;
	}

	public void setdetStatus(String detStatus) {
		this.detStatus = detStatus;
	}

	public String getlOpcionais() {
		return lOpcionais;
	}

	public void setlOpcionais(String lOpcionais) {
		this.lOpcionais = lOpcionais;
	}

	public String getpComercial() {
		return pComercial;
	}

	public void setpComercial(String pComercial) {
		this.pComercial = pComercial;
	}

	@Override
	public int compareTo(Plafond_Estrut order) {
			return codDealer.compareTo(order.getcodDealer());
	}	
	
	@Override
	public String toString() {
		return codDealer + ";" + FDP + ";" + pIndustrial + ";" + codStatus + ";" + detStatus + ";" + lOpcionais + ";" + pComercial;
		
	}
}
