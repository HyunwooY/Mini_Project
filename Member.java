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
			System.out.println("이름을 입력하세요.");
			String name=scan.nextLine();
			System.out.println("이용하실 ID를 입력하세요.");
			String id=null;
			while(true) {
				id=scan.nextLine();
				String search="select membernum from member where id=?";
				cs=con.prepareCall(search);
				cs.setString(1, id);
				rs=cs.executeQuery();
				if(rs.next()) {
					System.out.println("해당ID는 사용중입니다. 다른 ID를 입력해주세요.");
					continue;
				}else {
					break;
				}
			}
			System.out.println("이용하실 비밀번호를 입력하세요.");
			String pwd=scan.nextLine();
			System.out.println("전화번호를 입력하세요.");
			String phone=scan.nextLine();
			System.out.println("Email을 입력하세요.");
			String email=scan.nextLine();
			String insertsql="insert into member values(seq_membernum.nextval,?,?,?,?,?,'일반')";//마지막은 구분
			ps=con.prepareStatement(insertsql);
			ps.setString(1,name);
			ps.setString(2,id);
			ps.setString(3,pwd);
			ps.setString(4,phone);
			ps.setString(5,email);
			ps.executeUpdate();
			System.out.println("회원가입이 완료되었습니다.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
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
			System.out.println("사업자명을 입력하세요.");
			String name=scan.nextLine();
			System.out.println("이용하실 ID를 입력하세요.");
			String id=null;
			while(true) {
				id=scan.nextLine();
				String search="select membernum from member where id=?";
				cs=con.prepareCall(search);
				cs.setString(1, id);
				rs=cs.executeQuery();
				if(rs.next()) {
					System.out.println("해당ID는 사용중입니다. 다른 ID를 입력해주세요.");
					continue;
				}else {
					break;
				}
			}
			System.out.println("이용하실 비밀번호를 입력하세요.");
			String pwd=scan.nextLine();
			System.out.println("전화번호를 입력하세요.");
			String phone=scan.nextLine();
			System.out.println("Email을 입력하세요.");
			String email=scan.nextLine();
			String insertsql="insert into member values(seq_membernum.nextval,?,?,?,?,?,'사업자')";//마지막은 구분
			ps=con.prepareStatement(insertsql);
			ps.setString(1,name);
			ps.setString(2,id);
			ps.setString(3,pwd);
			ps.setString(4,phone);
			ps.setString(5,email);
			ps.executeUpdate();
			System.out.println("회원가입이 완료되었습니다.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps,cs,rs);
		}
	}
	public void login(Connection c) { // id 패스워드가 일치하면 id리턴
		while(true) {
			Connection con=c;
			ResultSet rs=null;
			PreparedStatement ps=null;
			System.out.println("ID를 입력하세요");
			String id=scan.nextLine();
			System.out.println("비밀번호를 입력하세요");
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
					System.out.println("ID혹은 비밀번호가 일치하지 않습니다. 계속하시겠습니까? (Y/N)");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("ㅛ")) {
						continue;
					}else {
						break;
					}
				}
			}else {
				System.out.println("ID혹은 비밀번호가 일치하지 않습니다. 계속하시겠습니까? (Y/N)");
				String yn=scan.nextLine();
				if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
						||yn.equals("ㅛ")) {
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
		System.out.println("로그아웃 하시겠습니까?(Y/N)");
		String yn=scan.nextLine();
		if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
				||yn.equals("ㅛ")) {
			loginid=null;
		}
	}
	public void update(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		try {
			System.out.println("새로운 전화번호를 입력하세요.");
			String phone=scan.nextLine();
			System.out.println("새로운 Email를 입력하세요.");
			String email=scan.nextLine();
			String sql="update member set phone=?,email=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, phone);
			ps.setString(2, email);
			ps.setString(3, loginid);
			ps.executeUpdate();
			System.out.println("수정이 완료되었습니다.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("수정 중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(ps);
		}
	}
	public void withdrawal(Connection c) { // 자식레코드 삭제코드 추가해야함
		Connection con=c;
		PreparedStatement ps=null;try {
			String sql="delete from member where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, loginid);
			ps.executeUpdate();
			con.commit();
			System.out.println("회원탈퇴가 정상적으로 완료되었습니다.");
			loginid=null;
			}catch(SQLException se) {
				try {
					con.rollback();
					System.out.print("오류가 발생했습니다. 오류:");
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
				System.out.println("회원명:"+rs.getString("name"));
				System.out.println("ID:"+rs.getString("id"));
				System.out.println("전화번호:"+rs.getString("phone"));
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
			System.out.println("새로운 비밀번호를 입력하세요");
			String pwd=scan.nextLine();
			String sql="update member set pwd=? where id=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, pwd);
			ps.setString(2, loginid);
			ps.executeUpdate();
			System.out.println("비밀번호 변경이 완료되었습니다.");
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("오류가 발생했습니다. 오류:");
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
				System.out.println("회원가입시 입력하셨던 성함을 입력해주세요");
				String name=scan.nextLine();
				System.out.println("회원가입시 입력하셨던 전화번호를 입력해주세요(-포함)");
				String phone=scan.nextLine();
				String sql="select id from member where name=? and phone=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, phone);
				rs=ps.executeQuery();
				if(rs.next()) {
					System.out.println(name+"님의 아이디는 "+rs.getString("id")+"입니다");
					System.out.println("비밀번호도 찾으시겠습니까?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("ㅛ")) {
						findPwd(c);
						break;
					}else {
						break;
					}
				}else {
					System.out.println("해당하는 ID가 없습니다.");
					System.out.println("다시 입력하시겠습니까?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("ㅛ")) {
						continue;
					}else {
						break;
					}
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("오류가 발생했습니다. 오류:");
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
				System.out.println("아이디를 입력해주세요");
				String id=scan.nextLine();
				System.out.println("회원가입시 입력하셨던 성함을 입력해주세요");
				String name=scan.nextLine();
				System.out.println("회원가입시 입력하셨던 Email을 입력해주세요");
				String email=scan.nextLine();
				String sql="select pwd from member where name=? and id=? and email=?";
				ps=con.prepareStatement(sql);
				ps.setString(1, name);
				ps.setString(2, id);
				ps.setString(3, email);
				rs=ps.executeQuery();
				if(rs.next()) {
					System.out.println(name+"님의 비밀번호는는 "+rs.getString("pwd")+"입니다");
					break;
				}else {
					System.out.println("회원가입이 되어있지않거나 Email주소가 틀렸습니다.");
					System.out.println("다시 입력하시겠습니까?");
					String yn=scan.nextLine();
					if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
							||yn.equals("ㅛ")) {
						continue;
					}else {
						break;
					}
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}
	}
}
