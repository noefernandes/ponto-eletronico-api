package com.noefer.pontoeletronicoapi;

import com.noefer.pontoeletronicoapi.model.Role;
import com.noefer.pontoeletronicoapi.model.UserProfile;
import com.noefer.pontoeletronicoapi.model.WorkLoad;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileRequest;
import com.noefer.pontoeletronicoapi.model.dto.UserProfileResponse;
import com.noefer.pontoeletronicoapi.service.UserProfileService;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import java.util.List;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class UserProfileServiceTests {

    @Autowired
    private UserProfileService service;

    @AfterEach
    void clenup() {
        service.deleteAll();
    }

    @Test
    @Order(1)
    void registerUser() {
        UserProfileRequest request = new UserProfileRequest("Noefer", "noefer", "123456", Role.ADMIN, WorkLoad.SIXHOURS);
        UserProfileResponse user = service.register(request);
        Assertions.assertEquals("Noefer", user.getName());
    }

    @Test
    @Order(2)
    void findUser() {
        UserProfileResponse user = service.register(new UserProfileRequest("Noefer", "noefer", "123456", Role.ADMIN, WorkLoad.SIXHOURS));
        service.findById(user.getId());
        Assertions.assertEquals("Noefer", user.getName());
    }

    @Test
    @Order(3)
    void findUserException() {
        Assertions.assertThrowsExactly(RuntimeException.class, () -> service.findById(2L));
    }

    @Test
    @Order(4)
    void findAllUsers() {
        service.register(new UserProfileRequest("Noefer", "noefer", "123456", Role.ADMIN, WorkLoad.SIXHOURS));
        service.register(new UserProfileRequest("Noefer2", "noefer", "123456", Role.ADMIN, WorkLoad.SIXHOURS));
        List<UserProfile> users = service.findAll();
        Assertions.assertEquals(2, users.size());
    }

    @Test
    @Order(5)
    void deleteUser() {
        UserProfileResponse user = service.register(new UserProfileRequest("Noefer", "noefer", "123456", Role.ADMIN, WorkLoad.SIXHOURS));
        service.delete(user.getId());
        Assertions.assertEquals(0, service.findAll().size());
    }
}
