package API.Product;

import io.qameta.allure.Allure;
import io.qameta.allure.Owner;
import io.restassured.http.ContentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;

@DisplayName("[API] - Тест API products")
public class ProductTest {

    public final static String URL = "https://fakestoreapi.com/";

    @Test
    @DisplayName("Получить продукт по его ID." +
            "Проверить правильность выдачи.")
    @Owner("Kriksson")
    public void getProductById() {
        ProductPOJO product = Allure.step("Получить продукт с ID 1", () -> given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "products/1")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().as(ProductPOJO.class));

        Allure.step("Проверить данные полученного продукта с ожидаемыми", () -> {
            Assert.assertEquals(Integer.valueOf(999), product.id());
            Assert.assertFalse(product.title().isEmpty());
            Assert.assertTrue(product.price() > 0);
            Assert.assertEquals("men's clothing", product.category());
        });
    }

    @Test
    @DisplayName("Получить все продукты с указанием лимита." +
            "Проверить что выдано ровно столько сколько указано в лимите")
    @Owner("Kriksson")
    public void getProductsWithLimit() {
        List<ProductPOJO> products = Allure.step("Получить продукты с лимитом 3", () -> given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "products?limit=3")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList(".", ProductPOJO.class));
        Allure.step("Проверить количество и ID полученных продуктов", () -> {
            List<Integer> expectedIds = new ArrayList<>(){{
                add(1);
                add(2);
                add(3);
            }};
            List<Integer> actualIds = products.stream().map(ProductPOJO::id).toList();
            Assert.assertEquals(3, products.size());
            Assert.assertEquals(expectedIds, actualIds);
        });
    }

    @Test
    @DisplayName("Получить отсортированные продукты." +
            "Проверить что выборка отсортирована")
    @Owner("Kriksson")
    public void getProductsWithSort() {
        List<ProductPOJO> products = Allure.step("Получить продукты в порядке убывания ID", () -> given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products?sort=desc")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList(".", ProductPOJO.class));

        Allure.step("Проверить, что список продуктов не пуст и отсортирован по убыванию ID", () -> {
            Assert.assertFalse(products.isEmpty());
            List<ProductPOJO> sortedProducts = products.stream().sorted(Comparator.comparingInt(ProductPOJO::id)).toList().reversed();
            Assert.assertEquals(products, sortedProducts);
        });
    }

    @Test
    @DisplayName("Получить все категории." +
            "Проверить что в категориях есть существующие")
    @Owner("Kriksson")
    public void getCategories() {
        List<String> categories = Allure.step("Получить все категории продуктов", () -> given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products/categories")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", String.class));

        Allure.step("Проверить наличие категорий electronics и jewelery", () -> {
            Assert.assertTrue(categories.contains("electronics"));
            Assert.assertTrue(categories.contains("jewelery"));
        });
    }

    @Test
    @DisplayName("Получить все продукты по категории." +
            "Проверить что в выборке все продукты нужной категории")
    @Owner("Kriksson")
    public void getAllProductsByCategory() {
        List<ProductPOJO> products = Allure.step("Получить продукты категории jewelery", () -> given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products/category/jewelery")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProductPOJO.class));

        Allure.step("Проверить, что список не пуст и все продукты относятся к категории jewelery", () -> {
            Assert.assertFalse(products.isEmpty());
            Assert.assertTrue(products.stream().allMatch(p -> p.category().equals("jewelery")));
        });
    }

    @Test
    @DisplayName("Создать новый продукт." +
            "Проверить что вернулось тело с указанными параметрами")
    @Owner("Kriksson")
    public void createNewProduct() {
        CreateProductPOJO product =
                new CreateProductPOJO(1, "Check", 10.0,
                        "Test", "WTF", "https://youtube.com");
        ProductPOJO createdProduct = Allure.step("Создать продукт с указанными данными", () -> given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(product)
                .when()
                .post(URL+"products")
                .then()
                .statusCode(201)
                .log().all()
                .extract().body().as(ProductPOJO.class));
        Allure.step("Проверить данные созданного продукта с ожидаемыми", () -> {
            Assert.assertEquals(product.title(), createdProduct.title());
            Assert.assertEquals(product.price(), createdProduct.price());
            Assert.assertEquals(product.description(), createdProduct.description());
            Assert.assertEquals(product.category(), createdProduct.category());
            Assert.assertEquals(product.image(), createdProduct.image());
        });
    }

    @Test
    @DisplayName("Обновить продукт." +
            "Проверить что вернувшееся тело соответствует заданному")
    @Owner("Kriksson")
    public void updateProduct() {
        CreateProductPOJO product =
                new CreateProductPOJO(1, "QA Notebook updated", 24.99,
                        "Check", "WTF", "https://youtube.com");
        ProductPOJO updatedProduct = Allure.step("Обновить продукт с ID 1", () -> given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(product)
                .when()
                .put(URL+"products/1")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().as(ProductPOJO.class));

        Allure.step("Проверить название и цену обновлённого продукта с ожидаемыми", () -> {
            Assert.assertEquals(product.title(), updatedProduct.title());
            Assert.assertEquals(product.price(), updatedProduct.price());
        });
    }

    @Test
    @DisplayName("Удалить продукт." +
            "Проверить что вернувшееся ID удаленного продукта соответствует запросу")
    @Owner("Kriksson")
    public void deleteProduct() {
        ProductPOJO deletedProduct = Allure.step("Удалить продукт с ID 1", () -> given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(URL+"products/1")
                .then()
                .log().all()
                .extract().body().as(ProductPOJO.class));

        Allure.step("Проверить ID удалённого продукта с ожидаемым", () -> {
            Assert.assertEquals(Integer.valueOf(1), deletedProduct.id());
        });
    }
}
