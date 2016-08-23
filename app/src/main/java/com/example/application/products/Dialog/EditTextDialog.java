package com.example.application.products.Dialog;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.application.products.ProductList;
import com.example.application.products.R;

public class EditTextDialog extends DialogFragment {
    private DialogInterface.OnClickListener okClickListener = null;
    private DialogInterface.OnClickListener cancelClickListener = null;
    private EditText editText;
    private View view;

    @Override
    public Dialog onCreateDialog(Bundle safedInstanceState) {
        view = getActivity().getLayoutInflater().inflate(R.layout.login, null);

        Button btn = (Button)view.findViewById(R.id.button_login);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ((ProductList)getActivity()).onDialogLoginClick();
                dismiss();
            }
        });

        return new AlertDialog.Builder(getActivity())
                .setTitle("ログイン")
                .setView(view)
                .show();
    }
}
