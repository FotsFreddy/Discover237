package com.example.discover237.ui.attractionList

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.discover237.R
import com.example.discover237.data.model.Attraction

class AttractionAdapter(
    private val attractions: List<Attraction>,
    private val favoritedIds: MutableSet<String>,
    private val onItemClick: (Attraction) -> Unit
) : RecyclerView.Adapter<AttractionAdapter.AttractionViewHolder>() {

    class AttractionViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val attractionImg: ImageView = itemView.findViewById(R.id.attractionImage)
        val nameText: TextView = itemView.findViewById(R.id.attractionNameText)
        val descText: TextView = itemView.findViewById(R.id.attractionShortDescText)
        val openingHrs: TextView = itemView.findViewById(R.id.attractionOpeningHours)
        val favoriteIndicator: ImageView = itemView.findViewById(R.id.favoriteIndicator)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AttractionViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_attraction, parent, false)
        return AttractionViewHolder(view)
    }

    override fun onBindViewHolder(holder: AttractionViewHolder, position: Int) {
        val attraction = attractions[position]
        holder.nameText.text = attraction.name
        holder.descText.text = attraction.shortDescription
        holder.openingHrs.text = attraction.openingHours
        holder.favoriteIndicator.visibility = if (attraction.id in favoritedIds) View.VISIBLE else View.GONE

        val imageName = attraction.imageFileName.substringBeforeLast(".")
        val context = holder.itemView.context
        val resId = context.resources.getIdentifier(imageName, "drawable", context.packageName)
        if (resId != 0) holder.attractionImg.setImageResource(resId)
        else holder.attractionImg.setImageResource(R.drawable.image_not_available)

        holder.itemView.setOnClickListener { onItemClick(attraction) }
    }

    override fun getItemCount(): Int = attractions.size
}