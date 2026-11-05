package API.Product;

import io.restassured.http.ContentType;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static io.restassured.RestAssured.given;

public class ProductTest {

    public final static String URL = "https://fakestoreapi.com/";

    @Test
    public void getProductById() {
        ProductPOJO product = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "products/1")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().as(ProductPOJO.class);

        Assert.assertEquals(Integer.valueOf(1), product.id());
        Assert.assertFalse(product.title().isEmpty());
        Assert.assertTrue(product.price() > 0);
        Assert.assertEquals("men's clothing", product.category());
    }

    @Test
    public void getProductsWithLimit() {
        List<ProductPOJO> products = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL + "products?limit=3")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList(".", ProductPOJO.class);
        List<Integer> expectedIds = new ArrayList<>(){{
            add(1);
            add(2);
            add(3);
        }};
        List<Integer> actualIds = products.stream().map(ProductPOJO::id).toList();
        Assert.assertEquals(3, products.size());
        Assert.assertEquals(expectedIds, actualIds);
    }

    @Test
    public void getProductsWithSort() {
        List<ProductPOJO> products = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products?sort=desc")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().jsonPath().getList(".", ProductPOJO.class);

        Assert.assertFalse(products.isEmpty());
        List<ProductPOJO> sortedProducts = products.stream().sorted(Comparator.comparingInt(ProductPOJO::id)).toList().reversed();
        Assert.assertEquals(products, sortedProducts);
    }

    @Test
    public void getCategories() {
        List<String> categories = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products/categories")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", String.class);

        Assert.assertTrue(categories.contains("electronics"));
        Assert.assertTrue(categories.contains("jewelery"));
    }

    @Test
    public void getAllProductsByCategory() {
        List<ProductPOJO> products = given()
                .contentType(ContentType.JSON)
                .when()
                .get(URL+"products/category/jewelery")
                .then()
                .log().all()
                .statusCode(200)
                .extract().body().jsonPath().getList(".", ProductPOJO.class);

        Assert.assertFalse(products.isEmpty());
        Assert.assertTrue(products.stream().allMatch(p -> p.category().equals("jewelery")));
    }

    @Test
    public void createNewProduct() {
        CreateProductPOJO product =
                new CreateProductPOJO(1, "Check", 10.0,
                        "Test", "WTF", "https://youtube.com");
        ProductPOJO createdProduct = given().
                contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(product)
                .when()
                .post(URL+"products")
                .then()
                .statusCode(201)
                .log().all()
                .extract().body().as(ProductPOJO.class);
        Assert.assertEquals(product.title(), createdProduct.title());
        Assert.assertEquals(product.price(), createdProduct.price());
        Assert.assertEquals(product.description(), createdProduct.description());
        Assert.assertEquals(product.category(), createdProduct.category());
        Assert.assertEquals(product.image(), createdProduct.image());
    }

    @Test
    public void updateProduct() {
        CreateProductPOJO product =
                new CreateProductPOJO(1, "QA Notebook updated", 24.99,
                        "Check", "WTF", "https://youtube.com");
        ProductPOJO updatedProduct = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body(product)
                .when()
                .put(URL+"products/1")
                .then()
                .statusCode(200)
                .log().all()
                .extract().body().as(ProductPOJO.class);

        Assert.assertEquals(product.title(), updatedProduct.title());
        Assert.assertEquals(product.price(), updatedProduct.price());
    }

    @Test
    public void deleteProduct() {
        ProductPOJO deletedProduct = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .when()
                .delete(URL+"products/1")
                .then()
                .log().all()
                .extract().body().as(ProductPOJO.class);

        Assert.assertEquals(Integer.valueOf(1), deletedProduct.id());
    }
}
