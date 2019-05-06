package Controle;

import gnu.io.*;
import java.util.Enumeration;

public class Portas {

	private String[]    PORTA_NOME;    //Informa o Nome das portas disponiveis
	private String[]    TIPO_PORT;    //Informa os tipos de Portas: Paralela, RS232 etc

	public String[] getPORTA_NOME() {
		return PORTA_NOME;
	}

	public void setPORTA_NOME(String[] pORTA_NOME) {
		PORTA_NOME = pORTA_NOME;
	}

	public String[] getTIPO_PORT() {
		return TIPO_PORT;
	}

	public void setTIPO_PORT(String[] tIPO_PORT) {
		TIPO_PORT = tIPO_PORT;
	}
	
	
	Enumeration LISTA_PORTA;  //Lista de todas as portas seriais disponíveis
	
	//Retorna as portas Seriais
	public String[] RetornaPorta(){
		LISTA_PORTA = CommPortIdentifier.getPortIdentifiers();
		PORTA_NOME  = new String[10];
		TIPO_PORT   = new String[10];
		
		int i=0;
		
		while(LISTA_PORTA.hasMoreElements()) {
			CommPortIdentifier NUM_PORTA = (CommPortIdentifier)LISTA_PORTA.nextElement();
			PORTA_NOME[i] = NUM_PORTA.getName();
			TIPO_PORT[i]  = Tipo_Porta(NUM_PORTA.getPortType());
			i++;
		}
		
		return PORTA_NOME;
		
	}
	
	public String Tipo_Porta(int tipoPorta) {
		switch(tipoPorta) {
			case CommPortIdentifier.PORT_SERIAL:
				return "Porta Serial";
			case CommPortIdentifier.PORT_PARALLEL:
				return "Porta Paralela";
			case CommPortIdentifier.PORT_I2C:
				return "Porta I2C";
			case CommPortIdentifier.PORT_RS485:
				return "Porta RS485";
			case CommPortIdentifier.PORT_RAW:
				return "Porta RAW";
			default:
				return "Porta NAO IDENTIFICADAS";
		}
	}
}
