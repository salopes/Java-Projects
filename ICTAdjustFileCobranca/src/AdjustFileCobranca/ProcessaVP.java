package AdjustFileCobranca;

public class ProcessaVP {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaVP(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 30);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(60, 70);
				if (sVendasDiretas.matches("^[0-9]*$")){
					sVendasDiretas = Linha.substring(100, 109);
					if (sVendasDiretas.matches("^[0-9]*$")){
						Integer iVendasPendentes = Integer.parseInt(Linha.substring(62, 70));
						if(iVendasPendentes!=0){
							iRetVd=1;
						}
						else{
							sRetVd = "CLIENTE-CODIGO = [" + Linha.substring(62, 70) + "]";
						}
					}
					else{
						sRetVd = "DUPLICATA = [" + Linha.substring(100, 109) + "]";
					}
				}
				else{
					sRetVd = "CLIENTE-REGIAO + CLIENTE-CODIGO = [" + Linha.substring(60, 70) + "]";
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
