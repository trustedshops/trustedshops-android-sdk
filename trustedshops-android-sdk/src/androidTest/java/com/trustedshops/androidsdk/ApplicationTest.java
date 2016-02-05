package com.trustedshops.androidsdk;

import android.app.Application;
import android.test.ApplicationTestCase;
import android.test.suitebuilder.annotation.MediumTest;
import android.test.suitebuilder.annotation.SmallTest;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.trustedshops.androidsdk.trustbadge.Trustbadge;
import com.trustedshops.androidsdk.trustbadge.TrustbadgeException;

public class ApplicationTest extends ApplicationTestCase<Application> {
    public ApplicationTest() {
        super(Application.class);
    }
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        createApplication();
    }
    @SmallTest
    public void testTsIdValidator() {
        Trustbadge test = new Trustbadge("X12312312321321");

        ImageView testImageView = new ImageView(getApplication());
        try {
            test.getTrustbadge(testImageView);
        } catch (TrustbadgeException exception) {
            assertEquals("Wrong TSID", exception.getMessage());
        }
    }

    @SmallTest
    public void testTsIdValidatorValidTsId() {
        Trustbadge test = new Trustbadge("X6A4AACCD2C75E430381B2E1C4CLASSIC");

        ImageView testImageView = new ImageView(getApplication());
        try {
            test.getTrustbadge(testImageView);
        } catch (IllegalArgumentException exception) {
            Log.d("TSDEBUG","testTsIdValidatorValidTsId IllegalArgumentException: " + exception.getMessage());
        } catch (TrustbadgeException exception) {
            Log.d("TSDEBUG","testTsIdValidatorValidTsId TrustbadgeException: " + exception.getMessage());
        }
        assertTrue(testImageView.getTag().equals("Trustbadge"));
    }
}