package com.truizlop.fabreveallayout;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

/**
 * Created by jeremy_shelly on 1/8/16.
 */
public class HierchyFABBehavior extends FloatingActionButton.Behavior {

    @Override public boolean layoutDependsOn(CoordinatorLayout parent, FloatingActionButton child, View dependency) {
        return false;
    }
}
