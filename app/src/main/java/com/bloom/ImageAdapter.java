package com.bloom;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import static android.content.Context.MODE_PRIVATE;

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private int[] ImageId = new int[] {R.drawable.f1,R.drawable.f2,R.drawable.f3};
    ImageAdapter(Context context){
        mContext = context;
    }
    @Override
    public int getCount() {
        return ImageId.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(final ViewGroup container, final int position) {
        final ImageView imageView = new ImageView(mContext);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageView.setImageResource(ImageId[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences mPrefs;
                mPrefs = view.getContext().getSharedPreferences("tagpage", MODE_PRIVATE);
                SharedPreferences.Editor editor = mPrefs.edit();

                if (position == 0) {
                    editor.putString("flower_type", "f1");
                    editor.commit();
                    Toast.makeText(mContext,"sunflower selected",Toast.LENGTH_SHORT).show();
                } else if (position == 1) {
                    editor.putString("flower_type", "f2");
                    editor.commit();
                    Toast.makeText(mContext,"rose selected",Toast.LENGTH_SHORT).show();
                } else if (position == 2) {
                    editor.putString("flower_type", "f3");
                    editor.commit();
                    Toast.makeText(mContext,"lily selected",Toast.LENGTH_SHORT).show();
                } else {
                    editor.putString("flower_type", "f1");
                    editor.commit();
                    //Toast.makeText(mContext,"YOU MUST SELECT FLOWER TYPE !!!",Toast.LENGTH_SHORT).show();
                }
            }
        });
        container.addView(imageView, 0);
        return imageView;
    }


    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((ImageView) object);
    }
}
