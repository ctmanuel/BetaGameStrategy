/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.beta;

import java.util.Collection;
import java.util.Iterator;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class BetaStrategyGameController implements StrategyGameController {
	
	private boolean gameStarted;
	private boolean gameOver;
	private int PlayerTurn;
	
	private Collection<PieceLocationDescriptor> redConfiguration;
	private Collection<PieceLocationDescriptor> blueConfiguration;

	public BetaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
									  Collection<PieceLocationDescriptor> blueConfiguration)
	{
		gameStarted = false;
		gameOver = false;
		
		this.redConfiguration = redConfiguration;
		this.blueConfiguration = blueConfiguration;
	}
	
	@Override
	public void startGame() throws StrategyException {
		gameStarted = true;
		gameOver = false;
		PlayerTurn = 0;
	}

	@Override
	//TODO:
	// fix chechCoordinates
	// check piece color
	// 
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		if (gameOver) {
			throw new StrategyException("The game is over, you cannot make a move");
		}
		if (!gameStarted) {
			throw new StrategyException("You must start the game!");
		}
		if (!(piece == PieceType.MARSHAL 
				|| piece == PieceType.LIEUTENANT
				|| piece == PieceType.COLONEL
				|| piece == PieceType.CAPTAIN
				|| piece == PieceType.SERGEANT)) {
			throw new StrategyException(piece + " is not a valid piece for the Beta Strategy.");
		}
		PlayerColor playerColor = null;
		if (PlayerTurn == 0) {
			playerColor = PlayerColor.RED;
			PlayerTurn = 1;
		}
		else {
			playerColor = PlayerColor.BLUE;
			PlayerTurn = 0;
		}
		
		//TODO
		checkLocationCoordinates(from, 0, 1);
		checkLocationCoordinates(to, 0, 2);
		final PieceLocationDescriptor newPiece =
				new PieceLocationDescriptor(new Piece(piece, playerColor), to);
		return new MoveResult(MoveResultStatus.OK, newPiece);
	}
	
	/**
	 * Check a location for validity. Throws an exception if the coordinates
	 * are not equal to the expected value.
	 * @param location the location to check
	 * @param x the expected x-coordinate
	 * @param y the expected y-coordinate
	 * @throws StrategyException if the location's coordinates do not match
	 * 		the expected values.
	 */
	private void checkLocationCoordinates(Location location, int x, int y) 
			throws StrategyException
	{
		final int locationX = location.getCoordinate(Coordinate.X_COORDINATE);
		final int locationY = location.getCoordinate(Coordinate.Y_COORDINATE);
		if (x != locationX || y != locationY) {
			throw new StrategyException(
					"Expected (" + x + ',' + y + ") and received ("
							+ locationX + ',' + locationY + ')');
		}
	}


	@Override
	public Piece getPieceAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public Iterator<PieceLocationDescriptor> getPieceLocation()
	{
		Iterator<PieceLocationDescriptor> iter = redConfiguration.iterator();
		return iter;
	}
}
