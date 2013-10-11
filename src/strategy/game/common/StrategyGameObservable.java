/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.common;

/**
 * Used when a reporter is requested
 * for a game
 * @author ctmanuel, dmlarose
 */
public interface StrategyGameObservable {
	
	/**
	 * Registers an observer
	 * @param observer observer to be registered
	 */
	void register(StrategyGameObserver observer); 

	/**
	 * Removes an observer
	 * @param observer observer to be removed
	 */
	void unregister(StrategyGameObserver observer); 
} 
