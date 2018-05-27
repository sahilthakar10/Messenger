package com.abc.example.com.timing;

import android.util.Log;

/**
 * Created by ABC on 29-09-2017.
 */

public class Registration {
    int id;
    String phone;
    String notification;
    String flag;
    String created_date;
    String msg;

    public  Registration()
    {

    }
    public Registration(int id ,String phone , String notification , String flag , String msg , String created_date ) {
      //  Log.e("eee" , phone);
    //    Log.e("id" , String.valueOf(id));
        this.id = id;
        this.phone = phone;
        this.notification = notification;
        this.flag = flag;
        this.msg = msg;
        this.created_date = created_date;
    }

    public Registration(String phone , String notification , String flag , String msg , String created_date)
    {
        this.phone = phone;
        this.notification = notification;
       this.flag = flag;
        this.msg = msg ;
        this.created_date = created_date;
    //    Log.e("this.phone",this.phone);
      //  Log.e("this.notification" , this.notification);
    }
    public int getId() {return id;}
    public void setId(int id)
    {
        this.id = id;
    }

    public String getPhone(){ return phone;}
    public void setphone(String phone) {this.phone = phone;}

    public String getNotification(){return notification;}
    public void setNotification(String notification)
    {
        this.notification = notification;
    }

    public String getflag(){return flag;}
    public void setFlag(String flag){this.flag = flag;}

    public String getCreated_date(){ return created_date;}
    public void setCreated_date(String created_date){ this.created_date = created_date;}

    public String getMsg(){ return msg;}
    public void setMsg(String msg){ this.msg = msg;}
}
