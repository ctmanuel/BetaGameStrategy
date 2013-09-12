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
	private int playerTurn;
	private PieceLocationDescriptor currentPieceDescriptor = null;
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
		playerTurn = 0;
	}

	@Override
	//TODO:
	// fix checkCoordinates
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
		if (playerTurn == 0) {
			playerColor = PlayerColor.RED;
			playerTurn = 1;
		}
		else {
			playerColor = PlayerColor.BLUE;
			playerTurn = 0;
		}

		currentPieceDescriptor = new PieceLocationDescriptor(new Piece(piece, playerColor), from);

		checkLocationCoordinates(to);
		final PieceLocationDescriptor newPiece =
				new PieceLocationDescriptor(new Piece(piece, playerColor), to);

		if (playerColor == PlayerColor.RED) {
			redConfiguration.remove(currentPieceDescriptor);
			redConfiguration.add(newPiece);
		}
		else {
			blueConfiguration.remove(currentPieceDescriptor);
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
	private void checkLocationCoordinates(Location to) throws StrategyException {
		//local variables
		int currentXcoordinate = currentPieceDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE);
		int currentYcoordinate = currentPieceDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
		int toXcoordinate = to.getCoordinate(Coordinate.X_COORDINATE);
		int toYcoordinate = to.getCoordinate(Coordinate.Y_COORDINATE);

		//if piece doesn't exist within the either collection configuration
		if (!(redConfiguration.contains(currentPieceDescriptor) || blueConfiguration.contains(currentPieceDescriptor))) {
			throw new StrategyException(playerColor + "'s " + currentPieceDescriptor.getPiece() + " at " 
					+ currentPieceDescriptor.getLocation() +" doesn't exist in this configuration");
		}

		//check if out of bounds
		if(toXcoordinate > 5 || toXcoordinate < 0) {
			throw new StrategyException("X Coordinate out of bounds");
		}
		if(toYcoordinate > 5 || toYcoordinate < 0) {
			throw new StrategyException("Y Coordinate out of bounds");
		}

		//check for valid X coordinate. If X to isn't X from +/- 1 or the same, throw exception
		if (currentXcoordinate + 1 != toXcoordinate
				&& currentXcoordinate - 1 != toXcoordinate
				&& currentXcoordinate != toXcoordinate) {
			throw new StrategyException("Invalid X coordinate move from " 
					+ currentXcoordinate + " to " + toXcoordinate);
		}

		//check for valid Y coordinate. If Y to isn't Y from +/- 1 or the same, throw exception
		if (currentYcoordinate + 1 != toYcoordinate
				&& currentYcoordinate - 1 != toYcoordinate
				&& currentYcoordinate != toYcoordinate) {
			throw new StrategyException("Invalid Y coordinate move from " 
					+ currentYcoordinate + " to " + toYcoordinate);
		}

		//check for diagonals moves
		if(currentXcoordinate + 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate
				|| currentXcoordinate - 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate
				|| currentXcoordinate + 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate
				|| currentXcoordinate - 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate) {
			throw new StrategyException("Illegal Diagonal move");
		}
		
		//check if occupied
		final Piece temp = getPieceAt(to);
		if (temp != null) {
			//check if the pieces are the same color
			if (currentPieceDescriptor.getPiece().getOwner() == temp.getOwner()) {
				throw new StrategyException("Space is occupied by same color piece");
			}
			
			//go to battle method
			//battle(currentPieceDescriptor.getPiece(), )
		}
	}

	@Override
	public Piece getPieceAt(Location location) {
		final Iterator<PieceLocationDescriptor> redIterator = redConfiguration.iterator();
		final Iterator<PieceLocationDescriptor> blueIterator = blueConfiguration.iterator();

		PieceLocationDescriptor currentRedIterPiece = null;
		PieceLocationDescriptor currentBlueIterPiece = null;
		Piece pieceAtLocation = null;

		while (blueIterator.hasNext()) {
			currentRedIterPiece = redIterator.next();
			currentBlueIterPiece = blueIterator.next();
			if (currentRedIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE)
					== location.getCoordinate(Coordinate.X_COORDINATE)
				&& currentRedIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE)
					== location.getCoordinate(Coordinate.Y_COORDINATE)) {
				pieceAtLocation = currentRedIterPiece.getPiece();
			}
			if (currentBlueIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE)
					== location.getCoordinate(Coordinate.X_COORDINATE)
				&& currentBlueIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE)
					== location.getCoordinate(Coordinate.Y_COORDINATE)) {
				pieceAtLocation = currentBlueIterPiece.getPiece();
			}
		}
		return pieceAtLocation;
	}
	
	/**
	 * EPIC BATTLE FIGHT TO THE DEATH
	 * @param playerPiece current player's piece
	 * @param opponentPiece opponent's piece
	 * @return status of winner
	 */
	private MoveResultStatus battle(Piece playerPiece, PieceLocationDescriptor opponentPiece){
		return null;
	}
}
