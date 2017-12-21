package com.example.geraldobarros.projetods_android_geraldo_barros;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

/**
 * Created by Geraldo Barros on 20/12/2017.
 */

public class Description extends AppCompatActivity {

    private ImageView imageHolder;
    Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.description);

        Intent intent = getIntent();
        String messageID = intent.getStringExtra(MainActivity.idLoja);
        String messageNome = intent.getStringExtra(MainActivity.nomeLoja);
        String messageTelefone = intent.getStringExtra(MainActivity.telefoneLoja);

        String messageComp = intent.getStringExtra(MainActivity.complementoDescrpt);
        String messageBairro = intent.getStringExtra(MainActivity.bairroDescrpt);
        String messageDescr = intent.getStringExtra(MainActivity.numeroDescrpt);
        String messageLogra = intent.getStringExtra(MainActivity.logradouroDescrpt);

        TextView textViewId = findViewById(R.id.textId);
        textViewId.setText(messageID);

        TextView textViewNome = findViewById(R.id.textId2);
        textViewNome.setText(messageNome);

        TextView textViewTelefone = findViewById(R.id.textId3);
        textViewTelefone.setText(messageTelefone);

        TextView textView = findViewById(R.id.txt1);
        textView.setText(messageComp);

        TextView textView2 = findViewById(R.id.txt2);
        textView2.setText(messageBairro);

        TextView textView3 = findViewById(R.id.txt3);
        textView3.setText(messageDescr);

        TextView textView4 = findViewById(R.id.txt4);
        textView4.setText(messageLogra);


        imageHolder = findViewById(R.id.captured_photo);

        imageHolder.setOnClickListener(new View.OnClickListener() {
            CharSequence options[] = new CharSequence[] {"Câmera", "Galeria"};

            @Override
            public void onClick(View v) {

                AlertDialog.Builder builder = new AlertDialog.Builder(Description.this);
                builder.setCancelable(false);
                builder.setTitle("Buscar foto:");
                builder.setItems(options, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                       if (which == 0){
                           Intent photoCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                           startActivityForResult(photoCaptureIntent, 0);
                       }
                       if (which == 1){
                           Intent photoCaptureIntent = new Intent(Intent.ACTION_PICK,
                                   android.provider.MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                           startActivityForResult(photoCaptureIntent, 1);

                       }
                    }
                });
                builder.setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //the user clicked on Cancel
                    }
                });
                builder.show();

            }
        });

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(resultCode != Activity.RESULT_CANCELED){
            if(requestCode == 0){
                Bitmap bitmap = (Bitmap)data.getExtras().get("data");
                imageHolder.setImageBitmap(bitmap);
                imageHolder.setBackground(null);
            }

            if(requestCode == 1){
                selectedImage = data.getData();
                imageHolder.setImageURI(selectedImage);


            }
        }


    }

    public void msgToast(String msg){
        Context context = getApplicationContext();
        CharSequence text = msg;
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
