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

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class GammaStrategyInvalidMoveTest {

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
	
	@Test(expected = StrategyException.class)
	public void makeInvalidFlagMove() throws StrategyException 
	{
		game.startGame();
		game.move(PieceType.FLAG, new Location2D(1,0), new Location2D(1,1));
	}
	
	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint22() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(2,1), new Location2D(2,2));
	}
	
	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint23() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(2,4), new Location2D(2,3));
	}
	
	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint32() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(3,1), new Location2D(3,2));
	}
	
	@Test(expected=StrategyException.class)
	public void invalidMoveToChokePoint33() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(3,4), new Location2D(3,3));
	}
	
	@Test(expected=StrategyException.class)
	public void makeRedRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(1,1));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
	}
	
	@Test(expected=StrategyException.class)
	public void makeBlueRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(1,1));
		game.move(PieceType.SERGEANT, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
	}
	
	@Test(expected=StrategyException.class)
	public void makeRedSecondPieceRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.SERGEANT, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(1,1));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
	}
	
	@Test(expected=StrategyException.class)
	public void makeRedPieceRepitionRulePlay() throws StrategyException {
		game.startGame();
		game.move(PieceType.SERGEANT, new Location2D(5,1), new Location2D(5,2));
		game.move(PieceType.SERGEANT, new Location2D(4,4), new Location2D(4,3));
		game.move(PieceType.SERGEANT, new Location2D(5,2), new Location2D(5,1));
		game.move(PieceType.SERGEANT, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
		game.move(PieceType.LIEUTENANT, new Location2D(1,4), new Location2D(1,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,2), new Location2D(1,1));
		game.move(PieceType.MARSHAL, new Location2D(5,4), new Location2D(5,3));
		game.move(PieceType.LIEUTENANT, new Location2D(1,1), new Location2D(1,2));
	}
	
}
