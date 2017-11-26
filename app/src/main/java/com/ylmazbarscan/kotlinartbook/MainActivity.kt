package com.ylmazbarscan.kotlinartbook

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val artNameArray=ArrayList<String>()
        val artImageArray=ArrayList<Bitmap>()

        val arrayAdapter=ArrayAdapter(this,android.R.layout.simple_list_item_1,artNameArray)

        listView.adapter=arrayAdapter
        try {
            //this.deleteDatabase("Arts")
            val database=this.openOrCreateDatabase("Arts", Context.MODE_PRIVATE,null)
            database.execSQL("CREATE TABLE IF NOT EXISTS Arts (name VARCHAR,image BLOB)")
            val cursor=database.rawQuery("SELECT * FROM Arts",null)
            val nameIx=cursor.getColumnIndex("name")
            val imageIx=cursor.getColumnIndex("image")

            cursor.moveToFirst()

            while (cursor!=null){
                val byteArray=cursor.getBlob(imageIx)
                val image=BitmapFactory.decodeByteArray(byteArray,0,byteArray.size)
                artNameArray.add(cursor.getString(nameIx))
                artImageArray.add(image)
                cursor.moveToNext()
                arrayAdapter.notifyDataSetChanged()
            }

            cursor?.close()

            listView.onItemClickListener=AdapterView.OnItemClickListener{parent: AdapterView<*>?, view: View?, position: Int, id: Long ->  
                val intent=Intent(applicationContext,Main2Activity::class.java)
                intent.putExtra("name",artNameArray[position])
                intent.putExtra("info","old")
                val chosen= Globals.Chosen
                chosen.chosenImage=artImageArray[position]
                startActivity(intent)
            }

        }catch (e:Exception){
            e.printStackTrace()
        }
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val menuInflater=menuInflater
        menuInflater.inflate(R.menu.add_art,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if (item?.itemId==R.id.add_art){
            val intent= Intent(applicationContext,Main2Activity::class.java)
            intent.putExtra("info","new")
            startActivity(intent)
        }

        return super.onOptionsItemSelected(item)
    }
}