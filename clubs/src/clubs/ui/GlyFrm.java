package clubs.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import clubs.utils.DbConn;

public class GlyFrm implements ActionListener {
	String cksql="select * from admin";
	JFrame w;
	JDialog jdl=new JDialog(w,"用户信息管理",true);
	//显示信息的表
	String[] scols={"id","账号","密码"};
	//设置表格整行显示
	DefaultTableModel model = new DefaultTableModel(null, scols);
	//数据显示表格
	JTable s = new JTable(model);
	//滚动
    JScrollPane pane = new JScrollPane(s);
	//查询模块
	JLabel checkcnolabel = new JLabel("账号＿");
	JTextField checkcnotext=new JTextField();
	JButton checkbt = new JButton("查询");
	JLabel no=new JLabel("");
	//管理模块
	JLabel mgsnolb=new JLabel("账号＿");
	JTextField mgsnotxt=new JTextField();
	JLabel mgpasswordlb=new JLabel("密码＿");
	JTextField mgpasswordtxt=new JTextField();
	JLabel ID = new JLabel("");
	JPanel jpl=new JPanel(new BorderLayout());
	JButton addbt = new JButton("添加");
	JButton updbt = new JButton("修改");
	JButton delbt = new JButton("删除");
	JButton resbt = new JButton("重置");
	GlyFrm(JFrame jf){
		w=jf;
		jdl.setSize(500,400);		
		jdl.setLocation(200, 200);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		//布局
		//查询部分布局
		jdl.add(checkcnolabel);
		jdl.add(checkcnotext);
		jdl.add(checkbt);
		jdl.add(no);
		no.setBounds(0,0,0,0);
		checkcnolabel.setBounds(80,10,70,20);
		checkcnotext.setBounds(155,10,100,20);
		checkbt.setBounds(260,10,60,20);
		//数据列表		
		jdl.add(pane);
		pane.setBounds(20,40,460,200);
		jpl.setBounds(20, 250, 460, 120) ;
		jdl.add(jpl);
		jpl.setBorder(BorderFactory.createTitledBorder("编辑匿"));
		jpl.setOpaque(false);
		//数据管理部分布局
		jpl.add(mgsnolb);
		mgsnolb.setBounds(20, 30, 50, 20);
		jpl.add(mgsnotxt);
		mgsnotxt.setBounds(60, 30, 120, 20);
		jpl.add(mgpasswordlb);
		mgpasswordlb.setBounds(230, 30, 50, 20);
		jpl.add(mgpasswordtxt);
		mgpasswordtxt.setBounds(290, 30, 120, 20);
		jpl.add(addbt);
		addbt.setBounds(50, 60, 60, 20);
		jpl.add(updbt);
		updbt.setBounds(120, 60, 60, 20);
		jpl.add(delbt);
		delbt.setBounds(190, 60, 60, 20);
		jpl.add(resbt);
		resbt.setBounds(260, 60, 60, 20);
		jpl.add(ID);
		ID.setBounds(0,0,0,0);
		ID.setVisible(false);
		checkbt.addActionListener(new CheckAction());
		//设置表格整行选中
		s.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//数据列表添加鼠标监听
		s.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
            	//获取鼠标选中那一行的数据
              int r= s.getSelectedRow();
              //获取每一列的值，赋忼给右侧编辑匿
              Object value= s.getValueAt(r, 0);
              ID.setText(value.toString());
              mgsnotxt.setText(s.getValueAt(r, 1).toString());
              mgpasswordtxt.setText(s.getValueAt(r, 2).toString());
            }
        });
		//为增删改重置功能添加监听事件
		addbt.addActionListener(new AddAction());
		updbt.addActionListener(new UpdAction());
		delbt.addActionListener(new DelAction());
		resbt.addActionListener(new ResAction());
		getData(cksql);
		jdl.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}
	//查询功能实现
	class CheckAction implements ActionListener{		
		ResultSet rs=null;		
		@Override
		public void actionPerformed(ActionEvent e) {
			no.setText(checkcnotext.getText());
			// TODO Auto-generated method coub			
			//获取数据，绑定到数据衿
			getData(cksql);
		}
	}
	//添加功能实现
	class AddAction implements ActionListener{
		String sql="";
		ResultSet rs=null;
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method coub
			String sno=mgsnotxt.getText().trim();
			String password=mgpasswordtxt.getText().trim();
			if("".equals(sno)||"".equals(password)){
				JOptionPane.showMessageDialog(null,"用户信息不完敿!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			DbConn db=new DbConn();
			if(db.checkTrue("select id from admin where uname='"+sno+"'")){
				JOptionPane.showMessageDialog(null,"账号已存圿!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			int x=db.executeUpdate("insert into admin(uname,upwd) values('"+sno+"','"+password+"')");
			if(x>0){
				JOptionPane.showMessageDialog(null,"添加成功.", "系统信息", JOptionPane.INFORMATION_MESSAGE);	
			}else{
				JOptionPane.showMessageDialog(null,"系统错误01", "系统信息", JOptionPane.ERROR_MESSAGE);	
			}
			//获取数据，绑定到数据衿
			getData(cksql);
			//重置
			rst();
		}
	}
	//重置功能实现
	class ResAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method coub
			rst();
		}
	}
	//删除功能实现
	class DelAction implements ActionListener{
		ResultSet rs=null;	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method coub
			String id=ID.getText();
			DbConn db=new DbConn();
			if("".equals(id)||!db.checkTrue("select id from admin where id="+id)){
				JOptionPane.showMessageDialog(null,"请?择承要删除的信息!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if(id.equals(MainFrm.uid)){
				JOptionPane.showMessageDialog(null,"当前登录用户不能删除!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			int x=db.executeUpdate("delete from admin where id="+id);
			if(x>0){
				JOptionPane.showMessageDialog(null,"删除成功.", "系统信息", JOptionPane.INFORMATION_MESSAGE);	
				//获取数据，绑定到数据衿
				getData(cksql);
				//重置
				rst();
			}else{
				JOptionPane.showMessageDialog(null,"系统错误01", "系统信息", JOptionPane.ERROR_MESSAGE);	
			}			
		}
	}
	//修改功能实现
	class UpdAction implements ActionListener{
		ResultSet rs=null;	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method coub
			String sno=mgsnotxt.getText().trim();
			String password=mgpasswordtxt.getText().trim();
			String id=ID.getText();
			if("".equals(id)){
				JOptionPane.showMessageDialog(null,"请?择承要修改的信息!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if("".equals(sno)||"".equals(password)){
				JOptionPane.showMessageDialog(null,"用户信息不完敿!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			DbConn db=new DbConn();
			rs=db.executeQuery("select id from admin where uname='"+sno+"'");
			try {
				while(rs.next()){
					String idi=String.valueOf(rs.getInt("id"));
					if(!id.equals(idi)){
						JOptionPane.showMessageDialog(null,"账号已存圿!", "系统信息", JOptionPane.WARNING_MESSAGE);	
						return;
					}
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			int x=db.executeUpdate("update admin set uname='"+sno+"',upwd='"+password+"' where id="+id);
			if(x>0){
				JOptionPane.showMessageDialog(null,"修改成功.", "系统信息", JOptionPane.INFORMATION_MESSAGE);	
				//获取数据，绑定到数据衿
				getData(cksql);
				//重置
				rst();
			}else{
				JOptionPane.showMessageDialog(null,"系统错误01", "系统信息", JOptionPane.ERROR_MESSAGE);	
			}
		}
	}
	//获取查询结果，绑定到数据衿
	public void getData(String sql){
		//如果查询有结果，则清空以徿数据
		if(model.getRowCount()>0){
			for(int i=model.getRowCount()-1;i>=0;i--){
				model.removeRow(i);
			}
		}
		DbConn db=new DbConn();
		if(!"".equals(no.getText())){
			sql+=" where uname='"+no.getText().trim()+"'";
		}
		ResultSet rs=db.executeQuery(sql);
		if(rs!=null){
			try {
				while(rs.next()){
					model.addRow(new String[]{String.valueOf(rs.getInt("ID")),rs.getString("uname"),rs.getString("upwd")});
				}
				
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(model.getColumnCount()>0){
			DefaultTableColumnModel dcm = (DefaultTableColumnModel)s .getColumnModel();//获取列模垿  
			dcm.getColumn(0).setMinWidth(0); 
			dcm.getColumn(0).setMaxWidth(0);
		}
	}
	public void rst(){
		ID.setText("");
		mgsnotxt.setText("");
		mgpasswordtxt.setText("");
	}
}
