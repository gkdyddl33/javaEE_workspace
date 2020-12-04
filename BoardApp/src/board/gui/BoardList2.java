package board.gui;

import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import board.model.Notice2;
import board.model.NoticeDAO2;

public class BoardList2 extends Page2{

	JTable table;
	JScrollPane scroll;
	JButton bt;
		
	BoardModel2 model;
	NoticeDAO2 noticeDAO2;
	ArrayList<Notice2> boardList;
	
	public BoardList2(BoardMain2 boardMain2) {
		super(boardMain2);
		// ����
		table = new JTable(model = new BoardModel2());	// jtable�� �� ��ü�� ����
		scroll = new JScrollPane(table);	
		bt =  new JButton("�۵��");	
		
		// ��Ÿ��
		scroll.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),600));

		// ����
		add(scroll);
		add(bt);
		
		getList();	// ������ �� �ִ� ����� �ִ� �Լ�
		table.updateUI();		
		
		// �۵�� ��ư
		bt.addActionListener((e)->{
			boardMain2.showPage(Pages2.valueOf("BoardWrite2").ordinal());
		});
		
		table.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseReleased(MouseEvent e) {
				int col = 0;
				int row = table.getSelectedRow();
				Notice2 notice = boardList.get(row);
				// �󼼺��� Ŭ����..ȭ����ȯ
				BoardContent2 boardContent2 = (BoardContent2)boardMain2.pageList[Pages2.valueOf("BoardContent2").ordinal()];
			
				boardMain2.showPage(Pages2.valueOf("BoardContent2").ordinal());
			}
		});
	}
	public void getList() {
		this.boardList = noticeDAO2.selectAll();
		model.list = boardList;
	}
}
