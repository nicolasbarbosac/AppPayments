package com.example.apppayments.database
import android.annotation.SuppressLint
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.content.ContentValues
import android.database.Cursor
import android.database.sqlite.SQLiteException
import com.example.apppayments.model.AuthorizationResponse

class DatabaseHandler (context: Context): SQLiteOpenHelper(context,DATABASE_NAME,null,DATABASE_VERSION) {
    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "AppPayments"
        private val TABLE_AUTORIZATION = "Autorization"
        //
        private val KEY_ID = "id"
        private val KEY_RRN = "rnn"
        private val KEY_STATUSCODE = "statuscode"
        private val KEY_STATUSDESCRIPTION = "statusdescription"

    }
    override fun onCreate(db: SQLiteDatabase?) {
        //creating table with fields
        val CREATE_CONTACTS_TABLE = ("CREATE TABLE " + TABLE_AUTORIZATION + "("
                + KEY_ID + " TEXT PRIMARY KEY," + KEY_RRN + " TEXT," + KEY_STATUSCODE + " TEXT,"
                + KEY_STATUSDESCRIPTION + " TEXT" + ")")
        db?.execSQL(CREATE_CONTACTS_TABLE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS " + TABLE_AUTORIZATION)
        onCreate(db)
    }


    //method to insert data
    fun addEmployee(emp: AuthorizationResponse):Long{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(KEY_ID, emp.receiptId)
        contentValues.put(KEY_RRN, emp.rrn)
        contentValues.put(KEY_STATUSCODE,emp.statusCode )
        contentValues.put(KEY_STATUSDESCRIPTION,emp.statusDescription )
        val success = db.insert(TABLE_AUTORIZATION, null, contentValues)
        db.close()
        return success
    }

    @SuppressLint("Range")
    fun viewEmployee(Filter:String):ArrayList<AuthorizationResponse>{
        val empList:ArrayList<AuthorizationResponse> = ArrayList<AuthorizationResponse>()
        val selectQuery = "SELECT  * FROM $TABLE_AUTORIZATION WHERE $KEY_ID LIKE '%$Filter%' or  $KEY_STATUSDESCRIPTION  LIKE '%$Filter%'"
        val db = this.readableDatabase
        var cursor: Cursor? = null
        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: SQLiteException) {
            db.execSQL(selectQuery)
            return ArrayList()
        }
        var receiptId: String?
        var rrn: String?
        var statusCode: String
        var statusDescription: String



        if (cursor.moveToFirst()) {
            do {
                receiptId = cursor.getString(cursor.getColumnIndex("id"))
                rrn = cursor.getString(cursor.getColumnIndex("rnn"))
                statusCode = cursor.getString(cursor.getColumnIndex("statuscode"))
                statusDescription = cursor.getString(cursor.getColumnIndex("statusdescription"))
                val emp= AuthorizationResponse( receiptId = receiptId, rrn = rrn, statusCode = statusCode,statusDescription=statusDescription)
                empList.add(emp)
            } while (cursor.moveToNext())
        }
        return empList
    }

}