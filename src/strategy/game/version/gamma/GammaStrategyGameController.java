/**
 * 
 */
package strategy.game.version.gamma;

import java.util.Collection;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * The GammaStrategyGameController implements the game core for
 * the Gamma Strategy version.
 * @author dmlarose, ctmanuel
 * @version Sep 23, 2013
 */
public class GammaStrategyGameController implements StrategyGameController {

	private boolean gameStarted;
	private static boolean gameOver = false;

	private final Collection<PieceLocationDescriptor> origionalredConfiguration;
	private final Collection<PieceLocationDescriptor> origionalblueConfiguration;
	private static Collection<PieceLocationDescriptor> redConfiguration = null;
	private static Collection<PieceLocationDescriptor> blueConfiguration = null;
	
	/**
	 * Constructor for the Gamma Game Strategy
	 * @param redConfiguration the list of red pieces
	 * @param blueConfiguration the list of blue pieces
	 */
	public GammaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		gameStarted = false;
		gameOver = false;

		origionalblueConfiguration = blueConfiguration;
		origionalredConfiguration = redConfiguration;
	}

	@Override
	public void startGame() throws StrategyException {
		// TODO Auto-generated method stub
		
	}

	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Piece getPieceAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}
}
