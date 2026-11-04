package TextBox;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class TextBoxTest extends BaseTest {
    public final static String URL = "https://demoqa.com/text-box";


    @Test
    public void successfulFormSend() {
        TextBoxPage textBoxPage = new TextBoxPage(URL);
        textBoxPage.fullNameInputBox.setValue(TextBoxPage.FULL_NAME);
        textBoxPage.emailInputBox.setValue(TextBoxPage.EMAIL);
        textBoxPage.currentAddressInputBox.setValue(TextBoxPage.CURRENT_ADDRESS);
        textBoxPage.permanentAddressInputBox.setValue(TextBoxPage.PERMANENT_ADDRESS);
        textBoxPage.submitButton.scrollIntoCenter().click();
        Output expectedOutput =
                new Output(TextBoxPage.FULL_NAME, TextBoxPage.EMAIL,
                        TextBoxPage.CURRENT_ADDRESS, TextBoxPage.PERMANENT_ADDRESS);
        Output currentOutput = textBoxPage.getOutput();
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedOutput, currentOutput));
    }

    @Test
    public void unsuccessfulFormSendWithWrongEmail() {
        TextBoxPage textBoxPage = new TextBoxPage(URL);
        textBoxPage.fullNameInputBox.setValue(TextBoxPage.FULL_NAME);
        textBoxPage.emailInputBox.setValue(TextBoxPage.WRONG_EMAIL);
        textBoxPage.currentAddressInputBox.setValue(TextBoxPage.CURRENT_ADDRESS);
        textBoxPage.permanentAddressInputBox.setValue(TextBoxPage.PERMANENT_ADDRESS);
        textBoxPage.submitButton.scrollIntoCenter().click();

        String attributeClass = textBoxPage.emailInputBox.getAttribute("class");
        boolean hasFieldError = attributeClass.contains("field-error");
        Assert.assertTrue(hasFieldError);

        Output expectedOutput =
                new Output(null, null, null, null);
        Output currentOutput = textBoxPage.getOutput();
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expectedOutput, currentOutput));
    }
}
