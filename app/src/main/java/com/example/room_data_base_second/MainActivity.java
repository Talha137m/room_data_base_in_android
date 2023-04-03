package com.example.room_data_base_second;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.room.Room;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewStub;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import adaptors.ContactAdaptor;
import dataBase.ContactDataBase;
import dataBase.ContactEntity;

public class MainActivity extends AppCompatActivity {
    //1->make the list object
    List<ContactEntity> contactEntityList = new ArrayList<>();

    //2->make the views variables
    RecyclerView rv;
    ViewStub viewStub;
    FloatingActionButton floatingBtn;
    //3->make the variable of DataBase class
    ContactDataBase contactDataBase;

    //4-> declare the variables
    String name;
    String mail;
    ContactAdaptor contactAdaptor;
    boolean isUpdate=false;
    int itemIndex;
    int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //1->initialize the views variables
        rv = findViewById(R.id.rv);
        floatingBtn = findViewById(R.id.floatingBtn);
        viewStub=findViewById(R.id.viewStub);

        viewStub.inflate();
        //2->initialize the dataBase
        contactDataBase = Room.databaseBuilder(
                getApplicationContext(),
                ContactDataBase.class,
                "contactDataBase"
        ).allowMainThreadQueries().build();
        //3->fetch add data from data base add to the list
        contactEntityList.addAll(contactDataBase.getContactDao().fetchContacts());
        //4-> set the method
        setAdaptor();
        floatingActionBtnClick();
        adaptorViewClick();
        checkVisibility();
    }
    //ending the on create method
    //1->floating action btn method
    private void floatingActionBtnClick() {
        floatingBtn.setOnClickListener(v -> {
            showDialog();
        });
    }

    //2->showDialog method
    @SuppressLint("NotifyDataSetChanged")
    private void showDialog() {
        EditText etMail, etName;
        AppCompatButton btnAdd, btnCancel;
        View view = LayoutInflater.from(MainActivity.this).inflate(R.layout.dialog_layout, null, false);
        etMail = view.findViewById(R.id.etMail);
        etName = view.findViewById(R.id.etName);
        btnAdd = view.findViewById(R.id.btnAdd);
        btnCancel = view.findViewById(R.id.btnCancel);

        AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).setView(view).create();
        alertDialog.show();
        if (isUpdate){
            name=contactEntityList.get(itemIndex).getName();
            mail=contactEntityList.get(itemIndex).getEmail();
            id=contactEntityList.get(itemIndex).getId();
            etName.setText(name);
            etMail.setText(mail);
        }

        //1-> cancel button implement
        btnCancel.setOnClickListener(v -> {
            alertDialog.dismiss();
        });

        //2-> add button implement
        btnAdd.setOnClickListener(v -> {
            name = etName.getText().toString();
            mail = etMail.getText().toString();

            if (isUpdate){
                try {
                    contactDataBase.getContactDao().updateContact(new ContactEntity(name,mail,id));
                    alertDialog.dismiss();
                    contactEntityList.clear();
                    contactEntityList.addAll(contactDataBase.getContactDao().fetchContacts());
                    contactAdaptor.notifyDataSetChanged();

                }catch (Exception e){
                    Toast.makeText(this, e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
            else {
                if (name.isEmpty() || mail.isEmpty()) {
                    Toast.makeText(this, "Field should not empty", Toast.LENGTH_SHORT).show();
                } else {
                    try {
                        contactDataBase.getContactDao().insetContact(new ContactEntity(name, mail, 0));
                        contactEntityList.clear();
                        contactEntityList.addAll(contactDataBase.getContactDao().fetchContacts());
                        contactAdaptor.notifyDataSetChanged();
                        checkVisibility();
                    } catch (Exception e) {
                        Toast.makeText(this, "" + e, Toast.LENGTH_LONG).show();
                        Log.d("TAG", "" + e);
                    }
                    alertDialog.dismiss();
                }
            }

        });
    }

    //3->setAdaptor method
    private void setAdaptor() {
        contactAdaptor = new ContactAdaptor(contactEntityList);
        rv.setAdapter(contactAdaptor);
        rv.setLayoutManager(new LinearLayoutManager(MainActivity.this, LinearLayoutManager.VERTICAL, false));
    }

    //4-> adaptor click method
    private void adaptorViewClick() {
        contactAdaptor.setContactAdaptorClick((view, index) -> {
            itemIndex=index;
            @SuppressLint("NotifyDataSetChanged") AlertDialog.Builder dialog = new AlertDialog.Builder(MainActivity.this)
                    .setMessage("Contacts")
                    .setTitle("Update And delete")
                    .setPositiveButton("Delete", (dialog1, which) -> {
                        try {
                            contactDataBase.getContactDao().deleteContact(new
                                    ContactEntity(contactEntityList.get(index).getName(),
                                    contactEntityList.get(index).getEmail(),
                                    contactEntityList.get(index).getId()));
                            contactEntityList.clear();
                            contactEntityList.addAll(contactDataBase.getContactDao().fetchContacts());
                            contactAdaptor.notifyDataSetChanged();
                            checkVisibility();
                        }catch (Exception e){
                            Toast.makeText(this, e.toString(), Toast.LENGTH_LONG).show();
                        }
                    })
                    .setNegativeButton("update",(dialog1, which) -> {
                        isUpdate=true;
                        showDialog();
                    }).setCancelable(true);
            dialog.create();
            dialog.show();
        });
    }

    private void checkVisibility(){
        if (contactEntityList.isEmpty()){
            viewStub.setVisibility(View.VISIBLE);
        }
        else {
            viewStub.setVisibility(View.GONE);
        }
    }
}
