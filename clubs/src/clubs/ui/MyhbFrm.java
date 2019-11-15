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

public class MyhbFrm implements ActionListener {	
	String cksql="select clbmember.*,sno,sname,classname,clbname,jbname from clbjob,clbmember,clubsinfo,students where clbmember.students_id=students.id and clbmember.clbjob_id=clbjob.id and clbmember.clubsinfo_id=clubsinfo.id and clubsinfo.id in(select clubsinfo_id from clbmember where students_id="+MainFrm.uid+")";
	JFrame w;
	JDialog jdl=new JDialog(w,"我的伙伴",true);
	//显示信息的表  id 学号
	String[] listcols={"id","社团名称","学号","姓名","班级","职务","是否领导"};
	//设置表格整行显示
	DefaultTableModel model = new DefaultTableModel(null, listcols);
	//数据显示表格
	JTable slist = new JTable(model);
	//滚动
    JScrollPane pane = new JScrollPane(slist);
	//查询模块
    JLabel cksnolabel = new JLabel("学号：");
    JTextField cksnotext=new JTextField();
    JLabel ckclasslabel = new JLabel("所属社团：");
	JComboBox ckclasstext=new JComboBox();
	JButton checkbt = new JButton("查询");
	//查询条件
	JLabel no=new JLabel("");
	JLabel no2=new JLabel("");
	//管理模块 
	JLabel mgsnolb=new JLabel("学号：");
	JComboBox mgsnotxt=new JComboBox();
	JLabel mgstlb=new JLabel("所属社团：");
	JComboBox mgsttxt=new JComboBox();
	JLabel mgzwlb=new JLabel("职务：");
	JComboBox mgzwtxt=new JComboBox();
	JLabel mgldlb=new JLabel("是否领导：");
	JComboBox mgldtxt=new JComboBox();
	JLabel ID = new JLabel("");
	JPanel jpl=new JPanel(new BorderLayout());
	MyhbFrm(JFrame jf){
		w=jf;
		jdl.setSize(600,600);		
		jdl.setLocation(100, 150);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		//布局
		//查询部分布局
		jdl.add(ckclasslabel);
		jdl.add(ckclasstext);
		jdl.add(cksnolabel);
		jdl.add(cksnotext);
		jdl.add(checkbt);
		jdl.add(no);
		no.setBounds(0,0,0,0);
		jdl.add(no2);
		no2.setBounds(0,0,0,0);
		cksnolabel.setBounds(90,10,70,20);
		cksnotext.setBounds(160,10,100,20);
		ckclasslabel.setBounds(270,10,70,20);
		ckclasstext.setBounds(340,10,100,20);
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
		
		jpl.add(mgstlb);
		mgstlb.setBounds(200, 20, 70, 20);
		jpl.add(mgsttxt);
		mgsttxt.setBounds(270, 20, 100, 20);
		
		jpl.add(mgzwlb);
		mgzwlb.setBounds(380, 20, 70, 20);
		jpl.add(mgzwtxt);
		mgzwtxt.setBounds(450, 20, 100, 20);
		
		jpl.add(mgldlb);
		mgldlb.setBounds(20, 45, 70, 20);
		jpl.add(mgldtxt);
		mgldtxt.setBounds(90, 45, 100, 20);
		
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
              //获取每一列的值，赋值给右侧编辑区{"id","社团名称","学号","姓名","班级","职务","是否领导"}
              Object value= slist.getValueAt(r, 0);
              ID.setText(value.toString());
              mgsttxt.setSelectedItem(slist.getValueAt(r, 1).toString());
              mgsnotxt.setSelectedItem(slist.getValueAt(r, 2).toString());
              mgzwtxt.setSelectedItem(slist.getValueAt(r, 5).toString());
              mgldtxt.setSelectedItem(slist.getValueAt(r, 6).toString());
            }
        });
		DbConn db=new DbConn();
		ResultSet rs=db.executeQuery("select * from clubsinfo");
		try {
			while(rs.next()){
				mgsttxt.addItem(rs.getString("clbname"));
				ckclasstext.addItem(rs.getString("clbname"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rs=db.executeQuery("select * from students");
		try {
			while(rs.next()){
				mgsnotxt.addItem(rs.getString("sno"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		rs=db.executeQuery("select * from clbjob");
		try {
			while(rs.next()){
				mgzwtxt.addItem(rs.getString("jbname"));
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		mgldtxt.addItem("是");
		mgldtxt.addItem("否");
		getData(cksql);
		mgsttxt.setEnabled(false);
		mgsnotxt.setEnabled(false);
		mgzwtxt.setEnabled(false);
		mgldtxt.setEnabled(false);
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
			no2.setText(ckclasstext.getSelectedItem().toString());
			// TODO Auto-generated method coub			
			//获取数据，绑定到数据表
			getData(cksql);
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
			cksql+=" and sno='"+no.getText()+"'";
		}
		if(!"".equals(no2.getText())){
			cksql+=" and clbname like '%"+no2.getText().trim()+"%'";
		}
		DbConn db=new DbConn();
		ResultSet rs=db.executeQuery(cksql);
		if(rs!=null){
			try {
				while(rs.next()){
					//{"id","社团名称","学号","姓名","班级","职务","是否领导"}
					model.addRow(new String[]{String.valueOf(rs.getInt("ID")),rs.getString("clbname"),rs.getString("sno"),rs.getString("sname"),rs.getString("classname"),rs.getString("jbname"),rs.getString("clbldflag")});
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
	}
}
