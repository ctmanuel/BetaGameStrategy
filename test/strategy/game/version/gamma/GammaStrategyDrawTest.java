package strategy.game.version.gamma;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
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
	private Location loc00 = new Location2D(0,0);
	private Location loc10 = new Location2D(1,0);
	private Location loc20 = new Location2D(2,0);
	private Location loc30 = new Location2D(3,0);
	private Location loc40 = new Location2D(4,0);
	private Location loc50 = new Location2D(5,0);
	private Location loc01 = new Location2D(0,1);
	private Location loc11 = new Location2D(1,1);
	private Location loc21 = new Location2D(2,1);
	private Location loc31 = new Location2D(3,1);
	private Location loc41 = new Location2D(4,1);
	private Location loc51 = new Location2D(5,1);
	private Location loc52 = new Location2D(5,2);
	private Location loc02 = new Location2D(0,2);
	private Location loc12 = new Location2D(1,2);
	private Location loc13 = new Location2D(1,3);
	private Location loc03 = new Location2D(0,3);
	private Location loc53 = new Location2D(5,3);
	private Location loc04 = new Location2D(0,4);
	private Location loc14 = new Location2D(1,4);
	private Location loc24 = new Location2D(2,4);
	private Location loc34 = new Location2D(3,4);
	private Location loc44 = new Location2D(4,4);
	private Location loc54 = new Location2D(5,4);
	private Location loc05 = new Location2D(0,5);
	private Location loc15 = new Location2D(1,5);
	private Location loc25 = new Location2D(2,5);
	private Location loc35 = new Location2D(3,5);
	private Location loc45 = new Location2D(4,5);
	private Location loc55 = new Location2D(5,5);

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
	public void Draw() throws StrategyException {
		game.move(redMarshal.getType(), loc01, loc02);
		game.move(blueMarshal.getType(), loc54, loc53);
		game.move(redMarshal.getType(), loc02, loc03);
		game.move(blueMarshal.getType(), loc53, loc52);
		game.move(redMarshal.getType(), loc03, loc04);
		game.move(blueMarshal.getType(), loc52, loc51);
		game.move(redMarshal.getType(), loc04, loc14);
		game.move(blueMarshal.getType(), loc51, loc50);
		game.move(redMarshal.getType(), loc14, loc15);
		game.move(blueMarshal.getType(), loc50, loc40);
		game.move(redMarshal.getType(), loc15, loc25);
		game.move(blueMarshal.getType(), loc40, loc41);
		game.move(redMarshal.getType(), loc25, loc24);
		game.move(blueMarshal.getType(), loc41, loc31);
		game.move(redMarshal.getType(), loc24, loc34);
		game.move(blueMarshal.getType(), loc31, loc30);
		game.move(redMarshal.getType(), loc34, loc35);
		game.move(blueMarshal.getType(), loc30, loc20);
		game.move(redMarshal.getType(), loc35, loc45);
		game.move(blueMarshal.getType(), loc20, loc21);
		game.move(redMarshal.getType(), loc45, loc44);
		game.move(blueMarshal.getType(), loc21, loc11);
		game.move(redMarshal.getType(), loc44, loc54);
		game.move(blueMarshal.getType(), loc11, loc01);
		game.move(redMarshal.getType(), loc54, loc55);
		game.move(blueMarshal.getType(), loc01, loc00);
		game.move(redMarshal.getType(), loc55, loc54);
		game.move(blueMarshal.getType(), loc00, loc01);
		game.move(redMarshal.getType(), loc54, loc53);
		game.move(blueMarshal.getType(), loc01, loc11);
		game.move(redMarshal.getType(), loc53, loc52);
		game.move(blueMarshal.getType(), loc11, loc21);
		game.move(redMarshal.getType(), loc52, loc51);
		game.move(blueMarshal.getType(), loc21, loc31);
		game.move(redMarshal.getType(), loc51, loc41);
		final MoveResult result = game.move(blueMarshal.getType(), loc31, loc41);
		assertEquals(MoveResultStatus.DRAW, result.getStatus());
	}
	
	@Test
	public void AllBluePiecesRemovedRedAttacks() throws StrategyException {
		game.move(redMarshal.getType(), loc01, loc02);
		game.move(blueLieutenant.getType(), loc04, loc03);
		game.move(redMarshal.getType(), loc02, loc03);
		game.move(blueLieutenant.getType(), loc14, loc13);
		game.move(redMarshal.getType(), loc03, loc13);
		game.move(blueColonel.getType(), loc15, loc14);
		game.move(redMarshal.getType(), loc13, loc14);
		game.move(blueMarshal.getType(), loc54, loc53);
		game.move(redMarshal.getType(), loc14, loc24);
		game.move(blueMarshal.getType(), loc53, loc52);
		game.move(redMarshal.getType(), loc24, loc25);
		game.move(blueMarshal.getType(), loc52, loc53);
		game.move(redMarshal.getType(), loc25, loc35);
		game.move(blueMarshal.getType(), loc53, loc54);
		game.move(redMarshal.getType(), loc35, loc34);
		game.move(blueMarshal.getType(), loc54, loc53);
		game.move(redMarshal.getType(), loc34, loc44);
		game.move(blueMarshal.getType(), loc53, loc52);
		game.move(redMarshal.getType(), loc44, loc45);
		game.move(blueMarshal.getType(), loc52, loc53);
		game.move(redMarshal.getType(), loc45, loc55);
		game.move(blueMarshal.getType(), loc53, loc54);
		final MoveResult result = game.move(redMarshal.getType(), loc55, loc54);
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());
	}
	
	@Test
	public void AllBluePiecesRemovedBlueAttacks() throws StrategyException {
		game.move(redMarshal.getType(), loc01, loc02);
		game.move(blueLieutenant.getType(), loc04, loc03);
		game.move(redMarshal.getType(), loc02, loc03);
		game.move(blueMarshal.getType(), loc54, loc53);
		game.move(redMarshal.getType(), loc03, loc04);
		game.move(blueLieutenant.getType(), loc14, loc04);
		game.move(redMarshal.getType(), loc14, loc15);
		game.move(blueColonel.getType(), loc25, loc15);
		game.move(redMarshal.getType(), loc25, loc24);
		game.move(blueSergeant.getType(), loc34, loc24);
		game.move(redMarshal.getType(), loc34, loc35);
		game.move(blueCaptain.getType(), loc45, loc35);
		game.move(redMarshal.getType(), loc45, loc44);
		game.move(blueSergeant.getType(), loc55, loc54);
		game.move(redMarshal.getType(), loc44, loc54);
		game.move(blueMarshal.getType(), loc53, loc54); 
		final MoveResult result = game.move(redLieutenant.getType(), loc11, loc12);
		assertEquals(MoveResultStatus.RED_WINS, result.getStatus());		
	}
	
	
}
