package vegh.balint.hotelreviewtest.ui.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import vegh.balint.hotelreviewtest.R;

/**
 * DialogFragment to show error messages
 */
public class ErrorDialogFragment extends DialogFragment {

    public static final String TAG = "ErrorDialogFragment";

    @BindView(R.id.tv_error) TextView tvError;
    @BindView(R.id.btn_action_ok) TextView btnActionOk;
    private Unbinder unbinder;

    public ErrorDialogFragment(){}

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_fragment_error, container);
        unbinder = ButterKnife.bind(this, view);
        String error = getArguments().getString("error", getString(R.string.error_default));
        tvError.setText(error);
        return view;
    }

    @OnClick(R.id.btn_action_ok)
    public void dismiss(){
        getDialog().dismiss();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }
}
