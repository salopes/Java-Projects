package com.coaudit.elo;

import java.lang.Comparable;

public class PedidosLink implements Comparable <PedidosLink>{

		String PedidoComercial;
		
		public PedidosLink() {
			// TODO Auto-generated constructor stub
		}
		
		public PedidosLink(String PedidoComercial) {
			this.PedidoComercial  = PedidoComercial ;
		}
		
		public String getPedidoComercial() {
			return PedidoComercial;
		}

		public void setPedidoComercial(String PedidoComercial){
		    this.PedidoComercial = PedidoComercial;
		}

		@Override
		public int compareTo(PedidosLink order) {
				return PedidoComercial.compareTo(order.getPedidoComercial());
		}	
		
		@Override
		public String toString() {
			return PedidoComercial;
		}
	}
