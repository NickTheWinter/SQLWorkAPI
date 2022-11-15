package com.example.apiwork;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

public class AdapterMask  extends BaseAdapter {

    private Context mContext;
    List<Mask> maskList;

    public AdapterMask(Context mContext, List<Mask> listProduct) {
        this.mContext = mContext;
        this.maskList = listProduct;
    }

    @Override
    public int getCount() {
        return maskList.size();
    }

    @Override
    public Object getItem(int i) {
        return maskList.get(i);
    }

    @Override
    public long getItemId(int i)
    {
        return maskList.get(i).getAirline_id();
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup)
    {
        View v = View.inflate(mContext,R.layout.item_layuot,null);

        TextView AirlineName = v.findViewById(R.id.AirlineName);
        TextView AirlineWebSite = v.findViewById(R.id.AirlineWebsite);
        ImageView imageView = v.findViewById(R.id.imageView);

        Mask mask = maskList.get(i);

        AirlineName.setText(mask.getAirline_name());
        AirlineWebSite.setText(mask.getAirline_website());
        imageView.setImageBitmap(mask.getAirlineImage(mask.getImage()));


        return v;
    }
}

