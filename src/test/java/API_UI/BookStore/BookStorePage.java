package API_UI.BookStore;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$x;

public class BookStorePage {
    SelenideElement searchInput = $x("//input[@id='searchBox']");
    SelenideElement firstBookInTable = $x("//table/tbody/tr[1]//a");

    public BookPage openBookByString(String bookName) {
        searchInput.setValue(bookName);
        String url = firstBookInTable.getAttribute("href");
        return new BookPage(url);
    }

    public BookStorePage(String URL) {
        Selenide.open(URL);
    }
}
