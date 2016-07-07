package com.androidsdktests;


import android.test.suitebuilder.annotation.SmallTest;

import com.trustedshops.androidsdk.trustbadge.ReviewIndicator;
import com.trustedshops.androidsdk.trustbadge.Shop;
import com.trustedshops.androidsdk.trustbadge.Validator;

import org.junit.Before;
import org.junit.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.net.URL;
import java.util.Date;

@SmallTest
public class ApiParserTest {
    protected Shop _testShopTrustMarkApi, _testShopWithReviewsQualityIndicators, _testShopWithoutReviewsQualityIndicators;

    @Before
    public void setUp() {
        String jsonDataTrustmark = readFile(getFileFromPath(this, "validTrustmarkResponse.json"));
        String jsonDataQualityIndicatorsWithReviews = readFile(getFileFromPath(this, "shopWithReviews.json"));
        String jsonDataQualityIndicatorsWithoutReviews = readFile(getFileFromPath(this, "shopWithoutReviews.json"));
        try {
            _testShopTrustMarkApi = Shop.createFromTrustmarkInformationApi(jsonDataTrustmark);
            _testShopWithReviewsQualityIndicators = Shop.createFromQualityIndicatorsApiResponse(jsonDataQualityIndicatorsWithReviews);
            _testShopWithoutReviewsQualityIndicators = Shop.createFromQualityIndicatorsApiResponse(jsonDataQualityIndicatorsWithoutReviews);
        } catch (Exception e) {
            throw new AssertionError(e.getMessage());
        }
    }

    public static String readFile(String filename) {
        String result = "";
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();
            while (line != null) {
                sb.append(line);
                line = br.readLine();
            }
            result = sb.toString();
        } catch(Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    @Test
    public void testValidISO2Language() {
        if (!_testShopTrustMarkApi.getLanguageISO2().equals("de")) {
            throw new AssertionError("ISO3 Language of the shop should be german");
        }
    }


    @Test
    public void testValidTsId() {
        if (!Validator.validateTsId(_testShopTrustMarkApi.getTsId())) {
            throw new AssertionError("TSID is invalid");
        }
    }

    @Test
    public void testValidTargetMarket() {
        if (!_testShopTrustMarkApi.getTargetMarketISO3().equals("CHE")) {
            throw new AssertionError("Target market of the shop should be switzerland CHE, found "+ _testShopTrustMarkApi.getTargetMarketISO3()+" instead");
        }
    }

    @Test
    public void testTrustMarkisValid() {
        if (!_testShopTrustMarkApi.getTrustMark().getStatus().equals("VALID")) {
            throw new AssertionError("Trustmark status should be valid");
        }
    }

    @Test
    public void testTrustMarkvalidTo() {
        if (!(_testShopTrustMarkApi.getTrustMark().getValidTo() instanceof Date)) {
            throw new AssertionError("Valid To should be instance of Date");
        }
    }

    @Test
    public void testTrustMarkvalidFrom() {
        if (!(_testShopTrustMarkApi.getTrustMark().getValidFrom() instanceof Date)) {
            throw new AssertionError("Valid To should be instance of Date");
        }
    }

    @Test
    public void testShopHasQualityIndicators() {
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator() instanceof ReviewIndicator)) {
            throw new AssertionError("Review Indicators should be instance of ReviewIndicator");
        }
    }

    @Test
    public void testShopWithReviewsOverallMark(){
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() >= 1)) {
            throw new AssertionError("OverallMark of Shop with Reviews should be greater or equal to 1");
        }
    }

    @Test
    public void testShopWithoutReviewsOverallMark(){
        if (!(_testShopWithoutReviewsQualityIndicators.getReviewIndicator().getOverallMark() == 0)) {
            throw new AssertionError("OverallMark of Shop without Reviews should be zero");
        }
    }

    @Test
    public void testShopWithReviewsActiveReviewCount(){
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator().getActiveReviewCount() > 0)) {
            throw new AssertionError("Active Review Count of Shop with Reviews should be bigger than zero");
        }
    }

    @Test
    public void testShopWithoutReviewsActiveReviewCount(){
        if (!(_testShopWithoutReviewsQualityIndicators.getReviewIndicator().getActiveReviewCount() == 0)) {
            throw new AssertionError("Active Review Count of Shop with Reviews should be zero");
        }
    }

    @Test
    public void testReviewIndicatorNumStars(){
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator().getNumStars() == 5)) {
            throw new AssertionError("Active Review Count of Shop with Reviews should be bigger than zero");
        }
    }

    @Test
    public void testReviewIndicatorMaxRating(){
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator().getMaxRating() == 5)) {
            throw new AssertionError("Active Review Count of Shop with Reviews should be bigger than zero");
        }
    }

    @Test
    public void testShopReviewsCountedSince(){
        if (!(_testShopWithReviewsQualityIndicators.getReviewIndicator().getReviewsCountedSince() instanceof Date)) {
            throw new AssertionError("Reviews Counter since should be instance of Date");
        }

        if (!(_testShopWithoutReviewsQualityIndicators.getReviewIndicator().getReviewsCountedSince() instanceof Date)) {
            throw new AssertionError("Reviews Counter since should be instance of Date");
        }
    }

    @Test
    public void testShopWithReviewsHasOverallMarkDescription() {
        String overAllMarkDescr = _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMarkDescription();
        Float overAllMark = _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark();

        if (overAllMark > 1 && overAllMark < 1.5) {
            if (!(overAllMarkDescr.equals("MANGELHAFT"))) {
                throw new AssertionError("Overall Mark Description should be MANGELHAFT. Found "+ _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() + " instead");
            }
        } else if (overAllMark >= 1.5 && overAllMark < 2.5) {
            if (!(overAllMarkDescr.equals("AUSREICHEND"))) {
                throw new AssertionError("Overall Mark Description should be AUSREICHEND. Found "+ _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() + " instead");
            }
        } else if (overAllMark >= 2.5 && overAllMark < 3.5) {
            if (!(overAllMarkDescr.equals("BEFRIEDIGEND"))) {
                throw new AssertionError("Overall Mark Description should be BEFRIEDIGEND. Found "+ _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() + " instead");
            }
        } else if (overAllMark >= 3.5 && overAllMark < 4.5) {
            if (!(overAllMarkDescr.equals("GUT"))) {
                throw new AssertionError("Overall Mark Description should be GUT. Found "+ _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() + " instead");
            }
        } else if (overAllMark >= 4.5) {
            if (!(overAllMarkDescr.equals("SEHR GUT"))) {
                throw new AssertionError("Overall Mark Description should be SEHR GUT. Found "+ _testShopWithReviewsQualityIndicators.getReviewIndicator().getOverallMark() + " instead");
            }
        }
    }

    private static String getFileFromPath(Object obj, String fileName) {
        ClassLoader classLoader = obj.getClass().getClassLoader();
        URL resource = classLoader.getResource(fileName);
        return resource.getPath();
    }
}
