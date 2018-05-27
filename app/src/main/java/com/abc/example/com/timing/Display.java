package com.abc.example.com.timing;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.graphics.Color;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.abc.example.com.timing.chat.Databasehandler;
import com.abc.example.com.timing.chat.Displaydate;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.List;

public class Display extends AppCompatActivity {
    SwipeMenuListView l1;
    Button b1;
    EditText e1;
    datalist datalist ;
    public int flag=0;
    public boolean fla = true;
    DATABASEHANDLER db;
    String lastdate;
    RequestQueue queue;
    String sender;
    private List<ApplicationInfo> mAppList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display);
        l1 = (SwipeMenuListView) findViewById(R.id.l1);
        db = new DATABASEHANDLER(Display.this);
        //To task in this. Can do network operation Also
        SharedPreferences shared = getSharedPreferences("number", Context.MODE_PRIVATE);
        sender = shared.getString("unumber", null);

        SwipeMenuCreator creator = new SwipeMenuCreator() {
            @Override
            public void create(SwipeMenu menu) {
                // create "open" item

//                Log.e("dddd","sdfsfs");
                SwipeMenuItem openItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                openItem.setBackground(R.drawable.button_bg_round);

                // set item width
                openItem.setWidth(120);

                // set item title

                openItem.setTitle("Open");
                // set item title fontsize
                openItem.setIcon(R.drawable.swipe_delete);
                // set item title font color
                openItem.setTitleColor(Color.WHITE);

                // add to menu
                menu.addMenuItem(openItem);


                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                // set item width
                deleteItem.setWidth(120);
                // set a icon
                deleteItem.setBackground(R.drawable.button_bg_round);

                deleteItem.setIcon(R.drawable.ic_notifications_black_24dp);
                // add to menu
                menu.addMenuItem(deleteItem);

                SwipeMenuItem Space1 = new SwipeMenuItem(getApplicationContext());

               // Space.setWidth(20);
               // Space.setTitle("ff");
              //  Space.setTitleColor(Color.WHITE);
               // Space.setBackground(R.drawable.button_space);
              //  menu.addMenuItem(Space);


            }
        };

// set creator
        l1.setMenuCreator(creator);
        l1.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                String i  = datalist.getItem(position);

                switch (index) {
                    case 0:
                        // open

                        Toast.makeText(getApplicationContext() , i , Toast.LENGTH_LONG).show();
                        DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                       db.delete(i);
                         fla=true;
                        break;
                    case 1:
                        Toast.makeText(getApplicationContext() , String.valueOf(index) , Toast.LENGTH_LONG).show();

                        // delete
                        break;
                }
                // false : close the menu; true : not close the menu
                return false;
            }
        });

  //      Log.d("check","Check Run" );
        final Handler handler = new Handler();
        Runnable runable = new Runnable() {

            @Override
            public void run() {
                UpdateDetails();
                friends();
                getdata();
                display();
                //call the function
                //also call the same runnable
                handler.postDelayed(this, 1000);
            }
        };
        handler.postDelayed(runable, 10);
    }

    public void display() {

        try {
            if (flag == 0) {
                List<Registration> contacts = db.getAllData();
                String[] mes = new String[contacts.size()];
                String[] notification = new String[contacts.size()];
                String[] date = new String[contacts.size()];
                int i = 0;
                for (Registration cn : contacts) {
                    mes[i] = cn.getPhone();
                   // Log.e("Mess", cn.getPhone());
                    notification[i] = cn.getCreated_date();
                    //Log.e("dadd", cn.getCreated_date());
                    DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                    db.updateflag1(cn.getPhone());
                    Log.e("i" , String.valueOf(i));
                    i++;
                    if(i==contacts.size())break;
                }
                flag = 1;

    //            Log.e("JJJJ", "Helloo");
                datalist = new datalist(Display.this, mes, notification);
                //l1.setItemsCanFocus(true);
                l1.setAdapter(datalist);

                l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        String name = parent.getItemAtPosition(position).toString();
                        Toast.makeText(Display.this, name, Toast.LENGTH_LONG).show();
                        Intent i = new Intent(getApplicationContext(), com.abc.example.com.timing.chat.Displaydate.class);
                        i.putExtra("num", name);
                        Intent j = new Intent(getApplicationContext() , com.abc.example.com.timing.chat.Databasehandler.class);
                        j.putExtra("num" , name);
                        startActivity(i);
                    }
                });
            } else {
      //          Log.e("iii" , "ddd");
                List<Registration> contacts = db.getAllbyData();
                int i = 0;
                for (Registration cn : contacts) {
                    //datalist.addItem(cn.getPhone() , cn.getCreated_date());
        //            Log.e("LOG", cn.getPhone());
                    DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                    db.updateflag1(cn.getPhone());
                    //l1.setAdapter(datalist);
                    flag = 0;
                }
                //       datalist.notifyDataSetChanged();

            }
        } catch (Exception e) {

        }
    }
      //  l1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        //    @Override
          //  public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            //    String name = parent.getItemAtPosition(position).toString();
     //           Toast.makeText(Display.this, name, Toast.LENGTH_LONG).show();

       //     }
       // });



        /*
        int firstPosition = l1.getFirstVisiblePosition();
        l1.setSelection(firstPosition);
        l1.smoothScrollToPosition(firstPosition);
        l1.setSelection(datalist.getCount()-1);
        */


    public  void UpdateDetails()
    {
        if (queue == null) {
            queue = Volley.newRequestQueue(Display.this);
        }

        String url1 = "http://192.168.1.171/updatedetails.php?sender="+sender;
        //Log.e("UpdateDetails" , url1);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ar = new JSONArray(response);
                            String sender_num[] = new String[ar.length()];
                            String receiver_num[] = new String[ar.length()];
                            String Date[] = new String[ar.length()];
                            String Mes[] = new String[ar.length()];
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject out = ar.getJSONObject(i);
                                sender_num[i] = out.getString("sender_number");
                                receiver_num[i] = out.getString("receiver_number");
                                Date[i] = out.getString("Date");
                                Mes[i] = out.getString("message");
       //                         Log.e("Date Display",Date[i]);
     //                           Log.e("Mes" , Mes[i]);

                                if(sender_num[i].equals("9909856507"))
                                {
                                    DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                                    db.update1(receiver_num[i] ,  Mes[i] , Date[i]);
                                }
                                else if(receiver_num[i].equals("9909856507"))
                                {
                                    DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                                    db.update1(sender_num[i] ,  Mes[i], Date[i]);
                                }
                            }
                        } catch (JSONException ex) {
        ///                    Log.e("result", "Error:=" + ex.getMessage());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }


        });
        stringRequest.setShouldCache(false);
        stringRequest.setRetryPolicy(
                new DefaultRetryPolicy(
                        20000,//time to wait for it in this case 20s
                        20,//tryies in case of error
                        DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
                )
        );
        queue.add(stringRequest);

    }


    private void friends(){

        if (queue == null) {
            queue = Volley.newRequestQueue(getApplicationContext());
        }
        //SharedPreferences shared = getSharedPreferences("number", Context.MODE_PRIVATE);
        //num = shared.getString("unumber", null);
        String url = "http://192.168.1.171/friend.php?uname="+sender;
       // Log.e("url",url);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ar = new JSONArray(response);
                            String number[] = new String[ar.length()];
                            String notification[] = new String[ar.length()];
                            String flag[] = new String[ar.length()];
        //                    Log.e("Checking", "Test");
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject out = ar.getJSONObject(i);
                                number[i] = out.getString("name");
                                notification[i] = out.getString("total");
                                flag[i] = out.getString("flag").trim();

        //                        Log.e("nameee", number[i]);
         //                       Log.e("notification", notification[i]);
                                DATABASEHANDLER db = new DATABASEHANDLER(Display.this);
                                Registration obj = new Registration(number[i], "0" , "0" , "0" , "0");
                                db.addDATA(obj);

                                if (Integer.parseInt(flag[i]) == 0) {
        //                            Log.e("sss", "sahil");
                                    db.update(number[i], notification[i]);
                                    db.updateflag0(number[i]);
                                    Display ob = new Display();

                                } else {
                                    Toast.makeText(Display.this, "NO NOTIFICATIONS", Toast.LENGTH_SHORT).show();
                                }
                            }
                        } catch (JSONException ex) {
         //                   Log.e("result", "Error:=" + ex.getMessage());
                        }

                    }

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

    }

    public void getdata(){
        String url1 = "http://192.168.1.171/receivemessage.php?sender=" + sender ;
        Log.e("urll", url1);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, url1,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray ar = new JSONArray(response);
                            String get_sender_phone[] = new String[ar.length()];
                            String get_receiver_phone[] = new String[ar.length()];
                            String getmes[] = new String[ar.length()];
                            String getdate[] = new String[ar.length()];
                            String gettvi[] = new String[ar.length()];
                            for (int i = 0; i < ar.length(); i++) {
                                JSONObject out = ar.getJSONObject(i);
                                gettvi[i] = out.getString("tvi");


                                get_sender_phone[i] = out.getString("sender_number");
                                Log.e("getsenderphone" , out.getString("sender_number"));
                                get_receiver_phone[i] = out.getString("receiver_number");
                                Log.e("getreceiverphone" , out.getString("receiver_number"));
                                getmes[i] = out.getString("message");
                                Log.e("getmes", getmes[i] = out.getString("message"));
                                getdate[i] = out.getString("Date");
                                Log.e("getdate", getdate[i] = out.getString("Date"));
                                if(Integer.parseInt(gettvi[i])==1)
                                {
                                    Databasehandler db = new Databasehandler(Display.this);
                                    com.abc.example.com.timing.chat.Registration obj4 = new com.abc.example.com.timing.chat.Registration(get_sender_phone[i] , get_receiver_phone[i] , getmes[i], getdate[i] , "1" , "0");
                                    db.addDATA(obj4);

                                }else{
                                    Databasehandler db = new Databasehandler(Display.this);
                                    com.abc.example.com.timing.chat.Registration obj4 = new com.abc.example.com.timing.chat.Registration(get_sender_phone[i] , get_receiver_phone[i] , getmes[i], getdate[i] , "0" , "0");
                                    db.addDATA(obj4);

                                }
                            }
                        } catch (JSONException ex) {
                            Log.e("result", "Error:=" + ex.getMessage());
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        });
        queue.add(stringRequest);

    }
}
