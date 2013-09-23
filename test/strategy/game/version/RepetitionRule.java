/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version;

import strategy.game.common.Location;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;

/**
 * Repetition Rule deals with the implementation
 * of the repetition rule
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */

public class RepetitionRule {

	PieceLocationDescriptor[][] redQueue;
	PieceLocationDescriptor[][] blueQueue;
	Piece piece;
	Location from;
	Location to;
	
	public RepetitionRule(Piece piece, Location from, Location to){
		blueQueue = new PieceLocationDescriptor[2][2];
		redQueue = new PieceLocationDescriptor[2][2];
		this.piece = piece;
		this.from = from;
		this.to = to;
	}
	
	// queue(list(pldfrom, pldto), list(pld2from, pld2to))
	
	/**
	 * 
	 */
	public static void checkRepRule() {
		
	}
	
	/**
	 * 
	 * @param pieceLD
	 */
	private void addToQueue(PieceLocationDescriptor pieceLD) {
		
	}
}
