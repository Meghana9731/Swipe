package com.app.swap;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends PagerAdapter {

    List<SliderItems> sliderItems;
    LayoutInflater mLayoutInflater;
    Context context;
    ArrayList<String> text;
    ArrayList<String> id;
    VerticalViewPager verticalViewPager;

    int newposition;
    float x1,x2;


    public ViewPagerAdapter(Context context, List<SliderItems> sliderItems,  ArrayList<String> text,
                            ArrayList<String> id, VerticalViewPager verticalViewPager) {
        this.context = context;
        this.sliderItems = sliderItems;
        this.text = text;
        this.id=id;
        this.verticalViewPager = verticalViewPager;
        mLayoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return sliderItems.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        return view == ((LinearLayout) object);
    }

    @NonNull
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public Object instantiateItem(@NonNull final ViewGroup container, final int position) {
        View itemView = mLayoutInflater.inflate(R.layout.item_container,container,false);

        TextView texttv = itemView.findViewById(R.id.text_tv);
        TextView idtv = itemView.findViewById(R.id.id);

        //set data from array list to textview
        texttv.setText(text.get(position));
        idtv.setText(id.get(position));

        verticalViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                //save position value in newposition variable on page change
                newposition = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        verticalViewPager.setOnTouchListener(new View.OnTouchListener() {

            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                switch (motionEvent.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = motionEvent.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = motionEvent.getX();
                        float deltaX = x1-x2;
                        break;


                }
                return false;
            }
        });


        container.addView(itemView);
        return itemView;

    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        container.removeView((LinearLayout) object);
    }
}
