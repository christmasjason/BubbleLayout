package com.daasuu.bl;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.widget.FrameLayout;

public class BubbleLayout extends FrameLayout {

  public static float DEFAULT_STROKE_WIDTH = -1;

  private int arrowDirection;
  private int bubbleColor;
  private int strokeColor;

  private float arrowWidth;
  private float cornersRadius;
  private float arrowHeight;
  private float arrowPosition;
  private float strokeWidth;

  private Bubble bubble;

  public BubbleLayout(Context context) {
    this(context, null, 0);
  }

  public BubbleLayout(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public BubbleLayout(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);

    if (attrs != null) {
      TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.BubbleLayout);
      arrowWidth = typedArray.getDimension(R.styleable.BubbleLayout_arrowWidth, convertDpToPixel(8, context));
      arrowHeight = typedArray.getDimension(R.styleable.BubbleLayout_arrowHeight, convertDpToPixel(8, context));
      cornersRadius = typedArray.getDimension(R.styleable.BubbleLayout_cornersRadius, 0);
      arrowPosition = typedArray.getDimension(R.styleable.BubbleLayout_arrowPosition, convertDpToPixel(12, context));
      bubbleColor = typedArray.getColor(R.styleable.BubbleLayout_bubbleColor, Color.WHITE);
      strokeWidth = typedArray.getDimension(R.styleable.BubbleLayout_strokeWidth, DEFAULT_STROKE_WIDTH);
      strokeColor = typedArray.getColor(R.styleable.BubbleLayout_strokeColor, Color.GRAY);
      arrowDirection = typedArray.getInt(R.styleable.BubbleLayout_arrowDirection, Bubble.LEFT);

      typedArray.recycle();
    } else {
      arrowWidth = convertDpToPixel(8, context);
      arrowHeight = convertDpToPixel(8, context);
      cornersRadius = 0;
      arrowPosition = convertDpToPixel(12, context);
      bubbleColor = Color.WHITE;
      strokeWidth = DEFAULT_STROKE_WIDTH;
      strokeColor = Color.GRAY;
      arrowDirection = Bubble.LEFT;
    }
    initPadding();
  }

  @Override
  protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
    super.onLayout(changed, left, top, right, bottom);
    initDrawable(0, getWidth(), 0, getHeight());
  }

  @Override
  protected void dispatchDraw(Canvas canvas) {
    if (bubble != null) {
      bubble.draw(canvas);
    }
    super.dispatchDraw(canvas);
  }

  private void initDrawable(int left, int right, int top, int bottom) {
    if (right < left || bottom < top) {
      return;
    }

    RectF rectF = new RectF(left, top, right, bottom);
    bubble = new Bubble(rectF, arrowWidth, cornersRadius, arrowHeight, arrowPosition,
        strokeWidth, strokeColor, bubbleColor, arrowDirection);
  }

  private void initPadding() {
    int paddingLeft = getPaddingLeft();
    int paddingRight = getPaddingRight();
    int paddingTop = getPaddingTop();
    int paddingBottom = getPaddingBottom();
    switch (arrowDirection) {
      case Bubble.LEFT:
        paddingLeft += arrowWidth;
        break;
      case Bubble.RIGHT:
        paddingRight += arrowWidth;
        break;
      case Bubble.TOP:
        paddingTop += arrowHeight;
        break;
      case Bubble.BOTTOM:
        paddingBottom += arrowHeight;
        break;
    }
    if (strokeWidth > 0) {
      paddingLeft += strokeWidth;
      paddingRight += strokeWidth;
      paddingTop += strokeWidth;
      paddingBottom += strokeWidth;
    }
    setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
  }

  static float convertDpToPixel(float dp, Context context) {
    DisplayMetrics metrics = context.getResources().getDisplayMetrics();
    return dp * (metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
  }
}
