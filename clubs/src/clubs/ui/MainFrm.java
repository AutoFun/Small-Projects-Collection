package clubs.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;


public class MainFrm implements ActionListener {	
	public static String uid;
	public static String utype;
	public  JFrame w = new JFrame("学生社团管理系统");
	private JMenuBar mb=new JMenuBar();
	private JMenu sysmanage = new JMenu("系统管理");
	private JMenuItem updpwd=new JMenuItem("修改密码");
	private JMenuItem backlogin=new JMenuItem("注销系统");
	private JMenu yhgl=new JMenu("用户管理");
	private JMenuItem madmin=new JMenuItem("管理员信息管琿");
	private JMenuItem mstudents=new JMenuItem("学生信息管理");
	private JMenuItem myinfo=new JMenuItem("我的信息");	
	private JMenu tsgl=new JMenu("社团管理");
	private JMenuItem mst=new JMenuItem("社团信息管理");
	private JMenuItem mstzw=new JMenuItem("社团职务管理");
	private JMenuItem mstcy=new JMenuItem("社团成员管理");
	private JMenuItem myst=new JMenuItem("我的社团");
	private JMenuItem myhb=new JMenuItem("我的伙伴");
	private JMenu jsgl=new JMenu("活动管理");
	private JMenuItem sthd=new JMenuItem("社团活动管理");	
	private JMenuItem myhd=new JMenuItem("活动查看");	
	//构成初始化界靿
	MainFrm(String usid,String ut){
		uid=usid;
		utype=ut;
		w.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();  
		w.setLocation((dim.width - w.getWidth()) / 2-150, (dim.height - w.getHeight()) / 2-150);  				
		w.setSize(600, 500);
		w.setResizable(false);
		// 把内容窗格转化为JPanel，否则不能用方法setOpaque()来使内容窗格透明  
		JPanel imagePanel = (JPanel) w.getContentPane();  
		imagePanel.setOpaque(false);  
		if(!"管理呿".equals(utype)){
			sysmanage.add(updpwd);
		}
		sysmanage.add(backlogin);
		if("管理呿".equals(utype)){
			yhgl.add(madmin);
			yhgl.add(mstudents);
			tsgl.add(mst);
			tsgl.add(mstzw);
			tsgl.add(mstcy);
			jsgl.add(sthd);
		}
		if(!"管理呿".equals(utype)){
			yhgl.add(myinfo);
			tsgl.add(myst);
			tsgl.add(myhb);
			jsgl.add(myhd);
		}		
		mb.add(yhgl);
		mb.add(tsgl);
		mb.add(jsgl);
		mb.add(sysmanage);
		w.add(mb,BorderLayout.NORTH);
		//给菜单添加监吿
		madmin.addActionListener(new glygl());
		updpwd.addActionListener(new updpwd());	
		backlogin.addActionListener(new backlog());		
		mstudents.addActionListener(new xsgl());
		mst.addActionListener(new stgl());
		mstzw.addActionListener(new zwgl());
		mstcy.addActionListener(new cygl());
		myinfo.addActionListener(new myinfo());
		myst.addActionListener(new myst());
		myhb.addActionListener(new myhb());
		sthd.addActionListener(new hdgl());
		myhd.addActionListener(new myhd());
		w.setLocation(200, 200);
		w.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

	}
	//管理员信息管琿
	class glygl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new GlyFrm(w);
		}
	}
	//学生信息管理
	class xsgl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MstudentsFrm(w);
		}
	}
	//社团信息管理
	class stgl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MclubsFrm(w);
		}
	}
	//职务管理
	class zwgl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MclbjobFrm(w);
		}
	}
	//成员信息管理
	class cygl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MclbmemberFrm(w);
		}
	}
	//我的信息
	class myinfo implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MstuinfoFrm(w);
		}
	}
	//我的社团
	class myst implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MystFrm(w);
		}
	}
	//我的伙伴
	class myhb implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MyhbFrm(w);
		}
	}
	//活动信息管理
	class hdgl implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MactFrm(w);
		}
	}
	//我的活动
	class myhd implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new MyhdFrm(w);
		}
	}
	//修改密码
	class updpwd implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			new UpdpwdFrm(w);
		}
	}
	//系统注销
	class backlog implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			w.dispose();
			new LoginFrm();
		}
	}
}