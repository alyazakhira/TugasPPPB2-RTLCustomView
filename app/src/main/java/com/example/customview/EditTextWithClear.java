package com.example.customview;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatEditText;
import androidx.core.content.res.ResourcesCompat;

public class EditTextWithClear extends AppCompatEditText {
    Drawable mClearButtonImage;

    private void init(){
        mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
        addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                // ketika ada perubahan teks, show button
                showClearButton();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                // jika pada posisi yg dimaksud tidak null
                if (getCompoundDrawablesRelative()[2] != null) {
                    // ambil posisi,
                    // dengan cara lebar total kelas ini (edit text) dikurangi padding end dikurangi lebar button
                    float clearButtonStartPosition = (getWidth()-getPaddingEnd()-mClearButtonImage.getIntrinsicWidth());
                    float clearButtonEndPosition = mClearButtonImage.getIntrinsicWidth() + getPaddingStart();
                    boolean isButtonClicked = false;

                    // kondisional ketika touch mengenai button, apakah rtl atau ltr
                    if (getLayoutDirection() == LAYOUT_DIRECTION_RTL) {
                        if (motionEvent.getX() < clearButtonEndPosition) {
                            isButtonClicked = true;
                        }
                    } else {
                        if (motionEvent.getX() > clearButtonStartPosition) {
                            isButtonClicked = true;
                        }
                    }

                    // aksi ketika ada klik, ubah warna button
                    if (isButtonClicked) {
                        if (motionEvent.getAction() == MotionEvent.ACTION_DOWN) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_black_24dp, null);
                            showClearButton();
                        } if (motionEvent.getAction() == MotionEvent.ACTION_UP) {
                            mClearButtonImage = ResourcesCompat.getDrawable(getResources(), R.drawable.ic_clear_opaque_24dp, null);
                            getText().clear();
                            hideClearButton();
                            return true;
                        } else {
                            return false;
                        }
                    }
                }

                return false;
            }
        });
    }

    public EditTextWithClear(@NonNull Context context) {
        super(context);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public EditTextWithClear(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    // function untuk show button
    private void showClearButton(){
        // menaruh clear button di bagian end
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, mClearButtonImage, null);
    }

    // function untuk hide button
    private void hideClearButton(){
        setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, null, null);
    }
}
