package com.radius.radius.ui.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.*
import com.radius.domain.model.business.Facility
import com.radius.domain.model.business.FacilityOption
import com.radius.radius.databinding.LayoutFacilityBinding

class FacilityAdapter: ListAdapter<Facility, FacilityAdapter.FacilityViewHolder>(diffUtil) {

    companion object {
        val diffUtil = object: DiffUtil.ItemCallback<Facility>(){
            override fun areItemsTheSame(oldItem: Facility, newItem: Facility): Boolean {
              return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Facility, newItem: Facility): Boolean {
                return oldItem == newItem
            }

        }
    }

    private val onClickOptionMediatorLiveData = MediatorLiveData<FacilityOption>()
    val onClickOptionLiveData: LiveData<FacilityOption> = onClickOptionMediatorLiveData

    inner class FacilityViewHolder (private val binding: LayoutFacilityBinding): RecyclerView.ViewHolder (binding.root), FacilityBinder<Facility> {
       private val optionAdapter = OptionAdapter()

        init {
            onClickOptionMediatorLiveData.addSource(optionAdapter.onClickOptionLiveData, Observer {
                onClickOptionMediatorLiveData.value = it
            })
        }
        override fun onBind(data: Facility) {
            binding.tvTitle.text = data.name

            with(binding.rvOptions){
                layoutManager = GridLayoutManager(context, 3, GridLayoutManager.VERTICAL, false)
                itemAnimator = null
                adapter = optionAdapter
            }

            optionAdapter.submitList(data.options)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FacilityViewHolder {
        val binding = LayoutFacilityBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return FacilityViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FacilityViewHolder, position: Int) {
        val viewHolder =  holder as? FacilityBinder <in Facility>
        viewHolder?.onBind(currentList[position])
    }

    interface FacilityBinder <DATA: Facility> {
        fun onBind(data: DATA)
    }
}