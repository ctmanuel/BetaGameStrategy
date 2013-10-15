/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.reporter;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

/**
 * The reporter for the Epsilon strategy
 * @author dmlarose, ctmanuel
 * @version Oct 12, 2013
 *
 */
public class StrategyGameReporter implements StrategyGameObserver
{
	
	private static Collection<PieceLocationDescriptor> reporterRedConfiguration;
	private static Collection<PieceLocationDescriptor> reporterBlueConfiguration;
	private int moveCount = 1;

	public StrategyGameReporter(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		reporterRedConfiguration = redConfiguration;
		reporterBlueConfiguration = blueConfiguration;
	}
	
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		System.out.println("RedConfiguration:");
		for(PieceLocationDescriptor pld : redConfiguration){
			System.out.println(pld.getPiece().toString() + " at (" 
					+ pld.getLocation().getCoordinate(Coordinate.X_COORDINATE) + "," 
					+ pld.getLocation().getCoordinate(Coordinate.Y_COORDINATE) + ")");
		}

		System.out.println("BlueConfiguration:");
		for(PieceLocationDescriptor pld : blueConfiguration){
			System.out.println(pld.getPiece().toString() + " at (" 
					+ pld.getLocation().getCoordinate(Coordinate.X_COORDINATE) + "," 
					+ pld.getLocation().getCoordinate(Coordinate.Y_COORDINATE) + ")");
		}
	}

	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) {
		String resultWinner;
		if(result == null || result.getBattleWinner() == null) {
			resultWinner = "NULL";
		}

		else {
			resultWinner = result.getBattleWinner().getPiece().toString();
		}
		
		if(fault != null){
			System.out.println("Recieved StrategtyException: " + fault.getMessage());
		}

		if(piece == null && from == null && to == null){
			System.out.println("Player Forfieted. Result: " + resultWinner);
		}

		else if(result != null){
			System.out.println("Move " + moveCount + " : " 
					+ piece.getSymbol() + " moved " + "From: " + from + " To: " + to + 
					" Result: " + resultWinner + " Status: " 
					+ result.getStatus().toString());
			moveCount++;
		}
	}
	
	public static Collection<PieceLocationDescriptor> getRedConfiguration() {
		return reporterRedConfiguration;
	}
	
	public static Collection<PieceLocationDescriptor> getBlueConfiguration() {
		return reporterBlueConfiguration;
	}
}