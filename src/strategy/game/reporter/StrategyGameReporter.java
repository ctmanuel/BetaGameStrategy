package strategy.game.reporter;

import java.util.Collection;

import strategy.common.StrategyException;
import strategy.game.common.Location;
import strategy.game.common.MoveResult;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.common.StrategyGameObserver;
import strategy.game.version.epsilon.EpsilonStrategyGameController;

public class StrategyGameReporter implements StrategyGameObserver{

	private String name;
	private EpsilonStrategyGameController game;
	private int moveCount = 1;
	public StrategyGameReporter(String name){
		this.name = name;
	}
	
	@Override
	public void gameStart(Collection<PieceLocationDescriptor> redConfiguration,
			Collection<PieceLocationDescriptor> blueConfiguration) {
		try {
			game = new EpsilonStrategyGameController(redConfiguration, blueConfiguration, null);
		} catch (StrategyException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void moveHappened(PieceType piece, Location from, Location to,
			MoveResult result, StrategyException fault) {
		String resultWinner;
		if( result.getBattleWinner() == null)
			resultWinner = "NULL";
		else
			resultWinner = result.getBattleWinner().getPiece().toString();
		if(piece == null && from == null && to == null){
			System.out.println("Player Forfieted. Result: " + resultWinner);
		}
		else{
		System.out.println("Move " + moveCount + " : " 
			+ piece.getSymbol() + " moved " + "From: " + from + " To: " + to + 
			" Result: " + resultWinner + " Status: " 
			+ result.getStatus().toString());
		moveCount++;
		}
	}

}