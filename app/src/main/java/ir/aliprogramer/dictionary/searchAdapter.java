package ir.aliprogramer.dictionary;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ir.aliprogramer.dictionary.database.Dictionary;

public class searchAdapter extends ArrayAdapter<Dictionary> {
    Context context;
    int resource, textViewResourceId;
    List<Dictionary> items, tempItems, suggestions;

    public searchAdapter(Context context, int resource, int textViewResourceId, List<Dictionary> items) {
        super(context, resource, textViewResourceId, items);
        this.context = context;
        this.resource = resource;
        this.textViewResourceId = textViewResourceId;
        this.items = items;
        tempItems = new ArrayList<Dictionary>(items); // this makes the difference.
        suggestions = new ArrayList<Dictionary>();
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (convertView == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.search_row_layout, parent, false);
        }
        Dictionary dicti = items.get(position);
        if (dicti != null) {
            TextView lblName = (TextView) view.findViewById(R.id.lbl_name);
            if (lblName != null)
                lblName.setText(dicti.getWord() +"\t\t\t\t"+ dicti.getDefinition());

        }
        return view;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    /**
     * Custom Filter implementation for custom suggestions we provide.
     */
    Filter nameFilter = new Filter() {
        @Override
        public CharSequence convertResultToString(Object resultValue) {
            String str = ((Dictionary) resultValue).getWord();
            Log.d("size3",str);
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Dictionary dictionary : tempItems) {
                    if (dictionary.getWord().toLowerCase().contains(constraint.toString().toLowerCase()) ||
                                            dictionary.getDefinition().contains(constraint.toString())) {
                        suggestions.add(dictionary);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Dictionary> filterList = (ArrayList<Dictionary>) results.values;
            if (results != null && results.count > 0) {
                clear();
                for (Dictionary dictionary : filterList) {
                    add(dictionary);
                    notifyDataSetChanged();
                }
            }
        }
    };
}
