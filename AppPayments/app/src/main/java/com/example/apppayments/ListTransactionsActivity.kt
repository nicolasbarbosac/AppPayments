package com.example.apppayments

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppayments.database.DatabaseHandler
import com.example.apppayments.model.AuthorizationResponse
import com.google.gson.Gson
import kotlinx.android.synthetic.main.activity_list_transactions.*
import kotlinx.android.synthetic.main.activity_main.*

class ListTransactionsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_transactions)


        val databaseHandler: DatabaseHandler = DatabaseHandler(this)
        var auth: ArrayList<AuthorizationResponse> = databaseHandler.viewEmployee("")
        recyclerview.layoutManager = LinearLayoutManager(this)
        val myListAdapter =CustomAdapter(auth,{ partItem : AuthorizationResponse -> partItemClicked(partItem) })

        recyclerview.adapter = myListAdapter

        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Transacciones"
        //set back button
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        FilterTransactions.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {
            }
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                auth.clear()
                auth.addAll( databaseHandler.viewEmployee(s.toString()))
                myListAdapter!!.notifyDataSetChanged()
if (auth.size==0){

    Toast.makeText(applicationContext,"Se consultarán las transacciones autorizadas disponibles en la base de datos interna del dispositivo.", Toast.LENGTH_SHORT).show()
}
            }
        })
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun partItemClicked(partItem : AuthorizationResponse) {

        val gson = Gson()
       val jsonString = gson.toJson(partItem)
        val intent = Intent(this, DetailTransactionActivity::class.java).apply {      putExtra("Auth",jsonString)  }
        startActivity(intent)
    }


}