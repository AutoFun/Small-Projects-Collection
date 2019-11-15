package clubs.ui;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JRadioButton;
import javax.swing.JTextField;

import clubs.utils.DbConn;
public class LoginFrm implements ActionListener {
	private JFrame w = new JFrame("登录");//定义登录窗口
	private JLabel title=new JLabel("学生社团管理系统");
	private JLabel username = new JLabel("用户名：");//用户名标筿
	private JTextField usernamei = new JTextField();//登录用户名输入文本框
	private JLabel password = new JLabel("密码＿");//密码标签
	private JPasswordField passwordi = new JPasswordField();//登录输入密码文本桿	
	private JLabel utype = new JLabel("角色＿");//角色标签
	private JComboBox utypetxt = new JComboBox();//登鿉择角色
	private JButton dl = new JButton("癿  彿");//登录按钮
	private JButton qx = new JButton("釿  罿");//重置按钮
	/**
	 * 构鿠的时忙，初始化窗使
	 */
	LoginFrm(){
		//设置窗口显示位置
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();  
		w.setLocation((dim.width - w.getWidth()) / 2-150, (dim.height - w.getHeight()) / 2-150);  
		//设置窗体大小
		w.setSize(360, 280);		
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		JPanel imagePanel = (JPanel) w.getContentPane();  
		imagePanel.setOpaque(false);  
		// 把背景图片添加到分层窗格的最底层作为背景  
		w.setLayout(null);
		//添加窗体关闭
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		//设置控件位置
		title.setBounds(120, 25, 200, 20);
		title.setFont(new Font("",1,14));
		username.setBounds(70, 70, 70, 20);
		usernamei.setBounds(150, 70, 120, 20);
		password.setBounds(70, 100, 70, 20);
		passwordi.setBounds(150, 100, 120, 20);
		utype.setBounds(70, 130, 70, 20);
		utypetxt.setBounds(150, 130, 120, 20);
		dl.setBounds(100, 180, 70, 25);
		qx.setBounds(180, 180, 70, 25);
		//控件添加到窗使
		w.add(title);
		w.add(username);
		w.add(usernamei);
		w.add(password);
		w.add(passwordi);
		w.add(utype);
		w.add(utypetxt);
		w.add(dl);
		w.add(qx);
		utypetxt.addItem("管理呿");
		utypetxt.addItem("学生");
		w.setResizable(false);
		dl.addActionListener(new LoginSubmit());
		qx.addActionListener(new Reset());
		w.setVisible(true);
	}		

	@Override
	public void actionPerformed(ActionEvent e) {
	}
	/**
	 *@ClassName LoginSubmit 
	 *@Description 用户登录操作监听 
	 */
	class LoginSubmit implements ActionListener{
		boolean logflag=false;
		@Override
		public void actionPerformed(ActionEvent e) {
			//获取用户账号和密砿
			String uname=usernamei.getText().trim();
			String upassword=new String(passwordi.getPassword()).trim();	
			//数据库连接对豿
			DbConn db=new DbConn();
			String sql="select id from admin where uname='"+uname+"' and upwd='"+upassword+"'";
			//判断用户身份
			String utype="";
			if("管理呿".equals(utypetxt.getSelectedItem().toString())){
				utype="管理呿";
			}else{
				sql="select id from students where sno='"+uname+"' and spwd='"+upassword+"'";
				utype="学生";
			}			
			//登录，验证数据库账户信息
			Object uid=db.getOnlyOne(sql);
			if(uid!=null){	
				if("学生".equals(utype)){
					if(db.checkTrue("select id from clbmember where clbldflag='昿' and students_id="+uid)){
						utype="昿";
					}else{
						utype="吿";
					}
				}
				new MainFrm(uid.toString(),utype);
				w.setVisible(false);
			}else{
				JOptionPane.showMessageDialog(null,"账户或密码错诿!", "系统信息", JOptionPane.WARNING_MESSAGE);	
			}
		}
		
	}
	
	/**
	 *@ClassName Reset 
	 *@Description 重置监听
	 */
	class Reset implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			usernamei.setText("");
			passwordi.setText("");
			utypetxt.setSelectedItem("管理呿");
		}
	}
	
	/**   
	 * @Title: main  
	 * @Description: 用户登录界面 主方法入叿
	 *  @param args      
	 * @throws  
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new LoginFrm();
	}
}

