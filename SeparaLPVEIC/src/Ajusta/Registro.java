package Ajusta;

public class Registro {

	String registro="";
	
	public Registro(){

	}

	public Registro (String registro){

		this.registro  = registro;
	}
	
	public String getRegistro(){
		return registro;
	}
	
	public void setRegistro(String registro){
		this.registro = registro;
	}
	
	@Override
	public String toString(){
		return registro;
	}

}



	

	
