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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

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

public class BetaStrategyMasterTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
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

	@Test(expected=StrategyException.class)
	public void cannotCreateBetaStrategyWithNullConfigurations() throws StrategyException
	{
		game = BetaStrategy.makeBetaStrategyGame(null, null);
	}

	@Test
	public void createBetaStrategyController() throws StrategyException
	{
		assertNotNull(BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration));
	}

	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(3,3)));
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(0);	// Marshall @ (0, 1)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,3)));
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(0);	// Marshall @ (0, 1)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(-1,1)));
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (5, 4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(5,3)));
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (5, 4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(6,4)));
		BetaStrategy.makeBetaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitalization() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
	}

	//TODO: fix this test
	@Test
	public void makeValidMove() throws StrategyException
	{
		game.startGame();
		final MoveResult result= 
				game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,2)), 
				result.getBattleWinner());
		assertEquals(MoveResultStatus.OK, result.getStatus());
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveWrongPiece() throws StrategyException{
		game.startGame();
		game.move(PieceType.BOMB, new Location2D(0,1), new Location2D(0,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveDiagonal() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(4,1), new Location2D(5,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXFrom() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(2,1), new Location2D(2,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYFrom() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(3,2), new Location2D(3,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToBound() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(6,1));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToBound() throws StrategyException {
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(5,5), new Location2D(5,6));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveYToMoreSpace() throws StrategyException {
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidMoveXToMoreSpace() throws StrategyException {
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(3,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidConsecutiveMove() throws StrategyException {
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(2,2));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidRedMovesBlue() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(5,4), new Location2D(5,3));
	}

	@Test(expected=StrategyException.class)
	public void makeInvalidBlueMovesRed() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(3,1), new Location2D(3,2));
		game.move(PieceType.SERGEANT, new Location2D(3,2), new Location2D(3,3));
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

	@Test(expected=StrategyException.class)
	public void makeMoveAfterGameIsComplete() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(0,2), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.MARSHAL, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(5,3), new Location2D(5,2));
		game.move(PieceType.MARSHAL, new Location2D(0,4), new Location2D(0,5));
		game.move(PieceType.SERGEANT, new Location2D(3,4), new Location2D(3,3));
	}

	@Test
	public void playTwoGames() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(0,2), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.MARSHAL, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(5,3), new Location2D(5,2));
		game.move(PieceType.MARSHAL, new Location2D(0,4), new Location2D(0,5));
		
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(0,2), new Location2D(0,3));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.MARSHAL, new Location2D(0,3), new Location2D(0,4));
		game.move(PieceType.MARSHAL, new Location2D(5,3), new Location2D(5,2));
		game.move(PieceType.MARSHAL, new Location2D(0,4), new Location2D(0,5));
		
	}

	@Test
	public void getBluePieceAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), game.getPieceAt(new Location2D(5,4)));
	}

	@Test
	public void getRedPieceAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(new Piece(PieceType.MARSHAL, PlayerColor.RED), game.getPieceAt(new Location2D(0,1)));
	}

	@Test
	public void getEmptyAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(null, game.getPieceAt(new Location2D(3,3)));
	}
	
	@Test
	public void ColonelvsLieutenantandFlag() throws StrategyException
	{
		redConfiguration.remove(2);	// Colonel @ (2,0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.COLONEL, PlayerColor.RED), new Location2D(2,2)));
		blueConfiguration.remove(8);	// Lieutenant @ (2,4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE), new Location2D(2,3)));
		blueConfiguration.remove(1);	// Flag @ (0,5)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.FLAG, PlayerColor.BLUE), new Location2D(2,4)));
		game.startGame();
		game.move(PieceType.COLONEL, new Location2D(2,2), new Location2D(2,3));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		
		final MoveResult result = 
				game.move(PieceType.COLONEL, new Location2D(2,3), new Location2D(2,4));
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
	}
	
	@Test
	public void CaptainvsLieutenantandFlag() throws StrategyException
	{
		redConfiguration.remove(4);	// Captain @ (4,0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.CAPTAIN, PlayerColor.RED), new Location2D(2,2)));
		blueConfiguration.remove(8);	// Lieutenant @ (2,4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE), new Location2D(2,3)));
		blueConfiguration.remove(1);	// Flag @ (0,5)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.FLAG, PlayerColor.BLUE), new Location2D(2,4)));
		game.startGame();
		game.move(PieceType.CAPTAIN, new Location2D(2,2), new Location2D(2,3));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		
		final MoveResult result = 
				game.move(PieceType.CAPTAIN, new Location2D(2,3), new Location2D(2,4));
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
	}
	
	@Test
	public void LieutenantvsSergeantFlag() throws StrategyException
	{
//		redConfiguration.remove(9);	// Sergeant @ (3,1)
//		redConfiguration.add(new PieceLocationDescriptor(
//				new Piece(PieceType.SERGEANT, PlayerColor.RED), new Location2D(3,2)));
//		blueConfiguration.remove(8);	// Lieutenant @ (2,4)
//		blueConfiguration.add(new PieceLocationDescriptor(
//				new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE), new Location2D(3,3)));
//		redConfiguration.remove(1);	// Flag @ (1,0)
//		redConfiguration.add(new PieceLocationDescriptor(
//				new Piece(PieceType.FLAG, PlayerColor.RED), new Location2D(3,1)));
//		game.startGame();
//		game.move(PieceType.SERGEANT, new Location2D(4,1), new Location2D(4,2));
//		game.move(PieceType.LIEUTENANT, new Location2D(3,3), new Location2D(3,2));
//		game.move(PieceType.SERGEANT, new Location2D(4,2), new Location2D(4,3));
//		final MoveResult result = 
//				game.move(PieceType.LIEUTENANT, new Location2D(3,2), new Location2D(3,1));
//		assertEquals(MoveResultStatus.BLUE_WINS, result.getStatus());
		
		redConfiguration.remove(9);
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.RED), new Location2D(3,2)));
		blueConfiguration.remove(8);
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE), new Location2D(3,3)));
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(4,1), new Location2D(4,2));
		game.move(PieceType.LIEUTENANT, new Location2D(3,3), new Location2D(3,2));
		//game.move(PieceType.SERGEANT, new Location2D(4,2), new Location2D(4,3));
		
	}
}