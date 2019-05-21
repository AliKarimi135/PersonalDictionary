package ir.aliprogramer.dictionary.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;

import ir.aliprogramer.dictionary.R;

public class SearchFragment extends Fragment {

    AutoCompleteTextView search;
    LinearLayout resultSearchUi;
    TextInputLayout word_layout,definition_layout;
    TextInputEditText word,definition;
    AppCompatButton edit,delete,no;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.search_fragment,container,false);
        search=view.findViewById(R.id.search_word);
        resultSearchUi=view.findViewById(R.id.result_search);
        word_layout=view.findViewById(R.id.word_layout);
        definition_layout=view.findViewById(R.id.definition_layout);
        word=view.findViewById(R.id.word);
        definition=view.findViewById(R.id.definition);
        edit=view.findViewById(R.id.edith);
        delete=view.findViewById(R.id.delete);
        no=view.findViewById(R.id.no);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
    }
}
