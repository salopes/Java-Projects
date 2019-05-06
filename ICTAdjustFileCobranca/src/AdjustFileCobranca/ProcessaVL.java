package AdjustFileCobranca;

public class ProcessaVL {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaVL(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 39);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(50, 72);
				if (sVendasDiretas.matches("^[0-9]*$")){
					iRetVd=1;
				}
				else{
					sRetVd = "VENCTO + SALDO = [" + Linha.substring(50, 72) + "]";
				}
			}
			else{
				sRetVd = "CONCES-CODIGO = [" + Linha.substring(20, 28) + "]";
			}
		}
		else{
			sRetVd = "CONCES-CODIGO + CONCES-REGIAO + DUPLICATA = [" + Linha.substring(20, 39) + "]";
		}
	}
}
