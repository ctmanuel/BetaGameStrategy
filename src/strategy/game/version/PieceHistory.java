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

/**
 * Stores a piece and the history of the locations its been
 * of the repetition rule
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */
public class PieceHistory {

	private final Piece piece;
	private final Location from;
	private final Location to;
	
	/**
	 * the PieceHistory constructor keeps track of
	 *  the current player's piece, from location 
	 *  and to location
	 * @param piece the current piece
	 * @param from the location the piece is currently at
	 * @param to the location the piece is moving to
	 */
	public PieceHistory(Piece piece, Location from, Location to){
		this.from = from;
		this.piece = piece;
		this.to = to;
	}
	
	public Piece getPiece(){
		return piece;
	}
	
	public Location getFromLocation(){
		return from;
	}
	
	public Location getToLocation(){
		return to;
	}
	
}
