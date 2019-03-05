package vegh.balint.hotelreviewtest.utils;

import android.view.View;
import android.widget.RatingBar;
import android.widget.TextView;

/**
 * Custom validator for RatingBar
 */
public class RatingValidator implements RatingBar.OnRatingBarChangeListener {

    private final RatingBar ratingBar;
    private final TextView tvError;

    public RatingValidator(RatingBar ratingBar, TextView tvError) {
        this.ratingBar = ratingBar;
        this.tvError = tvError;
    }

    @Override
    public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
        validateRating(ratingBar, tvError);
    }

    public static boolean validateRating(RatingBar ratingBar, TextView tvError){
        boolean valid = false;
        int rating = (int) (ratingBar.getRating());
        if(rating > 0 && rating < 6){
            valid = true;
            tvError.setVisibility(View.GONE);
        }else{
            tvError.setVisibility(View.VISIBLE);
        }
        return valid;
    }
}
