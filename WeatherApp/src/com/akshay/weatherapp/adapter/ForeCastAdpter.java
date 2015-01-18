package com.akshay.weatherapp.adapter;

import java.text.DecimalFormat;
import java.util.List;

import com.akshay.weatherapp.R;
import com.akshay.weatherapp.core.models.ForeCast;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

public class ForeCastAdpter extends ArrayAdapter<ForeCast> {

	Context context;
	int resource, textViewResource;
	List<ForeCast> mList;

	public ForeCastAdpter(Context context, int resource,
			int textViewResourceId, List<ForeCast> mList) {
		super(context, resource, textViewResourceId, mList);
		this.context = context;
		this.textViewResource = textViewResourceId;
		this.resource = resource;
		this.mList = mList;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = convertView;
		ViewHolder holder = null;
		String tempStr;
		if (view == null) {
			LayoutInflater inflater = (LayoutInflater) context
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.row_forecast, parent, false);
			holder = new ViewHolder(view);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}
		holder.lblDate.setText(mList.get(position).getDateTime());
		DecimalFormat decimalFormat = new DecimalFormat("#0.00");
		tempStr = decimalFormat.format(mList.get(position).getDayTemp())
				+ " \u2103";
		holder.lblDayTemp.setText(tempStr);
		tempStr = decimalFormat.format(mList.get(position).getEveTemp())
				+ " \u2103";
		holder.lblEveTemp.setText(tempStr);
		tempStr = decimalFormat.format(mList.get(position).getMornTemp())
				+ " \u2103";
		holder.lblMornTemp.setText(tempStr);
		tempStr = decimalFormat.format(mList.get(position).getEveTemp())
				+ " \u2103";
		holder.lblNightTemp.setText(tempStr);

		holder.lblHumidity.setText(mList.get(position).getHumidity() + " %");
		holder.lblWindSpeed
				.setText(mList.get(position).getWindSpeed() + " mph");
		holder.lblWindDegree.setText(mList.get(position).getWindDegree()
				+ " \u00b0");
		holder.lblPressure.setText(mList.get(position).getPressure() + " mb");
		return view;
	}

	static class ViewHolder {
		TextView lblDayTemp, lblNightTemp, lblEveTemp, lblMornTemp, lblDate,
				lblPressure, lblWindSpeed, lblWindDegree, lblHumidity;

		public ViewHolder(View view) {
			lblDate = (TextView) view.findViewById(R.id.lbl_forecast_date);
			lblDayTemp = (TextView) view.findViewById(R.id.lbl_day);
			lblMornTemp = (TextView) view.findViewById(R.id.lbl_morn);
			lblEveTemp = (TextView) view.findViewById(R.id.lbl_even);
			lblNightTemp = (TextView) view.findViewById(R.id.lbl_night);

			lblPressure = (TextView) view.findViewById(R.id.lbl_daily_pressure);
			lblHumidity = (TextView) view.findViewById(R.id.lbl_daily_humidity);
			lblWindSpeed = (TextView) view.findViewById(R.id.lbl_daily_wind);
			lblWindDegree = (TextView) view
					.findViewById(R.id.lbl_daily_wind_degree);
		}
	}
}
