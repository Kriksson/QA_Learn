package UI.RadioButton;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UI] - Тест переключателей")
public class RadioButtonTest extends BaseTest {

    public static final String URL = "https://demoqa.com/radio-button";

    @Test
    @Owner("Kriksson")
    @DisplayName("Выбрать Yes, затем Impressive. Проверить выбранное значение.")
    public void checkRadioButton() {
        RadioButtonPage radioButtonPage = Allure.step("Открыть страницу Radio Button", () -> new RadioButtonPage(URL));

        Allure.step("Проверить, что ни один переключатель не выбран", () -> {
            Assert.assertEquals("No selected", radioButtonPage.getSelectedRadioButton());
        });

        Allure.step("Выбрать Yes и проверить выбранное значение", () -> {
            radioButtonPage.yesRadioButton.click();
            Assert.assertEquals("Yes", radioButtonPage.getSelectedRadioButton());
        });

        Allure.step("Выбрать Impressive и проверить смену выбранного значения", () -> {
            radioButtonPage.impressiveRadioButton.click();
            Assert.assertEquals("Impressive", radioButtonPage.getSelectedRadioButton());
            Assert.assertNotEquals("Yes", radioButtonPage.getSelectedRadioButton());
        });
    }

    @Test
    @Owner("Kriksson")
    @DisplayName("Проверить, что переключатель No недоступен и ничего не выбрано.")
    public void isNoRadioEnabled() {
        RadioButtonPage radioButtonPage = Allure.step("Открыть страницу Radio Button", () -> new RadioButtonPage(URL));

        Allure.step("Проверить, что ни один переключатель не выбран", () -> {
            Assert.assertEquals("No selected", radioButtonPage.getSelectedRadioButton());
        });

        Allure.step("Проверить, что переключатель No недоступен", () -> {
            boolean enabled = radioButtonPage.isNoRadioEnabled();
            Assert.assertFalse(enabled);
        });
    }
}
