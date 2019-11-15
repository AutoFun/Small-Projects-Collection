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
import javax.swing.JTextField;
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

public class MstuinfoFrm implements ActionListener {	
	String cksql="select * from students where 1=1 ";
	JFrame w;
	JDialog jdl=new JDialog(w,"我的信息",true);	
	//管理模块 学号
	JLabel mgsnolb=new JLabel("学号：");
	JTextField mgsnotxt=new JTextField();
	JLabel mgsnamelb=new JLabel("姓名：");
	JTextField mgsnametxt=new JTextField();
	JLabel mgssexlb=new JLabel("性别：");
	JTextField mgssextxt=new JTextField();
	JLabel mgclasslb=new JLabel("班级：");
	JTextField mgclasstxt=new JTextField();
	JTextField mgsjobtxt=new JTextField();
	JLabel mgspwdlb=new JLabel("密码：");
	JTextField mgspwdtxt=new JTextField();
	JLabel ID = new JLabel("");
	JPanel jpl=new JPanel(new BorderLayout());
	MstuinfoFrm(JFrame jf){
		w=jf;
		jdl.setSize(600,200);		
		jdl.setLocation(100, 150);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		//布局
		//数据列表		
		jpl.setBounds(20, 10, 560, 120) ;
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
		
		DbConn db=new DbConn();
		ResultSet rs=db.executeQuery(cksql+" and id="+MainFrm.uid);
		try {
			while(rs.next()){
				mgsnotxt.setText(rs.getString("sno"));
				mgsnametxt.setText(rs.getString("sname"));
				mgssextxt.setText(rs.getString("sgender"));
				mgclasstxt.setText(rs.getString("classname"));
				mgspwdtxt.setText(rs.getString("spwd"));
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		mgsnotxt.setEditable(false);
		mgsnametxt.setEditable(false);
		mgssextxt.setEditable(false);
		mgclasstxt.setEditable(false);
		mgspwdtxt.setEditable(false);
		jpl.add(ID);
		ID.setBounds(0,0,0,0);
		ID.setVisible(false);		
		jdl.setVisible(true);
		
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	}	
}
