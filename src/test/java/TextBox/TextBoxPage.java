package TextBox;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Selenide.*;

public class TextBoxPage {
    SelenideElement fullNameInputBox = $x("//input[@placeholder='Full Name']");
    SelenideElement emailInputBox = $x("//input[@placeholder='name@example.com']");
    SelenideElement currentAddressInputBox = $x("//textarea[@placeholder='Current Address']");
    SelenideElement permanentAddressInputBox = $x("//textarea[@id='permanentAddress']");
    SelenideElement submitButton = $x("//button[@id='submit']");
    ElementsCollection output = $$x("//div[@id='output']//p");

    final static String FULL_NAME = "Alex Petrov";
    final static String EMAIL = "alex@example.com";
    final static String WRONG_EMAIL = "alex.example.com";
    final static String CURRENT_ADDRESS = "Moscow, Test street 10";
    final static String PERMANENT_ADDRESS = "Kazan, Sample street 5";

    public TextBoxPage(String URL) {
        Selenide.open(URL);
    }

    public Output getOutput() {
        List<String> outputs = new ArrayList<>();
        for (SelenideElement element : output) {
            int start = element.getText().indexOf(':');
            outputs.add(element.getText().substring(start + 1));
        }
        if (outputs.size() == 4) {
            return new Output(outputs.get(0), outputs.get(1), outputs.get(2), outputs.get(3));
        } else {
            return new Output(null, null, null, null);
        }

    }
}
