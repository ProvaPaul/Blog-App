package com.example.blogapplication;

import androidx.test.ext.junit.rules.ActivityScenarioRule;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.action.ViewActions;
import org.junit.Rule;
import org.junit.Test;

import static androidx.test.espresso.matcher.ViewMatchers.withId;


public class StartPgTest {

        @Rule
        public ActivityScenarioRule<Start_page> activityRule = new ActivityScenarioRule<>(Start_page.class);

        @Test
        public void testStartButton() {
            // Click the start button
            Espresso.onView(withId(R.id.startButton)).perform(ViewActions.click());

            // You can add assertions here to verify the expected behavior after clicking the button
        }
    }
