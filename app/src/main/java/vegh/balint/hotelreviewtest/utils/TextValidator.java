package vegh.balint.hotelreviewtest.utils;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.widget.EditText;

import vegh.balint.hotelreviewtest.R;

/**
 * Custom validator for EditText
 */
public class TextValidator implements TextWatcher {
    private final EditText editText;
    private final String mode;

    public static final String TEXT = "TEXT";
    public static final String INT = "INT";

    public TextValidator(EditText editText, String mode) {
        this.editText = editText;
        this.mode = mode;
    }

    @Override
    final public void afterTextChanged(Editable s) {
        String text = editText.getText().toString();
        validate(editText, text);
    }

    @Override
    final public void
    beforeTextChanged(CharSequence s, int start, int count, int after) {
        /* Needs to be implemented, but we are not using it. */
    }

    @Override
    final public void
    onTextChanged(CharSequence s, int start, int before, int count) {
        /* Needs to be implemented, but we are not using it. */
    }

    private void validate(EditText editText, String text){
        switch (this.mode){
            case TEXT:
                validateText(editText, text);
                break;
            case INT:
                validateInt(editText, text);
                break;
        }
    }

    public static boolean validateText(EditText editText, String text){
        boolean valid = false;
        if(TextUtils.isEmpty(text)){
            editText.setError(editText.getContext().getString(R.string.error_required));
        } else {
            valid = true;
            editText.setError(null);
        }
        return valid;
    }

    public static boolean validateInt(EditText editText, String text){
        boolean valid = false;
        if(TextUtils.isEmpty(text)){
            editText.setError(editText.getContext().getString(R.string.error_required));
        } else if(!checkInt(text)){
            editText.setError(editText.getContext().getString(R.string.error_int));
        } else {
            valid = true;
            editText.setError(null);
        }
        return valid;
    }

    public static boolean checkInt(String text){
        try {
            Integer.parseInt(text);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
