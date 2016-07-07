package com.trustedshops.androidsdk.trustbadge;

import android.os.Message;

public interface OnTsCustomerReviewsFetchCompleted {
    void onCustomerReviewsFetchCompleted(Shop shopObject);
    void onCustomerReviewsFetchFailed(Message errorMessage);
}
