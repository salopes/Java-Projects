package DDU;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import Finances.Cobranca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Comparable;

public class ProcessaDDU {

	public static void main(String[] args) {

		int iHeader=0;
		int iVendasDiretas=0;
		int iVendInternet=0;
		int iVendGoverno=0;
		int iVeicPendentes=0;
		int iDividPecas=0;
		int iDividAutos=0;
		int iEntregLiberada=0;
		int iVendLeasing=0;
		int iTrailler=0;
		int iTotRegistros=0;
		int iTotLidos=0;
		
		String sVendasDiretas="";
		String sVendInternet="";
		String sVendGoverno="";
		String sVeicPendentes="";
		String sDividPecas="";
		String sDividAutos="";
		String sEntregLiberada="";
		String sVendLeasing="";

		try{
			FileReader fileName = new FileReader("D:\\users\\f18867b\\Documents\\TODO List\\SAP AR FULL\\War\\Novo 2\\IF_BR_01_315_20160616-184742-741.TXT");
			BufferedReader lerArq = new BufferedReader(fileName);
			String linha = lerArq.readLine(); // lê a primeira linha
		
			
			while (linha != null) {
				sVendasDiretas="";
				sVendInternet="";
				sVendGoverno="";
				sVeicPendentes="";
				sDividPecas="";
				sDividAutos="";
				sEntregLiberada="";
				sVendLeasing="";

				//System.out.printf("%s\n", linha);
				
				String tpReg = linha.substring(0, 19);
				//System.out.printf("%s\n", linha);
				
				String tipoRegistro = tpReg.trim();
				
				if (tipoRegistro.equals("HEADER")){
					iHeader++;
				}
				else if (tipoRegistro.equals("VENDAS DIRETAS")){
					iTotRegistros++;
					ProcessaVendasDiretas();
				}
				else if (tipoRegistro.equals("VENDAS INTERNET")){
					iVendInternet++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("VENDAS GOVERNO")){
					iVendGoverno++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("VEICULOS PENDENTES")){
					iVeicPendentes++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("DIVIDAS DE PECAS")){
					iDividPecas++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("DIVIDAS DE AUTOS")){
					iDividAutos++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("ENTREGA LIBERADA")){
					iEntregLiberada++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("VENDAS LEASING")){
					iVendLeasing++;
					iTotRegistros++;
				}
				else if (tipoRegistro.equals("TRAILLER")){
					iTrailler++;
				}
				
				linha = lerArq.readLine(); // lê da segunda até a última linha
			} 
			
			iTotLidos = (iHeader + iTrailler + iTotRegistros);
			
			System.out.printf("Total de Registros %-20s: [%04d]\n", "HEADER", iHeader);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "VENDAS DIRETAS", vendasDiretas.size());
			System.out.printf("Total de Registros %-20s: [%04d]\n", "VENDAS INTERNET", iVendInternet);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "VENDAS GOVERNO", iVendGoverno);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "VEICULOS PENDENTES", iVeicPendentes);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "DIVIDAS DE PECAS", iDividPecas);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "DIVIDAS DE AUTOS", iDividAutos);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "ENTREGA LIBERADA", iEntregLiberada);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "VENDAS LEASING", iVendLeasing);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "TRAILLER", iTrailler);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "REGISTROS LOGICOS", iTotRegistros);
			System.out.printf("Total de Registros %-20s: [%04d]\n", "REGISTROS FISICOS", iTotLidos);

			Collections.sort(vendasDiretas);

			for (VendasDiretas VD : vendasDiretas){
				System.out.println(VD);
			}
			
			fileName.close();
		} catch (IOException  e) {
			e.getMessage();
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage());
		}
	}

}
