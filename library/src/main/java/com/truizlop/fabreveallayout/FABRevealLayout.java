/*
 * Copyright (C) 2015 Tomás Ruiz-López.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.truizlop.fabreveallayout;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.animation.FastOutSlowInInterpolator;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Interpolator;
import android.widget.RelativeLayout;

import java.util.List;

public class FABRevealLayout extends RelativeLayout {

    private static final int ANIMATION_DURATION = 500;
    private final Interpolator INTERPOLATOR = new FastOutSlowInInterpolator();

    private FloatingActionButton fab = null;
    private CircularExpandingView circularExpandingView = null;
    private OnRevealChangeListener onRevealChangeListener = null;
    private View secondView = null;
    private View mainView = null;

    private OnClickListener fabClicker = new OnClickListener() {
        @Override public void onClick(View v) {
            RevealFAB revealFAB = (RevealFAB) v;
            fab = revealFAB;
            secondView = findViewById(revealFAB.getRevealedLayoutId());
            revealSecondaryView();
        }
    };

    public FABRevealLayout(Context context) {
        this(context, null);
    }

    public FABRevealLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FABRevealLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override protected void onFinishInflate() {
        super.onFinishInflate();
        init();
    }

    private void init() {
        //inflate the secondary views
        int count = getChildCount();
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            if (view instanceof RevealFAB) {
                RevealFAB revealFAB = (RevealFAB) view;
                validateFAB(revealFAB);
                revealFAB.setOnClickListener(fabClicker);
            }
        }

        //create a main transparent view
        RelativeLayout relativeLayout = new RelativeLayout(getContext());
        RelativeLayout.LayoutParams rlp = new RelativeLayout.LayoutParams(
                LayoutParams.MATCH_PARENT,
                LayoutParams.MATCH_PARENT);
        relativeLayout.setBackgroundColor(getResources().getColor(android.R.color.transparent));
        relativeLayout.setLayoutParams(rlp);
        relativeLayout.setVisibility(View.VISIBLE);
        mainView = relativeLayout;
        addView(mainView, 2);

        addCircularRevealView();
    }

    private void validateFAB(RevealFAB fab) {
        if (fab.getRevealedLayoutId() == -1) {
            throw new IllegalArgumentException("RevealFAB needs to be associated with a layout");
        }
    }

    private void addCircularRevealView() {
        circularExpandingView = new CircularExpandingView(getContext());
        LayoutParams params = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
        params.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.corner_radius);
        params.bottomMargin = getContext().getResources().getDimensionPixelSize(R.dimen.corner_radius);
        params.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.corner_radius);
        params.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.corner_radius);
        circularExpandingView.setVisibility(View.GONE);
        addView(circularExpandingView, 3 ,params);
    }

    private boolean isShowingMainView() {
        return getMainView().getVisibility() == VISIBLE;
    }

    public void revealMainView() {
        if (!isShowingMainView()) {
            startHideAnimation();
        }
    }

    public void revealSecondaryView() {
        if (isShowingMainView()) {
            startRevealAnimation();
        }
    }

    public void setOnRevealChangeListener(OnRevealChangeListener onRevealChangeListener) {
        this.onRevealChangeListener = onRevealChangeListener;
    }

    private void startRevealAnimation() {
        View disappearingView = getMainView();

        ObjectAnimator fabAnimator = getFABAnimator();
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(disappearingView, "alpha", 1, 0);

        AnimatorSet set = new AnimatorSet();
        set.play(fabAnimator).with(alphaAnimator);
        setupAnimationParams(set);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab.setVisibility(GONE);
                prepareForReveal();
                expandCircle();
            }
        });

        set.start();
    }

    private void prepareForReveal() {
        circularExpandingView.getLayoutParams().height = getMainView().getHeight();
        circularExpandingView.setColor(fab.getBackgroundTintList() != null ?
                fab.getBackgroundTintList().getDefaultColor() :
                0xFF000000
        );
        circularExpandingView.setVisibility(VISIBLE);
    }

    private void setupAnimationParams(Animator animator) {
        animator.setInterpolator(INTERPOLATOR);
        animator.setDuration(ANIMATION_DURATION);
    }

    private CurvedAnimator getCurvedAnimator() {
        View view = getMainView();

        float fromX = fab.getLeft();
        float fromY = fab.getTop();
        float toX = view.getWidth() / 2 - fab.getWidth() / 2 + view.getLeft();
        float toY = view.getHeight() / 2 - fab.getHeight() / 2 + view.getTop();

        if (isShowingMainView()) {
            return new CurvedAnimator(fromX, fromY, toX, toY);
        } else {
            return new CurvedAnimator(toX, toY, fromX, fromY);
        }
    }

    private ObjectAnimator getFABAnimator() {
        CurvedAnimator curvedAnimator = getCurvedAnimator();
        return ObjectAnimator.ofObject(this, "fabPosition", new CurvedPathEvaluator(), curvedAnimator.getPoints());
    }

    private void expandCircle() {
        Animator expandAnimator = circularExpandingView.expand();
        expandAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                circularExpandingView.setBackgroundColor(Color.TRANSPARENT);
                swapViews();
            }
        });
        expandAnimator.start();
    }

    private void startHideAnimation() {
        Animator contractAnimator = circularExpandingView.contract();
        ViewGroup disappearingView = (ViewGroup) getSecondaryView();
        Animation shrink = AnimationUtils.loadAnimation(getContext(), R.anim.fab_out);
        ObjectAnimator alphaAnimator = ObjectAnimator.ofFloat(disappearingView, "alpha", 1, 0);
        View innerView = disappearingView.getChildAt(0);

        AnimatorSet set = new AnimatorSet();
        set.play(contractAnimator).with(alphaAnimator);
        setupAnimationParams(set);
        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                fab.setVisibility(VISIBLE);
                circularExpandingView.setVisibility(GONE);
                moveFABToOriginalLocation();
            }
        });
        set.start();
    }

    private void moveFABToOriginalLocation() {
        ObjectAnimator fabAnimator = getFABAnimator();

        setupAnimationParams(fabAnimator);
        fabAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                super.onAnimationEnd(animation);
                swapViews();
            }
        });

        fabAnimator.start();
    }

    public void setFabPosition(Point point) {
        fab.setX(point.x);
        fab.setY(point.y);
    }

    private void swapViews() {
        if (isShowingMainView()) {
            getMainView().setVisibility(GONE);
            getMainView().setAlpha(1);
            secondView.setVisibility(VISIBLE);
            circularExpandingView.setVisibility(VISIBLE);
        } else {
            getMainView().setVisibility(VISIBLE);
            secondView.setVisibility(GONE);
            secondView.setAlpha(1);
            circularExpandingView.setVisibility(View.GONE);
        }
        notifyListener();
    }

    private void notifyListener() {
        if (onRevealChangeListener != null) {
            if (isShowingMainView()) {
                onRevealChangeListener.onMainViewAppeared(this, getMainView());
            } else {
                onRevealChangeListener.onSecondaryViewAppeared(this, getSecondaryView());
            }
        }
        invalidate();
    }

    private View getSecondaryView() {
        return secondView;
    }

    private View getMainView() {
        return mainView;
    }

    private int dipsToPixels(float dips) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dips, getResources().getDisplayMetrics());
    }

}
