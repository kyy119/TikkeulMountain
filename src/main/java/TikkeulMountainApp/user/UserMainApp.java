package TikkeulMountainApp.user;

public class UserMainApp {
    public static void main(String[] args) {

        //scanner
     //   User user = new User("pk999","박성진","1111","010-1111-1111","1234-1234-1234",5000, "0");

       // UserDao.registerUser(user);

        UserDao.updateUserBalance("user1",20000);
        System.out.println(UserDao.getUserBalance("user1"));

        UserDao.updateUserActive("pk999","1");
    }
//        public static void main(String[] args) {
//            // 테스트용 예제 사용자 생성
//            User newUser = new User("user123", "John Doe", "password123", "01012345678", "123456789012345", 1000);
//
//            // 회원가입 메서드 호출
//            registerUser(newUser);
//
//            // 로그인 메서드 호출
//            loginUser("user123", "password123");
//
//            // 비밀번호 수정 메서드 호출
//            updateUserPassword("user123", "newPassword123");
//
//            // 회원 삭제 메서드 호출
//            deleteUser("user123");
//
//            // 사용자 계좌 잔액 수정 메서드 호출
//            updateUserBalance("user123", 2000);
      //  }

    }
