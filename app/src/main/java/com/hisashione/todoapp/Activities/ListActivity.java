package com.hisashione.todoapp.Activities;

import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.hisashione.todoapp.Fragments.DateFragment;
import com.hisashione.todoapp.Models.ItemInformation;
import com.hisashione.todoapp.Models.UserInformation;
import com.hisashione.todoapp.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;

import static android.R.attr.startYear;

public class ListActivity extends AppCompatActivity  {


    Button dateBTN, saveBTN;
    TextView itemTxt;
    FirebaseAuth firebaseAuth;
    DatabaseReference databaseReference;
    ListView mainList;
    ArrayList <String> keys;
    ArrayList <String> itemsList;
    String keyValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        firebaseAuth = FirebaseAuth.getInstance();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        dateBTN =  (Button) findViewById(R.id.dateList);
        saveBTN = (Button) findViewById(R.id.saveListBTN);
        itemTxt = (TextView) findViewById(R.id.itemText);
        mainList = (ListView) findViewById(R.id.itemList_1);



        loadItemData();

        mainList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                /**/

                keyValue = keys.get(i);

                AlertDialog dialog = new AlertDialog.Builder(ListActivity.this).create();
                dialog.setTitle("Eliminar Articulo?");
                dialog.setMessage("Deseas elimiar: " + itemsList.get(i));
                dialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int which) {


                                Log.d("Tag_2", "Key selected: " + keyValue );

                                FirebaseDatabase database = FirebaseDatabase.getInstance();
                                DatabaseReference reference = database.getReference();
                                FirebaseUser user  = firebaseAuth.getCurrentUser();


                                reference.child("list" + user.getUid()).child(keyValue).removeValue();

                                dialog.dismiss();
                            }
                        });

                dialog.show();

            }
        });



        dateBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                BottomSheetDialogFragment bottomSheetDialogFragment = new DateFragment();
                bottomSheetDialogFragment.show(getSupportFragmentManager(), bottomSheetDialogFragment.getTag());
            }
        });


        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



                String itemList = itemTxt.getText().toString();
                String dateItem = dateBTN.getText().toString();


                if (itemList.length() == 0 || dateItem.equals("Fecha")){
                    if (dateItem.equals("Fecha")){

                        Toast.makeText(getApplicationContext(), "Selecciona una fecha", Toast.LENGTH_SHORT).show();

                    }
                    if (itemList.length() == 0){

                        itemTxt.setError("Campo Vacio");
                    }


                }else {

                    ItemInformation itemInformation = new ItemInformation();
                    itemInformation.setItem(itemList);
                    itemInformation.setDateItem(dateItem);
                    //UserInformation userInformation = new UserInformation(nameText.getText().toString(), adressText.getText().toString());

                    FirebaseUser user1  = firebaseAuth.getCurrentUser();

                    //Push Add Item to List
                    databaseReference.child("list" + user1.getUid() ).push().setValue(itemInformation);

                    Log.d("Tag_2", "Save Item" + itemList + "   " + dateItem);




                }





            }
        });





    }




    private void  loadItemData (){


        try {

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference reference = database.getReference();
            FirebaseUser user  = firebaseAuth.getCurrentUser();

            reference.child("list" + user.getUid()).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Log.d("Tag_2", "Values" + dataSnapshot);




                    keys = new ArrayList<String>();
                    itemsList = new ArrayList<String>();


                    for (DataSnapshot dataSnapshot1 : dataSnapshot.getChildren()){
                       Log.d("Tag_2", "Keys" +  dataSnapshot1.getKey());
                        keys.add(dataSnapshot1.getKey());

                        ItemInformation itemInformation = dataSnapshot1.getValue(ItemInformation.class);


                        String item_ = itemInformation.getItem();
                        String itemDate = itemInformation.getDateItem();

                        itemsList .add(item_ + "   " + itemDate);


                        Log.d("Tag_2", "Item Info:" + itemInformation.getDateItem());



                   }

                    ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, itemsList);
                    mainList.setAdapter(adapter);
                    adapter.notifyDataSetChanged();


                    Log.d("Tag_2", "Keys: " + keys);



                   /* if (dataSnapshot.getChildrenCount() > 0){

                       // itemsMap((Map<String,Object>) dataSnapshot.getValue());
                    }*/






                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


        }catch (Exception e){

            e.printStackTrace();
        }

    }


    private void itemsMap(Map<String,Object> users) {

        ArrayList<String> itemsArray = new ArrayList<>();






        //iterate through each user, ignoring their UID
        for (Map.Entry<String, Object> entry : users.entrySet()){

            //Get user map


            Map map = (Map) entry.getValue();

            Log.d("Tag_2", "Map " + map);
            //Get phone field and append to list

            String item = (String) map.get("item");
            String dateItem = (String) map.get("dateItem");


            itemsArray.add(item + "  "  + dateItem);


        }


        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_list_item_1, itemsArray);
        mainList.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        Log.d("Tag_2", "Item: " + itemsArray);

    }

}
