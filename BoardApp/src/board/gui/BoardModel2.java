package board.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import board.model.Notice2;

public class BoardModel2 extends AbstractTableModel{

	// 칼럼이름
	String[] column = {"notice_id","작성자","제목","등록일","조회수"};
	// 행? 정보들
	ArrayList<Notice2> list = new ArrayList<>();
	
	public int getRowCount() {
		
		return list.size();
	}
	
	public int getColumnCount() {

		return column.length;
	}

	public String getColumnName(int col) {
		return column[col];
	}
	
	public Object getValueAt(int row, int col) {// 행열에 값을 가지고 잇는것은? notice..
		
		Notice2 notice = new Notice2();
		String obj = null;
		if(col==0) {
			obj = Integer.toString(notice.getNotice_id());
		}else if(col==1) {
			obj = notice.getAuthor();
		}else if(col==2) {
			obj = notice. getTitle();
		}else if(col==3) {
			obj = notice.getRegdate();
		}else if(col==4) {
			obj = Integer.toString(notice.getHit());
		}
		
		return obj;
	}

}
