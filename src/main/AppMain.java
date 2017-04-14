package main;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.Connection;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import db.DBManager;

public class AppMain extends JFrame implements ActionListener{
	JPanel p_center, p_east, p_south;
	JTable table;
	JScrollPane scroll;
	JButton bt_save, bt_graph;
	DBManager manager=DBManager.getInstance(); //유일성을 위해서
	Connection con;
	MyModel myModel;
	PieChartPanel pie;
	JFileChooser chooser;
	FileOutputStream fos;
	
	
	public AppMain() {
		con=manager.getConnection();
		p_center  = new JPanel();
		p_east = new JPanel();
		p_south = new JPanel();
		table = new JTable(myModel = new MyModel(con));
		scroll = new JScrollPane(table);
		bt_save = new JButton("엑셀로 저장");
		bt_graph = new JButton("그래프 보기");
		pie = new PieChartPanel(con);
		chooser = new JFileChooser();
		
		p_center.setLayout(new BorderLayout());
		
		p_east.setPreferredSize(new Dimension(450, 400));
		
		p_south.add(bt_save);
		p_south.add(bt_graph);
		p_center.add(scroll);
		
		myModel.getList();
		
		add(p_center);
		add(p_east, BorderLayout.EAST);
		add(p_south, BorderLayout.SOUTH);
		
		
		//윈도우와 리스너 연결 및 끌때 연결도 끊기
		this.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				manager.disConnect(con);
				System.exit(0);
			}
		});
		
		//버튼 리스너 연결
		bt_save.addActionListener(this);
		bt_graph.addActionListener(this);
		
		setSize(900,500);
		setVisible(true);		
		
	}
	
	//엑셀로 저장
	public void save(){
		//엑셀 하나의 파일
		HSSFWorkbook workBook=new HSSFWorkbook();
		HSSFSheet sheet = workBook.createSheet("시험응시정보");		
		
		for(int i=0;i<table.getRowCount();i++){
			//HSSFRow를 생성
			HSSFRow row=sheet.createRow(i);
			for(int a=0;a<table.getColumnCount();a++){
				//HSSFColumn을 생성할 것
				HSSFCell cell = row.createCell(a);
				cell.setCellValue((String)table.getValueAt(i, a));
				System.out.print(table.getValueAt(i, a)+",");
			}
			System.out.println("");
		}
		
		int result=chooser.showSaveDialog(this);
		if(result==JFileChooser.APPROVE_OPTION){
			//사용자는 확장자 .xls을 붙여야 한다!
			
			File file = chooser.getSelectedFile();
			
			try {
				fos = new FileOutputStream(file);
				//POI workBook이 알아서 설정한다!
				workBook.write(fos);
				JOptionPane.showMessageDialog(this, "엑셀 생성 완료");
				
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				if(fos!=null){
					try {
						fos.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		
	}
	
	//그래프보기
	public void graph(){
		p_east.add(pie.showChart());
		p_east.updateUI();
	}
	
	
	public void actionPerformed(ActionEvent e) {
		Object obj= e.getSource();
		if(obj == bt_save){
			save();
		}else if(obj == bt_graph){
			graph();
		}
	}

	public static void main(String[] args) {
		new AppMain();

	}

}
