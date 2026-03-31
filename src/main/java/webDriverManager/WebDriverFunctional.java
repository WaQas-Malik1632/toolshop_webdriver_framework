package webDriverManager;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.io.FileInputStream;
import java.io.IOException;
import java.time.Duration;
import java.util.Properties;

public class WebDriverFunctional {

    public static final Logger log = LogManager.getLogger(WebDriverFunctional.class);
    public static final Properties prop = new Properties();

    // Kept for direct access; always reflects the current thread's driver
    public static WebDriver driver;

    private static final ThreadLocal<WebDriver> driverThreadLocal = new ThreadLocal<>();

    // ── Static initialiser: loads config once at class-load time ──────────────
    static {
        try (FileInputStream ip = new FileInputStream("src/main/java/config/config.properties")) {
            prop.load(ip);
            log.info("config.properties loaded successfully");
        } catch (IOException e) {
            log.error("Failed to load config.properties: {}", e.getMessage(), e);
            throw new ExceptionInInitializerError("Cannot load config.properties: " + e.getMessage());
        }
    }

    private WebDriverFunctional() {}

    // ── Browser setup ─────────────────────────────────────────────────────────

    public static void setupBrowser() {
        String browser = prop.getProperty("browser", "chrome").toLowerCase().trim();
        log.info("Setting up browser: {}", browser);

        driver = createDriver(browser);
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(Long.parseLong(prop.getProperty("implicitWait", "10"))));

        driverThreadLocal.set(driver);
        log.info("'{}' driver ready — navigating to: {}", browser, prop.getProperty("baseUrl"));
        driver.get(prop.getProperty("baseUrl", "").trim());
    }

    // ── Private factory: one place for all browser-specific wiring ────────────

    private static WebDriver createDriver(String browser) {
        return switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                yield new ChromeDriver(defaultOptions(new ChromeOptions()));
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                yield new EdgeDriver(defaultOptions(new EdgeOptions()));
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                FirefoxOptions opts = new FirefoxOptions();
                opts.addArguments("--start-maximized");
                yield new FirefoxDriver(opts);
            }
            default -> throw new IllegalArgumentException(
                    "Unsupported browser: '" + browser + "'. Use chrome, edge, or firefox.");
        };
    }

    /** Applies common Chrome/Edge options to avoid duplication. */
    private static <T extends org.openqa.selenium.chromium.ChromiumOptions<T>>
    T defaultOptions(T options) {
        options.addArguments("--start-maximized", "--disable-notifications");
        return options;
    }

    // ── Accessors ─────────────────────────────────────────────────────────────

    public static WebDriver getDriver() {
        WebDriver d = driverThreadLocal.get();
        if (d == null) throw new IllegalStateException(
                "WebDriver not initialised — call setupBrowser() first.");
        return d;
    }

    // ── Teardown ──────────────────────────────────────────────────────────────

    public static void quitDriver() {
        WebDriver d = driverThreadLocal.get();
        if (d != null) {
            log.info("Quitting WebDriver");
            d.quit();
            driverThreadLocal.remove();
            driver = null;
        }
    }
}
