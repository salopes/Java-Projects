package AdjustFileCobranca;

public class ProcessaDA {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaDA(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 30);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(60, 69);
				if (sVendasDiretas.matches("^[0-9]*$")){
					sVendasDiretas = Linha.substring(80, 126);
					if (sVendasDiretas.matches("^[0-9]*$")){
						iRetVd=1;
					}
					else{
						sRetVd = "SALDO a EMPLAC = [" + Linha.substring(80, 126) + "]";
					}
				}
				else{
					sRetVd = "DUPLICATA = [" + Linha.substring(60, 69) + "]";
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
