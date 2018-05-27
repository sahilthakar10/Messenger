package com.abc.example.com.timing.chat;

import android.util.Log;

/**
 * Created by ABC on 12-10-2017.
 */

public class Registration {
    int id;
    String name;
    String created_date;
    String sender_phone;
    String reciever_phone;
    String mes;
    String tvi;
    String flag;
    public  Registration()
    {

    }
    public Registration(int id , String sender_phone , String receiver_phone, String mes, String date , String tvi , String flag) {
      //  Log.e("eee" , name);
       // Log.e("id" , String.valueOf(id));
        this.sender_phone = sender_phone;
        this.reciever_phone = receiver_phone;
       // Log.e("sp" , String.valueOf(sender_phone));
       // Log.e("rp" , String.valueOf(receiver_phone));
        this.id = id;
        this.mes = mes;
        this.created_date = date;
        this.tvi = tvi;
        this.flag = flag;
       Log.e("message" , this.mes);
        Log.e("nameq" , this.name);

    }

    public Registration(String sender_phone , String receiver_phone,String mes , String date , String tvi , String flag) {

        this.mes = mes;
        this.created_date = date;
        Log.e("this.created_Date" , date);
        this.sender_phone = sender_phone;
        this.reciever_phone = receiver_phone;
        this.tvi = tvi;
        this.flag = flag;
    }

    public int getId() {
        return id;
    }
    public void setId(int id)
    {
        this.id = id;
    }

    public String getmes(){return mes;}
    public void setmes(String mes){this.mes=mes;}

    public String getdate() {
        return created_date;
    }
    public void setdate(String date) {
        this.created_date = date;
    }

    public String getSender_phone(){return sender_phone;}
    public void setSender_phone(String sender_phone){this.sender_phone = sender_phone;}

    public String getReciever_phone(){return reciever_phone;}
    public void setReciever_phone(String reciever_phone){this.reciever_phone = reciever_phone;}

    public String getTvi(){return tvi;}
    public void setTvi(String tvi){this.tvi = tvi;}

    public String getFlag(){return flag;}
    public void setFlag(String flag){this.flag = flag;}
}
