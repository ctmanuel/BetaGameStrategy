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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

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
import strategy.game.version.Battle;


public class EpsilonStrategyMasterTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory EpsilonStrategy = StrategyGameFactory.getInstance();

	@Before
	public void setup() throws StrategyException {
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();

		game = EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);

	}

	@Test(expected=StrategyException.class)
	public void cannotCreateEpsilonStrategyWithNullConfigurations() throws StrategyException
	{
		game = EpsilonStrategy.makeEpsilonStrategyGame(null, null, null);
	}

	@Test(expected=StrategyException.class)
	public void cannotStartGameAlreadyInProgress() throws StrategyException{
		game.startGame();
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		game.startGame();

	}

	@Test(expected=StrategyException.class)
	public void cannotStartGameOver() throws StrategyException{
		game.startGame();
		PieceLocationDescriptor playerPiece1 = new PieceLocationDescriptor(new Piece(PieceType.SPY, PlayerColor.RED), new Location2D(0,3));
		PieceLocationDescriptor opponentPiece1 = new PieceLocationDescriptor(new Piece(PieceType.FLAG, PlayerColor.BLUE), new Location2D(0,7));
		final MoveResult result = Battle.battle(playerPiece1, opponentPiece1);
		assertEquals(MoveResultStatus.FLAG_CAPTURED, result.getStatus());
		PieceLocationDescriptor playerPiece2 = new PieceLocationDescriptor(new Piece(PieceType.MINER, PlayerColor.RED), new Location2D(8,3));
		PieceLocationDescriptor opponentPiece2 = new PieceLocationDescriptor(new Piece(PieceType.FLAG, PlayerColor.BLUE), new Location2D(6,7));
		final MoveResult result2 = Battle.battle(playerPiece2, opponentPiece2);
		assertEquals(MoveResultStatus.RED_WINS, result2.getStatus());
		game.startGame();
	}

	@Test
	public void createEpsilonStrategyController() throws StrategyException
	{
		assertNotNull(EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null));
	}

	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(0,4)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(0);	// Bomb @ (0, 0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.BOMB, PlayerColor.RED), new Location2D(0,4)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(0);	// Bomb @ (0, 0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.BOMB, PlayerColor.RED), new Location2D(-1,1)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(5,4)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(10,6)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void placePieceOnChokePoint() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(3,5)));
		EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitalization() throws StrategyException {
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
	}

	@Test
	public void makeValidMove() throws StrategyException
	{
		game.startGame();
		final MoveResult result= 
				game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SPY, PlayerColor.RED), new Location2D(0,4)), 
				result.getBattleWinner());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}	

	@Test
	public void getBluePieceAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), game.getPieceAt(new Location2D(0,6)));
	}

	@Test
	public void getRedPieceAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(new Piece(PieceType.FIRST_LIEUTENANT, PlayerColor.RED), game.getPieceAt(new Location2D(5,3)));
	}

	@Test
	public void getEmptyAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(null, game.getPieceAt(new Location2D(1,4)));
	}

	@Test
	public void ScoutMovePositiveY() throws StrategyException {
		game.startGame();
		assertNull(game.getPieceAt(new Location2D(1,4)));
		assertNull(game.getPieceAt(new Location2D(1,5)));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,5));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.RED), new Location2D(1,5)), 
				result.getBattleWinner());
	}

	@Test
	public void ScoutMoveNegativeX() throws StrategyException {
		game.startGame();
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(0,6), new Location2D(0,5));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		assertNull(game.getPieceAt(new Location2D(0,6)));
		assertNull(game.getPieceAt(new Location2D(1,6)));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(2,6), new Location2D(0,6));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.BLUE), new Location2D(0,6)), 
				result.getBattleWinner());
	}

	@Test
	public void ScoutMovePositiveX() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.SCOUT, new Location2D(4,2), new Location2D(4,3));
		game.move(PieceType.MARSHAL, new Location2D(0,6), new Location2D(0,5));
		game.move(PieceType.SCOUT, new Location2D(5,2), new Location2D(5,3));
		game.move(PieceType.MARSHAL, new Location2D(0,5), new Location2D(0,4));
		assertNull(game.getPieceAt(new Location2D(4,2)));
		assertNull(game.getPieceAt(new Location2D(5,2)));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(3,2), new Location2D(5,2));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.RED), new Location2D(5,2)), 
				result.getBattleWinner());
	}

	@Test
	public void ScoutMoveNegativeY() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,4));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.BLUE), new Location2D(9,4)), 
				result.getBattleWinner());
	}

	@Test
	public void RedMakesNullMove() throws StrategyException{
		game.startGame();
		final MoveResult result = game.move(null, null, null);
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
	}

	@Test
	public void BlueMakesNullMove() throws StrategyException{
		game.startGame();
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		final MoveResult result = game.move(null, null, null);
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
	}
}