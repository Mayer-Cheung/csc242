package pl.examples;

import pl.core.*;

public class MLATTKB extends KB {

	public MLATTKB() {
		super();
		Symbol a = intern("Amy is a truth-teller");
		Symbol b = intern("Bob is a truth-teller");
		Symbol c = intern("Cal is a truth-teller");
		Symbol d = intern("Dee is a truth-teller");
		Symbol e = intern("Eli is a truth-teller");
		Symbol f = intern("Fay is a truth-teller");
		Symbol g = intern("Gil is a truth-teller");
		Symbol h = intern("Hal is a truth-teller");
		Symbol i = intern("Ida is a truth-teller");
		Symbol j = intern("Jay is a truth-teller");
		Symbol k = intern("Kay is a truth-teller");
		Symbol l = intern("Lee is a truth-teller");
		
		add(new Implication(a, new Conjunction(h, i)));
		add(new Implication(b, new Conjunction(a, l)));
		add(new Implication(c, new Conjunction(b, g)));
		add(new Implication(d, new Conjunction(e, l)));
		add(new Implication(e, new Conjunction(c, h)));
		add(new Implication(f, new Conjunction(d, i)));
		add(new Implication(g, new Conjunction(new Negation(e), new Negation(j))));
		add(new Implication(h, new Conjunction(new Negation(f), new Negation(k))));
		add(new Implication(i, new Conjunction(new Negation(g), new Negation(k))));
		add(new Implication(j, new Conjunction(new Negation(a), new Negation(c))));
		add(new Implication(k, new Conjunction(new Negation(d), new Negation(f))));
		add(new Implication(l, new Conjunction(new Negation(b), new Negation(j))));
		
	
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
		System.out.println("Query d " + TT.TT_CHECK_ALL(this, d, m, 1));
		System.out.println("Query ~d " + TT.TT_CHECK_ALL(this, new Negation(d), m, 1));
		System.out.println();
		System.out.println("Query e " + TT.TT_CHECK_ALL(this, e, m, 1));
		System.out.println("Query ~e " + TT.TT_CHECK_ALL(this, new Negation(e), m, 1));
		System.out.println();
		System.out.println("Query f " + TT.TT_CHECK_ALL(this, f, m, 1));
		System.out.println("Query ~f " + TT.TT_CHECK_ALL(this, new Negation(f), m, 1));
		System.out.println();
		System.out.println("Query g " + TT.TT_CHECK_ALL(this, g, m, 1));
		System.out.println("Query ~g " + TT.TT_CHECK_ALL(this, new Negation(g), m, 1));
		System.out.println();
		System.out.println("Query h " + TT.TT_CHECK_ALL(this, h, m, 1));
		System.out.println("Query ~h " + TT.TT_CHECK_ALL(this, new Negation(h), m, 1));
		System.out.println();
		System.out.println("Query i " + TT.TT_CHECK_ALL(this, i, m, 1));
		System.out.println("Query ~i " + TT.TT_CHECK_ALL(this, new Negation(i), m, 1));
		System.out.println();
		System.out.println("Query j " + TT.TT_CHECK_ALL(this, j, m, 1));
		System.out.println("Query ~j " + TT.TT_CHECK_ALL(this, new Negation(j), m, 1));
		System.out.println();
		System.out.println("Query k " + TT.TT_CHECK_ALL(this, k, m, 1));
		System.out.println("Query ~k " + TT.TT_CHECK_ALL(this, new Negation(k), m, 1));
		System.out.println();
		System.out.println("Query l " + TT.TT_CHECK_ALL(this, l, m, 1));
		System.out.println("Query ~l " + TT.TT_CHECK_ALL(this, new Negation(l), m, 1));
		System.out.println();
		System.out.println();
		
		
		System.out.println("Resolution part");
		Resolution resolution = new Resolution(this);
	
		System.out.println("Query a " + resolution.check(a));
		System.out.println("Query ~a " + resolution.check(new Negation(a)));
		System.out.println();
		System.out.println("Query b " + resolution.check(b));
		System.out.println("Query ~b " + resolution.check(new Negation(b)));
		System.out.println();
		System.out.println("Query c " + resolution.check(c));
		System.out.println("Query ~c " + resolution.check(new Negation(c)));
		System.out.println();
		System.out.println("Query d " + resolution.check(d));
		System.out.println("Query ~d " + resolution.check(new Negation(d)));
		System.out.println();
		System.out.println("Query e " + resolution.check(e));
		System.out.println("Query ~e " + resolution.check(new Negation(e)));
		System.out.println();
		System.out.println("Query f " + resolution.check(f));
		System.out.println("Query ~f " + resolution.check(new Negation(f)));
		System.out.println();
		System.out.println("Query g " + resolution.check(g));
		System.out.println("Query ~g " + resolution.check(new Negation(g)));
		System.out.println();
		System.out.println("Query h " + resolution.check(h));
		System.out.println("Query ~h " + resolution.check(new Negation(h)));
		System.out.println();
		System.out.println("Query i " + resolution.check(i));
		System.out.println("Query ~i " + resolution.check(new Negation(i)));
		System.out.println();
		System.out.println("Query j " + resolution.check(j));
		System.out.println("Query ~j " + resolution.check(new Negation(j)));
		System.out.println();
		System.out.println("Query k " + resolution.check(k));
		System.out.println("Query ~k " + resolution.check(new Negation(k)));
		System.out.println();
		System.out.println("Query l " + resolution.check(l));
		System.out.println("Query ~l " + resolution.check(new Negation(l)));
		System.out.println();
	
		System.out.println();
	}
}
