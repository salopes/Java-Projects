package DDU;

import java.lang.Comparable;

public class VendasInternet implements Comparable <VendasInternet> {

	String IDENTIFICADOR;
	String CONCESCODIGO;
	String CONCESREGIAO;
	String CONCESNOME;
	String CLIENTEREGIAO;
	String CLIENTECODIGO;
	String CLIENTENOME;
	String RAC;
	String DUPLICATA;
	String CHASSI;
	String SALDO;
	String EMISSAO;
	String EXPED;
	String VENCTO;
	String EMPLAC;
	String INSPECAOSTATUS;
	String INSPECAODATA;
	String AGENTEFCODIGO;
	String AGENTEFNOME;
	String OBS;
	String SALDOCC;
	String IDINEXCVENDA;
	String FILLER;
	String CHAVESORT;

	public VendasInternet() {
		// TODO Auto-generated constructor stub
	}

	public VendasInternet(String IDENTIFICADOR, String CONCESCODIGO, String CONCESREGIAO, String CONCESNOME, String CLIENTEREGIAO, String CLIENTECODIGO, String CLIENTENOME, String RAC, String DUPLICATA, String CHASSI, String SALDO, String EMISSAO, String EXPED, String VENCTO, String EMPLAC, String INSPECAOSTATUS, String INSPECAODATA, String AGENTEFCODIGO, String AGENTEFNOME, String OBS, String SALDOCC, String IDINEXCVENDA, String FILLER, String CHAVESORT) {
		this.IDENTIFICADOR = CONCESCODIGO;
		this.CONCESCODIGO = CONCESCODIGO;
		this.CONCESREGIAO = CONCESREGIAO;
		this.CONCESNOME = CONCESNOME;
		this.CLIENTEREGIAO = CLIENTEREGIAO;
		this.CLIENTECODIGO = CLIENTECODIGO;
		this.CLIENTENOME = CLIENTENOME;
		this.RAC = RAC;
		this.DUPLICATA = DUPLICATA;
		this.CHASSI = CHASSI;
		this.SALDO = SALDO;
		this.EMISSAO = EMISSAO;
		this.EXPED = EXPED;
		this.VENCTO = VENCTO;
		this.EMPLAC = EMPLAC;
		this.INSPECAOSTATUS = INSPECAOSTATUS;
		this.INSPECAODATA = INSPECAODATA;
		this.AGENTEFCODIGO = AGENTEFCODIGO;
		this.AGENTEFNOME = AGENTEFNOME;
		this.OBS = OBS;
		this.SALDOCC = SALDOCC;
		this.IDINEXCVENDA = IDINEXCVENDA;
		this.FILLER = FILLER;
		this.CHAVESORT = CHAVESORT;
	}

	public String getIDENTIFICADOR() {
		return IDENTIFICADOR;
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

	public String getRAC() {
		return RAC;
	}

	public void setRAC(String RAC) {
		this.RAC = RAC;
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

	public String getINSPECAOSTATUS() {
		return INSPECAOSTATUS;
	}

	public void setINSPECAOSTATUS(String INSPECAOSTATUS) {
		this.INSPECAOSTATUS = INSPECAOSTATUS;
	}

	public String getINSPECAODATA() {
		return INSPECAODATA;
	}

	public void setINSPECAODATA(String INSPECAODATA) {
		this.INSPECAODATA = INSPECAODATA;
	}

	public String getAGENTEFCODIGO() {
		return AGENTEFCODIGO;
	}

	public void setAGENTEFCODIGO(String AGENTEFCODIGO) {
		this.AGENTEFCODIGO = AGENTEFCODIGO;
	}

	public String getAGENTEFNOME() {
		return AGENTEFNOME;
	}

	public void setAGENTEFNOME(String AGENTEFNOME) {
		this.AGENTEFNOME = AGENTEFNOME;
	}

	public String getOBS() {
		return OBS;
	}

	public void setOBS(String OBS) {
		this.OBS = OBS;
	}

	public String getSALDOCC() {
		return SALDOCC;
	}

	public void setSALDOCC(String SALDOCC) {
		this.SALDOCC = SALDOCC;
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
	public int compareTo(VendasInternet order) {
			return CHAVESORT.compareTo(order.getCHAVESORT());
	}	
	
	@Override
	public String toString() {
		return IDENTIFICADOR + CONCESCODIGO + CONCESREGIAO + CONCESNOME + CLIENTEREGIAO + CLIENTECODIGO + CLIENTENOME + RAC + DUPLICATA + CHASSI + SALDO + EMISSAO + EXPED + VENCTO + EMPLAC + INSPECAOSTATUS + INSPECAODATA + AGENTEFCODIGO + AGENTEFNOME + OBS + SALDOCC + IDINEXCVENDA + FILLER ;
	}
	
}
