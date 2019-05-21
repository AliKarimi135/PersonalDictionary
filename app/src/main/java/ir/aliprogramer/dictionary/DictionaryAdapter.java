package ir.aliprogramer.dictionary;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import ir.aliprogramer.dictionary.database.Dictionary;

public class DictionaryAdapter extends RecyclerView.Adapter<DictionaryAdapter.ViewHolder> {
    List<Dictionary> dictionary;

    public DictionaryAdapter(List<Dictionary> dictionary) {
        this.dictionary = dictionary;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view= LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.recycler_view_item,viewGroup,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
            viewHolder.word.setText(dictionary.get(i).getWord());
            viewHolder.defintion.setText(dictionary.get(i).getDefinition());
    }

    @Override
    public int getItemCount() {
        return dictionary.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView word,defintion;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            word=itemView.findViewById(R.id.word);
            defintion=itemView.findViewById(R.id.definition);
        }
    }
}
