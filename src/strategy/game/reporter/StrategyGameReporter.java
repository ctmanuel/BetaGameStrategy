package strategy.game.reporter;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.Coordinate;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;

public class StrategyGameReporter implements StrategyGameObserver{

	private int moveCount = 1;

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
}