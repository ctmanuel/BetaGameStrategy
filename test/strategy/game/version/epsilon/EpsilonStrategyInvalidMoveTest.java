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

import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class EpsilonStrategyInvalidMoveTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory EpsilonStrategy = StrategyGameFactory.getInstance();

	@Before
	public void setup() {
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();

		try {
			game = EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveDiagonal() throws StrategyException {
		game.startGame();
		game.move(PieceType.MINER, new Location2D(8,3), new Location2D(9,4));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXFrom() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(3,3), new Location2D(3,4));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYFrom() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,2), new Location2D(4,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToBound() throws StrategyException {
		game.startGame();
		game.move(PieceType.GENERAL, new Location2D(9,2), new Location2D(10,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToBound() throws StrategyException {
		game.startGame();
		game.move(PieceType.COLONEL, new Location2D(9,0), new Location2D(9,-1));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToMoreSpace() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToMoreSpace() throws StrategyException {
		game.startGame();
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(9,5), new Location2D(9,6));
		game.move(PieceType.MINER, new Location2D(3,3), new Location2D(5,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidConsecutiveMove() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidRedMovesBlue() throws StrategyException {
		game.startGame();
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidBlueMovesRed() throws StrategyException {
		game.startGame();
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
	}

	@Test(expected=StrategyException.class)
	public void makeMoveAfterGameIsComplete() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MINER, new Location2D(8,3), new Location2D(8,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(9,5), new Location2D(9,4));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
		MoveResult result = game.move(PieceType.SCOUT, new Location2D(9,4), new Location2D(9,3));
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
		assertEquals(PieceType.SCOUT, result.getBattleWinner().getPiece().getType());
		game.move(PieceType.MARSHAL, new Location2D(4,5), new Location2D(4,4));
	}

	@Test(expected = StrategyException.class)
	public void makeInvalidFlagMove() throws StrategyException 
	{
		game.startGame();
		game.move(PieceType.FLAG, new Location2D(9,3), new Location2D(9,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint34() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MINER, new Location2D(3,3), new Location2D(3,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint25() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(2,6), new Location2D(2,5));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint64() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MINER, new Location2D(6,3), new Location2D(6,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint65() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(6,6), new Location2D(6,5));
	}

	@Test
	public void makeRedRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,5), new Location2D(1,4));
		MoveResult result = game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		assertEquals(null, result.getBattleWinner());
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
	}

	@Test
	public void makeBlueRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,5), new Location2D(1,6));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
		MoveResult result = game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		assertEquals(null, result.getBattleWinner());
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
	}

	@Test(expected=StrategyException.class)
	public void ScoutMoveAndAttack() throws StrategyException {
		game.startGame();
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,6));
	}

	@Test(expected=StrategyException.class)
	public void BombMove() throws StrategyException {
		game.startGame();
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.BOMB, new Location2D(8,6), new Location2D(8,5));
	}

	@Test(expected=StrategyException.class)
	public void ScoutMoveOverPiece() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(5,4));
		game.move(PieceType.MINER, new Location2D(4,5), new Location2D(4,4));
		assertNull(game.getPieceAt(new Location2D(4,5)));
		assertNotNull(game.getPieceAt(new Location2D(4,4)));
		game.move(PieceType.SCOUT, new Location2D(4,2), new Location2D(4,6));
	}
}
