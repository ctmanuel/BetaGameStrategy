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

import java.util.HashMap;
import java.util.Map;

import strategy.game.common.PieceType;
import strategy.game.version.Initialize;

/**
 * InitializeGamma is used to initialize the
 *  pieces for the gamma version of strategy
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 *
 */
public class InitializeGamma implements Initialize{
	private final int FLAG_COUNT = 1;
	private final int MARSHAL_COUNT = 1;
	private final int COLONEL_COUNT = 2;
	private final int CAPTAIN_COUNT = 2;
	private final int LIEUTENANT_COUNT = 3;
	private final int SERGEANT_COUNT = 3;

	private final Map<PieceType, Integer> startingPieces;
	
	public InitializeGamma(){
		startingPieces = new HashMap<PieceType, Integer>();
		fillInitialPieces();
	}
	
	@Override
	public void initializePieceRanks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillInitialPieces() {
		startingPieces.put(PieceType.MARSHAL, MARSHAL_COUNT);
		startingPieces.put(PieceType.COLONEL, COLONEL_COUNT);
		startingPieces.put(PieceType.CAPTAIN, CAPTAIN_COUNT);
		startingPieces.put(PieceType.LIEUTENANT, LIEUTENANT_COUNT);
		startingPieces.put(PieceType.SERGEANT, SERGEANT_COUNT);
		startingPieces.put(PieceType.FLAG, FLAG_COUNT);
		
	}
	
	@Override
	public Map<PieceType, Integer> getStartingPieces(){
		return startingPieces;
	}

}
