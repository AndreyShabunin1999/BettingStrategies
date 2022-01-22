package com.example.bettingstrategies.adapter

import android.content.Context
import android.content.Intent
import android.content.Intent.getIntent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingstrategies.R
import com.example.bettingstrategies.databinding.ItemFavouritesListBinding
import com.example.bettingstrategies.databinding.ItemListBinding
import com.example.bettingstrategies.model.StrategiesData
import com.example.bettingstrategies.view.FullStrategies
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.activity_main.*
import androidx.core.content.ContextCompat.startActivity

import android.content.Intent.getIntent
import androidx.core.content.ContextCompat


class FavouritesAdapter(var context: Context, var favouritesList:ArrayList<StrategiesData>):RecyclerView.Adapter<FavouritesAdapter.FavouritesViewHolder>(){

    lateinit var sDataBase: DatabaseReference

    inner class FavouritesViewHolder(var v: ItemFavouritesListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavouritesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemFavouritesListBinding>(
            inflater, R.layout.item_favourites_list,parent,
            false)

        return FavouritesViewHolder(v)
    }

    override fun onBindViewHolder(holder: FavouritesViewHolder, position: Int) {
        val newList = favouritesList[position]
        holder.v.isFavourites = favouritesList[position]

        /**Удаление из избранного*/
        holder.v.btnDeleteFav.setOnClickListener(){
            val name = newList.name

            sDataBase = FirebaseDatabase.getInstance().getReference("Strategies")
            sDataBase.addValueEventListener(object : ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    if (snapshot.exists()) {
                        for (strategiesSnapshot in snapshot.children) {
                            var str_name: String = strategiesSnapshot.child("name").getValue().toString()

                            if (str_name.equals(name)) {
                                sDataBase = FirebaseDatabase.getInstance().getReference("Strategies").child(strategiesSnapshot.getKey().toString())
                                sDataBase.child("favourites").setValue("0")
                                favouritesList.clear()
                            }
                        }

                        for(strategiesSnapshot in snapshot.children) {
                            var str_fav: String = strategiesSnapshot.child("favourites").getValue().toString()
                            if (str_fav.equals("1")){
                                val strategies = strategiesSnapshot.getValue(StrategiesData::class.java)
                                favouritesList.add(strategies!!)
                            }
                        }
                    }
                }

                override fun onCancelled(error: DatabaseError) {
                    Toast.makeText(context,
                        error.message, Toast.LENGTH_SHORT).show()
                }

            })
        }

        /**Открытие полной информации о стратегии*/
        holder.v.btnOpenFav.setOnClickListener(){

            val ImgFull = newList.ImgFull
            val name = newList.name
            val Full = newList.Full
            val pos = position + 1
            val fav = newList.favourites

            val mIntent = Intent(context, FullStrategies::class.java)
            mIntent.putExtra("ImgFull", ImgFull)
            mIntent.putExtra("name", name)
            mIntent.putExtra("Full", Full)
            mIntent.putExtra("position", pos.toString())
            mIntent.putExtra("fav", fav)
            context.startActivity(mIntent)
        }
    }

    override fun getItemCount(): Int {
        return favouritesList.size
    }




}

