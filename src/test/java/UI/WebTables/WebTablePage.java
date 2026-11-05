package UI.WebTables;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Selenide.$$x;
import static com.codeborne.selenide.Selenide.$x;

public class WebTablePage {
    SelenideElement searchInput = $x("//input[@id='searchBox']");
    SelenideElement searchButton = $x("//button[@id='basic-addon2']");
    ElementsCollection rows = $$x("//tbody/tr");

    SelenideElement addButton = $x("//button[@id='addNewRecordButton']");
    SelenideElement firstNameInput = $x("//input[@id='firstName']");
    SelenideElement lastNameInput = $x("//input[@id='lastName']");
    SelenideElement emailInput = $x("//input[@id='userEmail']");
    SelenideElement ageInput = $x("//input[@id='age']");
    SelenideElement salaryInput = $x("//input[@id='salary']");
    SelenideElement departmentInput = $x("//input[@id='department']");
    SelenideElement submitButton = $x("//button[@id='submit']");

    public Personal getPersonalByRowId(int id) {
        SelenideElement row = rows.get(id);
        ElementsCollection cells = row.$$x("td");
        String firstName = cells.get(0).text();
        String lastName = cells.get(1).text();
        Integer age = Integer.parseInt(cells.get(2).getText());
        String email = cells.get(3).text();
        Integer salary = Integer.parseInt(cells.get(4).getText());
        String department = cells.get(5).text();
        return new Personal(firstName, lastName, age, email, salary, department);
    }

    public WebTablePage(String url) {
        Selenide.open(url);
    }
}
