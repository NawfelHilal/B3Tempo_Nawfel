package com.example.b3tempo_nawfel;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

/**
 * TODO: document your custom view class.
 */
public class DayColorView extends View {
    private static final float CIRCLE_SCALE = 0.9f;
    private String captionText;
    private int captionTextColor = Color.BLACK;
    private float captionTextSize = 0;
    private int dayCircleColor = Color.GRAY;

    private TextPaint textPaint;
    private Paint circlePaint;
    private float textWidth;
    private float textHeight;

    public DayColorView(Context context) {
        super(context);
        init(context,null, 0);
    }

    public DayColorView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs, 0);
    }

    public DayColorView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(context, attrs, defStyle);
    }

    private void init(Context context, AttributeSet attrs, int defStyle) {
        // Load attributes
        final TypedArray a = getContext().obtainStyledAttributes(
                attrs, R.styleable.DayColorView, defStyle, 0);

        String text = a.getString(R.styleable.DayColorView_captionText);
        captionText = text == null ? context.getString(R.string.not_set) : text;

        captionTextColor = a.getColor(R.styleable.DayColorView_captionTextColor, captionTextColor);

        // Use getDimensionPixelSize or getDimensionPixelOffset when dealing with
        // values that should fall on pixel boundaries.
        captionTextSize = a.getDimension(R.styleable.DayColorView_captionTextSize, getResources().getDimension(R.dimen.tempo_color_view_text_size));

        dayCircleColor = a.getColor(R.styleable.DayColorView_dayCircleColor, ContextCompat.getColor(context, R.color.tempo_undecided_day_bg));

        a.recycle();

        // Set up a default TextPaint object
        textPaint = new TextPaint();
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);

        circlePaint = new Paint();
        setCirclePaint();

        // Update TextPaint and text measurements from attributes
        setTextPaintAndMeasurements();
    }

    private void setTextPaintAndMeasurements() {
        // setup a default TextPaint object
        textPaint.setFlags(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextAlign(Paint.Align.LEFT);
        textPaint.setTextSize(captionTextSize);
        textPaint.setColor(captionTextColor);

        textWidth = textPaint.measureText(captionText);
        Paint.FontMetrics fontMetrics = textPaint.getFontMetrics();
        textHeight = fontMetrics.bottom;
    }

    private void setCirclePaint(){
        circlePaint.setStyle(Paint.Style.FILL);
        circlePaint.setColor(dayCircleColor);
    }
    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        super.onDraw(canvas);

        // TODO: consider storing these as member variables to reduce
        // allocations per draw cycle.
        int paddingLeft = getPaddingLeft();
        int paddingTop = getPaddingTop();
        int paddingRight = getPaddingRight();
        int paddingBottom = getPaddingBottom();

        int contentWidth = getWidth() - paddingLeft - paddingRight;
        int contentHeight = getHeight() - paddingTop - paddingBottom;

        float cx = (float) contentWidth / 2;
        float cy = (float) contentHeight / 2;
        float radius = Math.min(contentHeight, contentWidth) / 2f * CIRCLE_SCALE;
    canvas.drawCircle(cx,cy,radius, circlePaint);
        // Draw the text.
        canvas.drawText(captionText,
                paddingLeft + (contentWidth - textWidth) / 2,
                paddingTop + (contentHeight + textHeight) / 2,
                textPaint);

    }

    public void setDayCircleColor(TempoColor color) {
        dayCircleColor = ContextCompat.getColor(getContext(),color.getColorResId());
        setCirclePaint();
        invalidate();
    }

}