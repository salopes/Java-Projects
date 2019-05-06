package Connect;

import Connect.SerialComLeitura;

public class FSBalancaSerial {

	public static String obterIniciaSerial(){

		//Iniciando leitura serial
        SerialComLeitura leitura = new SerialComLeitura("COM8", 9600, 0);

        leitura.HabilitarLeitura();
        //leitura.HabilitarEscrita();
        leitura.ObterIdDaPorta();
        leitura.AbrirPorta();
        leitura.LerDados();

        return leitura.getPeso();
    }
	
	public static void main(String[] args) {
       		
		String flagOff = "true";
		String sResultado = "";
    
		sResultado = obterIniciaSerial();
		System.out.println("Devolvendo: " + sResultado);

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Erro na Thread: " + ex);
        }
                
        //if (flagOff == "false") {
        //	leitura.FecharCom();
        //}
	}
}
