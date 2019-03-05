package vegh.balint.hotelreviewtest.network;

import android.content.Context;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import vegh.balint.hotelreviewtest.Config;
import vegh.balint.hotelreviewtest.interfaces.IReviewResponse;
import vegh.balint.hotelreviewtest.interfaces.ReviewUploadService;
import vegh.balint.hotelreviewtest.models.Review;
import vegh.balint.hotelreviewtest.utils.ImageHelper;

/**
 * Handle the review calls with retrofit
 */
public class ReviewHandler {

    private static final String TAG = "ReviewHandler";

    private Review review;
    private Context context;
    private IReviewResponse iReviewResponse;

    public ReviewHandler(Review review, Context context, IReviewResponse iReviewResponse) {
        this.review = review;
        this.context = context;
        this.iReviewResponse = iReviewResponse;
    }

    /**
     * send the review with multipart form data
     */
    public void sendReview() {
        Retrofit.Builder builder = new Retrofit.Builder()
                .baseUrl(Config.SERVER_URL)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create());

        Retrofit retrofit = builder.build();

        //create the imageParts array for the images
        MultipartBody.Part[] imageParts = new MultipartBody.Part[review.getImages().size()];

        for (int i = 0; i < review.getImages().size(); i++) {
            imageParts[i] = prepareFilePart("" + i, review.getImages().get(i));
        }

        ReviewUploadService reviewUploadService = retrofit.create(ReviewUploadService.class);

        Call<String> call = reviewUploadService.uploadReview(
                review.getRating(),
                review.getHotel_id(),
                review.getReview(),
                review.getLongDate(),
                review.getDaily_rate(),
                review.getCurrency(),
                imageParts
        );

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                Log.d(TAG, "onResponse: response" + response.body());
                JSONObject jsonResponse = null;
                try {
                     jsonResponse = new JSONObject(response.body());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                if(jsonResponse != null && !jsonResponse.has("error")){
                    iReviewResponse.sendResponse(false);
                }else{
                    iReviewResponse.sendResponse(true);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Log.d(TAG, "onFailure: failure" + t.getMessage());
                iReviewResponse.sendResponse(true);
            }
        });
    }

    /**
     * create MultipartBody from files
     */
    private MultipartBody.Part prepareFilePart(String partName, String filePath) {

        File file = new File(filePath);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(ImageHelper.getMimeType(filePath)),
                        file
                );

        return MultipartBody.Part.createFormData(partName, file.getName(), requestFile);
    }

}
