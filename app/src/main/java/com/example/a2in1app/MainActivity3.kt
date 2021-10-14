package com.example.a2in1app


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.util.ArrayList

class MainActivity3 : AppCompatActivity() {

    lateinit var btn:Button
    lateinit var rv: RecyclerView
    lateinit var et:EditText
    lateinit var al:ArrayList<String>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main3)
        al = ArrayList()
        var guesstry = 3
        btn = findViewById(R.id.btn)
        rv = findViewById(R.id.rv)
        et = findViewById(R.id.et)
        val randomNumber = (0..10).random()


        btn.setOnClickListener {
            var txt = et.text.toString()

            if(txt == ""){
                Toast.makeText(applicationContext,"Type a text", Toast.LENGTH_SHORT).show()

            }else{
                al.add(txt)
            }
            if(txt.toInt() == randomNumber){
                al.add("Welldone!")
                disableEntry()
                showAlertDialog("Welldone!..Play again?")

            }else{
                guesstry -= 1
                al.add("wrong try again..")
                al.add("You Have "+guesstry+"left")
            }

            if(guesstry == 0){
                disableEntry()
                showAlertDialog("answer is $randomNumber.Play again?")

            }


            rv.adapter?.notifyDataSetChanged()
            et.text.clear()
            et.clearFocus()


        }

        rv.adapter = MA(al)

        rv.layoutManager = LinearLayoutManager(this)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.GuessaPhrase -> {
                val intent = Intent(this, MainActivity2::class.java)
                startActivity(intent)
                return true
            }
            R.id.NumberGame -> {
                val intent = Intent(this, MainActivity3::class.java)
                startActivity(intent)
                return true
            }
            R.id.MainMenu -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)

    }



    fun showAlertDialog(title: String) {
        val db = AlertDialog.Builder(this)

        db.setMessage(title)
            .setCancelable(false)

            .setPositiveButton("Go", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = db.create()
        alert.setTitle("Game Over")
        alert.show()
    }

    fun disableEntry(){
        btn.isEnabled = false
        btn.isClickable = false
        et.isEnabled = false
        et.isClickable = false
    }
}