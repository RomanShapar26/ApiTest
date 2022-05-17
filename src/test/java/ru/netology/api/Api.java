package ru.netology.api;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.BeforeAll;
import ru.netology.generate.Generate;
import ru.netology.test.AuthTest;

import static io.restassured.RestAssured.given;

public class Api {
    private static RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    public static void signUp(Generate.UserInfo userInfo) {
        given()
                .spec(requestSpec)
                .body(userInfo)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }
}
