package com.danielvilha.tds

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.danielvilha.tds.network.Builder
import com.danielvilha.tds.data.Items
import com.danielvilha.tds.data.Emitted
import com.danielvilha.tds.util.biggerNumber
import com.danielvilha.tds.util.middleNumber
import com.danielvilha.tds.util.smallerNumber
import com.google.android.material.snackbar.Snackbar
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var timer: Timer
    private val noDelay = 0L
    private var emitted = Emitted(false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        timer = Timer()
        emergencies()
    }

    // This function check if it is emitted
    private fun showEmergency(isEmitted: Boolean) {
        if (isEmitted) {
            /* Querying this url every 5 seconds to check how many employees are currently in the building . */
            repeat(5) {
                val compositeDisposable = CompositeDisposable()
                compositeDisposable.add(
                    Builder.buildService().getEmployees()
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(
                            { response -> onResponse(response) },
                            { t -> onFailure(t) })
                )
            }
        } else {
            onResponse()
        }
    }

    private fun emergencies() {
        var num = Random.nextLong(30000L, 90000L)
        val timerTask = object : TimerTask() {
            override fun run() {
                num = Random.nextLong(30000L, 90000L)
                emitted.value = num.toInt() % 2 == 0
                Log.v(MainActivity::class.java.name, "Number $num emitted: ${emitted.value}")
                showEmergency(emitted.value)
            }
        }

        timer.schedule(timerTask, noDelay, num)
    }

    private fun onFailure(t: Throwable) {
        Log.v(MainActivity::class.java.name, t.localizedMessage ?: t.message.toString())
        Snackbar.make(txv_emergency, t.message.toString(), Snackbar.LENGTH_SHORT).show()
    }

    private fun onResponse() {
        runOnUiThread {
            Log.v(MainActivity::class.java.name, "NO EMERGENCY")
            /* Changing the Constraint view to GONE */
            constraint.visibility = View.GONE

            /* Changing the text for No Emergency */
            txv_emergency.text = getString(R.string.no_emergency)
            txv_emergency.setTextColor(getColor(R.color.green))
        }
    }

    private fun onResponse(response: Items) {
        runOnUiThread {
            Log.v(MainActivity::class.java.name, "EMERGENCY")
            /* Changing the Constraint view to VISIBLE */
            constraint.visibility = View.VISIBLE

            /* Changing the text for Emergency */
            txv_emergency.text = getString(R.string.emergency)
            txv_emergency.setTextColor(getColor(R.color.red))

            /* Total number of employees */
            txv_total_number.text = response.data.size.toString()
            txv_total.text = getString(R.string.total)

            /* Total smaller number of employers */
            txv_smaller.text = smallerNumber(response.data).toString()
            txv_smaller_text.text = String.format(
                "%s<%s",
                getString(R.string.employer),
                getString(R.string.eighteen)
            )

            /* Total middle number of employers */
            txv_middle.text = middleNumber(response.data).toString()
            txv_middle_text.text = String.format(
                "%s<%s<%s",
                getString(R.string.eighteen),
                getString(R.string.employer),
                getString(R.string.sixty)
            )

            /* Total bigger number of employers */
            txv_bigger.text = biggerNumber(response.data).toString()
            txv_bigger_text.text = String.format(
                "%s<%s",
                getString(R.string.sixty),
                getString(R.string.employer)
            )
        }
    }
}