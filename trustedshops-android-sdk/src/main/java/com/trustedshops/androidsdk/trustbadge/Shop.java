package com.trustedshops.androidsdk.trustbadge;

public class Shop {
    protected String _tsId;
    protected String _url;
    protected String _name;
    protected String _languageISO2 = "en";
    protected String _targetMarketISO3 = "EUO";
    protected TrustMark _trustMark;

    public String get_tsId() {
        return _tsId;
    }
    public void set_tsId(String _tsId) {
        this._tsId = _tsId;
    }
    public String get_url() {
        return _url;
    }
    public void set_url(String _url) {
        this._url = _url;
    }
    public String get_name() {
        return _name;
    }
    public void set_name(String _name) {
        this._name = _name;
    }
    public String get_languageISO2() {
        return _languageISO2;
    }
    public void set_languageISO2(String _languageISO2) {
        this._languageISO2 = _languageISO2;
    }
    public String get_targetMarketISO3() {
        return _targetMarketISO3;
    }
    public void set_targetMarketISO3(String _targetMarketISO3) {
        this._targetMarketISO3 = _targetMarketISO3;
    }
    public TrustMark get_trustMark() {
        return _trustMark;
    }
    public void set_trustMark(TrustMark _trustMark) {
        this._trustMark = _trustMark;
    }


}
