package com.example.ageinwhat

import android.app.DatePickerDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private var selectedDateTextview: TextView? = null
    private var ageInMinuteTextView: TextView? = null
    private var ageInSecondsTextView: TextView? = null
    private var ageInHourTextView: TextView? = null
    private var ageInMilisecondsTextView: TextView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val btnPickaDate : Button = findViewById(R.id.calculate_btn)
        selectedDateTextview = findViewById(R.id.selectedDate)
        ageInMilisecondsTextView = findViewById(R.id.ageInMiliSecondsTextView)
        ageInSecondsTextView = findViewById(R.id.ageInSecondsTextView)
        ageInMinuteTextView = findViewById(R.id.ageInMinutesTextView)
        ageInHourTextView = findViewById(R.id.ageInHourTextView)

        btnPickaDate.setOnClickListener{
            datePickerisClicked()
        }
    }

    private fun datePickerisClicked(){
        // get date, month and year from a calender
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH)
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialogHolder = DatePickerDialog(this, {view, selectedYear, selectedMonth, selectedDayOfMonth ->
            val userSelectedDate = "${selectedMonth + 1}/$selectedDayOfMonth/$selectedYear"
            selectedDateTextview?.text = userSelectedDate // updates selected date text view

            // give us a date format
            val simpleDateFormat = SimpleDateFormat("MM/dd/yyyy", Locale.ENGLISH)
            val formattedSelectedDate = simpleDateFormat.parse(userSelectedDate)

            // finding the selected date in different times. Ex hour, seconds etc
            val selectedDateInHour = formattedSelectedDate.time / 3600000
            val selectedDateInMinutes = formattedSelectedDate.time / 60000
            var selectedDateInSeconds = formattedSelectedDate.time / 1000
            var selectedDateInMiliseconds = formattedSelectedDate.time

            // now lets find the current time
            var currentDate = simpleDateFormat.parse(simpleDateFormat.format(System.currentTimeMillis()))
            currentDate?.let {
                // now we find the age of the user in minutes
                val ageInMinutes = (currentDate.time / 60000) - selectedDateInMinutes
                val ageInSeconds = (currentDate.time / 1000) - selectedDateInSeconds
                val ageInHour = (currentDate.time / 3600000) - selectedDateInHour
                val ageInMiliseconds = currentDate.time -selectedDateInMiliseconds

                //show result in text view
                ageInHourTextView?.text = ageInHour.toString()
                ageInMinuteTextView?.text = ageInMinutes.toString()
                ageInSecondsTextView?.text = ageInSeconds.toString()
                ageInMilisecondsTextView?.text = ageInMiliseconds.toString()

                Toast.makeText(this, "AIM $ageInMinutes AIS $ageInSeconds AIH $ageInHour " +
                        "AIMS $ageInMiliseconds", Toast.LENGTH_LONG).show()
            }
        },
            year, month, day
        )
        // preventing user from selecting a date that is in the future
        datePickerDialogHolder.datePicker.maxDate = System.currentTimeMillis() - 86400000
        datePickerDialogHolder.show()
    }
}