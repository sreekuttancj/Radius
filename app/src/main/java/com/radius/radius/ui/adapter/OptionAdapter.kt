package com.radius.radius.ui.adapter

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.radius.domain.model.business.FacilityOption
import com.radius.radius.R
import com.radius.radius.databinding.LayoutOptionsBinding

class OptionAdapter: ListAdapter<FacilityOption, OptionAdapter.OptionViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<FacilityOption>(){
            override fun areItemsTheSame(
                oldItem: FacilityOption,
                newItem: FacilityOption
            ): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(
                oldItem: FacilityOption,
                newItem: FacilityOption
            ): Boolean {
              return oldItem == newItem
            }
        }
    }

    private val onClickOptionMutableLiveData = MutableLiveData<FacilityOption>()
    val onClickOptionLiveData: LiveData<FacilityOption> = onClickOptionMutableLiveData

    inner class OptionViewHolder (private val binding: LayoutOptionsBinding): RecyclerView.ViewHolder(binding.root), ViewHolderBinder<FacilityOption> {
        override fun onBind(data: FacilityOption) {
            binding.tvName.text = data.name

            val imageOverlayForeground =  when(data.isSelected){
                true -> binding.root.resources.getDrawable(R.drawable.option_overlay, null)
                else -> null
            }
            binding.ivIcon.foreground = imageOverlayForeground

            //todo add error image or default image
            Glide.with(binding.root.context)
                .load(getImage(binding.root.context, data.icon))
                .into(binding.ivIcon)

            binding.root.setOnClickListener {
                onClickOptionMutableLiveData.value = data
                Log.i("click_event", "item: ${data.name} isSelected: ${data.isSelected} isExcluded: ${data.isExcluded}")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OptionViewHolder {
        val binding = LayoutOptionsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OptionViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OptionViewHolder, position: Int) {
        val viewHolder = holder as? ViewHolderBinder<in FacilityOption>
        viewHolder?.onBind(currentList[position])
    }

    interface ViewHolderBinder <DATA: FacilityOption> {
        fun onBind(data: DATA)
    }

    fun getImage(context: Context, iconName: String): Int {
        return context.resources.getIdentifier(iconName, "drawable", context.packageName)
    }
}