package vegh.balint.hotelreviewtest.ui.dialogs;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import cdflynn.android.library.checkview.CheckView;
import vegh.balint.hotelreviewtest.R;

/**
 * DialogFragment to show the success message
 */
public class SuccessDialogFragment extends DialogFragment {

    public static final String TAG = "LoadingDialogFragment";

    @BindView(R.id.check) CheckView checkView;
    @BindView(R.id.btn_action_ok) Button btnActionOk;
    private Unbinder unbinder;

    public SuccessDialogFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_success, container);
        unbinder = ButterKnife.bind(this, view);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setCancelable(false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        checkView.check();
    }

    @OnClick(R.id.btn_action_ok)
    public void recreate(){
        getActivity().finish();
        Intent i = getActivity().getBaseContext().getPackageManager()
                .getLaunchIntentForPackage(getActivity().getBaseContext().getPackageName());
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
