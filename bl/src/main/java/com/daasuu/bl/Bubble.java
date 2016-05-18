package com.daasuu.bl;

import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.RectF;
import android.graphics.drawable.Drawable;
import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

public class Bubble extends Drawable {

  public static final int LEFT = 0;
  public static final int TOP = 1;
  public static final int RIGHT = 2;
  public static final int BOTTOM = 3;

  @Retention(RetentionPolicy.SOURCE)
  @IntDef({LEFT, TOP, RIGHT, BOTTOM})
  public @interface ArrowDirection {
  }

  private RectF bubbleRect;

  private Path solidPath;
  private Path strokePath;

  private Paint solidPaint;
  private Paint strokePaint;

  private float arrowWidth;
  private float cornersRadius;
  private float arrowHeight;
  private float arrowPosition;
  private float strokeWidth;

  public Bubble(RectF rect, float arrowWidth, float cornersRadius, float arrowHeight,
                float arrowPosition, float strokeWidth, int strokeColor, int bubbleColor,
                @ArrowDirection int arrowDirection) {
    this.bubbleRect = rect;

    this.arrowWidth = arrowWidth;
    this.cornersRadius = cornersRadius;
    this.arrowHeight = arrowHeight;
    this.arrowPosition = arrowPosition;
    this.strokeWidth = strokeWidth;

    solidPath = new Path();
    solidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    solidPaint.setColor(bubbleColor);

    if (strokeWidth > 0) {
      strokePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
      strokePaint.setColor(strokeColor);
      strokePath = new Path();

      initPath(arrowDirection, strokePath, 0);
    }
    initPath(arrowDirection, solidPath, strokeWidth);
  }

  @Override
  public void draw(Canvas canvas) {
    if (strokeWidth > 0) {
      canvas.drawPath(strokePath, strokePaint);
    }
    canvas.drawPath(solidPath, solidPaint);
  }

  @Override
  public int getOpacity() {
    return PixelFormat.TRANSLUCENT;
  }

  @Override
  public void setAlpha(int alpha) {
    solidPaint.setAlpha(alpha);
  }

  @Override
  public void setColorFilter(ColorFilter colorFilter) {
    solidPaint.setColorFilter(colorFilter);
  }

  @Override
  public int getIntrinsicWidth() {
    return (int) bubbleRect.width();
  }

  @Override
  public int getIntrinsicHeight() {
    return (int) bubbleRect.height();
  }

  private void initPath(@ArrowDirection int arrowDirection, Path path, float strokeWidth) {
    switch (arrowDirection) {
      case LEFT:
        if (cornersRadius <= 0 ||
            (strokeWidth > 0 && strokeWidth > cornersRadius)) {
          initLeftSquarePath(bubbleRect, path, strokeWidth);
        } else {
          initLeftRoundedPath(bubbleRect, path, strokeWidth);
        }
        break;

      case TOP:
        if (cornersRadius <= 0 ||
            (strokeWidth > 0 && strokeWidth > cornersRadius)) {
          initTopSquarePath(bubbleRect, path, strokeWidth);
        } else {
          initTopRoundedPath(bubbleRect, path, strokeWidth);
        }
        break;

      case RIGHT:
        if (cornersRadius <= 0 ||
            (strokeWidth > 0 && strokeWidth > cornersRadius)) {
          initRightSquarePath(bubbleRect, path, strokeWidth);
        } else {
          initRightRoundedPath(bubbleRect, path, strokeWidth);
        }
        break;

      case BOTTOM:
        if (cornersRadius <= 0 &&
            (strokeWidth > 0 && strokeWidth > cornersRadius)) {
          initBottomSquarePath(bubbleRect, path, strokeWidth);
        } else {
          initBottomRoundedPath(bubbleRect, path, strokeWidth);
        }
        break;
    }
  }

  private void initLeftRoundedPath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(arrowWidth + rect.left + cornersRadius + strokeWidth, rect.top + strokeWidth);

    path.lineTo(rect.width() - cornersRadius - strokeWidth, rect.top + strokeWidth);
    path.arcTo(new RectF(rect.right - cornersRadius, rect.top + strokeWidth, rect.right - strokeWidth,
        cornersRadius + rect.top), 270, 90);

    path.lineTo(rect.right - strokeWidth, rect.bottom - cornersRadius - strokeWidth);
    path.arcTo(new RectF(rect.right - cornersRadius, rect.bottom - cornersRadius,
        rect.right - strokeWidth, rect.bottom - strokeWidth), 0, 90);

    path.lineTo(rect.left + arrowWidth + cornersRadius + strokeWidth, rect.bottom - strokeWidth);
    path.arcTo(new RectF(rect.left + arrowWidth + strokeWidth, rect.bottom - cornersRadius,
        cornersRadius + rect.left + arrowWidth, rect.bottom - strokeWidth), 90, 90);

    path.lineTo(rect.left + arrowWidth + strokeWidth, arrowHeight + arrowPosition - (strokeWidth / 2));

    path.lineTo(rect.left + strokeWidth + strokeWidth, arrowPosition + arrowHeight / 2);
    path.lineTo(rect.left + arrowWidth + strokeWidth, arrowPosition + (strokeWidth / 2));

    path.lineTo(rect.left + arrowWidth + strokeWidth, rect.top + cornersRadius + strokeWidth);
    path.arcTo(new RectF(rect.left + arrowWidth + strokeWidth, rect.top + strokeWidth, cornersRadius
        + rect.left + arrowWidth, cornersRadius + rect.top), 180, 90);

    path.close();
  }

  private void initLeftSquarePath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(arrowWidth + rect.left + strokeWidth, rect.top + strokeWidth);

    path.lineTo(rect.width() - strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.right - strokeWidth, rect.bottom - strokeWidth);
    path.lineTo(rect.left + arrowWidth + strokeWidth, rect.bottom - strokeWidth);

    path.lineTo(rect.left + arrowWidth + strokeWidth, arrowPosition + arrowHeight - (strokeWidth / 2));
    path.lineTo(rect.left + strokeWidth + strokeWidth, arrowPosition + arrowHeight / 2);
    path.lineTo(rect.left + arrowWidth + strokeWidth, arrowPosition + (strokeWidth / 2));

    path.lineTo(rect.left + arrowWidth + strokeWidth, rect.top + strokeWidth);
    path.close();
  }

  private void initTopRoundedPath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + Math.min(arrowPosition, cornersRadius) + strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.lineTo(rect.left + arrowPosition + (strokeWidth / 2), rect.top + arrowHeight + strokeWidth);
    path.lineTo(rect.left + arrowWidth / 2 + arrowPosition, rect.top + strokeWidth + strokeWidth);

    path.lineTo(rect.left + arrowWidth + arrowPosition - (strokeWidth / 2), rect.top + arrowHeight + strokeWidth);
    path.lineTo(rect.right - cornersRadius - strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.arcTo(new RectF(rect.right - cornersRadius,
        rect.top + arrowHeight + strokeWidth, rect.right - strokeWidth, cornersRadius + rect.top + arrowHeight), 270, 90);
    path.lineTo(rect.right - strokeWidth, rect.bottom - cornersRadius - strokeWidth);

    path.arcTo(new RectF(rect.right - cornersRadius, rect.bottom - cornersRadius,
        rect.right - strokeWidth, rect.bottom - strokeWidth), 0, 90);
    path.lineTo(rect.left + cornersRadius + strokeWidth, rect.bottom - strokeWidth);

    path.arcTo(new RectF(rect.left + strokeWidth, rect.bottom - cornersRadius,
        cornersRadius + rect.left, rect.bottom - strokeWidth), 90, 90);

    path.lineTo(rect.left + strokeWidth, rect.top + arrowHeight + cornersRadius + strokeWidth);

    path.arcTo(new RectF(rect.left + strokeWidth, rect.top + arrowHeight + strokeWidth, cornersRadius
        + rect.left, cornersRadius + rect.top + arrowHeight), 180, 90);

    path.close();
  }

  private void initTopSquarePath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + arrowPosition + strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.lineTo(rect.left + arrowWidth / 2 + arrowPosition, rect.top + strokeWidth + strokeWidth);
    path.lineTo(rect.left + arrowWidth + arrowPosition - (strokeWidth / 2), rect.top + arrowHeight + strokeWidth);
    path.lineTo(rect.right - strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.lineTo(rect.right - strokeWidth, rect.bottom - strokeWidth);
    path.lineTo(rect.left + strokeWidth, rect.bottom - strokeWidth);
    path.lineTo(rect.left + strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.lineTo(rect.left + arrowPosition + strokeWidth, rect.top + arrowHeight + strokeWidth);

    path.close();
  }

  private void initRightRoundedPath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + cornersRadius + strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.width() - cornersRadius - arrowWidth - strokeWidth, rect.top + strokeWidth);
    path.arcTo(new RectF(rect.right - cornersRadius - arrowWidth,
        rect.top + strokeWidth, rect.right - arrowWidth - strokeWidth, cornersRadius + rect.top), 270, 90);

    path.lineTo(rect.right - arrowWidth - strokeWidth, arrowPosition + (strokeWidth / 2));
    path.lineTo(rect.right - strokeWidth - strokeWidth, arrowPosition + arrowHeight / 2);
    path.lineTo(rect.right - arrowWidth - strokeWidth, arrowPosition + arrowHeight - (strokeWidth / 2));
    path.lineTo(rect.right - arrowWidth - strokeWidth, rect.bottom - cornersRadius - strokeWidth);

    path.arcTo(new RectF(rect.right - cornersRadius - arrowWidth, rect.bottom - cornersRadius,
        rect.right - arrowWidth - strokeWidth, rect.bottom - strokeWidth), 0, 90);
    path.lineTo(rect.left + arrowWidth + strokeWidth, rect.bottom - strokeWidth);

    path.arcTo(new RectF(rect.left + strokeWidth, rect.bottom - cornersRadius,
        cornersRadius + rect.left, rect.bottom - strokeWidth), 90, 90);

    path.arcTo(new RectF(rect.left + strokeWidth, rect.top + strokeWidth, cornersRadius
        + rect.left, cornersRadius + rect.top), 180, 90);
    path.close();
  }

  private void initRightSquarePath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.width() - arrowWidth - strokeWidth, rect.top + strokeWidth);

    path.lineTo(rect.right - arrowWidth - strokeWidth, arrowPosition + (strokeWidth / 2));
    path.lineTo(rect.right - strokeWidth - strokeWidth, arrowPosition + arrowHeight / 2);
    path.lineTo(rect.right - arrowWidth - strokeWidth, arrowPosition + arrowHeight - (strokeWidth / 2));

    path.lineTo(rect.right - arrowWidth - strokeWidth, rect.bottom - strokeWidth);

    path.lineTo(rect.left + strokeWidth, rect.bottom - strokeWidth);
    path.lineTo(rect.left + strokeWidth, rect.top + strokeWidth);

    path.close();
  }

  private void initBottomRoundedPath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + cornersRadius + strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.width() - cornersRadius - strokeWidth, rect.top + strokeWidth);
    path.arcTo(new RectF(rect.right - cornersRadius,
        rect.top + strokeWidth, rect.right - strokeWidth, cornersRadius + rect.top), 270, 90);

    path.lineTo(rect.right - strokeWidth, rect.bottom - arrowHeight - cornersRadius - strokeWidth);
    path.arcTo(new RectF(rect.right - cornersRadius, rect.bottom - cornersRadius - arrowHeight,
        rect.right - strokeWidth, rect.bottom - arrowHeight - strokeWidth), 0, 90);

    path.lineTo(rect.left + arrowWidth + arrowPosition - (strokeWidth / 2), rect.bottom - arrowHeight - strokeWidth);
    path.lineTo(rect.left + arrowPosition + arrowWidth / 2, rect.bottom - strokeWidth - strokeWidth);
    path.lineTo(rect.left + arrowPosition + (strokeWidth / 2), rect.bottom - arrowHeight - strokeWidth);
    path.lineTo(rect.left + Math.min(cornersRadius, arrowPosition) + strokeWidth, rect.bottom - arrowHeight - strokeWidth);

    path.arcTo(new RectF(rect.left + strokeWidth, rect.bottom - cornersRadius - arrowHeight,
        cornersRadius + rect.left, rect.bottom - arrowHeight - strokeWidth), 90, 90);
    path.lineTo(rect.left + strokeWidth, rect.top + cornersRadius + strokeWidth);
    path.arcTo(new RectF(rect.left + strokeWidth, rect.top + strokeWidth, cornersRadius
        + rect.left, cornersRadius + rect.top), 180, 90);
    path.close();
  }

  private void initBottomSquarePath(RectF rect, Path path, float strokeWidth) {
    path.moveTo(rect.left + strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.right - strokeWidth, rect.top + strokeWidth);
    path.lineTo(rect.right - strokeWidth, rect.bottom - arrowHeight - strokeWidth);

    path.lineTo(rect.left + arrowWidth + arrowPosition - (strokeWidth / 2), rect.bottom - arrowHeight - strokeWidth);
    path.lineTo(rect.left + arrowPosition + arrowWidth / 2, rect.bottom - strokeWidth - strokeWidth);
    path.lineTo(rect.left + arrowPosition + (strokeWidth / 2), rect.bottom - arrowHeight - strokeWidth);
    path.lineTo(rect.left + arrowPosition + strokeWidth, rect.bottom - arrowHeight - strokeWidth);

    path.lineTo(rect.left + strokeWidth, rect.bottom - arrowHeight - strokeWidth);
    path.lineTo(rect.left + strokeWidth, rect.top + strokeWidth);
    path.close();
  }
}