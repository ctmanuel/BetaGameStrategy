/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.epsilon;

import static org.junit.Assert.*;
import static strategy.common.PlayerColor.*;
import static strategy.game.common.PieceType.*;
import static strategy.game.common.MoveResultStatus.*;

import java.util.ArrayList;

import org.junit.*;

import strategy.common.*;
import strategy.game.*;
import strategy.game.common.*;
import strategy.game.version.Battle;

/**
 * Test suite for EpsilonStrategyMaster.
 * @author gpollice
 * @version Sep 12, 2013
 */
public class EpsilonStrategyPolliceBetaTest
{
	private ArrayList<PieceLocationDescriptor> redConfiguration;
	private ArrayList<PieceLocationDescriptor> blueConfiguration;
	private final static StrategyGameFactory factory = StrategyGameFactory.getInstance();
	private StrategyGameController game;	// used for many tests
	
	// Locations used in the test
	private Location
		loc03 = new Location2D(0, 3),
		loc04 = new Location2D(0, 4),
		loc11 = new Location2D(1, 1),
		loc13 = new Location2D(1, 3),
		loc14 = new Location2D(1, 4),
		loc21 = new Location2D(2, 1),
		loc53 = new Location2D(5, 3),
		loc54 = new Location2D(5, 4),
		loc06 = new Location2D(0, 6),
		loc05 = new Location2D(0, 5),
		loc93 = new Location2D(9, 3),
		loc94 = new Location2D(9, 4),
		loc07 = new Location2D(0, 7),
		loc16 = new Location2D(1, 6),
		loc15 = new Location2D(1, 5);
	
	@Before
	public void setup() throws StrategyException
	{
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();
		
		try {
			game = factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
		
		game.startGame();
	}
	
	@Test(expected=StrategyException.class)
	public void cannotCreateEpsilonStrategyWithNullConfigurations() throws StrategyException
	{
		factory.makeEpsilonStrategyGame(null, null, null);
	}
	
	@Test
	public void createEpsilonStrategyController() throws StrategyException
	{
		assertNotNull(factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null));
	}
	
	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		addToConfiguration(SERGEANT, BLUE, 0, 4);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(1);	// Captain @ (1, 0)
		addToConfiguration(CAPTAIN, RED, 0, 4);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(1);	// Captain @ (1, 0)
		addToConfiguration(CAPTAIN, RED, -1, 0);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// Miner @ (1, 7)
		addToConfiguration(MINER, BLUE, 1, 5);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// Miner @ (1, 7)
		addToConfiguration(MINER, BLUE, 10, 6);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void twoPiecesOnSameLocationInStartingConfiguration() throws StrategyException
	{
		redConfiguration.remove(1);	// Captain @ (1, 0)
		addToConfiguration(CAPTAIN, RED, 9, 3); // Same place as RED Flag
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void redHasLessColonelAndMoreSergeants() throws StrategyException
	{
		redConfiguration.remove(9); // Colonel @ (9, 0)
		addToConfiguration(SERGEANT, RED, 9, 0);
		factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}
	
	@Test(expected=StrategyException.class)
	public void makeMoveBeforeCallingStartGame() throws StrategyException
	{
		game = factory.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
		game.move(SCOUT, loc53, loc54);
	}
	
	@Test
	public void redMakesValidFirstMoveStatusIsOK() throws StrategyException
	{
		final MoveResult result = game.move(SCOUT, loc53, loc54);
		assertEquals(OK, result.getStatus());
	}
	
	@Test
	public void redMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(SCOUT, loc53, loc54);
		assertNull(game.getPieceAt(loc53));
		assertEquals(new Piece(SCOUT, RED), game.getPieceAt(loc54));
	}
	
	@Test(expected=StrategyException.class)
	public void redAttemptsMoveFromEmptyLocation() throws StrategyException
	{
		game.move(SPY, loc04, loc05);
	}
	
	@Test
	public void blueMakesValidFirstMoveAndBoardIsCorrect() throws StrategyException
	{
		game.move(SPY, loc03, loc04);
		game.move(MARSHAL, loc06, loc05);
		assertEquals(new Piece(MARSHAL, BLUE), game.getPieceAt(loc05));
	}
	
	@Test
	public void redWins() throws StrategyException
	{
		final MoveResult moveResult = Battle.battle(new PieceLocationDescriptor(new Piece(SPY, RED), loc03), 
				new PieceLocationDescriptor(new Piece(FLAG, BLUE), loc07));
		assertEquals(RED_WINS, moveResult.getStatus());
	}
	
	@Test
	public void blueWins() throws StrategyException
	{
		final MoveResult moveResult = Battle.battle(new PieceLocationDescriptor(new Piece(MARSHAL, BLUE), loc06), 
				new PieceLocationDescriptor(new Piece(FLAG, RED), loc93));
		assertEquals(BLUE_WINS, moveResult.getStatus());
	}
	
	@Test
	public void attackerWinsStrike() throws StrategyException
	{
		game.move(SPY, loc03, loc04);
		game.move(MARSHAL, loc06, loc05);
		final MoveResult moveResult = game.move(SPY, loc04, loc05);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(SPY, RED), loc05),
				moveResult.getBattleWinner());
		assertNull(game.getPieceAt(loc14));
		assertEquals(new Piece(SPY, RED), game.getPieceAt(loc05));
	}
	
	@Test
	public void defenderWinsStrike() throws StrategyException
	{
		game.move(SCOUT, loc13, loc14);
		game.move(MARSHAL, loc06, loc05);
		game.move(SCOUT, loc14, loc04);
		game.move(GENERAL, loc16, loc15);
		final MoveResult moveResult = game.move(SCOUT, loc04, loc05);
		assertEquals(OK, moveResult.getStatus());
		assertEquals(
				new PieceLocationDescriptor(new Piece(MARSHAL, BLUE), loc04),
				moveResult.getBattleWinner());
		assertEquals(new Piece(MARSHAL, BLUE), game.getPieceAt(loc04));
	}
	
	@Test
	public void strikeResultsInDraw() throws StrategyException
	{
		PieceLocationDescriptor playerPiece = new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(4,3));
		PieceLocationDescriptor opponentPiece = new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(0,6));
		final MoveResult moveResult = Battle.battle(playerPiece, opponentPiece);
		assertEquals(OK, moveResult.getStatus());
		assertNull(moveResult.getBattleWinner());
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToStrikeYourOwnPiece() throws StrategyException
	{
		game.move(CAPTAIN, loc11, loc21);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToMoveFlag() throws StrategyException
	{
		game.move(FLAG, loc93, loc94);
	}
	
	@Test(expected=StrategyException.class)
	public void attemptToRestartGameInProgress() throws StrategyException
	{
		game.move(SCOUT, loc53, loc54);
		game.startGame();
	}
	
	// Helper methods
	private void addToConfiguration(PieceType type, PlayerColor color, int x, int y)
	{
		final PieceLocationDescriptor confItem = new PieceLocationDescriptor(
				new Piece(type, color),
				new Location2D(x, y));
		if (color == PlayerColor.RED) {
			redConfiguration.add(confItem);
		} else {
			blueConfiguration.add(confItem);
		}
	}
}