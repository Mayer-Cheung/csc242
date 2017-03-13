package pl.core;

import java.util.HashMap;
import java.util.Map;

public class EnumTB {
	protected Map<Integer, Symbol> ID = new HashMap<>();
	protected int count;
	/**
	 * push all symbol in the KB into a map, and return the total number
	 */
	public EnumTB(ModelImplementation m)
	{
		int cnt = 1;
		for (Symbol s : m.assignments.keySet())
		{
			ID.put(cnt, s);
			cnt += 1;
		}
		count = cnt;
	}
	/**
	 * enumerating all the truth table by Depth First Search
	 */
	public boolean TT_CHECK_ALL(KB kb, Sentence query, ModelImplementation m, int depth)
	{
		//  all symbols are set value, check it
		if (depth == count)
		{
			if (m.satisfies(kb))
				return m.satisfies(query);
			else
				return true;
		}
		else
		{
			//  else set next symbol, try both true and false then increase the depth
			m.set(ID.get(depth), true);
			boolean a = TT_CHECK_ALL(kb, query, m, depth+1);
			m.set(ID.get(depth), false);
			boolean b = TT_CHECK_ALL(kb, query, m, depth+1);
			return a && b;
		}
	}
	
}
