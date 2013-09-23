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
	public void validateConfiguration(Collection<PieceLocationDescriptor> configuration, int lowRow, int highRow) throws StrategyException;

	/**
	 * Iterate through the pieces in the configuration and validate their initial
	 * characteristics (such as the location correctness, whether the location is
	 * already occupied, and so on).
	 * @param configuration the configuration to iterate over
	 * @param lowRow the lowest row of the player's starting area
	 * @param highRow the highest row of the player's starting area
	 * @return piecesUsed a map of the pieces that were used in the initial configuration and their distribution
	 * @throws StrategyException
	 */
	public Map<PieceType, Integer> validateEachPiece(Collection<PieceLocationDescriptor> configuration, int lowRow, int highRow);

	/**
	 * Validate that the location is not out of bounds
	 * @param location
	 * @throws StrategyException 
	 */
	public void validateOutOfBounds(Location location) throws StrategyException;

	/**
	 * Check that the move is valid
	 * if the move is not:
	 *   X, Y +/- 1
	 *   X +/- 1, Y
	 * from current location, then throw an exception
	 * @param currentCoordinate
	 * @param toCoordinate
	 * @throws StrategyException
	 */
	public void validateCrossMove(Location from, Location to) throws StrategyException;

	/**
	 * Checks to see if the piece is trying to make a 
	 * diagonal move. If so, throw an exception.
	 * @param currentXcoordinate
	 * @param currentYcoordinate
	 * @param toXcoordinate
	 * @param toYcoordinate
	 * @throws StrategyException
	 */
	public void validateDiagonalMove(int currentXcoordinate, int currentYcoordinate, 
			int toXcoordinate, int toYcoordinate) throws StrategyException;
	
	/**
	 * After all of the pieces have been placed on the board, make sure that
	 * there are the correct number of each type of piece.
	 * @param piecesUsed a Map containing the pieces that have been placed
	 * 		from the configuration
	 * @throws StrategyException if there is an incorrect distribution of pieces
	 */
	public void checkPieceDistribution(final Map<PieceType, Integer> piecesUsed)
			throws StrategyException;
}
