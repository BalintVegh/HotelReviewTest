package vegh.balint.hotelreviewtest.models;

import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.Date;
import java.util.List;

import vegh.balint.hotelreviewtest.R;

public class Review {

    private static final String hotelImgUrl = "https://images.unsplash.com/reserve/8T8J12VQxyqCiQFGa2ct_bahamas-atlantis.jpg?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&auto=format&fit=crop&w=2550&q=80";

    public static final String[] currencies = {"EUR","USD"};

    public static final int HOTEL_ID = 110;

    private int rating;
    private int hotel_id;
    private String review;
    private Date event_date;
    private int daily_rate;
    private String currency;
    private List<String> images;

    public Review(int rating, int hotel_id, String review, Date event_date, int daily_rate, int currencyPos, List<String> images) {
        this.rating = rating;
        this.hotel_id = hotel_id;
        this.review = review;
        this.event_date = event_date;
        this.daily_rate = daily_rate;
        this.currency = currencies[currencyPos];
        this.images = images;
    }

    public int getRating() {
        return rating;
    }

    public int getHotel_id() {
        return hotel_id;
    }

    public String getReview() {
        return review;
    }

    public Date getEvent_date() {
        return event_date;
    }

    public int getDaily_rate() {
        return daily_rate;
    }

    public String getCurrency() {
        return currency;
    }

    public List<String> getImages() {
        return images;
    }

    public Long getLongDate(){
        return event_date.getTime() / 1000;
    }

    public static void loadHotelImage(ImageView ivHotel){
        /* create placeholder */
        RequestOptions requestOptions = new RequestOptions()
                .placeholder(R.drawable.hotel_placeholder);

        Glide.with(ivHotel.getContext())
                .load(hotelImgUrl)
                .apply(requestOptions)
                .into(ivHotel);
    }

    @Override
    public String toString() {
        return "Review{" +
                "rating=" + rating +
                ", hotel_id=" + hotel_id +
                ", review='" + review + '\'' +
                ", event_date=" + event_date +
                ", daily_rate=" + daily_rate +
                ", currency='" + currency + '\'' +
                ", images=" + images +
                '}';
    }
}
