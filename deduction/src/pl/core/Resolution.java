package pl.core;

import java.util.Set;

import pl.cnf.*;
import pl.core.*;
import pl.util.ArraySet;

public class Resolution {
	
	protected ArraySet<Clause> clauses = new ArraySet<Clause>();
	
	/**
	 * Constructor
	 * Change all sentence in KB into CNF, then add them into the ArraySet
	 * @param kb
	 */
	public Resolution(KB kb)
	{
		for (Sentence s : kb.sentences)
		{
			for (Clause c : CNFConverter.convert(s))
			{
				clauses.add(c);
			}
		}
//		for (Clause c : clauses)
//			System.out.println(c);
	}
	
	/**
	 * Use resolution to check a query
	 * @param query
	 */
	public boolean check(Sentence q)
	{
		//  use a copy so that the protected variable won't change and can use many times
		ArraySet<Clause> news = new ArraySet<Clause>();
		ArraySet<Clause> clauses2 = new ArraySet<Clause>(clauses);
		
		//  change the query into negation form and add in
		for (Clause c : CNFConverter.convert(new Negation(q)))
			clauses2.add(c);

		//  for each two clause, resolve them and check
		while (true)
		{
			for (int i = 0; i < clauses2.size() - 1; i++)
			{
				for (int j = i + 1; j < clauses2.size(); j++)
				{
					ArraySet<Clause> resolvents = Resolve(clauses2.get(i), clauses2.get(j));
					//  if get empty, return true
					if (isContainEmpty(resolvents))
					{
						return true;
					}
					for (Clause s : resolvents)
						news.add(s);
				}
			}

			//  if nothing more can be added, return false
			if (isContained(clauses2, news))
				return false;
			
			for (Clause s : news)
				clauses2.add(s);
		}
	}

	/**
	 * check one set is contained by another
	 * O(n^2) complexity by double loop
	 */
	public boolean isContained(ArraySet<Clause> clauses, ArraySet<Clause> news) {
		for (int i = 0; i < news.size(); i++)
		{
			Clause c = news.get(i);
			boolean flag = false;
			for (int j = 0; j < clauses.size(); j++)
			{
				if (clauseEQ(c, clauses.get(j)))
				{
					flag = true;
					break;
				}
			}
			//  one clause couldn't be find, return false
			if (flag == false)
				return false;
		}
		//  everything is contained, return true
		return true;
	}

	/**
	 * Check two clause is equal or not
	 * O(n^2) complexity by double loop
	 */
	public boolean clauseEQ(Clause cls, Clause cls2) {
		//  if atom members are not equal, they couldn't be the same
		if (cls.size() != cls2.size())
			return false;
		
		for (int i = 0; i < cls.size(); i++)
		{
			Literal l = cls.get(i);
			boolean flag = false;
			for (int j = 0; j < cls2.size(); j++)
			{
				if (l.equals(cls2.get(j)))
				{
					flag = true;
					break;
				}
			}
			//  one atom is not equal, return false
			if (flag == false)
				return false;
		}
		//  everything is the same, return true
		return true;
	}

	/**
	 * return if the set contains empty clause
	 * O(n) complexity by single loop
	 */
	public boolean isContainEmpty(ArraySet<Clause> resolvents) {
		for (int i = 0; i < resolvents.size(); i++)
			if (resolvents.get(i).size() == 0)
				return true;
		return false;
	}

	/**
	 * Get all possible result of resolution with two clause
	 * O(n^3) complexity
	 */
	public ArraySet<Clause> Resolve(Clause cls, Clause cls2) {
		
		ArraySet<Clause> res = new ArraySet<Clause>();
		
		for (int i = 0; i < cls.size(); i++)
		{
			for (int j = 0; j < cls2.size(); j++)
			{
				//  two symbol has opposite polarity, they could be ignored and add the other
				if (cls.get(i).getContent() == cls2.get(j).getContent() &&
					cls.get(i).getPolarity() != cls2.get(j).getPolarity())
				{
					Clause c = new Clause();
					for (int k = 0; k < cls.size(); k++)
					{
						if (cls.get(k).getContent() != cls.get(i).getContent())
						{
							c.add(cls.get(k));
						}
					}
					for (int k = 0; k < cls2.size(); k++)
					{
						if (cls2.get(k).getContent() != cls2.get(j).getContent())
						{
							c.add(cls2.get(k));
						}
					}
					//  get rid of symbols of opposite polarity like a and ~a, and redundancy
					c = factoring(c);
					
					if (c != null)
						res.add(c);
				}
			}
		}
		return res;
	}

	/**
	 * get rid of symbols with opposite polarity in a clause and redundancy
	 * O(n^3) complexity
	 */
	private Clause factoring(Clause c) {
		Clause cls = new Clause();
		Clause tmp = new Clause();
		for (int i = 0; i < c.size(); i++)
		{
			boolean factor = false;
			for (int j = 0; j < c.size(); j++)
			{
				//  maybe redundancy
				if (i != j && c.get(i).getContent() == c.get(j).getContent() &&
						c.get(i).getPolarity() == c.get(j).getPolarity())
				{
					factor = true;
					//  already occurred
					boolean already = false;
					for (int it = 0; it < tmp.size(); it++)
					{
						if (c.get(i).getContent() == c.get(it).getContent()
								&& c.get(i).getPolarity() == c.get(it).getPolarity())
						{
							already = true;
						}
					}
					if (!already)
					{
						tmp.add(c.get(i));
					}
					break;
				}
				//  complementary symbols
				else if (i != j && c.get(i).getContent() == c.get(j).getContent()
						&& c.get(i).getPolarity() != c.get(j).getPolarity())
				{
					return null;
				}
			}
			if (!factor)
				cls.add(c.get(i));
				
		}
		cls.addAll(tmp);
		return cls;
	}
}
