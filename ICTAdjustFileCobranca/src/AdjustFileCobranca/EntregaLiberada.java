package AdjustFileCobranca;

import java.lang.Comparable;

public class EntregaLiberada implements Comparable <EntregaLiberada> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String CLIENTECODIGO;
	String CLIENTEREGIAO;
	String CLIENTENOME;
	String DUPLICATA;
	String CHASSI;
	String VALORDUPLIC;
	String EMISSAO;
	String EXPED;
	String VENCTO;
	String FILLER;
	String CHAVESORT;

	public EntregaLiberada() {
		// TODO Auto-generated constructor stub
	}

	
	
	public EntregaLiberada(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String CLIENTECODIGO, String CLIENTEREGIAO, String CLIENTENOME, String DUPLICATA, String CHASSI, String VALORDUPLIC, String EMISSAO, String EXPED, String VENCTO, String FILLER, String CHAVESORT) {
		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.CLIENTECODIGO = CLIENTECODIGO;
		this.CLIENTEREGIAO = CLIENTEREGIAO;
		this.CLIENTENOME = CLIENTENOME;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.VALORDUPLIC = VALORDUPLIC;
		this.EMISSAO = EMISSAO;
		this.EXPED = EXPED;
		this.VENCTO = VENCTO;
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


	public String getCLIENTECODIGO() {
		return CLIENTECODIGO;
	}


	public void setCLIENTECODIGO(String CLIENTECODIGO) {
		this.CLIENTECODIGO = CLIENTECODIGO;
	}


	public String getCLIENTEREGIAO() {
		return CLIENTEREGIAO;
	}


	public void setCLIENTEREGIAO(String CLIENTEREGIAO) {
		this.CLIENTEREGIAO = CLIENTEREGIAO;
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

	public String getVALORDUPLIC() {
		return VALORDUPLIC;
	}


	public void setVALORDUPLIC(String VALORDUPLIC) {
		this.VALORDUPLIC = VALORDUPLIC;
	}


	public String getEMISSAO() {
		return EMISSAO;
	}


	public void setEMISSAO(String EMISSAO) {
		this.EMISSAO = EMISSAO;
	}


	public String getEXPED() {
		return EXPED;
	}


	public void setEXPED(String EXPED) {
		this.EXPED = EXPED;
	}


	public String getVENCTO() {
		return VENCTO;
	}


	public void setVENCTO(String VENCTO) {
		this.VENCTO = VENCTO;
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
	public int compareTo(EntregaLiberada order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + CLIENTECODIGO + CLIENTEREGIAO + CLIENTENOME + DUPLICATA + CHASSI + VALORDUPLIC + EMISSAO + EXPED + VENCTO + FILLER;
	}
}
