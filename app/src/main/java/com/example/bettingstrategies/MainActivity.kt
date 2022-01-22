package com.example.bettingstrategies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatDelegate
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettingstrategies.adapter.StrategiesAdapter
import com.example.bettingstrategies.model.StrategiesData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var sDataBase: DatabaseReference
    private lateinit var strategiesList:ArrayList<StrategiesData>
    private lateinit var strategiesAdapter:StrategiesAdapter
    lateinit var btn_fav: ImageView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /**Инициализация*/
        btn_fav = findViewById(R.id.btn_fav)
        strategiesList = ArrayList()
        strategiesAdapter = StrategiesAdapter(this, strategiesList)
        recyclerStrategies.layoutManager = LinearLayoutManager(this)
        recyclerStrategies.setHasFixedSize(true)
        /**Получение данных от firebase*/
        getStrategiesData()

        /**Слушатель клика по сердцу (Favourites) в Toolbar*/
        btn_fav.setOnClickListener(){
            val intent = Intent(this@MainActivity, StrategiesFavourites::class.java)
            finish()
            startActivity(intent)
            this.overridePendingTransition(0,0)
        }
    }

    /**Функция получения данных о стратегиях из Firebase*/
    private fun getStrategiesData() {

        sDataBase = FirebaseDatabase.getInstance().getReference("Strategies")
        sDataBase.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()){
                    for (strategiesSnapshot in snapshot.children){
                        val strategies = strategiesSnapshot.getValue(StrategiesData::class.java)
                        strategiesList.add(strategies!!)
                    }
                    recyclerStrategies.adapter = strategiesAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@MainActivity,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
