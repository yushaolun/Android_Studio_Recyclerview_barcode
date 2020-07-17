package com.example.recycler_view_renew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.TextView;

import java.util.LinkedList;

public class MainActivity extends AppCompatActivity {
    RecyclerView.Adapter myadapter;
    RecyclerView.LayoutManager layoutManager;
    RecyclerView recyclerView;
    LinkedList <CheckBean> data=new LinkedList<>();
    CheckBox ch_all;
    CheckBox ch1;
    EditText ed1;
    String name="";



    CheckBean checkBean=new CheckBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = (RecyclerView) findViewById(R.id.recycle);
        recyclerView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        myadapter = new MyAdapter();
        recyclerView.setAdapter(myadapter);
        initial();


    }
        private void initial() {

            ed1=findViewById(R.id.ed1);
            ed1.setOnKeyListener(new ALOnKeyListener());
            ch_all = findViewById(R.id.check_all);
            ch1 = findViewById(R.id.checkBox);
            ch_all.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                        for (int i = 0; i < data.size(); i++) {
                            data.get(i).setChecked(ch_all.isChecked());
                        }

                        myadapter.notifyDataSetChanged();

                }
            });


        }

    public void addata(String name) {

        CheckBean checkBean = new CheckBean();
        checkBean.setname(name);
        checkBean.setChecked(false);


        data.add(checkBean);
        myadapter.notifyDataSetChanged();

    }

    public void delete(View view) {
        for (int i=0;i<data.size();i++) {
             if (data.get(i).ischeck()!=false){
                 data.remove(i);
                 i--;
             }

        }
        ch_all.setChecked(false);
        myadapter.notifyDataSetChanged();
        recyclerView.setAdapter(myadapter);
    }

    private class ALOnKeyListener implements View.OnKeyListener {
        @Override

        public boolean onKey(View v, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_UP) {


                name = ed1.getEditableText().toString();



                if (!name.equals("")) {
                    addata(name);
                }

                ed1.setText("");
                return true;
            }

            return false;
        }


    }

    public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {


        @NonNull
        @Override
        public MyAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View itemview = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);  //取得xml layout

            MyViewHolder vh = new MyViewHolder(itemview);


            return vh;
        }

        @Override
        public void onBindViewHolder(@NonNull final MyAdapter.MyViewHolder holder, final int position) {


            checkBean = data.get(position);
            holder.comment.setText(checkBean.getname());


            holder.che.setChecked(checkBean.ischeck());
            holder.che.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {  //onclick
                @Override
                public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                    Log.v("ch",""+position+"  "+b);
                    CheckBean checkBean = new CheckBean();
                    checkBean.setChecked(b);
                    data.set(position,checkBean);
                }
            });






        }

        @Override
        public int getItemCount() {
            return data.size();
        }

        class MyViewHolder extends RecyclerView.ViewHolder {

            TextView amount;
            TextView comment;
            CheckBox che;
            View itemview;


            public MyViewHolder(View view) {
                super(view);
                itemview = view;
                comment = itemview.findViewById(R.id.tex1);
                amount=itemview.findViewById(R.id.tex2);
                che = itemview.findViewById(R.id.checkBox);


            }


        }


    }

}