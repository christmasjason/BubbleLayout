package com.daasuu.bubblelayout;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.PopupWindow;

import com.daasuu.bl.BubbleLayout;
import com.daasuu.bl.BubblePopupHelper;

public class MainActivity extends AppCompatActivity {

  private PopupWindow popupWindow;

  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = (Button) findViewById(R.id.btn_popup);

    BubbleLayout bubbleLayout = (BubbleLayout) LayoutInflater.from(this).inflate(R.layout.layout_sample_popup, null);
    popupWindow = BubblePopupHelper.create(this, bubbleLayout);
    assert button != null;
    button.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View view) {
        int[] location = new int[2];
        view.getLocationInWindow(location);
        popupWindow.showAtLocation(view, Gravity.NO_GRAVITY, location[0], view.getHeight() + location[1]);
      }
    });

  }

}
