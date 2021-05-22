package com.example.demo.user;

import com.example.demo.BaseControllerTest;
import com.example.demo.TestCreationFactory;
import com.example.demo.user.dto.PasswordChangeRequest;
import com.example.demo.user.dto.UserDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static com.example.demo.UrlMapping.ENTITY;
import static com.example.demo.UrlMapping.USERS;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class UserControllerTest extends BaseControllerTest {

    @InjectMocks
    private UserController controller;

    @Mock
    private UserService userService;

    @BeforeEach
    protected void setUp() {
        super.setUp();
        controller = new UserController(userService);
        mockMvc = MockMvcBuilders.standaloneSetup(controller).build();
    }

    @Test
    void findAll() throws Exception {

        List<UserDTO> users = TestCreationFactory.listOf(UserDTO.class);
        when(userService.findAll()).thenReturn(users);

        ResultActions response = mockMvc.perform(get(USERS));

        response.andExpect(status().isOk())
                .andExpect(jsonContentToBe(users));
    }

    @Test
    void edit() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        when(userService.edit(id, user)).thenReturn(user);

        ResultActions result = performPutWithRequestBodyAndPathVariable(USERS + ENTITY, user, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void getUser() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        when(userService.getUser(id)).thenReturn(user);

        ResultActions result = performGetWithPathVariable(USERS + ENTITY, id);
        result.andExpect(status().isOk())
                .andExpect(jsonContentToBe(user));
    }

    @Test
    void changePassword() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();
        PasswordChangeRequest passwordChangeRequest = new PasswordChangeRequest("password");

        when(userService.changePassword(id,passwordChangeRequest.getPassword())).thenReturn(user);

        ResultActions result = performPatchWithRequestBodyAndPathVariable(USERS + ENTITY, passwordChangeRequest, id);
        verify(userService,times(1)).changePassword(id,passwordChangeRequest.getPassword());
        result.andExpect(status().isOk());
    }

    @Test
    void deleteUser() throws Exception {
        UserDTO user = TestCreationFactory.newUserDTO();
        long id = user.getId();

        doNothing().when(userService).delete(id);

        ResultActions result = performDeleteWIthPathVariable(USERS + ENTITY, id);
        verify(userService,times(1)).delete(id);
        result.andExpect(status().isOk());
    }
}