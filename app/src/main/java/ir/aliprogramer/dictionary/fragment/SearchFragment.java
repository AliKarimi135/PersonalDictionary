package ir.aliprogramer.dictionary.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.LinearLayout;
import android.widget.ListAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.aliprogramer.dictionary.MainActivity;
import ir.aliprogramer.dictionary.R;
import ir.aliprogramer.dictionary.database.AppDatabase;
import ir.aliprogramer.dictionary.database.Dictionary;
import ir.aliprogramer.dictionary.searchAdapter;

public class SearchFragment extends Fragment {

    AutoCompleteTextView search;
    LinearLayout resultSearchUi;
    TextInputLayout word_layout,definition_layout;
    TextInputEditText word,definition;
    AppCompatButton edit,delete,no;
    List<Dictionary> dictionary;
    searchAdapter adapter;
    Dictionary resultSearch,updateInput,input;
    int positionItem=0;
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
        //getData();
        search.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int pos, long id) {
                updateInput= (Dictionary) adapterView.getItemAtPosition(pos);
                clearInput();
                resultSearchUi.setVisibility(View.VISIBLE);
                word.setText(updateInput.getWord());
                definition.setText(updateInput.getDefinition());
                positionItem=pos;
            }
        });
        edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordSt,defintionSt;
                wordSt=word.getText().toString().trim().toLowerCase();
                defintionSt=definition.getText().toString().trim();
                if(!checkInput(wordSt,defintionSt))
                    return;
                input=new Dictionary(wordSt,defintionSt);
                input.setId(updateInput.getId());
                updateDictionary();
                clearInput();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteItemFromDB();
                clearInput();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clearInput();
            }
        });
    }

    private void deleteItemFromDB() {
        class deleteItemFromDB extends AsyncTask<Void,Void,Void>{

            @Override
            protected Void doInBackground(Void... voids) {
                AppDatabase.getInstance(getContext()).dao().deleteWord(updateInput.getId());
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                updateInput.setId(positionItem);
                Toast.makeText(getContext(),"لغات با موفقیت حذف شد",Toast.LENGTH_LONG).show();
                ((MainActivity)getContext()).updateRecycler(2,updateInput);
            }
        }
        deleteItemFromDB deleteItem=new deleteItemFromDB();
        deleteItem.execute();
    }

    private void updateDictionary() {
        class updateDictionary extends AsyncTask<Void,Void,Integer>{

            @Override
            protected Integer doInBackground(Void... voids) {
                /*resultSearch= AppDatabase.getInstance(getContext()).dao().findWord(word.getText().toString().trim().toLowerCase());
                if(resultSearch==null) {
                    AppDatabase.getInstance(getContext()).dao().update(input);
                    return 1;
                }
                return 0;*/
                AppDatabase.getInstance(getContext()).dao().update(input);
                return 1;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if(result==1){
                    Toast.makeText(getContext(),"لغات با موفقیت ویرایش شد",Toast.LENGTH_LONG).show();
                    ((MainActivity)getContext()).updateRecycler(positionItem,input);
                    clearInput();
                }else{
                    Toast.makeText(getContext(),"لغت: "+resultSearch.getWord() +"\n" + " با معنی : "+resultSearch.getDefinition()+"\n" + " قبلا ثبت کرده اید.",Toast.LENGTH_LONG).show();
                }
            }
        }
        updateDictionary updateDic=new updateDictionary();
        updateDic.execute();
    }

    public void getData() {
        class getData extends AsyncTask<Void,Void,Void> {

            @Override
            protected Void doInBackground(Void... voids) {
                dictionary= AppDatabase.getInstance(getContext()).dao().getAllWords();
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                adapter=new searchAdapter(getContext(),R.layout.activity_main,R.id.lbl_name,dictionary);
                search.setAdapter(adapter);
            }
        }
        getData data=new getData();
        data.execute();
    }
    boolean checkInput(String wordSt,String defintionSt){
        //clear message in view
        word_layout.setError("");
        definition_layout.setError("");
        if(wordSt.isEmpty()){
            word_layout.setError("لطفا لغت  را وارد کنید.");
        }
        if(defintionSt.isEmpty()){
            definition_layout.setError("لطفا معنی لغت  را وارد کنید.");
        }

        if(wordSt.isEmpty() || defintionSt.isEmpty())
            return false;

        return true;
    }
    public void clearInput(){
        word.setText("");
        word_layout.setError("");
        definition.setText("");
        definition_layout.setError("");
        search.setText("");
        resultSearchUi.setVisibility(View.GONE);
    }
}
