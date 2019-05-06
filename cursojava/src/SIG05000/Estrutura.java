package SIG05000;

import java.lang.Comparable;

public class Estrutura implements Comparable <Estrutura> {

	String Parte;

	public Estrutura() {
		// TODO Auto-generated constructor stub
	}

	public Estrutura(String Parte) {
		
		this.Parte = Parte;
	}
	
	public String getParte() {
		return Parte;
	}

	public void setParte(String Parte) {
		this.Parte = Parte;
	}
	
	@Override
	public int compareTo(Estrutura order) {
			return Parte.compareTo(order.getParte());
	}	
	
	@Override
	public String toString() {
		return Parte;
	}
}
