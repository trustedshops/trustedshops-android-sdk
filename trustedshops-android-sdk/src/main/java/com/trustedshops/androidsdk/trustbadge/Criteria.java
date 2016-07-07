package com.trustedshops.androidsdk.trustbadge;

public class Criteria {
    public int getMark() {
        return mark;
    }

    public void setMark(int mark) {
        this.mark = mark;
    }

    public Criteriatype getType() {
        return type;
    }

    public void setType(Criteriatype type) {
        this.type = type;
    }

    protected int mark;
    protected Criteriatype type;
}
