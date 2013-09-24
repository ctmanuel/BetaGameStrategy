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

import java.util.ArrayList;
import java.util.List;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
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

	private static final List<PieceHistory> redQueue = new ArrayList<PieceHistory>();
	private static final List<PieceHistory> blueQueue = new ArrayList<PieceHistory>();
	Piece piece;
	Location from;
	Location to;
	
	public RepetitionRule() {
		redQueue.clear();
		blueQueue.clear();
	}

	/**
	 * check for violation of the repetition rule
	 * @param piece the current piece
	 * @param to the location the piece is moving to
	 * @throws StrategyException
	 */
	public static void checkRepRule(PieceLocationDescriptor piece, Location to) throws StrategyException {
		final PieceHistory currentHistory = new PieceHistory(piece.getPiece(), piece.getLocation(), to); 
		System.out.print("Player piece history: ");
		currentHistory.printHistory();
		if(piece.getPiece().getOwner() == PlayerColor.RED){
			for(int i = 0; i < redQueue.size(); i++){
				System.out.print("Red Queue piece history: ");
				redQueue.get(i).printHistory();
				if(redQueue.get(i).getFromLocation().equals(currentHistory.getFromLocation())
						&& redQueue.get(i).getPiece().equals(currentHistory.getPiece())
						&& redQueue.get(i).getToLocation().equals(currentHistory.getToLocation())){
					throw new StrategyException ("Illegal Repetition Move");
				}
			}
		}
		else{
			for(int i = 0; i < blueQueue.size(); i++){
				System.out.print("Blue Queue piece history: ");
				blueQueue.get(i).printHistory();
				if(blueQueue.get(i).getFromLocation().equals(currentHistory.getFromLocation())
						&& blueQueue.get(i).getPiece().equals(currentHistory.getPiece())
						&& blueQueue.get(i).getToLocation().equals(currentHistory.getToLocation())){
					throw new StrategyException ("Illegal Repetition Move");
				}
			}
		}
	}

	/**
	 * add the piece history to the list (queue)
	 * @param piece the piece to be added
	 * @param to the location it is moving to
	 */
	public static void addToQueue(PieceLocationDescriptor piece, Location to) {
		if (piece.getPiece().getOwner().equals(PlayerColor.RED)
				&& redQueue.size() < 2) {
			redQueue.add(new PieceHistory(piece.getPiece(), piece.getLocation(), to));
		}
		else if (piece.getPiece().getOwner().equals(PlayerColor.BLUE)
					&& blueQueue.size() < 2) {
			blueQueue.add(new PieceHistory(piece.getPiece(), piece.getLocation(), to));
		}
		else if (piece.getPiece().getOwner().equals(PlayerColor.RED)
				&& redQueue.size() == 2) {
			final PieceHistory tempHistory = redQueue.get(1);
			redQueue.remove(1);
			redQueue.remove(0);
			redQueue.add(tempHistory);
			redQueue.add(new PieceHistory(piece.getPiece(), piece.getLocation(), to));
		}
		else if (piece.getPiece().getOwner().equals(PlayerColor.BLUE)
				&& blueQueue.size() == 2) {
			final PieceHistory tempHistory = blueQueue.get(1);
			blueQueue.remove(1);
			blueQueue.remove(0);
			blueQueue.add(tempHistory);
			blueQueue.add(new PieceHistory(piece.getPiece(), piece.getLocation(), to));
		}
	}
}