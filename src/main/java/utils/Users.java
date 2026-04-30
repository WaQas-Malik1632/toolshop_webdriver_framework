package utils;

import java.util.Properties;

public class Users {
    public static String customer1Email = "customer@practicesoftwaretesting.com";
    public static String customer1Pass = "welcome01";

    public static String customer2Email = "customer@practicesoftwaretesting.com";
    public static String customer2Pass = "welcome01";

    public static String customer3Email = "customer@practicesoftwaretesting.com";
    public static String customer3Pass = "pass123";

    public static String adminUserEmail = "admin@practicesoftwaretesting.com";
    public static String adminUserPass = "welcome01";

    public String loginUserEmail;
    public String loginUserPass;
    public String loginWrongPass;
    public String resetWithUnRegisteredEmail;
    public String resetWithRegisteredEmail;

    public String userCurrentPass;
    public String userNewPass;
    public String userNewConfirmPass;

    public Users(Properties prop) {
        loginUserEmail = prop.getProperty("user.login.email");
        loginUserPass = prop.getProperty("user.login.password");
        loginWrongPass = prop.getProperty("user.login.wrong.password");
        resetWithUnRegisteredEmail = prop.getProperty("user.reset.unregistered.email");
        resetWithRegisteredEmail = prop.getProperty("user.reset.registered.email");

        userCurrentPass = prop.getProperty("user.change.current.password");
        userNewPass = prop.getProperty("user.change.new.password");
        userNewConfirmPass = prop.getProperty("user.change.confirm.password");
    }

}