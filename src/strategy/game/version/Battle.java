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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

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
	private static final Map<PieceType, Integer> pieceRanks = new HashMap<PieceType, Integer>();
	private static int blueflagCount = 0;
	private static int redflagCount = 0;

	/**
	 * The constructor for the battle initialization
	 * @param redConfiguration
	 * @param blueConfiguration
	 */
	public Battle(Collection<PieceLocationDescriptor> redConfiguration, 
			Collection<PieceLocationDescriptor> blueConfiguration){
		Battle.redConfiguration = redConfiguration;
		Battle.blueConfiguration = blueConfiguration;
		initializePieceRanks();
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
		if (pieceRanks.get(playerPiece.getPiece().getType()) == pieceRanks.get(opponentPiece.getPiece().getType())){
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

		//if opponent is a bomb
		if(opponentPiece.getPiece().getType() == PieceType.BOMB 
				&& playerPiece.getPiece().getType() != PieceType.MINER){
			if (playerPieceOwner == PlayerColor.RED){
				redConfiguration.remove(playerPiece);
				return new MoveResult(MoveResultStatus.OK, opponentPiece);
			}
			else{
				blueConfiguration.remove(playerPiece);
				return new MoveResult(MoveResultStatus.OK, opponentPiece);
			}
		}

		//if player is spy vs marshal
		if(playerPiece.getPiece().getType() == PieceType.SPY 
				&& opponentPiece.getPiece().getType() == PieceType.MARSHAL){
			if (playerPieceOwner == PlayerColor.RED){
				redConfiguration.remove(playerPiece);
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
			else{
				blueConfiguration.remove(playerPiece);
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, attackingBattleWinner);
			}
		}

		//if first lieutenant attacks and loses, distance of 2
		if(playerPiece.getPiece().getType() == PieceType.FIRST_LIEUTENANT
				&& playerPiece.getLocation().distanceTo(opponentPiece.getLocation()) == 2
				&& pieceRanks.get(playerPiece.getPiece().getType()) < pieceRanks.get(opponentPiece.getPiece().getType())){
			//			System.out.println("gets here");
			//			System.out.println("Player rank is " + pieceRanks.get(playerPiece.getPiece().getType()));
			//			System.out.println("Player piece type is " + playerPiece.getPiece().getType());
			//			System.out.println("opponent rank is " + pieceRanks.get(opponentPiece.getPiece().getType()));
			//			System.out.println("Opponent piece type is " + opponentPiece.getPiece().getType());
			if(playerPieceOwner == PlayerColor.RED) {
				redConfiguration.remove(playerPiece);
				blueConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, opponentPiece);
			}
			else {
				blueConfiguration.remove(playerPiece);
				redConfiguration.remove(opponentPiece);
				return new MoveResult(MoveResultStatus.OK, opponentPiece);
			}
		}

		//check piece ranks
		if(pieceRanks.get(playerPiece.getPiece().getType()) > pieceRanks.get(opponentPiece.getPiece().getType())) {
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

		//if moving piece loses, remove moving piece and return opponent
		if(opponentPiece.getPiece().getOwner() == PlayerColor.RED) {
			blueConfiguration.remove(playerPiece);
			redConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.OK, defendingBattleWinner);
		}
		else{
			redConfiguration.remove(playerPiece);
			blueConfiguration.remove(opponentPiece);
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


		System.out.println("Gets here");
		if (playerPiece.getPiece().getOwner() == PlayerColor.RED
				&& blueflagCount == 0) {
			blueflagCount += 1;
			blueConfiguration.remove(opponentPiece);
			redConfiguration.remove(playerPiece);
			return new MoveResult(MoveResultStatus.FLAG_CAPTURED, battleWinner);

		}
		else if (playerPiece.getPiece().getOwner() == PlayerColor.BLUE
				&& redflagCount == 0){
			redflagCount += 1;
			redConfiguration.remove(opponentPiece);
			blueConfiguration.remove(opponentPiece);
			return new MoveResult(MoveResultStatus.FLAG_CAPTURED, battleWinner);
		}

		else if (playerPiece.getPiece().getOwner() == PlayerColor.RED) {
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
	 */
	public void initializePieceRanks() {
		pieceRanks.put(PieceType.MARSHAL, 12);
		pieceRanks.put(PieceType.GENERAL, 11);
		pieceRanks.put(PieceType.COLONEL, 10);
		pieceRanks.put(PieceType.MAJOR, 9);
		pieceRanks.put(PieceType.CAPTAIN, 8);
		pieceRanks.put(PieceType.LIEUTENANT, 7);
		pieceRanks.put(PieceType.FIRST_LIEUTENANT, 7);
		pieceRanks.put(PieceType.SERGEANT, 6);
		pieceRanks.put(PieceType.MINER, 5);
		pieceRanks.put(PieceType.SCOUT, 4);
		pieceRanks.put(PieceType.SPY, 3);
		pieceRanks.put(PieceType.BOMB, 2);
		pieceRanks.put(PieceType.FLAG, 1);
	}
}
