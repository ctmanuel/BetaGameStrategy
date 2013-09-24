/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/
package strategy.game.version.beta;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

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

public class BetaStrategyBattleTest {

	private StrategyGameController game;
	private List<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private List<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory BetaStrategy = StrategyGameFactory.getInstance();

	@Before
	public void setup() {
		final PieceLocationDescriptor[] redPieces = {
				new PieceLocationDescriptor((new Piece(PieceType.MARSHAL, PlayerColor.RED)), new Location2D(0,1)),
				new PieceLocationDescriptor((new Piece(PieceType.FLAG, PlayerColor.RED)), new Location2D(1,0)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.RED)), new Location2D(2,0)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.RED)), new Location2D(3,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(4,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(5,0)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(0,0)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(1,1)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(2,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(3,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(4,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(5,1))};

		final PieceLocationDescriptor[] bluePieces = {
				new PieceLocationDescriptor((new Piece(PieceType.MARSHAL, PlayerColor.BLUE)), new Location2D(5,4)),
				new PieceLocationDescriptor((new Piece(PieceType.FLAG, PlayerColor.BLUE)), new Location2D(0,5)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.BLUE)), new Location2D(1,5)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.BLUE)), new Location2D(2,5)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(3,5)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(4,5)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(0,4)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(1,4)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(2,4)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(3,4)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(4,4)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(5,5))};

		for (PieceLocationDescriptor piece : redPieces) {
			redConfiguration.add(piece);
		}
		for (PieceLocationDescriptor piece : bluePieces) {
			blueConfiguration.add(piece);		
		}

		try {
			game = BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void makeRedWinningBattleMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(0,2), new Location2D(0,3));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,3)), 
				result.getBattleWinner());
	}

	@Test
	public void makeBlueWinningBattleMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(5,3), new Location2D(5,2));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(5,2)), 
				result.getBattleWinner());

	}

	@Test
	public void makeRedWinsGame() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(0,2), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.MARSHAL, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(5,3), new Location2D(5,2));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(0,4), new Location2D(0,5));
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,5)), 
				result.getBattleWinner());
	}
	
	@Test
	public void RedColonelvsLieutenantandFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(redConfiguration.get(2), blueConfiguration.get(8));
		final MoveResult resultFlag = BetaStrategyGameController.battle(redConfiguration.get(2), blueConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.RED_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void BlueColonelvsLieutenantandFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(blueConfiguration.get(2), redConfiguration.get(8));
		final MoveResult resultFlag = BetaStrategyGameController.battle(blueConfiguration.get(2), redConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.BLUE_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void RedCaptainvsLieutenantandFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(redConfiguration.get(4), blueConfiguration.get(8));
		final MoveResult resultFlag = BetaStrategyGameController.battle(redConfiguration.get(4), blueConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.RED_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void BlueCaptainvsLieutenantandFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(blueConfiguration.get(4), redConfiguration.get(8));
		final MoveResult resultFlag = BetaStrategyGameController.battle(blueConfiguration.get(4), redConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.BLUE_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void BlueLieutenantvsSergeantFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(blueConfiguration.get(6), redConfiguration.get(9));
		final MoveResult resultFlag = BetaStrategyGameController.battle(blueConfiguration.get(6), redConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.BLUE_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void RedLieutenantvsSergeantFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(redConfiguration.get(6), blueConfiguration.get(9));
		final MoveResult resultFlag = BetaStrategyGameController.battle(redConfiguration.get(6), blueConfiguration.get(1));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(MoveResultStatus.RED_WINS, resultFlag.getStatus());
	}
	
	@Test
	public void BlueSergeantvsFlag() throws StrategyException
	{
		game.startGame();
		final MoveResult result = BetaStrategyGameController.battle(blueConfiguration.get(11), redConfiguration.get(1));
		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
		
	}
	
	@Test
	public void battleSameTypePiece() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(3,1), new Location2D(3,2));
		game.move(PieceType.SERGEANT, new Location2D(3,4), new Location2D(3,3));
		final MoveResult result = 
				game.move(PieceType.SERGEANT, new Location2D(3,2), new Location2D(3,3));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(null, result.getBattleWinner());
	}
	
	@Test
	public void makeMovingRedPieceLose() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		final MoveResult result = 
				game.move(PieceType.SERGEANT, new Location2D(5,2), new Location2D(5,3));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(5,2)), result.getBattleWinner());
	}
	
	@Test
	public void makeMovingBluePieceLose() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.LIEUTENANT, new Location2D(0,0), new Location2D(0,1));
		final MoveResult result = 
				game.move(PieceType.LIEUTENANT, new Location2D(0,3), new Location2D(0,2));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,3)), result.getBattleWinner());
	}
}
