package main;

import java.awt.Font;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PiePlot;
import org.jfree.data.general.DefaultPieDataset;

public class PieChartPanel{
	JFreeChart chart;
	DefaultPieDataset dataset;
	Connection con;
	
	public PieChartPanel(Connection con) {
		this.con=con;
	}
	
	//�����м�
	public void getGenderData(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		StringBuffer sb= new StringBuffer();
		sb.append("select gender, count(gender) as �����ڼ�, (select count(*) from score) as ���л���, ");
		sb.append("(count(gender)/(select count(*) from score))*100 as ����  from score group by gender");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			dataset = new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("gender"), rs.getInt("����"));
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
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
	//�г�м�
	public void getGradeData(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		StringBuffer sb= new StringBuffer();
		sb.append("select grade, count(grade) as �����ڼ�, (select count(*) from score) as ���л���, ");
		sb.append("(count(grade)/(select count(*) from score))*100 as ����  from score group by grade");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			dataset = new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("grade"), rs.getInt("����"));
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
					rs.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	
	public ChartPanel showChart(){
		//getGenderData();
		getGradeData();
		chart = ChartFactory.createPieChart("������Ȳ", dataset, true, true, false);
		
		//���� ��Ʈ�� ������ ��Ʈ�� �ѱ���Ʈ�� �ٲ��� ������ �������δ�.
		System.out.println(chart.getTitle().getFont().getFontName());
		Font old = chart.getTitle().getFont();
		chart.getTitle().setFont(new Font("����", old.getStyle(), old.getSize()));
		
		Font oldLe=chart.getLegend().getItemFont();
		chart.getLegend().setItemFont(new Font("����", oldLe.getStyle(), oldLe.getSize()));
		
		PiePlot plot=(PiePlot)chart.getPlot();
		Font oldpl=plot.getLabelFont();
		
		plot.setLabelFont(new Font("����",oldpl.getStyle(),oldpl.getSize()));
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	
	
	
	

}
