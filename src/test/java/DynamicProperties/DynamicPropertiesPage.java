package DynamicProperties;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class DynamicPropertiesPage {

    SelenideElement buttonAfter = $x("//button[@id='enableAfter']");
    SelenideElement buttonBefore = $x("//button[@id='visibleAfter']");

    public DynamicPropertiesPage(String URL) {
        Selenide.open(URL);
    }
}
