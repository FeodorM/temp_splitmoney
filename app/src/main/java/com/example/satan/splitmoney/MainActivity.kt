package com.example.satan.splitmoney

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.util.Log
import android.view.View
import android.view.Menu
import android.view.MenuItem
import android.widget.*
import java.util.*

class MainActivity : AppCompatActivity() {

    companion object {
        val PERSON_LIST_KEY = "com.example.satan.MainActivity.PERSON_LIST"
    }
    private val TAG = "Main Activity"

    private lateinit var personList: ArrayList<ListItem>
    private lateinit var arrayAdapter: ArrayAdapter<ListItem>
    private lateinit var editName: EditText
    private lateinit var editAmount: EditText

    private var itemId: Int? = null

    private val testData = listOf(
            ListItem("person 1", 123), ListItem("person 2", 3242), ListItem("person 3", 123),
            ListItem("person 4", 324), ListItem("person 5", 9767), ListItem("person 6", 322),
            ListItem("person 7", 123), ListItem("person 8", 3242), ListItem("person 9", 123),
            ListItem("person 10", 32), ListItem("person 11", 976), ListItem("person 12", 42)
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // get list of persons from bundle (restore state)
        if (savedInstanceState == null || !savedInstanceState.containsKey(PERSON_LIST_KEY)) {
            personList = ArrayList<ListItem>(testData)
        } else {
            personList = savedInstanceState.getParcelableArrayList(PERSON_LIST_KEY)
        }

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)


        // Some other things
        editName = findViewById(R.id.editName) as EditText
        editAmount = findViewById(R.id.editAmount) as EditText

        // List
        arrayAdapter = ArrayAdapter<ListItem>(this, android.R.layout.simple_list_item_1, personList)
        val listView = findViewById(R.id.listView) as ListView
        listView.adapter = arrayAdapter
        listView.setOnItemClickListener { adapterView: AdapterView<*>?, view: View?, position: Int, id: Long ->
            itemId = position
            editName.setText(personList[position].name)
            editAmount.setText(personList[position].amount.toString())
        }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        outState?.putParcelableArrayList(PERSON_LIST_KEY, personList)
        super.onSaveInstanceState(outState)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        return when(item.itemId) {
            R.id.action_settings -> true
            R.id.action_clear_list -> {
                personList.clear()
                arrayAdapter.notifyDataSetChanged()
                true
            }
            R.id.action_process_data -> {
                processData()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    fun showMessage(message: String) {
        Toast.makeText(applicationContext, message, Toast.LENGTH_SHORT).show()
    }

    fun _processData(): Array<String> {
        val wholeSum = personList.sumBy { it.amount }
        val sumPerPerson = wholeSum / personList.size
        val result = personList.map {
            val part = it.amount
            val amount: Any
            val sign: String
            if (sumPerPerson > part) {
                amount = sumPerPerson - part
                sign = " -> "
            } else if (sumPerPerson < part) {
                amount = part - sumPerPerson
                sign = " <- "
            } else {
                amount = ""
                sign = "owes nothing"
            }
            "${it.name}\t$sign\t$amount"
        }.toTypedArray()
        return result
    }

    fun processData() {
        if (personList.isEmpty()) {
            showMessage("Not Enough Data")
            return
        }
        val intent: Intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(PERSON_LIST_KEY, _processData())
        startActivity(intent)
    }

    fun onClickProcessData(view: View) {
        processData()
    }

    fun addPerson(view: View) {
        val person = editName.text.toString()
        val amount: Int? = try {
            editAmount.text.toString().toInt()
        } catch(e: NumberFormatException) {
            null
        }
        if (person != "" && amount != null) {
            val id = itemId
            if (id == null) {
                personList.add(0, ListItem(person, amount))
            } else {
                personList[id] = ListItem(person, amount)
                itemId = null
            }
            arrayAdapter.notifyDataSetChanged()
            editName.setText("Mr. Robot")
            editAmount.setText("")
        } else {
            showMessage("Wrong Data")
        }
    }
}


