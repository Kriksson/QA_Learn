package RadioButton;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.enabled;
import static com.codeborne.selenide.Condition.selected;
import static com.codeborne.selenide.Selenide.$x;

public class RadioButtonPage {
    SelenideElement yesRadioButton = $x("//input[@id='yesRadio']");
    SelenideElement impressiveRadioButton = $x("//input[@id='impressiveRadio']");
    SelenideElement noRadioButton = $x("//input[@id='noRadio']");

    public RadioButtonPage(String URL) {
        Selenide.open(URL);
    }

    public String getSelectedRadioButton() {
        if (yesRadioButton.is(selected)) {
            return "Yes";
        } else if (impressiveRadioButton.is(selected)) {
            return "Impressive";
        } else {
            return "No selected";
        }
    }

    public boolean isNoRadioEnabled() {
        return noRadioButton.is(enabled);
    }
}
