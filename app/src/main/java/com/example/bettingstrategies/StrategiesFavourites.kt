package com.example.bettingstrategies

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.widget.Toolbar
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bettingstrategies.adapter.FavouritesAdapter
import com.example.bettingstrategies.model.StrategiesData
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.activity_strategies_favourites.*

class StrategiesFavourites : AppCompatActivity() {

    lateinit var sDataBase: DatabaseReference
    private lateinit var favouritesList:ArrayList<StrategiesData>
    private lateinit var favouritesAdapter:FavouritesAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_strategies_favourites)

        /**Инициализация*/
        favouritesList = ArrayList()
        favouritesAdapter = FavouritesAdapter(this@StrategiesFavourites, favouritesList)
        recyclerFavourites.layoutManager = LinearLayoutManager(this@StrategiesFavourites)
        recyclerFavourites.setHasFixedSize(true)
        /**Получение данных от firebase*/
        getStrategiesData()

        btn_back_fav.setOnClickListener(){
            val intent = Intent(this@StrategiesFavourites, MainActivity::class.java)
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
                        var str: String = strategiesSnapshot.child("favourites").getValue().toString()

                        if(str.equals("1")){
                            val strategies = strategiesSnapshot.getValue(StrategiesData::class.java)
                            favouritesList.add(strategies!!)
                        }
                    }
                    recyclerFavourites.adapter = favouritesAdapter
                }
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(this@StrategiesFavourites,
                    error.message, Toast.LENGTH_SHORT).show()
            }
        })
    }
}