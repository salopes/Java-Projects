package Finances;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;
import Finances.VendasDiretas;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.Comparable;

public class Cobranca {

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

	ArrayList<VendasDiretas> vendasDiretas = new ArrayList();
	
	public void ProcessaVendasDiretas (VendasDiretas vD){
		vendasDiretas.add(vD);
	}
	
	public int numReistrosVD(){
		return vendasDiretas.size();
	}

}
