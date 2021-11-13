package project1;

import java.sql.Connection;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		Member member=new Member();
		Connection con=Connect.connect();
		while(true) {
			System.out.println("1.회원가입 2. 회원로그인 3.아이디or비밀번호찾기 4.종료");
			String menus=scan.nextLine();
			int menu=Integer.parseInt(menus);
			if(menu==1) {
				System.out.println("1.일반 2.사업자");
				String joins=scan.nextLine();
				int join=Integer.parseInt(joins);
				switch(join) {
				case 1: member.joinNomal(con); break;
				case 2: member.joinen(con);break;
				} 
			}else if(menu==2){
				member.login(con);
			}else if(menu==3){
				System.out.println("1.아이디찾기 2.비밀번호찾기");
				menus=scan.nextLine();
				menu=Integer.parseInt(menus);
				if(menu==1) {
					member.findId(con);
				}else if(menu==2) {
					member.findPwd(con);
				}
			}else if(menu==4){
				System.out.println("이용해주셔서 감사합니다.");
				break;
			}else {
				System.out.println("올바른 번호를 입력해주세요");continue;
			}
			String sortation=member.getSortation(Member.getLoginid(),con);
			if(sortation==null) continue;
			boolean state=true;
			if(sortation.equals("일반")) {
				User user=new User();
				while(state) {
					System.out.println("1.객실정보 2.객실예약 3.리뷰등록 4.마이페이지 5.로그아웃");
					menus=scan.nextLine();
					menu=Integer.parseInt(menus);
					if(menu==1) {
						user.showroom(con);
					}else if(menu==2) {
						user.reservation(con);
					}else if(menu==3) {
						user.review(con);
					}else if(menu==4) {
						System.out.println("1.회원정보확인 2.회원정보수정 3.예약내역확인 4.예약취소 5.회원탈퇴 6.처음으로");
						String a=scan.nextLine();
						int ai=Integer.parseInt(a);
						if(ai==1) {
							member.check(con);
						}else if(ai==2) {
							member.update(con);
							System.out.println("비밀번호를 변경하시겠습니까?");
							String yn=scan.nextLine();
							if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
									||yn.equals("ㅛ")) {
								member.changePwd(con);
							}
						}else if(ai==3) {
							user.checkreserv(con);
						}else if(ai==4) {
							user.cancel(con);
						}else if(ai==5){
							member.withdrawal(con);
							break;
						}else if(ai==6){
							continue;
						}else {
							System.out.println("올바른 번호를 입력해주세요.");
						}
					}else if(menu==5) {	
						member.logout();
						if(Member.getLoginid()!=null) continue;
						state=false;
					}else {
						System.out.println("올바른 번호를 입력해주세요.");
					}
				}
			}else {
				while(state) {
					Admin admin=new Admin();
					System.out.println("1.객실등록 2.객실정보확인 3.객실정보수정 4.객실삭제 5.로그아웃");
					menus=scan.nextLine();
					menu=Integer.parseInt(menus);
					if(menu==1) {
						admin.inputroom(con);
					}else if(menu==2) {
						admin.showroom(con);
					}else if(menu==3) {
						admin.updateroom(con);
					}else if(menu==4) {
						admin.deleteroom(con);
					}else if(menu==5) {
						member.logout();
						if(Member.getLoginid()!=null) continue;
						state=false;
					}else {
						System.out.println("올바른 번호를 입력해주세요.");
					}
				}
			}
			//System.out.println(Member.getLoginid());
		}
		Connect.disconnect(con);
	}
}










