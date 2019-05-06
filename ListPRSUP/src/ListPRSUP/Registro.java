package ListPRSUP;

import java.lang.Comparable;

public class Registro implements Comparable <Registro> {

	String sRegistro;

	public Registro() {
		// TODO Auto-generated constructor stub
	}
	
	public Registro (String sRegistro){

		this.sRegistro  = sRegistro;
	}

	
	public String getsRegistro() {
		return sRegistro;
	}	

	
	public void setsRegistro(String sRegistro) {
		this.sRegistro = sRegistro;
	}	
	
	
	@Override
	public int compareTo(Registro order) {
			return sRegistro.compareTo(order.getsRegistro());
	}	

	@Override
	public String toString() {
		return sRegistro;
	
	}
	
}
