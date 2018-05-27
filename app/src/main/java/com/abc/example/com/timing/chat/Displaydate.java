package com.abc.example.com.timing.chat;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.abc.example.com.timing.DATABASEHANDLER;
import com.abc.example.com.timing.Display;
import com.abc.example.com.timing.R;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class Displaydate extends AppCompatActivity implements View.OnClickListener {

    EditText e1;
    datalist datalist ;
    int flag=0;
    String file;
    Databasehandler db;
    String lastdate;
    ListView l1;
    RequestQueue queue ;
    String sender;
    String receive ;
    Button b1,Gallery;
    Bitmap bmp;
    boolean fla=true;
    boolean f=true;
    private String UPLOAD_URL ="http://192.168.1.171/Volleyupload/upload1.php";
    String mSelectedImagePath;
    private static final int SELECT_IMAGE = 1;
    private static final int PICK_IMAGE_REQUEST = 1;

    private ImageView actualImageView;
    private ImageView compressedImageView;
    private TextView actualSizeTextView , t1;
    private TextView compressedSizeTextView;
    private File actualImage;
    private File compressedImage;

    int i =0;
    private String KEY_IMAGE = "image";
    View id1;
    @Override
    public void onBackPressed()
    {
        // code here to show dialog
        super.onBackPressed();  // optional depending on your needs
        f = false;
        getIntent().removeExtra("num");
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_display);

        Intent i = getIntent();
        receive = i.getStringExtra("num");

        SharedPreferences shared = getSharedPreferences("number", Context.MODE_PRIVATE);
        sender = shared.getString("unumber", null);

        id1 = findViewById(R.id.id1);
        actualImageView = (ImageView)id1.findViewById(R.id.image);
        id1.setVisibility(View.GONE);

        queue = Volley.newRequestQueue(Displaydate.this);
        l1 = (ListView)findViewById(R.id.l1);
        Gallery = (Button)findViewById(R.id.gallery);
        display();
        getdidntsenddaata();


        b1.setOnClickListener(this);
        Gallery.setOnClickListener(this);

        try
        {
            Thread t2 = new Thread() {
                @Override
                public void run() {
                    db = new Databasehandler(Displaydate.this);
                    b1 = (Button)findViewById(R.id.send);
                    e1 = (EditText)findViewById(R.id.e1);
                    while (f) {
                        try {
                            Thread.sleep(1000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    display();

                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            t2.start();
        }catch (Exception e)
        {
            Toast.makeText(this,"it will be destroy",Toast.LENGTH_LONG).show();
        }
        try
        {
            Thread t = new Thread() {
                @Override
                public void run() {
                    while (f) {
                        try {
                            Thread.sleep(3000);
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                   getdidntsenddaata();
                                }
                            });
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            };
            t.start();
        }catch (Exception e)
        {
            Toast.makeText(this,"it will be destroy",Toast.LENGTH_LONG).show();
        }

    }

    public void display() {
        try {

            Log.e("ddddddddddddd","dddddddddddd");

            if(fla)
            {
                List<Registration> contacts = db.getAllData(receive);
                    String[] message = new String[contacts.size()];
                    String[] sender = new String[contacts.size()];
                    String[] tvi = new String[contacts.size()];
                    String[] receiver = new String[contacts.size()];
                    int i = 0;
                    for (Registration cn : contacts) {
                        message[i] = cn.getmes();
                        Log.e("message" , message[i]);
                        sender[i] = cn.getSender_phone();
                        receiver[i] = cn.getReciever_phone();
                        tvi[i] = cn.getTvi();
                        Log.e("oo",tvi[i]);
                        Log.e("LOG", cn.getdate() + "hello");

                        lastdate = cn.getdate();

                        i++;
                        if (i == contacts.size()) break;
                    }
                    datalist = new datalist(Displaydate.this , sender , receiver , message , tvi);
                    l1.setAdapter(datalist);
                    Log.e("DIsplay", "Helloo");
                    fla = false;
            }

            else {
                Log.e("getAlllbydate" , "helloo");
                List<Registration> contacts = db.getAllbydate(lastdate , receive);
                for (Registration cn : contacts) {
                    datalist.addItem(cn.getmes(),cn.getSender_phone(),cn.getReciever_phone(),cn.getTvi());
                    Log.e("LOG", cn.getmes());
                    lastdate = cn.getdate();
                    Log.e("LOGdate", cn.getdate());
                }
                datalist.notifyDataSetChanged();

           }
        }catch (Exception e)
        {
            Log.e("error" , e.getMessage());
        }
    }

     /* **************************    UPLOAD IMAGE TO SERVER USING VOLLEY REQUEST   ********************************/

    private void uploadImage(){
        //Showing the progress dialog
        final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String s) {
                        //Disimissing the progress dialog
                        loading.dismiss();
                        //Showing toast message of the response
                        Toast.makeText(Displaydate.this, s , Toast.LENGTH_LONG).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        //Dismissing the progress dialog
                        loading.dismiss();
                        Log.e("url",UPLOAD_URL);
                        //Showing toast
                    }
                }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                //Converting Bitmap to String
                String image = getStringImage(bmp);

                Log.e("getString" , image);

                //Creating parameters
                Map<String,String> params = new Hashtable<String, String>();

                //Adding parameters
                params.put(KEY_IMAGE, image);
                params.put("sender" , sender);
                params.put("receiver" , receive);
                //returning parameters
                return params;
            }
        };

        //Creating a Request Queue
        RequestQueue requestQueue = Volley.newRequestQueue(this);

        //Adding request to the queue
        requestQueue.add(stringRequest);
        Log.e("URL","CALL");
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void imageFromGallery() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent,"Select Picture"), SELECT_IMAGE);
    }

    /*public void onActivityResult(int requestCode, int resultCode, Intent data) {
        String j = null;

        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch(requestCode) {
                case SELECT_IMAGE:
                    mSelectedImagePath = getPath(data.getData());
                    System.out.println("mSelectedImagePath : " + mSelectedImagePath);
                    try {
                        Log.e("mselect", mSelectedImagePath);
                        i++;
                        Log.e("i" , String .valueOf(i) );
                        try {
                            File sd = Environment.getExternalStorageDirectory();
                            Log.e("sd" , String.valueOf(sd));
                            if (sd.canWrite()) {
                                System.out.println("(sd.canWrite()) = " + (sd.canWrite()));
                                Log.e("sdwrite" , String.valueOf(sd.canWrite()));
                                String destinationImagePath= "/SS/file"+i+".jpg";   // this is the destination image path.
                                Log.e("image path" , destinationImagePath);
                                File e destination= new File(sd, destinationImagePath);
source = new File(mSelectedImagePath );
                                Fil
                                if (source.exists()) {
                                    FileChannel src = new FileInputStream(source).getChannel();
                                    FileChannel dst = new FileOutputStream(destination).getChannel();
                                    dst.transferFrom(src, 0, src.size());// copy the first file to second.....
                                    src.close();
                                    dst.close();
                                    Toast.makeText(getApplicationContext(), "Check the copy of the image in the same path as the gallery image. File is name file.jpg", Toast.LENGTH_LONG).show();

                                    String path = Environment.getExternalStorageDirectory().getAbsolutePath() + "/SS/file"+i+".jpg";
                                    file = "file"+i+".jpg";

                                    Log.e("path", path);
                                    bmp = BitmapFactory.decodeFile(path);
                                    Log.e("Bitmap " , String.valueOf(BitmapFactory.decodeFile(path)));
                                }
                            }else{
                                Toast.makeText(getApplicationContext(), "SDCARD Not writable.", Toast.LENGTH_LONG).show();
                            }
                        }catch (Exception e) {
                            System.out.println("Error :" + e.getMessage());
                        }
                        break;


                    } catch (Exception e)
                    {
                    }
            }
        }
    }*/





    public String getPath(Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = managedQuery(uri, projection, null, null, null);
        startManagingCursor(cursor);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }

    @Override
    public void onClick(View v) {

        if (v.getId() == R.id.send)
        {

            String message = e1.getText().toString();
            SimpleDateFormat dateFormat = new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
            Date date = new Date();
            Log.e("dateformat" , dateFormat.format(date));

            Registration obj = new Registration(sender , receive , message , dateFormat.format(date),"0" , "0");
              db.addDATA(obj);

            DATABASEHANDLER db = new DATABASEHANDLER(Displaydate.this);
            db.update1(receive ,  message , dateFormat.format(date));

         //   Registration obj = new Registration(sender , receive , e , dateFormat.format(date),"0");
          //  db.addDATA(obj);
   /*         RequestQueue queue = Volley.newRequestQueue(this);
            String url = "http://192.168.1.171/online.php?sender=" + sender + "&receiver=" + receive + "&date=" + Uri.encode(String.valueOf(date)) + "&message=" + Uri.encode(e) ;
            Log.e("online", url);
            final StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            if (Integer.parseInt(response) == 1) {

                                Toast.makeText(getApplicationContext() , "message" , Toast.LENGTH_LONG).show();
// RECEIVING DATA FROM MYSQL AND ENTERING IN LOCAL SQLITE DATASE
                            }
                        }

                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {


                }
            });
            queue.add(stringRequest);*/
        }
        else if (v.getId() == R.id.gallery)
        {
            imageFromGallery();
        }

 }

            /***************************************compressor *********************************************************/

public void compressImage(View view) {
    if (actualImage == null) {
        showError("Please choose an image!");
    } else {
        id1.setVisibility(View.GONE);

        // Compress image in main thread
        //compressedImage = new Compressor(this).compressToFile(actualImage);
        //setCompressedImage();

        // Compress image to bitmap in main thread
        //compressedImageView.setImageBitmap(new Compressor(this).compressToBitmap(actualImage));

        // Compress image using RxJava in background thread
        new com.abc.example.com.timing.chat.Compressor()
                .compressToFileAsFlowable(actualImage)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(new Consumer<File>() {
                    @Override
                    public void accept(File file) {
                        compressedImage = file;
                   //     setCompressedImage();
                        SimpleDateFormat dateFormat = new SimpleDateFormat(
                                "yyyy-MM-dd HH:mm:ss", Locale.getDefault());
                        Date date = new Date();
                        Toast.makeText(getApplicationContext() , compressedImage.getPath() , Toast.LENGTH_LONG).show();
                        Toast.makeText(getApplicationContext() , compressedImage.getName() , Toast.LENGTH_LONG).show();


                        Registration obj = new Registration(sender , receive , compressedImage.getName() , dateFormat.format(date),"1" , "0");
                        db.addDATA(obj);

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) {
                        throwable.printStackTrace();
                        showError(throwable.getMessage());
                    }
                });
    }
}

   /* public void customCompressImage(View view) {
        if (actualImage == null) {
            showError("Please choose an image!");
        } else {
            // Compress image in main thread using custom Compressor
            try {
                compressedImage = new Compressor(this)
                        .setMaxWidth(640)
                        .setMaxHeight(480)
                        .setQuality(75)

                        .setCompressFormat(Bitmap.CompressFormat.WEBP)
                        .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                                Environment.DIRECTORY_PICTURES).getAbsolutePath())
                        .compressToFile(actualImage);

                setCompressedImage();
            } catch (IOException e) {
                e.printStackTrace();
                showError(e.getMessage());
            }*/

    // Compress image using RxJava in background thread with custom Compressor
            /*new Compressor(this)
                    .setMaxWidth(640)
                    .setMaxHeight(480)
                    .setQuality(75)
                    .setCompressFormat(Bitmap.CompressFormat.WEBP)
                    .setDestinationDirectoryPath(Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_PICTURES).getAbsolutePath())
                    .compressToFileAsFlowable(actualImage)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Consumer<File>() {
                        @Override
                        public void accept(File file) {
                            compressedImage = file;
                            setCompressedImage();
                        }
                    }, new Consumer<Throwable>() {
                        @Override
                        public void accept(Throwable throwable) {
                            throwable.printStackTrace();
                            showError(throwable.getMessage());
                        }
                    });*/
       /* }
    }*/

    public void getdidntsenddaata()
    {
        List<Registration> contacts = db.getdidntsenddaata();
        int i = 0;
        for (final Registration cn : contacts)
        {
            if (cn.getTvi().equals("1"))
            {
                Log.e("imageurl" , UPLOAD_URL);

                String path = Environment.getExternalStorageDirectory().getAbsolutePath()+"/SS/"+cn.getmes();
                Log.e("path",path);
                bmp = BitmapFactory.decodeFile(path);

                final ProgressDialog loading = ProgressDialog.show(this,"Uploading...","Please wait...",false,false);
                StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String s) {
                                //Disimissing the progress dialog
                                loading.dismiss();
                                //Showing toast message of the response
                                Databasehandler db = new Databasehandler(Displaydate.this);
                                db.updateflag1(s);
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
                                //Dismissing the progress dialog
                                loading.dismiss();
                                Log.e("url",UPLOAD_URL);
                                //Showing toast
                            }
                        }){
                    @Override
                    protected Map<String, String> getParams() throws AuthFailureError {
                        //Converting Bitmap to String
                        String image = getStringImage(bmp);
                        Log.e("getString" , image);
                        //Creating parameters
                        Map<String,String> params = new Hashtable<String, String>();
                        //Adding parameters
                        params.put(KEY_IMAGE, image);
                        params.put("sender" , cn.getSender_phone());
                        params.put("receiver" , cn.getReciever_phone());
                        params.put("date", cn.getdate());
                        //returning parameters
                        return params;
                    }
                };

                //Creating a Request Queue
                RequestQueue requestQueue = Volley.newRequestQueue(this);

                //Adding request to the queue
                requestQueue.add(stringRequest);
                Log.e("URL","CALL");
                if (i == contacts.size()) break;
                i++;
            }
            else {

                Log.e("Volley" , "Volley request");

                if (queue == null) {
                    queue = Volley.newRequestQueue(Displaydate.this);
                }

                String url = "http://192.168.1.171/online.php?sender=" + cn.getSender_phone() + "&receiver=" + cn.getReciever_phone() + "&date=" + Uri.encode(cn.getdate()) +  "&message=" + Uri.encode(cn.getmes());
                Log.e("online", url);
                 StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                Databasehandler db = new Databasehandler(Displaydate.this);
                                db.updateflag1(response);
                            }

                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                        Log.e("hi" , "hi");

                    }
                });
                queue.add(stringRequest);
            }

        }
    }

    private void setCompressedImage() {
        // compressedImageView.setImageBitmap(BitmapFactory.decodeFile(compressedImage.getAbsolutePath()));
        //  compressedSizeTextView.setText(String.format("Size : %s", getReadableFileSize(compressedImage.length())));
        Toast.makeText(this, "Compressed image save in " + compressedImage.getPath(), Toast.LENGTH_LONG).show();
        String i =  compressedImage.getPath();

     //   t1.setText(compressedImage.getPath().substring(29));

        Log.d("Compressor", "Compressed image save in " + compressedImage.getPath());
    }

    private void clearImage() {
        actualImageView.setBackgroundColor(getRandomColor());
//        compressedImageView.setImageDrawable(null);
  //      compressedImageView.setBackgroundColor(getRandomColor());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK) {
            if (data == null) {
                showError("Failed to open picture!");
                return;
            }
            try {
                id1.setVisibility(View.VISIBLE);
                actualImage = FileUtil.from(this, data.getData());
                actualImageView.setImageBitmap(BitmapFactory.decodeFile(actualImage.getAbsolutePath()));
                clearImage();
            } catch (IOException e) {
                showError("Failed to read picture data!");
                e.printStackTrace();
            }
        }
    }

    public void showError(String errorMessage) {
        Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT).show();
    }

    private int getRandomColor() {
        Random rand = new Random();
        return Color.argb(100, rand.nextInt(256), rand.nextInt(256), rand.nextInt(256));
    }

    public String getReadableFileSize(long size) {
        if (size <= 0) {
            return "0";
        }
        final String[] units = new String[]{"B", "KB", "MB", "GB", "TB"};
        int digitGroups = (int) (Math.log10(size) / Math.log10(1024));
        return new DecimalFormat("#,##0.#").format(size / Math.pow(1024, digitGroups)) + " " + units[digitGroups];
    }

}
