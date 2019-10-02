package codesquad.web;

import codesquad.AcceptanceTest;
import codesquad.domain.Category;
import codesquad.domain.CategoryRepository;
import org.junit.Before;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.*;

import java.net.URI;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertAll;

public class AdminAcceptanceTest extends AcceptanceTest {
    private static final String BASE_URL = "/admin/category/";
    private static final String DEFAULT_MENU_TITLE = "pizza";

    private static final Logger log = LoggerFactory.getLogger(AdminAcceptanceTest.class);

    private Category mockMenu;

    @Autowired
    private TestRestTemplate adminTemplate;

    @Autowired
    private TestRestTemplate userTemplate;

    @Autowired
    private CategoryRepository categoryRepository;

    @Before
    public void setUp() {
        mockMenu = new Category().createDefaultCategory(DEFAULT_MENU_TITLE);

        adminTemplate = basicAuthTemplate(adminAccount());
        userTemplate = basicAuthTemplate(defaultUser());

        log.debug("adminTemplate: ", adminTemplate);
        log.debug("userTemplate: ", userTemplate);
    }

    @Test
    public void request_adminPage() {
        ResponseEntity<String> adminResponse = adminTemplate.getForEntity("/admin", String.class);
        ResponseEntity<String> userResponse = userTemplate.getForEntity("/admin", String.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, adminResponse.getStatusCode())
//                ,() -> assertEquals(HttpStatus.FORBIDDEN, userResponse.getStatusCode())
        );
    }

    @Test
    public void create_category() {
        categoryRepository.deleteAll();

        ResponseEntity<String> adminResponse = adminTemplate
                .postForEntity(BASE_URL + "create", mockMenu, String.class);


        assertAll(
                () -> assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).isPresent()).isTrue()

        );
    }


    @Test
    public void create_category_when_not_admin() {
        categoryRepository.deleteAll();

        ResponseEntity<String> userResponse = userTemplate
                .postForEntity(BASE_URL + "create", mockMenu, String.class);

        assertAll(
                () -> assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN), () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).isPresent()).isFalse()
        );

        categoryRepository.deleteAll();
    }

    @Test
    public void show_category() {
        categoryRepository.deleteAll();

        ResponseEntity<String> adminResponse = adminTemplate
                .getForEntity(BASE_URL + mockMenu.getId(), String.class);

        assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.FOUND);
    }

    @Test
    public void show_category_when_not_admin() {
        ResponseEntity<String> userResponse = userTemplate
                .getForEntity(BASE_URL + mockMenu.getId(), String.class);

        assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN);
    }

    @Test
    public void update_category() {
        categoryRepository.deleteAll();
        categoryRepository.save(mockMenu);
        log.info("categoryDB: {}", categoryRepository.findAll());

        Category updatedMenu = categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new);
        log.debug("updatedMenu: ", updatedMenu);

        String url = BASE_URL + updatedMenu.getId() + "/update";

        updatedMenu.title("chicken")
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity httpEntity = new HttpEntity(updatedMenu, headers);

        ResponseEntity<Category> adminResponse = adminTemplate
                .exchange(URI.create(url), HttpMethod.PUT, httpEntity, Category.class);

        assertAll(
                () -> assertEquals(HttpStatus.OK, adminResponse.getStatusCode()),
                () -> assertThat(categoryRepository.findByTitle(updatedMenu.getTitle()).isPresent()).isTrue(),
                () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).isPresent()).isFalse()
        );
    }

    @Test
    public void update_category_when_not_admin() {
        categoryRepository.deleteAll();
        categoryRepository.save(mockMenu);
        log.info("categoryDB: {}", categoryRepository.findAll());

        Category updatedMenu = categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new);

        String url = BASE_URL + updatedMenu.getId() + "/update";

        updatedMenu.title("chicken")
                .build();

        ResponseEntity<String> userResponse = userTemplate
                .postForEntity(url, HttpMethod.PUT, String.class, updatedMenu);

        assertAll(
                () -> assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN),
                () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).isPresent()).isTrue(),
                () -> assertThat(categoryRepository.findByTitle(updatedMenu.getTitle()).isPresent()).isFalse()
        );
    }

    @Test
    public void delete_category() {
        categoryRepository.deleteAll();
        categoryRepository.save(mockMenu);
        log.info("categoryDB: {}", categoryRepository.findAll());

        Category deletedMenu = categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new);

        String url = BASE_URL + deletedMenu.getId() + "/delete";

        deletedMenu.delete();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<Void> adminResponse = adminTemplate
                .exchange(URI.create(url), HttpMethod.DELETE, httpEntity, Void.class);

        assertAll(
                () -> assertThat(adminResponse.getStatusCode()).isEqualTo(HttpStatus.OK),
                () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new).isDeleted()).isTrue()
        );
    }

    @Test
    public void delete_category_when_not_admin() {
        categoryRepository.deleteAll();
        categoryRepository.save(mockMenu);
        log.info("categoryDB: {}", categoryRepository.findAll());

        Category deletedMenu = categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new);

        String url = BASE_URL + deletedMenu.getId() + "/delete";

        deletedMenu.delete();

        HttpHeaders headers = new HttpHeaders();
        HttpEntity httpEntity = new HttpEntity(headers);

        ResponseEntity<Void> userResponse = userTemplate
                .exchange(URI.create(url), HttpMethod.DELETE, httpEntity, Void.class);

        assertAll(
                () -> assertThat(userResponse.getStatusCode()).isEqualTo(HttpStatus.FORBIDDEN),
                () -> assertThat(categoryRepository.findByTitle(DEFAULT_MENU_TITLE).orElseThrow(RuntimeException::new).isDeleted()).isFalse()
        );
    }
}
