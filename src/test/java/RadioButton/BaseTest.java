package RadioButton;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;

public abstract class BaseTest {
    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        Configuration.browser = "chrome";
        Configuration.headless = false;
        Configuration.browserSize = "1024x768";
    }

    @AfterEach
    public void tearDown() {
        Selenide.closeWebDriver();
    }
}
