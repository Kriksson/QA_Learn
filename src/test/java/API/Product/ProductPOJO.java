package API.Product;

public record ProductPOJO(Integer id, String title, Double price, String description, String category, String image, RatingPOJO rating) {
}
