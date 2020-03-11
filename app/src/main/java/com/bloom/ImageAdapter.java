package com.bloom;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class ImageAdapter extends PagerAdapter {

    private Context mContext;
    private int[] ImageId = new int[] {R.drawable.f1,R.drawable.f2/*R.drawable.f3*/};
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
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setImageResource(ImageId[position]);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //SharedPreferences myPrefs;
                //myPrefs = getSharedPreferences("tagpage", MODE_PRIVATE);
                //SharedPreferences.Editor editor = myPrefs.edit();

                if(position == 0){
                    //editor.putString("flower_type", "f1");
                    //editor.commit();
                    Toast.makeText(mContext,"f1 selected",Toast.LENGTH_SHORT).show();
                }
                else if(position == 0){
                    //editor.putString("flower_type", "f2");
                    //editor.commit();
                    Toast.makeText(mContext,"f2 selected",Toast.LENGTH_SHORT).show();
                }
                else{
                    //editor.putString("flower_type", "f3");
                    //editor.commit();
                    Toast.makeText(mContext,"f3 selected",Toast.LENGTH_SHORT).show();
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
