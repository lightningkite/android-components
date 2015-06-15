package com.lightningkite.androidcomponents.example.bigview;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.lightningkite.androidcomponents.R;
import com.lightningkite.androidcomponents.bigview.BigView;
import com.lightningkite.androidcomponents.bigview.BigViewContainer;
import com.lightningkite.androidcomponents.form.EntrySelectBlock;
import com.lightningkite.androidcomponents.form.FormView;

import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;

/**
 * An example BigView demonstrating the FormView, which automatically creates a form.
 * Created by jivie on 6/2/15.
 */
public class FormBigView extends BigView {

    @InjectView(R.id.form_form)
    FormView mFormView;

    public FormBigView(BigViewContainer container, Bundle arguments, int id) {
        super(container, arguments, id);

        inflate(mActivity, R.layout.bigview_form_example, this);
        ButterKnife.inject(this);

        mFormView
                .addTextEmail("email", "Email", "Enter your email", false)
                .addTextPassword("password", "Password", "Enter your password", 8)
                .addText("fav_color", "Favorite Color", "Enter your favorite color", false)
                .addTextInteger("lucky_num", "Lucky Number", "Enter your lucky number", false)
                .addTextDecimal("miles", "Miles", "Miles traveled (optional)", true)
                .addToggle("roundtrip", "Round Trip", true)
                .addSelect("testSelect", "Test Select", new EntrySelectBlock.EntrySelectListener() {
                    @Override
                    public void onSelect(EntrySelectBlock v) {
                        AlertDialog.Builder builder = makeTextEntryDialog(v);
                        builder.show();
                    }
                }, true)
                .addSpinner("fav_fruit",
                        "Favorite Fruit",
                        new ArrayAdapter<>(
                                getContext(),
                                R.layout.row_text,
                                new String[]{
                                        "Apple",
                                        "Pear",
                                        "Orange",
                                        "Banana",
                                        "Grapes",
                                        "Watermelon"
                                }
                        )
                )
                .start();
        mFormView.focusOnFirst();
    }

    private AlertDialog.Builder makeTextEntryDialog(final EntrySelectBlock block) {

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Title");

        // Set up the input
        final EditText input = new EditText(getContext());
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                block.setData(input.getText().toString());
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder;
    }

    @OnClick(R.id.form_validate)
    void onValidateClicked() {
        mFormView.validate();
    }
}
