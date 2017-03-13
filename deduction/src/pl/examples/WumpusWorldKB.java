package pl.examples;

import pl.core.*;


public class WumpusWorldKB extends KB {
	/**
	 * check Wumpus World by enumerating truth table and resolution
	 */
	public WumpusWorldKB() {
		super();
		Symbol p11 = intern("P1,1");
		Symbol p12 = intern("P1,2");
		Symbol p21 = intern("P2,1");
		Symbol p22 = intern("P2,2");
		Symbol p31 = intern("P3,1");
		Symbol b11 = intern("B1,1");
		Symbol b21 = intern("B2,1");

		add(new Negation(p11));
		add(new Biconditional(b11, new Disjunction(p12, p21)));
		add(new Biconditional(b21, new Disjunction(p12, new Disjunction(p22, p31))));
		add(new Negation(b11));
		add(b21);
		
		System.out.println("Enumerating truth table");
		ModelImplementation m = new ModelImplementation(this);
		EnumTB tt = new EnumTB(m);
		System.out.println("Query ~p12 " + tt.TT_CHECK_ALL(this, new Negation(p12), m, 1));
		System.out.println();
		
		System.out.println("Resolution part");
		Resolution resolution = new Resolution(this);
		System.out.println("Query ~p12 " + resolution.check(new Negation(p12)));
		
		//  to make sure the result
//		System.out.println("Query p12 " + resolution.check(p12));
		System.out.println();
	}

	public static void main(String[] argv) {
		new WumpusWorldKB();
	}

}
