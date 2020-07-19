package com.example.recycler_view_renew;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentValues;
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
    AlertDialog dialog;

    String name="";
    int x = 0;
    String table;
    SQLiteDatabase db;
    MyDB helper=new MyDB(this);


    boolean repeat=false;

    CheckBean checkBean=new CheckBean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        table=helper.table_name; //資料表
        db=helper.getReadableDatabase(); //讀取資料庫

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

        for (int i=0;i<data.size();i++) {
            if (checkBean.getname().equals(data.get(i).getname())) {
                repeat = true;
                x = data.get(i).getamount() + 1;
                checkBean.setamount(x);
                data.set(i, checkBean);
            }
        }

            if (!repeat){
                data.add(checkBean);

            }
            else {
                repeat=false;

            }




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
            holder.amount.setText(checkBean.getamount()+" ");
            holder.amount.setOnClickListener(new View.OnClickListener() {    //當按下textview
                @Override
                public void onClick(final View view) {
                    checkBean = data.get(position);
                    View layout = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog, null);
                    final AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                    final EditText ed3 = layout.findViewById(R.id.edi3);
                    ed3.setText(""+checkBean.getamount());
                    builder.setMessage("喜歡的話給個好評吧！");
                    builder.setView(layout);   //設定layout


                    builder.setPositiveButton("確定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            int a = Integer.parseInt(ed3.getText().toString());
                            checkBean = data.get(position);
                            if (a == 0) {
                                data.remove(position);
                                Log.v("HI",""+position);
                                myadapter.notifyDataSetChanged();

                            } else {
                                checkBean.setamount(a);
                                data.set(position, checkBean);
                                myadapter.notifyDataSetChanged();
                            }


                        }
                    });
                    dialog = builder.create();
                    dialog.show();




                }
            });


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