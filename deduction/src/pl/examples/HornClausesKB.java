package pl.examples;

import pl.core.*;

public class HornClausesKB extends KB {
	/**
	 * check Horn Clause by enumerating truth table and resolution
	 */
	public HornClausesKB()
	{
		super();
		Symbol my = intern("unicorn is mythical");
		Symbol mt  = intern("unicorn is mortal");
		Symbol mm = intern("unicorn is a mammal");
		Symbol h  = intern("unicorn is horned");
		Symbol mg = intern("unicorn is magical");
		
		add(new Implication(my, new Negation(mt)));
		add(new Implication(new Negation(my), new Conjunction(mt, mm)));
		add(new Implication(new Disjunction(new Negation(mt),mm),h));
		add(new Implication(h, mg));
		
		System.out.println("Enumerating truth table");
		
		ModelImplementation model = new ModelImplementation(this);
		EnumTB tt = new EnumTB(model);
		
		System.out.println("Query mythical");
		System.out.println(tt.TT_CHECK_ALL(this, my, model, 1));
		System.out.println("Query ~mythical");
		System.out.println(tt.TT_CHECK_ALL(this, new Negation(my), model, 1));
		
		System.out.println("Query magical");
		System.out.println(tt.TT_CHECK_ALL(this, mg, model, 1));
		System.out.println("Query ~magical");
		System.out.println(tt.TT_CHECK_ALL(this, new Negation(mg), model, 1));
		
		System.out.println("Query horned");
		System.out.println(tt.TT_CHECK_ALL(this, h, model, 1));
		System.out.println("Query ~horned");
		System.out.println(tt.TT_CHECK_ALL(this, new Negation(h), model, 1));
		System.out.println();
		
		System.out.println("Resolution part");
		Resolution resolution = new Resolution(this);
		System.out.println("Query mythical");
		System.out.println(resolution.check(my));
		System.out.println("Query ~mythical");
		System.out.println(resolution.check(new Negation(my)));

		System.out.println("Query magical");
		System.out.println(resolution.check(mg));
		System.out.println("Query ~magical");
		System.out.println(resolution.check(new Negation(mg)));
	
		System.out.println("Query horned");
		System.out.println(resolution.check(h));
		System.out.println("Query ~horned");
		System.out.println(resolution.check(new Negation(h)));
		System.out.println();

	}
	public static void main(String[] argv)
	{
		new HornClausesKB();
	}
}
