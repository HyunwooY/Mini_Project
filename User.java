package project1;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

public class User {
	private String id=Member.getLoginid();
	Scanner scan=new Scanner(System.in);

	public void showroom(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		ResultSet rs=null;
		PreparedStatement ps1=null;
		ResultSet rs1=null;
		PreparedStatement ps2=null;
		ResultSet rs2=null;
		System.out.println("숙박하실 날짜를 입력해주세요(형식:YYMMDD)");
		String checkin=scan.nextLine();
		int date=Integer.parseInt(checkin);
		System.out.println("숙박기간을 입력하세요");
		String sPeriod=scan.nextLine();
		int Period=Integer.parseInt(sPeriod);
		String checkout=Integer.toString(date+Period);
		try {
			String sql="select rnum,rname,rsize,price from room "
					+"where rnum not in(select r.rnum num from room r,reservation s "
					+"where not(to_date(?,'yymmdd')<=nvl(s.check_in,'000101')) and not(to_date(?,'yymmdd')>=nvl(s.check_out,'000101')) "
					+ "and r.rnum=s.rnum)";
			ps=con.prepareStatement(sql);
			ps.setString(1, checkout);
			ps.setString(2, checkin);
			rs=ps.executeQuery();
			System.out.println("<<<<<예약 가능한 객실>>>>>");
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
					while(rs2.next()) {
						String rid=rs2.getString("id");
						System.out.println(rid.substring(0, 2)+"****님:"+rs2.getString("osr"));
					}
				}else {
					System.out.println("등록된 리뷰없음");
				}
				System.out.println("---------------------");
			}
			System.out.println("입력된 정보로 예약 하시겠습니까?");
			String yn=scan.nextLine();
			if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
				||yn.equals("ㅛ")) {
				reservation(c,checkin,checkout);
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			Connect.close(ps,ps1,ps2,rs,rs1,rs2);
		}
	}
	public void reservation(Connection c, String checkin,String checkout) {
		Connection con=c;
		PreparedStatement ps=null;
		PreparedStatement search=null;
		ResultSet rs=null;
		System.out.println("예약하려는 객실의 등록번호를 입력하세요.");
		String num=scan.nextLine();
		int rnum=Integer.parseInt(num);
		try {
			String searchdate="select rnum,rname,rsize,price from room "
					+"where rnum not in(select r.rnum num from room r,reservation s "
					+"where not(to_date(?,'yymmdd')<=nvl(s.check_in,'000101')) and not(to_date(?,'yymmdd')>=nvl(s.check_out,'000101')) "
					+ "and r.rnum=s.rnum)";
			search=con.prepareStatement(searchdate);
			search.setString(1, checkout);
			search.setString(2, checkin);
			rs=search.executeQuery();
			if(rs.next()) {
				String sql="insert into reservation values(seq_rv.nextval,?,to_date(?,'yymmdd'),to_date(?,'yymmdd'),?)";
				ps=con.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, checkin);
				ps.setString(3, checkout);
				ps.setInt(4, rnum);
				ps.executeUpdate();
				con.commit();
				System.out.println("객실 예약이 완료되었습니다");
			}else {
				System.out.println("해당 객실은 예약이 불가합니다.");
				System.out.println("다른 객실을 예약하시겠습니까?");
				String yn=scan.nextLine();
				if(yn.equals("yes")||yn.equals("Yes")||yn.equals("YES")||yn.equals("Y")||yn.equals("y")) {
					Connect.close(ps,search,rs);
					reservation(con,checkin,checkout);
				}else {
					
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps,search,rs);
		}
	}
	public void reservation(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		PreparedStatement search=null;
		ResultSet rs=null;
		System.out.println("예약하려는 객실의 등록번호를 입력하세요.");
		String num=scan.nextLine();
		int rnum=Integer.parseInt(num);
		System.out.println("예약하려는 날짜를 입력하세요");
		String check_in=scan.nextLine();
		System.out.println("숙박기간을 입력하세요");
		String sPeriod=scan.nextLine();
		int Period=Integer.parseInt(sPeriod);
		try {
			String searchdate="select r.rnum, r.rname, r.rsize, r.price from room r,reservation s where r.rnum=? and to_date(?,'yymmdd')>=nvl(s.check_out,'000101') and r.rnum=s.rnum(+)";
			search=con.prepareStatement(searchdate);
			search.setInt(1, rnum);
			search.setString(2, check_in);
			rs=search.executeQuery();
			if(rs.next()) {
				String sql="insert into reservation values(seq_rv.nextval,?,to_date(?,'yymmdd'),to_date(?,'yymmdd')+?,?)";
				ps=con.prepareStatement(sql);
				ps.setString(1, id);
				ps.setString(2, check_in);
				ps.setString(3, check_in);
				ps.setInt(4, Period);
				ps.setInt(5, rnum);
				ps.executeUpdate();
				con.commit();
				System.out.println("객실 예약이 완료되었습니다");
			}else {
				System.out.println("해당 객실은 예약이 불가합니다.");
				System.out.println("다른 객실을 예약하시겠습니까?");
				String yn=scan.nextLine();
				if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
						||yn.equals("ㅛ")) {
					Connect.close(ps,search,rs);
					reservation(con);
				}else {
					
				}
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				se.printStackTrace();
			}
		}finally {
			Connect.close(ps,search,rs);
		}
	}
	public void cancel(Connection c) { // 예약취소
		Connection con=c;
		PreparedStatement pssearch=null;
		CallableStatement cs=null;
		ResultSet rs=null;
		System.out.println("예약번호를 입력해주세요");
		String num=scan.nextLine();
		int rvnum=Integer.parseInt(num);
		try {
			String search="select m.name,r.rname,v.check_in,v.check_out, v.check_out-v.check_in days from member m, room r,reservation v "
						+ "where m.id=v.id and r.rnum=v.rnum and v.resernum=?";
			pssearch=con.prepareStatement(search);
			pssearch.setInt(1, rvnum);
			rs=pssearch.executeQuery();
			rs.next();
			System.out.println("<<<"+rs.getString("name")+"님의 예약정보>>>");
			System.out.println("객실명:"+rs.getString("rname"));
			System.out.println("기간:"+rs.getDate("check_in")+"~"+rs.getDate("check_out")
							  +" ("+rs.getInt("days")+"박)");
			System.out.println("예약을 취소하시겠습니까?");
			String yn=scan.nextLine();
			if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
					||yn.equals("ㅛ")) {
				String delete="{call deletereserv(?)}";
				cs=con.prepareCall(delete);
				cs.setInt(1, rvnum);
				cs.execute();
				System.out.println("정상적으로 취소되었습니다.");
			}
			con.commit();
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(pssearch,cs,rs);
		}
	}
	public void review(Connection c) {
		Connection con=c;
		PreparedStatement ps=null;
		PreparedStatement cs=null;
		ResultSet rs=null;
		System.out.println("예약번호를 입력해주세요");
		String num=scan.nextLine();
		int rvnum=Integer.parseInt(num);
		try {
			String search="select rnum from reservation where resernum=?";
			cs=con.prepareStatement(search);
			cs.setInt(1, rvnum);
			rs=cs.executeQuery();
			if(rs.next()) {
				int rnum=rs.getInt("rnum");
				System.out.println("평점을 입력해주세요(0.5~5.0까지 0.5단위)");
				String star=scan.nextLine();
				System.out.println("한줄평을 입력해주세요(최대 50글자)");
				String osr=scan.nextLine();
				String insert="insert into review values(seq_rvnum.nextval,?,?,?,?)";
				ps=con.prepareStatement(insert);
				ps.setInt(1, rnum);
				ps.setString(2, star);
				ps.setString(3, osr);
				ps.setInt(4, rvnum);
				ps.executeUpdate();
				System.out.println("리뷰작성이 완료되었습니다.");
				con.commit();
			}else {
				System.out.println("해당 예약번호가 없습니다.");
			}
		}catch(SQLException se) {
			try {
				con.rollback();
				System.out.print("입력중 오류가 발생했습니다. 오류:");
				se.printStackTrace();
			}catch(SQLException s) {
				s.printStackTrace();
			}
		}finally {
			Connect.close(ps,cs,rs);
		}
	}
	public void checkreserv(Connection c) {
		Connection con=c;
		PreparedStatement pssearch=null;
		ResultSet rs=null;
		try {
			String search="select m.name,v.resernum, r.rname, v.check_in, v.check_out, v.check_out-v.check_in days from member m, room r,reservation v where m.id=? and r.rnum=v.rnum and m.id=v.id";
			pssearch=con.prepareStatement(search);
			pssearch.setString(1,id);
			rs=pssearch.executeQuery();
			boolean i=rs.next();
			if(i) {
				System.out.println("<<<"+id+"님의 예약정보>>>");
			while(i) {
				System.out.println("객실명:"+rs.getString("rname"));
				System.out.println("예약번호:"+rs.getInt("resernum"));
				System.out.println("기간:"+rs.getDate("check_in")+"~"+rs.getDate("check_out")
								  +" ("+rs.getInt("days")+"박)");
				System.out.println("------------------");
				i=rs.next();
				}
			}else {
				System.out.println("예약내역이 없습니다.");
			}
		}catch(SQLException se) {
			se.printStackTrace();
		}finally {
			Connect.close(pssearch,rs);
		}
	}
}










