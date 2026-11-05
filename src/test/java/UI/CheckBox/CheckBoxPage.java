package UI.CheckBox;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class CheckBoxPage {
    SelenideElement checkBox = $x("//span[@role='checkbox']");

    public CheckBoxPage(String URL) {
        Selenide.open(URL);
    }

    public boolean isCheckboxChecked() {
        if (checkBox.getAttribute("aria-checked").equals("true")) {
            return true;
        }
        return false;
    }
}
