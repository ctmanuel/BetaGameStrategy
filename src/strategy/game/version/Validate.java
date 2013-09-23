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

import java.util.Collection;
import java.util.Map;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * The Validate interface must be implemented by the 
 * version controller in any Strategy variant.
 * (beginning with Gamma)
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */

public interface Validate {

	/**
	 * Validate the starting configuration
	 * @param configuration the configuration to validate
	 * @param lowRow the lowest row of the player's starting area
	 * @param highRow the highest row of the player's starting area
	 * @throws StrategyException if the configuration is invalid
	 */
	void validateConfiguration(Collection<PieceLocationDescriptor> configuration, int lowRow, int highRow) throws StrategyException;

	/**
	 * Iterate through the pieces in the configuration and validate their initial
	 * characteristics (such as the location correctness, whether the location is
	 * already occupied, and so on).
	 * @param configuration the configuration to iterate over
	 * @param lowRow the lowest row of the player's starting area
	 * @param highRow the highest row of the player's starting area
	 * @return piecesUsed a map of the pieces that were used in the initial configuration and their distribution
	 */
	Map<PieceType, Integer> validateEachPiece(Collection<PieceLocationDescriptor> configuration, int lowRow, int highRow);

	/**
	 * Validate that the location is not out of bounds
	 * @param location
	 * @throws StrategyException 
	 */
	void validateOutOfBounds(Location location) throws StrategyException;

	/**
	 * Check that the move is valid
	 * if the move is not:
	 *   X, Y +/- 1
	 *   X +/- 1, Y
	 * from current location, then throw an exception
	 * @param from the location moving from
	 * @param to the location moving to
	 * @throws StrategyException
	 */
	void validateCrossMove(Location from, Location to) throws StrategyException;

	/**
	 * Checks to see if the piece is trying to make a 
	 * diagonal move. If so, throw an exception.
	 * @param currentXcoordinate
	 * @param currentYcoordinate
	 * @param toXcoordinate
	 * @param toYcoordinate
	 * @throws StrategyException
	 */
	void validateDiagonalMove(int currentXcoordinate, int currentYcoordinate, 
			int toXcoordinate, int toYcoordinate) throws StrategyException;
	
	/**
	 * After all of the pieces have been placed on the board, make sure that
	 * there are the correct number of each type of piece.
	 * @param piecesUsed a Map containing the pieces that have been placed
	 * 		from the configuration
	 * @throws StrategyException if there is an incorrect distribution of pieces
	 */
	void checkPieceDistribution(Map<PieceType, Integer> piecesUsed)
			throws StrategyException;
}
