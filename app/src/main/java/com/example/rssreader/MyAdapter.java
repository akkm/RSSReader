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
public class MyAdapter extends ArrayAdapter<Person> {
	public MyAdapter(Context context, List<Person> objects) {
		super(context, R.layout.list_item_person, R.id.text1, objects);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = super.getView(position, convertView, parent);
		TextView text1 = (TextView) view.findViewById(R.id.text1);
		TextView text2 = (TextView) view.findViewById(R.id.text2);
		Person person = getItem(position);
		text1.setText(person.getName() + "(" + person.getAge() + ")");
		text2.setText(person.getComment());
		return view;
	}
}
