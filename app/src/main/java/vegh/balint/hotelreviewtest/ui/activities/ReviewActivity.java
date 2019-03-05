package vegh.balint.hotelreviewtest.ui.activities;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.AppBarLayout;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import vegh.balint.hotelreviewtest.R;
import vegh.balint.hotelreviewtest.adapters.PhotoGridViewAdapter;
import vegh.balint.hotelreviewtest.network.ReviewHandler;
import vegh.balint.hotelreviewtest.interfaces.IPhotoAdd;
import vegh.balint.hotelreviewtest.interfaces.IReviewResponse;
import vegh.balint.hotelreviewtest.models.Review;
import vegh.balint.hotelreviewtest.network.ConnectionHelper;
import vegh.balint.hotelreviewtest.ui.dialogs.DatePickerFragment;
import vegh.balint.hotelreviewtest.ui.dialogs.ErrorDialogFragment;
import vegh.balint.hotelreviewtest.ui.dialogs.LoadingDialogFragment;
import vegh.balint.hotelreviewtest.ui.dialogs.SuccessDialogFragment;
import vegh.balint.hotelreviewtest.utils.AutoHeightGridView;
import vegh.balint.hotelreviewtest.utils.ImageHelper;
import vegh.balint.hotelreviewtest.utils.RatingValidator;
import vegh.balint.hotelreviewtest.utils.TextValidator;

public class ReviewActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, IPhotoAdd, IReviewResponse {

    private static final String TAG = "ReviewActivity";

    @BindView(R.id.appbar)
    AppBarLayout appBarLayout;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.hotel_image)
    ImageView ivHotel;
    @BindView(R.id.spinner_curreny)
    Spinner spinnerCurrency;
    @BindView(R.id.grid_photo)
    AutoHeightGridView gvPhoto;
    @BindView(R.id.et_date)
    EditText etDate;
    @BindView(R.id.et_review)
    EditText etReview;
    @BindView(R.id.et_paid)
    EditText etPaid;
    @BindView(R.id.rb_review)
    RatingBar rbReview;
    @BindView(R.id.tv_rating_error)
    TextView tvRatingError;

    private ArrayList<String> images = new ArrayList<>();

    private LoadingDialogFragment loadingDialogFragment;
    private PhotoGridViewAdapter photoGridViewAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_review);

        ButterKnife.bind(this);

        initCollapsingToolbar();

        Review.loadHotelImage(ivHotel);

        setStartDate();

        setSpinnerCurrency();

        setGridViewPhotos();

        etReview.addTextChangedListener(new TextValidator(etReview, TextValidator.TEXT));

        etPaid.addTextChangedListener(new TextValidator(etPaid, TextValidator.INT));

        rbReview.setOnRatingBarChangeListener(new RatingValidator(rbReview, tvRatingError));
    }

    /**
     * Initializing the toolbar
     */
    private void initCollapsingToolbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        appBarLayout.setExpanded(true);
        setTitle("");
    }

    /**
     * Set start date for event date input
     */
    private void setStartDate() {
        etDate.setText(DatePickerFragment.getCurrentDate());
    }

    /**
     * Show the datepicker dialog
     */
    @OnClick(R.id.et_date)
    public void showDatePicker() {
        DialogFragment datePicker = new DatePickerFragment();
        datePicker.show(getSupportFragmentManager(), DatePickerFragment.TAG);
    }

    /**
     * After the date set show it on the edittext
     */
    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.YEAR, year);
        c.set(Calendar.MONTH, month);
        c.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        String currentDateString = DateFormat.getDateInstance().format(c.getTime());

        etDate.setText(currentDateString);
    }

    /**
     * Set the currency spinner component
     */
    private void setSpinnerCurrency() {
        String[] currencies = getResources().getStringArray(R.array.currencies);
        ArrayAdapter<String> currencyAdapter = new ArrayAdapter<String>(this,
                R.layout.spinner_text, currencies);
        currencyAdapter.setDropDownViewResource(R.layout.simple_spinner_dropdown);
        spinnerCurrency.setAdapter(currencyAdapter);
    }

    /**
     * Init the adapter for Gridview
     * Handle orientation changes
     */
    private void setGridViewPhotos() {
        if(getLastCustomNonConfigurationInstance() != null){
            images = (ArrayList<String>) getLastCustomNonConfigurationInstance();   
        }else{
            images.add("button");   
        }

        photoGridViewAdapter = new PhotoGridViewAdapter(this, this, images);
        gvPhoto.setAdapter(photoGridViewAdapter);
    }

    /**
     * IPhotoAdd interface
     * Add permission to read images
     */
    @Override
    public void requestPermission() {
        ImageHelper.addPermissionToReadImages(this);
    }

    /**
     * IPhotoAdd interface
     * start the images selection
     */
    @Override
    public void selectImage() {
        ImageHelper.selectImages(this);
    }

    /**
     * Handle the image selection result
     * check if one or more image selected
     * notify the gridview adapter
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ImageHelper.SELECT_PICTURES_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            if (data.getClipData() != null) {
                int count = data.getClipData().getItemCount();
                for (int i = 0; i < count; i++) {
                    Uri imageUri = data.getClipData().getItemAt(i).getUri();
                    images.add(ImageHelper.getImageFilePath(imageUri, this));
                }
            } else if (data.getData() != null) {
                Uri imgUri = data.getData();
                images.add(ImageHelper.getImageFilePath(imgUri, this));
            }
            photoGridViewAdapter.notifyDataSetChanged();
        }
    }

    /**
     * If the read external storage permission granted start the image selection
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case ImageHelper.READ_EXTERNAL_STORAGE_REQUEST_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    selectImage();
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.review_activity_menu, menu);
        MenuItem menuItem = menu.findItem(R.id.action_done);
        menuItem.getActionView().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendReview();
            }
        });
        return super.onCreateOptionsMenu(menu);
    }

    /**
     * Start sending a review
     * 1. Check connection
     * 2. Validate inputs
     * 3. start the retrofit call
     */
    public void sendReview(){
        if (!ConnectionHelper.isOnline(this)) {
            showErrorDialog(getString(R.string.error_default));
        } else if(RatingValidator.validateRating(rbReview, tvRatingError)
                && TextValidator.validateText(etReview, etReview.getText().toString())
                && TextValidator.validateInt(etPaid, etPaid.getText().toString())) {

            showLoadingDialog();

            Date date = null;
            try {
                date = DateFormat.getDateInstance().parse(etDate.getText().toString());
            } catch (ParseException e) {
                e.printStackTrace();
            }

            List<String> reviewImages = new ArrayList<>(images);
            reviewImages.remove(0);

            Review review = new Review(
                    (int) rbReview.getRating(),
                    Review.HOTEL_ID,
                    etReview.getText().toString(),
                    date,
                    Integer.parseInt(etPaid.getText().toString()),
                    spinnerCurrency.getSelectedItemPosition(),
                    reviewImages
            );

            ReviewHandler reviewHandler = new ReviewHandler(review, this, this);
            reviewHandler.sendReview();
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    /**
     * Store the images list at orientation change
     */
    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return images;
    }

    /**
     * After the review sent
     * Stop loading and show error or success dialog
     */
    @Override
    public void sendResponse(boolean hasError) {
        stopLoading();
        if(hasError){
            showErrorDialog(getString(R.string.error_default));
        }else{
            showSuccessDialog();
        }
    }

    /**
     * ----------------------------
     * Show/Hide the dialogs
     * ----------------------------
     */
    private void showSuccessDialog() {
        SuccessDialogFragment successDialogFragment = new SuccessDialogFragment();
        successDialogFragment.show(getSupportFragmentManager(), SuccessDialogFragment.TAG);
    }

    public void showLoadingDialog() {
        loadingDialogFragment = new LoadingDialogFragment();
        loadingDialogFragment.show(getSupportFragmentManager(), LoadingDialogFragment.TAG);
    }

    public void stopLoading() {
        loadingDialogFragment.dismiss();
    }

    public void showErrorDialog(String error) {
        ErrorDialogFragment errorDialogFragment = new ErrorDialogFragment();
        Bundle args = new Bundle();
        args.putString("error", error);
        errorDialogFragment.setArguments(args);
        errorDialogFragment.show(getSupportFragmentManager(), ErrorDialogFragment.TAG);
    }
}
