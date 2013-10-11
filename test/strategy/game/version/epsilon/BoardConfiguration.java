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

import java.util.ArrayList;

import strategy.common.PlayerColor;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class BoardConfiguration {
	private static ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private static ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();

	/**
	 * fill configurations for epsilon
	 */
	public static void setup(){
		redConfiguration.clear();
		blueConfiguration.clear();
		
		final PieceLocationDescriptor[] redPieces = {
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(0,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(1,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(2,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(3,0)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.RED)), new Location2D(4,0)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.RED)), new Location2D(5,0)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.RED)), new Location2D(6,0)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.RED)), new Location2D(7,0)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.RED)), new Location2D(8,0)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.RED)), new Location2D(9,0)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.RED)), new Location2D(0,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(1,1)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(2,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(3,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(4,1)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.RED)), new Location2D(5,1)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(6,1)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(7,1)),
				//TODO
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(8,1)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.RED)), new Location2D(9,1)),
				
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(0,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(1,2)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(2,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(3,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(4,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(5,2)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.RED)), new Location2D(6,2)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(7,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(8,2)),
				new PieceLocationDescriptor((new Piece(PieceType.GENERAL, PlayerColor.RED)), new Location2D(9,2)),
				new PieceLocationDescriptor((new Piece(PieceType.SPY, PlayerColor.RED)), new Location2D(0,3)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(1,3)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(2,3)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.RED)), new Location2D(3,3)),
				new PieceLocationDescriptor((new Piece(PieceType.MARSHAL, PlayerColor.RED)), new Location2D(4,3)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.RED)), new Location2D(5,3)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.RED)), new Location2D(6,3)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.RED)), new Location2D(7,3)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.RED)), new Location2D(8,3)),
				new PieceLocationDescriptor((new Piece(PieceType.FLAG, PlayerColor.RED)), new Location2D(9,3))};

		final PieceLocationDescriptor[] bluePieces = {
				new PieceLocationDescriptor((new Piece(PieceType.MARSHAL, PlayerColor.BLUE)), new Location2D(0,6)),
				new PieceLocationDescriptor((new Piece(PieceType.GENERAL, PlayerColor.BLUE)), new Location2D(1,6)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(2,6)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(3,6)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.BLUE)), new Location2D(4,6)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(5,6)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(6,6)),
				new PieceLocationDescriptor((new Piece(PieceType.SPY, PlayerColor.BLUE)), new Location2D(7,6)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(8,6)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(9,6)),
				new PieceLocationDescriptor((new Piece(PieceType.FLAG, PlayerColor.BLUE)), new Location2D(0,7)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.BLUE)), new Location2D(1,7)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(2,7)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.BLUE)), new Location2D(3,7)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(4,7)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.BLUE)), new Location2D(5,7)),
				new PieceLocationDescriptor((new Piece(PieceType.MINER, PlayerColor.BLUE)), new Location2D(6,7)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(7,7)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(8,7)),
				new PieceLocationDescriptor((new Piece(PieceType.SCOUT, PlayerColor.BLUE)), new Location2D(9,7)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(0,8)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(1,8)),
				//TODO
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(2,8)),
				new PieceLocationDescriptor((new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE)), new Location2D(3,8)),
				
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(4,8)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(5,8)),
				new PieceLocationDescriptor((new Piece(PieceType.BOMB, PlayerColor.BLUE)), new Location2D(6,8)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(7,8)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(8,8)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(9,8)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.BLUE)), new Location2D(0,9)),
				new PieceLocationDescriptor((new Piece(PieceType.COLONEL, PlayerColor.BLUE)), new Location2D(1,9)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(2,9)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(3,9)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(4,9)),
				new PieceLocationDescriptor((new Piece(PieceType.CAPTAIN, PlayerColor.BLUE)), new Location2D(5,9)),
				new PieceLocationDescriptor((new Piece(PieceType.SERGEANT, PlayerColor.BLUE)), new Location2D(6,9)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.BLUE)), new Location2D(7,9)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.BLUE)), new Location2D(8,9)),
				new PieceLocationDescriptor((new Piece(PieceType.MAJOR, PlayerColor.BLUE)), new Location2D(9,9))};

		/*
		 * The board with the initial configuration looks like this:
		 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 9 | COL | COL | CPT | CPT | CPT | CPT | SGT | MAJ | MAJ |MAJ  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 8 | LT  | LT  | LT  | LT  |  B  |  B  |  B  | SGT | SGT | SGT |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 7 |  F  | MIN | SCT | MIN | SCT | MIN | MIN | SCT | SCT | SCT |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 6 | MAR | GEN | SCT |  B  | MIN |  B  | SCT | SPY |  B  | SCT |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 5 |     |     | CP  | CP  |     |     | CP  | CP  |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 4 |     |     | CP  | CP  |     |     | CP  | CP  |     |     |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 3 | SPY | SCT |  B  | MIN | MAR | SCT | MIN |  B  | MIN |  F  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 2 | SCT | SCT |  B  | SCT | SCT | SCT | MIN |  B  | SCT | GEN |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 1 | MIN | SGT |  B  | SGT | SGT | SGT | 1LT | LT  | LT  | LT  |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 * 0 |  B  | CPT | CPT | CPT | CPT | MAJ | MAJ | MAJ | COL | COL |
		 * - +-----+-----+-----+-----+-----+-----+-----+-----+-----+-----+
		 *   |  0  |  1  |  2  |  3  |  4  |  5  |  6  |  7  |  8  |  9  |
		 */

		for (PieceLocationDescriptor piece : redPieces) {
			redConfiguration.add(piece);
		}
		for (PieceLocationDescriptor piece : bluePieces) {
			blueConfiguration.add(piece);		
		}
	}
	
	/**
	 * get red configuration
	 * @return redConfiguration
	 */
	public static ArrayList<PieceLocationDescriptor> getRedConfiguration(){
		return redConfiguration;
	}
	
	/**
	 * get blue configuration
	 * @return blueConfiguration
	 */
	public static ArrayList<PieceLocationDescriptor> getBlueConfiguration(){
		return blueConfiguration;
	}
}
