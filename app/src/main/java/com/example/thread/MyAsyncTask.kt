package com.example.thread

import android.app.ProgressDialog
import android.app.SearchManager
import android.content.Context
import android.content.DialogInterface
import android.os.AsyncTask
import android.util.Log
import java.lang.reflect.Constructor

class MyAsyncTask : AsyncTask<String,Int,Long>,SearchManager.OnCancelListener,DialogInterface.OnCancelListener{
    val TAG:String = "MyAsyncTask"
    lateinit var dialog:ProgressDialog
    lateinit var context:Context

    constructor(context: Context) {
        this.context = context
    }

    override protected fun onPreExecute() {
        super.onPreExecute()
        Log.d(TAG,"onPreExecute")
            dialog = ProgressDialog(context)
            dialog.setTitle("Please wait")
            dialog.setMessage("Loading data...")
            dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
            dialog.setCancelable(true)
            dialog.setOnCancelListener(this)
            dialog.setMax(100)
            dialog.setProgress(0)
            dialog.show()
    }

    override fun doInBackground(vararg params: String?): Long {
        Log.d(TAG,"doInBackground - " + params[0])

        try{
            for (i in 0..9){
                if(isCancelled()){
                    Log.d(TAG,"CancelIntegerled!")
                    break
                }
                Thread.sleep(1000)
                publishProgress((i+1)*10)
            }
        }catch (e:InterruptedException){
            Log.d(TAG,"InterruptedException in doInBackground")
        }

        return 123L
    }

    override fun onProgressUpdate(vararg values: Int?) {
        Log.d(TAG,"onProgressUpdate - " + values[0])
            dialog.setProgress(values[0]!!.toInt())
    }
    override fun onCancelled() {
        super.onCancelled()
        Log.d(TAG,"onCancelled")
            dialog.dismiss()
    }

    override fun onPostExecute(result: Long?) {
        super.onPostExecute(result)
        Log.d(TAG,"onPostExecute - " + result)
            dialog.dismiss()
    }

    override fun onCancel() {

    }

    override fun onCancel(dialog: DialogInterface?) {
        Log.d(TAG,"Dialog onCancell...calling cancel(true)")
        this.cancel(true)
    }

}