package LotoFacil;

public class LotoLinha_Estrut {

	String qtdSem01;
	String qtdSem02;
	String qtdSem03;
	String qtdSem04;
	String qtdSem05;
    
	public LotoLinha_Estrut() {
		// TODO Auto-generated constructor stub
	}
	
	public LotoLinha_Estrut(String qtdSem01, String qtdSem02, String qtdSem03, String qtdSem04, String qtdSem05) {
		
		this.qtdSem01  = qtdSem01; 
		this.qtdSem02  = qtdSem02; 
		this.qtdSem03  = qtdSem03; 
		this.qtdSem04  = qtdSem04; 
		this.qtdSem05  = qtdSem05; 
	}
	
	public String getqtdSem01() {
		return qtdSem01;
	}

	public void setqtdSem01(String qtdSem01) {
		this.qtdSem01 = qtdSem01;
	}

	public String getqtdSem02() {
		return qtdSem02;
	}

	public void setqtdSem02(String qtdSem02) {
		this.qtdSem02 = qtdSem02;
	}

	public String getqtdSem03() {
		return qtdSem03;
	}

	public void setqtdSem03(String qtdSem03) {
		this.qtdSem03 = qtdSem03;
	}

	public String getqtdSem04() {
		return qtdSem04;
	}

	public void setqtdSem04(String qtdSem04) {
		this.qtdSem04 = qtdSem04;
	}

	public String getqtdSem05() {
		return qtdSem05;
	}

	public void setqtdSem05(String qtdSem05) {
		this.qtdSem05 = qtdSem05;
	}

	
	@Override
	public String toString() {
		return "Soma" + ";" + qtdSem01 + ";" + qtdSem02 + ";" + qtdSem03 + ";" + qtdSem04 + ";" + qtdSem05;
	}
	
}
