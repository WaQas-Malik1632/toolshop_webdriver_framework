package utils;

public class TestContext {
    private static final ThreadLocal<Users> userData = new ThreadLocal<>();

    public static void setUserData(Users data) {
        userData.set(data);
    }

    public static Users getUserData() {
        return userData.get();
    }

}
