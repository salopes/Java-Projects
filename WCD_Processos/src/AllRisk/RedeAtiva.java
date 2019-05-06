package AllRisk;

public class RedeAtiva {

		String CODDEALER;
		String COD;
		
		public RedeAtiva() {
			// TODO Auto-generated constructor stub
		}
		
		public RedeAtiva(String CODDEALER, String COD) {
			this.CODDEALER  = CODDEALER ;
			this.COD  = COD ;
		}
		
		public String getCODDEALER() {
			return CODDEALER;
		}


		public void setCODDEALER(String CODDEALER){
		    this.CODDEALER = CODDEALER;
		}

		public String getCOD() {
			return COD;
		}


		public void setCOD(String COD){
		    this.COD = COD;
		}
		
	
		@Override
		public String toString() {
			return CODDEALER + COD;
		}
	}
