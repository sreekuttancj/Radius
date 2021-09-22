package com.radius.radius.ui

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.radius.data.viewmodel.FacilityViewModel
import com.radius.domain.util.NetworkConnectionError
import com.radius.domain.util.NetworkResponseError
import com.radius.radius.R
import com.radius.radius.databinding.FragmentFacilityBinding
import com.radius.radius.ui.adapter.FacilityAdapter
import com.radius.radius.util.ItemSpaceDividerItemDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FacilityFragment : Fragment() {
    private val facilityViewModel by viewModels<FacilityViewModel>()
    private lateinit var binding: FragmentFacilityBinding
    private lateinit var facilityAdapter: FacilityAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        facilityAdapter = FacilityAdapter()
        facilityViewModel.getFacilityInfo()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentFacilityBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObserver()
        initAdapterObserver()

        with(binding.rvFacilities){
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            addItemDecoration(ItemSpaceDividerItemDecoration(spacing = resources.getDimensionPixelOffset(R.dimen.full_margin)))
            itemAnimator = null
            adapter = facilityAdapter
        }
    }

    private fun initObserver(){
        facilityViewModel.facilityLiveData.observe(viewLifecycleOwner,{
            it?.let {
                Log.i("frag_facility","Data: $it")
                facilityAdapter.submitList(it.facilitiesList)
            }
        })

        facilityViewModel.invalidCombinationLiveData.observe(viewLifecycleOwner, {
            it?.let {
                Toast.makeText(requireContext(), getString(R.string.not_available), Toast.LENGTH_SHORT).show()
            }
        })

        facilityViewModel.showProgress.observe(viewLifecycleOwner,{
            it?.let {
                binding.flProgress.flProgress.isVisible = it.inProgress
            }
        })

        facilityViewModel.showError.observe(viewLifecycleOwner,{
            it?.let {
                when (it) {
                    is NetworkConnectionError -> {
                       Toast.makeText(requireContext(), getString(R.string.network_error), Toast.LENGTH_SHORT).show()
                    }
                    is NetworkResponseError -> {
                        Toast.makeText(requireContext(), getString(R.string.server_error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        })

    }

    private fun initAdapterObserver (){
        facilityAdapter.onClickOptionLiveData.observe(viewLifecycleOwner, {
            it?.let {
                facilityViewModel.updateUserSelectedOption(it)
            }
        })
    }
}