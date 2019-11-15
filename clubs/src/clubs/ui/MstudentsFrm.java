package clubs.ui;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import clubs.utils.DbConn;

public class MstudentsFrm implements ActionListener {	
	String cksql="select * from students where 1=1 ";
	JFrame w;
	JDialog jdl=new JDialog(w,"学生信息管理",true);
	//显示信息的表  id 学号
	String[] listcols={"id","学号","姓名","性别","班级","密码"};
	//设置表格整行显示
	DefaultTableModel model = new DefaultTableModel(null, listcols);
	//数据显示表格
	JTable slist = new JTable(model);
	//滚动
    JScrollPane pane = new JScrollPane(slist);
	//查询模块
	JLabel cksnolabel = new JLabel("学号：");
	JTextField cksnotext=new JTextField();
	JLabel cksnamelabel = new JLabel("姓名：");
	JTextField cksnametext=new JTextField();
	JButton checkbt = new JButton("查询");
	//查询条件
	JLabel no=new JLabel("");
	JLabel no2=new JLabel("");
	//管理模块 学号
	JLabel mgsnolb=new JLabel("学号：");
	JTextField mgsnotxt=new JTextField();
	JLabel mgsnamelb=new JLabel("姓名：");
	JTextField mgsnametxt=new JTextField();
	JLabel mgssexlb=new JLabel("性别：");
	JComboBox mgssextxt=new JComboBox();
	JLabel mgclasslb=new JLabel("班级：");
	JTextField mgclasstxt=new JTextField();
	JLabel mgspwdlb=new JLabel("密码：");
	JTextField mgspwdtxt=new JTextField();
	JLabel ID = new JLabel("");
	JPanel jpl=new JPanel(new BorderLayout());
	JButton addbt = new JButton("添加");
	JButton updbt = new JButton("修改");
	JButton delbt = new JButton("删除");
	JButton resbt = new JButton("重置");
	MstudentsFrm(JFrame jf){
		w=jf;
		jdl.setSize(600,600);		
		jdl.setLocation(100, 150);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		//布局
		//查询部分布局
		jdl.add(cksnolabel);
		jdl.add(cksnotext);
		jdl.add(cksnamelabel);
		jdl.add(cksnametext);
		jdl.add(checkbt);
		jdl.add(no);
		no.setBounds(0,0,0,0);
		jdl.add(no2);
		no2.setBounds(0,0,0,0);
		cksnolabel.setBounds(90,10,70,20);
		cksnotext.setBounds(160,10,100,20);
		cksnamelabel.setBounds(270,10,70,20);
		cksnametext.setBounds(340,10,100,20);
		checkbt.setBounds(450,7,60,26);
		checkbt.setBackground(new Color(245, 245, 245));
		//数据列表		
		jdl.add(pane);
		pane.setBounds(20,40,560,400);
		jpl.setBounds(20, 440, 560, 120) ;
		jdl.add(jpl);
		jpl.setBorder(BorderFactory.createTitledBorder("编辑区"));
		jpl.setOpaque(false);
		//数据管理部分布局
		jpl.add(mgsnolb);
		mgsnolb.setBounds(20, 20, 70, 20);
		jpl.add(mgsnotxt);
		mgsnotxt.setBounds(90, 20, 100, 20);
		
		jpl.add(mgsnamelb);
		mgsnamelb.setBounds(200, 20, 70, 20);
		jpl.add(mgsnametxt);
		mgsnametxt.setBounds(270, 20, 100, 20);
		
		jpl.add(mgssexlb);
		mgssexlb.setBounds(380, 20, 70, 20);
		jpl.add(mgssextxt);
		mgssextxt.setBounds(450, 20, 100, 20);
		
		jpl.add(mgclasslb);
		mgclasslb.setBounds(20, 45, 70, 20);
		jpl.add(mgclasstxt);
		mgclasstxt.setBounds(90, 45, 100, 20);
		
		jpl.add(mgspwdlb);
		mgspwdlb.setBounds(200, 45, 70, 20);
		jpl.add(mgspwdtxt);
		mgspwdtxt.setBounds(270, 45, 100, 20);
		
		jpl.add(addbt);
		addbt.setBackground(new Color(245, 245, 245));
		addbt.setBounds(180, 70, 60, 31);
		jpl.add(updbt);
		updbt.setBackground(new Color(245, 245, 245));
		updbt.setBounds(250, 70, 60, 31);
		jpl.add(delbt);
		delbt.setBackground(new Color(245, 245, 245));
		delbt.setBounds(320, 70, 60, 31);
		jpl.add(resbt);
		resbt.setBackground(new Color(245, 245, 245));
		resbt.setBounds(390, 70, 60, 31);
		jpl.add(ID);
		ID.setBounds(0,0,0,0);
		ID.setVisible(false);
		checkbt.addActionListener(new CheckAction());
		//创建数据库操作对象
		//DbHelper dh=new DbHelper();
		//设置表格整行选中
		slist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		//数据列表添加鼠标监听
		slist.addMouseListener(new java.awt.event.MouseAdapter(){
            public void mouseClicked(MouseEvent e) {
            	//获取鼠标选中那一行的数据
              int r= slist.getSelectedRow();
              //获取每一列的值，赋值给右侧编辑区
              Object value= slist.getValueAt(r, 0);
              ID.setText(value.toString());
              mgsnotxt.setText(slist.getValueAt(r, 1).toString());
              mgsnametxt.setText(slist.getValueAt(r, 2).toString());
              mgssextxt.setSelectedItem(slist.getValueAt(r, 3).toString());
              mgclasstxt.setText(slist.getValueAt(r, 4).toString());
              mgspwdtxt.setText(slist.getValueAt(r, 5).toString());
            }
        });
		mgssextxt.addItem("男");
		mgssextxt.addItem("女");
		
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
			no.setText(cksnotext.getText());
			no2.setText(cksnametext.getText());
			// TODO Auto-generated method coub			
			//获取数据，绑定到数据表
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
			String spwd=mgspwdtxt.getText().trim();
			String sname=mgsnametxt.getText().trim();
			String ssex=mgssextxt.getSelectedItem().toString().trim();
			String sclass=mgclasstxt.getText().trim();
			//信息完整性验证
			if("".equals(sno)||"".equals(sname)||"".equals(ssex)||"".equals(sclass)||"".equals(spwd)){
				JOptionPane.showMessageDialog(null,"学生信息不完整!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			//创建数据库操作对象
			DbConn db=new DbConn();
			//学号唯一性验证
			if(db.checkTrue("select id from students where sno='"+sno+"'")){
				JOptionPane.showMessageDialog(null,"学号已存在!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			//添加信息
			if(db.executeUpdate("insert into students(sno,sname,sgender,classname,spwd) values('"+sno+"','"+sname+"','"+ssex+"','"+sclass+"','"+spwd+"')") >0){
				JOptionPane.showMessageDialog(null,"添加成功!", "系统信息", JOptionPane.WARNING_MESSAGE);	
			}else{
				JOptionPane.showMessageDialog(null,"添加失败!", "系统信息", JOptionPane.WARNING_MESSAGE);
			}
			//获取数据，绑定到数据表
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
			//获取所要删除信息的id
			String id=ID.getText();
			//创建数据库操作对象
			if("".equals(id)){
				JOptionPane.showMessageDialog(null,"请选择所要删除的信息!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			DbConn db=new DbConn();
			if(db.executeUpdate("delete from students where id="+id)>0){
				JOptionPane.showMessageDialog(null,"删除成功!", "系统信息", JOptionPane.WARNING_MESSAGE);	
			}else{
				JOptionPane.showMessageDialog(null,"删除失败!", "系统信息", JOptionPane.WARNING_MESSAGE);
			}
			getData(cksql);
			//重置
			rst();
		}
	}
	//修改功能实现
	class UpdAction implements ActionListener{
		ResultSet rs=null;	
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method coub
			String id=ID.getText();
			if("".equals(id)){
				JOptionPane.showMessageDialog(null,"请选择所要修改的信息!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			String sno=mgsnotxt.getText().trim();
			String spwd=mgspwdtxt.getText().trim();
			String sname=mgsnametxt.getText().trim();
			String ssex=mgssextxt.getSelectedItem().toString().trim();
			String sclass=mgclasstxt.getText().trim();
			//信息完整性验证
			if("".equals(sno)||"".equals(sname)||"".equals(ssex)||"".equals(sclass)||"".equals(spwd)){
				JOptionPane.showMessageDialog(null,"学生信息不完整!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			DbConn db=new DbConn();
			//学号唯一性验证
			if(db.checkTrue("select id from students where sno='"+sno+"' and id!="+id)){
				JOptionPane.showMessageDialog(null,"学生已存在!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if(db.executeUpdate("update students set sno='"+sno+"',sname='"+sname+"',sgender='"+ssex+"',classname='"+sclass+"',spwd='"+spwd+"' where id="+id)>0){
				JOptionPane.showMessageDialog(null,"修改成功!", "系统信息", JOptionPane.WARNING_MESSAGE);	
			}else{
				JOptionPane.showMessageDialog(null,"修改失败!", "系统信息", JOptionPane.WARNING_MESSAGE);
			}
			getData(cksql);
			//重置
			rst();
		}
	}
	//获取查询结果，绑定到数据表
	public void getData(String cksql){
		//如果查询有结果，则清空以往数据
		if(model.getRowCount()>0){
			for(int i=model.getRowCount()-1;i>=0;i--){
				model.removeRow(i);
			}
		}
		if(!"".equals(no.getText())){
			cksql+=" and sno like '%"+no.getText()+"%'";
		}
		if(!"".equals(no2.getText())){
			cksql+=" and sname like '%"+no2.getText().trim()+"%'";
		}
		DbConn db=new DbConn();
		ResultSet rs=db.executeQuery(cksql);
		if(rs!=null){
			try {
				while(rs.next()){
					//{"id","学号"};
					model.addRow(new String[]{String.valueOf(rs.getInt("ID")),rs.getString("sno"),rs.getString("sname"),rs.getString("sgender"),rs.getString("classname"),rs.getString("spwd")});
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		if(model.getColumnCount()>0){
			DefaultTableColumnModel dcm = (DefaultTableColumnModel)slist .getColumnModel();//获取列模型  
			dcm.getColumn(0).setMinWidth(0); 
			dcm.getColumn(0).setMaxWidth(0);
		}
	}
	public void rst(){
		ID.setText("");
		mgsnotxt.setText("");
		mgsnametxt.setText("");
		mgssextxt.setSelectedItem("男");
		mgspwdtxt.setText("");
		mgclasstxt.setText("");
	}
}
