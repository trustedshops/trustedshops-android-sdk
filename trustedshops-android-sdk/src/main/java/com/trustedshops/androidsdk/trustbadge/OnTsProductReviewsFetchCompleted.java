package com.trustedshops.androidsdk.trustbadge;

import android.os.Message;

public interface OnTsProductReviewsFetchCompleted {
    void onProductReviewsFetchCompleted(Product productObject);
    void onProductReviewsFetchFailed(Message errorMessage);
}
