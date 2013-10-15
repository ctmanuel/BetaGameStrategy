package strategy.game;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Font;
import java.util.Collection;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SpringLayout;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;

import strategy.common.PlayerColor;
import strategy.game.common.Location2D;
import strategy.game.common.Piece;
import strategy.game.common.PieceLocationDescriptor;
import strategy.game.common.PieceType;

public class ReporterView {

	private static Collection<PieceLocationDescriptor> RedConfiguration;
	private static Collection<PieceLocationDescriptor> BlueConfiguration;
	
	PieceLocationDescriptor temp1 = new PieceLocationDescriptor(new Piece(PieceType.MARSHAL, PlayerColor.BLUE), new Location2D(0,9));
	private JFrame frame;
	private JTable table;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					ReporterView window = new ReporterView();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public ReporterView() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {		
		frame = new JFrame();
		frame.setBounds(100, 100, 648, 349);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		SpringLayout springLayout = new SpringLayout();
		frame.getContentPane().setLayout(springLayout);
		
		JLabel lblEpsilonStrategy = new JLabel("Epsilon Strategy");
		springLayout.putConstraint(SpringLayout.NORTH, lblEpsilonStrategy, 10, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, lblEpsilonStrategy, 39, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, lblEpsilonStrategy, -369, SpringLayout.EAST, frame.getContentPane());
		lblEpsilonStrategy.setFont(new Font("Tahoma", Font.PLAIN, 23));
		frame.getContentPane().add(lblEpsilonStrategy);
		
		table = new JTable();
		table.setEnabled(false);
		springLayout.putConstraint(SpringLayout.SOUTH, lblEpsilonStrategy, -64, SpringLayout.NORTH, table);
		springLayout.putConstraint(SpringLayout.NORTH, table, 102, SpringLayout.NORTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.WEST, table, 0, SpringLayout.WEST, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.SOUTH, table, 0, SpringLayout.SOUTH, frame.getContentPane());
		springLayout.putConstraint(SpringLayout.EAST, table, 0, SpringLayout.EAST, frame.getContentPane());
		table.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		frame.getContentPane().add(table);
		table.setModel(new DefaultTableModel(
			new Object[][] {
				{temp1.getPiece().getType().getPrintableName(), null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
				{null, null, null, null, null, null, null, null, null, null},
			},
			new String[] {
				"New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column", "New column"
			}
		));
		table.getColumnModel().getColumn(0).setPreferredWidth(50);
		table.getColumnModel().getColumn(1).setPreferredWidth(50);
		table.getColumnModel().getColumn(2).setPreferredWidth(50);
		table.getColumnModel().getColumn(3).setPreferredWidth(50);
		table.getColumnModel().getColumn(4).setPreferredWidth(50);
		table.getColumnModel().getColumn(5).setPreferredWidth(50);
		table.getColumnModel().getColumn(6).setPreferredWidth(50);
		table.getColumnModel().getColumn(7).setPreferredWidth(50);
		table.getColumnModel().getColumn(8).setPreferredWidth(50);
		table.getColumnModel().getColumn(9).setPreferredWidth(50);
		table.setBackground(Color.WHITE);
		table.setForeground(Color.BLACK);
	}
}
