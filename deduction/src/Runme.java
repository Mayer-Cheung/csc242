import pl.examples.*;

public class Runme {

	/**
	 * as the name said, main function, run me!
	 */
	public static void main(String[] args) {
		System.out.println("The ModusPonens comes first");
		new ModusPonensKB();
		System.out.println();
		
		System.out.println("Then is the WumpusWorld");
		new WumpusWorldKB();
		System.out.println();
		
		System.out.println("Then comes the HornClause");
		new HornClausesKB();
		
		System.out.println("Then the 4(a)" );
		new LiarsAndTruthTellersKB();
		
		System.out.println("Then the 4(b)");
		new LATT2KB();
		
		System.out.println("Then the 5");
		new MLATTKB();
	}

}
