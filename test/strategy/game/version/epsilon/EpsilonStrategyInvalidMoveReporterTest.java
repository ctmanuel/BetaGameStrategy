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
import strategy.game.common.StrategyGameObserver;

public class EpsilonStrategyInvalidMoveReporterTest {

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

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveDiagonal() throws StrategyException {
		game.move(PieceType.MINER, new Location2D(8,3), new Location2D(9,4));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXFrom() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(3,3), new Location2D(3,4));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYFrom() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(4,2), new Location2D(4,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToBound() throws StrategyException {
		game.move(PieceType.GENERAL, new Location2D(9,2), new Location2D(10,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToBound() throws StrategyException {
		game.move(PieceType.COLONEL, new Location2D(9,0), new Location2D(9,-1));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToMoreSpace() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToMoreSpace() throws StrategyException {
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(9,5), new Location2D(9,6));
		game.move(PieceType.MINER, new Location2D(3,3), new Location2D(5,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidConsecutiveMove() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidRedMovesBlue() throws StrategyException {
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidBlueMovesRed() throws StrategyException {
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
	}

	@Test(expected=StrategyException.class)
	public void makeMoveAfterGameIsComplete() throws StrategyException
	{
		game.move(PieceType.MINER, new Location2D(8,3), new Location2D(8,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.MINER, new Location2D(8,4), new Location2D(8,5));
		game.move(PieceType.SCOUT, new Location2D(9,5), new Location2D(9,4));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		final MoveResult flagResult = game.move(PieceType.SCOUT, new Location2D(9,4), new Location2D(9,3));
		assertEquals(MoveResultStatus.FLAG_CAPTURED, flagResult.getStatus());
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.SCOUT, new Location2D(1,4), new Location2D(1,5));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,4), new Location2D(1,3));
		game.move(PieceType.MINER, new Location2D(8,5), new Location2D(8,4));

		final MoveResult result = 
				game.move(PieceType.FIRST_LIEUTENANT, new Location2D(1,3), new Location2D(1,2));
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.FIRST_LIEUTENANT, PlayerColor.BLUE), new Location2D(1,2)), 
				result.getBattleWinner());
		game.move(PieceType.MINER, new Location2D(8,4), new Location2D(8,5));
	}

	@Test(expected = StrategyException.class)
	public void makeInvalidFlagMove() throws StrategyException 
	{
		game.move(PieceType.FLAG, new Location2D(9,3), new Location2D(9,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint34() throws StrategyException
	{
		game.move(PieceType.MINER, new Location2D(3,3), new Location2D(3,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint25() throws StrategyException
	{
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(2,6), new Location2D(2,5));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint64() throws StrategyException
	{
		game.move(PieceType.MINER, new Location2D(6,3), new Location2D(6,4));
	}

	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint65() throws StrategyException
	{
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(6,6), new Location2D(6,5));
	}

	@Test
	public void makeRedRepitionRulePlay() throws StrategyException {
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
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,6));
	}

	@Test(expected=StrategyException.class)
	public void BombMove() throws StrategyException {
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.BOMB, new Location2D(8,6), new Location2D(8,5));
	}

	@Test(expected=StrategyException.class)
	public void ScoutMoveOverPiece() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(5,4));
		game.move(PieceType.MINER, new Location2D(4,5), new Location2D(4,4));
		assertNull(game.getPieceAt(new Location2D(4,5)));
		assertNotNull(game.getPieceAt(new Location2D(4,4)));
		game.move(PieceType.SCOUT, new Location2D(4,2), new Location2D(4,6));
	}

	@Test(expected=StrategyException.class)
	public void InvalidFirstLieutenantMove() throws StrategyException{
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,5));
	}

	@Test(expected=StrategyException.class)
	public void FirstLieutenantBattleOverPiece() throws StrategyException {
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,3), new Location2D(5,4));
		game.move(PieceType.SERGEANT, new Location2D(5,6), new Location2D(5,5));
		game.move(PieceType.SPY, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(5,6));
		game.move(PieceType.FIRST_LIEUTENANT, new Location2D(5,4), new Location2D(5,6));
	}

	@Test(expected=StrategyException.class) 
	public void ScoutMoveToChokePoint() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SCOUT, new Location2D(2,6), new Location2D(2,4));
	}
}