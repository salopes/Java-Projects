package ListPRSUP;

import java.lang.Comparable;

public class ListPRSUP_Estrut implements Comparable <ListPRSUP_Estrut> {
	
	String TpRegistro;
	String CodPeca;
	String Reservado1;
	String DescPeca;
	String PesoPeca;
	String AliqIPI;
	String ClassificFiscal;
	String QME;
	String GpoLucratividade;
	String Margem;
	String PrecoPublico;
	String CustoDealer;
	String PrecoGarantiaFIAT;
	String PrecoNF;
	String DtValidadePreco;
	String IdPISCOFINS;
	String IdSubstTributaria;
	String IdOrigemPeca;
	String StatusPeca;
	String UndMedida;
	String Reservado2;
	String TpPeca;
	String IcmsFIASA;
	String IcmsDealer;
	String PisConfinsFIASA;
	String PisConfinsDealer;
	String ImpostImport;
	String Armazem;
	String PrecoGarantiaCJDR;
	String Reservado3;

	public ListPRSUP_Estrut() {
		// TODO Auto-generated constructor stub
	}
	
	public ListPRSUP_Estrut (String TpRegistro, String CodPeca, String Reservado1, String DescPeca, String PesoPeca, String AliqIPI, String ClassificFiscal, String QME, String GpoLucratividade, String Margem, String PrecoPublico, String CustoDealer, String PrecoGarantiaFIAT, String PrecoNF, String DtValidadePreco, String IdPISCOFINS, String IdSubstTributaria, String IdOrigemPeca, String StatusPeca, String UndMedida, String Reservado2, String TpPeca, String IcmsFIASA, String IcmsDealer, String PisConfinsFIASA, String PisConfinsDealer, String ImpostImport, String Armazem, String PrecoGarantiaCJDR, String Reservado3){

		this.TpRegistro        = TpRegistro;
		this.CodPeca           = CodPeca;
		this.Reservado1        = Reservado1;
		this.DescPeca          = DescPeca;
		this.PesoPeca          = PesoPeca;
		this.AliqIPI           = AliqIPI;
		this.ClassificFiscal   = ClassificFiscal;
		this.QME               = QME;
		this.GpoLucratividade  = GpoLucratividade;
		this.Margem            = Margem;
		this.PrecoPublico      = PrecoPublico;
		this.CustoDealer       = CustoDealer;
		this.PrecoGarantiaFIAT = PrecoGarantiaFIAT;
		this.PrecoNF           = PrecoNF;
		this.DtValidadePreco   = DtValidadePreco;
		this.IdPISCOFINS       = IdPISCOFINS;
		this.IdSubstTributaria = IdSubstTributaria;
		this.IdOrigemPeca      = IdOrigemPeca;
		this.StatusPeca        = StatusPeca;
		this.UndMedida         = UndMedida;
		this.Reservado2        = Reservado2;
		this.TpPeca            = TpPeca;
		this.IcmsFIASA         = IcmsFIASA;
		this.IcmsDealer        = IcmsDealer;
		this.PisConfinsFIASA   = PisConfinsFIASA;
		this.PisConfinsDealer  = PisConfinsDealer;
		this.ImpostImport      = ImpostImport;
		this.Armazem           = Armazem;
		this.PrecoGarantiaCJDR = PrecoGarantiaCJDR;
		this.Reservado3        = Reservado3;
		
	}

	public String getTpRegistro() {
		return TpRegistro;
	}
	public String getCodPeca() {
		return CodPeca;
	}
	public String getReservado1() {
		return Reservado1;
	}
	public String getDescPeca() {
		return DescPeca;
	}
	public String getPesoPeca() {
		return PesoPeca;
	}
	public String getAliqIPI() {
		return AliqIPI;
	}
	public String getClassificFiscal() {
		return ClassificFiscal;
	}
	public String getQME() {
		return QME;
	}
	public String getGpoLucratividade() {
		return GpoLucratividade;
	}
	public String getMargem() {
		return Margem;
	}
	public String getPrecoPublico() {
		return PrecoPublico;
	}
	public String getCustoDealer() {
		return CustoDealer;
	}
	public String getPrecoGarantiaFIAT() {
		return PrecoGarantiaFIAT;
	}
	public String getPrecoNF() {
		return PrecoNF;
	}
	public String getDtValidadePreco() {
		return DtValidadePreco;
	}
	public String getIdPISCOFINS() {
		return IdPISCOFINS;
	}
	public String getIdSubstTributaria() {
		return IdSubstTributaria;
	}
	public String getIdOrigemPeca() {
		return IdOrigemPeca;
	}
	public String getStatusPeca() {
		return StatusPeca;
	}
	public String getUndMedida() {
		return UndMedida;
	}
	public String getReservado2() {
		return Reservado2;
	}
	public String getTpPeca() {
		return TpPeca;
	}
	public String getIcmsFIASA() {
		return IcmsFIASA;
	}
	public String getIcmsDealer() {
		return IcmsDealer;
	}
	public String getPisConfinsFIASA() {
		return PisConfinsFIASA;
	}
	public String getPisConfinsDealer() {
		return PisConfinsDealer;
	}
	public String getImpostImport() {
		return ImpostImport;
	}
	public String getArmazem() {
		return Armazem;
	}
	public String getPrecoGarantiaCJDR() {
		return PrecoGarantiaCJDR;
	}
	public String getReservado3() {
		return Reservado3;
	}


	public void setTpRegistro(String TpRegistro) {
		this.TpRegistro = TpRegistro;
	}
	public void setCodPeca(String CodPeca) {
		this.CodPeca = CodPeca;
	}
	public void setReservado1(String Reservado1) {
		this.Reservado1 = Reservado1;
	}
	public void setDescPeca(String DescPeca) {
		this.DescPeca = DescPeca;
	}
	public void setPesoPeca(String PesoPeca) {
		this.PesoPeca = PesoPeca;
	}
	public void setAliqIPI(String AliqIPI) {
		this.AliqIPI = AliqIPI;
	}
	public void setClassificFiscal(String ClassificFiscal) {
		this.ClassificFiscal = ClassificFiscal;
	}
	public void setQME(String QME) {
		this.QME = QME;
	}
	public void setGpoLucratividade(String GpoLucratividade) {
		this.GpoLucratividade = GpoLucratividade;
	}
	public void setMargem(String Margem) {
		this.Margem = Margem;
	}
	public void setPrecoPublico(String PrecoPublico) {
		this.PrecoPublico = PrecoPublico;
	}
	public void setCustoDealer(String CustoDealer) {
		this.CustoDealer = CustoDealer;
	}
	public void setPrecoGarantiaFIAT(String PrecoGarantiaFIAT) {
		this.PrecoGarantiaFIAT = PrecoGarantiaFIAT;
	}
	public void setPrecoNF(String PrecoNF) {
		this.PrecoNF = PrecoNF;
	}
	public void setDtValidadePreco(String DtValidadePreco) {
		this.DtValidadePreco = DtValidadePreco;
	}
	public void setIdPISCOFINS(String IdPISCOFINS) {
		this.IdPISCOFINS = IdPISCOFINS;
	}
	public void setIdSubstTributaria(String IdSubstTributaria) {
		this.IdSubstTributaria = IdSubstTributaria;
	}
	public void setIdOrigemPeca(String IdOrigemPeca) {
		this.IdOrigemPeca = IdOrigemPeca;
	}
	public void setStatusPeca(String StatusPeca) {
		this.StatusPeca = StatusPeca;
	}
	public void setUndMedida(String UndMedida) {
		this.UndMedida = UndMedida;
	}
	public void setReservado2(String Reservado2) {
		this.Reservado2 = Reservado2;
	}
	public void setTpPeca(String TpPeca) {
		this.TpPeca = TpPeca;
	}
	public void setIcmsFIASA(String IcmsFIASA) {
		this.IcmsFIASA = IcmsFIASA;
	}
	public void setIcmsDealer(String IcmsDealer) {
		this.IcmsDealer = IcmsDealer;
	}
	public void setPisConfinsFIASA(String PisConfinsFIASA) {
		this.PisConfinsFIASA = PisConfinsFIASA;
	}
	public void setPisConfinsDealer(String PisConfinsDealer) {
		this.PisConfinsDealer = PisConfinsDealer;
	}
	public void setImpostImport(String ImpostImport) {
		this.ImpostImport = ImpostImport;
	}
	public void setArmazem(String Armazem) {
		this.Armazem = Armazem;
	}
	public void setPrecoGarantiaCJDR(String PrecoGarantiaCJDR) {
		this.PrecoGarantiaCJDR = PrecoGarantiaCJDR;
	}
	public void setReservado3(String Reservado3) {
		this.Reservado3 = Reservado3;
	}
	
	@Override
	public int compareTo(ListPRSUP_Estrut order) {
			return TpRegistro.compareTo(order.getTpRegistro());
	}	

	@Override
	public String toString() {
		return TpRegistro + CodPeca + Reservado1 + DescPeca + PesoPeca + AliqIPI + ClassificFiscal + QME + GpoLucratividade + Margem + PrecoPublico + CustoDealer + PrecoGarantiaFIAT + PrecoNF + DtValidadePreco + IdPISCOFINS + IdSubstTributaria + IdOrigemPeca + StatusPeca + UndMedida + Reservado2 + TpPeca+IcmsFIASA + IcmsDealer + PisConfinsFIASA + PisConfinsDealer + ImpostImport + Armazem + PrecoGarantiaCJDR + Reservado3;
	
	}


}
