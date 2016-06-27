package com.trustedshops.androidsdk.trustbadge;

public class Shop {
    protected String _tsId;
    protected String _url;
    protected String _name;
    protected String _languageISO2 = "en";
    protected String _targetMarketISO3 = "EUO";
    protected TrustMark _trustMark;
    protected ReviewIndicator _reviewIndicator;

    public String getTsId() {
        return _tsId;
    }
    public void setTsId(String _tsId) {
        this._tsId = _tsId;
    }
    public String getUrl() {
        return _url;
    }
    public void setUrl(String _url) {
        this._url = _url;
    }
    public String getName() {
        return _name;
    }
    public void setName(String _name) {
        this._name = _name;
    }
    public String getLanguageISO2() {
        return _languageISO2;
    }
    public void setLanguageISO2(String _languageISO2) {
        this._languageISO2 = _languageISO2;
    }
    public String getTargetMarketISO3() {
        return _targetMarketISO3;
    }
    public void setTargetMarketISO3(String _targetMarketISO3) {
        this._targetMarketISO3 = _targetMarketISO3;
    }
    public TrustMark getTrustMark() {
        return _trustMark;

    }
    public void setTrustMark(TrustMark _trustMark) {
        this._trustMark = _trustMark;

    }

    public ReviewIndicator getReviewIndicator() {
        return _reviewIndicator;
    }

    public void setReviewIndicator(ReviewIndicator reviewIndicator) {
        this._reviewIndicator =reviewIndicator;
    }

    public String getShopProfileUrl() {
        return UrlManager.getShopProfileUrl(this);
    }

}
