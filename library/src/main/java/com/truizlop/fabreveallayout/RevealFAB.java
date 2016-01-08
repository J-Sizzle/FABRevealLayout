package com.truizlop.fabreveallayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.design.widget.FloatingActionButton;
import android.util.AttributeSet;

public class RevealFAB extends FloatingActionButton {

    protected int revealedLayoutId;

    public RevealFAB(Context context) {
        this(context, null);
    }

    public RevealFAB(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init(context, attrs);
    }

    public RevealFAB(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RevealFAB);
        revealedLayoutId = a.getResourceId(R.styleable.RevealFAB_reveal_layout, -1);
        a.recycle();
    }

    public int getRevealedLayoutId(){
       return revealedLayoutId;
    }
}
