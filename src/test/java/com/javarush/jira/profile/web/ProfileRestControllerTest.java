package com.javarush.jira.profile.web;

import com.javarush.jira.AbstractControllerTest;
import com.javarush.jira.common.util.JsonUtil;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;
import com.javarush.jira.profile.internal.ProfileMapper;
import com.javarush.jira.profile.internal.ProfileRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static com.javarush.jira.login.internal.web.UserTestData.ADMIN_MAIL;
import static com.javarush.jira.profile.web.ProfileTestData.ADMIN_ID;
import static com.javarush.jira.profile.web.ProfileTestData.getUpdated;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;



public class ProfileRestControllerTest extends AbstractControllerTest {
    @Autowired
    ProfileMapper mapper;
    @Autowired
    private ProfileRepository repository;

    @Test
    @WithUserDetails(value = ADMIN_MAIL)
    void get() throws Exception {
                perform(MockMvcRequestBuilders.get(ProfileRestController.REST_URL))
                .andExpect(status().isOk())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id", is(2)));
    }


    @Test
    @WithUserDetails(value = ADMIN_MAIL)
     void update() throws Exception {
    ProfileTo updatedTo = mapper.toTo(getUpdated());


    perform(MockMvcRequestBuilders.put(ProfileRestController.REST_URL)
            .content(JsonUtil.writeValue(updatedTo))
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isNoContent());
    Profile afterUpdated = repository.getExisted(ADMIN_ID);
    assertEquals(updatedTo.getId(),afterUpdated.getId(), "user's id must not be changed");
    assertNotEquals(updatedTo.getMailNotifications(), afterUpdated.getMailNotifications());
}

}
