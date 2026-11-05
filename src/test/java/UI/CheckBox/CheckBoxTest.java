package UI.CheckBox;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class CheckBoxTest {
    public static final String URL = "https://demoqa.com/checkbox";

    @Test
    public void checkboxTest() {
        CheckBoxPage checkBoxPage = new CheckBoxPage(URL);
        Assert.assertFalse(checkBoxPage.isCheckboxChecked());
        checkBoxPage.checkBox.scrollIntoCenter().click();
        Assert.assertTrue(checkBoxPage.isCheckboxChecked());
        checkBoxPage.checkBox.scrollIntoCenter().click();
        Assert.assertFalse(checkBoxPage.isCheckboxChecked());
    }
}
