package API_UI.BookStore;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BookPage {

    SelenideElement ISBNText = $x("//label[@id='userName-value']");
    SelenideElement titleText = $x("//div[@id='title-wrapper']//label[@id='userName-value']");
    SelenideElement authorText = $x("//div[@id='author-wrapper']//label[@id='userName-value']");
    SelenideElement publisherText = $x("//div[@id='publisher-wrapper']//label[@id='userName-value']");

    public BookPage(String URL) {
        Selenide.open(URL);
    }

    public BookPOJO getBookWithUI() {
        return new BookPOJO(ISBNText.getText(), titleText.getText(), authorText.getText(), publisherText.getText());
    }
}
