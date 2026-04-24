package webDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Properties;

public class DriverManager {

    public static final Logger log = LogManager.getLogger(DriverManager.class);
    public static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static Properties prop;

    public static WebDriver getDriver() {
        return driver.get();
    }

    public static Properties loadProperties() throws IOException {
        if (prop == null) {
            prop = new Properties();
            String configPath = "src/main/java/config/config.properties";

            try (FileInputStream fis = new FileInputStream(configPath)) {
                prop.load(fis);
            } catch (IOException e) {
                log.error("Failed to load configuration file: {}", configPath, e);
                throw e;
            }

            // Apply CI/CD system property overrides if running from Jenkins
            applySystemPropertyOverrides(prop);
        }
        return prop;
    }

    private static void applySystemPropertyOverrides(Properties properties) {
        for (String key : new String[]{"browser", "app.base.url", "user.login.email", "user.login.password"}) {
            String sysProp = System.getProperty(key);
            if (sysProp != null && !sysProp.isEmpty()) {
                properties.setProperty(key, sysProp);
                log.info("System property override: {}={}", key, key.contains("password") ? "***" : sysProp);
            }
        }
    }

    public static Properties loadApiProperties() throws IOException {
        if (prop == null) {
            prop = new Properties();
            String configPath = "src/main/java/com/toolshop/qa/config/configApi.properties";

            try (FileInputStream fis = new FileInputStream(configPath)) {
                prop.load(fis);
            } catch (IOException e) {
                log.error("Failed to load configuration file: {}", configPath, e);
                throw e;
            }
        }
        return prop;
    }

    public static void initializeDriver(String browserName, String baseUrl) {
        log.info("Initializing browser: {}", browserName);

        WebDriver webDriver;

        switch (browserName.trim().toLowerCase()) {
            case "chrome":
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.setExperimentalOption("excludeSwitches",
                        Arrays.asList("enable-automation", "load-extension"));
                chromeOptions.addArguments(
                        "--restrict-iframe-permissions",
                        "--disable-popup-blocking",
                        "disable-infobars",
                        "--disable-extensions",
                        "--remote-allow-origins=*");
                chromeOptions.enableBiDi();
                webDriver = new ChromeDriver(chromeOptions);
                webDriver.manage().window().maximize();
                break;

            case "edge":
                WebDriverManager.edgedriver().clearDriverCache().setup();
                EdgeOptions edgeOptions = new EdgeOptions();
                edgeOptions.setExperimentalOption("excludeSwitches",
                        Arrays.asList("enable-automation", "load-extension"));
                edgeOptions.addArguments(
                        "--restrict-iframe-permissions",
                        "--disable-popup-blocking",
                        "disable-infobars",
                        "--disable-extensions",
                        "--remote-allow-origins=*");
                edgeOptions.enableBiDi();
                webDriver = new EdgeDriver(edgeOptions);
                webDriver.manage().window().maximize();
                break;

            case "chrome-headless":
                WebDriverManager.chromedriver().clearDriverCache().setup();
                ChromeOptions chromeHeadlessOptions = new ChromeOptions();
                chromeHeadlessOptions.setExperimentalOption("excludeSwitches",
                        Arrays.asList("enable-automation", "load-extension"));
                chromeHeadlessOptions.addArguments(
                        "--headless",
                        "--restrict-iframe-permissions",
                        "--disable-popup-blocking",
                        "disable-infobars",
                        "--disable-extensions",
                        "--remote-allow-origins=*");
                chromeHeadlessOptions.enableBiDi();
                webDriver = new ChromeDriver(chromeHeadlessOptions);
                break;

            case "edge-headless":
                WebDriverManager.edgedriver().clearDriverCache().setup();
                EdgeOptions edgeHeadlessOptions = new EdgeOptions();
                edgeHeadlessOptions.setExperimentalOption("excludeSwitches",
                        Arrays.asList("enable-automation", "load-extension"));
                edgeHeadlessOptions.addArguments(
                        "--headless",
                        "--restrict-iframe-permissions",
                        "--disable-popup-blocking",
                        "disable-infobars",
                        "--disable-extensions",
                        "--remote-allow-origins=*");
                edgeHeadlessOptions.enableBiDi();
                webDriver = new EdgeDriver(edgeHeadlessOptions);
                break;

            default:
                log.fatal("Invalid browser value provided: {}", browserName);
                throw new IllegalArgumentException("Invalid browser value provided: " + browserName);
        }

        driver.set(webDriver);
        log.info("Navigating to Application URL: {}", baseUrl);
        webDriver.get(baseUrl);
    }


    public static void quitDriver() {
        WebDriver webDriver = driver.get();
        if (webDriver != null) {
            try {
                webDriver.quit();
                log.info("Browser closed successfully");
            } catch (Exception e) {
                log.error("Error while closing browser: {}", e.getMessage());
            } finally {
                driver.remove();
            }
        }
    }

}