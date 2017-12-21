package com.example.geraldobarros.projetods_android_geraldo_barros;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

/**
 * Created by Geraldo Barros on 21/12/2017.
 */

public class DownloadAsyncTask extends AppCompatActivity {
    ImageView imageHolder;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        imageHolder = findViewById(R.id.aa);

        imageHolder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent photoCaptureIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(photoCaptureIntent, 1);
            }
        });
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

                Uri selectedImage = data.getData();
                imageHolder.setImageURI(selectedImage);

        }



}
