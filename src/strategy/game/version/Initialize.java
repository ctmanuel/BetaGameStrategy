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

/**
 * The Initialize interface must be implemented by the 
 * version controller in any Strategy variant.
 * (beginning with Gamma)
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */
public interface Initialize {
	
	/**
	 * Initialize the each piece's rank
	 */
	void initializePieceRanks();
	
	/**
	 * Initialize the pieces required for the game
	 */
	void fillInitialPieces();
}
