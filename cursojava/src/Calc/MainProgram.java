package Calc;

public class MainProgram {

	public static void main(String[] args) {
		int ires=0;
		double dres=0;
		int dresto=0;
		//System.out.print("My first code!");
		
		// Tipo + Variável de Referência + Atribuição + Construção de Objeto
				// Calculator c -> Aqui se declara uma variavel de referecnia
		Calculator c = new Calculator(); // new Calculator()-> Instanciando o objeto Calculator ou atribuindo - o igual, siginfica recebe
		                                 // Guarda o endereço do objeto Calculator
		ires = c.sum(10,20);			 // Chamando o metodo, o ponto permite acessar a funcionalidade
		System.out.println("Adding numbers"+ ires);

		ires = 0;
		
		ires = c.subtract(10, 20);					 // Chamando o metodo
		System.out.println("Subtract numbers"+ ires);
		
		dres = c.divide(40, 8);
		System.out.println("Divide numbers"+ dres);
		
		dres = 0;
		dres = c.multiply(40, 8);
		System.out.println("Multiplic numbers"+ dres);
		
		dresto = c.mod(40, 9);
		System.out.println("Remainder numbers"+ dresto);
		
		
	}

}
