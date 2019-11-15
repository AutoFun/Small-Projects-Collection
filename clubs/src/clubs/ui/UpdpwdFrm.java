package clubs.ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.ResultSet;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import clubs.utils.DbConn;

public class UpdpwdFrm implements ActionListener {	
	JFrame w;
	JDialog jdl=new JDialog(w,"修改登录密码",true);
	String sid="";
	JLabel opwdlb=new JLabel("原密码：");
	JTextField opwdtxt=new JTextField("");
	JLabel npwdlb=new JLabel("新密码：");
	JTextField npwdtxt=new JTextField("");
	JLabel dnpwdlb=new JLabel("确认密码：");
	JTextField dnpwdtxt=new JTextField("");
	JButton qdbt = new JButton("确  定");
	UpdpwdFrm(JFrame jf){
		w=jf;
		jdl.setSize(269,260);		
		jdl.setLocation(200, 260);
		jdl.setLayout(null);
		jdl.setDefaultCloseOperation(JDialog.DISPOSE_ON_CLOSE);
		jdl.setResizable(false);
		jdl.add(opwdlb);
		jdl.add(opwdtxt);
		jdl.add(npwdlb);
		jdl.add(npwdtxt);
		jdl.add(dnpwdlb);
		jdl.add(dnpwdtxt);
		jdl.add(qdbt);
		opwdlb.setBounds(30, 40, 80, 20);
		opwdtxt.setBounds(115, 40, 120, 20);
		npwdlb.setBounds(30, 65, 80, 20);
		npwdtxt.setBounds(115, 65, 120, 20);
		dnpwdlb.setBounds(30, 90, 80, 20);
		dnpwdtxt.setBounds(115, 90, 120, 20);
		qdbt.setBounds(150, 160, 70, 20);	
		//布局
		qdbt.addActionListener(new QdbtAction());
		jdl.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method coub
	}
	//修改密码提交
	class QdbtAction implements ActionListener{		
		ResultSet rs=null;		
		@Override
		public void actionPerformed(ActionEvent e) {
			
			if("".equals(opwdtxt.getText().trim())||"".equals(npwdtxt.getText().trim())){
				JOptionPane.showMessageDialog(null,"原密码不会为空!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			if(!npwdtxt.getText().trim().equals(dnpwdtxt.getText().trim())){
				JOptionPane.showMessageDialog(null,"两次密码输入不一致!", "系统信息", JOptionPane.WARNING_MESSAGE);
				return;
			}
			DbConn db=new DbConn();
			String sql="select id from students where spwd='"+opwdtxt.getText()+"' and id="+MainFrm.uid;
			Object id=db.getOnlyOne(sql);
			if(id==null){
				JOptionPane.showMessageDialog(null,"原密码输入有误!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
			sql="update students set spwd='"+npwdtxt.getText()+"' where id="+id;
			
			int x=db.executeUpdate(sql);
			if(x>0){
				JOptionPane.showMessageDialog(null,"密码修改成功!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}else{
				JOptionPane.showMessageDialog(null,"密码修改失败!", "系统信息", JOptionPane.WARNING_MESSAGE);	
				return;
			}
		}
	}		
}
