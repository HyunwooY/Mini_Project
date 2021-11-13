package project1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;


public class Member {
	private static String loginid=null;
	Scanner scan=new Scanner(System.in);
	public void joinNomal(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		PreparedStatement cs=null;
		ResultSet rs=null;
		try {
			System.out.println("�̸��� �Է��ϼ���.");
			String name=scan.nextLine();
			System.out.println("�̿��Ͻ� ID�� �Է��ϼ���.");
			String id=null;
			while(true) {
				id=scan.nextLine();
				String search="select membernum from member where id=?";
				cs=con.prepareCall(search);
				cs.setString(1, id);
				rs=cs.executeQuery();
				if(rs.next()) {
					System.out.println("�ش�ID�� ������Դϴ�. �ٸ� ID�� �Է����ּ���.");
					continue;
				}else {
					break;
				}
			}
			System.out.println("�̿��Ͻ� ��й�ȣ�� �Է��ϼ���.");
			String pwd=scan.nextLine();
			System.out.println("��ȭ��ȣ�� �Է��ϼ���.");
			String phone=scan.nextLine();
			System.out.println("Email�� �Է��ϼ���.");
			String email=scan.nextLine();
			String insertsql="insert into member values(seq_membernum.nextval,?,?,?,?,?,'�Ϲ�')";//�������� ����
			ps=con.prepareStatement(insertsql);
			ps.setString(1,name);
			ps.setString(2,id);
			ps.setString(3,pwd);
			ps.setString(4,phone);
			ps.setString(5,email);
			ps.executeUpdate();
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("�Է��� ������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps,cs,rs);
		}
	}
	public void joinen(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		PreparedStatement cs=null;
		ResultSet rs=null;
		try {
			System.out.println("����ڸ��� �Է��ϼ���.");
			String name=scan.nextLine();
			System.out.println("�̿��Ͻ� ID�� �Է��ϼ���.");
			String id=null;
			while(true) {
				id=scan.nextLine();
				String search="select membernum from member where id=?";
				cs=con.prepareCall(search);
				cs.setString(1, id);
				rs=cs.executeQuery();
				if(rs.next()) {
					System.out.println("�ش�ID�� ������Դϴ�. �ٸ� ID�� �Է����ּ���.");
					continue;
				}else {
					break;
				}
			}
			System.out.println("�̿��Ͻ� ��й�ȣ�� �Է��ϼ���.");
			String pwd=scan.nextLine();
			System.out.println("��ȭ��ȣ�� �Է��ϼ���.");
			String phone=scan.nextLine();
			System.out.println("Email�� �Է��ϼ���.");
			String email=scan.nextLine();
			String insertsql="insert into member values(seq_membernum.nextval,?,?,?,?,?,'�����')";//�������� ����
			ps=con.prepareStatement(insertsql);
			ps.setString(1,name);
			ps.setString(2,id);
			ps.setString(3,pwd);
			ps.setString(4,phone);
			ps.setString(5,email);
			ps.executeUpdate();
			System.out.println("ȸ�������� �Ϸ�Ǿ����ϴ�.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("�Է��� ������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps,cs,rs);
		}
	}
	public void login(Connection c) { // id �н����尡 ��ġ�ϸ� id����
		while(true) {
			Connection con=c;
			ResultSet rs=null;
			PreparedStatement ps=null;
			System.out.println("ID�� �Է��ϼ���");
			String id=scan.nextLine();
			System.out.println("��й�ȣ�� �Է��ϼ���");
			String pwd=scan.nextLine();
			try {
			String sql="select pwd from member where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			if(rs.next()) {
				if((rs.getString("pwd")).equals(pwd)) {
					loginid=id;break;
				}else {
					System.out.println("IDȤ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�. ����Ͻðڽ��ϱ�? (Y/N)");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("��")) {
						continue;
					}else {
						break;
					}
				}
			}else {
				System.out.println("IDȤ�� ��й�ȣ�� ��ġ���� �ʽ��ϴ�. ����Ͻðڽ��ϱ�? (Y/N)");
				String yn=scan.nextLine();
				if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
						||yn.equals("��")) {
					break;
				}else {
					continue;
				}
			}
			}catch(SQLException se) {
				se.printStackTrace();
			}finally {
				Connect.close(ps,rs);
			}
			
		}
	}
	public void logout() {
		System.out.println("�α׾ƿ� �Ͻðڽ��ϱ�?(Y/N)");
		String yn=scan.nextLine();
		if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
				||yn.equals("��")) {
			loginid=null;
		}
	}
	public void update(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		try {
			System.out.println("���ο� ��ȭ��ȣ�� �Է��ϼ���.");
			String phone=scan.nextLine();
			System.out.println("���ο� Email�� �Է��ϼ���.");
			String email=scan.nextLine();
			String sql="update member set phone=?,email=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setString(2, email);
			ps.setString(3, loginid);
			ps.executeUpdate();
			System.out.println("������ �Ϸ�Ǿ����ϴ�.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("���� �� ������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(ps);
		}
	}
	public void withdrawal(Connection c) { // �ڽķ��ڵ� �����ڵ� �߰��ؾ���
		Connection con=c;
		PreparedStatement ps=null;try {
			String sql="delete from member where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, loginid);
			ps.executeUpdate();
			con.commit();
			System.out.println("ȸ��Ż�� ���������� �Ϸ�Ǿ����ϴ�.");
			loginid=null;
			}catch(SQLException se) {
				try {
					con.rollback();
					System.out.print("������ �߻��߽��ϴ�. ����:");
					se.printStackTrace();
				}catch(SQLException s) {
					s.printStackTrace();
				}
			}finally {
				Connect.close(ps);
			}
	}
	public static String getLoginid() {
		return loginid;
	}
	public String getSortation(String id,Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="select sortation from member where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			if(rs.next()) {
				return rs.getString("sortation");
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			Connect.close(ps,rs);
		}
		return null;
	}
	public void check(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			String sql="select * from member where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, loginid);
			rs=ps.executeQuery();
			if(rs.next()) {
				System.out.println("ȸ����:"+rs.getString("name"));
				System.out.println("ID:"+rs.getString("id"));
				System.out.println("��ȭ��ȣ:"+rs.getString("phone"));
				System.out.println("Email:"+rs.getString("email"));
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			Connect.close(ps, rs);
		}
	}
	public void changePwd(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		try {
			System.out.println("���ο� ��й�ȣ�� �Է��ϼ���");
			String pwd=scan.nextLine();
			String sql="update member set pwd=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, pwd);
			ps.setString(2, loginid);
			ps.executeUpdate();
			System.out.println("��й�ȣ ������ �Ϸ�Ǿ����ϴ�.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(ps);
		}
	}
	public void findId(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			while(true) {
				System.out.println("ȸ�����Խ� �Է��ϼ̴� ������ �Է����ּ���");
				String name=scan.nextLine();
				System.out.println("ȸ�����Խ� �Է��ϼ̴� ��ȭ��ȣ�� �Է����ּ���(-����)");
				String phone=scan.nextLine();
				String sql="select id from member where name=? and phone=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, phone);
				rs=ps.executeQuery();
				if(rs.next()) {
					System.out.println(name+"���� ���̵�� "+rs.getString("id")+"�Դϴ�");
					System.out.println("��й�ȣ�� ã���ðڽ��ϱ�?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("��")) {
						findPwd(c);
						break;
					}else {
						break;
					}
				}else {
					System.out.println("�ش��ϴ� ID�� �����ϴ�.");
					System.out.println("�ٽ� �Է��Ͻðڽ��ϱ�?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("��")) {
						continue;
					}else {
						break;
					}
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(ps,rs);
		}
	}
	public void findPwd(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			while(true) {
				System.out.println("���̵� �Է����ּ���");
				String id=scan.nextLine();
				System.out.println("ȸ�����Խ� �Է��ϼ̴� ������ �Է����ּ���");
				String name=scan.nextLine();
				System.out.println("ȸ�����Խ� �Է��ϼ̴� Email�� �Է����ּ���");
				String email=scan.nextLine();
				String sql="select pwd from member where name=? and id=? and email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, id);
				ps.setString(3, email);
				rs=ps.executeQuery();
				if(rs.next()) {
					System.out.println(name+"���� ��й�ȣ�´� "+rs.getString("pwd")+"�Դϴ�");
					break;
				}else {
					System.out.println("ȸ�������� �Ǿ������ʰų� Email�ּҰ� Ʋ�Ƚ��ϴ�.");
					System.out.println("�ٽ� �Է��Ͻðڽ��ϱ�?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("��")) {
						continue;
					}else {
						break;
					}
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("������ �߻��߽��ϴ�. ����:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
	}
}
