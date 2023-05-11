package com.example.todolist

import android.annotation.SuppressLint
import android.content.DialogInterface
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {

    lateinit var item:EditText
    lateinit var add:Button
    lateinit var listview:ListView

    lateinit var itemList:ArrayList<String>

    var filehelper=filehelper()



    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        item=findViewById(R.id.editText)
        add=findViewById(R.id.button)
        listview=findViewById(R.id.list)

        itemList=filehelper.readData(this)

        var arrayadapter=ArrayAdapter(this
            ,android.R.layout.simple_list_item_1,android.R.id.text1,itemList)
        listview.adapter=arrayadapter

        add.setOnClickListener {
            var itemname:String=item.text.toString()
            itemList.add(itemname)
            item.setText("")
            filehelper.writeData(itemList,applicationContext)
            arrayadapter.notifyDataSetChanged()
        }
        listview.setOnItemClickListener { adapterView, view, position, l ->
            var alert=AlertDialog.Builder(this)
            alert.setTitle("Delete")
            alert.setMessage("Do you want to delete this item from the list")
            alert.setCancelable(false)
            alert.setNegativeButton("No", DialogInterface.OnClickListener{dialogInterface, i ->
                dialogInterface.cancel()
            })
            alert.setPositiveButton("yes", DialogInterface.OnClickListener{dialogInterface, i ->
                itemList.removeAt(position)
                arrayadapter.notifyDataSetChanged()
                filehelper.writeData(itemList,applicationContext)
            })
            alert.create().show()
        }

    }
}