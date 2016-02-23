package com.trustedshops.androidsdk.trustbadge;

import java.util.Date;

public class TrustMark {
    protected String _status;
    protected Date _validFrom;
    protected Date _validTo;

    public Date getValidTo() {
        return _validTo;
    }

    public void setValidTo(Date _validTo) {
        this._validTo = _validTo;
    }

    public String getStatus() {
        return _status;
    }

    public void setStatus(String _status) {
        this._status = _status;
    }

    public Date getValidFrom() {
        return _validFrom;
    }

    public void setValidFrom(Date _validFrom) {
        this._validFrom = _validFrom;
    }
}