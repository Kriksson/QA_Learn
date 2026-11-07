package UI.DynamicProperties;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Duration;

@DisplayName("[UI] - Тест динамических свойств элементов")
public class DynamicPropertiesTest extends BaseTest {
    public static final String URL = "https://demoqa.com/dynamic-properties";

    @Test
    @Owner("Kriksson")
    @DisplayName("Дождаться доступности кнопки Enable After. Нажать кнопку.")
    public void checkAfterButton() {
        DynamicPropertiesPage dynamicPropertiesPage = Allure.step("Открыть страницу Dynamic Properties", () -> new DynamicPropertiesPage(URL));

        Allure.step("Дождаться доступности кнопки Enable After", () -> {
            dynamicPropertiesPage.buttonAfter.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        });

        Allure.step("Нажать кнопку Enable After", () -> {
            dynamicPropertiesPage.buttonAfter.click();
        });
    }

    @Test
    @Owner("Kriksson")
    @DisplayName("Дождаться доступности кнопки Visible After. Нажать кнопку.")
    public void checkBeforeButton() {
        DynamicPropertiesPage dynamicPropertiesPage = Allure.step("Открыть страницу Dynamic Properties", () -> new DynamicPropertiesPage(URL));

        Allure.step("Дождаться доступности кнопки Visible After", () -> {
            dynamicPropertiesPage.buttonBefore.shouldBe(Condition.enabled, Duration.ofSeconds(10));
        });

        Allure.step("Нажать кнопку Visible After", () -> {
            dynamicPropertiesPage.buttonBefore.click();
        });
    }
}
