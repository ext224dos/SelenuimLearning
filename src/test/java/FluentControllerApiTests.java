import controllers.FluentPetController;
import controllers.FluentUserController;

import io.qameta.allure.Feature;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static testdata.ApiTestData.DEFAULT_PET;
import static testdata.ApiTestData.DEFAULT_USER;

@Feature("FluentControllerTests")
@Tag("api")
class FluentControllerApiTests {
    FluentUserController fluentUserController = new FluentUserController();
    FluentPetController fluentPetController = new FluentPetController();

    @BeforeEach
    @AfterEach
    void clear() {
        fluentUserController.deleteUserByName(DEFAULT_USER.getUsername());
        fluentPetController.deletePetById(DEFAULT_PET.getId());
    }

    @Test
    @Tag("smoke")
    @DisplayName("Check add user is returns 200 status ok")
    void checkAddUserTest() {
        fluentUserController.addDefaultUser()
                .statusCodeIs(200);
    }

    @Test
    @Tag("smoke")
    @DisplayName("Check add pet is returns 200 status ok")
    void checkAddPetTest() {
        fluentPetController.addDefaultPet()
                .statusCodeIs(200);
    }

    @Test
    @Tag("extended")
    @DisplayName("Check user added correctly")
    void checkAddUserExtendedTest() {
        String expectedId = fluentUserController.addUser(DEFAULT_USER)
                .statusCodeIs(200)
                .getJsonValue("message");

        fluentUserController.getUserByName(DEFAULT_USER.getUsername())
                .statusCodeIs(200)
                .jsonValueIs("id", expectedId)
                .jsonValueIs("username", DEFAULT_USER.getUsername())
                //...
                .jsonValueIs("email", DEFAULT_USER.getEmail());
    }

    @Test
    @Tag("extended")
    @DisplayName("Check pet added correctly")
    void checkAddPetExtendedTest() {
        Long expectedId = Long.parseLong(fluentPetController.addPet(DEFAULT_PET)
                .statusCodeIs(200)
                .getJsonValue("id"));

        fluentPetController.getPetById(expectedId)
                .statusCodeIs(200)
                .jsonValueIs("id", expectedId.toString())
                .jsonValueIs("name", DEFAULT_PET.getName())
                //...
                .jsonValueIs("status", DEFAULT_PET.getStatus());
    }
}
