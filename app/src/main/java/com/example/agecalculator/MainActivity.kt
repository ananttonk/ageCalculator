package com.example.agecalculator

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.annotation.RequiresApi
import com.example.agecalculator.databinding.ActivityMainBinding
import java.util.*
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        val current = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
        val formatted = current.format(formatter)
        binding.currentDate.text = formatted.toString()
        binding.dateButton.setOnClickListener { view ->
            datePicker(view)
        }
        binding.backButton.setOnClickListener {
            finish()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun datePicker(view: View) {
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH)
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val dpd = DatePickerDialog(
            this,
            DatePickerDialog.OnDateSetListener { _, selectedYear, selectedMonth, selectedDayOfMonth ->
                val selectedDate = "$selectedDayOfMonth/${selectedMonth + 1}/${selectedYear}"
                binding.selectedDate1.text = (selectedDate)
                val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.ENGLISH)
                val theDate = sdf.parse(selectedDate)
                val selectedDateToMonth = theDate!!.time / (60000)
                val currentDate = sdf.parse(sdf.format(System.currentTimeMillis()))
                val currentDateToMinute = currentDate!!.time / (60000)
                val differenceInMinutes = currentDateToMinute - selectedDateToMonth
                binding.totalMin.text = (differenceInMinutes.toString())

                val dateInSecond = differenceInMinutes * 60
                binding.totalSecond.text = dateInSecond.toString()

                val dateInYear = differenceInMinutes / (60 * 24 * 365)
                if (dateInYear.toInt() == 1 || dateInYear.toInt() == 0) {
                    binding.tvYear.text = "Year"
                    binding.tvTotalYear.text = "Age in Year"
                } else {
                    binding.tvYear.text = "Years"
                    binding.tvTotalYear.text = "Age in Years"
                }
                binding.totalYear.text = dateInYear.toString()
                binding.year.text = (dateInYear.toString())

                val dateInMonth = differenceInMinutes / (60 * 24 * 30)
                val months = dateInMonth - (dateInYear * 12)
                if (dateInMonth.toInt() == 1 || dateInMonth.toInt() == 0 || months.toInt() == 0 || months.toInt() == 1) {
                    binding.tvMonth.text = "Month"
                    binding.tvTotalMonth.text = "Age in Month"
                } else {
                    binding.tvMonth.text = "Months"
                    binding.tvTotalMonth.text = "Age in Months"
                }
                binding.month.text = months.toString()
                binding.totalMonths.text = dateInMonth.toString()

                val dateInWeek = differenceInMinutes / (60 * 24 * 7)
                if (dateInWeek.toInt() == 1 || dateInWeek.toInt() == 0) {
                    binding.tvTotalWeek.text = "Age in Week"
                } else {
                    binding.tvTotalWeek.text = "Age in Weeks"
                }
                binding.totalWeeks.text = dateInWeek.toString()

                val dateInDay = differenceInMinutes / (60 * 24)
                if (dateInDay.toInt() == 1 || dateInDay.toInt() == 0) {
                    binding.tvTotalDay.text = "Age in Day"
                } else {
                    binding.tvTotalDay.text = "Age in Days"
                }
                binding.totalDays.text = (dateInDay.toString())
                val dateInHour = differenceInMinutes / 60
                binding.selectedDateInHour.text = (dateInHour.toString())


            }, year, month, day
        )
        dpd.datePicker.maxDate = Date().time - 86400000
        dpd.show()
    }
}



