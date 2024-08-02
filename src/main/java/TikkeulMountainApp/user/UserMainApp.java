package TikkeulMountainApp.user;

import java.util.Scanner;


public class UserMainApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

//정보 조회 main
//        // Id 입력
//        System.out.print("조회할 사용자 ID를 입력하세요: ");
//        String userId = scanner.nextLine();
//        // 특정 사용자 ID를 통해 사용자 정보 가져오기
//        User user = UserDao.getUser(userId);
//
//        // Id가 존재하는 경우 정보 출력
//        if (user != null) {
//            System.out.println("사용자 ID: " + user.getUser_id());
//            System.out.println("사용자 이름: " + user.getUser_name());
//            System.out.println("사용자 비밀번호: " + user.getUser_password());
//            System.out.println("사용자 전화번호: " + user.getUser_phone());
//            System.out.println("사용자 계정: " + user.getUser_account());
//            System.out.println("사용자 계좌 잔액: " + user.getUser_account_balance());
//            System.out.println("사용자 활성화 상태: " + user.getUser_active());
//        } else {
//            System.out.println("사용자를 찾을 수 없습니다.");
//        }
//        scanner.close();


        //scanner

        // User user = new User("jhc5555","정회찬","1234","010-2222-2222","1111-1234-1234",50000, "0"); // 회원가입
        //    UserDao.registerUser(user); // 테이블 등록

        //  UserDao.updateUserBalance("pk999",20000); // 수정 메서드 출력

        //  System.out.println(UserDao.getUserBalance("pk999")); // 잔액 조회 출력
//
        //UserDao.updateUserActive("pk999", "0");// 활성화 유무

        //  UserDao.loginUser("pk999","1111");
        // UserDao.updateUserPassword("jhc5555","회찬");
        //  UserDao.getUser("jhc5555");


      //  UserDao.updateUserActive("jhc5555","0");
UserDao.loginUser("jhc5555","회찬");

    }
}


