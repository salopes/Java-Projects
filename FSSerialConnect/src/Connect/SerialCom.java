package Connect;

import gnu.io.CommPortIdentifier;
import java.util.Enumeration;

public class SerialCom {

	protected String[] portas;
	protected static Enumeration listaDePortas;

	public String[] ObterPortas(){
        return portas;
	}

	protected void ListarPortas(){
        int i = 0;
        portas = new String[10];
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips =
            (CommPortIdentifier)listaDePortas.nextElement();
            portas[i] = ips.getName();
            i++;
        }
	}
	
	public boolean PortaExiste(String COMp){
        String temp;
        boolean e = false;
        while (listaDePortas.hasMoreElements()) {
            CommPortIdentifier ips = (CommPortIdentifier)listaDePortas.nextElement();
            temp = ips.getName();
            if (temp.equals(COMp)== true) {
            e = true;
            }
        }
        return e;
	}
	
	public static void main(String[] args) {
		listaDePortas = CommPortIdentifier.getPortIdentifiers();
	}
}
