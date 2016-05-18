package com.daasuu.bl;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.annotation.NonNull;
import android.view.ViewGroup;
import android.widget.PopupWindow;

public class BubblePopupHelper {

  public static PopupWindow create(@NonNull Context context, @NonNull BubbleLayout bubbleLayout) {

    PopupWindow popupWindow = new PopupWindow(context);

    popupWindow.setContentView(bubbleLayout);
    popupWindow.setOutsideTouchable(true);
    popupWindow.setWidth(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setHeight(ViewGroup.LayoutParams.WRAP_CONTENT);
    popupWindow.setAnimationStyle(android.R.style.Animation_Dialog);
    popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

    return popupWindow;
  }

}
