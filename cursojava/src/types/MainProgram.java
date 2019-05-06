package types;

public class MainProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//George Boole
		boolean a=false; //recebe apenas true or false - que são literais (constantes)
		                 // atribuidas a variavel
		System.out.println("a:" + a);
		
		char ch='a'; //armazena apenas 01 caracter - ocupa um espaço de 16 bits
		System.out.println("ch:" + ch);
		
		byte n1=1; //espera um byte, ou seja um númermo ou um caracter. n1='r'
		           //armazena a representaçaõ decimal da atribuição
		           // n1='r' (aramazena 114, a representaçao decimal de r
		           // usa 8 bits
		           //-128 a 127 - -2^7 a 2^7 - 1 - pq inclui o zero
		System.out.println("n1:" + n1);
		
		short n2=22; // n2='a', vai mostrar 97
				    // 16 bits - 2^15 a 2^15 -1
		System.out.println("n2:" + n2);
		
		int n4 = 1000; //32 bits -2^31 a 2^31 -1
		System.out.println("n4:" + n4);
		
		long n5 = 9000000L; // 64 bits - 2^63 a 2^63 -1
				           // Sempre colocar o L no final
		
		System.out.println("n5:" + n5);
		
		//floating point
				
		float n6 = 10.5f; //32 bits -2^31 a 2^31 -1
		                  // sempre colocar o f
		System.out.println("n6:" + n6);
		
		double n7=45.8d; //64 bits -2^63 a 2^63 -1
		                 // pode não precisar colcar o d
		System.out.println("n7:" + n7);
		
		
		//Tipos Wrappers - Ele usa uma classe que usa um tipo primitivo
		//Serve para manipular o parâmetro, a partir dos método
		//dá um tratamento ao parâmetro
		//Se for só guardar, use o primitivo
		
		Integer x = new Integer(10000); //O Integer() é denominado um construtor
		                            //O Integer encapsula o Int - O x (objeto Integer) é um wrapper do Int
		                            //o X não guarda o 5, ele guarda o endereço de um objeto
		                            //O tipo INT encapsulado é uma propriedade de instância
		                            //Ou seja, o x guarda 0x1016 ao inves de 5, em 0x1016 tem o valor 5
		
		byte x2 = x.byteValue(); //transforma o INT 5 (32 bits) em um Tipo Byte com 8 bits
		System.out.println("X2:" + x2);
		System.out.println("X:" + x);
		
		//Byte, Short, Integer, Long, Float, Double, Boolean, Character, 
		                         
		
		
		
		
		
		
		

	}

}
