package DynamicProperties;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.Test;

import java.time.Duration;

public class DynamicPropertiesTest extends BaseTest {
    public static final String URL = "https://demoqa.com/dynamic-properties";

    @Test
    public void checkAfterButton() {
        DynamicPropertiesPage dynamicPropertiesPage = new DynamicPropertiesPage(URL);

        dynamicPropertiesPage.buttonAfter.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        dynamicPropertiesPage.buttonAfter.click();
    }

    @Test
    public void checkBeforeButton() {
        DynamicPropertiesPage dynamicPropertiesPage = new DynamicPropertiesPage(URL);

        dynamicPropertiesPage.buttonBefore.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        dynamicPropertiesPage.buttonBefore.click();
    }
}
