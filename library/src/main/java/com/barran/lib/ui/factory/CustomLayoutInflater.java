package com.barran.lib.ui.factory;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import org.xmlpull.v1.XmlPullParser;

/**
 * 自定义LayoutInflater
 * 
 * Created by tanwei on 2019/2/25.
 */

public class CustomLayoutInflater extends LayoutInflater {
    private LayoutInflater mInflater;
    
    CustomLayoutInflater(Context context, LayoutInflater delegate) {
        super(context);
        if (delegate instanceof CustomLayoutInflater) {
            this.mInflater = ((CustomLayoutInflater) delegate).mInflater
                    .cloneInContext(context);
        }
        else {
            this.mInflater = delegate;
        }
        Factory2 factory2 = mInflater.getFactory2();
        if (factory2 == null) {
            mInflater.setFactory2(new CustomViewFactory());
        }
        else {
            if (!(factory2 instanceof CustomViewFactory))
                Log.i(CustomDrawableGenerator.TAG,
                        "The Activity's LayoutInflater already has a Factory installed"
                                + " so we can not install Folivora's");
        }
    }
    
    @Override
    public LayoutInflater cloneInContext(Context newContext) {
        return new CustomLayoutInflater(newContext, this);
    }
    
    @Override
    public void setFactory(Factory factory) {
        CustomViewFactory f = (CustomViewFactory) mInflater.getFactory2();
        f.mFactory = factory;
    }
    
    @Override
    public void setFactory2(Factory2 factory2) {
        CustomViewFactory f = (CustomViewFactory) mInflater.getFactory2();
        f.mFactory2 = factory2;
    }
    
    @Override
    public Filter getFilter() {
        return mInflater.getFilter();
    }
    
    @Override
    public void setFilter(Filter filter) {
        mInflater.setFilter(filter);
    }
    
    @Override
    public View inflate(int resource, ViewGroup root) {
        return mInflater.inflate(resource, root);
    }
    
    @Override
    public View inflate(XmlPullParser parser, ViewGroup root) {
        return mInflater.inflate(parser, root);
    }
    
    @Override
    public View inflate(int resource, ViewGroup root, boolean attachToRoot) {
        return mInflater.inflate(resource, root, attachToRoot);
    }
    
    @Override
    public View inflate(XmlPullParser parser, ViewGroup root, boolean attachToRoot) {
        return mInflater.inflate(parser, root, attachToRoot);
    }
    
    LayoutInflater getBaseInflater() {
        return mInflater;
    }
}
