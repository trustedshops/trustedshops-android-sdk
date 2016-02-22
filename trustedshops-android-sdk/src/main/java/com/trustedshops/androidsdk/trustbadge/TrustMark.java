package com.trustedshops.androidsdk.trustbadge;

import java.util.Date;

public class TrustMark {
    protected String _status;
    protected Date _validFrom;
    protected Date _validTo;

    public Date get_validTo() {
        return _validTo;
    }

    public void set_validTo(Date _validTo) {
        this._validTo = _validTo;
    }

    public String get_status() {
        return _status;
    }

    public void set_status(String _status) {
        this._status = _status;
    }

    public Date get_validFrom() {
        return _validFrom;
    }

    public void set_validFrom(Date _validFrom) {
        this._validFrom = _validFrom;
    }


}
