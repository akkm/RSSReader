package com.example.rssreader;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.rssreader.db.FeedItemEntity;

import java.util.List;

/**
 * TODO ArrayAdapter を継承して、独自の Adapter をつくろう
 * @author KeishinYokomaku
 */
public class MyAdapter extends ArrayAdapter<FeedItemEntity> {
	public MyAdapter(Context context, List<FeedItemEntity> objects) {
		super(context, R.layout.list_item_feed, R.id.title, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView title = (TextView) view.findViewById(R.id.title);
		TextView description = (TextView) view.findViewById(R.id.description);
		TextView category = (TextView) view.findViewById(R.id.category);
		TextView date = (TextView) view.findViewById(R.id.date);

		final FeedItem item = getItem(position);

		title.setText(item.getTitle());
		description.setText(item.getDescription());
		category.setText(item.getCategory());
		date.setText(item.getDate());
		view.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				Context context = v.getContext();
				Intent i = new Intent(context, FeedContentActivity.class);
				i.putExtra(FeedContentActivity.URI_PARAM, item.getUriString());
				context.startActivity(i);
			}
		});

		return view;
	}
}
