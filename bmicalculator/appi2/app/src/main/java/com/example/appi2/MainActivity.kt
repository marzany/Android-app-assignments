package com.example.appi2

import android.R.array
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.ui.AppBarConfiguration
import androidx.preference.PreferenceManager.getDefaultSharedPreferences
import com.example.appi2.databinding.ActivityMainBinding
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.util.*


class MainActivity : AppCompatActivity() {

    lateinit var lista : ArrayList<String>

    private lateinit var binding: ActivityMainBinding

    lateinit var bmi : TextView
    lateinit var editTextNumber : EditText
    lateinit var button : Button
    lateinit var button2 : Button
    lateinit var preferences : SharedPreferences
    

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        setSupportActionBar(binding.toolbar)

        editTextNumber = findViewById(R.id.editTextNumber)
        bmi = findViewById(R.id.textView)
        button = findViewById(R.id.button)
        button2 = findViewById(R.id.button2)


        preferences = getDefaultSharedPreferences(applicationContext)

        lista = try {
            getList()
        } catch (e: NullPointerException) {
            arrayListOf<String>()
        }




        button.setOnClickListener {

            var teksti = ""
            teksti = preferences.getString("signature", "TYHJÄ").toString()

            val pituus = teksti.toFloat()
            val paino = editTextNumber.text.toString().toFloat()

            calculation(pituus, paino)

        }

        button2.setOnClickListener {

            val intent = Intent(this@MainActivity, MainActivity2::class.java)
            setLists(lista)
            intent.putExtra("bmit",lista)
            //lista.removeAt(0) <--- vanhassa listassa virheellinen syöte, rikkoo chartin.
            //intent.putExtra("tulokset",lista) <-- vanha lista
            startActivity(intent)

        }



    }
    fun setLists(list:ArrayList<String>){
        val gson = Gson()
        val json = gson.toJson(list)//converting list to Json
        val editor = preferences.edit()
        editor.putString("bmit",json)
        editor.apply()
    }
    //getting the list from shared preference
    fun getList():ArrayList<String>{
        val gson = Gson()
        val json = preferences.getString("bmit",null)
        val type = object : TypeToken<ArrayList<String>>(){}.type//converting the json to list
        return gson.fromJson(json,type)//returning the list
    }

    private fun calculation(pituus: Float, paino: Float) {

        val result = (paino/((pituus/100)*(pituus/100)))
        lista += result.toString()

        bmi.text = result.toString()


    }


    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when (item.itemId) {
            R.id.action_settings -> {
                val intent = Intent(this@MainActivity, SettingsActivity::class.java)
                startActivity(intent)
                true
            }


            else -> {
                super.onOptionsItemSelected(item)
            }
        }
    }
}