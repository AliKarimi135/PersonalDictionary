package ir.aliprogramer.dictionary;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ir.aliprogramer.dictionary.database.AppDatabase;
import ir.aliprogramer.dictionary.database.Dictionary;
import ir.aliprogramer.dictionary.fragment.AddWordFragment;
import ir.aliprogramer.dictionary.fragment.SearchFragment;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener{
    Toolbar toolbar;
    TextView title;
    FrameLayout frameLayout;
    RecyclerView recyclerView;
    FragmentManager fragmentManager;
    BottomNavigationView bottomNavigationView;
    FragmentTransaction transaction;
    SearchFragment searchFragment;
    AddWordFragment wordFragment;
    List<Dictionary>  dictionary;
    DictionaryAdapter dictionaryAdapter;
    //MutableLiveData<List<Dictionary>>  liveData=new MutableLiveData<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar=findViewById(R.id.toolbar);
        frameLayout=findViewById(R.id.frame_layout);
        title=toolbar.findViewById(R.id.title);
        title.setText(getString(R.string.app_name));
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView=findViewById(R.id.recyclerView);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        DividerItemDecoration dividerItemDecoration=new DividerItemDecoration(recyclerView.getContext(),new LinearLayoutManager(this).getOrientation());
        recyclerView.addItemDecoration(dividerItemDecoration);



        bottomNavigationView=findViewById(R.id.buttomMenu);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        fragmentManager=getSupportFragmentManager();
        transaction=fragmentManager.beginTransaction();

        wordFragment=new AddWordFragment();
        transaction.add(R.id.frame_layout,wordFragment,"AddWordFragment");
        transaction.addToBackStack("AddWordFragment");
        transaction.hide(wordFragment);


        searchFragment=new SearchFragment();
        transaction.add(R.id.frame_layout,searchFragment,"SearchFragment");
        transaction.addToBackStack("SearchFragment");
        transaction.hide(searchFragment);

       /* SearchFragment sf=new SearchFragment();
        transaction.add(R.id.frame_layout,sf,"SearchFragment2");
        transaction.addToBackStack("SearchFragment2");
        transaction.hide(sf);*/

        transaction.commit();


        loadDataFromDb();


    }


    private void loadDataFromDb() {
         class getData extends AsyncTask<Void,Void,Integer> {

             @Override
             protected Integer doInBackground(Void... voids) {
                 dictionary= AppDatabase.getInstance(getApplicationContext()).dao().getAllWords();
                 dictionaryAdapter=new DictionaryAdapter(dictionary);
                 recyclerView.setAdapter(dictionaryAdapter);
                 if(dictionary==null)
                     return 0;
                 return 1;
             }

             @Override
             protected void onPostExecute(Integer result) {
                 super.onPostExecute(result);
                 if(result==0){
                     Toast.makeText(getApplicationContext(),"شما لغتی ذخیره نکردید.",Toast.LENGTH_LONG).show();
                 }else{
                     Log.d("sizeDictionary",dictionary.size()+" ");
                 }
             }
         }
         getData getData=new getData();
         getData.execute();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        transaction=fragmentManager.beginTransaction();
        frameLayout.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
        switch (menuItem.getItemId()){
            case R.id.search:
                            transaction.hide(wordFragment);
                            transaction.show(searchFragment);
                             break;
            case R.id.quiz:
                        transaction.hide(wordFragment);
                        transaction.show(searchFragment);
                            break;
            case R.id.add:
                        transaction.hide(searchFragment);
                        transaction.show(wordFragment);
                        break;
        }
        transaction.commit();
        Log.d("count",fragmentManager.getBackStackEntryCount()+"");
        return true;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==android.R.id.home) {
            recyclerView.setVisibility(View.VISIBLE);
            frameLayout.setVisibility(View.GONE);
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     *
     * @param status
     * status add or update or delete data to change ui data
     */
    public void updateRecycler(int status,Dictionary input){
        if(status==1){
            dictionary.add(0,input);
            dictionaryAdapter.notifyDataSetChanged();
        }else if(status==2){

        }else {

        }
    }
}
