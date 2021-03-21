package com.example.myapplication;

import androidx.test.rule.ActivityTestRule;

import com.example.myapplication.activities.MainActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.robotium.solo.Solo;

import static androidx.test.platform.app.InstrumentationRegistry.getInstrumentation;
import static org.junit.Assert.assertTrue;

public class MainActivityTest extends ActivityTestRule<MainActivity> {
    private Solo solo;
    private MainActivity mActivity;
    private FloatingActionButton add;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    public void setUp() throws Exception {

        solo = new Solo(getInstrumentation(), getActivity());
        mActivity = this.getActivity();
        solo = new Solo(getInstrumentation(),getActivity());
        add = (FloatingActionButton) mActivity.findViewById(R.id.addNote);


    }
    public void testSearchButton() throws Exception {
        boolean found = solo.searchButton("");
        solo.clickOnView(add);
        assertTrue(found);
    }


}
