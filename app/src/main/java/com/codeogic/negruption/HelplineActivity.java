package com.codeogic.negruption;

import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.provider.Settings;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class HelplineActivity extends AppCompatActivity implements AdapterView.OnItemClickListener{


    ListView listViewHelpline;
    ArrayList<String> arrayListItems;
    ArrayAdapter<String> arrayAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_helpline);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        listViewHelpline = (ListView)findViewById(R.id.listView_helpline);
        arrayListItems=new ArrayList<String>();
        arrayListItems.add(" Airports");
        arrayListItems.add("Banking");
        arrayListItems.add("Bureau Of Immigration");
        arrayListItems.add("Commercial/Sales Tax ,VAT");
        arrayListItems.add("Customs,Excise & Service Tax");
        arrayListItems.add("Education");
        arrayListItems.add("Electricity & Power Supply");
        arrayListItems.add("Food & Drug Administration");
        arrayListItems.add("Food,Civil Supplies & Consumer Rights");
        arrayListItems.add("Foreign Trade");
        arrayListItems.add("Forest");
        arrayListItems.add("Health And Family Welfare");
        arrayListItems.add("Income Tax");
        arrayListItems.add("Insurance");
        arrayListItems.add("Judiciary");
        arrayListItems.add("Labour");
        arrayListItems.add("Municipal Services");
        arrayListItems.add("Passport");
        arrayListItems.add("Pension");
        arrayListItems.add("Police");
        arrayListItems.add("Post Office");
        arrayListItems.add("Public Undertakings");
        arrayListItems.add("Public Services");
        arrayListItems.add("Public Works Department");
        arrayListItems.add("Railways");
        arrayListItems.add("Religious Trusts");
        arrayListItems.add("Revenue");
        arrayListItems.add("Slum Development");
        arrayListItems.add("Social Welfare");
        arrayListItems.add("Stamps And Registration");
        arrayListItems.add("Telecom Services");
        arrayListItems.add("Transport");
        arrayListItems.add("Urban Development Authorities");
        arrayListItems.add("Water Sewage");

        arrayAdapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayListItems);


        listViewHelpline.setAdapter(arrayAdapter);
        listViewHelpline.setOnItemClickListener(this);


    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();

        if(id == android.R.id.home){

            finish();
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {



        switch (position){

            case 0 :

                AlertDialog.Builder builder=new AlertDialog.Builder(this);
                builder.setTitle("HelpLine");
                builder.setMessage(" Do You Want to make a Call ? ");

                builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"+91 124 3376000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder.setNegativeButton("No",null);
                builder.create().show();
                break;

            case 1 :

                AlertDialog.Builder builder1=new AlertDialog.Builder(this);
                builder1.setTitle("HelpLine");
                builder1.setMessage(" Do You Want to make a Call ? ");

                builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"+91-11-25652011"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder1.setNegativeButton("No",null);
                builder1.create().show();
                break;

            case 2 :

                AlertDialog.Builder builder2=new AlertDialog.Builder(this);
                builder2.setTitle("HelpLine");
                builder2.setMessage(" Do You Want to make a Call ? ");

                builder2.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"011-26102622"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder2.setNegativeButton("No",null);
                builder2.create().show();
                break;

            case 3 :

                AlertDialog.Builder builder3=new AlertDialog.Builder(this);
                builder3.setTitle("HelpLine");
                builder3.setMessage(" Do You Want to make a Call ? ");

                builder3.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"91 0361-2232661"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder3.setNegativeButton("No",null);
                builder3.create().show();
                break;

            case 4 :

                AlertDialog.Builder builder4=new AlertDialog.Builder(this);
                builder4.setTitle("HelpLine");
                builder4.setMessage(" Do You Want to make a Call ? ");

                builder4.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"011-25652088"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder4.setNegativeButton("No",null);
                builder4.create().show();
                break;

            case 5 :

                AlertDialog.Builder builder5=new AlertDialog.Builder(this);
                builder5.setTitle("HelpLine");
                builder5.setMessage(" Do You Want to make a Call ? ");

                builder5.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"91-11-22509256"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder5.setNegativeButton("No",null);
                builder5.create().show();
                break;

            case 6 :

                AlertDialog.Builder builder6=new AlertDialog.Builder(this);
                builder6.setTitle("HelpLine");
                builder6.setMessage(" Do You Want to make a Call ? ");

                builder6.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-4000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder6.setNegativeButton("No",null);
                builder6.create().show();
                break;

            case 7 :

                AlertDialog.Builder builder7=new AlertDialog.Builder(this);
                builder7.setTitle("HelpLine");
                builder7.setMessage(" Do You Want to make a Call ? ");

                builder7.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800 222 365"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder7.setNegativeButton("No",null);
                builder7.create().show();
                break;

            case 8:

                AlertDialog.Builder builder8=new AlertDialog.Builder(this);
                builder8.setTitle("HelpLine");
                builder8.setMessage(" Do You Want to make a Call ? ");

                builder8.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-4000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder8.setNegativeButton("No",null);
                builder8.create().show();
                break;

            case 9:

                AlertDialog.Builder builder9=new AlertDialog.Builder(this);
                builder9.setTitle("HelpLine");
                builder9.setMessage(" Do You Want to make a Call ? ");

                builder9.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800111550"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder9.setNegativeButton("No",null);
                builder9.create().show();
                break;


            case 10 :

                AlertDialog.Builder builder10=new AlertDialog.Builder(this);
                builder10.setTitle("HelpLine");
                builder10.setMessage(" Do You Want to make a Call ? ");

                builder10.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800118600"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder10.setNegativeButton("No",null);
                builder10.create().show();
                break;

            case 11 :

                AlertDialog.Builder builder11=new AlertDialog.Builder(this);
                builder11.setTitle("HelpLine");
                builder11.setMessage(" Do You Want to make a Call ? ");

                builder11.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"18001801104"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder11.setNegativeButton("No",null);
                builder11.create().show();
                break;

            case 12 :

                AlertDialog.Builder builder12=new AlertDialog.Builder(this);
                builder12.setTitle("HelpLine");
                builder12.setMessage(" Do You Want to make a Call ? ");

                builder12.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-425- 2229"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder12.setNegativeButton("No",null);
                builder12.create().show();
                break;

            case 13 :

                AlertDialog.Builder builder13=new AlertDialog.Builder(this);
                builder13.setTitle("HelpLine");
                builder13.setMessage(" Do You Want to make a Call ? ");

                builder13.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"91-044- 28332220"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder13.setNegativeButton("No",null);
                builder13.create().show();
                break;


            case 14 :

                AlertDialog.Builder builder14=new AlertDialog.Builder(this);
                builder14.setTitle("HelpLine");
                builder14.setMessage(" Do You Want to make a Call ? ");

                builder14.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"23388922"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder14.setNegativeButton("No",null);
                builder14.create().show();
                break;

            case 15 :

                AlertDialog.Builder builder15=new AlertDialog.Builder(this);
                builder15.setTitle("HelpLine");
                builder15.setMessage(" Do You Want to make a Call ? ");

                builder15.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"18003456526"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder15.setNegativeButton("No",null);
                builder15.create().show();
                break;


            case 16 :

                AlertDialog.Builder builder16=new AlertDialog.Builder(this);
                builder16.setTitle("HelpLine");
                builder16.setMessage(" Do You Want to make a Call ? ");

                builder16.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-1800-024"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder16.setNegativeButton("No",null);
                builder16.create().show();
                break;


            case 17 :

                AlertDialog.Builder builder17=new AlertDialog.Builder(this);
                builder17.setTitle("HelpLine");
                builder17.setMessage(" Do You Want to make a Call ? ");

                builder17.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-258-1800"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder17.setNegativeButton("No",null);
                builder17.create().show();
                break;

            case 18 :

                AlertDialog.Builder builder18=new AlertDialog.Builder(this);
                builder18.setTitle("HelpLine");
                builder18.setMessage(" Do You Want to make a Call ? ");

                builder18.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800112211"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder18.setNegativeButton("No",null);
                builder18.create().show();
                break;

            case 19 :

                AlertDialog.Builder builder19=new AlertDialog.Builder(this);
                builder19.setTitle("HelpLine");
                builder19.setMessage(" Do You Want to make a Call ? ");

                builder19.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"919156111999"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder19.setNegativeButton("No",null);
                builder19.create().show();
                break;


            case 20 :

                AlertDialog.Builder builder20=new AlertDialog.Builder(this);
                builder20.setTitle("HelpLine");
                builder20.setMessage(" Do You Want to make a Call ? ");

                builder20.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800 119888"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder20.setNegativeButton("No",null);
                builder20.create().show();
                break;

            case 21 :

                AlertDialog.Builder builder21=new AlertDialog.Builder(this);
                builder21.setTitle("HelpLine");
                builder21.setMessage(" Do You Want to make a Call ? ");

                builder21.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-4000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder21.setNegativeButton("No",null);
                builder21.create().show();
                break;

            case 22 :

                AlertDialog.Builder builder22=new AlertDialog.Builder(this);
                builder22.setTitle("HelpLine");
                builder22.setMessage(" Do You Want to make a Call ? ");

                builder22.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-4000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder22.setNegativeButton("No",null);
                builder22.create().show();
                break;

            case 23 :

                AlertDialog.Builder builder23=new AlertDialog.Builder(this);
                builder23.setTitle("HelpLine");
                builder23.setMessage(" Do You Want to make a Call ? ");

                builder23.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"18002664499"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder23.setNegativeButton("No",null);
                builder23.create().show();
                break;

            case 24:

                AlertDialog.Builder builder24=new AlertDialog.Builder(this);
                builder24.setTitle("HelpLine");
                builder24.setMessage(" Do You Want to make a Call ? ");

                builder24.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-111-139"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder24.setNegativeButton("No",null);
                builder24.create().show();
                break;

            case 25 :

                AlertDialog.Builder builder25=new AlertDialog.Builder(this);
                builder25.setTitle("HelpLine");
                builder25.setMessage(" Do You Want to make a Call ? ");

                builder25.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800112211"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder25.setNegativeButton("No",null);
                builder25.create().show();
                break;

            case 26 :

                AlertDialog.Builder builder26=new AlertDialog.Builder(this);
                builder26.setTitle("HelpLine");
                builder26.setMessage(" Do You Want to make a Call ? ");

                builder26.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-6163"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder26.setNegativeButton("No",null);
                builder26.create().show();
                break;

            case 27 :

                AlertDialog.Builder builder27=new AlertDialog.Builder(this);
                builder27.setTitle("HelpLine");
                builder27.setMessage(" Do You Want to make a Call ? ");

                builder27.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"800-11-0031"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder27.setNegativeButton("No",null);
                builder27.create().show();
                break;

            case 28 :

                AlertDialog.Builder builder28=new AlertDialog.Builder(this);
                builder28.setTitle("HelpLine");
                builder28.setMessage(" Do You Want to make a Call ? ");

                builder28.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-11-4000"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder28.setNegativeButton("No",null);
                builder28.create().show();
                break;


            case 29 :

                AlertDialog.Builder builder29=new AlertDialog.Builder(this);
                builder29.setTitle("HelpLine");
                builder29.setMessage(" Do You Want to make a Call ? ");

                builder29.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800 22 6161"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder29.setNegativeButton("No",null);
                builder29.create().show();
                break;

            case 30 :

                AlertDialog.Builder builder30=new AlertDialog.Builder(this);
                builder30.setTitle("HelpLine");
                builder30.setMessage(" Do You Want to make a Call ? ");

                builder30.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"800 180 2428"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder30.setNegativeButton("No",null);
                builder30.create().show();
                break;

            case 31 :

                AlertDialog.Builder builder31=new AlertDialog.Builder(this);
                builder31.setTitle("HelpLine");
                builder31.setMessage(" Do You Want to make a Call ? ");

                builder31.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"1800-22-1401"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder31.setNegativeButton("No",null);
                builder31.create().show();
                break;

            case 32 :

                AlertDialog.Builder builder32=new AlertDialog.Builder(this);
                builder32.setTitle("HelpLine");
                builder32.setMessage(" Do You Want to make a Call ? ");

                builder32.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+"23092510"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder32.setNegativeButton("No",null);
                builder32.create().show();
                break;

            case 33 :

                AlertDialog.Builder builder33=new AlertDialog.Builder(this);
                builder33.setTitle("HelpLine");
                builder33.setMessage(" Do You Want to make a Call ? ");

                builder33.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        try {

                            Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:"+" 1800-11-6163"));
                            startActivity(intent);
                        }
                        catch (android.content.ActivityNotFoundException e){

                            Toast.makeText(HelplineActivity.this,"Call Could Not be Made",Toast.LENGTH_LONG).show();
                        }


                    }
                });

                builder33.setNegativeButton("No",null);
                builder33.create().show();
                break;

        }

    }
}
