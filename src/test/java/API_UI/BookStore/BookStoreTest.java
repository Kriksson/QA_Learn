package API_UI.BookStore;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

@DisplayName("[API + UI] - Тест книжного магазина")
public class BookStoreTest extends BaseTest {

    public static final String URL_UI = "https://demoqa.com/books";
    public static final String URL_API = "https://demoqa.com/BookStore/v1/Book";
    public static final String BOOK_NAME = "Git Pocket Guide";
    public static final String ISBN = "9781449325862";

    @Test
    @DisplayName("Открыть книгу через UI и получить её через API. Сравнить ISBN, название, автора и издателя.")
    @Owner("Kriksson")
    @Description("Открывается страница с книгами, выполняем поиск по имени и записываем данные книги в POJO. " +
            "Выполняем API запрос, данные записываем в DTO и сравниваем их")
    public void BookStoreTest() {
        BookStorePage bookStorePage = Allure.step("Открыть страницу книжного магазина", () -> new BookStorePage(URL_UI));
        BookDTO bookDTO = Allure.step("Получить данные книги по ISBN через API", () -> BookStoreAPI.getBookWithAPI(URL_API, ISBN));
        BookPage bookPage = Allure.step("Найти книгу Git Pocket Guide и открыть её страницу", () -> bookStorePage.openBookByString(BOOK_NAME));
        BookPOJO bookPOJO = Allure.step("Прочитать данные книги со страницы", () -> bookPage.getBookWithUI());

        Allure.step("Сравнить ISBN, название, автора и издателя книги из API и UI", () -> {
            Assert.assertEquals(bookDTO.isbn(), bookPOJO.ISBN());
            Assert.assertEquals(bookDTO.title(), bookPOJO.title());
            Assert.assertEquals(bookDTO.author(), bookPOJO.author());
            Assert.assertEquals(bookDTO.publisher(), bookPOJO.publisher());
        });
    }
}
