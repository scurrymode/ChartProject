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
	
	//성별분석
	public void getGenderData(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		StringBuffer sb= new StringBuffer();
		sb.append("select gender, count(gender) as 응시자수, (select count(*) from score) as 총학생수, ");
		sb.append("(count(gender)/(select count(*) from score))*100 as 비율  from score group by gender");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			dataset = new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("gender"), rs.getInt("비율"));
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
	//학년분석
	public void getGradeData(){
		PreparedStatement pstmt=null;
		ResultSet rs = null;
		
		StringBuffer sb= new StringBuffer();
		sb.append("select grade, count(grade) as 응시자수, (select count(*) from score) as 총학생수, ");
		sb.append("(count(grade)/(select count(*) from score))*100 as 비율  from score group by grade");
		
		try {
			pstmt = con.prepareStatement(sb.toString());
			rs=pstmt.executeQuery();
			
			dataset = new DefaultPieDataset();
			
			while(rs.next()){
				dataset.setValue(rs.getString("grade"), rs.getInt("비율"));
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
		chart = ChartFactory.createPieChart("성적현황", dataset, true, true, false);
		
		//현재 차트에 설정된 폰트를 한글폰트로 바꾸지 않으면 깨져보인다.
		System.out.println(chart.getTitle().getFont().getFontName());
		Font old = chart.getTitle().getFont();
		chart.getTitle().setFont(new Font("돋움", old.getStyle(), old.getSize()));
		
		Font oldLe=chart.getLegend().getItemFont();
		chart.getLegend().setItemFont(new Font("돋움", oldLe.getStyle(), oldLe.getSize()));
		
		PiePlot plot=(PiePlot)chart.getPlot();
		Font oldpl=plot.getLabelFont();
		
		plot.setLabelFont(new Font("돋움",oldpl.getStyle(),oldpl.getSize()));
		
		ChartPanel chartPanel = new ChartPanel(chart);
		return chartPanel;
	}
	
	
	
	
	

}
