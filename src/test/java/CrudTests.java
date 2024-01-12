import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CrudTests {

    String baseUrl = "https://reqres.in/api";

    @Test
    void createUserTest() {
        String userData = "{\"name\": \"morpheus\",\"job\": \"leader\"}";

        given().body(userData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post(baseUrl + "/users")
                .then()
                .log().status()
                .log().body()
                .statusCode(201)
                .body("name", is("morpheus"))
                .body("job", is("leader"))
                .body("id", is(notNullValue()))
                .body("createdAt", is(notNullValue()));
    }

    @Test
    void updateUserTest() {
        String updateUserData = "{\"name\": \"morpheus\",\"job\": \"zion resident\"}";

        given().body(updateUserData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .put(baseUrl + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("name", is("morpheus"))
                .body("job", is("zion resident"));
    }

    @Test
    void successfulRegisterUserTest() {

        String userData = "{\"email\": \"eve.holt@reqres.in\",\"password\": \"pistol\"}";

        given().body(userData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post(baseUrl + "/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(200)
                .body("id", is(4))
                .body("token", is("QpwL5tke4Pnpja7X4"));
    }

    @Test
    void unsuccessfulRegisterUserTest() {

        String userData = "{\"email\": \"sydney@fife\"}";

        given().body(userData)
                .contentType(ContentType.JSON)
                .log().body()
                .when()
                .post(baseUrl + "/register")
                .then()
                .log().status()
                .log().body()
                .statusCode(400)
                .body("error", is("Missing password"));
    }

    @Test
    void deleteUserTest() {

        given().when()
                .delete(baseUrl + "/users/2")
                .then()
                .log().status()
                .log().body()
                .statusCode(204);
    }

}
