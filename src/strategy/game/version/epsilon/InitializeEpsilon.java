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

import java.util.HashMap;
import java.util.Map;

import strategy.game.common.PieceType;
import strategy.game.version.Initialize;

/**
 * InitializeEpsilon is used to initialize the
 *  pieces for the gamma version of strategy
 * @author dmlarose, ctmanuel
 * @version Oct 08, 2013
 *
 */
public class InitializeEpsilon implements Initialize {
	
	private final int FLAG_COUNT = 1;
	private final int MARSHAL_COUNT = 1;
	private final int GENERAL_COUNT = 1;
	private final int COLONEL_COUNT = 2;
	private final int MAJOR_COUNT = 3;
	private final int CAPTAIN_COUNT = 4;
	private final int LIEUTENANT_COUNT = 4;
	private final int SERGEANT_COUNT = 4;
	private final int MINER_COUNT = 5;
	private final int SCOUT_COUNT = 8;
	private final int SPY_COUNT = 1;
	private final int BOMB_COUNT = 6;
	
	private final Map<PieceType, Integer> startingPieces;
	
	public InitializeEpsilon(){
		startingPieces = new HashMap<PieceType, Integer>();
		fillInitialPieces();
	}

	@Override
	public void fillInitialPieces() {
		startingPieces.put(PieceType.MARSHAL, MARSHAL_COUNT);
		startingPieces.put(PieceType.GENERAL, GENERAL_COUNT);
		startingPieces.put(PieceType.COLONEL, COLONEL_COUNT);
		startingPieces.put(PieceType.MAJOR, MAJOR_COUNT);
		startingPieces.put(PieceType.CAPTAIN, CAPTAIN_COUNT);
		startingPieces.put(PieceType.LIEUTENANT, LIEUTENANT_COUNT);
		startingPieces.put(PieceType.SERGEANT, SERGEANT_COUNT);
		startingPieces.put(PieceType.MINER, MINER_COUNT);
		startingPieces.put(PieceType.SCOUT, SCOUT_COUNT);
		startingPieces.put(PieceType.SPY, SPY_COUNT);
		startingPieces.put(PieceType.BOMB, BOMB_COUNT);
		startingPieces.put(PieceType.FLAG, FLAG_COUNT);
	}
	
	@Override
	public Map<PieceType, Integer> getStartingPieces(){
		return startingPieces;
	}
}
