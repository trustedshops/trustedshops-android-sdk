package com.trustedshops.androidsdk.trustbadge;


import java.util.Date;

public class ReviewIndicator extends QualityIndicator {
    private final int numStars = 5;
    private final float maxRating = 5.0f;
    private QualityIndicatorType type = QualityIndicatorType.REVIEW_INDICATOR;
    protected Integer activeReviewCount;
    protected Integer totalReviewCount;
    protected Date reviewsScountedSince;
    protected Float overallMark;
    protected String overallMarkDescription;

    /**
     * Get count of reviews collected in the last 12 Month
     * @return int reviewCount
     */
    public Integer getActiveReviewCount() {
        return activeReviewCount;
    }

    /**
     * Set count of reviews collected in last 12 Month
     * @param activeReviewCount
     */
    public void setActiveReviewCount(Integer activeReviewCount) {
        this.activeReviewCount = activeReviewCount;
    }

    /**
     * Get count of reviews collected since registration
     * @return totalReviewCount int
     */
    public Integer getTotalReviewCount() {
        return totalReviewCount;
    }

    /**
     * Set count of reviews collected since registration
     * @param totalReviewCount int
     */
    public void setTotalReviewCount(Integer totalReviewCount) {
        this.totalReviewCount = totalReviewCount;
    }

    /**
     * Get the date since when the reviews are counted
     * @return Date reviewsCountedSince
     */
    public Date getReviewsCountedSince() {
        return reviewsScountedSince;
    }

    public void setReviewsScountedSince(Date reviewsScountedSince) {
        this.reviewsScountedSince = reviewsScountedSince;
    }

    /**
     * Get the overall mark
     * @return Float overall mark
     */
    public Float getOverallMark() {
        return overallMark;
    }

    /**
     * Set overall mark
     * @param overallMark
     */
    public void setOverallMark(Float overallMark) {
        this.overallMark = overallMark;
    }

    /**
     * Get overall mark description in user phone locale language
     * @return overall mark translated in user phone language
     */
    public String getOverallMarkDescription() {
        return overallMarkDescription;
    }

    public void setOverallMarkDescription(String overallMarkDescription) {
        this.overallMarkDescription = overallMarkDescription;
    }

    /**
     * Get number of stars you should use
     * @return int number of Stars
     */
    public int getNumStars() {
        return this.numStars;
    }

    /**
     * Get maximal rating
     * @return Float maximal rating
     */
    public Float getMaxRating() {
        return this.maxRating;
    }

    @Override
    public QualityIndicatorType getType() {
        return this.type;
    }

}
