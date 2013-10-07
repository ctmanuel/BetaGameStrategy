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


public class DeltaStrategyMasterTest {

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

	@Test(expected=StrategyException.class)
	public void cannotCreateGammaStrategyWithNullConfigurations() throws StrategyException
	{
		game = DeltaStrategy.makeDeltaStrategyGame(null, null);
	}

	@Test
	public void createGammaStrategyController() throws StrategyException
	{
		assertNotNull(DeltaStrategy.makeDeltaStrategyGame(redConfiguration, blueConfiguration));
	}

	@Test(expected=StrategyException.class)
	public void redConfigurationHasTooFewItem() throws StrategyException
	{
		redConfiguration.remove(0);
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void blueConfigurationHasTooManyItems() throws StrategyException
	{
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.SERGEANT, PlayerColor.BLUE), new Location2D(0,4)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidRow() throws StrategyException
	{
		redConfiguration.remove(0);	// Bomb @ (0, 0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.BOMB, PlayerColor.RED), new Location2D(0,4)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeRedPieceOnInvalidColumn() throws StrategyException
	{
		redConfiguration.remove(0);	// Bomb @ (0, 0)
		redConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.BOMB, PlayerColor.RED), new Location2D(-1,1)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidRow() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(5,4)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placeBluePieceOnInvalidColumn() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(10,6)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
	}

	@Test(expected=StrategyException.class)
	public void placePieceOnChokePoint() throws StrategyException
	{
		blueConfiguration.remove(0);	// Marshal @ (0, 6)
		blueConfiguration.add(new PieceLocationDescriptor(
				new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(3,5)));
		DeltaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
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
		assertEquals(new Piece(PieceType.SCOUT, PlayerColor.RED), game.getPieceAt(new Location2D(5,3)));
	}

	@Test
	public void getEmptyAtLocation() throws StrategyException {
		game.startGame();
		assertEquals(null, game.getPieceAt(new Location2D(1,4)));
	}

	@Test
	public void ScoutMove() throws StrategyException {
		game.startGame();
		assertNull(game.getPieceAt(new Location2D(1,4)));
		assertNull(game.getPieceAt(new Location2D(1,5)));
		final MoveResult result= 
				game.move(PieceType.SCOUT, new Location2D(1,3), new Location2D(1,5));
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.SCOUT, PlayerColor.RED), new Location2D(1,5)), 
				result.getBattleWinner());
	}
}
