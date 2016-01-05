package com.truizlop.fabreveallayoutsample;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import com.truizlop.fabreveallayout.FABRevealLayout;

import butterknife.Bind;
import butterknife.ButterKnife;

public class PlayActivity extends AppCompatActivity {
    @Bind(R.id.ivClose)
    ImageView ivClose;
    @Bind(R.id.fab_reveal_layout)
    FABRevealLayout fabRevealLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);

        ivClose.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {
                fabRevealLayout.revealMainView();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Animator animator = ViewAnimationUtils.createCircularReveal(
//                        shape,
//                        shape.getWidth() - 130,
//                        shape.getHeight() - 130,
//                        0,
//                        (float) Math.hypot(shape.getWidth(), shape.getHeight()));
//                shape.setVisibility(View.VISIBLE);
//                animator.setInterpolator(new AccelerateDecelerateInterpolator());
//                if (shape.getVisibility() == View.VISIBLE) {
//                    animator.setDuration(400);
//                    animator.start();
//                    shape.setEnabled(true);
//                }
//            }
//        });
    }

}
