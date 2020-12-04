package board.gui;

import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

import board.model.Notice2;
import board.model.NoticeDAO2;

public class BoardWrite2 extends Page2{

	JTextField t_author;
	JTextField t_title;
	JTextArea area;
	JScrollPane scroll;
	JButton bt;
	NoticeDAO2 noticeDAO2;
	
	public BoardWrite2(BoardMain2 boardMain2) {
		super(boardMain2);
		// 생성
		t_author = new JTextField();
		t_title = new JTextField();
		area = new JTextArea();
		scroll = new JScrollPane(area);
		bt = new JButton("등록");
		// 스타일
		t_author.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,25));
		t_title.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,25));
		scroll.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth()-10,500));
		// 조립
		add(t_author);
		add(t_title);
		add(scroll);
		add(bt);
		
		// 글등록 구현.. 목록에 추가 - vo에 아서 목록에 넣기
		bt.addActionListener((e)->{
			Notice2 notice = new Notice2();
			notice.setAuthor(t_author.getText());
			notice.setTitle(t_title.getText());
			notice.setContent(area.getText());
			
			int result = noticeDAO2.regist(notice);
			if(result == 0) {
				JOptionPane.showMessageDialog(BoardWrite2.this, "등록실패");
			}else {
				JOptionPane.showMessageDialog(BoardWrite2.this, "등록성공");
			}
		});
	}

	
}
