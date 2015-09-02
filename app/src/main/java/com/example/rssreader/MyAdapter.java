package com.example.rssreader;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * TODO ArrayAdapter を継承して、独自の Adapter をつくろう
 * @author KeishinYokomaku
 */
public class MyAdapter extends ArrayAdapter<FeedItem> {
	public MyAdapter(Context context, List<FeedItem> objects) {
		super(context, R.layout.list_item_feed, R.id.title, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView description = (TextView) view.findViewById(R.id.description);
		TextView category = (TextView) view.findViewById(R.id.category);
		TextView date = (TextView) view.findViewById(R.id.date);

		FeedItem item = getItem(position);

		title.setText(item.getTitle());
		description.setText(item.getDescription());
		category.setText(item.getCategory());
		date.setText(item.getDate());

		return view;
	}
}
