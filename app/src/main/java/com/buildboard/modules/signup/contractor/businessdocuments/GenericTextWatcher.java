package com.buildboard.modules.signup.contractor.businessdocuments;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;

import com.buildboard.R;
import com.buildboard.modules.signup.contractor.businessdocuments.interfaces.ITextWatcherCallback;

public class GenericTextWatcher implements TextWatcher {

    private View view;
    private ITextWatcherCallback iTextWatcherCallback;

    public GenericTextWatcher(View view, ITextWatcherCallback iTextWatcherCallback) {
        this.view = view;
        this.iTextWatcherCallback = iTextWatcherCallback;
    }

    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
    }

    public void afterTextChanged(Editable editable) {
        String text = editable.toString();
        switch (view.getId()) {
            case R.id.edit_liability_insurance:
                iTextWatcherCallback.getValue(text);
                break;
            case R.id.edit_insurance_provider:
                iTextWatcherCallback.getValue(text);
                break;
            case R.id.edit_insurance_amount:
                iTextWatcherCallback.getValue(text);
                break;
        }
    }
}
