package com.example.lab5;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements AdapterView.OnItemClickListener {
    String[] androidNames, androidVersion, androidAPI, releaseDates;
    ListView lists;
    int[] androidLogo = {R.drawable.android_cupcake, R.drawable.android_donut, R.drawable.android_eclair, R.drawable.android_froyo, R.drawable.android_gingerbread, R.drawable.android_honeycomb, R.drawable.android_icecreamsandwitch, R.drawable.android_jellybean, R.drawable.android_kitkat, R.drawable.android_lollipop, R.drawable.android_marshmallow, R.drawable.android_nougat, R.drawable.android_oreo, R.drawable.android_pie, R.drawable.android_10};
    ArrayList<Android> androidList = new ArrayList<>();
    String[] versionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        androidNames = getResources().getStringArray(R.array.androidName);
        androidVersion = getResources().getStringArray(R.array.androidVersion);
        androidAPI = getResources().getStringArray(R.array.apiLevel);
        releaseDates = getResources().getStringArray(R.array.releaseDate);
        versionInfo = getResources().getStringArray(R.array.androidInformation);

        for (int i = 0; i < androidNames.length; i++) {
            androidList.add(new Android(androidLogo[i], androidNames[i], "Ver. " + androidVersion[i], "API Level " + androidAPI[i], "Released " + releaseDates[i]));
        }

        lists = findViewById(R.id.listView);

        ArrayAdapter androidArrayAdapter = new AndroidAdaptors(this, R.layout.item, androidList);

        lists.setAdapter(androidArrayAdapter);
        lists.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        try {
            final AlertDialog.Builder myDialog = new AlertDialog.Builder(this);
            final File folder = getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS);
            File file = new File(folder, "AndroidVer.txt");
            FileOutputStream fos = new FileOutputStream(file);
            String choice = "Version Name: " + androidList.get(position).getAndroidNames() + "\n" + androidList.get(position).getReleaseDates();
            fos.write(choice.getBytes());
            fos.close();
            myDialog.setTitle(androidList.get(position).getAndroidNames());
            myDialog.setIcon(androidList.get(position).getLogo());
            myDialog.setMessage(versionInfo[position]);
            myDialog.setNeutralButton("Close", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                    showToast();
                }
            });

            myDialog.create().show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showToast() {
        try {
            FileInputStream fin;
            fin = new FileInputStream(new File(getExternalFilesDir(Environment.DIRECTORY_DOWNLOADS) + "/AndroidVer.txt"));
            int i;
            String str = "";
            while ((i = fin.read()) != -1) {
                str += Character.toString((char) i);
            }
            fin.close();
            Toast.makeText(this, str, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
