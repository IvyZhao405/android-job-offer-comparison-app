package edu.gatech.seclass.jobcompare6300;

import android.content.Context;
import android.os.SystemClock;

import androidx.test.espresso.DataInteraction;
import androidx.test.espresso.Espresso;
import androidx.test.espresso.ViewInteraction;
import androidx.test.espresso.internal.inject.InstrumentationContext;
import androidx.test.ext.junit.runners.AndroidJUnit4;
import androidx.test.platform.app.InstrumentationRegistry;
import androidx.test.rule.ActivityTestRule;

import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewParent;

import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.hamcrest.TypeSafeMatcher;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;

import static androidx.test.espresso.Espresso.onData;
import static androidx.test.espresso.Espresso.onView;
import static androidx.test.espresso.action.ViewActions.clearText;
import static androidx.test.espresso.action.ViewActions.click;
import static androidx.test.espresso.action.ViewActions.replaceText;
import static androidx.test.espresso.assertion.ViewAssertions.matches;
import static androidx.test.espresso.matcher.ViewMatchers.hasErrorText;
import static androidx.test.espresso.matcher.ViewMatchers.isDisplayed;
import static androidx.test.espresso.matcher.ViewMatchers.withClassName;
import static androidx.test.espresso.matcher.ViewMatchers.withId;
import static androidx.test.espresso.matcher.ViewMatchers.withParent;
import static androidx.test.espresso.matcher.ViewMatchers.withText;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.anything;
import static org.hamcrest.Matchers.hasToString;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.StringStartsWith.startsWith;

@RunWith(AndroidJUnit4.class)
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SystemTests {

    //Context appContext = InstrumentationRegistry.getInstrumentation().getContext();

    @Rule
    public ActivityTestRule<MainActivity> tActivityRule = new ActivityTestRule<>(
            MainActivity.class);

    @BeforeClass
    public static void setUp()
    {
        InstrumentationRegistry.getInstrumentation().getTargetContext().deleteDatabase("job.db");
    }


    @Test
    public void Test1CurrentJob() {
        onView(withId(R.id.enterCurrentJobButtonID)).perform(click());
        onView(withId(R.id.currentjob_titleID)).perform(clearText(), replaceText("Current Job"));
        onView(withId(R.id.currentjob_companyID)).perform(clearText(), replaceText("Current Company"));
        onView((withId(R.id.currentjob_cityID))).perform(clearText(), replaceText("Denver"));
        onView(withId(R.id.currentjob_stateID)).perform(clearText(), replaceText("Colorado"));
        onView((withId(R.id.currentjob_costLivingIndexID))).perform(clearText(), replaceText("150"));
        onView((withId(R.id.currentjob_yearlySalaryID))).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.currentjob_signingBonusID)).perform(clearText(), replaceText("20000"));
        onView(withId(R.id.currentjob_yearlyBonusID)).perform(clearText(), replaceText("10000"));
        onView(withId(R.id.currentjob_retirementBenefitID)).perform(clearText(), replaceText("15"));
        onView(withId(R.id.currentjob_leaveTimeID)).perform(clearText(), replaceText("20"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.currentjob_saveButtonID)).perform(click());
        onView(withId(R.id.enterCurrentJobButtonID)).perform(click());
        onView(withId(R.id.currentjob_titleID)).check(matches(withText("Current Job")));
        onView(withId(R.id.currentjob_companyID)).check(matches(withText("Current Company")));
        onView(withId(R.id.currentjob_cityID)).check(matches(withText("Denver")));
        onView(withId(R.id.currentjob_stateID)).check(matches(withText("Colorado")));
        onView(withId(R.id.currentjob_costLivingIndexID)).check(matches(withText("150")));
        onView(withId(R.id.currentjob_yearlySalaryID)).check(matches(withText("150000.0")));
        onView(withId(R.id.currentjob_signingBonusID)).check(matches(withText("20000.0")));
        onView(withId(R.id.currentjob_yearlyBonusID)).check(matches(withText("10000.0")));
        onView(withId(R.id.currentjob_retirementBenefitID)).check(matches(withText("15.0")));
        onView(withId(R.id.currentjob_leaveTimeID)).check(matches(withText("20")));

    }

    @Test
    public void Test2JobOffer() {
        // Load and save new job offer.
        onView(withId(R.id.enterJobOffersButtonID)).perform(click());
        onView(withId(R.id.joboffer_titleID)).perform(clearText(), replaceText("Job Offer 1"));
        onView(withId(R.id.joboffer_companyID)).perform(clearText(), replaceText("ABC Corporation"));
        onView((withId(R.id.joboffer_cityID))).perform(clearText(), replaceText("Chicago"));
        onView(withId(R.id.joboffer_stateID)).perform(clearText(), replaceText("IL"));
        onView((withId(R.id.joboffer_costLivingIndexID))).perform(clearText(), replaceText("100"));
        onView((withId(R.id.joboffer_yearlySalaryID))).perform(clearText(), replaceText("250000"));
        onView(withId(R.id.joboffer_signingBonusID)).perform(clearText(), replaceText("50000"));
        onView(withId(R.id.joboffer_yearlyBonusID)).perform(clearText(), replaceText("250000"));
        onView(withId(R.id.joboffer_retirementBenefitID)).perform(clearText(), replaceText("25"));
        onView(withId(R.id.joboffer_leaveTimeID)).perform(clearText(), replaceText("50"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.joboffer_saveButtonID)).perform(click());
        SystemClock.sleep(1000);
        // Load and save Another job offer.
        onView(withId(R.id.joboffer_enteranother_ButtonID)).perform(click());
        onView(withId(R.id.joboffer_titleID)).perform(clearText(), replaceText("Job Offer 2"));
        onView(withId(R.id.joboffer_companyID)).perform(clearText(), replaceText("Systems Tech Ltd."));
        onView((withId(R.id.joboffer_cityID))).perform(clearText(), replaceText("Springfield"));
        onView(withId(R.id.joboffer_stateID)).perform(clearText(), replaceText("IL"));
        onView((withId(R.id.joboffer_costLivingIndexID))).perform(clearText(), replaceText("120"));
        onView((withId(R.id.joboffer_yearlySalaryID))).perform(clearText(), replaceText("200000"));
        onView(withId(R.id.joboffer_signingBonusID)).perform(clearText(), replaceText("60000"));
        onView(withId(R.id.joboffer_yearlyBonusID)).perform(clearText(), replaceText("150000"));
        onView(withId(R.id.joboffer_retirementBenefitID)).perform(clearText(), replaceText("5"));
        onView(withId(R.id.joboffer_leaveTimeID)).perform(clearText(), replaceText("40"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.joboffer_saveButtonID)).perform(click());
        SystemClock.sleep(1000);
        // Compare with current job test.
        onView(withId(R.id.joboffer_compareButtonID)).perform(click());
        SystemClock.sleep(1500);
        onView(withId(R.id.compare_backButtonID)).perform(click());
        // Cancel button test.
        onView(withId(R.id.joboffer_cancelButtonID)).perform(click());
    }

    @Test
    public void Test3OfferComparison()
    {
        onView(withId(R.id.compareJobOffersButtonID)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.jobrank_rankListID)).atPosition(0)
                .onChildView(withId(R.id.checkbox)).perform(click());
        onData(anything()).inAdapterView(withId(R.id.jobrank_rankListID)).atPosition(1)
                .onChildView(withId(R.id.checkbox)).perform(click());
        onView(withId(R.id.jobrank_compareButtonID)).perform(click());
        onView(withId(R.id.jobcomp_firstTitleID)).check(matches(withText("Job Offer 1")));
        onView(withId(R.id.jobcomp_firstCompanyID)).check(matches(withText("ABC Corporation")));
        onView(withId(R.id.jobcomp_firstLocationID)).check(matches(withText("Chicago, IL")));
        onView(withId(R.id.jobcomp_firstYearlySalaryID)).check(matches(withText("250000.0")));
        onView(withId(R.id.jobcomp_firstSigningBonusID)).check(matches(withText("50000.0")));
        onView(withId(R.id.jobcomp_firstYearlyBonusID)).check(matches(withText("250000.0")));
        onView(withId(R.id.jobcomp_firstRetirementBenefitID)).check(matches(withText("25.0")));
        onView(withId(R.id.jobcomp_firstLeaveTimeID)).check(matches(withText("50")));

        onView(withId(R.id.jobcomp_secondTitleID)).check(matches(withText("Job Offer 2")));
        onView(withId(R.id.jobcomp_secondCompanyID)).check(matches(withText("Systems Tech Ltd.")));
        onView(withId(R.id.jobcomp_secondLocationID)).check(matches(withText("Springfield, IL")));
        onView(withId(R.id.jobcomp_secondYearlySalaryID)).check(matches(withText("200000.0")));
        onView(withId(R.id.jobcomp_secondSigningBonusID)).check(matches(withText("60000.0")));
        onView(withId(R.id.jobcomp_secondYearlyBonusID)).check(matches(withText("150000.0")));
        onView(withId(R.id.jobcomp_secondRetirementBenefitID)).check(matches(withText("5.0")));
        onView(withId(R.id.jobcomp_secondLeaveTimeID)).check(matches(withText("40")));

        onView(withId(R.id.jobcomp_cancelButtonID)).perform(click());

    }

    @Test
    public void Test4AdjustComparisonSettings1() {

        //check if initial values are all 1s.
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlySalaryID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_signingBonusID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_yearlyBonusID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_retirementBenefitID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_leaveTimeID)).check(matches(withText("1")));


        //check error message match if enter empty weights for yearly Salary
        onView(withId(R.id.compsetting_yearlySalaryID)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlySalaryID)).check(matches(hasErrorText("Empty Salary Weight!")));

        //check error message match if enter 0 as weight for yearly Salary
        onView(withId(R.id.compsetting_yearlySalaryID)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlySalaryID)).check(matches(hasErrorText("Please enter a nonzero value.")));
        onView(withId(R.id.compsetting_cancelButtonID)).perform(click());


        //check error message match if enter empty weights for Signing Bonus
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_signingBonusID)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_signingBonusID)).check(matches(hasErrorText("Empty Signing Bonus Weight!")));

        //check error message match if enter 0 as weight for Signing Bonus
        onView(withId(R.id.compsetting_signingBonusID)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_signingBonusID)).check(matches(hasErrorText("Please enter a nonzero value.")));
        onView(withId(R.id.compsetting_cancelButtonID)).perform(click());


        //check error message match if enter empty weights for Yearly Bonus
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlyBonusID)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlyBonusID)).check(matches(hasErrorText("Empty Yearly Bonus Weight!")));

        //check error message match if enter 0 as weight for Yearly Bonus
        onView(withId(R.id.compsetting_yearlyBonusID)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlyBonusID)).check(matches(hasErrorText("Please enter a nonzero value.")));
        onView(withId(R.id.compsetting_cancelButtonID)).perform(click());


        //check error message match if enter empty weights for retirement Benefit
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_retirementBenefitID)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_retirementBenefitID)).check(matches(hasErrorText("Empty Retirement Benefits Weight!")));

        //check error message match if enter 0 as weight for retirement Benefit
        onView(withId(R.id.compsetting_retirementBenefitID)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_retirementBenefitID)).check(matches(hasErrorText("Please enter a nonzero value.")));
        onView(withId(R.id.compsetting_cancelButtonID)).perform(click());

        //check error message match if enter empty weights for Leave Time
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_leaveTimeID)).perform(clearText(), replaceText(""));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_leaveTimeID)).check(matches(hasErrorText("Empty Leave Time Weight!")));

        //check error message match if enter 0 as weight for Leave Time
        onView(withId(R.id.compsetting_leaveTimeID)).perform(clearText(), replaceText("0"));
        Espresso.closeSoftKeyboard();
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.compsetting_leaveTimeID)).check(matches(hasErrorText("Please enter a nonzero value.")));


        //enter valid new weights, save and check if the new weights are stored
        onView(withId(R.id.compsetting_yearlySalaryID)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.compsetting_signingBonusID)).perform(clearText(), replaceText("2"));
        onView(withId(R.id.compsetting_yearlyBonusID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_retirementBenefitID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_leaveTimeID)).perform(clearText(), replaceText("1"));
        Espresso.closeSoftKeyboard();

        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlySalaryID)).check(matches(withText("2")));
        onView(withId(R.id.compsetting_signingBonusID)).check(matches(withText("2")));
        onView(withId(R.id.compsetting_yearlyBonusID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_retirementBenefitID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_leaveTimeID)).check(matches(withText("1")));
        onView(withId(R.id.compsetting_cancelButtonID)).perform(click());

        //Restore weights to original settings
        onView(withId(R.id.adjustComparisonSettingsButtonID)).perform(click());
        onView(withId(R.id.compsetting_yearlySalaryID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_signingBonusID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_yearlyBonusID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_retirementBenefitID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_leaveTimeID)).perform(clearText(), replaceText("1"));
        onView(withId(R.id.compsetting_saveButtonID)).perform(click());
    }

}
