package pl.examples;

import pl.cnf.CNFConverter;
import pl.cnf.Clause;
import pl.cnf.Literal;
import pl.core.*;

public class ModusPonensKB extends KB {
	/**
	 * check Modus Ponens by enumerating truth table and resolution
	 */
	public ModusPonensKB() {
		super();
		Symbol p = intern("P");
		Symbol q = intern("Q");
		add(p);
		add(new Implication(p, q));

		System.out.println("Enumerating truth table");
		ModelImplementation a = new ModelImplementation(this);


		EnumTB TT = new EnumTB(a);
		System.out.println("Query q " + TT.TT_CHECK_ALL(this, q, a, 1));
		System.out.println();
		
		System.out.println("Resolution part");
		Resolution resolution = new Resolution(this);

		System.out.println("Query q " + resolution.check(q));
		
		//  to make sure resolution works
//		System.out.println("Query ~q " + resolution.check(new Negation(q)));

		System.out.println();
	}
	
	public static void main(String[] argv) {
		new ModusPonensKB();
		
	}

}
