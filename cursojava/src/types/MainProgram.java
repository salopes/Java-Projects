package types;

public class MainProgram {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//George Boole
		boolean a=false; //recebe apenas true or false - que s�o literais (constantes)
		                 // atribuidas a variavel
		System.out.println("a:" + a);
		
		char ch='a'; //armazena apenas 01 caracter - ocupa um espa�o de 16 bits
		System.out.println("ch:" + ch);
		
		byte n1=1; //espera um byte, ou seja um n�mermo ou um caracter. n1='r'
		           //armazena a representa�a� decimal da atribui��o
		           // n1='r' (aramazena 114, a representa�ao decimal de r
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
		                 // pode n�o precisar colcar o d
		System.out.println("n7:" + n7);
		
		
		//Tipos Wrappers - Ele usa uma classe que usa um tipo primitivo
		//Serve para manipular o par�metro, a partir dos m�todo
		//d� um tratamento ao par�metro
		//Se for s� guardar, use o primitivo
		
		Integer x = new Integer(10000); //O Integer() � denominado um construtor
		                            //O Integer encapsula o Int - O x (objeto Integer) � um wrapper do Int
		                            //o X n�o guarda o 5, ele guarda o endere�o de um objeto
		                            //O tipo INT encapsulado � uma propriedade de inst�ncia
		                            //Ou seja, o x guarda 0x1016 ao inves de 5, em 0x1016 tem o valor 5
		
		byte x2 = x.byteValue(); //transforma o INT 5 (32 bits) em um Tipo Byte com 8 bits
		System.out.println("X2:" + x2);
		System.out.println("X:" + x);
		
		//Byte, Short, Integer, Long, Float, Double, Boolean, Character, 
		                         
		
		
		
		
		
		
		

	}

}
