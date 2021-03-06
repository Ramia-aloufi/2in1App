package com.example.a2in1app


import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.guessthephrase.MyAdapter
import java.lang.Exception

lateinit var tv1:TextView
lateinit var tv2:TextView
lateinit var rv:RecyclerView
lateinit var et:EditText
lateinit var btn:Button
 var phrase:String = ""

var r = 0
var txt = ""
var count = 10
var state:Int = 0
var convertPhrase = ""
lateinit var al:ArrayList<Items>
lateinit var starphrase:CharArray

data class Items(val textval :String,val color:Int)

class MainActivity2 : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main2)
         phrase = "coding dojo"


        initializeUI()
        btn.setOnClickListener {

            btnClicked()
        }
        check()
        Rvadapter()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu1, menu)
        return true
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.GuessaPhrase -> {
                val intent = Intent(this,MainActivity2::class.java)
                startActivity(intent)
                return true
            }
            R.id.NumberGame -> {
                val intent = Intent(this,MainActivity3::class.java)
                startActivity(intent)
                return true
            }R.id.MainMenu -> {
            val intent = Intent(this,MainActivity::class.java)
            startActivity(intent)
            return true
        }
        }
        return super.onOptionsItemSelected(item)
    }

    fun check(){
        if (count == 0) {
            getalert("Guessed Letter", "Game Over..\n" + "Phrase is $phrase")

        }
    }

    override fun recreate() {
        super.recreate()
        count = 10
        state = 0
        txt = ""
        et.text.clear()
        et.isFocusable = false
        tv1.text = ""
    }

    fun initializeUI(){
        tv1 = findViewById(R.id.tv1)
        tv2 = findViewById(R.id.tv2)
        rv = findViewById(R.id.rv)
        et = findViewById(R.id.et)
        btn = findViewById(R.id.btn)
        al = ArrayList()
        convertPhraseToStar()


    }
    fun convertPhraseToStar(){
        for(i in phrase){
            if (i.isWhitespace()){
                convertPhrase += " "
            }else{
                convertPhrase += "*"
            }
        }
        // userguess = phrase.replace("[^A-Za-z0-9]","*")
        tv1.text = convertPhrase
        starphrase = convertPhrase.toCharArray()
    }
    fun btnClicked() {
        var UserInt = et.text.toString().lowercase()
        if (state == 0) {
            if (UserInt == phrase) {
                getalert("Guessed Phrase", "Welldone..\n" + "Phrase is $phrase")
                et.text.clear()
            } else {
                getalert1("Guessed Phrase", "Wrong..\n" + "Guess a Letter",)
                et.text.clear()
                al.add(Items("Wrong guess : $UserInt",1))
                rv.adapter?.notifyDataSetChanged()
            }
        } else {

            if (phrase == tv1.text.toString()) {
                getalert("Guessed Letter", "Welldone..\n" + "Phrase is $phrase")
            }else{
                convertToLetter(UserInt)
                count --
                al.add(Items("$count guessess remining", 2))
                et.text.clear()
                check()
            }
            et.text.clear()
        }
    }

    fun getalert(title:String, message:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> this.recreate()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }
    fun getalert1(title:String, message:String){
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage(message)
            .setPositiveButton("Yes", DialogInterface.OnClickListener {
                    dialog, id -> guessLetter()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setTitle(title)
        alert.show()
    }

    fun convertToLetter(UserIn: String){

        Log.d("lngth","${phrase[1]}")
        for (i in phrase.indices){
            if(phrase[i].toString() == UserIn ){
                starphrase[i] = phrase[i]
                r ++
            }
            txt += starphrase[i]
        }

        Log.d("lngth","$phrase")
        Log.d("starphrase","$starphrase")
        Log.d("txt","$txt")

        tv1.text = txt
        if (r!==0) {
            al.add(Items(" found $r ${UserIn.capitalize()}(s)", 0))
        }else {
            al.add(Items(" found $r ${UserIn.capitalize()}(s)", 1))
        }
        rv.adapter?.notifyDataSetChanged()
        txt = ""
        r = 0
    }


    fun Rvadapter(){
        rv.adapter = MyAdapter(al)
        rv.layoutManager = LinearLayoutManager(this)
    }

    fun guessLetter(){
        et.hint = "Guess a Letter"
        tv2.text = "Guess a Letter"
        state = 1
    }

}