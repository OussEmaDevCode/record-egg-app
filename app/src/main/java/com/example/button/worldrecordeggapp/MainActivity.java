package com.example.button.worldrecordeggapp;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.zip.Inflater;

public class MainActivity extends AppCompatActivity {
    ImageView face = null;
    View egg = null;
    private static final int PICK_IMAGE = 100;
    private static final int TAKE_PHOTO = 0;
    private Uri imageUri;
    ProgressDialog waitingDialog = null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        face = findViewById(R.id.faceimg);
        egg = findViewById(R.id.egg);
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShowDialogBox();

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
       int id = item.getItemId();
       if (id == R.id.action_save){
           new getAsynctask().execute("");
           return true;
       }
       if (id == R.id.action_how){
           startActivity(new Intent(MainActivity.this,Intro.class));
       }
      return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //choose form gallery
        if (resultCode == RESULT_OK && requestCode == PICK_IMAGE) {
            imageUri = data.getData();
            Bitmap img = null;
            try {
                img = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            face.setImageBitmap(img);

        }
        //take a photo
        else if (resultCode == RESULT_OK && requestCode == TAKE_PHOTO) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            face.setImageBitmap(image);

        }
    }

    private void openGallery() {
        Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
        startActivityForResult(gallery, PICK_IMAGE);
    }

    private void openCamera() {
        Intent takephto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(takephto, TAKE_PHOTO);
    }

    private void ShowDialogBox() {
        //setting up the dialog box...
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setIcon(android.R.drawable.ic_menu_camera);
        alertDialogBuilder.setCancelable(true);
        alertDialogBuilder.setTitle("How do you want to add the photo?");
        alertDialogBuilder.setPositiveButton("from gallery",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface arg0, int arg1) {
                        openGallery();

                    }
                });
        alertDialogBuilder.setNegativeButton("take a photo", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                openCamera();
            }
        });
        AlertDialog alertDialog = alertDialogBuilder.create();
        //...showing it
        alertDialog.show();
    }

    public void start(){
        Bitmap bmp = viewToBitmap(egg);
        try {
            //Write file
            String filename = "bitmap.png";
            FileOutputStream stream = this.openFileOutput(filename, Context.MODE_PRIVATE);
            bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);

            //Cleanup
            stream.close();
            bmp.recycle();

            //Pop intent
            Intent in1 = new Intent(this, save.class);
            in1.putExtra("image", filename);
            startActivity(in1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public Bitmap viewToBitmap(View view) {
        Bitmap bitmap = Bitmap.createBitmap(view.getWidth(), view.getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }
    class getAsynctask extends AsyncTask<String, Long, Integer> {

        protected void onPreExecute() {
            super.onPreExecute();
            waitingDialog = ProgressDialog.show(MainActivity.this, "Please Wait..."
                    , "setting up your egg");
        }
        protected Integer doInBackground(String... params) {
            try {
                start();
                return null;
            } catch (Exception e) {
                return null;
            }

        }

        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);
            try {
                if (waitingDialog != null && waitingDialog.isShowing())
                    waitingDialog.dismiss();
            } catch (Throwable t) {
                Log.v("this is praki", "loading.dismiss() problem", t);
            }
        }
    }
}

