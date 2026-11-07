package UI.WebTables;

import Base.BaseTest;
import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;

@DisplayName("[UI] - Тест таблицы сотрудников")
public class WebTableTest extends BaseTest {

    public final static String URL = "https://demoqa.com/webtables";
    public final static String SEARCH_EMAIL = "cierra@example.com";
    public final static String EXP_FIRST_NAME = "Cierra";
    public final static String EXP_LAST_NAME = "Vega";
    public final static Integer EXP_AGE = 39;
    public final static Integer EXP_SALARY = 10000;
    public final static String EXP_DEPARTMENT = "Insurance";

    public final static String ADD_FIRST_NAME = "Alex";
    public final static String ADD_LAST_NAME = "Petrov";
    public final static String ADD_EMAIL = "alex@example.com";
    public final static Integer ADD_AGE = 25;
    public final static Integer ADD_SALARY = 50000;
    public final static String ADD_DEPARTMENT = "QA";


    @Test
    @Owner("Kriksson")
    @DisplayName("Найти сотрудника по email. Проверить единственную строку и все данные сотрудника.")
    public void WebTableTest() {
        WebTablePage webTablePage = Allure.step("Открыть таблицу сотрудников", () -> new WebTablePage(URL));

        Allure.step("Найти сотрудника по email cierra@example.com", () -> {
            webTablePage.searchInput.setValue(SEARCH_EMAIL);
            webTablePage.searchButton.scrollIntoCenter().click();
        });

        Allure.step("Проверить единственную найденную строку и данные сотрудника", () -> {
            webTablePage.rows.shouldBe(CollectionCondition.size(1));
            Personal expected = new Personal(EXP_FIRST_NAME, EXP_LAST_NAME, EXP_AGE, SEARCH_EMAIL, EXP_SALARY, EXP_DEPARTMENT);
            Personal current = webTablePage.getPersonalByRowId(0);
            Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, current));
        });
    }

    @Test
    @Owner("Kriksson")
    @DisplayName("Добавить сотрудника и найти его по email. Проверить сохранённые данные.")
    public void WebTableAddRowTest() {
        WebTablePage webTablePage = Allure.step("Открыть таблицу сотрудников", () -> new WebTablePage(URL));

        Allure.step("Открыть форму добавления сотрудника", () -> {
            webTablePage.addButton.scrollIntoCenter().click();
        });

        Allure.step("Дождаться формы и заполнить данные Alex Petrov", () -> {
            webTablePage.firstNameInput.shouldBe(visible);
            webTablePage.firstNameInput.setValue(ADD_FIRST_NAME);
            webTablePage.lastNameInput.setValue(ADD_LAST_NAME);
            webTablePage.emailInput.setValue(ADD_EMAIL);
            webTablePage.ageInput.setValue(String.valueOf(ADD_AGE));
            webTablePage.salaryInput.setValue(String.valueOf(ADD_SALARY));
            webTablePage.departmentInput.setValue(ADD_DEPARTMENT);
        });

        Allure.step("Отправить форму и проверить её закрытие", () -> {
            webTablePage.submitButton.scrollIntoCenter().click();

            webTablePage.submitButton.shouldNotBe(visible);
        });

        Allure.step("Найти добавленного сотрудника по email alex@example.com", () -> {
            webTablePage.searchInput.setValue(ADD_EMAIL);
            webTablePage.searchButton.scrollIntoCenter().click();
        });

        Allure.step("Проверить единственную найденную строку и данные добавленного сотрудника", () -> {
            webTablePage.rows.shouldBe(CollectionCondition.size(1));
            Personal expected = new Personal(ADD_FIRST_NAME, ADD_LAST_NAME, ADD_AGE, ADD_EMAIL, ADD_SALARY, ADD_DEPARTMENT);
            Personal current = webTablePage.getPersonalByRowId(0);

            Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, current));
        });
    }

    @Test
    @Owner("Kriksson")
    @DisplayName("Получить имена и фамилии сотрудников. Проверить состав и порядок строк.")
    public void checkWebTableRows() {
        WebTablePage webTablePage = Allure.step("Открыть таблицу сотрудников", () -> new WebTablePage(URL));

        Allure.step("Собрать имена и фамилии сотрудников и сравнить состав и порядок строк", () -> {
            List<String> expected = new ArrayList<>(){{
                add("Cierra Vega");
                add("Alden Cantrell");
                add("Kierra Gentry");
            }};
            List<String> actual = new ArrayList<>();
            for (SelenideElement row : webTablePage.rows) {
                ElementsCollection cells = row.$$x("td");
                String name = cells.get(0).text() + " " + cells.get(1).text();
                actual.add(name);
            }

            Assert.assertEquals(expected, actual);
        });
    }
}
