/*******************************************************************************
 * This files was developed for CS4233: Object-Oriented Analysis & Design.
 * The course was taken at Worcester Polytechnic Institute.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *******************************************************************************/

package strategy.game.version.epsilon;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

import strategy.common.PlayerColor;
import strategy.common.StrategyException;
import strategy.game.StrategyGameController;
import strategy.game.StrategyGameFactory;
import strategy.game.common.Location2D;
import strategy.game.common.MoveResult;
import strategy.game.common.MoveResultStatus;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;
import strategy.game.version.delta.BoardConfiguration;

public class EpsilonStrategyDrawWinTest {
	private StrategyGameController game;
	private ArrayList<PieceLocationDescriptor> redConfiguration = new ArrayList<PieceLocationDescriptor>();
	private ArrayList<PieceLocationDescriptor> blueConfiguration = new ArrayList<PieceLocationDescriptor>();
	private final static StrategyGameFactory EpsilonStrategy = StrategyGameFactory.getInstance();

	@Before
	public void setup() throws StrategyException {
		BoardConfiguration.setup();
		redConfiguration = BoardConfiguration.getRedConfiguration();
		blueConfiguration = BoardConfiguration.getBlueConfiguration();

		game = EpsilonStrategy.makeEpsilonStrategyGame(redConfiguration, blueConfiguration, null);
	}

	@Test
	public void makeRedWinningBattleMove() throws StrategyException
	{
		game.startGame();
		game.move(PieceType.MARSHAL, new Location2D(4,3), new Location2D(4,4));
		game.move(PieceType.MINER, new Location2D(4,6), new Location2D(4,5));
		final MoveResult result = 
				game.move(PieceType.MARSHAL, new Location2D(4,4), new Location2D(4,5));
		assertEquals(MoveResultStatus.OK, result.getStatus());
		assertEquals(new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.RED), new Location2D(4,5)), 
				result.getBattleWinner());
	}
}
