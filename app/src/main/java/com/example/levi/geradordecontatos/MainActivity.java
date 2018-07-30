package com.example.levi.geradordecontatos;

import android.os.Bundle;
import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import java.util.Arrays;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                        != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1000);
        }
        //getActionBar().setTitle("Gerador de Contatos");

        final Button btnGerar = (Button) findViewById(R.id.btnGerar);

        final CheckBox cbClaro = (CheckBox) findViewById(R.id.cbClaro);
        final CheckBox cbOi = (CheckBox) findViewById(R.id.cbOi);
        final CheckBox cbTim = (CheckBox) findViewById(R.id.cbTim);
        final CheckBox cbVivo = (CheckBox) findViewById(R.id.cbVivo);

        final EditText txtQuantidade = (EditText) findViewById(R.id.txtQuantidade);
        final EditText txtDDI = (EditText) findViewById(R.id.txtDDI);
        final EditText txtDDD = (EditText) findViewById(R.id.txtDDD);

        Intent intent = new Intent(MainActivity.this, GeneratedNumbersActivity.class);


        btnGerar.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                String[] rest = new String[]{"1","2","3","4","5","6","7","8","9","10","20","23","25","26","29","30","36","39","40","50","52","56","57","58","59","60","70","72","76","78","80","90"};

                Random rand = new Random();
                String generated = "";

                String[] op = new String[]{cbClaro.isChecked() ? "claro" : null
                        , cbOi.isChecked() ? "oi" : null
                        , cbTim.isChecked() ? "tim" : null
                        , cbVivo.isChecked() ? "vivo" : null};


                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                try {

                    if ((!cbClaro.isChecked() && !cbOi.isChecked() && !cbTim.isChecked() && !cbVivo.isChecked())) {
                        builder.setMessage("Selecione pelo menos uma operadora.")
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                }).show();
                        builder.create();
                    } else if (txtQuantidade.getText().toString().isEmpty()) {
                        builder.setMessage("Selecione quantos contatos deseja gerar.")
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                }).show();
                        builder.create();
                        txtQuantidade.requestFocus();
                    }else if (Integer.parseInt(txtQuantidade.getText().toString()) < 1){
                        builder.setMessage("Não pode gerar 0 contatos.")
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                }).show();
                        builder.create();
                        txtQuantidade.requestFocus();
                    }else if(txtDDI.getText().toString().isEmpty()){
                        builder.setMessage("O DDI não pode ser nulo.").setPositiveButton(R.string.alt_ddi, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                txtDDI.setText(R.string._55);
                            }
                        }).setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).show();
                        builder.create();
                        txtDDI.requestFocus();
                    }else if(txtDDD.getText().toString().isEmpty()) {
                        builder.setMessage("O DDD não pode ser nulo.").setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {

                            }
                        }).show();
                        builder.create();
                        txtDDD.requestFocus();
                    }else if(txtDDI.getText().toString().equals("55") && Arrays.asList(rest).contains(txtDDD.getText().toString())){
                        builder.setMessage("O DDD está inválido.")
                                .setNegativeButton(R.string.ok, new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int id) {

                                    }
                                }).show();
                        builder.create();
                        txtDDD.requestFocus();
                    }else{
                        Intent intent = new Intent(MainActivity.this, GeneratedNumbersActivity.class);

                        int[] num = new int[getQtde(op)];
                        int j = 0;
                        for (int i = 0; i < num.length; i++) {
                            if (op[i] != null) {
                                if (i == 0) {
                                    num[j] = rand.nextInt(4) + 91;
                                } else if (i == 1) {
                                    num[j] = rand.nextInt(6) + 84;
                                } else if (i == 2) {
                                    num[j] = rand.nextInt(4) + 96;
                                } else {
                                    num[j] = rand.nextInt(2) + 81;
                                }
                                j++;
                            }
                        }


                        for (int i = 0; i < Integer.parseInt(txtQuantidade.getText().toString()); i++) {
                            generated += "BEGIN:VCARD\nVERSION:2.1\nN:;Número " + (i + 1) +
                                    ";;;\nTEL;CELL:+"+ txtDDI.getText().toString() + txtDDD.getText().toString() + "9" +
                                    num[rand.nextInt(num.length)] + (rand.nextInt(10)) + (rand.nextInt(10)) +
                                    (rand.nextInt(10)) + (rand.nextInt(10)) + (rand.nextInt(10)) +
                                    (rand.nextInt(10)) + "\nEND:VCARD\n";
                        }

                        intent.putExtra("temp", generated);
                        startActivity(intent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int getQtde(String[] op){
        int  j = 0;
        for(int i = 0; i < op.length; i++){
            if(op[i] != null){
                j++;
            }
        }
        return j;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.help) {
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
        }else if(id == R.id.about){
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        }

        return super.onOptionsItemSelected(item);
    }
}
