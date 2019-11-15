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
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableColumnModel;
import javax.swing.table.DefaultTableModel;

import clubs.utils.DbConn;

public class MclubsFrm implements ActionListener {	
	String cksql="select * from clubsinfo where 1=1 ";
	JFrame w;
	JDialog jdl=new JDialog(w,"社团信息管理",true);
	//显示信息的表  id 社团名称
	String[] listcols={"id","社团名称","社团简介","创办时间"};
	//设置表格整行显示
	DefaultTableModel model = new DefaultTableModel(null, listcols);
	//数据显示表格
	JTable slist = new JTable(model);
	//滚动
    JScrollPane pane = new JScrollPane(slist);
	//查询模块
	JLabel ckclbmarklabel = new JLabel("名称：");
	JTextField ckclbmarktext=new JTextField();
	JButton checkbt = new JButton("查询");
	//查询条件
	JLabel no=new JLabel("");
	JLabel no2=new JLabel("");
	//管理模块 社团名称
	JLabel mgclbnamelb=new JLabel("社团名称：");
	JTextField mgclbnametxt=new JTextField();
	JLabel mgclbmarklb=new JLabel("社团简介：");
	JTextArea mgclbmarktxt=new JTextArea();
	JLabel mgclbtimelb=new JLabel("创办时间：");
	JTextField mgclbtimetxt=new JTextField();
	JLabel ID = new JLabel("");
	JPanel jpl=new JPanel(new BorderLayout());
	JScrollPane jspane;	
	JButton addbt = new JButton("添加");
	JButton updbt = new JButton("修改");
	JButton delbt = new JButton("删除");
	JButton resbt = new JButton("重置");
	MclubsFrm(JFrame jf){
		w=jf;
		jdl.setSize(600,600);		
		jdl.setLocation(100, 150);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		//布局
		//查询部分布局
		jdl.add(ckclbmarklabel);
		jdl.add(ckclbmarktext);
		jdl.add(checkbt);
		jdl.add(no);
		no.setBounds(0,0,0,0);
		jdl.add(no2);
		no2.setBounds(0,0,0,0);
		ckclbmarklabel.setBounds(270,10,70,20);
		ckclbmarktext.setBounds(340,10,100,20);
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
		jpl.add(mgclbnamelb);
		mgclbnamelb.setBounds(20, 20, 70, 20);
		jpl.add(mgclbnametxt);
		mgclbnametxt.setBounds(90, 20, 100, 20);
		
		jpl.add(mgclbmarklb);
		mgclbmarklb.setBounds(380, 20, 70, 20);
//		jpl.add(mgclbmarktxt);
//		mgclbmarktxt.setBounds(450, 20, 100, 60);		
		mgclbmarktxt.setLineWrap(true);//设置多行文本框自动换行
		jspane=new JScrollPane(mgclbmarktxt);	//创建滚动窗格
		jpl.add(jspane);
		jspane.setBounds(450, 20, 100, 60);

		jpl.add(mgclbtimelb);
		mgclbtimelb.setBounds(200, 20, 70, 20);
		jpl.add(mgclbtimetxt);
		mgclbtimetxt.setBounds(270, 20, 100, 20);
		
		
		jpl.add(addbt);
		addbt.setBackground(new Color(245, 245, 245));
		addbt.setBounds(120, 70, 60, 31);
		jpl.add(updbt);
		updbt.setBackground(new Color(245, 245, 245));
		updbt.setBounds(190, 70, 60, 31);
		jpl.add(delbt);
		delbt.setBackground(new Color(245, 245, 245));
		delbt.setBounds(260, 70, 60, 31);
		jpl.add(resbt);
		resbt.setBackground(new Color(245, 245, 245));
		resbt.setBounds(330, 70, 60, 31);
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
              mgclbnametxt.setText(slist.getValueAt(r, 1).toString());
              mgclbmarktxt.setText(slist.getValueAt(r, 2).toString());
              mgclbtimetxt.setText(slist.getValueAt(r, 3).toString());
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
			no2.setText(ckclbmarktext.getText());
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
			String clbname=mgclbnametxt.getText().trim();
			String clbmark=mgclbmarktxt.getText().trim();
			String sclbtime=mgclbtimetxt.getText().trim();
			//信息完整性验证
			if("".equals(clbname)||"".equals(clbmark)||"".equals(sclbtime)){
				JOptionPane.showMessageDialog(null,"社团信息不完整!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			//创建数据库操作对象
			DbConn db=new DbConn();
			//社团名称唯一性验证
			if(db.checkTrue("select id from clubsinfo where clbname='"+clbname+"'")){
				JOptionPane.showMessageDialog(null,"社团名称已存在!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			//添加信息
			if(db.executeUpdate("insert into clubsinfo(clbname,clbmark,clbtime) values('"+clbname+"','"+clbmark+"','"+sclbtime+"')") >0){
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
			if(db.executeUpdate("delete from clubsinfo where id="+id)>0){
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
			String clbname=mgclbnametxt.getText().trim();
			String clbmark=mgclbmarktxt.getText().trim();
			String sclbtime=mgclbtimetxt.getText().trim();
			//信息完整性验证
			if("".equals(clbname)||"".equals(clbmark)||"".equals(sclbtime)){
				JOptionPane.showMessageDialog(null,"社团信息不完整!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			DbConn db=new DbConn();
			//社团名称唯一性验证
			if(db.checkTrue("select id from clubsinfo where clbname='"+clbname+"' and id!="+id)){
				JOptionPane.showMessageDialog(null,"社团已存在!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if(db.executeUpdate("update clubsinfo set clbname='"+clbname+"',clbmark='"+clbmark+"',clbtime='"+sclbtime+"' where id="+id)>0){
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
		if(!"".equals(no2.getText())){
			cksql+=" and clbmark like '%"+no2.getText().trim()+"%'";
		}
		DbConn db=new DbConn();
		ResultSet rs=db.executeQuery(cksql);
		if(rs!=null){
			try {
				while(rs.next()){
					//{"id","社团名称"};
					model.addRow(new String[]{String.valueOf(rs.getInt("ID")),rs.getString("clbname"),rs.getString("clbmark"),rs.getString("clbtime")});
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
		mgclbnametxt.setText("");
		mgclbmarktxt.setText("");
		mgclbtimetxt.setText("");
	}
}
