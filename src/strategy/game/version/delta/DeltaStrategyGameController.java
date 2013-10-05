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
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
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

	public DeltaStrategyGameController(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) throws StrategyException
			{
		new RepetitionRule();
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
	private void mapConfigurationBoard(Collection<PieceLocationDescriptor> config) throws StrategyException {
		Piece newPiece;
		for(PieceLocationDescriptor pieceLD : config) {
			newPiece = pieceLD.getPiece();
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
		// TODO Auto-generated method stub
		return null;
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
