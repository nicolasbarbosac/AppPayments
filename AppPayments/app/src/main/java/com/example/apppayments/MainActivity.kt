package com.example.apppayments


import android.os.Bundle
import android.view.View
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import android.content.Intent
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import com.example.apppayments.Helpers.DataGlobal
import com.example.apppayments.HttpClient.RestApiService
import com.example.apppayments.database.DatabaseHandler
import com.example.apppayments.model.AuthorizationRequest
import com.example.apppayments.model.AuthorizationResponse
import java.text.NumberFormat
import java.util.*


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        var global = DataGlobal()
        var Id= UUID.randomUUID()
        u_id.setText(Id.toString())
       // u_id.isEnabled=false
        u_commerceCode.setText(global.commerceCode)
     //   u_commerceCode.isEnabled=false
        u_terminalCode.setText(global.terminalCode)
     //   u_terminalCode.isEnabled=false
        u_card.setText(global.card)

        u_amount.addTextChangedListener(textWatcher)

        var st=global.keyApi
    }

    //method for saving records in database
    fun saveRecord(view: View){


            val apiService = RestApiService()
            val authReq = AuthorizationRequest(
                u_id.text.toString(),
                u_commerceCode.text.toString(),
                u_terminalCode.text.toString(),
                u_amount.text.replace("""[$,.]""".toRegex(), "").toInt().toString(),
                u_card.text.toString())
        if(authReq.id.trim()!="" && authReq.commerceCode.trim()!="" && authReq.terminalCode.trim()!="" && authReq.amount.trim()!="" && authReq.card.trim()!=""  ){
            apiService.authorization(authReq) {
                if (it != null) {

                    val receiptId =it.receiptId
                    val rrn = it.rrn
                    val statusCode = it.statusCode
                    val statusDescription =it.statusDescription
                    val databaseHandler: DatabaseHandler = DatabaseHandler(this)
                    if(receiptId!=null&& rrn!=null&&  receiptId?.trim()!="" && rrn?.trim()!="" && statusCode.trim()!=""&& statusDescription.trim()!=""){
                        val status = databaseHandler.addEmployee(AuthorizationResponse(receiptId,rrn, statusCode,statusDescription))
                        if(status > -1){
                            Toast.makeText(applicationContext,"transaccion alamacenada",Toast.LENGTH_LONG).show()
                            u_amount.removeTextChangedListener(textWatcher)
                            u_amount.text.clear()
                            u_amount.addTextChangedListener(textWatcher)
                        }
                    }else{
                        Toast.makeText(applicationContext,statusDescription,Toast.LENGTH_LONG).show()
                    }
                } else {
                    Toast.makeText(applicationContext,"error en api",Toast.LENGTH_LONG).show()
                }
            }
        }
        else{
            Toast.makeText(applicationContext,"diligencia todos los campos",Toast.LENGTH_LONG).show()
        }
    }
    fun viewTransactions(view: View) {
        val intent = Intent(this@MainActivity, ListTransactionsActivity::class.java).apply {        }
        startActivity(intent)
    }


    private var current: String = ""


    private val textWatcher = object : TextWatcher {
        override fun afterTextChanged(s: Editable?) {
        }
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }
        override  fun onTextChanged(
            s: CharSequence,
            start: Int,
            before: Int,
            count: Int
        ) {
            if (s.toString() != current) {
                u_amount.removeTextChangedListener(this)

                val cleanString: String = s.replace("""[$,.]""".toRegex(), "")

                val parsed = cleanString.toDouble()
                val formatted = NumberFormat.getCurrencyInstance().format((parsed / 100))

                current = formatted
                u_amount.setText(formatted)
                u_amount.setSelection(formatted.length)

                u_amount.addTextChangedListener(this)
            }
        }
    }
//
//    fun updateRecord(view: View){
//        val dialogBuilder = AlertDialog.Builder(this)
//        val inflater = this.layoutInflater
//        val dialogView = inflater.inflate(R.layout.update_dialog, null)
//        dialogBuilder.setView(dialogView)
//
//        val edtreceiptId = dialogView.findViewById(R.id.updateReceiptId) as EditText
//        val edtrrn = dialogView.findViewById(R.id.updaterrn) as EditText
//        val edtstatusCode = dialogView.findViewById(R.id.updatestatusCode) as EditText
//        val edtstatusDescription = dialogView.findViewById(R.id.updatestatusDescription) as EditText
//
//        dialogBuilder.setTitle("Update Record")
//        dialogBuilder.setMessage("Enter data below")
//        dialogBuilder.setPositiveButton("Update", DialogInterface.OnClickListener { _, _ ->
//
//            val updateReceiptId = edtreceiptId.text.toString()
//            val updaterrn = edtrrn.text.toString()
//            val updatestatusCode = edtstatusCode.text.toString()
//            val updatestatusDescription = edtstatusDescription.text.toString()
//
//            //creating the instance of DatabaseHandler class
//            val databaseHandler: DatabaseHandler= DatabaseHandler(this)
//            if(updateReceiptId.trim()!="" && updaterrn.trim()!="" && updatestatusCode.trim()!="" && updatestatusDescription.trim()!=""){
//                //calling the updateEmployee method of DatabaseHandler class to update record
//                val status = databaseHandler.updateEmployee(AuthorizationResponse(updateReceiptId,updaterrn, updatestatusCode,updatestatusDescription))
//                if(status > -1){
//                    Toast.makeText(applicationContext,"record update",Toast.LENGTH_LONG).show()
//                }
//            }else{
//                Toast.makeText(applicationContext,"id or name or email cannot be blank",Toast.LENGTH_LONG).show()
//            }
//
//        })
//        dialogBuilder.setNegativeButton("Cancel", DialogInterface.OnClickListener { dialog, which ->
//            //pass
//        })
//        val b = dialogBuilder.create()
//        b.show()
//    }

}