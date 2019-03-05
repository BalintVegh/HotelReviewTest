package vegh.balint.hotelreviewtest.interfaces;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * Define the review upload api
 */
public interface ReviewUploadService {

    @Multipart
    @POST("send-review")
    Call<String> uploadReview(
            @Part("rating") Integer rating,
            @Part("hotel_id") Integer hotel_id,
            @Part("review") String review,
            @Part("event_date") Long event_date,
            @Part("daily_rate") Integer daily_rate,
            @Part("currency") String currency,
            @Part MultipartBody.Part[] images
    );

}
