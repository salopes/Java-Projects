package Calc;

public class Calculator {
	public int sum(int a, int b){
		int s=0;
		s=a+b;
		return s;
	}
	
	public int subtract(int a, int b){
		int s=0;
		s=a-b;
		return s;
	}

	public double divide(double a, double b){
		double s=0;
		if (b>0) {
			s=a/b;
		}
		else{
			s=0;
		}
		return s;
	}

	public double multiply(double a, double b){
		double s=0;
		s=a*b;
		return s;
	}
	
	public int mod(int a, int b){
		int s=0;
		s = a % b;
		return s;
	}
	
}
