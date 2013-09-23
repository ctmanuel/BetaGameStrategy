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

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import strategy.common.PlayerColor;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

/**
 * The Battle Class
 * in charge of handling battle events
 * @author dmlarose, ctmanuel
 * @version Sep 22, 2013
 */

public class Battle {
	private static Collection<PieceLocationDescriptor> redConfiguration = null;
	private static Collection<PieceLocationDescriptor> blueConfiguration = null;
	
	private static final List<PieceType> MarshalBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> ColonelBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> CaptainBeatsThese = new ArrayList<PieceType>();
	private static final List<PieceType> LieutenantBeatsThese = new ArrayList<PieceType>();
	
	/**
	 * The constructor for the battle initialization
	 * @param redConfiguration
	 * @param blueConfiguration
	 */
	public Battle(Collection<PieceLocationDescriptor> redConfiguration, 
			Collection<PieceLocationDescriptor> blueConfiguration){
		Battle.redConfiguration = redConfiguration;
		Battle.blueConfiguration = blueConfiguration;
		fillBattleLists();
	}
	/**
	 * EPIC BATTLE FIGHT TO THE DEATH
	 * @param playerPiece current player's piece
	 * @param opponentPiece opponent's piece
	 * @return status of winner
	 */
	public static MoveResult battle(PieceLocationDescriptor playerPiece, PieceLocationDescriptor opponentPiece){
		final PieceLocationDescriptor attackingBattleWinner = 
				new PieceLocationDescriptor(playerPiece.getPiece(), opponentPiece.getLocation());
		final PieceLocationDescriptor defendingBattleWinner = 
				new PieceLocationDescriptor(opponentPiece.getPiece(), playerPiece.getLocation());
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
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			} 
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
		}
		case COLONEL: if (ColonelBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
		}
		case CAPTAIN: if (CaptainBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
		}
		case LIEUTENANT: if (LieutenantBeatsThese.contains(opponentPieceType)) {
			if (opponentPieceType.equals(PieceType.FLAG)) {
				return flagBattle(playerPiece, opponentPiece);
			}
			else if (playerPieceOwner == PlayerColor.RED) {
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
			else {
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
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
			return new MoveResult(MoveResultStatus.OK, defendingBattleWinner);
		}
		else{
			redConfiguration.remove(playerPiece);
			return new MoveResult(MoveResultStatus.OK, defendingBattleWinner);
		}
	}
	

	/**
	 * If the current player runs into a flag, 
	 * @param playerPiece
	 * @param opponentPiece
	 * @return
	 */
	private static MoveResult flagBattle(PieceLocationDescriptor playerPiece, PieceLocationDescriptor opponentPiece) {
		final PieceLocationDescriptor battleWinner = new PieceLocationDescriptor(playerPiece.getPiece(), opponentPiece.getLocation());
//		gameOver = true;
//		gameStarted = false;
		if (playerPiece.getPiece().getOwner() == PlayerColor.RED) {
			blueConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.RED_WINS, battleWinner);
		}
		else {
			redConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.BLUE_WINS, battleWinner);
		}
	}
	
	/**
	 * Fills the piece battle lists
	 * TODO: possibly "global" across versions
	 */
	public void fillBattleLists() {
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

}
