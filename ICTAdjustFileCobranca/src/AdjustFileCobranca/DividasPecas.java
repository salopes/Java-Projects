package AdjustFileCobranca;

import java.lang.Comparable;

public class DividasPecas implements Comparable <DividasPecas> {
	
	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String DUPLICATA;
	String EMISSAO;
	String VENCTOO;
	String SALDOAO;
	String TIPO;
	String COBBANCARIA;
	String FILLER;
	String CHAVESORT;

	public DividasPecas() {
		// TODO Auto-generated constructor stub
	}

	public DividasPecas(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String DUPLICATA, String EMISSAO, String VENCTOO, String SALDOAO, String TIPO, String COBBANCARIA, String FILLER, String CHAVESORT) {
		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.DUPLICATA = DUPLICATA;
		this.EMISSAO = EMISSAO;
		this.VENCTOO = VENCTOO;
		this.SALDOAO = SALDOAO;
		this.TIPO = TIPO;
		this.COBBANCARIA = COBBANCARIA;
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

	public String getSALDOAO() {
		return SALDOAO;
	}

	public void setSALDOAO(String SALDOAO) {
		this.SALDOAO = SALDOAO;
	}

	public String getTIPO() {
		return TIPO;
	}

	public void setTIPO(String TIPO) {
		this.TIPO = TIPO;
	}

	public String getCOBBANCARIA() {
		return COBBANCARIA;
	}

	public void setCOBBANCARIA(String COBBANCARIA) {
		this.COBBANCARIA = COBBANCARIA;
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
	public int compareTo(DividasPecas order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + DUPLICATA + EMISSAO + VENCTOO + SALDOAO + TIPO + COBBANCARIA + FILLER;
	}
}
