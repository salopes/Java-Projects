package DDU;


public class ProcessaVI {
	String sVendasDiretas="", sRetVd="";
	public int iRetVd=0;

	public ProcessaVI(String Linha) {
		//Verifica se os registro tupo Numerico sao numericos
		sVendasDiretas = Linha.substring(20, 30);
		if (sVendasDiretas.matches("^[0-9]*$")){
			Integer iCodDealer = Integer.parseInt(Linha.substring(20, 28));
			if(iCodDealer!=0){
				sVendasDiretas = Linha.substring(60, 70);
				if (sVendasDiretas.matches("^[0-9]*$")){
					sVendasDiretas = Linha.substring(100, 118);
					if (sVendasDiretas.matches("^[0-9]*$")){
						sVendasDiretas = Linha.substring(129, 175);
						if (sVendasDiretas.matches("^[0-9]*$")){
							sVendasDiretas = Linha.substring(180, 196);
							if (sVendasDiretas.matches("^[0-9]*$")){
								iRetVd=1;
							}
							else{
								sRetVd = "INSPECAO-DATA + AGENTE-F-CODIGO = [" + Linha.substring(180, 196) + "]";
							}
						}
						else{
							sRetVd = "SALDO a EMPLAC = [" + Linha.substring(129, 175) + "]";
						}
					}
					else{
						sRetVd = "RAC + DUPLICATA = [" + Linha.substring(100, 118) + "]";
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
