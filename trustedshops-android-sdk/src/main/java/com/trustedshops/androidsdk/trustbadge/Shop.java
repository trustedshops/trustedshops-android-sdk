package com.trustedshops.androidsdk.trustbadge;

import org.json.JSONObject;

import java.text.SimpleDateFormat;

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

    public static Shop createFromTrustmarkInformationApi(String jsonData) throws Exception {
            Shop _responseShop = new Shop();
            JSONObject Jobject = new JSONObject(jsonData);
            JSONObject responseJsonObject = Jobject.getJSONObject("response");
            JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
            JSONObject shopJsonObject = dataJsonObject.getJSONObject("shop");

            _responseShop.setLanguageISO2(shopJsonObject.getString("languageISO2"));
            _responseShop.setTargetMarketISO3(shopJsonObject.getString("targetMarketISO3"));
            _responseShop.setUrl(shopJsonObject.getString("url"));
            _responseShop.setTsId(shopJsonObject.getString("tsId"));
            _responseShop.setName(shopJsonObject.getString("name"));

            TrustMark _responseTrustMark = new TrustMark();

            if (shopJsonObject.has("trustMark")) {
                JSONObject trustMarkJsonObject = shopJsonObject.getJSONObject("trustMark");

                if (trustMarkJsonObject.has("status")) {
                    _responseTrustMark.setStatus(trustMarkJsonObject.getString("status"));

                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
                    if (trustMarkJsonObject.has("validFrom")) {
                        _responseTrustMark.setValidFrom(format.parse(trustMarkJsonObject.getString("validFrom")));
                    }

                    if (trustMarkJsonObject.has("validTo")) {
                        _responseTrustMark.setValidTo(format.parse(trustMarkJsonObject.getString("validTo")));
                    }
                }
                _responseShop.setTrustMark(_responseTrustMark);
            }

            return _responseShop;
    }


    public static Shop createFromQualityIndicatorsApiResponse(String jsonData) throws Exception {
        Shop _responseShop = new Shop();
        JSONObject Jobject = new JSONObject(jsonData);
        JSONObject responseJsonObject = Jobject.getJSONObject("response");
        JSONObject dataJsonObject = responseJsonObject.getJSONObject("data");
        JSONObject shopJsonObject = dataJsonObject.getJSONObject("shop");

        _responseShop.setLanguageISO2(shopJsonObject.getString("languageISO2"));
        _responseShop.setTargetMarketISO3(shopJsonObject.getString("targetMarketISO3"));
        _responseShop.setUrl(shopJsonObject.getString("url"));
        _responseShop.setTsId(shopJsonObject.getString("tsId"));
        _responseShop.setName(shopJsonObject.getString("name"));

        ReviewIndicator _responseReviewIndicator = new ReviewIndicator();

        if (shopJsonObject.has("qualityIndicators")) {
            JSONObject qualityIndicatorsObject = shopJsonObject.getJSONObject("qualityIndicators");
            if (qualityIndicatorsObject.has("reviewIndicator")) {
                JSONObject reviewIndicatorObject = qualityIndicatorsObject.getJSONObject("reviewIndicator");
                _responseReviewIndicator.setActiveReviewCount(reviewIndicatorObject.getInt("activeReviewCount"));

                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                if (reviewIndicatorObject.has("reviewsCountedSince")) {
                    _responseReviewIndicator.setReviewsScountedSince(format.parse(reviewIndicatorObject.getString("reviewsCountedSince")));
                }

                if (reviewIndicatorObject.has("totalReviewCount")) {
                    _responseReviewIndicator.setTotalReviewCount(reviewIndicatorObject.getInt("totalReviewCount"));
                }

                if (reviewIndicatorObject.has("overallMark")) {
                    _responseReviewIndicator.setOverallMark((float) reviewIndicatorObject.getDouble("overallMark"));
                }

                if (reviewIndicatorObject.has("overallMarkDescription")) {
                    _responseReviewIndicator.setOverallMarkDescription(reviewIndicatorObject.getString("overallMarkDescription"));
                }

                _responseShop.setReviewIndicator(_responseReviewIndicator);
            }
        }

        return _responseShop;


    }

}
