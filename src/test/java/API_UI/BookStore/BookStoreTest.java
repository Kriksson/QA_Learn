package API_UI.BookStore;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class BookStoreTest extends BaseTest {

    public static final String URL_UI = "https://demoqa.com/books";
    public static final String URL_API = "https://demoqa.com/BookStore/v1/Book";
    public static final String BOOK_NAME = "Git Pocket Guide";
    public static final String ISBN = "9781449325862";

    @Test
    public void BookStoreTest() {
        BookStorePage bookStorePage = new BookStorePage(URL_UI);
        BookDTO bookDTO = BookStoreAPI.getBookWithAPI(URL_API, ISBN);
        BookPage bookPage = bookStorePage.openBookByString(BOOK_NAME);
        BookPOJO bookPOJO = bookPage.getBookWithUI();

        Assert.assertEquals(bookDTO.isbn(), bookPOJO.ISBN());
        Assert.assertEquals(bookDTO.title(), bookPOJO.title());
        Assert.assertEquals(bookDTO.author(), bookPOJO.author());
        Assert.assertEquals(bookDTO.publisher(), bookPOJO.publisher());
    }
}
