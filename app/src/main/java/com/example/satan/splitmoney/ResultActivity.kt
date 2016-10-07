package com.example.satan.splitmoney

import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView

//import com.example.satan.appicon.R;

class ResultActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_result)

        val toolbar = findViewById(R.id.toolbar) as Toolbar
        setSupportActionBar(toolbar)

        val listView = findViewById(R.id.result_list) as ListView
        listView.adapter = ArrayAdapter<String>(
                this,
                android.R.layout.simple_list_item_1,
                intent.getStringArrayExtra(MainActivity.PERSON_LIST_KEY)
        )
    }

    fun goBack(view: View) {
        finish()
    }
}
