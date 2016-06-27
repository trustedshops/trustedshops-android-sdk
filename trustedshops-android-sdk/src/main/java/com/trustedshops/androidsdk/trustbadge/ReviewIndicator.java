package com.trustedshops.androidsdk.trustbadge;


import java.util.Date;

public class ReviewIndicator extends QualityIndicator {

    private QualityIndicatorType type = QualityIndicatorType.REVIEW_INDICATOR;
    protected Integer activeReviewCount;
    protected Integer totalReviewCount;
    protected Date reviewsScountedSince;
    protected Float overallMark;
    protected String overallMarkDescription;

    public Integer getActiveReviewCount() {
        return activeReviewCount;
    }

    public void setActiveReviewCount(Integer activeReviewCount) {
        this.activeReviewCount = activeReviewCount;
    }

    public Integer getTotalReviewCount() {
        return totalReviewCount;
    }

    public void setTotalReviewCount(Integer totalReviewCount) {
        this.totalReviewCount = totalReviewCount;
    }

    public Date getReviewsScountedSince() {
        return reviewsScountedSince;
    }

    public void setReviewsScountedSince(Date reviewsScountedSince) {
        this.reviewsScountedSince = reviewsScountedSince;
    }

    public Float getOverallMark() {
        return overallMark;
    }

    public void setOverallMark(Float overallMark) {
        this.overallMark = overallMark;
    }

    public String getOverallMarkDescription() {
        return overallMarkDescription;
    }

    public void setOverallMarkDescription(String overallMarkDescription) {
        this.overallMarkDescription = overallMarkDescription;
    }



    @Override
    public QualityIndicatorType getType() {
        return this.type;
    }

}
