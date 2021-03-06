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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

/**
 * The BetaStrategyGameController implements the game core for
 * the Beta Strategy version.
 * @author dmlarose, ctmanuel
 * @version Sep 13, 2013
 */
public class BetaStrategyGameController implements StrategyGameController {

	private boolean gameStarted;
	private static boolean gameOver = false;
	private int playerTurn;
	private PieceLocationDescriptor currentPieceDescriptor = null;
	PlayerColor playerColor;
	private MoveResult battleResult;
	private int moveCounter;

	private final Collection<PieceLocationDescriptor> origionalredConfiguration;
	private final Collection<PieceLocationDescriptor> origionalblueConfiguration;
	private static Collection<PieceLocationDescriptor> redConfiguration = null;
	private static Collection<PieceLocationDescriptor> blueConfiguration = null;

	private static final List<PieceType> MarshalBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> ColonelBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> CaptainBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> LieutenantBeatsThese = new ArrayList<PieceType>();

	/**
	 * Constructor for the Beta Game Strategy
	 * @param redConfiguration the list of red pieces
	 * @param blueConfiguration the list of blue pieces
	 */
	public BetaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
	{
		gameStarted = false;
		gameOver = false;

		origionalblueConfiguration = blueConfiguration;
		origionalredConfiguration = redConfiguration;
		fillBattleLists();
	}

	/*
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() {
		gameStarted = true;
		gameOver = false;
		playerTurn = 0;
		moveCounter = 0;
		redConfiguration = new ArrayList<PieceLocationDescriptor>(origionalredConfiguration);
		blueConfiguration = new ArrayList<PieceLocationDescriptor>(origionalblueConfiguration);
	}

	/*
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {
		
		moveCounter += 1;
		
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
				|| piece == PieceType.SERGEANT
				|| piece == PieceType.FLAG)) {
			throw new StrategyException(piece + " is not a valid piece for the Beta Strategy.");
		}
		
		if(piece == PieceType.FLAG) {
			throw new StrategyException("Cannot move the Flag");
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

		//check location for valid location
		currentPieceDescriptor = new PieceLocationDescriptor(new Piece(piece, playerColor), from);
		checkLocationCoordinates(to);

		//check if occupied
		final Piece tempPieceAtTo = getPieceAt(to);
		if (tempPieceAtTo != null) {
			//check if the pieces are the same color
			if (currentPieceDescriptor.getPiece().getOwner() == tempPieceAtTo.getOwner()) {
				throw new StrategyException("Space is occupied by same color piece");
			}

			//go to battle method
			battleResult = battle(currentPieceDescriptor, new PieceLocationDescriptor(tempPieceAtTo, to));
			if(battleResult.getBattleWinner() == null) { 
				return battleResult;
			}
			if(playerColor == PlayerColor.RED){
				redConfiguration.remove(currentPieceDescriptor);
				redConfiguration.add(battleResult.getBattleWinner());
			}
			else{
				blueConfiguration.remove(currentPieceDescriptor);
				blueConfiguration.add(battleResult.getBattleWinner());
			}
			
			if (moveCounter == 12 && !gameOver) {
				gameOver = true;
				return new MoveResult(MoveResultStatus.DRAW, battleResult.getBattleWinner());
			}
			
			return battleResult;

		}
		
		//if no battle go here
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
		
		if (moveCounter == 12) {
			gameOver = true;
			return new MoveResult(MoveResultStatus.DRAW, newPiece);
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
		final int currentXcoordinate = currentPieceDescriptor.getLocation().getCoordinate(Coordinate.X_COORDINATE);
		final int currentYcoordinate = currentPieceDescriptor.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
		final int toXcoordinate = to.getCoordinate(Coordinate.X_COORDINATE);
		final int toYcoordinate = to.getCoordinate(Coordinate.Y_COORDINATE);

		//if piece doesn't exist within the either collection configuration
		if (!(redConfiguration.contains(currentPieceDescriptor) || blueConfiguration.contains(currentPieceDescriptor))) {
			throw new StrategyException(currentPieceDescriptor.getPiece() + " at " 
					+ currentPieceDescriptor.getLocation() + " doesn't exist in this configuration");
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
		if((currentXcoordinate + 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate)
				|| (currentXcoordinate - 1 == toXcoordinate && currentYcoordinate + 1 == toYcoordinate)
				|| (currentXcoordinate + 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate)
				|| (currentXcoordinate - 1 == toXcoordinate && currentYcoordinate - 1 == toYcoordinate)) {
			throw new StrategyException("Illegal Diagonal move");
		}
	}

	@Override
	public Piece getPieceAt(Location location) {
		final Iterator<PieceLocationDescriptor> redIterator = redConfiguration.iterator();
		final Iterator<PieceLocationDescriptor> blueIterator = blueConfiguration.iterator();

		PieceLocationDescriptor currentRedIterPiece = null;
		PieceLocationDescriptor currentBlueIterPiece = null;
		Piece pieceAtLocation = null;

		final int locationX = location.getCoordinate(Coordinate.X_COORDINATE);
		final int locationY = location.getCoordinate(Coordinate.Y_COORDINATE);
		int currentRedX;
		int currentRedY;
		int currentBlueX;
		int currentBlueY;

		while (blueIterator.hasNext() && redIterator.hasNext()) {
			currentRedIterPiece = redIterator.next();
			currentBlueIterPiece = blueIterator.next();

			currentRedX = currentRedIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
			currentRedY = currentRedIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
			if (currentRedX == locationX && currentRedY == locationY) {
				pieceAtLocation = currentRedIterPiece.getPiece();
			}

			currentBlueX = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
			currentBlueY = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
			if (currentBlueX == locationX && currentBlueY == locationY) {
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
	public static MoveResult battle(PieceLocationDescriptor playerPiece, PieceLocationDescriptor opponentPiece){
		final PieceLocationDescriptor battleWinner = new PieceLocationDescriptor(playerPiece.getPiece(), opponentPiece.getLocation());
		final PieceType opponentPieceType = opponentPiece.getPiece().getType();
		final PlayerColor playerPieceOwner = playerPiece.getPiece().getOwner();

		//if the pieces are the same type, remove both
		if (playerPiece.getPiece().getType().equals(opponentPieceType)){
			if (playerPieceOwner == PlayerColor.RED){
				redConfiguration.remove(playerPiece);
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, null);
			}
			else{
				blueConfiguration.remove(playerPiece);
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, null);
			}
		}
		//battle
		switch (playerPiece.getPiece().getType()) {
		case MARSHAL: if (MarshalBeatsThese.contains(opponentPieceType)) {
			//if opponent piece is flag, set game over to true, remove flag from configuration, return battle winner
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			//if red wins, remove from blue configuration
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			} 
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
		}
		case COLONEL: if (ColonelBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
		}
		case CAPTAIN: if (CaptainBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
		}
		case LIEUTENANT: if (LieutenantBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, battleWinner);
			}
		}
		case SERGEANT: if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
		default:
			break;
		}
		//if moving piece loses, remove moving piece and return opponent
		if(opponentPiece.getPiece().getOwner() == PlayerColor.RED) {
			blueConfiguration.remove(playerPiece);
			return new MoveResult(MoveResultStatus.OK, new PieceLocationDescriptor(opponentPiece.getPiece(), playerPiece.getLocation()));
		}
		else{
			redConfiguration.remove(playerPiece);
			return new MoveResult(MoveResultStatus.OK, new PieceLocationDescriptor(opponentPiece.getPiece(), playerPiece.getLocation()));
		}
	}

	/**
	 * Fills the piece type lists
	 */
	private static void fillBattleLists() {
		MarshalBeatsThese.add(PieceType.SERGEANT);
		MarshalBeatsThese.add(PieceType.LIEUTENANT);
		MarshalBeatsThese.add(PieceType.CAPTAIN);
		MarshalBeatsThese.add(PieceType.COLONEL);
		MarshalBeatsThese.add(PieceType.FLAG);
		ColonelBeatsThese.add(PieceType.LIEUTENANT);
		ColonelBeatsThese.add(PieceType.CAPTAIN);
		ColonelBeatsThese.add(PieceType.SERGEANT);
		ColonelBeatsThese.add(PieceType.FLAG);
		CaptainBeatsThese.add(PieceType.LIEUTENANT);
		CaptainBeatsThese.add(PieceType.SERGEANT);
		CaptainBeatsThese.add(PieceType.FLAG);
		LieutenantBeatsThese.add(PieceType.SERGEANT);
		LieutenantBeatsThese.add(PieceType.FLAG);
	}

	/**
	 * If the current player runs into a flag, 
	 * @param playerPiece
	 * @param opponentPiece
	 * @return
	 */
	private static MoveResult flagBattle(PieceLocationDescriptor playerPiece, PieceLocationDescriptor opponentPiece) {
		final PieceLocationDescriptor battleWinner = new PieceLocationDescriptor(playerPiece.getPiece(), opponentPiece.getLocation());
		gameOver = true;
		if (playerPiece.getPiece().getOwner() == PlayerColor.RED) {
			blueConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.RED_WINS, battleWinner);
		}
		else {
			redConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.BLUE_WINS, battleWinner);
		}
	}

}
