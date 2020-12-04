/*게시판 목록 페이지*/
package board.gui;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import board.model.Notice;
import board.model.NoticeDAO;

public class BoardList extends Page{

	JTable table;
	JScrollPane scroll;
	JButton bt;
	
	BoardModel model;
	NoticeDAO noticeDAO;
	ArrayList<Notice> boardList;	// 추후 사용할 일이 있을거 대비
	
	public BoardList(BoardMain boardMain) {
		super(boardMain);
		// 생성
		table = new JTable(model = new BoardModel());	// jtable과 모델 객체와 연결
		scroll = new JScrollPane(table);	
		bt =  new JButton("글등록");	
		noticeDAO = new NoticeDAO();
		
		// 스타일		
		scroll.setPreferredSize(new Dimension((int)this.getPreferredSize().getWidth(),600));
//		this.setBackground(Color.YELLOW);
		
		// 조립
		add(scroll);
		add(bt);
		
		getList();
		table.updateUI();
		
		bt.addActionListener((e)->{
			boardMain.showPage(Pages.valueOf("BoardWrite").ordinal());			
		});
		
		// (데이터 다 넣은 후) 테이블 중 하나의 레코드를 선택하면 상세보기 보여주기!
		// -> boardContent 이용
		table.addMouseListener(new MouseAdapter() {			
			public void mouseReleased(MouseEvent e) {
				
				// 상세보기로 가기전에 notice_id를 추출! -> 누른 데이터를 가져와야 하므로
				// -> boardContent 상세보기에서 데이터를 채워넣아야 하므로 메소드 생성
				int col=0;
				int row = table.getSelectedRow();
				Notice notice = boardList.get(row);
				BoardContent boardContent = (BoardContent)boardMain.pageList[Pages.valueOf("BoardContent").ordinal()];
				boardContent.setData(notice);
				
				boardMain.showPage(Pages.valueOf("BoardContent").ordinal());	// 화면전환
			}
		});
	}
	
	// DAO를 이용하여 데이터 가져오기(BoardModel)
	public void getList() {
		this.boardList = noticeDAO.selectAll(); // 멤버변수에도 알려주고 한꺼번에 데이터도 가져오고
																 // 일석이조!!
		model.list = boardList;
	}
}
