package com.example.drake.stamploadproject;


import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.test.suitebuilder.annotation.LargeTest;

import com.example.drake.stamploadproject.Activity.LoginClientActitivy;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;

@LargeTest
@RunWith(AndroidJUnit4.class)
public class LoginClientActitivyTest {

    @Rule
    public ActivityTestRule<LoginClientActitivy> mActivityTestRule = new ActivityTestRule<>(LoginClientActitivy.class);

    @Test
    public void loginClientActitivyTest() {
    }

}
