package com.example.oksana.country;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.List;

public class CountryListAdapter extends BaseAdapter {
    private List<Country> countryList;
    private Context context;
    private LayoutInflater layoutInflater;
    private final DecimalFormat formatter = new DecimalFormat("#,###,###.##");


    public CountryListAdapter(Context context, List<Country> countryList) {
        this.context = context;
        this.countryList = countryList;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return countryList.size();
    }

    @Override
    public Object getItem(int i) {
        return countryList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        convertView = layoutInflater.inflate(R.layout.grid_item_layout, null);
        holder = new ViewHolder();
        holder.flagView = convertView.findViewById(R.id.imageView_flag);
        holder.countryNameView = convertView.findViewById(R.id.textView_countryName);
        holder.populationView = convertView.findViewById(R.id.textView_population);
        holder.areaView = convertView.findViewById(R.id.textView_area);
        holder.capitalView = convertView.findViewById(R.id.textView_capital);
        holder.subRegionView = convertView.findViewById(R.id.textView_subregion);
        convertView.setTag(holder);

        final Country country = countryList.get(i);
        holder.countryNameView.setText(country.getName());
        holder.populationView.setText(context.getString(R.string.population) + formatter.format(Double.parseDouble(country.getPopulation())));

        String area = country.getArea();
        if (!area.isEmpty() && !area.equals("null")) {
            area = formatter.format(Double.parseDouble(country.getArea()));
        }

        holder.areaView.setText(context.getString(R.string.area) + area + context.getString(R.string.km2));
        holder.capitalView.setText(context.getString(R.string.capital) + country.getCapital());
        holder.subRegionView.setText(context.getString(R.string.subregion) + country.getSubregion());

        holder.countryNameView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.textView_countryName: {
                        Intent intent = new Intent(context, DetailsActivity.class);
                        intent.putExtra("country", country.getName());
                        intent.putExtra("alpha2", country.getAlpha2Code());
                        context.startActivity(intent);
                        break;
                    }
                    default:
                        break;
                }
            }
        });

        String userAvatarUrl = country.getFlagDrawable();
        ImageUtils.fetchSvg(context, userAvatarUrl, holder.flagView);
        return convertView;
    }

    static class ViewHolder {
        ImageView flagView;
        TextView countryNameView;
        TextView populationView;
        TextView capitalView;
        TextView areaView;
        TextView subRegionView;
    }
}
