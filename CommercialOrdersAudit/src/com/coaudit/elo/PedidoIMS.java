package com.coaudit.elo;

import java.lang.Comparable;

public class PedidoIMS implements Comparable <PedidoIMS>{

	String PedidoComercial;
	
	public PedidoIMS() {
		// TODO Auto-generated constructor stub
	}
	
	public PedidoIMS(String PedidoComercial) {
		this.PedidoComercial  = PedidoComercial ;
	}
	
	public String getPedidoComercial() {
		return PedidoComercial;
	}

	public void setPedidoComercial(String PedidoComercial){
	    this.PedidoComercial = PedidoComercial;
	}

	@Override
	public int compareTo(PedidoIMS order) {
			return PedidoComercial.compareTo(order.getPedidoComercial());
	}		
	
	@Override
	public String toString() {
		return PedidoComercial;
	}
}
