package pl.core;

import java.util.HashMap;
import java.util.Map;

public class ModelImplementation extends KB implements Model{
	
	/**
	 * use a map to record all value of the symbols occurred in KB
	 */
	public Map<Symbol, Boolean> assignments = new HashMap<>();
	public ModelImplementation(KB kb)
	{
		super();
		for (Symbol s : kb.symbols()) {
			assignments.put(s, false);
		}
	}
	
	/**
	 * Set the value assigned to the given PropositionSymbol in this
	 * Model to the given boolean VALUE.
	 */
	public void set(Symbol sym, boolean value)
	{
		Boolean bol = assignments.get(sym);
		if (bol == null) {
			bol = new Boolean(value);
		}
		assignments.put(sym, value);
	}

	/**
	 * Returns the boolean value associated with the given PropositionalSymbol
	 * in this Model.
	 */
	public boolean get(Symbol sym)
	{
		return assignments.get(sym);
	}
	
	/**
	 * Return true if this Model satisfies (makes true) the given KB.
	 */
	public boolean satisfies(KB kb)
	{
		for (Sentence s : kb.sentences()) {
			if (satisfies(s) == false)
				return false;
		}
		return true;
	}

	/**
	 * Return true if this Model satisfies (makes true) the given Sentence.
	 */
	public boolean satisfies(Sentence sentence)
	{
		if (sentence instanceof Symbol)
		{
			return get((Symbol) sentence);
		}
		else
		{
			return sentence.isSatisfiedBy(this);
		}
	}
	
	/**
	 * Print the assignments in this Model to System.out.
	 */
	public void dump()
	{
		for (Symbol s : assignments.keySet())
			System.out.println(s + " " + assignments.get(s));
	}
}
