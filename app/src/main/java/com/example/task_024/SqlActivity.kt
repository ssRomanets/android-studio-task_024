package com.example.task_024

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.EditText
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import kotlin.system.exitProcess

class SqlActivity : AppCompatActivity() {

    private val db = DBHelper(this, null)

    private lateinit var toolbarMain: Toolbar
    private lateinit var enterNameET: EditText
    private lateinit var enterPhoneET: EditText
    private lateinit var spinner: Spinner
    private lateinit var addWorkerBTN: Button
    private lateinit var printWorkerBTN: Button
    private lateinit var clearTableBTN: Button
    private lateinit var nameTV: TextView
    private lateinit var phoneTV: TextView
    private lateinit var appointmentTV: TextView

    private var workerAppointment = ""

    private val role = mutableListOf(
        "Выберите должность",
        "Engineer",
        "Designer",
        "Contractor",
        "Master",
        "PowerEngineer",
        "Mechanic"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sql)

        init()

        setSupportActionBar(toolbarMain)
        title = "База данных."

        val adapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            role
        )
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner.adapter = adapter

        val itemSelectedListener: AdapterView.OnItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    val item = parent?.getItemAtPosition(position) as String
                    workerAppointment = item.toString()
                }
                override  fun onNothingSelected(parent: AdapterView<*>?) {}
            }
        spinner.onItemSelectedListener = itemSelectedListener

        addWorkerBTN.setOnClickListener{
            val workerName = enterNameET.text.toString()
            val workerPhone = enterPhoneET.text.toString()

            db.addWorker(workerName, workerPhone, workerAppointment)

            Toast.makeText(this, "$workerName, $workerPhone и  $workerAppointment добавлены в базу данных",
                Toast.LENGTH_LONG).show()

            enterNameET.text.clear()
            enterPhoneET.text.clear()
        }

        printWorkerBTN.setOnClickListener{
            val cursor = db.getInfo()
            if (cursor != null && cursor.moveToFirst()) {
                cursor.moveToFirst()
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))+"\n")
                phoneTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))+"\n")
                appointmentTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_APPOINTMENT))+"\n")
            }
            while (cursor!!.moveToNext()) {
                nameTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_NAME))+"\n")
                phoneTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_PHONE))+"\n")
                appointmentTV.append(cursor.getString(cursor.getColumnIndex(DBHelper.KEY_APPOINTMENT))+"\n")
            }
            cursor?.close()
        }

        clearTableBTN.setOnClickListener{
            db.removeAll()
            nameTV.text = ""
            phoneTV.text = ""
        }
    }

    private fun init() {
        toolbarMain = findViewById(R.id.toolbarMain)
        enterNameET  = findViewById(R.id.enterNameET)
        enterPhoneET = findViewById(R.id.enterPhoneET)
        spinner = findViewById(R.id.spinner)
        addWorkerBTN = findViewById(R.id.addWorkerBTN)
        printWorkerBTN = findViewById(R.id.printWorkerBTN)
        clearTableBTN = findViewById(R.id.clearTableBTN)
        nameTV = findViewById(R.id.nameTV)
        phoneTV = findViewById(R.id.phoneTV)
        appointmentTV = findViewById(R.id.appointmentTV)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sql_menu, menu)
        return  true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.exitMenuMain->{
                moveTaskToBack(true);
                exitProcess(-1)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}