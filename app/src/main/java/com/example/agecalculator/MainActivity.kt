package com.example.agecalculator

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.agecalculator.databinding.ActivityMainBinding
import java.util.*
import java.text.SimpleDateFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        binding.dateButton.setOnClickListener { view ->
            date_picker(view) }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    fun date_picker(view: View) {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { view, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/${selectedYear}"
                binding.selectedDate.setText(selectedDate)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                val selectedDateToMonth = theDate!!.time / (60000)
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateToMinute = currentDate!!.time / (60000)
                val differenceInMinutes = currentDateToMinute - selectedDateToMonth
                binding.selectedDateInMinutes.setText(differenceInMinutes.toString())
                val dateInYear = differenceInMinutes / (60 * 24 * 365)
                binding.selectedDateInYear.setText(dateInYear.toString())
                val dateInDay=differenceInMinutes / (60 * 24)
                binding.selectedDateInDay.setText(dateInDay.toString())
                val dateInHour=differenceInMinutes / 60
                binding.selectedDateInHour.setText(dateInHour.toString())

            },
            year,
            month,
            day
        )
        dpd.datePicker.setMaxDate(Date().time - 86400000)
        dpd.show()
    }
}



