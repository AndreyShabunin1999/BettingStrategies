package com.example.bettingstrategies.view

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.example.bettingstrategies.MainActivity
import com.example.bettingstrategies.R
import com.example.bettingstrategies.StrategiesFavourites
import com.example.bettingstrategies.model.StrategiesData
import com.example.bettingstrategies.uitel.getProgressDrawable
import com.example.bettingstrategies.uitel.loadImage
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_full_strategies.*
import kotlinx.android.synthetic.main.activity_main.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase.getInstance


class FullStrategies : AppCompatActivity() {

    lateinit var btn_save: Button
    lateinit var sDataBase: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_strategies)

        /**get Data*/
        val strategiesIntent = intent
        val strategiesName = strategiesIntent.getStringExtra("name")
        val strategiesInfo = strategiesIntent.getStringExtra("Full")
        val strategiesImg = strategiesIntent.getStringExtra("ImgFull")
        val position = strategiesIntent.getStringExtra("position")
        val fav = strategiesIntent.getStringExtra("fav")

        myToolbar_full.title = strategiesName

        /**call text and images*/
        name.text = strategiesName
        info.text = strategiesInfo
        img.loadImage(strategiesImg, getProgressDrawable(this))

        btn_save = findViewById(R.id.btn_save_full)

        btn_fav_full.setOnClickListener(){
            val intent = Intent(this@FullStrategies, StrategiesFavourites::class.java)
            startActivity(intent)
            this.overridePendingTransition(0,0)
        }

        btn_back_full.setOnClickListener(){
            val intent = Intent(this@FullStrategies, MainActivity::class.java)
            startActivity(intent)
            this.overridePendingTransition(0,0)
        }


        if(fav.equals("1")){
            btn_save.isEnabled = false
            btn_save.text = resources.getString(R.string.text_btn_save2)
        }

        btn_save.setOnClickListener(){
            println("0$position")
            sDataBase = getInstance().getReference("Strategies").child("0$position");
            sDataBase.child("favourites").setValue("1");
        }
    }
}