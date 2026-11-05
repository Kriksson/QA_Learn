package UI.WebTables;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;

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
    public void WebTableTest() {
        WebTablePage webTablePage = new WebTablePage(URL);

        webTablePage.searchInput.setValue(SEARCH_EMAIL);
        webTablePage.searchButton.scrollIntoCenter().click();
        webTablePage.rows.shouldBe(CollectionCondition.size(1));
        Personal expected = new Personal(EXP_FIRST_NAME, EXP_LAST_NAME, EXP_AGE, SEARCH_EMAIL, EXP_SALARY, EXP_DEPARTMENT);
        Personal current = webTablePage.getPersonalByRowId(0);
        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, current));
    }

    @Test
    public void WebTableAddRowTest() {
        WebTablePage webTablePage = new WebTablePage(URL);

        webTablePage.addButton.scrollIntoCenter().click();

        webTablePage.firstNameInput.shouldBe(visible);
        webTablePage.firstNameInput.setValue(ADD_FIRST_NAME);
        webTablePage.lastNameInput.setValue(ADD_LAST_NAME);
        webTablePage.emailInput.setValue(ADD_EMAIL);
        webTablePage.ageInput.setValue(String.valueOf(ADD_AGE));
        webTablePage.salaryInput.setValue(String.valueOf(ADD_SALARY));
        webTablePage.departmentInput.setValue(ADD_DEPARTMENT);

        webTablePage.submitButton.scrollIntoCenter().click();

        webTablePage.submitButton.shouldNotBe(visible);

        webTablePage.searchInput.setValue(ADD_EMAIL);
        webTablePage.searchButton.scrollIntoCenter().click();
        webTablePage.rows.shouldBe(CollectionCondition.size(1));
        Personal expected = new Personal(ADD_FIRST_NAME, ADD_LAST_NAME, ADD_AGE, ADD_EMAIL, ADD_SALARY, ADD_DEPARTMENT);
        Personal current = webTablePage.getPersonalByRowId(0);

        Assert.assertTrue(EqualsBuilder.reflectionEquals(expected, current));
    }

    @Test
    public void checkWebTableRows() {
        WebTablePage webTablePage = new WebTablePage(URL);

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
    }
}
