package com.example.farid.newser.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

import com.example.farid.newser.R;

/**
 * Created by Farid on 8/4/2016.
 */
public class CustomTextView extends TextView {
    public CustomTextView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
        init(attrs);
    }

    public CustomTextView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
        init(attrs);

    }

    public CustomTextView(Context context)
    {
        super(context);
        init(null);
    }

    private void init(AttributeSet attrs)
    {
        if (attrs!=null)
        {
            TypedArray a = getContext().obtainStyledAttributes(attrs, R.styleable.CustomeFont);

            String fontName=a.getString(R.styleable.CustomeFont_Scratch);

            if (fontName!=null)
            {
                Typeface myTypeface = Typeface.createFromAsset(getContext().getAssets(), "fonts/" + fontName);
                setTypeface(myTypeface);
            }
            a.recycle();
        }
    }
}
