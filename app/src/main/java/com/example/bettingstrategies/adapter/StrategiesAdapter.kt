package com.example.bettingstrategies.adapter

import android.content.Context
import android.content.Intent
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingstrategies.R
import com.example.bettingstrategies.databinding.ItemListBinding
import com.example.bettingstrategies.model.StrategiesData
import com.example.bettingstrategies.view.FullStrategies
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.item_list.view.*

class StrategiesAdapter(var context:Context,var strategiesList:ArrayList<StrategiesData>):RecyclerView.Adapter<StrategiesAdapter.StrategiesViewHolder>(){

    lateinit var sDataBase: DatabaseReference

    inner class StrategiesViewHolder(var v: ItemListBinding): RecyclerView.ViewHolder(v.root){}

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StrategiesViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val v = DataBindingUtil.inflate<ItemListBinding>(
            inflater, R.layout.item_list,parent,
            false)

        return StrategiesViewHolder(v)
    }

    override fun getItemCount(): Int {
        return strategiesList.size
    }

    override fun onBindViewHolder(holder: StrategiesViewHolder, position: Int) {
        val newList = strategiesList[position]
        holder.v.isStrategies = strategiesList[position]

        val fav: String? = newList.favourites
        if(fav.equals("1")){
            holder.v.btnSave?.isEnabled = false
            holder.v.btnSave?.text =  "SAVED"
        }

        holder.v.btnSave.setOnClickListener(){
            val pos = position + 1
            sDataBase = FirebaseDatabase.getInstance().getReference("Strategies").child("0$pos");
            sDataBase.child("favourites").setValue("1");
            strategiesList.clear()
        }

        holder.v.btnOpen.setOnClickListener(){

            val ImgFull = newList.ImgFull
            val name = newList.name
            val Full = newList.Full
            val pos = position + 1

            val mIntent = Intent(context, FullStrategies::class.java)
            mIntent.putExtra("ImgFull", ImgFull)
            mIntent.putExtra("name", name)
            mIntent.putExtra("Full", Full)
            mIntent.putExtra("position", pos.toString())
            mIntent.putExtra("fav", fav)
            context.startActivity(mIntent)
        }
    }
}