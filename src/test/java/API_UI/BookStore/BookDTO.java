package API_UI.BookStore;

public record BookDTO(String isbn, String title, String subTitle, String author, String publish_date, String publisher,
                      Integer pages, String description, String website) {
}
