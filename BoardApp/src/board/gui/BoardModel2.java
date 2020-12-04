package board.gui;

import java.util.ArrayList;

import javax.swing.table.AbstractTableModel;

import board.model.Notice2;

public class BoardModel2 extends AbstractTableModel{

	// Į���̸�
	String[] column = {"notice_id","�ۼ���","����","�����","��ȸ��"};
	// ��? ������
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
	
	public Object getValueAt(int row, int col) {// �࿭�� ���� ������ �մ°���? notice..
		
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
