package com.truizlop.fabreveallayoutsample;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import com.truizlop.fabreveallayout.FABRevealLayout;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PlayActivity extends AppCompatActivity {
    @Bind(R.id.fab_reveal_layout)
    FABRevealLayout fabRevealLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play);
        ButterKnife.bind(this);
    }

    @OnClick({R.id.ivClose, R.id.ivClose2, R.id.ivClose3})
    public void closeClicked(){
        fabRevealLayout.revealMainView();
    }

}
