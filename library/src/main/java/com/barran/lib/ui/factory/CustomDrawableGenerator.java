package com.barran.lib.ui.factory;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.TypedArray;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.barran.lib.R;
import com.barran.lib.utils.log.Logs;

/**
 * 完整实现参考github项目：https://github.com/Cricin/Folivora
 * 
 * Created by tanwei on 2019/2/25.
 */

public class CustomDrawableGenerator {
    
    public static final String TAG = "customGenerator";
    
    private static final int SET_AS_BACKGROUND = 0;
    private static final int SET_AS_SRC = 1;
    private static final int SET_AS_FOREGROUND = 2;
    
    private static final int DRAWABLE_TYPE_SHAPE = 0;
    private static final int DRAWABLE_TYPE_SELECTOR = 1;
    
    private static final int SHAPE_INDEX_0 = 0;
    private static final int SHAPE_INDEX_1 = 1;
    private static final int SHAPE_INDEX_2 = 2;
    
    private static final int[] STATE_ACTIVE = { android.R.attr.state_active };
    private static final int[] STATE_ACTIVATED = { android.R.attr.state_activated };
    private static final int[] STATE_CHECKED = { android.R.attr.state_checked };
    private static final int[] STATE_ENABLED = { android.R.attr.state_enabled };
    private static final int[] STATE_FOCUSED = { android.R.attr.state_focused };
    private static final int[] STATE_PRESSED = { android.R.attr.state_pressed };
    private static final int[] STATE_NORMAL = {};
    
    /**
     * Install custom ViewFactory to current context. note that if you are using
     * AppCompatActivity, this method should called after your activity's
     * super.onCreate() method, since AppCompatDelegate will install a
     * {@link LayoutInflater.Factory2} factory2 to this context.
     */
    public static void installViewFactory(Context ctx) {
        LayoutInflater inflater = LayoutInflater.from(ctx);
        LayoutInflater.Factory2 factory2 = inflater.getFactory2();
        if (factory2 instanceof CustomViewFactory)
            return;
        CustomViewFactory viewFactory = new CustomViewFactory();
        viewFactory.mFactory2 = factory2;
        if (factory2 != null) {
            CustomViewFactory.forceSetFactory2(inflater, viewFactory);
        }
        else {
            inflater.setFactory2(viewFactory);
        }
    }
    
    /**
     * Wraps the given context, replace the {@link LayoutInflater} inflater to
     * custom implementation, this method does nothing if the given context is
     * already been wrapped.
     *
     * @param newBase
     *            new base context
     * @return a wrapped context
     */
    public static Context wrap(final Context newBase) {
        final LayoutInflater inflater = LayoutInflater.from(newBase);
        if (inflater instanceof CustomLayoutInflater)
            return newBase;
        return new ContextWrapper(newBase) {
            private CustomLayoutInflater mInflater;
            
            @Override
            public Object getSystemService(String name) {
                if (LAYOUT_INFLATER_SERVICE.equals(name)) {
                    if (mInflater == null) {
                        mInflater = new CustomLayoutInflater(newBase, inflater);
                    }
                    return mInflater;
                }
                return super.getSystemService(name);
            }
        };
    }
    
    /**
     * to support preview, simple usage is:
     * 
     * <pre>
     * public class CustomViewStub extends CustomView {
     *     public CustomViewStub(Context context, AttributeSet attrs) {
     *         super(context, attrs);
     *         CustomDrawableGenerator.applyDrawableToView(this, attrs);
     *     }
     * }
     * </pre>
     *
     * @param view
     *            view of drawable attached
     * @param attrs
     *            attributes from view tag
     */
    public static void applyDrawableToView(View view, AttributeSet attrs) {
        final Context ctx = view.getContext();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.CustomDrawable);
        
        int drawableType = a.getInt(R.styleable.CustomDrawable_drawableType, -1);
        int setAs = a.getInt(R.styleable.CustomDrawable_setAs, SET_AS_BACKGROUND);
        a.recycle();
        if ((drawableType < 0) || setAs < 0)
            return;
        
        Drawable d = null;
        if (drawableType >= 0) {
            d = newDrawable(drawableType, ctx, attrs);
        }
        if (d == null)
            return;
        switch (setAs) {
            
            case SET_AS_BACKGROUND:
                view.setBackground(d);
                break;
            
            case SET_AS_SRC:
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(d);
                }
                break;
            
            case SET_AS_FOREGROUND:
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    view.setForeground(d);
                }
                else if (view instanceof FrameLayout) {
                    // noinspection RedundantCast
                    ((FrameLayout) view).setForeground(d);
                }
                else {
                    Logs.w(TAG, "can not set foreground to [" + view.getClass()
                            + "], Current device platform is lower than MarshMallow");
                }
                break;
        }
        
    }
    
    private static Drawable newDrawable(int drawableType, Context ctx,
            AttributeSet attrs) {
        Drawable result = null;
        switch (drawableType) {
            case -1:// not used
                break;
            case DRAWABLE_TYPE_SHAPE:
                result = CustomShapeFactory.newShape(ctx, attrs);
                break;
            case DRAWABLE_TYPE_SELECTOR:
                result = newSelector(ctx, attrs);
                break;
            default:
                Logs.w(TAG, "Unexpected drawableType: " + drawableType);
                break;
        }
        return result;
    }
    
    private static StateListDrawable newSelector(Context ctx, AttributeSet attrs) {
        StateListDrawable d = new StateListDrawable();
        TypedArray a = ctx.obtainStyledAttributes(attrs, R.styleable.Custom_Selector);
        Drawable temp;
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateActive);
        if (temp != null)
            d.addState(STATE_ACTIVE, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateActivated);
        if (temp != null)
            d.addState(STATE_ACTIVATED, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateChecked);
        if (temp != null)
            d.addState(STATE_CHECKED, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateEnabled);
        if (temp != null)
            d.addState(STATE_ENABLED, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateFocused);
        if (temp != null)
            d.addState(STATE_FOCUSED, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStatePressed);
        if (temp != null)
            d.addState(STATE_PRESSED, temp);
        temp = getDrawable(ctx, a, attrs,
                R.styleable.Custom_Selector_selectorStateNormal);
        if (temp != null)
            d.addState(STATE_NORMAL, temp);
        a.recycle();
        return d;
    }
    
    public static Drawable getDrawable(Context ctx, TypedArray a, AttributeSet attrs,
            int attrIndex) {
        if (!a.hasValue(attrIndex))
            return null;
        Drawable result = null;
        final int shapeIndex = a.getInt(attrIndex, -1);
        switch (shapeIndex) {
            case -1:
                // not used
                break;
            case SHAPE_INDEX_0:
                result = CustomShapeFactory.newShape(ctx, attrs);
                break;
            case SHAPE_INDEX_1:
                result = CustomShapeFactory.newShape1(ctx, attrs);
                break;
            case SHAPE_INDEX_2:
                result = CustomShapeFactory.newShape2(ctx, attrs);
                break;
            default:
                Logs.i(TAG, "Unexpected shape index" + shapeIndex);
                break;
        }
        if (result == null) {
            result = a.getDrawable(attrIndex);
        }
        return result;
    }
}
