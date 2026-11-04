package RadioButton;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class RadioButtonTest extends BaseTest{

    public static final String URL = "https://demoqa.com/radio-button";

    @Test
    public void checkRadioButton() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(URL);

        Assert.assertEquals("No selected", radioButtonPage.getSelectedRadioButton());

        radioButtonPage.yesRadioButton.click();
        Assert.assertEquals("Yes", radioButtonPage.getSelectedRadioButton());

        radioButtonPage.impressiveRadioButton.click();
        Assert.assertEquals("Impressive", radioButtonPage.getSelectedRadioButton());
        Assert.assertNotEquals("Yes", radioButtonPage.getSelectedRadioButton());
    }

    @Test
    public void isNoRadioEnabled() {
        RadioButtonPage radioButtonPage = new RadioButtonPage(URL);
        Assert.assertEquals("No selected", radioButtonPage.getSelectedRadioButton());
        boolean enabled = radioButtonPage.isNoRadioEnabled();
        Assert.assertFalse(enabled);
    }
}
