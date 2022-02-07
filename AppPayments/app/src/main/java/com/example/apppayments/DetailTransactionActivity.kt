package com.example.apppayments

import CustomAdapter
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.apppayments.HttpClient.RestApiService
import com.example.apppayments.R
import com.example.apppayments.database.DatabaseHandler
import com.example.apppayments.model.AnnulmentRequest
import com.example.apppayments.model.AuthorizationRequest
import com.example.apppayments.model.AuthorizationResponse
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_detail_transaction.*

class DetailTransactionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_transaction)


        val actionbar = supportActionBar
        actionbar!!.title = "Detalle transaccion"
        actionbar.setDisplayHomeAsUpEnabled(true)
        actionbar.setDisplayHomeAsUpEnabled(true)

        var authJson:String= intent.getStringExtra("Auth").toString()
        val gson = Gson()
        val type = object : TypeToken<AuthorizationResponse>() {}.type
        auth=gson.fromJson(authJson,type)

        textViewReceiptId.setText(auth.receiptId)
        textViewRRN.setText(auth.rrn)
        textViewStatusCode.setText(auth.statusCode)
        textViewStatusDescription.setText(auth.statusDescription)
    }


    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    lateinit var auth: AuthorizationResponse  //AnnulmentRequest

    fun annulmentTransaction(view:View) {

        val apiService = RestApiService()
      var annul:  AnnulmentRequest=AnnulmentRequest(auth.receiptId.toString(),auth.rrn.toString())
       apiService.annulment(annul) {
           if (it != null) {

               Toast.makeText(applicationContext,"anulacion exitosa estado ${it.statusCode} Descripción: ${it.statusDescription}", Toast.LENGTH_LONG).show()
           }else {
               Toast.makeText(applicationContext,"Anulacion fallida: ${it?.statusCode}  ${it?.statusDescription}", Toast.LENGTH_LONG).show()
           }

      }
    }
}