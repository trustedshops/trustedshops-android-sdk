package com.trustedshops.androidsdk.trustbadge;


import java.util.ArrayList;
import java.util.Date;

public class ProductReview {
    protected Date creationDate;
    protected String comment;
    protected Integer mark;
    protected String UID;
    protected ArrayList<Criteria> criteria = new ArrayList<Criteria>();

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getMark() {
        return mark;
    }

    public void setMark(Integer mark) {
        this.mark = mark;
    }

    public String getUID() {
        return UID;
    }

    public void setUID(String UID) {
        this.UID = UID;
    }

    public ArrayList<Criteria> getCriteria() {
        return criteria;
    }

    public void setCriteria(ArrayList<Criteria> criteria) {
        this.criteria = criteria;
    }

    public void addCritera(Criteria criteria) {
        this.criteria.add(criteria);
    }
}
