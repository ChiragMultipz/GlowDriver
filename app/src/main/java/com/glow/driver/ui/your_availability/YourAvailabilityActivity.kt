package com.glow.driver.ui.your_availability

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.glow.driver.R
import com.glow.driver.databinding.ActivityYourAvailabilityBinding
import com.glow.driver.network.MyApi
import com.glow.driver.network.Resource
import com.glow.driver.ui.select_radius.SelectRadiusActivity
import com.glow.driver.utils.Constants
import com.glow.driver.utils.Prefrences
import com.glow.driver.utils.Utils
import com.glow.driver.utils.Utils.snackbar
import com.glow.driver.utils.Utils.toast
import com.prolificinteractive.materialcalendarview.CalendarDay
import kotlinx.coroutines.launch
import kotlin.math.log

class YourAvailabilityActivity : AppCompatActivity(), View.OnClickListener {

    lateinit var binding: ActivityYourAvailabilityBinding
    private lateinit var viewModel: YourAvailabilityViewModel
    private lateinit var morningSlotAdapter: MorningSlotsAdapter
    private lateinit var afternoonSlotAdapter: MorningSlotsAdapter
    private lateinit var eveningSlotAdapter: MorningSlotsAdapter
    private lateinit var timeSlotList: List<ResultItem>
    private lateinit var morningTimeSlotList: List<ResultItem>
    private lateinit var afternoonTimeSlotList: List<ResultItem>
    private lateinit var eveningTimeSlotList: List<ResultItem>
    private lateinit var selectedTimeSlotList: ArrayList<String>
    private lateinit var barberAvailableDates: ArrayList<String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityYourAvailabilityBinding.inflate(layoutInflater)

        viewModel = ViewModelProvider(
            this,
            YourAvailabilityViewModelFactory(
                YourAvailabilityRepository(
                    MyApi.getInstanceToken(
                        Prefrences.getPreferences(
                            this,
                            Constants.API_TOKEN
                        )!!
                    )
                )
            )
        ).get(YourAvailabilityViewModel::class.java)

        setContentView(binding.root)
        setUpCalenderView()
        setUpSlotRecyclerView()
        setOnClickListener()
        getTimeSlots()

        viewModel.getTimeSlotsResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    if (it.value.result != null) {
                        timeSlotList = (it.value.result as List<ResultItem>?)!!
                        filterSlotsTimeWise(timeSlotList)
                    }
                }
                is Resource.Failure -> Utils.handleApiError(binding.root, it) {
                    getTimeSlots()
                }
            }
        }

        viewModel.addBarberTimeResponse.observe(this) {
            when (it) {
                is Resource.Success -> {
                    if (it.value.success!!) {
                        Prefrences.savePreferencesString(this, Constants.IS_AVAILABILITY_ADDED, "1")
                        toast(getString(R.string.availability_added_msg))
                        startActivity(Intent(this, SelectRadiusActivity::class.java))
                        finish()

                    } else {
                        binding.root.snackbar(it.value.message!!)
                    }
                }
                is Resource.Failure -> Utils.handleApiError(binding.root, it) {
                }
            }
        }
    }

    private fun filterSlotsTimeWise(timeSlotList: List<ResultItem>) {

        morningTimeSlotList = timeSlotList.filter {
            it.id in 13..25
        }
        morningSlotAdapter.updateList(morningTimeSlotList)

        afternoonTimeSlotList = timeSlotList.filter {
            it.id in 26..33
        }
        afternoonSlotAdapter.updateList(afternoonTimeSlotList)

        eveningTimeSlotList = timeSlotList.filter {
            it.id in 34..45
        }
        eveningSlotAdapter.updateList(eveningTimeSlotList)
    }

    private fun setUpCalenderView() {
        // Disable Previous Dates
        binding.calendarView.state().edit()
            .setMinimumDate(CalendarDay.from(CalendarDay.today().date))
            .commit()
    }

    private fun setUpSlotRecyclerView() {
        morningSlotAdapter = MorningSlotsAdapter(this)
        afternoonSlotAdapter = MorningSlotsAdapter(this)
        eveningSlotAdapter = MorningSlotsAdapter(this)

        binding.morningSlotRecyclerview.layoutManager = GridLayoutManager(this, 4)
        binding.morningSlotRecyclerview.adapter = morningSlotAdapter

        binding.eveningSlotRecyclerview.layoutManager = GridLayoutManager(this, 4)
        binding.eveningSlotRecyclerview.adapter = eveningSlotAdapter

        binding.afternoonSlotRecyclerview.layoutManager = GridLayoutManager(this, 4)
        binding.afternoonSlotRecyclerview.adapter = afternoonSlotAdapter
    }

    private fun getTimeSlots() {

        if (Utils.isConnected(this)){
            viewModel.viewModelScope.launch {
                viewModel.getTimesSlots()
            }
        } else{
            binding.root.snackbar(getString(R.string.no_internet_connection))
        }

    }

    private fun setOnClickListener() {
        binding.ivBackButton.setOnClickListener(this)
        binding.tvContinue.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.ivBackButton -> finish()
            R.id.tvContinue -> addBarberAvailability()
        }
    }

    private fun addBarberAvailability() {
        selectedTimeSlotList = ArrayList()
        barberAvailableDates = ArrayList()
        selectedTimeSlotList.addAll(morningSlotAdapter.getSelectedSlots())
        selectedTimeSlotList.addAll(afternoonSlotAdapter.getSelectedSlots())
        selectedTimeSlotList.addAll(eveningSlotAdapter.getSelectedSlots())

        val selectedDates = binding.calendarView.selectedDates
        selectedDates.forEach {
            barberAvailableDates.add(Utils.formatDate("yyyy-MM-dd", "dd-MM-yyyy", it.date.toString())!!)
        }

        when {
            selectedTimeSlotList.isEmpty() -> {
                binding.root.snackbar(getString(R.string.empty_time_slots))
            }
            barberAvailableDates.isEmpty() -> {
                binding.root.snackbar(getString(R.string.empty_barber_date))
            }
            else -> {
                viewModel.viewModelScope.launch {
                    viewModel.addBarberTime(barberAvailableDates,selectedTimeSlotList)
                }
            }
        }

    }
}