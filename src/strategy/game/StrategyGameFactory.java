/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game;

import java.util.Collection;
import java.util.Iterator;

import strategy.common.StrategyException;
import strategy.common.StrategyRuntimeException;
import strategy.game.common.Coordinate;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.version.alpha.AlphaStrategyGameController;
import strategy.game.version.beta.BetaStrategyGameController;
import strategy.game.version.gamma.GammaStrategyGameController;

/**
 * <p>
 * Factory to produce various versions of the Strategy game. This is implemented
 * as a singleton.
 * </p><p>
 * NOTE: If an error occurs creating any game, that is not specified in the particular
 * factory method's documentation, the factory method should throw a 
 * StrategyRuntimeException.
 * </p>
 * 
 * @author gpollice
 * @version Sep 10, 2013
 */
public class StrategyGameFactory
{
	private static final StrategyGameFactory instance = new StrategyGameFactory();

	/**
	 * Default private constructor to ensure this is a singleton.
	 */
	private StrategyGameFactory()
	{
		// Intentionally left empty.
	}

	/**
	 * @return the instance
	 */
	public static StrategyGameFactory getInstance()
	{
		return instance;
	}

	/**
	 * Create an Alpha Strategy game.
	 * @return the created Alpha Strategy game
	 */
	public StrategyGameController makeAlphaStrategyGame()
	{
		final StrategyGameController AlphaGame = new AlphaStrategyGameController();

		if(AlphaGame.getClass() != AlphaStrategyGameController.class) {
			throw new StrategyRuntimeException("Change this implementation");
		}

		return AlphaGame;
	}

	/**
	 * Create a new Beta Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Beta Strategy game instance with the initial configuration of pieces
	 * @throws StrategyException if either configuration is correct
	 */
	public StrategyGameController makeBetaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
					throws StrategyException
	{
		//check configurations aren't null
		if (redConfiguration == null || blueConfiguration == null) {
			throw new StrategyException("Null configurations");
		}
		//check configurations have 12 items
		if (redConfiguration.size() != 12 || blueConfiguration.size() != 12) {
			throw new StrategyException("Incorrect configuration size.");
		}

		//check if pieces are placed out of bounds
		final Iterator<PieceLocationDescriptor> redIterator = redConfiguration.iterator();
		final Iterator<PieceLocationDescriptor> blueIterator = blueConfiguration.iterator();

		PieceLocationDescriptor currentRedIterPiece = null;
		PieceLocationDescriptor currentBlueIterPiece = null;
		int currentRedX;
		int currentRedY;
		int currentBlueX;
		int currentBlueY;

		while (redIterator.hasNext()) {
			currentRedIterPiece = redIterator.next();
			
			currentRedX = currentRedIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
			currentRedY = currentRedIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);

			if(currentRedX > 5 || currentRedX < 0) {
				throw new StrategyException("X Coordinate out of bounds");
			}
			if(currentRedY > 1 || currentRedY < 0) {
				throw new StrategyException("Y Coordinate out of bounds");
			}
		}
		while (blueIterator.hasNext()) {
			currentBlueIterPiece = blueIterator.next();

			currentBlueX = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
			currentBlueY = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);

			if(currentBlueX > 5 || currentBlueX < 0) {
				throw new StrategyException("X Coordinate out of bounds");
			}
			if(currentBlueY > 5 || currentBlueY < 4) {
				throw new StrategyException("Y Coordinate out of bounds");
			}
		}
		
		final StrategyGameController BetaGame = new BetaStrategyGameController(redConfiguration, blueConfiguration);

		if(BetaGame.getClass() != BetaStrategyGameController.class) {
			throw new StrategyRuntimeException("Change this implementation");
		}

		return BetaGame;
	}
	
	/**
	 * Create a new Gamma Strategy game given the 
	 * @param redConfiguration the initial starting configuration for the RED pieces
	 * @param blueConfiguration the initial starting configuration for the BLUE pieces
	 * @return the Gamma Strategy game instance with the initial configuration of pieces
	 * @throws StrategyException if either configuration is correct
	 */
	public StrategyGameController makeGammaStrategyGame(
			Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
					throws StrategyException
	{
		final StrategyGameController GammaGame = new GammaStrategyGameController(redConfiguration, blueConfiguration);

		if(GammaGame.getClass() != GammaStrategyGameController.class) {
			throw new StrategyRuntimeException("Change this implementation");
		}
		
		return GammaGame;
	}
	
}