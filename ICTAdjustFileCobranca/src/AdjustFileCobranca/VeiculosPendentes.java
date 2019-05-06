package AdjustFileCobranca;

import java.lang.Comparable;

public class VeiculosPendentes implements Comparable <VeiculosPendentes> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String CLIENTEREGIAO;
	String CLIENTECODIGO;
	String CLIENTENOME;
	String DUPLICATA;
	String CHASSI;
	String SALDO;
	String DATACANC;
	String FILLER;
	String CHAVESORT;

	public VeiculosPendentes() {
		// TODO Auto-generated constructor stub
	}

	public VeiculosPendentes(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String CLIENTEREGIAO, String CLIENTECODIGO, String CLIENTENOME, String DUPLICATA, String CHASSI, String SALDO, String DATACANC, String FILLER, String CHAVESORT) {

		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.CLIENTEREGIAO = CLIENTEREGIAO;
		this.CLIENTECODIGO = CLIENTECODIGO;
		this.CLIENTENOME = CLIENTENOME;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.SALDO = SALDO;
		this.DATACANC = DATACANC;
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

	public String getCLIENTEREGIAO() {
		return CLIENTEREGIAO;
	}

	public void setCLIENTEREGIAO(String CLIENTEREGIAO) {
		this.CLIENTEREGIAO = CLIENTEREGIAO;
	}

	public String getCLIENTECODIGO() {
		return CLIENTECODIGO;
	}

	public void setCLIENTECODIGO(String CLIENTECODIGO) {
		this.CLIENTECODIGO = CLIENTECODIGO;
	}

	public String getCLIENTENOME() {
		return CLIENTENOME;
	}

	public void setCLIENTENOME(String CLIENTENOME) {
		this.CLIENTENOME = CLIENTENOME;
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

	public String getDATACANC() {
		return DATACANC;
	}

	public void setDATACANC(String DATACANC) {
		this.DATACANC = DATACANC;
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
	public int compareTo(VeiculosPendentes order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + CLIENTEREGIAO + CLIENTECODIGO + CLIENTENOME + DUPLICATA + CHASSI + SALDO + DATACANC + FILLER;
	}
}
