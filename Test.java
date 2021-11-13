package project1;

import java.sql.Connection;
import java.util.Scanner;

public class Test {
	public static void main(String[] args) {
		Scanner scan=new Scanner(System.in);
		Member member=new Member();
		Connection con=Connect.connect();
		while(true) {
			System.out.println("1.ȸ������ 2. ȸ���α��� 3.���̵�or��й�ȣã�� 4.����");
			String menus=scan.nextLine();
			int menu=Integer.parseInt(menus);
			if(menu==1) {
				System.out.println("1.�Ϲ� 2.�����");
				String joins=scan.nextLine();
				int join=Integer.parseInt(joins);
				switch(join) {
				case 1: member.joinNomal(con); break;
				case 2: member.joinen(con);break;
				} 
			}else if(menu==2){
				member.login(con);
			}else if(menu==3){
				System.out.println("1.���̵�ã�� 2.��й�ȣã��");
				menus=scan.nextLine();
				menu=Integer.parseInt(menus);
				if(menu==1) {
					member.findId(con);
				}else if(menu==2) {
					member.findPwd(con);
				}
			}else if(menu==4){
				System.out.println("�̿����ּż� �����մϴ�.");
				break;
			}else {
				System.out.println("�ùٸ� ��ȣ�� �Է����ּ���");continue;
			}
			String sortation=member.getSortation(Member.getLoginid(),con);
			if(sortation==null) continue;
			boolean state=true;
			if(sortation.equals("�Ϲ�")) {
				User user=new User();
				while(state) {
					System.out.println("1.�������� 2.���ǿ��� 3.������ 4.���������� 5.�α׾ƿ�");
					menus=scan.nextLine();
					menu=Integer.parseInt(menus);
					if(menu==1) {
						user.showroom(con);
					}else if(menu==2) {
						user.reservation(con);
					}else if(menu==3) {
						user.review(con);
					}else if(menu==4) {
						System.out.println("1.ȸ������Ȯ�� 2.ȸ���������� 3.���೻��Ȯ�� 4.������� 5.ȸ��Ż�� 6.ó������");
						String a=scan.nextLine();
						int ai=Integer.parseInt(a);
						if(ai==1) {
							member.check(con);
						}else if(ai==2) {
							member.update(con);
							System.out.println("��й�ȣ�� �����Ͻðڽ��ϱ�?");
							String yn=scan.nextLine();
							if(yn.equals("y")||yn.equals("Y")||yn.equals("yes")||yn.equals("YES")
									||yn.equals("��")) {
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
							System.out.println("�ùٸ� ��ȣ�� �Է����ּ���.");
						}
					}else if(menu==5) {	
						member.logout();
						if(Member.getLoginid()!=null) continue;
						state=false;
					}else {
						System.out.println("�ùٸ� ��ȣ�� �Է����ּ���.");
					}
				}
			}else {
				while(state) {
					Admin admin=new Admin();
					System.out.println("1.���ǵ�� 2.��������Ȯ�� 3.������������ 4.���ǻ��� 5.�α׾ƿ�");
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
						System.out.println("�ùٸ� ��ȣ�� �Է����ּ���.");
					}
				}
			}
			//System.out.println(Member.getLoginid());
		}
		Connect.disconnect(con);
	}
}










