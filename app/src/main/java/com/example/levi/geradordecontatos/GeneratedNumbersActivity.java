package com.example.levi.geradordecontatos;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Environment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class GeneratedNumbersActivity extends AppCompatActivity {
    private final static String CONTACTS = "Contacts.vcf";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generated_numbers);

        final Intent intent = getIntent();

        final EditText txtGenerated = (EditText) findViewById(R.id.txtGenerated);


        final Button btnCopiar = (Button) findViewById(R.id.btnCopiar);
        final Button btnNovo = (Button) findViewById(R.id.btnNovo);
        final Button btnSalvar = (Button) findViewById(R.id.btnSalvar);

        final CheckBox cbClaro = (CheckBox) findViewById(R.id.cbClaro);
        final CheckBox cbOi = (CheckBox) findViewById(R.id.cbOi);
        final CheckBox cbTim = (CheckBox) findViewById(R.id.cbTim);
        final CheckBox cbVivo = (CheckBox) findViewById(R.id.cbVivo);

        txtGenerated.setText(intent.getStringExtra("temp"));


        btnNovo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                finish();
            }
        });

        btnCopiar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                ClipData clip = ClipData.newPlainText(txtGenerated.getText(), txtGenerated.getText());
                clipboard.setPrimaryClip(clip);

                AlertDialog.Builder builder = new AlertDialog.Builder(GeneratedNumbersActivity.this);
                builder.setMessage("Texto copiado com sucesso.")
                        .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // User cancelled the dialog
                            }
                        }).show();
                builder.create();
            }
        });

        btnSalvar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                File f = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), CONTACTS);
                try {
                    FileOutputStream file = new FileOutputStream(f);
                    file.write(intent.getStringExtra("temp").getBytes());
                    AlertDialog.Builder builder = new AlertDialog.Builder(GeneratedNumbersActivity.this);
                    builder.setMessage("Lista salva: " +
                            Environment.getExternalStorageDirectory().getAbsolutePath() + "/" + CONTACTS)
                            .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    // User cancelled the dialog
                                }
                            }).show();
                    builder.create();
                    file.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

    }

}
