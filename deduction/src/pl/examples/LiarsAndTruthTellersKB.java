package pl.examples;

import pl.core.*;


public class LiarsAndTruthTellersKB extends KB{
	public LiarsAndTruthTellersKB() {
		super();
		Symbol a = intern("Amy is a truth-teller");
		Symbol b = intern("Bob is a truth-teller");
		Symbol c = intern("Cal is a truth-teller");
		add(new Implication(a, (new Conjunction(a, c))));
		add(new Implication(b, new Negation(c)));
		add(new Implication(c, new Disjunction(b, new Negation(a))));
		
	
		System.out.println("Enumerating truth table");
		ModelImplementation m = new ModelImplementation(this);
		EnumTB TT = new EnumTB(m);
		
		System.out.println("Query a " + TT.TT_CHECK_ALL(this, a, m, 1));
		System.out.println("Query ~a " + TT.TT_CHECK_ALL(this, new Negation(a), m, 1));
		System.out.println();
		System.out.println("Query b " + TT.TT_CHECK_ALL(this, b, m, 1));
		System.out.println("Query ~b " + TT.TT_CHECK_ALL(this, new Negation(b), m, 1));
		System.out.println();
		System.out.println("Query c " + TT.TT_CHECK_ALL(this, c, m, 1));
		System.out.println("Query ~c " + TT.TT_CHECK_ALL(this, new Negation(c), m, 1));
		System.out.println();
		
		
		System.out.println("Resolution part");
		Resolution resolution = new Resolution(this);
	
		System.out.println("Query a " + resolution.check(a));
		System.out.println("Query ~a " + resolution.check(new Negation(a)));
		System.out.println("Query b " + resolution.check(b));
		System.out.println("Query ~b " + resolution.check(new Negation(b)));
		System.out.println("Query c " + resolution.check(c));
		System.out.println("Query ~c " + resolution.check(new Negation(c)));

	
		System.out.println();
	}
//	public static void main(String[] args)
//	{
//		new LiarsAndTruthTellersKB();
//	}
}