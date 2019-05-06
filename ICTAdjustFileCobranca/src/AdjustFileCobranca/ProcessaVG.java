package AdjustFileCobranca;

public class ProcessaVG {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaVG(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 30);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(60, 70);
				if (sVendasDiretas.matches("^[0-9]*$")){
					sVendasDiretas = Linha.substring(100, 111);
					if (sVendasDiretas.matches("^[0-9]*$")){
						sVendasDiretas = Linha.substring(122, 168);
						if (sVendasDiretas.matches("^[0-9]*$")){
							sVendasDiretas = Linha.substring(183, 193);
							if (sVendasDiretas.matches("^[0-9]*$")){
								Integer iVendasGoverno = Integer.parseInt(Linha.substring(62, 70));
								if(iVendasGoverno!=0){
									iRetVd=1;
								}
								else{
									sRetVd = "CLIENTE-CODIGO = [" + Linha.substring(62, 70) + "]";
								}
							}
							else{
								sRetVd = "ENTREGA-CODIGO + ENTREGA-REGIAO = [" + Linha.substring(122, 168) + "]";
							}
						}
						else{
							sRetVd = "SALDO a EMPLAC = [" + Linha.substring(122, 168) + "]";
						}
					}
					else{
						sRetVd = "CATEGORIA + DUPLICATA = [" + Linha.substring(100, 111) + "]";
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
