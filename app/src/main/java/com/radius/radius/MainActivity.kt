package com.radius.radius

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.radius.data.viewmodel.FacilityViewModel
import com.radius.radius.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private val facilityViewModel by viewModels<FacilityViewModel>()
    private lateinit var activityMainBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setSupportActionBar(activityMainBinding.toolbar.toolbarMain)
        setContentView(activityMainBinding.root)

        facilityViewModel.getFacilityInfo()
    }
}