/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.gamma;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
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

/**
 * The BetaStrategyGameController implements the game core for
 * the Beta Strategy version.
 * @author dmlarose, ctmanuel
 * @version Sep 13, 2013
 */
public class GammaStrategyGameController implements StrategyGameController {

	private static boolean gameStarted = false;
	private static boolean gameOver = false;
	private int playerTurn;
	private PieceLocationDescriptor currentPieceDescriptor = null;
	private PlayerColor playerColor;
	private MoveResult battleResult;
	private PieceLocationDescriptor CP22;
	private PieceLocationDescriptor CP23;
	private PieceLocationDescriptor CP32;
	private PieceLocationDescriptor CP33;
	
	private final ValidateGamma validateGamma = new ValidateGamma();
	private final Collection<PieceLocationDescriptor> origionalredConfiguration;
	private final Collection<PieceLocationDescriptor> origionalblueConfiguration;
	private static Collection<PieceLocationDescriptor> redConfiguration = null;
	private static Collection<PieceLocationDescriptor> blueConfiguration = null;
	private final Map<Location, Piece> boardMap;
	private final Collection<PieceLocationDescriptor> chokePointList = new ArrayList<PieceLocationDescriptor>();

	/**
	 * Constructor for the Beta Game Strategy
	 * @param redConfiguration the list of red pieces
	 * @param blueConfiguration the list of blue pieces
	 * @throws StrategyException 
	 */
	public GammaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException
	{
		new InitializeGamma();
		validateGamma.validateConfiguration(redConfiguration, 0, 1);
		validateGamma.validateConfiguration(blueConfiguration, 4, 5);
		
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
		CP22 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(2,2));
		CP23 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(2,3));
		CP32 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(3,2));
		CP33 = new PieceLocationDescriptor(new Piece(PieceType.CHOKE_POINT, null), new Location2D(3,3));

		chokePointList.add(CP22);
		chokePointList.add(CP23);
		chokePointList.add(CP32);
		chokePointList.add(CP33);
	}

	/**
	 * Places the configurations on the board map
	 * @param config Configuration to place
	 * @throws StrategyException 
	 */
	private void mapConfigurationBoard(Collection<PieceLocationDescriptor> config) throws StrategyException {
		Piece newPiece;
		for(PieceLocationDescriptor pieceLD : config) {
			newPiece = pieceLD.getPiece();
			//check if pieces are trying to be put on the same space
			//TODO:move this to validator if time  
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
	//TODO: add DRAW
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
				|| piece == PieceType.LIEUTENANT
				|| piece == PieceType.COLONEL
				|| piece == PieceType.CAPTAIN
				|| piece == PieceType.SERGEANT
				|| piece == PieceType.FLAG
				|| piece == PieceType.CHOKE_POINT)) {
			throw new StrategyException(piece + " is not a valid piece for the Gamma Strategy.");
		}

		if(piece == PieceType.FLAG || piece == PieceType.CHOKE_POINT) {
			throw new StrategyException("Cannot move the " + piece);
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
			battleResult = Battle.battle(currentPieceDescriptor, new PieceLocationDescriptor(tempPieceAtTo, to));
			if(battleResult.getBattleWinner() == null) { 
				return battleResult;
			}
			if(playerColor == PlayerColor.RED) {
				redConfiguration.remove(currentPieceDescriptor);
				redConfiguration.add(battleResult.getBattleWinner());
				boardMap.remove(currentPieceDescriptor.getLocation());
				boardMap.put(battleResult.getBattleWinner().getLocation(), battleResult.getBattleWinner().getPiece());
			}
			else {
				blueConfiguration.remove(currentPieceDescriptor);
				blueConfiguration.add(battleResult.getBattleWinner());
				boardMap.remove(currentPieceDescriptor.getLocation());
				boardMap.put(battleResult.getBattleWinner().getLocation(), battleResult.getBattleWinner().getPiece());
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
		validateGamma.validateOutOfBounds(to);

		//check for diagonals moves
		validateGamma.validateDiagonalMove(currentXcoordinate, currentYcoordinate,
				toXcoordinate, toYcoordinate);
		
		//check for valid X,Y coordinates
		validateGamma.validateCrossMove(currentPieceDescriptor.getLocation(),to);

	}

	@Override
	public Piece getPieceAt(Location location) {
//		final Iterator<PieceLocationDescriptor> redIterator = redConfiguration.iterator();
//		final Iterator<PieceLocationDescriptor> blueIterator = blueConfiguration.iterator();
//
//		PieceLocationDescriptor currentRedIterPiece = null;
//		PieceLocationDescriptor currentBlueIterPiece = null;
//		Piece pieceAtLocation = null;
//
//		final int locationX = location.getCoordinate(Coordinate.X_COORDINATE);
//		final int locationY = location.getCoordinate(Coordinate.Y_COORDINATE);
//		int currentRedX;
//		int currentRedY;
//		int currentBlueX;
//		int currentBlueY;
//
//		while (blueIterator.hasNext() && redIterator.hasNext()) {
//			currentRedIterPiece = redIterator.next();
//			currentBlueIterPiece = blueIterator.next();
//
//			currentRedX = currentRedIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
//			currentRedY = currentRedIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
//			if (currentRedX == locationX && currentRedY == locationY) {
//				pieceAtLocation = currentRedIterPiece.getPiece();
//			}
//
//			currentBlueX = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.X_COORDINATE);
//			currentBlueY = currentBlueIterPiece.getLocation().getCoordinate(Coordinate.Y_COORDINATE);
//			if (currentBlueX == locationX && currentBlueY == locationY) {
//				pieceAtLocation = currentBlueIterPiece.getPiece();
//			}
//		}
//		return pieceAtLocation;
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
