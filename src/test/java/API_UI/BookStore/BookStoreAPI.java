package API_UI.BookStore;

import static io.restassured.RestAssured.given;

public class BookStoreAPI {

    public static BookDTO getBookWithAPI(String URL, String ISBN) {
        return given()
                .when()
                .queryParam("ISBN", ISBN)
                .get(URL)
                .then()
                .log().all()
                .extract().body().as(BookDTO.class);
    }
}
