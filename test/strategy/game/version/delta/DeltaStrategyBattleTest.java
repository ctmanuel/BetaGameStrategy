/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.delta;

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
import strategy.game.version.Battle;
import strategy.game.version.delta.BoardConfiguration;

public class DeltaStrategyBattleTest {
	
	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory DeltaStrategy = StrategyGameFactory.getInstance();

	@Before
	public void setup() {
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();
		
		try {
			game = DeltaStrategy.makeDeltaStrategyGame(redConfiguration, blueConfiguration);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeRedWinningBattleMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(4,5)), 
				result.getBattleWinner());
	}
	
	@Test
	public void makeBlueWinningBattleMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.GENERAL, new Location2D(1,6), new Location2D(1,5));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		final MoveResult result = 
				game.move(PieceType.GENERAL, new Location2D(1,5), new Location2D(1,4));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.GENERAL, PlayerColor.BLUE), new Location2D(1,4)), 
				result.getBattleWinner());

	}
	
	@Test
	public void makeRedWinsGame() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		game.move(PieceType.MARSHAL, new Location2D(0,6), new Location2D(0,5));
		game.move(PieceType.SCOUT, new Location2D(1,4), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(0,5), new Location2D(1,5));
		game.move(PieceType.SCOUT, new Location2D(0,4), new Location2D(0,5));
		game.move(PieceType.MARSHAL, new Location2D(1,5), new Location2D(1,4));
		game.move(PieceType.SCOUT, new Location2D(0,5), new Location2D(0,6));
		game.move(PieceType.MARSHAL, new Location2D(1,4), new Location2D(0,4));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(0,6), new Location2D(0,7));
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.RED), new Location2D(0,7)), 
				result.getBattleWinner());
	}
	
	@Test
	public void makeBlueWinsGame() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MINER, new Location2D(8,3), new Location2D(8,4));
		game.move(PieceType.SCOUT, new Location2D(9,6), new Location2D(9,5));
		game.move(PieceType.MINER, new Location2D(8,4), new Location2D(8,5));
		game.move(PieceType.SCOUT, new Location2D(9,5), new Location2D(9,4));
		game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,4));
		final MoveResult result = 
				game.move(PieceType.SCOUT, new Location2D(9,4), new Location2D(9,3));
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.BLUE), new Location2D(9,3)), 
				result.getBattleWinner());
	}
	
	@Test
	public void battleSameTypePiece() throws StrategyException {
		game.startGame();
		final MoveResult result = Battle.battle(redConfiguration.get(1), blueConfiguration.get(35));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(null, result.getBattleWinner());
	}
	
	@Test
	public void makeMovingRedPieceLose() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(redConfiguration.get(8), blueConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.GENERAL, PlayerColor.BLUE), new Location2D(8,0)), result.getBattleWinner());
	}
	
	@Test
	public void makeMovingBluePieceLose() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(blueConfiguration.get(35), redConfiguration.get(5));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MAJOR, PlayerColor.RED), new Location2D(5,9)), result.getBattleWinner());
	}
	
	@Test
	public void SpyVsMarshal() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(redConfiguration.get(30), blueConfiguration.get(0));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(PieceType.SPY, result.getBattleWinner().getPiece().getType());
		assertEquals(new Location2D(0,6), result.getBattleWinner().getLocation());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SPY, PlayerColor.RED), new Location2D(0,6)), result.getBattleWinner());
	}
	@Test
	public void MarshalVsSpy() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(blueConfiguration.get(0), redConfiguration.get(30));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(0,3)), result.getBattleWinner());
	}
	
	@Test
	public void MinerVsBomb() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(redConfiguration.get(38), blueConfiguration.get(8));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MINER, PlayerColor.RED), new Location2D(8,6)), result.getBattleWinner());
	}
	
	@Test
	public void PieceVsBomb() throws StrategyException
	{
		game.startGame();
		final MoveResult result = Battle.battle(redConfiguration.get(35), blueConfiguration.get(5));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.BOMB, PlayerColor.BLUE), new Location2D(5,6)), result.getBattleWinner());
	}
}
