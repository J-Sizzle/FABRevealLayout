package com.truizlop.fabreveallayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RevealFAB extends RelativeLayout {

    protected int revealedLayoutId;
    protected Drawable icon;
    protected boolean useDefaultClick;
    private boolean isAnimatedOut;

    public ImageView ivIcon;

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

    protected void init(Context context, AttributeSet attrs) {

        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.reveal_fab, this, true);

        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RevealFAB);
        revealedLayoutId = a.getResourceId(R.styleable.RevealFAB_reveal_layout, -1);
        icon = a.getDrawable(R.styleable.RevealFAB_fab_icon);
        useDefaultClick = a.getBoolean(R.styleable.RevealFAB_default_click, true);

        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        setBackground(ContextCompat.getDrawable(context, R.drawable.fab_selector));
        ivIcon.setImageDrawable(icon);
        a.recycle();

    }

    @Override protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(getResources().getDimensionPixelSize(R.dimen.fab_size_normal), getResources().getDimensionPixelSize(R.dimen.fab_size_normal));
    }

    public int getRevealedLayoutId() {
        return revealedLayoutId;
    }

    public void toggleAnimated() {
        this.isAnimatedOut = !isAnimatedOut;
    }

    public boolean getIsAnimated() {
        return isAnimatedOut;
    }

    public boolean isUseDefaultClick() {
        return useDefaultClick;
    }
}
