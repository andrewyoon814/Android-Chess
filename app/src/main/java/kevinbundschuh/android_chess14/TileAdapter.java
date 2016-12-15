package kevinbundschuh.android_chess14;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

/**
 * Created by Andrew Yoon on 12/14/2016.
 */

public class TileAdapter extends BaseAdapter {

    private Context mContext;

    public TileAdapter(Context c) {
        mContext = c;
    }

    @Override
    public int getCount() {
        return 0;
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup paren) {
        ImageView imageView;
        // if it's not recycled, initialize some attributes
        if (convertView == null) {
            imageView = new ImageView(mContext);
            // Center crop image
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        } else {
            imageView = (ImageView) convertView;
        }
        // Set images into ImageView
        imageView.setImageResource(tiles[position]);
        return imageView;
    }

    public Integer[] tiles = {
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,
            R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop,R.drawable.bbishop
    };
}
