package com.trustedshops.trustbadgeexample;

import android.content.Context;
import androidx.appcompat.widget.AppCompatRatingBar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.trustedshops.androidsdk.trustbadge.ProductReview;

import java.util.ArrayList;

public class ProductReviewsAdapter extends ArrayAdapter<ProductReview> {
    private Context context;

    private static class ViewHolder {
        TextView productReviewChangeDate;
        TextView productReviewComment;
        AppCompatRatingBar productReviewCommentRatingBar;

    }

    public ProductReviewsAdapter(Context context, ArrayList<ProductReview> users) {
        super(context, R.layout.item_productreview, users);
        this.context = context;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        ProductReview review = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        ViewHolder viewHolder; // view lookup cache stored in tag
        if (convertView == null) {
            viewHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_productreview, parent, false);
            viewHolder.productReviewChangeDate = (TextView) convertView.findViewById(R.id.productReviewChangeDate);
            viewHolder.productReviewComment = (TextView) convertView.findViewById(R.id.productReviewComment);
            viewHolder.productReviewCommentRatingBar = (AppCompatRatingBar) convertView.findViewById(R.id.productReviewCommentRatingBar);

            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Populate the data into the template view using the data object
        java.text.DateFormat dateFormat = android.text.format.DateFormat.getDateFormat(this.context);

        String changeDateFormated = dateFormat.format(review.getCreationDate());
        viewHolder.productReviewChangeDate.setText(changeDateFormated);
        viewHolder.productReviewComment.setText(review.getComment());
        viewHolder.productReviewCommentRatingBar.setRating(review.getMark());
        // Return the completed view to render on screen
        return convertView;
    }


}
