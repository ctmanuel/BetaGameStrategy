/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.gamma;

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

public class GammaStrategyMasterTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory GammaStrategy = StrategyGameFactory.getInstance();

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
			game = GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
	}

	@Test(expected=StrategyException.class)
	public void cannotCreateGammaStrategyWithNullConfigurations() throws StrategyException
	{
		game = GammaStrategy.makeGammaStrategyGame(null, null);
	}

	@Test
	public void createGammaStrategyController() throws StrategyException
	{
		assertNotNull(GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration));
	}

	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(3,3)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(0);	// Marshall @ (0, 1)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(0,3)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(0);	// Marshall @ (0, 1)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(-1,1)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (5, 4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(5,3)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (5, 4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(6,4)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}
	
	@Test(expected=StrategyException.class)
	public void placePieceOnChokePoint() throws StrategyException
	{
		blueConfiguration.remove(11);	// Sergeant @ (0, 4)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(2,2)));
		GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void makeMoveBeforeInitalization() throws StrategyException {
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
	}

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
/*
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
*/
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
		assertEquals(null, game.getPieceAt(new Location2D(1,2)));
	}
/*	
	//TODO draw if no possible moves left, not six moves
	@Test
	public void checkDrawGame() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(0,1), new Location2D(0,2));
		game.move(PieceType.LIEUTENANT, new Location2D(0,4), new Location2D(0,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.LIEUTENANT, new Location2D(1,4), new Location2D(1,3));
		game.move(PieceType.LIEUTENANT, new Location2D(2,1), new Location2D(2,2));
		game.move(PieceType.LIEUTENANT, new Location2D(2,4), new Location2D(2,3));
		game.move(PieceType.SERGEANT, new Location2D(3,1), new Location2D(3,2));
		game.move(PieceType.SERGEANT, new Location2D(3,4), new Location2D(3,3));
		game.move(PieceType.SERGEANT, new Location2D(4,1), new Location2D(4,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		assertEquals(MoveResultStatus.DRAW, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(5,3)), result.getBattleWinner());
	} */
}