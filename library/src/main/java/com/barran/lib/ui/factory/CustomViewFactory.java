package com.barran.lib.ui.factory;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import com.barran.lib.utils.log.Logs;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义Factory2
 * 
 * Created by tanwei on 2019/2/25.
 */

public class CustomViewFactory implements LayoutInflater.Factory2 {
    private static final String[] sClassPrefixList = { "android.widget.",
            "android.webkit.", "android.app.", "android.view." };
    
    private static final Class<?>[] sConstructorSignature = new Class[] { Context.class,
            AttributeSet.class };
    private static Object[] sConstructorArgs = new Object[2];
    private static Map<String, Constructor<? extends View>> sConstructorMap = new HashMap<>();
    
    LayoutInflater.Factory2 mFactory2;
    LayoutInflater.Factory mFactory;
    
    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return onCreateView(null, name, context, attrs);
    }
    
    @Override
    public View onCreateView(View parent, String name, Context context,
            AttributeSet attrs) {
        View result = null;
        if (mFactory2 != null) {
            result = mFactory2.onCreateView(parent, name, context, attrs);
        }
        if (mFactory != null && result == null) {
            result = mFactory.onCreateView(name, context, attrs);
        }
        if (result == null && name.endsWith("ViewStub"))
            return null;// fix NPE when creating ViewStub
            
        if (result == null && name.indexOf('.') != -1) {
            result = createCustomView(name, context, attrs);
        }
        
        if (result == null) {
            LayoutInflater inflater = getLayoutInflater(context);
            for (String prefix : sClassPrefixList) {
                try {
                    result = inflater.createView(name, prefix, attrs);
                    if (result != null)
                        break;
                } catch (ClassNotFoundException e) {
                    // In this case we want to let the LayoutInflater self take
                    // a crack
                    // at it.
                }
            }
        }
        
        if (result != null) {
            CustomDrawableGenerator.applyDrawableToView(result, attrs);
        }
        return result;
    }
    
    private View createCustomView(String name, Context ctx, AttributeSet attrs) {
        
        Logs.v(CustomDrawableGenerator.TAG, "createCustomView : " + name);
        
        Constructor<? extends View> constructor = sConstructorMap.get(name);
        
        try {
            if (constructor == null) {
                // Class not found in the cache, see if it's real, and try to
                // add it
                Class<? extends View> clazz = ctx.getClassLoader().loadClass(name)
                        .asSubclass(View.class);
                
                constructor = clazz.getConstructor(sConstructorSignature);
                sConstructorMap.put(name, constructor);
            }
            constructor.setAccessible(true);
            sConstructorArgs[0] = ctx;
            sConstructorArgs[1] = attrs;
            return constructor.newInstance(sConstructorArgs);
        } catch (Exception e) {
            // We do not want to catch these, lets return null and let the
            // actual LayoutInflater
            // try
            return null;
        } finally {
            sConstructorArgs[0] = null;
            sConstructorArgs[1] = null;
        }
    }
    
    private LayoutInflater getLayoutInflater(Context context) {
        LayoutInflater inflater = LayoutInflater.from(context);
        if (inflater instanceof CustomLayoutInflater) {
            return ((CustomLayoutInflater) inflater).getBaseInflater();
        }
        else {
            return inflater;
        }
    }
    
    private static boolean sCheckedField;
    private static Field sLayoutInflaterFactory2Field;
    
    static void forceSetFactory2(LayoutInflater inflater,
            LayoutInflater.Factory2 factory) {
        if (!sCheckedField) {
            try {
                // noinspection JavaReflectionMemberAccess
                sLayoutInflaterFactory2Field = LayoutInflater.class
                        .getDeclaredField("mFactory2");
                sLayoutInflaterFactory2Field.setAccessible(true);
            } catch (NoSuchFieldException e) {
                Log.e(CustomDrawableGenerator.TAG,
                        "forceSetFactory2 Could not find field 'mFactory2' on class "
                                + inflater.getClass().getName()
                                + "; Folivora will not available.",
                        e);
            }
            sCheckedField = true;
        }
        if (sLayoutInflaterFactory2Field != null) {
            try {
                sLayoutInflaterFactory2Field.set(inflater, factory);
            } catch (Exception e) {
                Log.e(CustomDrawableGenerator.TAG,
                        "forceSetFactory2 could not set the Factory2 on LayoutInflater "
                                + inflater + "; Folivora will not available.",
                        e);
            }
        }
    }
}
