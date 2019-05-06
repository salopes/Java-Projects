package DDU;

public class ProcessaDP {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaDP(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 30);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(60, 99);
				if (sVendasDiretas.matches("^[0-9]*$")){
					iRetVd=1;
				}
				else{
					sRetVd = "DUPLICATA a SALDO = [" + Linha.substring(60, 99) + "]";
				}
			}
			else{
				sRetVd = "CONCES-CODIGO = [" + Linha.substring(20, 28) + "]";
			}
		}
		else{
			sRetVd = "CONCES-CODIGO + CONCES-REGIAO = [" + Linha.substring(20, 30) + "]";
		}
	}
}
