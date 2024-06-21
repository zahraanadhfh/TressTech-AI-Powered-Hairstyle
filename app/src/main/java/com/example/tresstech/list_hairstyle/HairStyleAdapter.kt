package com.example.tresstech.list_hairstyle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.tresstech.databinding.ItemListHairstyleBinding
import com.example.tresstech.list_hairstyle.hairstyle.HairStyle
import android.widget.Filter
import android.widget.Filterable

class HairStyleAdapter (private val hairStyleList: List<HairStyle>) :
    RecyclerView.Adapter<HairStyleAdapter.HairStyleViewHolder>(),Filterable {

    private var filteredHairStyleList: List<HairStyle> = hairStyleList
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HairStyleViewHolder {
        val binding = ItemListHairstyleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return HairStyleViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HairStyleViewHolder, position: Int) {
        val hairStyle = hairStyleList[position]
        holder.bind(hairStyle)
    }

    override fun getItemCount(): Int {
        return hairStyleList.size
    }
    override fun getFilter(): Filter { //fiturFilterMasihDalamtahapPengembangan
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val queryString = constraint.toString().lowercase()

                val filteredList = if (queryString.isEmpty()) {
                    hairStyleList
                } else {
                    hairStyleList.filter {
                        it.name.lowercase().contains(queryString)
                    }
                }

                val filterResults = FilterResults()
                filterResults.values = filteredList
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredHairStyleList = results?.values as List<HairStyle>
                notifyDataSetChanged()
            }
        }
    }
    class HairStyleViewHolder(private val binding: ItemListHairstyleBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(hairStyle: HairStyle) {
            binding.HairStyleImage.setImageResource(hairStyle.picture)
            binding.HairStyleName.text = hairStyle.name
            binding.descTextView.text = hairStyle.description
        }
    }

}