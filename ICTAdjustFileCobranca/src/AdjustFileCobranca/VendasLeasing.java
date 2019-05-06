package AdjustFileCobranca;

import java.lang.Comparable;

public class VendasLeasing implements Comparable <VendasLeasing> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String DUPLICATA;
	String CHASSI;
	String VENCTO;
	String SALDO;
	String FILLER;
	String CHAVESORT;
	
	public VendasLeasing() {
		// TODO Auto-generated constructor stub
	}
	
	
	public VendasLeasing(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String DUPLICATA, String CHASSI, String VENCTO, String SALDO, String FILLER, String CHAVESORT) {
		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.VENCTO = VENCTO;
		this.SALDO = SALDO;
		this.FILLER = FILLER;
		this.CHAVESORT = CHAVESORT;
	}


	
	public String getIDENTIFICADOR() {
		return IDENTIFICADOR;
	}


	public void setIDENTIFICADOR(String IDENTIFICADOR) {
		this.IDENTIFICADOR = IDENTIFICADOR;
	}


	public String getCONCESCODIGO() {
		return CONCESCODIGO;
	}


	public void setCONCESCODIGO(String CONCESCODIGO) {
		this.CONCESCODIGO = CONCESCODIGO;
	}


	public String getCONCESREGIAO() {
		return CONCESREGIAO;
	}


	public void setCONCESREGIAO(String CONCESREGIAO) {
		this.CONCESREGIAO = CONCESREGIAO;
	}


	public String getDUPLICATA() {
		return DUPLICATA;
	}


	public void setDUPLICATA(String DUPLICATA) {
		this.DUPLICATA = DUPLICATA;
	}


	public String getCHASSI() {
		return CHASSI;
	}


	public void setCHASSI(String CHASSI) {
		this.CHASSI = CHASSI;
	}


	public String getVENCTO() {
		return VENCTO;
	}


	public void setVENCTO(String VENCTO) {
		this.VENCTO = VENCTO;
	}


	public String getSALDO() {
		return SALDO;
	}


	public void setSALDO(String SALDO) {
		this.SALDO = SALDO;
	}


	public String getFILLER() {
		return FILLER;
	}


	public void setFILLER(String FILLER) {
		this.FILLER = FILLER;
	}


	public String getCHAVESORT() {
	    return CHAVESORT;
	}

	public void setCHAVESORT(String CHAVESORT) {
	    this.CHAVESORT = CHAVESORT;
	}

	@Override
	public int compareTo(VendasLeasing order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + DUPLICATA + CHASSI + VENCTO + SALDO + FILLER;
	}
}
