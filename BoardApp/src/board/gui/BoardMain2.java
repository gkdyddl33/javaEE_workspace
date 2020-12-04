package board.gui;

import java.awt.FlowLayout;

import javax.swing.JFrame;

import db.DBManager;

public class BoardMain2 extends JFrame{

	Page2[] pageList = new Page2[Pages2.values().length];
	// 서버가동
	DBManager dbManager;
	
	public BoardMain2() {
		pageList[0] = new BoardList2(this);
		pageList[1] = new BoardWrite2(this);
		pageList[2] = new BoardContent2(this);
		
		dbManager = new DBManager();
		
		setLayout(new FlowLayout());
		// 페이지 부착
		for(Page2 page: pageList) {
			add(page);
			page.setVisible(false);
		}
		showPage(Pages2.valueOf("BoardList2").ordinal());
		
		setSize(900,700);
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
	
	public void showPage(int viewPage) {
		for(int i=0;i<pageList.length;i++) {
			if(viewPage==i) {
				pageList[i].setVisible(true);
			}else {
				pageList[i].setVisible(false);
			}
		}
	}
	public static void main(String[] args) {
		new BoardMain2();
	}
}
