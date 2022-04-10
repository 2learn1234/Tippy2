package com.example.tippy

import android.animation.ArgbEvaluator
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.SeekBar
import android.widget.TextView
import androidx.core.content.ContextCompat

private const val TAG = "MainActivity"
private const val INIT_TIP_PERCENT = "15%"
private const val INIT_PROGRESS = 15

class MainActivity : AppCompatActivity() {
    private lateinit var edBaseAmount: EditText
    private lateinit var tvTipPercentLabel: TextView
    private lateinit var seekbarTipPercent: SeekBar
    private lateinit var tvTip: TextView
    private lateinit var tvTotalPrice: TextView
    private lateinit var tvTipDescription: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        edBaseAmount = findViewById(R.id.edBaseAmount)
        seekbarTipPercent = findViewById(R.id.seekBarTipPercent)
        tvTipPercentLabel = findViewById(R.id.tvTipPercentLabel)
        tvTotalPrice = findViewById(R.id.tvTotal)
        tvTip = findViewById(R.id.tvTip)
        tvTipDescription = findViewById(R.id.tvTipDescription)
        tvTipPercentLabel.text = INIT_TIP_PERCENT
        seekbarTipPercent.progress = INIT_PROGRESS

        updateLanguage(INIT_PROGRESS)

        seekbarTipPercent.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                tvTipPercentLabel.text = "$p1%"
                updateTipAndTotal()
                updateLanguage(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }

        })

        edBaseAmount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                updateTipAndTotal()

            }

        })

    }

    private fun updateLanguage(p1: Int) {
        val tipDescription = when (p1) {
            in 0..9 -> "Poor"
            in 10..14 -> "Acceptable"
            in 15..20 -> "Great"
            else -> {
                "Amazing"
            }
        }

        val colorDescr = ArgbEvaluator().evaluate(
            p1.toFloat() / seekbarTipPercent.max,
            ContextCompat.getColor(this, R.color.color_Worst),
            ContextCompat.getColor(this, R.color.color_best)
        ) as Int

        tvTipDescription.text = tipDescription
        tvTipDescription.setTextColor(colorDescr)

    }

    private fun updateTipAndTotal() {
        if (edBaseAmount.text.isEmpty()) {
            tvTotalPrice.text = ""
            tvTip.text = ""
            return
        }
        val baseAmount = edBaseAmount.text.toString().toDouble()
        val tipPercent = seekbarTipPercent.progress
        val tipAmount = tipPercent * baseAmount / 100.00
        tvTip.text = "%.2f".format(tipAmount)
        val total = tipAmount + baseAmount
        tvTotalPrice.text = "%.2f".format(total)

    }


}