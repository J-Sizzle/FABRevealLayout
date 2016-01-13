package com.truizlop.fabreveallayout;

import android.content.Context;
import android.content.res.TypedArray;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class RevealFAB extends RelativeLayout {

    protected int revealedLayoutId;
    private int icon;
    private boolean isAnimatedOut;
    private boolean useDefaultClick;

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
        icon = a.getResourceId(R.styleable.RevealFAB_fab_icon, android.R.drawable.ic_dialog_alert);
        useDefaultClick = a.getBoolean(R.styleable.RevealFAB_default_click, false);

        a.recycle();
        ivIcon = (ImageView) findViewById(R.id.ivIcon);
        setBackground(ContextCompat.getDrawable(context, R.drawable.fab_selector));
        ivIcon.setImageResource(icon);
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
