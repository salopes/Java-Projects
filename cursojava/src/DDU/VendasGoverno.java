package DDU;

import java.lang.Comparable;

public class VendasGoverno implements Comparable <VendasGoverno> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String CLIENTEREGIAO;
	String CLIENTECODIGO;
	String CLIENTENOME;
	String CATEGORIA;
	String DUPLICATA;
	String CHASSI;
	String SALDO;
	String EMISSAO;
	String EXPED;
	String VENCTO;
	String EMPLAC;
	String OBS;
	String ENTREGACODIGO;
	String ENTREGAREGIAO;
	String ENTREGANOME;
	String IDINEXCVENDA;
	String FILLER;
	String CHAVESORT;
	
	public VendasGoverno() {
		// TODO Auto-generated constructor stub
	}

	
	public VendasGoverno(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String CLIENTEREGIAO, String CLIENTECODIGO, String CLIENTENOME, String CATEGORIA, String DUPLICATA, String CHASSI, String SALDO, String EMISSAO, String EXPED, String VENCTO, String EMPLAC, String OBS, String ENTREGACODIGO, String ENTREGAREGIAO, String ENTREGANOME, String IDINEXCVENDA, String FILLER, String CHAVESORT) {

		this.IDENTIFICADOR = IDENTIFICADOR;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.CLIENTEREGIAO = CLIENTEREGIAO;
		this.CLIENTECODIGO = CLIENTECODIGO;
		this.CLIENTENOME = CLIENTENOME;
		this.CATEGORIA = CATEGORIA;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.SALDO = SALDO;
		this.EMISSAO = EMISSAO;
		this.EXPED = EXPED;
		this.VENCTO = VENCTO;
		this.EMPLAC = EMPLAC;
		this.OBS = OBS;
		this.ENTREGACODIGO = ENTREGACODIGO;
		this.ENTREGAREGIAO = ENTREGAREGIAO;
		this.ENTREGANOME = ENTREGANOME;
		this.IDINEXCVENDA = IDINEXCVENDA;
		this.FILLER = FILLER;
		this.CHAVESORT = CHAVESORT;
	}


	public String getIDENTIFICADOR() {
		return this.IDENTIFICADOR;
	}


	public void setIDENTIFICADOR(String iDENTIFICADOR) {
		this.IDENTIFICADOR = iDENTIFICADOR;
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


	public String getCATEGORIA() {
		return CATEGORIA;
	}


	public void setCATEGORIA(String CATEGORIA) {
		this.CATEGORIA = CATEGORIA;
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


	public String getEMPLAC() {
		return EMPLAC;
	}


	public void setEMPLAC(String EMPLAC) {
		this.EMPLAC = EMPLAC;
	}


	public String getOBS() {
		return OBS;
	}


	public void setOBS(String OBS) {
		this.OBS = OBS;
	}


	public String getENTREGACODIGO() {
		return ENTREGACODIGO;
	}


	public void setENTREGACODIGO(String ENTREGACODIGO) {
		this.ENTREGACODIGO = ENTREGACODIGO;
	}


	public String getENTREGAREGIAO() {
		return ENTREGAREGIAO;
	}


	public void setENTREGAREGIAO(String ENTREGAREGIAO) {
		this.ENTREGAREGIAO = ENTREGAREGIAO;
	}


	public String getENTREGANOME() {
		return ENTREGANOME;
	}


	public void setENTREGANOME(String ENTREGANOME) {
		this.ENTREGANOME = ENTREGANOME;
	}


	public String getIDINEXCVENDA() {
		return IDINEXCVENDA;
	}


	public void setIDINEXCVENDA(String IDINEXCVENDA) {
		this.IDINEXCVENDA = IDINEXCVENDA;
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
	public int compareTo(VendasGoverno order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + CLIENTEREGIAO + CLIENTECODIGO + CLIENTENOME + CATEGORIA + DUPLICATA + CHASSI + SALDO + EMISSAO + EXPED + VENCTO + EMPLAC + OBS + ENTREGACODIGO + ENTREGAREGIAO + ENTREGANOME + IDINEXCVENDA + FILLER;
	}
}
