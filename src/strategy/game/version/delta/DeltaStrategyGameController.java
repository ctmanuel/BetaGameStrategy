/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.delta;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.Battle;
import strategy.game.version.RepetitionRule;

/**
 * The DeltaStrategyGameController implements the game core for
 * the Delta Strategy version.
 * @author dmlarose, ctmanuel
 * @version Oct 03, 2013
 */
public class DeltaStrategyGameController implements StrategyGameController {

	private static boolean gameStarted = false;
	private static boolean gameOver = false;
	private int playerTurn;
	private MoveResult flagResult;
	private PieceLocationDescriptor currentPieceDescriptor = null;
	private PlayerColor playerColor;
	private MoveResult battleResult;
	private PieceLocationDescriptor CP24;
	private PieceLocationDescriptor CP25;
	private PieceLocationDescriptor CP34;
	private PieceLocationDescriptor CP35;
	private PieceLocationDescriptor CP64;
	private PieceLocationDescriptor CP65;
	private PieceLocationDescriptor CP74;
	private PieceLocationDescriptor CP75;

	private final ValidateDelta validateDelta = new ValidateDelta();
	private final Collection<PieceLocationDescriptor> origionalredConfiguration;
	private final Collection<PieceLocationDescriptor> origionalblueConfiguration;
	private static List<PieceLocationDescriptor> redConfiguration = null;
	private static List<PieceLocationDescriptor> blueConfiguration = null;
	private final Map<Location, Piece> boardMap;
	private final Collection<PieceLocationDescriptor> chokePointList = new ArrayList<PieceLocationDescriptor>();

	/**
	 * Constructor for the Delta Game Strategy
	 * @param redConfiguration the list of red pieces
	 * @param blueConfiguration the list of blue pieces
	 * @throws StrategyException 
	 */	
	public DeltaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration)
					throws StrategyException {

		new RepetitionRule();
		new InitializeDelta();
		validateDelta.validateConfiguration(redConfiguration, 0, 3);
		validateDelta.validateConfiguration(blueConfiguration, 6, 9);

		new Battle(redConfiguration, blueConfiguration);

		//set game variables
		gameStarted = false;
		gameOver = false;

		//remember original configurations
		origionalblueConfiguration = blueConfiguration;
		origionalredConfiguration = redConfiguration;
		makeChokePoints();

		//set up board and pieces
		boardMap = new HashMap<Location, Piece>();
		boardMap.clear();

		//map configurations 
		mapConfigurationBoard(redConfiguration);
		mapConfigurationBoard(blueConfiguration);
		mapConfigurationBoard(chokePointList);
	}

	private void makeChokePoints() {
		CP24 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(2,4));
		CP25 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(2,5));
		CP34 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(3,4));
		CP35 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(3,5));
		CP64 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(6,4));
		CP65 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(6,5));
		CP74 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(7,4));
		CP75 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(7,5));

		chokePointList.add(CP24);
		chokePointList.add(CP25);
		chokePointList.add(CP34);
		chokePointList.add(CP35);
		chokePointList.add(CP64);
		chokePointList.add(CP65);
		chokePointList.add(CP74);
		chokePointList.add(CP75);
	}

	/**
	 * Places the configurations on the board map
	 * @param config Configuration to place
	 * @throws StrategyException 
	 */
	private void mapConfigurationBoard(Collection<PieceLocationDescriptor> config) 
			throws StrategyException {

		for(PieceLocationDescriptor pieceLD : config) {
			Piece newPiece = pieceLD.getPiece();
			//check if pieces are trying to be put on the same space  
			checkDestinationIsNotOccupied(pieceLD);
			boardMap.put(pieceLD.getLocation(), newPiece);
		}
	}

	/*
	 * @see strategy.game.StrategyGameController#startGame()
	 */
	@Override
	public void startGame() throws StrategyException {

		if(gameStarted){
			throw new StrategyException("Game already in progress");
		}
		gameStarted = true;
		gameOver = false;
		playerTurn = 0;
		redConfiguration = new ArrayList<PieceLocationDescriptor>(origionalredConfiguration);
		blueConfiguration = new ArrayList<PieceLocationDescriptor>(origionalblueConfiguration);
	}

	/*
	 * @see strategy.game.StrategyGameController#move(strategy.game.common.PieceType, strategy.game.common.Location, strategy.game.common.Location)
	 */
	@Override
	public MoveResult move(PieceType piece, Location from, Location to)
			throws StrategyException {

		if (gameOver) {
			throw new StrategyException("The game is over, you cannot make a move");
		}
		if (!gameStarted) {
			throw new StrategyException("You must start the game");
		}
		if (!(piece == PieceType.MARSHAL 
				|| piece == PieceType.GENERAL
				|| piece == PieceType.COLONEL
				|| piece == PieceType.MAJOR
				|| piece == PieceType.CAPTAIN
				|| piece == PieceType.LIEUTENANT
				|| piece == PieceType.SERGEANT
				|| piece == PieceType.MINER
				|| piece == PieceType.SCOUT
				|| piece == PieceType.SPY)) {

			throw new StrategyException(piece + " is not a valid piece for the Delta Strategy.");
		}

		if(piece == PieceType.FLAG
				|| piece == PieceType.BOMB
				|| piece == PieceType.CHOKE_POINT) {

			throw new StrategyException("Cannot move the " + piece);
		}

		MoveResult flagOnly = checkPlayerTurnAndFlag();
		if(flagOnly != null) {
			return flagOnly;
		}

		currentPieceDescriptor = new PieceLocationDescriptor(new Piece(piece, playerColor), from);

		//if scout 
		if(piece == PieceType.SCOUT && currentPieceDescriptor.getLocation().distanceTo(to) > 1){
			Piece tempPieceAtTo;
			//check for pieces in scouts path
			for (int i = 1;  i <= currentPieceDescriptor.getLocation().distanceTo(to); i++){
				//if moving on the y axis
				if(from.getCoordinate(Coordinate.Y_COORDINATE) != to.getCoordinate(Coordinate.Y_COORDINATE)){
					tempPieceAtTo = getPieceAt(new Location2D(to.getCoordinate(Coordinate.X_COORDINATE),
							from.getCoordinate(Coordinate.Y_COORDINATE)+i));
					if(tempPieceAtTo != null){
						throw new StrategyException("Piece in Scout path");
					}
				}
				//if moving on the x axis
				else{
					tempPieceAtTo = getPieceAt(new Location2D(from.getCoordinate(Coordinate.X_COORDINATE)+i,
							to.getCoordinate(Coordinate.Y_COORDINATE)));
					if(tempPieceAtTo != null){
						throw new StrategyException("Piece in Scout path");
					}
				}
			}
			final PieceLocationDescriptor newPiece =
					new PieceLocationDescriptor(new Piece(piece, playerColor), to);

			if (playerColor == PlayerColor.RED) {
				redConfiguration.remove(currentPieceDescriptor);
				redConfiguration.add(newPiece);
				boardMap.remove(currentPieceDescriptor.getLocation());
				boardMap.put(to, newPiece.getPiece());
			}
			else {
				blueConfiguration.remove(currentPieceDescriptor);
				blueConfiguration.add(newPiece);
				boardMap.remove(currentPieceDescriptor.getLocation());
				boardMap.put(to, newPiece.getPiece());
			}

			return new MoveResult(MoveResultStatus.OK, newPiece);

		}

		//check location for valid location
		checkLocationCoordinates(to);

		//check for repetition rule
		RepetitionRule.checkRepRule(currentPieceDescriptor, to);
		RepetitionRule.addToQueue(currentPieceDescriptor, to);

		final Piece tempPieceAtTo = getPieceAt(to);

		//check if occupied
		if (tempPieceAtTo != null) {

			//check if the pieces are the same color
			if (currentPieceDescriptor.getPiece().getOwner() == tempPieceAtTo.getOwner()) {
				throw new StrategyException("Space is occupied by same color piece");
			}

			//go to battle method
			battleResult = Battle.battle(currentPieceDescriptor, new PieceLocationDescriptor(tempPieceAtTo, to));

			//if both pieces lose
			if(battleResult.getBattleWinner() == null) { 
				boardMap.remove(currentPieceDescriptor.getLocation());
				boardMap.remove(to);

				flagResult = checkFlagOnly(playerColor);
				if(flagResult != null){
					return flagResult;
				}
				return battleResult;
			}

			if(battleResult.getBattleWinner().getPiece().getOwner() == PlayerColor.RED) {
				redConfiguration.add(battleResult.getBattleWinner());
			}
			else {
				blueConfiguration.add(battleResult.getBattleWinner());
			}
			boardMap.remove(currentPieceDescriptor.getLocation());
			boardMap.remove(to);
			boardMap.put(battleResult.getBattleWinner().getLocation(), battleResult.getBattleWinner().getPiece());

			flagResult = checkFlagOnly(playerColor);
			if(flagResult != null){
				return flagResult;
			}
			return battleResult;
		}

		//if no battle go here
		final PieceLocationDescriptor newPiece =
				new PieceLocationDescriptor(new Piece(piece, playerColor), to);

		if (playerColor == PlayerColor.RED) {
			redConfiguration.remove(currentPieceDescriptor);
			redConfiguration.add(newPiece);
			boardMap.remove(currentPieceDescriptor.getLocation());
			boardMap.put(to, newPiece.getPiece());
		}
		else {
			blueConfiguration.remove(currentPieceDescriptor);
			blueConfiguration.add(newPiece);
			boardMap.remove(currentPieceDescriptor.getLocation());
			boardMap.put(to, newPiece.getPiece());
		}

		return new MoveResult(MoveResultStatus.OK, newPiece);
	}

	/**
	 * get the current player's turn
	 *  and if there are only flags left on the board
	 * @return moveResult if there are only flags left on the board,
	 *    null otherwise
	 */
	private MoveResult checkPlayerTurnAndFlag() {

		//check which color turn it is
		playerColor = null;
		if (playerTurn == 0) {
			playerColor = PlayerColor.RED;
			playerTurn = 1;

			//check if flag is the only piece left
			flagResult = checkFlagOnly(PlayerColor.BLUE);
			if(flagResult != null){
				return flagResult;
			}
		}
		else {
			playerColor = PlayerColor.BLUE;
			playerTurn = 0;

			//check if flag is the only piece left
			flagResult = checkFlagOnly(PlayerColor.RED);
			if(flagResult != null){
				return flagResult;
			}
		}
		return null;
	}

	/**
	 * checks if the flag is the only piece left on the board
	 * @return 
	 */
	private MoveResult checkFlagOnly(PlayerColor pc) {
		Piece temp;
		PieceType shouldBeBlueFlag = null;
		PieceType shouldBeRedFlag = null;
		int blueCount = 0;
		int redCount = 0;

		for (Location location : boardMap.keySet()) {
			if(location != null) {
				temp = boardMap.get(location);
				if(temp.getOwner() != null && temp.getOwner().equals(PlayerColor.BLUE)) {
					blueCount += 1;
					if(temp.getType().equals(PieceType.FLAG)) {
						shouldBeBlueFlag = temp.getType();
					}
				}
				else if(temp.getOwner() != null && temp.getOwner().equals(PlayerColor.RED)) {
					redCount += 1;
					if(temp.getType().equals(PieceType.FLAG)) {
						shouldBeRedFlag = temp.getType();
					}
				}
			}
		}
		if((blueCount == 1 && shouldBeBlueFlag.equals(PieceType.FLAG))
				&& (redCount == 1 && shouldBeRedFlag.equals(PieceType.FLAG))) {
			return new MoveResult(MoveResultStatus.DRAW, null);
		}
		else if (blueCount == 1 && shouldBeBlueFlag.equals(PieceType.FLAG)) {
			return new MoveResult(MoveResultStatus.RED_WINS, null);
		}
		else if (redCount == 1 && shouldBeRedFlag.equals(PieceType.FLAG)) {
			return new MoveResult(MoveResultStatus.BLUE_WINS, null);
		}
		else {
			return null;
		}

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

		//check if choke point
		final PieceLocationDescriptor[] chokePointArray = new PieceLocationDescriptor[chokePointList.size()];
		chokePointList.toArray(chokePointArray);
		for (int i = 0; i < chokePointList.size(); i++) {
			PieceLocationDescriptor chokePoint = chokePointArray[i];
			final int chokePointX = chokePoint.getLocation().getCoordinate(Coordinate.X_COORDINATE);
			final int chokePointY = chokePoint.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
			if(toXcoordinate == chokePointX && toYcoordinate == chokePointY) {
				throw new StrategyException("Cannot move to a choke point position");
			}
		}

		//check if out of bounds
		validateDelta.validateOutOfBounds(to);

		//check for diagonals moves
		validateDelta.validateDiagonalMove(currentXcoordinate, currentYcoordinate,
				toXcoordinate, toYcoordinate);

		//check for valid X,Y coordinates
		// TODO: add scout checking
		validateDelta.validateCrossMove(currentPieceDescriptor.getLocation(),to);

	}

	@Override
	public Piece getPieceAt(Location location) {
		return boardMap.get(location);
	}

	private void checkDestinationIsNotOccupied(PieceLocationDescriptor pld) 
			throws StrategyException
			{
		if (getPieceAt(pld.getLocation()) != null) {
			throw new StrategyException("Attempt to place " + pld.getPiece()
					+ " on occupied location " + pld.getLocation());
		}
			}

}
