package com.abc.example.com.timing.chat;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.abc.example.com.timing.R;
import com.makeramen.roundedimageview.RoundedTransformationBuilder;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

public class datalist extends ArrayAdapter<String> {

    private ArrayList<String> name;
    private ArrayList<String> sender;
    private ArrayList<String> reciever;
    private ArrayList<String> tvi;
    private Activity context;
    int b = 0 ;

    public datalist(Activity context , String[] sender , String[] receiver ,  String[] name , String[] tvi) {
        super(context, R.layout.activity_chatlist, name);
        this.name=new ArrayList<String>();
        this.sender = new ArrayList<String>();
        this.reciever = new ArrayList<String>();
        this.tvi = new ArrayList<String>();
        this.context = context;
        this.name.addAll(Arrays.asList(name));
        Log.e("this.name", String.valueOf(this.name));
        this.sender.addAll(Arrays.asList(sender));
        Log.e("this.sender" , String.valueOf(this.sender));
        this.reciever.addAll(Arrays.asList(receiver));
        Log.e("this.receiver" , String.valueOf(this.reciever));
        this.tvi.addAll(Arrays.asList(tvi));
        Log.e("pp" , String.valueOf(this.tvi));
  //      Log.e("sn" , String.valueOf(this.sender));
  //     Log.e("rn" , String.valueOf(this.reciever));

    }

    public void addItem(String name , String sender , String receiver , String tvi)
    {
        this.name.add(name);
        this.sender.add(sender);
        this.reciever.add(receiver);
        this.tvi.add(tvi);
    }

    @Override
    public int getCount() {
        return name.size();
    }

    @Override
    public View getView(int position , View convertView , ViewGroup parent) {

        Log.e("mm", name.get(position));
        View listViewItem = null;
        SharedPreferences shared = context.getSharedPreferences("number", Context.MODE_PRIVATE);
        String num = shared.getString("unumber", null);
        LayoutInflater inflater = context.getLayoutInflater();
        if (tvi.get(position).equals("1")) {


            listViewItem = inflater.inflate(R.layout.activity_datalist3, null, true);
            final Transformation transformation = new RoundedTransformationBuilder()
                    .borderColor(Color.BLACK)
                    .borderWidthDp(0)
                    .cornerRadiusDp(10)
                    .oval(false)
                    .build();

            if (sender.get(position).equals(num))
            {
                ImageView mImageView=(ImageView) listViewItem.findViewById(R.id.i1);

                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SS/"+name.get(position);
                Log.e("path",path);
                Picasso.with(context).load(new File(path)).placeholder(R.drawable.edittext_corner).fit().transform(transformation).into(mImageView);

            }else
            {
                try {

                    ImageView mImageView=(ImageView) listViewItem.findViewById(R.id.i1);
                        String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SS/"+name.get(position);
                        Log.e("path",path);
                        Picasso.with(context).load(new File(path)).placeholder(R.drawable.edittext_corner).fit().transform(transformation).into(mImageView);




                }catch (Exception e)
                {

                    ImageView mImageView;
                    mImageView = (ImageView) listViewItem.findViewById(R.id.i1);
                    Picasso.with(context).load(name.get(position)).placeholder(R.drawable.edittext_corner).fit().transform(transformation).into(mImageView);
                    Log.e("image111" ,name.get(position));

                }

            }


         //   notifyDataSetChanged();
    }
        else
        {
            Log.e("chat" , "chat");
            if(sender.get(position).equals(num)) {
                Log.e("sender" , "sender");
                listViewItem = inflater.inflate(R.layout.activity_chatlist2, null, true);

                TextView textViewContact = (TextView) listViewItem.findViewById(R.id.t1);
                textViewContact.setText(name.get(position));

       //         notifyDataSetChanged();
            }

            else
            {
                Log.e("receiver" , "receiver");

                listViewItem = inflater.inflate(R.layout.activity_chatlist, null, true);
                TextView textViewContact = (TextView) listViewItem.findViewById(R.id.t1);
                textViewContact.setText(name.get(position));
     //         notifyDataSetChanged();
            }

        }



        Log.e("msg" , name.get(position));
        return listViewItem;

    }
}
