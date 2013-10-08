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

public class DeltaStrategyDrawTest {
	private StrategyGameController game;
	private ArrayList<Location2D> boardSpaces = new ArrayList<Location2D>();
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory DeltaStrategy = StrategyGameFactory.getInstance();
//	private Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
//	private Piece redGeneral = new Piece(PieceType.GENERAL, PlayerColor.RED);
//	private Piece redColonel = new Piece(PieceType.COLONEL, PlayerColor.RED);
//	private Piece redMajor = new Piece(PieceType.MAJOR, PlayerColor.RED);
//	private Piece redCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.RED);
//	private Piece redLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.RED);
//	private Piece redSergeant = new Piece(PieceType.SERGEANT, PlayerColor.RED);
//	private Piece redMiner = new Piece(PieceType.MINER, PlayerColor.RED);
//	private Piece redSpy = new Piece(PieceType.SPY, PlayerColor.RED);
//	private Piece redScout = new Piece(PieceType.SCOUT, PlayerColor.RED);
//	private Piece redBomb = new Piece(PieceType.BOMB, PlayerColor.RED);
//	private Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
//	private Piece blueGeneral = new Piece(PieceType.GENERAL, PlayerColor.BLUE);
//	private Piece blueColonel = new Piece(PieceType.COLONEL, PlayerColor.BLUE);
//	private Piece blueMajor = new Piece(PieceType.MAJOR, PlayerColor.BLUE);
//	private Piece blueCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.BLUE);
//	private Piece blueLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE);
//	private Piece blueSergeant = new Piece(PieceType.SERGEANT, PlayerColor.BLUE);
//	private Piece blueMiner = new Piece(PieceType.MINER, PlayerColor.BLUE);
//	private Piece blueSpy = new Piece(PieceType.SPY, PlayerColor.BLUE);
//	private Piece blueScout = new Piece(PieceType.SCOUT, PlayerColor.BLUE);
//	private Piece blueBomb = new Piece(PieceType.BOMB, PlayerColor.BLUE);
	
	@Before
	public void setup(){
		for(int column = 0; column <= 9; column++){
			for(int row = 0; row <= 9; row++){
				boardSpaces.add(new Location2D(row, column));
			}
		}
		
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
	public void testSetup() throws StrategyException {
		game.startGame();
		assertEquals(redMarshal, game.getPieceAt(boardSpaces.get(34)));
	}
	
	@Test
	public void Draw() throws StrategyException {
		game.startGame();
		game.move(redMarshal.getType(), boardSpaces.get(34), boardSpaces.get(44));
		game.move(blueMarshal.getType(), boardSpaces.get(60), boardSpaces.get(50));
		game.move(redMarshal.getType(), boardSpaces.get(44), boardSpaces.get(54));
		game.move(blueMarshal.getType(), boardSpaces.get(50), boardSpaces.get(40));
		game.move(redMarshal.getType(), boardSpaces.get(54), boardSpaces.get(64));
		game.move(blueMarshal.getType(), boardSpaces.get(40), boardSpaces.get(30));
		game.move(redMarshal.getType(), boardSpaces.get(64), boardSpaces.get(74));
		game.move(blueMarshal.getType(), boardSpaces.get(30), boardSpaces.get(31));
		game.move(redMarshal.getType(), boardSpaces.get(74), boardSpaces.get(75));
		game.move(blueMarshal.getType(), boardSpaces.get(31), boardSpaces.get(21));
		game.move(redMarshal.getType(), boardSpaces.get(75), boardSpaces.get(76));
		game.move(blueMarshal.getType(), boardSpaces.get(21), boardSpaces.get(20));
		game.move(redMarshal.getType(), boardSpaces.get(76), boardSpaces.get(66));
		game.move(blueMarshal.getType(), boardSpaces.get(20), boardSpaces.get(10));
		game.move(redMarshal.getType(), boardSpaces.get(66), boardSpaces.get(67));
		game.move(blueMarshal.getType(), boardSpaces.get(10), boardSpaces.get(11));
		game.move(redMarshal.getType(), boardSpaces.get(67), boardSpaces.get(77));
		game.move(blueMarshal.getType(), boardSpaces.get(11), boardSpaces.get(1));
		game.move(redMarshal.getType(), boardSpaces.get(77), boardSpaces.get(78));
		game.move(blueMarshal.getType(), boardSpaces.get(1), boardSpaces.get(2));
		game.move(redMarshal.getType(), boardSpaces.get(78), boardSpaces.get(79));
		game.move(blueMarshal.getType(), boardSpaces.get(2), boardSpaces.get(3));
		game.move(redMarshal.getType(), boardSpaces.get(79), boardSpaces.get(69));
		game.move(blueMarshal.getType(), boardSpaces.get(3), boardSpaces.get(13));
		game.move(redMarshal.getType(), boardSpaces.get(69), boardSpaces.get(79));
		game.move(blueMarshal.getType(), boardSpaces.get(13), boardSpaces.get(23));
		game.move(redMarshal.getType(), boardSpaces.get(79), boardSpaces.get(89));
		game.move(blueMarshal.getType(), boardSpaces.get(23), boardSpaces.get(33));
		game.move(redMarshal.getType(), boardSpaces.get(89), boardSpaces.get(99));
		game.move(blueMarshal.getType(), boardSpaces.get(33), boardSpaces.get(34));
		game.move(redMarshal.getType(), boardSpaces.get(99), boardSpaces.get(98));
		game.move(blueMarshal.getType(), boardSpaces.get(34), boardSpaces.get(24));
		game.move(redMarshal.getType(), boardSpaces.get(98), boardSpaces.get(88));
		game.move(blueMarshal.getType(), boardSpaces.get(24), boardSpaces.get(14));
		game.move(redMarshal.getType(), boardSpaces.get(88), boardSpaces.get(87));
		game.move(blueMarshal.getType(), boardSpaces.get(14), boardSpaces.get(4));
		game.move(redMarshal.getType(), boardSpaces.get(87), boardSpaces.get(97));
		game.move(blueMarshal.getType(), boardSpaces.get(4), boardSpaces.get(5));
		game.move(redMarshal.getType(), boardSpaces.get(97), boardSpaces.get(96));
		game.move(blueMarshal.getType(), boardSpaces.get(5), boardSpaces.get(15));
		game.move(redMarshal.getType(), boardSpaces.get(96), boardSpaces.get(95));
		game.move(blueMarshal.getType(), boardSpaces.get(15), boardSpaces.get(25));
		game.move(redMarshal.getType(), boardSpaces.get(95), boardSpaces.get(94));
		game.move(blueMarshal.getType(), boardSpaces.get(25), boardSpaces.get(35));
		game.move(redMarshal.getType(), boardSpaces.get(94), boardSpaces.get(93));
		game.move(blueMarshal.getType(), boardSpaces.get(35), boardSpaces.get(36));
		game.move(redMarshal.getType(), boardSpaces.get(93), boardSpaces.get(83));
		game.move(blueMarshal.getType(), boardSpaces.get(36), boardSpaces.get(26));
		game.move(redMarshal.getType(), boardSpaces.get(83), boardSpaces.get(73));
		game.move(blueMarshal.getType(), boardSpaces.get(26), boardSpaces.get(16));
		game.move(redMarshal.getType(), boardSpaces.get(73), boardSpaces.get(72));
		game.move(blueMarshal.getType(), boardSpaces.get(16), boardSpaces.get(6));
		game.move(redMarshal.getType(), boardSpaces.get(72), boardSpaces.get(62));
		game.move(blueMarshal.getType(), boardSpaces.get(6), boardSpaces.get(7));
		game.move(redMarshal.getType(), boardSpaces.get(62), boardSpaces.get(61));
		game.move(blueMarshal.getType(), boardSpaces.get(7), boardSpaces.get(17));
		game.move(redMarshal.getType(), boardSpaces.get(61), boardSpaces.get(71));
		game.move(blueMarshal.getType(), boardSpaces.get(17), boardSpaces.get(18));
		game.move(redMarshal.getType(), boardSpaces.get(71), boardSpaces.get(81));
		game.move(blueMarshal.getType(), boardSpaces.get(18), boardSpaces.get(8));
		game.move(redMarshal.getType(), boardSpaces.get(81), boardSpaces.get(82));
		game.move(blueMarshal.getType(), boardSpaces.get(8), boardSpaces.get(9));
		game.move(redMarshal.getType(), boardSpaces.get(82), boardSpaces.get(92));
		game.move(blueMarshal.getType(), boardSpaces.get(9), boardSpaces.get(19));
		game.move(redMarshal.getType(), boardSpaces.get(92), boardSpaces.get(91));
		game.move(blueMarshal.getType(), boardSpaces.get(19), boardSpaces.get(29));
		game.move(redMarshal.getType(), boardSpaces.get(91), boardSpaces.get(90));
		game.move(blueMarshal.getType(), boardSpaces.get(29), boardSpaces.get(28));
		game.move(redMarshal.getType(), boardSpaces.get(90), boardSpaces.get(80));
		game.move(blueMarshal.getType(), boardSpaces.get(28), boardSpaces.get(38));
		
		game.move(redMarshal.getType(), boardSpaces.get(80), boardSpaces.get(81));
		game.move(blueMarshal.getType(), boardSpaces.get(38), boardSpaces.get(48));
		game.move(redMarshal.getType(), boardSpaces.get(81), boardSpaces.get(71));
		game.move(blueMarshal.getType(), boardSpaces.get(48), boardSpaces.get(58));
		game.move(redMarshal.getType(), boardSpaces.get(71), boardSpaces.get(72));
		game.move(blueMarshal.getType(), boardSpaces.get(58), boardSpaces.get(59));
		game.move(redMarshal.getType(), boardSpaces.get(72), boardSpaces.get(73));
		game.move(blueMarshal.getType(), boardSpaces.get(59), boardSpaces.get(69));
		game.move(redMarshal.getType(), boardSpaces.get(73), boardSpaces.get(74));
		game.move(blueMarshal.getType(), boardSpaces.get(69), boardSpaces.get(79));
		game.move(redMarshal.getType(), boardSpaces.get(74), boardSpaces.get(75));
		game.move(blueMarshal.getType(), boardSpaces.get(79), boardSpaces.get(78));
		game.move(redMarshal.getType(), boardSpaces.get(75), boardSpaces.get(76));
		game.move(blueMarshal.getType(), boardSpaces.get(78), boardSpaces.get(77));
		MoveResult result = game.move(redMarshal.getType(), boardSpaces.get(76), boardSpaces.get(77));
		
		assertEquals(MoveResultStatus.DRAW, result.getStatus());
	}
}
