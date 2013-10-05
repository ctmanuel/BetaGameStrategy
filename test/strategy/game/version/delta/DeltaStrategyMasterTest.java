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

import static org.junit.Assert.assertNotNull;

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


public class DeltaStrategyMasterTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory DeltaStrategy = StrategyGameFactory.getInstance();

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
		
		/*
		 * The board with the initial configuration looks like this:
		 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 9 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 8 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 7 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 6 | MAR |  F  |     |     |     |  B  |     |     |  B  |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 5 |     |     | CP  | CP  |     |     | CP  | CP  |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 4 |     |     | CP  | CP  |     |     | CP  | CP  |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 3 | SPY |     |     |     | SCT | MAR |     |     | MIN |  F  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 2 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 1 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 0 |     |     |     |     |     |     |     |     |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
		 */
		
		MAJ
		COL
		CPT
		LT
		GEN
		MIN
		SCT
		SPY
		B

		for (PieceLocationDescriptor piece : redPieces) {
			redConfiguration.add(piece);
		}
		for (PieceLocationDescriptor piece : bluePieces) {
			blueConfiguration.add(piece);		
		}

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

}
