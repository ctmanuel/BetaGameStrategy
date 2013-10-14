package strategy.game.version.epsilon;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

public class EpsilonStrategyReporterTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory EpsilonStrategy = StrategyGameFactory.getInstance();
	private ArrayList<StrategyGameObserver> EpsilonObserver = new ArrayList<StrategyGameObserver>();

	@Before
	public void setup() throws StrategyException {
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();

		game = EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, EpsilonObserver);
		game.startGame();
	}
	
	@Test
	public void RedMakesNullMove() throws StrategyException{
		final MoveResult result = game.move(null, null, null);
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
	}
	
	@Test
	public void makeValidMove() throws StrategyException
	{

		final MoveResult result= 
				game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SPY, PlayerColor.RED), new Location2D(0,4)), 
				result.getBattleWinner());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}
	
	@Test
	public void makeRedWinsGame() throws StrategyException
	{
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.MARSHAL, new Location2D(0,6), new Location2D(0,5));
		game.move(PieceType.SCOUT, new Location2D(1,4), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(0,5), new Location2D(1,5));
		game.move(PieceType.SCOUT, new Location2D(0,4), new Location2D(0,5));
		game.move(PieceType.MARSHAL, new Location2D(1,5), new Location2D(1,4));
		game.move(PieceType.SCOUT, new Location2D(0,5), new Location2D(0,6));
		game.move(PieceType.MARSHAL, new Location2D(1,4), new Location2D(0,4));
		final MoveResult flagResult = game.move(PieceType.SCOUT, new Location2D(0,6), new Location2D(0,7));
		assertEquals(MoveResultStatus.FLAG_CAPTURED, flagResult.getStatus());
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,4), new Location2D(5,6));
		game.move(PieceType.MINER, new Location2D(4,5), new Location2D(4,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,6), new Location2D(6,6));
		game.move(PieceType.SCOUT, new Location2D(4,7), new Location2D(4, 6));
		final MoveResult result = game.move(PieceType.FIRST_LIEUTENANT, new Location2D(6,6), new Location2D(6,7));
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.FIRST_LIEUTENANT, PlayerColor.RED), new Location2D(6,7)), 
				result.getBattleWinner());
	}
	
	@Test(expected=StrategyException.class)
	public void makeInvalidMoveDiagonal() throws StrategyException {
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(0,4));	}
}
