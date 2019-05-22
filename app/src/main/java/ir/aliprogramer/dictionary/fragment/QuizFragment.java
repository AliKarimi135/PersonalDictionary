package ir.aliprogramer.dictionary.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import ir.aliprogramer.dictionary.DictionaryAdapter;
import ir.aliprogramer.dictionary.R;
import ir.aliprogramer.dictionary.database.AppDatabase;
import ir.aliprogramer.dictionary.database.Dictionary;

public class QuizFragment extends Fragment {
    CardView card1,card2,card3,card4;
    TextView question,answer1,answer2,answer3,answer4;
    List<Dictionary> dictionary;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.quiz_fragment,container,false);
        question=view.findViewById(R.id.question);
        card1=view.findViewById(R.id.card1);
        card2=view.findViewById(R.id.card2);
        card3=view.findViewById(R.id.card3);
        card4=view.findViewById(R.id.card4);
        answer1=view.findViewById(R.id.answer1);
        answer2=view.findViewById(R.id.answer2);
        answer3=view.findViewById(R.id.answer3);
        answer4=view.findViewById(R.id.answer4);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        loadDataFromDb();
    }
    private void loadDataFromDb() {
        class getData extends AsyncTask<Void,Void,Integer> {

            @Override
            protected Integer doInBackground(Void... voids) {
                dictionary= AppDatabase.getInstance(getContext()).dao().getAllWords();
                if(dictionary.size()>=4)
                    return 1;
                return 0;
            }

            @Override
            protected void onPostExecute(Integer result) {
                super.onPostExecute(result);
               if(result==1){
                   startQuiz();
               }else {
                   Toast.makeText(getContext(),"تعداد لغات برای شروع آزمون کم است.",Toast.LENGTH_LONG).show();
               }
            }
        }
        getData getData=new getData();
        getData.execute();
    }

    private void startQuiz() {

    }
}
