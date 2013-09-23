package strategy.game.version;

/**
 * The Initialize interface must be implemented by the 
 * version controller in any Strategy variant.
 * (beginning with Gamma)
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */
public interface Initialize {
	
	/**
	 * Initialize the each piece's rank
	 */
	public void initializePieceRanks();
	
	/**
	 * Initialize the pieces required for the game
	 */
	public void fillInitialPieces();
}
