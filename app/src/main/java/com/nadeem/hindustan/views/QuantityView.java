package com.nadeem.hindustan.views;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.view.ContextThemeWrapper;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nadeem.hindustan.R;

/**
 * Created by LSTDev10 on 08/08/16.
 */

public class QuantityView extends LinearLayout implements View.OnClickListener {
    private static final float DEFAULT_STROKE_WIDTH = 0;
    private static final float DEFAULT_BUTTON_PADDING = 4;
    private static final float DEFAULT_TEXT_PADDING = 4;
    private static final int DEFAULT_QUANTITY = 0;
    public OnQuantityChangeListener listener;
    private int quantity;
    private int minQuantity = 0;
    private float buttonPadding;
    private float quantityTextPadding;
    private float borderStrokeWidth;
    private float quantityTextSize;
    private int borderColor = Color.GRAY;
    private int buttonTextColor = Color.GRAY;
    private int quantityTextColor = Color.GRAY;
    private TextView txtQuantity;
    private Button btnIncrement, btnDecrement;
    private Typeface quantityTypeFace = ResourcesCompat.getFont(getContext(), R.font.font_helvetica_neue);

    public QuantityView(Context context) {
        super(context, null);
    }

    public QuantityView(Context context, AttributeSet attrs) {
        super(context, attrs, 0);
        if (attrs != null) {
            TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.QuantityView);
            try {
                minQuantity = a.getInt(R.styleable.QuantityView_minQuantity, DEFAULT_QUANTITY);
                quantity = a.getInt(
                        R.styleable.QuantityView_quantity, DEFAULT_QUANTITY);
                buttonPadding = a.getDimension(
                        R.styleable.QuantityView_buttonPadding, DEFAULT_BUTTON_PADDING);
                quantityTextPadding = a.getDimension(
                        R.styleable.QuantityView_quantityTextPadding, DEFAULT_TEXT_PADDING);
                quantityTextSize = a.getDimension(
                        R.styleable.QuantityView_quantityTextSize, 14);
                borderStrokeWidth = a.getDimension(
                        R.styleable.QuantityView_borderStrokeWidth, DEFAULT_STROKE_WIDTH);
                borderColor = a.getColor(
                        R.styleable.QuantityView_borderColor, getResources().getColor(R.color.colorTextPrimary));
                buttonTextColor = a.getColor(
                        R.styleable.QuantityView_buttonTextColor, Color.GRAY);
                quantityTextColor = a.getColor(
                        R.styleable.QuantityView_quantityTextColor, Color.GRAY);

            } finally {
                a.recycle();
            }
        }
        //setQuantity(quantity);
        init();
        setQuantityTextSize(quantityTextSize);
        setQuantityTextColor(quantityTextColor);
        //setQuantityTypeFace(quantityTypeFace);
        /*getViewTreeObserver().addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
            @Override
            public boolean onPreDraw() {
                getViewTreeObserver().removeOnPreDrawListener(this);

                return true;
            }
        });*/
    }

    /**
     * Helper method to convert pixel to dp
     *
     * @param context
     * @param px
     * @return
     */
    static int pxToDp(final Context context, final float px) {
        return (int) (px / context.getResources().getDisplayMetrics().density);
    }

    /**
     * Helper method to convert dp to pixel
     *
     * @param context
     * @param dp
     * @return
     */
    static int dpToPx(final Context context, final float dp) {
        return (int) (dp * context.getResources().getDisplayMetrics().density);
    }

    public void setQuantityChangeListener(OnQuantityChangeListener listener) {
        this.listener = listener;

    }

    public void init() {
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.WRAP_CONTENT);
        int sdk = Build.VERSION.SDK_INT;
        TypedValue outValue = new TypedValue();
        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        removeAllViews();
        ContextThemeWrapper newContext = new ContextThemeWrapper(getContext(), R.style.quantityButton);
        btnIncrement = new Button(getContext(), null, R.style.quantityButton);
        btnIncrement.setOnClickListener(this);
        btnIncrement.setText("+");
        btnIncrement.setTextSize(26);
        btnIncrement.setSelected(true);
        //btnIncrement.setLayoutParams(params);
        //btnIncrement.setPadding((int) buttonPadding, (int) buttonPadding, (int) buttonPadding, (int) buttonPadding);
        btnDecrement = new Button(getContext(), null, R.style.quantityButton);
        btnDecrement.setText("-");
        btnDecrement.setTextSize(26);
        btnDecrement.setOnClickListener(this);
        //btnIncrement.setSelected(quantity > 0 ? true : false);
        //btnDecrement.setLayoutParams(params);
        //btnDecrement.setPadding((int) buttonPadding, (int) buttonPadding, (int) buttonPadding, (int) buttonPadding);
        txtQuantity = new TextView(getContext());
        /*if (Build.VERSION.SDK_INT < 23) {
            txtQuantity.setTextAppearance(getContext(), R.style.quantityText);
        }else{
            txtQuantity.setTextAppearance(R.style.quantityText);
        }*/
        //txtQuantity.setTextAppearance(R.style.quantityText);
        //txtQuantity.setTypeface(ResourcesCompat.getFont(getContext(), R.font.font_futura_semibold));
        //txtQuantity.setTypeface(txtQuantity.getTypeface(), Typeface.BOLD);

        //txtQuantity.setLayoutParams(params);
        txtQuantity.setPadding((int) quantityTextPadding, (int) buttonPadding, (int) quantityTextPadding, (int) buttonPadding);
        setQuantity(quantity);
        params.width = (int) borderStrokeWidth;
        addView(btnDecrement);
        addView(txtQuantity);
        addView(btnIncrement);

    }

    /*private void setViewHeight(int height){
        LayoutParams params = new LayoutParams(LayoutParams.WRAP_CONTENT
                , LayoutParams.WRAP_CONTENT);
        params.width = (int) borderStrokeWidth;
        params.height = LayoutParams.MATCH_PARENT;//height;
        params.weight=1;
        getChildAt(1).setLayoutParams(params);
        getChildAt(3).setLayoutParams(params);

    }*/
    public float getButtonPadding() {
        return buttonPadding;
    }

    public void setButtonPadding(int buttonPadding) {
        this.buttonPadding = buttonPadding;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
        if (txtQuantity != null)
            txtQuantity.setText(quantity + "");
        if (listener != null)
            listener.onQuantityChange(quantity);
        btnDecrement.setSelected(quantity > minQuantity ? true : false);
    }

    public int getMinQuantity() {
        return minQuantity;
    }

    public void setMinQuantity(int minQuantity) {
        this.minQuantity = minQuantity;
    }

    public float getBorderWidth() {
        return borderStrokeWidth;
    }

    public void setBorderWidth(int borderWidth) {
        this.borderStrokeWidth = borderWidth;
        init();
    }


    public float getQuantityTextSize() {
        return quantityTextSize;
    }

    public void setQuantityTextSize(float quantityTextSize) {
        this.quantityTextSize = quantityTextSize;
        txtQuantity.setTextSize(quantityTextSize);
    }

    public int getBorderColor() {
        return borderColor;
    }

    public void setBorderColor(int borderColor) {
        this.borderColor = borderColor;
        init();
    }

    public float getQuantityTextPadding() {
        return quantityTextPadding;
    }

    public void setQuantityTextPadding(float quantityTextPadding) {
        this.quantityTextPadding = quantityTextPadding;
        init();
    }

    public Typeface getQuantityTypeFace() {
        return quantityTypeFace;
    }

    /**
     * Typeface to quantity and +. - button
     */
    public void setQuantityTypeFace(Typeface quantityTypeFace) {
        this.quantityTypeFace = quantityTypeFace;
        txtQuantity.setTypeface(quantityTypeFace);
    }

    public int getQuantityTextColor() {
        return quantityTextColor;
    }

    public void setQuantityTextColor(int quantityTextColor) {
        this.quantityTextColor = quantityTextColor;
        txtQuantity.setTextColor(quantityTextColor);
    }

    public int getButtonTextColor() {
        return buttonTextColor;
    }

    /*
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec));
    }


    */
/*

    private int measureHeight(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            final int height;
            if (layoutHeight == ViewGroup.LayoutParams.WRAP_CONTENT) {
                height = dpToPx(getContext(), DEFAULT_HEIGHT_IN_DP);
            } else if (layoutHeight == ViewGroup.LayoutParams.MATCH_PARENT) {
                height = getMeasuredHeight();
            } else {
                height = layoutHeight;
            }
            result = height + getPaddingTop() + getPaddingBottom() + (2 * DEFAULT_PAINT_STROKE_WIDTH);
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }

    */
/*


    private int measureWidth(int measureSpec) {
        int specMode = MeasureSpec.getMode(measureSpec);
        int specSize = MeasureSpec.getSize(measureSpec);
        int result;
        if (specMode == MeasureSpec.EXACTLY) {
            result = specSize;
        } else {
            result = specSize + getPaddingLeft() + getPaddingRight() + (2 * DEFAULT_PAINT_STROKE_WIDTH) ;
            if (specMode == MeasureSpec.AT_MOST) {
                result = Math.min(result, specSize);
            }
        }
        return result;
    }
*/
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {

        int count = getChildCount();
        LayoutParams params;

        /** The amount of space used by children in the left gutter. */
        int mLeftWidth;

        /** The amount of space used by children in the right gutter. */
        int mRightWidth;

        // These keep track of the space we are using on the left and right for
        // views positioned there; we need member variables so we can also use
        // these for layout later.
        mLeftWidth = 0;
        mRightWidth = 0;

        // Measurement will ultimately be computing these values.
        int maxHeight = 0;

        int maxWidth = 0;
        int childState = 0;

        // Iterate through all children, measuring them and computing our dimensions
        // from their size.
        for (int i = 0; i < count; i++) {
            final View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                // Measure the child.
                measureChildWithMargins(child, widthMeasureSpec, 0, heightMeasureSpec, 0);

                // Update our size information based on the layout params.  Children
                // that asked to be positioned on the left or right go in those gutters.
                final LayoutParams lp = (LayoutParams) child.getLayoutParams();
              /*  if (*//*lp.position == LayoutParams.POSITION_LEFT*//*true) {
                    mLeftWidth += Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                } else if (*//*lp.position == LayoutParams.POSITION_RIGHT*//*false) {
                    mRightWidth += Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                } else {
                    maxWidth = Math.max(maxWidth,
                            child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin);
                }*/
                maxWidth += child.getMeasuredWidth() + lp.leftMargin + lp.rightMargin;
                //maxHeight = child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin;
                maxHeight = Math.max(maxHeight,
                        child.getMeasuredHeight() + lp.topMargin + lp.bottomMargin);
                /*if (i == 1 || i == 3)
                    getChildAt(1).getLayoutParams().height = maxHeight;
                getChildAt(3).getLayoutParams().height = maxHeight;*/
                childState = combineMeasuredStates(childState, child.getMeasuredState());
            }
        }
        // Total width is the maximum width of all inner children plus the gutters.
        maxWidth += mLeftWidth + mRightWidth;

        // Check against our minimum height and width
        maxHeight = Math.max(maxHeight, getSuggestedMinimumHeight());
        maxWidth = Math.max(maxWidth, getSuggestedMinimumWidth());

        // Report our final dimensions.
        setMeasuredDimension(resolveSizeAndState(maxWidth, widthMeasureSpec, childState),
                resolveSizeAndState(maxHeight, heightMeasureSpec,
                        childState << MEASURED_HEIGHT_STATE_SHIFT));
    }

    @Override
    public void onClick(View v) {
        //if (listener == null) return;
        Button button = (Button) v;
        if (button == btnDecrement && quantity > minQuantity) {
            int x = --quantity;
            setQuantity(x);
        } else if (button == btnIncrement) {
            int x = ++quantity;
            setQuantity(x);
        }

        btnDecrement.setSelected(quantity > minQuantity ? true : false);
    }

    public interface OnQuantityChangeListener {
        void onQuantityChange(int quantity);
    }
}
