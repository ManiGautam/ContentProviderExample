package com.manijee.contentproviderexample;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
TextView tvname,tvcontact;
Button btngetContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tvname=findViewById(R.id.txt_name);
        tvcontact=findViewById(R.id.txt_contact);
        btngetContact=findViewById(R.id.btn_pic_contact);
        btngetContact.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
         requestPermissions(new String[]{Manifest.permission.READ_CONTACTS},123);
        }else{
            Intent callContact=new Intent();
            callContact.setAction(Intent.ACTION_PICK);
            callContact.setData(ContactsContract.CommonDataKinds.Phone.CONTENT_URI);
            startActivityForResult(callContact,321);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==321&&resultCode==RESULT_OK){
            ContentResolver resolver=getContentResolver();
            String[] columns=new String[]{
                    ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
                    ContactsContract.CommonDataKinds.Phone.NUMBER
            };
            Cursor cursor=resolver.query(data.getData(),columns,null,null,null);
            cursor.moveToFirst();
            if (cursor!=null&&cursor.getColumnCount()>0){
                int nameIndex=cursor.getColumnIndex(columns[0]);
                String name=cursor.getString(nameIndex);
                tvname.setText(name);

                int contactIndex=cursor.getColumnIndex(columns[1]);
                String contact=cursor.getString(contactIndex);
               tvcontact.setText(contact);
            }else{
                Toast.makeText(this, "Unable to pic contact", Toast.LENGTH_SHORT).show();
            }
        }else{
            Toast.makeText(this, "Unable to pic contact", Toast.LENGTH_SHORT).show();
        }
    }
}