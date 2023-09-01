package com.polarbookshop.catalogservice;

import com.polarbookshop.catalogservice.model.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.reactive.server.WebTestClient;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("integration")
class CatalogServiceApplicationTests {

    private WebTestClient webTestClient;

    @Autowired
    public CatalogServiceApplicationTests(WebTestClient webTestClient) {
        this.webTestClient = webTestClient;
    }

    @Test
    void whenGetRequestThenBookReturned(){
        Book newBook = new Book("Test 1 Book","1234567890", "Author",12.0);

        Book expectedBook = webTestClient
                            .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                }).returnResult().getResponseBody();

        webTestClient.get()
                .uri("/books/" + expectedBook.getIsbn())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class)
                .value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
                });
    }

    @Test
    void whenPostRequestThenBookCreated() {
        Book expectedBook = new Book("Title","1234567891", "Author",12.0);

        webTestClient
                .post()
                .uri("/books")
                .bodyValue(expectedBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                    assertThat(actualBook.getIsbn()).isEqualTo(expectedBook.getIsbn());
                });

    }

    @Test
    void whenPutRequestThenBookUpdated(){
        Book newBook = new Book("Title","1234567892", "Author",12.0);
        Book expectedBook = webTestClient
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                }).returnResult().getResponseBody();

        Book bookModified = new Book("Title2", "1234567892", "Author2", 13.0);
        webTestClient
                .put()
                .uri("/books/" + expectedBook.getIsbn())
                .bodyValue(bookModified)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook.getIsbn()).isEqualTo(bookModified.getIsbn());
                    assertThat(actualBook.getAuthor()).isEqualTo(bookModified.getAuthor());
                    assertThat(actualBook.getTitle()).isEqualTo(bookModified.getTitle());
                    assertThat(actualBook.getPrice()).isEqualTo(bookModified.getPrice());
                });
    }

    @Test
    void whenDeleteRequestThenBookDeleted(){
        Book newBook = new Book("Title","1234567893", "Author",12.0);
        webTestClient
                .post()
                .uri("/books")
                .bodyValue(newBook)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Book.class).value(actualBook -> {
                    assertThat(actualBook).isNotNull();
                }).returnResult().getResponseBody();

        webTestClient
                .delete()
                .uri("/books/" + newBook.getIsbn())
                .exchange()
                .expectStatus().isNoContent();

        webTestClient
                .get()
                .uri("/books/" + newBook.getIsbn())
                .exchange()
                .expectStatus().isNotFound()
                .expectBody(String.class).value(errMessage -> {
                    assertThat(errMessage).isEqualTo("The book with ISBN= " + newBook.getIsbn() + " was not found.");
                });
    }

}
