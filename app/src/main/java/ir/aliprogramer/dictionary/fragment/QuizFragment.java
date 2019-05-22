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
import java.util.Random;

import ir.aliprogramer.dictionary.DictionaryAdapter;
import ir.aliprogramer.dictionary.R;
import ir.aliprogramer.dictionary.database.AppDatabase;
import ir.aliprogramer.dictionary.database.Dictionary;

public class QuizFragment extends Fragment implements View.OnClickListener{
    CardView card1,card2,card3,card4;
    TextView question,answer1,answer2,answer3,answer4,scoreView;
    List<Dictionary> dictionary;
    boolean startQuiz;
    Random random;
    int guestionNumber,corectCard,score=0;
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
        scoreView=view.findViewById(R.id.score);
        card1.setOnClickListener(this);
        card2.setOnClickListener(this);
        card3.setOnClickListener(this);
        card4.setOnClickListener(this);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

       // loadDataFromDb();
        random=new Random();


    }
    public void loadDataFromDb() {
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
                   startQuiz=true;
                   scoreView.setText("0");
                   score=0;
                    initQuiz();
               }else {
                   startQuiz=false;
                   Toast.makeText(getContext(),"تعداد لغات برای شروع آزمون کم است.",Toast.LENGTH_LONG).show();
                   question.setText("تعداد لغات برای شروع آزمون کم است.");
               }
            }
        }
        getData getData=new getData();
        getData.execute();
    }


    @Override
    public void onClick(View view) {
        if(!startQuiz)
            return;
        switch (view.getId()){
            case R.id.card1:
                if(corectCard == 1)
                    score +=10;
                break;
            case R.id.card2:
                if(corectCard == 2)
                    score += 10;
                break;
            case R.id.card3:
                if(corectCard == 3)
                    score += 10;
                break;
            case R.id.card4:
                if(corectCard == 4)
                    score += 10;
                break;
        }
        scoreView.setText(score+"");
        initQuiz();
    }
    public void initQuiz() {
        if(!startQuiz)
            return;
        guestionNumber=random.nextInt(dictionary.size());
        corectCard=random.nextInt(4)+1;
        question.setText(dictionary.get(guestionNumber).getDefinition());

        switch (corectCard){
            case 1: answer1.setText(dictionary.get(guestionNumber).getWord());
                    answer2.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer3.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer4.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    break;
            case 2: answer2.setText(dictionary.get(guestionNumber).getWord());
                    answer1.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer3.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer4.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    break;
            case 3: answer3.setText(dictionary.get(guestionNumber).getWord());
                    answer2.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer1.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer4.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    break;
            case 4: answer4.setText(dictionary.get(guestionNumber).getWord());
                    answer2.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer3.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    answer1.setText(dictionary.get(random.nextInt(dictionary.size())).getWord());
                    break;
        }
    }
}
