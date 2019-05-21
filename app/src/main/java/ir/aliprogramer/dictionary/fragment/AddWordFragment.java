package ir.aliprogramer.dictionary.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.TextInputEditText;
import android.support.design.widget.TextInputLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.AppCompatButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import ir.aliprogramer.dictionary.MainActivity;
import ir.aliprogramer.dictionary.R;
import ir.aliprogramer.dictionary.database.AppDatabase;
import ir.aliprogramer.dictionary.database.Dictionary;

public class AddWordFragment extends Fragment {
    TextInputLayout word_layout,definition_layout;
    TextInputEditText word,definition;
    AppCompatButton ok;
    AppCompatButton no;
    Dictionary resultSearch;
    Dictionary input;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.add_word_fragment,container,false);
        word_layout=view.findViewById(R.id.word_layout);
        definition_layout=view.findViewById(R.id.definition_layout);
        word=view.findViewById(R.id.word);
        definition=view.findViewById(R.id.definition);
        ok=view.findViewById(R.id.ok);
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
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String wordSt,defintionSt;
                wordSt=word.getText().toString().trim();
                defintionSt=definition.getText().toString().trim();
                if(!checkInput(wordSt,defintionSt))
                    return;
                input=new Dictionary(wordSt,defintionSt);
                saveWord();

            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               clearInput();
            }
        });
    }
        void clearInput(){
            word.setText("");
            word_layout.setError("");
            definition.setText("");
            definition_layout.setError("");
        }
    private void saveWord() {
        class  saveWord extends AsyncTask<Void,Void,Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                resultSearch= AppDatabase.getInstance(getContext()).dao().findWord(input.getWord());
                if(resultSearch==null) {
                    AppDatabase.getInstance(getContext()).dao().insertWord(input);
                    return 1;
                }
                return 0;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
                if(result==1){
                    Toast.makeText(getContext(),"لغات با موفقیت ذخیره شد",Toast.LENGTH_LONG).show();
                    ((MainActivity)getContext()).updateRecycler(1,input);
                    clearInput();
                }else{
                    Toast.makeText(getContext(),"لغت: "+resultSearch.getWord() +"\n" + " با معنی : "+resultSearch.getDefinition()+"\n" + " قبلا ثبت کرده اید.",Toast.LENGTH_LONG).show();
                }
            }
        }
        saveWord sw=new saveWord();
        sw.execute();
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
}
