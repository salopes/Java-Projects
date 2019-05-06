package Connect;

import Connect.SerialCom;
import Connect.SerialComLeitura;

public class Main extends SerialCom {

	public static void Main(String[] args){     
        //Iniciando leitura serial
        SerialComLeitura leitura = new SerialComLeitura("COM1", 9600, 0);

        leitura.HabilitarLeitura();
        leitura.ObterIdDaPorta();
        leitura.AbrirPorta();
        leitura.LerDados();

        //Controle de tempo da leitura aberta na serial

        try {
            Thread.sleep(1000);
        } catch (InterruptedException ex) {
            System.out.println("Erro na Thread: " + ex);
        }
        leitura.FecharCom();
    }
}
