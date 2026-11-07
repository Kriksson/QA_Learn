package UI.TextBox;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[UI] - Тест текстовой формы")
public class TextBoxTest extends BaseTest {
    public final static String URL = "https://demoqa.com/text-box";


    @Test
    @Owner("Kriksson")
    @DisplayName("Отправить форму с корректными данными. Проверить все значения в результате.")
    public void successfulFormSend() {
        TextBoxPage textBoxPage = Allure.step("Открыть страницу Text Box", () -> new TextBoxPage(URL));

        Allure.step("Заполнить форму корректными данными Alex Petrov", () -> {
            textBoxPage.fullNameInputBox.setValue(TextBoxPage.FULL_NAME);
            textBoxPage.emailInputBox.setValue(TextBoxPage.EMAIL);
            textBoxPage.currentAddressInputBox.setValue(TextBoxPage.CURRENT_ADDRESS);
            textBoxPage.permanentAddressInputBox.setValue(TextBoxPage.PERMANENT_ADDRESS);
        });

        Allure.step("Отправить форму", () -> {
            textBoxPage.submitButton.scrollIntoCenter().click();
        });

        Allure.step("Сравнить все данные результата с отправленными значениями", () -> {
            Output expectedOutput =
                    new Output(TextBoxPage.FULL_NAME, TextBoxPage.EMAIL,
                            TextBoxPage.CURRENT_ADDRESS, TextBoxPage.PERMANENT_ADDRESS);
            Output currentOutput = textBoxPage.getOutput();
            Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedOutput, currentOutput));
        });
    }

    @Test
    @Owner("Kriksson")
    @DisplayName("Отправить форму с некорректным email. Проверить класс ошибки и отсутствие данных в результате.")
    public void unsuccessfulFormSendWithWrongEmail() {
        TextBoxPage textBoxPage = Allure.step("Открыть страницу Text Box", () -> new TextBoxPage(URL));

        Allure.step("Заполнить форму с некорректным email", () -> {
            textBoxPage.fullNameInputBox.setValue(TextBoxPage.FULL_NAME);
            textBoxPage.emailInputBox.setValue(TextBoxPage.WRONG_EMAIL);
            textBoxPage.currentAddressInputBox.setValue(TextBoxPage.CURRENT_ADDRESS);
            textBoxPage.permanentAddressInputBox.setValue(TextBoxPage.PERMANENT_ADDRESS);
        });

        Allure.step("Отправить форму", () -> {
            textBoxPage.submitButton.scrollIntoCenter().click();
        });

        Allure.step("Проверить класс ошибки у поля email", () -> {
            String attributeClass = textBoxPage.emailInputBox.getAttribute("class");
            boolean hasFieldError = attributeClass.contains("field-error");
            Assert.assertTrue(hasFieldError);
        });

        Allure.step("Проверить отсутствие отправленных данных в результате", () -> {
            Output expectedOutput =
                    new Output(null, null, null, null);
            Output currentOutput = textBoxPage.getOutput();
            Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedOutput, currentOutput));
        });
    }
}
