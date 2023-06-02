package com.javarush.jira.profile.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.login.internal.UserRepository;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.ProfileMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_MAIL;
import static com.javarush.jira.profile.web.ProfileTestData.*;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    ProfileMapper mapper;
    @Autowired
    private UserRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
                perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }


    @Test
    @WithUserDetails(value = GUEST_MAIL)
     void update() throws Exception {

    ObjectMapper mapper = new ObjectMapper();


    ProfileTo beforeUpdated = ProfileTestData.getNew();
    ProfileTo updatedTo = ProfileTestData.getUpdated();

    String json = mapper.writeValueAsString(beforeUpdated);
    perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
            .contentType(MediaType.APPLICATION_JSON)
            .content(json))
            .andDo(print())
            .andExpect(status().isNoContent());

    assertEquals(beforeUpdated.getId(), updatedTo.getId(), "user's id must not be changed");
    assertNotEquals(beforeUpdated.getMailNotifications().size(), updatedTo.getMailNotifications().size());
}

}
