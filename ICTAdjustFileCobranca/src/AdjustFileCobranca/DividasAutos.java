package AdjustFileCobranca;

import java.lang.Comparable;

public class DividasAutos implements Comparable <DividasAutos> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String DUPLICATA;
	String CHASSI;
	String SALDO;
	String EMISSAO;
	String VENCTOO;
	String EXPED;
	String EMPLAC;
	String FILLER;
	String CHAVESORT;

	public DividasAutos() {
		// TODO Auto-generated constructor stub
	}

	public DividasAutos(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String DUPLICATA, String CHASSI, String SALDO, String EMISSAO, String VENCTOO, String EXPED, String EMPLAC, String FILLER, String CHAVESORT) {
		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.SALDO = SALDO;
		this.EMISSAO = EMISSAO;
		this.VENCTOO = VENCTOO;
		this.EXPED = EXPED;
		this.EMPLAC = EMPLAC;
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

	public String getCONCESNOME() {
		return CONCESNOME;
	}

	public void setCONCESNOME(String CONCESNOME) {
		this.CONCESNOME = CONCESNOME;
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

	public String getSALDO() {
		return SALDO;
	}

	public void setSALDO(String SALDO) {
		this.SALDO = SALDO;
	}

	public String getEMISSAO() {
		return EMISSAO;
	}

	public void setEMISSAO(String EMISSAO) {
		this.EMISSAO = EMISSAO;
	}

	public String getVENCTOO() {
		return VENCTOO;
	}

	public void setVENCTOO(String VENCTOO) {
		this.VENCTOO = VENCTOO;
	}

	public String getEXPED() {
		return EXPED;
	}

	public void setEXPED(String EXPED) {
		this.EXPED = EXPED;
	}

	public String getEMPLAC() {
		return EMPLAC;
	}

	public void setEMPLAC(String EMPLAC) {
		this.EMPLAC = EMPLAC;
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
	public int compareTo(DividasAutos order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + DUPLICATA + CHASSI + SALDO + EMISSAO + VENCTOO + EXPED + EMPLAC + FILLER;
	}
}
