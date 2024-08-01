package TikkeulMountainApp.user;

public class UserMainApp {
    public static void main(String[] args) {

        User user = new User("pk999","박성진","1111","010-1111-1111","1234-1234-1234",5000);
        UserDao.registerUser(user);
    }
}
