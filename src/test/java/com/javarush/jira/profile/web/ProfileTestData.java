package com.javarush.jira.profile.web;

import com.javarush.jira.MatcherFactory;
import com.javarush.jira.profile.ProfileTo;
import com.javarush.jira.profile.internal.Profile;

public class ProfileTestData {
    public static final long USER_ID = 1;
    public static final long ADMIN_ID = 2;
    public static final long GUEST_ID = 3;

    public static final long NOT_FOUND = 100;
    public static final String USER_MAIL = "user@gmail.com";
    public static final String ADMIN_MAIL = "admin@gmail.com";
    public static final String GUEST_MAIL = "guest@gmail.com";
    public static final MatcherFactory.Matcher<ProfileTo> PROFILE_TO_MATCHER = MatcherFactory.usingIgnoringFieldsComparator(ProfileTo.class, "lastLogin", "lastFailedLogin");

    public static Profile getUpdated() {
        return new Profile(ADMIN_ID);
    }

}


