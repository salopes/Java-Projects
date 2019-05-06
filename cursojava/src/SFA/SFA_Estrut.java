package SFA;

import java.lang.Comparable;

public class SFA_Estrut implements Comparable <SFA_Estrut>{

	private String linha;
	private String chaveSort;
	
	public SFA_Estrut(String linha, String chaveSort){
		this.linha = linha;
		this.chaveSort = chaveSort; 
	}
	
	public String getLinha(){
		return linha;
	}
	
	public void setLinha(String linha){
		this.linha = linha;
	}

	public String getChaveSort(){
		return chaveSort;
	}
	
	public void setChaveSort(){
		this.chaveSort = chaveSort;
	}
	
	@Override
	public int compareTo(SFA_Estrut order) {
		return chaveSort.compareTo(order.getChaveSort());
	}
	
	@Override
	public String toString(){
		return linha;
	}
	
}

