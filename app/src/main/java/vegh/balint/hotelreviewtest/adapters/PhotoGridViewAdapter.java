package vegh.balint.hotelreviewtest.adapters;

import android.content.Context;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import com.bumptech.glide.Glide;

import java.io.File;
import java.util.ArrayList;

import vegh.balint.hotelreviewtest.R;
import vegh.balint.hotelreviewtest.interfaces.IPhotoAdd;
import vegh.balint.hotelreviewtest.utils.ImageHelper;

/**
 * Custom adapter to create the custom gridview with add button and images
 */
public class PhotoGridViewAdapter extends BaseAdapter {

    private static final String TAG = "PhotoGridViewAdapter";

    private ArrayList<String> images;
    private Context context;
    private LayoutInflater layoutInflater;
    private IPhotoAdd iPhotoAdd;

    public PhotoGridViewAdapter(Context context, IPhotoAdd iPhotoAdd, ArrayList<String> images) {
        this.context = context;
        this.layoutInflater = LayoutInflater.from(context);
        this.images = images;
        this.iPhotoAdd = iPhotoAdd;
    }

    @Override
    public int getCount() {
        return images.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, final ViewGroup parent) {

        if (position == 0) {
            ImageButton imageButton;

            convertView = layoutInflater.inflate(R.layout.grid_button_item, parent, false);

            imageButton = convertView.findViewById(R.id.grid_button);

            imageButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (ImageHelper.hasPermissionToReadImages(context)) {
                        iPhotoAdd.selectImage();
                    } else {
                        iPhotoAdd.requestPermission();
                    }
                }
            });
        } else {

            ImageView picture;

            convertView = layoutInflater.inflate(R.layout.grid_photo_item, parent, false);

            picture = convertView.findViewById(R.id.grid_imageview);

            File imgFile = new File(images.get(position));

            if (imgFile.exists()) {

                Glide.with(parent.getContext())
                        .load(Uri.fromFile(imgFile))
                        .into(picture);
            }
        }
        return convertView;

    }
}
