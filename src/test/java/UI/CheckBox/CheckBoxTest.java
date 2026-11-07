package UI.CheckBox;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UI] - Тест флажков")
public class CheckBoxTest extends BaseTest {
    public static final String URL = "https://demoqa.com/checkbox";

    @Test
    @Owner("Kriksson")
    @DisplayName("Выбрать и снять флажок Home. Проверить изменение состояния.")
    public void checkboxTest() {
        CheckBoxPage checkBoxPage = Allure.step("Открыть страницу Check Box", () -> new CheckBoxPage(URL));

        Allure.step("Проверить, что флажок Home изначально снят", () -> {
            Assert.assertFalse(checkBoxPage.isCheckboxChecked());
        });

        Allure.step("Выбрать флажок Home и проверить его состояние", () -> {
            checkBoxPage.checkBox.scrollIntoCenter().click();
            Assert.assertTrue(checkBoxPage.isCheckboxChecked());
        });

        Allure.step("Снять флажок Home и проверить его состояние", () -> {
            checkBoxPage.checkBox.scrollIntoCenter().click();
            Assert.assertFalse(checkBoxPage.isCheckboxChecked());
        });
    }
}
