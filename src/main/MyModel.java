package main;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

import javax.swing.event.TableModelEvent;
import javax.swing.event.TableModelListener;
import javax.swing.table.AbstractTableModel;

public class MyModel extends AbstractTableModel {
	Vector<String> columnName;
	Vector<Vector> data = new Vector<Vector>();
	Connection con;
	
	public MyModel(Connection con) {
		this.con=con;

		columnName = new Vector<String>();
		columnName.add("score_id");
		columnName.add("�г�");
		columnName.add("����");
		columnName.add("����");
		columnName.add("����");
		columnName.add("����");	
	}
	
	//��� ���ڵ� ��������
	public void getList(){
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String sql = "select * from score";
		
		try {
			pstmt=con.prepareStatement(sql);
			rs = pstmt.executeQuery();
			
			while(rs.next()){
				Vector vec = new Vector();

				vec.add(rs.getString("score_id"));
				vec.add(rs.getString("grade"));
				vec.add(rs.getString("gender"));
				vec.add(rs.getString("kor"));
				vec.add(rs.getString("eng"));
				vec.add(rs.getString("math"));
				
				data.add(vec);
			}
			
		} catch (SQLException e) {
			e.printStackTrace();
		} finally {
			if(rs!=null){
				try {
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(pstmt!=null){
				try {
					pstmt.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}

	public int getColumnCount() {
		return columnName.size();
	}

	public int getRowCount() {
		return data.size();
	}

	
	public String getColumnName(int col) {
		return columnName.elementAt(col);
	}
	
	//���������ϰ� �ϱ�
	public boolean isCellEditable(int row, int col) {
		boolean flag = true;
		if(col==0){
			flag = false;
		}
		return flag;
	}

	public Object getValueAt(int row, int col) {
		return data.elementAt(row).elementAt(col);
	}
	
	//�����Ѱ� �ݿ��ǰ� �ϱ�
	public void setValueAt(Object value, int row, int col) {
		data.elementAt(row).set(col, value);
	}

}
