package strategy.game.version.gamma;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.Validate;

/**
 * The Validate class must implement the Validate
 * interface in any Strategy variant.
 * (beginning with Gamma)
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */

public class ValidateGamma implements Validate{

	InitializeGamma initializeGamma = new InitializeGamma();

	@Override
	public void validateConfiguration(
			Collection<PieceLocationDescriptor> configuration, int lowRow,
			int highRow) throws StrategyException {
		//check for null configurations
		if (configuration == null) {
			throw new StrategyException("Null configuration");
		}
		//check configurations have 12 items
		if (configuration.size() != 12) {
			throw new StrategyException("Incorrect configuration size.");
		}

		//check if pieces are placed out of bounds
		final Iterator<PieceLocationDescriptor> configIterator = configuration.iterator();

		PieceLocationDescriptor currentConfigIterPiece = null;
		int currentY;

		while (configIterator.hasNext()) {
			currentConfigIterPiece = configIterator.next();

			currentY = currentConfigIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);

			if(currentY > highRow || currentY < lowRow) {
				throw new StrategyException("Piece out of starting configuration bounds");
			}

			//check out of bounds
			validateOutOfBounds(currentConfigIterPiece.getLocation());
		}

		//check piece distribution
		final Map<PieceType, Integer> piecesUsed = 
				validateEachPiece(configuration, lowRow, highRow);

		checkPieceDistribution(piecesUsed);

	}

	@Override
	public Map<PieceType, Integer> validateEachPiece(
			Collection<PieceLocationDescriptor> configuration, int lowRow,
			int highRow) {
		final Map<PieceType, Integer> piecesUsed = new HashMap<PieceType, Integer>();
		for (PieceLocationDescriptor pld : configuration) {
			final Piece piece = pld.getPiece();
			final Integer nUsed = piecesUsed.get(piece.getType());
			piecesUsed.put(piece.getType(), nUsed == null ? 1 : nUsed.intValue() + 1);
		}
		return piecesUsed;
	}

	@Override
	public void validateOutOfBounds(Location location) throws StrategyException {
		final int coordinateX = location.getCoordinate(Coordinate.X_COORDINATE);
		final int coordinateY = location.getCoordinate(Coordinate.Y_COORDINATE);

		if(coordinateX > 5 || coordinateX < 0) {
			throw new StrategyException("X Coordinate out of bounds");
		}
		if(coordinateY > 5 || coordinateY < 0) {
			throw new StrategyException("Y Coordinate out of bounds");
		}

	}

	@Override
	public void validateCrossMove(Location from, Location to)
			throws StrategyException {
		if (from.distanceTo(to) != 1) {
			throw new StrategyException("Invalid coordinate move from " 
					+ from + " to " + to);
		}
	}

	@Override
	public void validateDiagonalMove(int currentXcoordinate,
			int currentYcoordinate, int toXcoordinate, int toYcoordinate)
					throws StrategyException {
		if((currentXcoordinate + 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate)
				|| (currentXcoordinate - 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate)
				|| (currentXcoordinate + 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate)
				|| (currentXcoordinate - 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate)) {
			throw new StrategyException("Illegal Diagonal move");
		}

	}

	@Override
	public void checkPieceDistribution(Map<PieceType, Integer> piecesUsed)
			throws StrategyException {
		for (PieceType pt : initializeGamma.getStartingPieces().keySet()) {
			int required = initializeGamma.getStartingPieces().get(pt).intValue();
			Integer used = piecesUsed.get(pt);
			if (used == null || used.intValue() != required) {
				throw new StrategyException("Invalid number of " + pt);
			}
		}
	}


}