package project1;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class Admin {
	private String id=Member.getLoginid();
	Scanner scan=new Scanner(System.in);
	public void inputroom(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		try {
		System.out.println("방이름을 입력하세요.");
		String rname=scan.nextLine();
		System.out.println("방크기를 입력하세요.");
		String rsize=scan.nextLine();
		System.out.println("가격을 입력하세요.");
		String price=scan.nextLine();
		String insertsql="insert into room(rnum,rname,rsize,price, provider) values(seq_roomnum.nextval,?,?,?,?)";
		ps=con.prepareStatement(insertsql);
		ps.setString(1,rname);
		ps.setString(2,rsize);
		ps.setString(3,price);
		ps.setString(4, id);
		ps.executeUpdate();
		con.commit();
		System.out.println("방정보 입력이 완료되었습니다.");
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps);
		}
		
	}
	public void showroom(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		PreparedStatement ps1=null;
		ResultSet rs1=null;
		PreparedStatement ps2=null;
		ResultSet rs2=null;
		try {
			String sql="select rnum,rname,rsize,price from room "
					+ "where provider=? order by rnum";
			ps=con.prepareStatement(sql);
			ps.setString(1, id);
			rs=ps.executeQuery();
			System.out.println("<<<<<"+id+"님의 등록정보>>>>>");
			while(rs.next()) {
				System.out.println("등록번호:"+rs.getInt("rnum"));
				System.out.println("이름:"+rs.getString("rname"));
				System.out.println("크기:"+rs.getString("rsize"));
				System.out.println("가격:"+rs.getString("price")+"원");
				String review="select rnum,round(avg(star),1) star from review group by rnum having rnum=?";
				ps1=con.prepareStatement(review);
				ps1.setInt(1, rs.getInt("rnum"));
				rs1=ps1.executeQuery();
				if(rs1.next()) {
					System.out.println("평점:"+rs1.getString("star"));
					String osr="select r.id,v.osr from reservation r,review v where r.resernum=v.resernum and v.rnum=?";
					ps2=con.prepareStatement(osr);
					ps2.setInt(1, rs.getInt("rnum"));
					rs2=ps2.executeQuery();
					System.out.println("한줄평");
					while(rs2.next()) {
						String rid=rs2.getString("id");
						System.out.println("  "+rid.substring(0, 2)+"****님:"+rs2.getString("osr"));
					}
				}else {
					System.out.println("등록된 리뷰없음");
				}
				System.out.println("---------------------");
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			Connect.close(ps,ps1,ps2,rs,rs1,rs2);
		}
	}
	public void updateroom(Connection c) {
		Connection con=c;
		PreparedStatement searchps=null;
		PreparedStatement ps=null;
		ResultSet rs=null;
		try {
			System.out.println("수정할 방 등록번호를 입력하세요.");
			String num=scan.nextLine();
			int rnum=Integer.parseInt(num);
			String search="select rname,rsize,price from room r where rnum=?";
			searchps=con.prepareStatement(search);
			searchps.setInt(1, rnum);
			rs=searchps.executeQuery();
			rs.next();
			System.out.println("새로운 이름을 입력하세요.(0입력시 유지)");
			String rname=scan.nextLine();
			if(rname.equals("0")) rname=rs.getString("rname");
			System.out.println("수정할 크기를 입력하세요.(0입력시 유지)");
			String rsize=scan.nextLine();
			if(rsize.equals("0")) rsize=rs.getString("rzise");
			System.out.println("수정할 가격을 입력하세요.(0입력시 유지)");
			String price=scan.nextLine();
			if(price.equals("0")) price=rs.getString("price");
			String sql="update room set rname=?,rsize=?,price=? where rnum=?";
			ps=con.prepareStatement(sql);
			ps.setString(1, rname);
			ps.setString(2, rsize);
			ps.setString(3, price);
			ps.setInt(4, rnum);
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
			Connect.close(searchps,ps,rs);
		}
	}
	public void deleteroom(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		try {
			System.out.println("삭제할 방 번호를 입력하세요");
			String num=scan.nextLine();
			int rnum=Integer.parseInt(num);
			String sql="delete from room where rnum=?";
			ps=con.prepareStatement(sql);
			ps.setInt(1, rnum);
			ps.executeUpdate();
			System.out.println("삭제가 완료되었습니다.");
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
}
