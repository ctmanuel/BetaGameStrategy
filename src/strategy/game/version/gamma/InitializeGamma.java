package strategy.game.version.gamma;

import java.util.HashMap;
import java.util.Map;

import strategy.game.common.PieceType;
import strategy.game.version.Initialize;

public class InitializeGamma implements Initialize{
	private final int FLAG_COUNT = 1;
	private final int MARSHAL_COUNT = 1;
	private final int COLONEL_COUNT = 2;
	private final int CAPTAIN_COUNT = 2;
	private final int LIEUTENANT_COUNT = 3;
	private final int SERGEANT_COUNT = 3;
	private final int CHOKEPOINT_COUNT = 4;

	private final Map<PieceType, Integer> startingPieces;
	
	public InitializeGamma(){
		startingPieces = new HashMap<PieceType, Integer>();
		fillInitialPieces();
	}
	
	@Override
	public void initializePieceRanks() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void fillInitialPieces() {
		startingPieces.put(PieceType.MARSHAL, MARSHAL_COUNT);
		startingPieces.put(PieceType.COLONEL, COLONEL_COUNT);
		startingPieces.put(PieceType.CAPTAIN, CAPTAIN_COUNT);
		startingPieces.put(PieceType.LIEUTENANT, LIEUTENANT_COUNT);
		startingPieces.put(PieceType.SERGEANT, SERGEANT_COUNT);
		startingPieces.put(PieceType.FLAG, FLAG_COUNT);
		
	}
	
	public Map<PieceType, Integer> getStartingPieces(){
		return startingPieces;
	}

}
