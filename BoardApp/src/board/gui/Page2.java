package board.gui;

import java.awt.Dimension;

import javax.swing.JPanel;

public class Page2 extends JPanel{
	
	BoardMain2 boardMain2;
	
	public Page2(BoardMain2 boardMain2) {
		this.boardMain2=boardMain2;
		this.setPreferredSize(new Dimension(880,680));
	}
}
