package clubs.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class DbConn {
	Connection con = null; 
	ResultSet rs = null; 
	Statement stmt=null;
	//数据库连接字符串初始化
	String url="jdbc:mysql://localhost:3306/clubs?characterEncoding=utf-8";
	//账号密码初始化
	String username = "root";
	String password = "";
	String driver="com.mysql.jdbc.Driver";
	/**
	 * 构成方法
	 * @param driver驱动
	 * @param url链接字符
	 * @param username用户名
	 * @param password密码
	 */
	public DbConn() {
		try{
			Class.forName(driver);
			con = DriverManager.getConnection(url, username, password);
			stmt = con.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);	
		}catch(ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		catch(SQLException e)
		{
			e.printStackTrace();
		}

	}
	/**
	 * 查询获取结果集
	 * @param sql
	 * @return
	 */
	public ResultSet executeQuery(String sql) {
		try {
			rs = stmt.executeQuery(sql);
		} catch (SQLException er) {
			System.err.println(er.getMessage());
		}
		return rs;
	}
	/**
	 * 执行sql语句，返回受影响行数
	 * @param sql
	 * @return
	 */
	public int executeUpdate(String sql) { 
		int result = 0;
		try {
			result = stmt.executeUpdate(sql);
		} catch (SQLException ex) {
			System.err.println(ex.getMessage());
		}
		return result;
	}
	
	/**
	 * 执行sql语句
	 * @param sql
	 * @return
	 */
	public boolean execute(String sql) {
		boolean result = false;
		try {
			result = stmt.execute(sql);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	/**
	 * 获取一行一列数据
	 * @param sql
	 * @return
	 */
	public Object getOnlyOne(String sql){
		Object str=null;
		ResultSet rs=executeQuery(sql);
		try {
			if(rs.first()){
				str=rs.getObject(1);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;
	}
	/**
	 * 判断是否有查询结果
	 * @param sql
	 * @return
	 */
	public boolean checkTrue(String sql){
		ResultSet rs=executeQuery(sql);
		try {
			if(rs.next()){
				return true;
			}else{
				return false;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}
	//数据库连接关闭
	public void close() {
		try {
			if (con != null)
				con.close();
		} catch (Exception e) {
			System.out.print(e);
		}
		try {
			if (rs != null)
				rs.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
