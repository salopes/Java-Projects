package types;

import Calc.Person;

public class WrappersVsPrimitives {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		int x=0;
		Integer y=null;
		
		System.out.println(y);
		Person p = new Person();
		// p.age;  - é public por isso e possivel acessar de outro pacote, Vairiavel de instancia
		System.out.println(p.age);
		
		
	}

}
