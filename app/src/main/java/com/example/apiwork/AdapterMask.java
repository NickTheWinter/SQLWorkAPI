package com.example.apiwork;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class AdapterMask  extends BaseAdapter {

    private Context mContext;
    List<Mask> maskList;

    private List<Mask> mOriginalValues;
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
    public Filter getFilter() {
        Filter filter = new Filter() {

            @SuppressWarnings("unchecked")
            @Override
            protected void publishResults(CharSequence constraint,FilterResults results) {

                maskList = (ArrayList<Mask>) results.values; // has the filtered values
                notifyDataSetChanged();  // notifies the data with new filtered values
            }

            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                FilterResults results = new FilterResults();        // Holds the results of a filtering operation in values
                ArrayList<Mask> FilteredArrList = new ArrayList<Mask>();

                if (mOriginalValues == null) {
                    mOriginalValues = new ArrayList<Mask>(maskList); // saves the original data in mOriginalValues
                }
                if (constraint == null || constraint.length() == 0) {

                    // set the Original result to return
                    results.count = mOriginalValues.size();
                    results.values = mOriginalValues;
                } else {
                    constraint = constraint.toString().toLowerCase();
                    for (int i = 0; i < mOriginalValues.size(); i++) {
                        String data = mOriginalValues.get(i).getAirline_name();
                        if (data.toLowerCase().startsWith(constraint.toString())) {
                            FilteredArrList.add(new Mask(mOriginalValues.get(i).getAirline_id(),mOriginalValues.get(i).getAirline_name(),
                                    mOriginalValues.get(i).getAirline_website(),mOriginalValues.get(i).getImage()));
                        }
                    }

                    results.count = FilteredArrList.size();
                    results.values = FilteredArrList;
                }
                return results;
            }
        };
        return filter;
    }
}

