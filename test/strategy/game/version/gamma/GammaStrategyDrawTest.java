package strategy.game.version.gamma;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class GammaStrategyDrawTest {

	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory GammaStrategy = StrategyGameFactory.getInstance();
	private Piece redFlag = new Piece(PieceType.FLAG, PlayerColor.RED);
	private Piece redMarshal = new Piece(PieceType.MARSHAL, PlayerColor.RED);
	private Piece redColonel = new Piece(PieceType.COLONEL, PlayerColor.RED);
	private Piece redCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.RED);
	private Piece redLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.RED);
	private Piece redSergeant = new Piece(PieceType.SERGEANT, PlayerColor.RED);
	private Piece blueFlag = new Piece(PieceType.FLAG, PlayerColor.BLUE);
	private Piece blueMarshal = new Piece(PieceType.MARSHAL, PlayerColor.BLUE);
	private Piece blueColonel = new Piece(PieceType.COLONEL, PlayerColor.BLUE);
	private Piece blueCaptain = new Piece(PieceType.CAPTAIN, PlayerColor.BLUE);
	private Piece blueLieutenant = new Piece(PieceType.LIEUTENANT, PlayerColor.BLUE);
	private Piece blueSergeant = new Piece(PieceType.SERGEANT, PlayerColor.BLUE);
	private Location2D loc00 = new Location2D(0,0);
	private Location2D loc10 = new Location2D(1,0);
	private Location2D loc20 = new Location2D(2,0);
	private Location2D loc30 = new Location2D(3,0);
	private Location2D loc40 = new Location2D(4,0);
	private Location2D loc50 = new Location2D(5,0);
	private Location2D loc01 = new Location2D(0,1);
	private Location2D loc11 = new Location2D(1,1);
	private Location2D loc21 = new Location2D(2,1);
	private Location2D loc31 = new Location2D(3,1);
	private Location2D loc41 = new Location2D(4,1);
	private Location2D loc51 = new Location2D(5,1);
	private Location2D loc02 = new Location2D(0,2);
	private Location2D loc12 = new Location2D(1,2);
	private Location2D loc42 = new Location2D(4,2);
	private Location2D loc52 = new Location2D(5,2);
	private Location2D loc03 = new Location2D(0,3);
	private Location2D loc13 = new Location2D(1,3);
	private Location2D loc43 = new Location2D(4,3);
	private Location2D loc53 = new Location2D(5,3);
	private Location2D loc04 = new Location2D(0,4);
	private Location2D loc14 = new Location2D(1,4);
	private Location2D loc24 = new Location2D(2,4);
	private Location2D loc34 = new Location2D(3,4);
	private Location2D loc44 = new Location2D(4,4);
	private Location2D loc54 = new Location2D(5,4);
	private Location2D loc05 = new Location2D(0,5);
	private Location2D loc15 = new Location2D(1,5);
	private Location2D loc25 = new Location2D(2,5);
	private Location2D loc35 = new Location2D(3,5);
	private Location2D loc45 = new Location2D(4,5);
	private Location2D loc55 = new Location2D(5,5);

	@Before
	public void setup() {
		final PieceLocationDescriptor[] redPieces = {
				new PieceLocationDescriptor(redMarshal, loc01),
				new PieceLocationDescriptor(redFlag, loc10),
				new PieceLocationDescriptor(redColonel, loc20),
				new PieceLocationDescriptor(redColonel, loc30),
				new PieceLocationDescriptor(redCaptain, loc40),
				new PieceLocationDescriptor(redCaptain, loc50),
				new PieceLocationDescriptor(redLieutenant, loc00),
				new PieceLocationDescriptor(redLieutenant, loc11),
				new PieceLocationDescriptor(redLieutenant, loc21),
				new PieceLocationDescriptor(redSergeant, loc31),
				new PieceLocationDescriptor(redSergeant, loc41),
				new PieceLocationDescriptor(redSergeant, loc51)};

		final PieceLocationDescriptor[] bluePieces = {
				new PieceLocationDescriptor(blueMarshal, loc54),
				new PieceLocationDescriptor(blueFlag, loc05),
				new PieceLocationDescriptor(blueColonel, loc15),
				new PieceLocationDescriptor(blueColonel, loc25),
				new PieceLocationDescriptor(blueCaptain,loc35),
				new PieceLocationDescriptor(blueCaptain, loc45),
				new PieceLocationDescriptor(blueLieutenant, loc04),
				new PieceLocationDescriptor(blueLieutenant, loc14),
				new PieceLocationDescriptor(blueLieutenant, loc24),
				new PieceLocationDescriptor(blueSergeant, loc34),
				new PieceLocationDescriptor(blueSergeant, loc44),
				new PieceLocationDescriptor(blueSergeant, loc55)};

		for (PieceLocationDescriptor piece : redPieces) {
			redConfiguration.add(piece);
		}
		for (PieceLocationDescriptor piece : bluePieces) {
			blueConfiguration.add(piece);		
		}

		try {
			game = GammaStrategy.makeGammaStrategyGame(redConfiguration, blueConfiguration);
			game.startGame();
		} catch (StrategyException e) {
			e.printStackTrace();
		}
		
		/*
		* The board with the initial configuration looks like this:
		*   |  0  |  1  |  2  |  3  |  4  |  5  |
		* - +-----+-----+-----+-----+-----+-----+
		* 5 |  F  | COL | COL | CPT | CPT | SGT |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	* 4 | LT  | LT  | LT  | SGT | SGT | MAR |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	* 3 |     |     |  CP | CP  |     |     |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	* 2 |     |     |  CP | CP  |     |     |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	* 1 | MAR | LT  | LT  | SGT | SGT | SGT |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	* 0 | LT  |  F  | COL | COL | CPT | CPT |
	 	* - +-----+-----+-----+-----+-----+-----+
	 	*   |  0  |  1  |  2  |  3  |  4  |  5  |
	 	*/
	}
	
	@Test
	public void RedTakesLastBluePiece() throws StrategyException {
		
	}
	
}
