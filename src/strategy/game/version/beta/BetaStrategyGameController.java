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
//import java.util.Iterator;

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
	private PieceLocationDescriptor tempDescriptor = null;
	PlayerColor playerColor;

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
	// fix move result status
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
		//check which color turn it is
		playerColor = null;
		if (PlayerTurn == 0) {
			playerColor = PlayerColor.RED;
			PlayerTurn = 1;
		}
		else {
			playerColor = PlayerColor.BLUE;
			PlayerTurn = 0;
		}

		tempDescriptor = new PieceLocationDescriptor(new Piece(piece, playerColor), from);

		checkLocationCoordinates(to);
		final PieceLocationDescriptor newPiece =
				new PieceLocationDescriptor(new Piece(piece, playerColor), to);
		if (playerColor == PlayerColor.RED) {
			redConfiguration.remove(tempDescriptor);
			redConfiguration.add(newPiece);
		}
		else {
			blueConfiguration.remove(tempDescriptor);
			blueConfiguration.add(newPiece);
		}
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
	private void checkLocationCoordinates(Location to) 
			throws StrategyException
			{
		//if piece doesn't exsit within the either collection configuration
		if (!(redConfiguration.contains(tempDescriptor) || blueConfiguration.contains(tempDescriptor))) {
			throw new StrategyException(playerColor + "'s " + tempDescriptor.getPiece() + " at " 
					+ tempDescriptor.getLocation() +" doesn't exist in this configuration");
		}

		//check for valid x coordinate. If X to isn't X from +-1 or the same, throw exception
//		if (tempDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE) + 1 
//				!= to.getCoordinate(Coordinate.X_COORDINATE) ||
//				tempDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE) - 1 
//				!= to.getCoordinate(Coordinate.X_COORDINATE) ||
//				tempDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE) 
//				!= to.getCoordinate(Coordinate.X_COORDINATE)) {
//			throw new StrategyException("Invalid X coordinate move from " 
//					+ tempDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE) + " to " 
//					+ to.getCoordinate(Coordinate.X_COORDINATE));
//		}

//		if(!(to.getCoordinate(Coordinate.X_COORDINATE) >= 
//				tempDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE) + 1))
//		
//		//check for valid y coordinate. If Y to isn't Y from +-1 or the same, throw exception
//		if (//tempDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE) + 1 
//				//!= to.getCoordinate(Coordinate.Y_COORDINATE) ||
//				//tempDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE) - 1 
//				//!= to.getCoordinate(Coordinate.Y_COORDINATE) ||
//				tempDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE) 
//				!= to.getCoordinate(Coordinate.Y_COORDINATE)){
//			throw new StrategyException("Invalid Y coordinate move from " 
//					+ tempDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE) + " to " 
//					+ to.getCoordinate(Coordinate.Y_COORDINATE));
//		}


			}


	@Override
	public Piece getPieceAt(Location location) {
		// TODO Auto-generated method stub
		return null;
	}

}
