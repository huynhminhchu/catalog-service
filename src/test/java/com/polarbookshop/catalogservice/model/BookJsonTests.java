package com.polarbookshop.catalogservice.model;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.json.JsonTest;
import org.springframework.boot.test.json.JacksonTester;
import static org.assertj.core.api.Assertions.assertThat;
@JsonTest
public class BookJsonTests {

    @Autowired
    private JacksonTester<Book> json;

    @Test
    void testSerialize() throws Exception {
        Book newBook = new Book("Test 1 Book","1234567890", "Author",12.0);

        var jsonContent = json.write(newBook);

        assertThat(jsonContent).extractingJsonPathStringValue("@.isbn")
                .isEqualTo("1234567890");
        assertThat(jsonContent).extractingJsonPathStringValue("@.author")
                .isEqualTo("Author");
        assertThat(jsonContent).extractingJsonPathStringValue("@.title")
                .isEqualTo("Test 1 Book");
        assertThat(jsonContent).extractingJsonPathNumberValue("@.price").isEqualTo(12.0);
    }

    @Test
    void testDeserialize() throws Exception {
        var content = """
                {
                    "isbn": "1234567890",
                    "title": "Title",
                    "author": "Author",
                    "price": 12.0
                }
                """;
        assertThat(json.parse(content))
                .usingRecursiveComparison()
                .isEqualTo(new Book("Title","1234567890", "Author",12.0));
    }
}
