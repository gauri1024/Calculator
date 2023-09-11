package com.example.myapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log.e
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import com.example.myapplication.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    var lastNumeric : Boolean = false
    var lastDot : Boolean = false
    lateinit var binding: ActivityMainBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_main)

        var tvInput : TextView = findViewById(R.id.tvInput)

    }

    fun onDigit(view: View) {
        e("text", binding.tvInput.text.toString())
        var exp = binding.tvInput.text.toString()
        exp += (view as Button).text
        binding.tvInput.text = exp
        e("text", binding.tvInput.text.toString())
        lastNumeric = true
        lastDot = false

        var sentence = ""
    }

    fun onClear(view : View) {
        binding.tvInput.text = ""
    }

    fun onDecimalPoint(view : View) {
        if(lastNumeric && !lastDot) {
            binding.tvInput.append(".")
            lastNumeric = false
            lastDot = true
        }
    }

    fun onOperator(view : View) {
        binding.tvInput.text?.let{
            if(lastNumeric && !isOperatorAdded(it.toString())){
                binding.tvInput.append((view as Button).text)
                lastNumeric  = false
                lastDot = false
            }
        }
    }

    fun onEqual(view : View) {
        if(lastNumeric) {
            var tvValue = binding.tvInput.text.toString()
            var prefix = ""

            try{
                if(tvValue.startsWith("-")) {
                    prefix = "-"
                    tvValue = tvValue.substring(1)
                }
                if (tvValue.contains("-")){
                    val splitValue = tvValue.split("-")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput?.text = removeZeroAfterDot((one.toDouble() - two.toDouble()).toString())
                }
                else if (tvValue.contains("+")){
                    val splitValue = tvValue.split("+")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput?.text = removeZeroAfterDot((one.toDouble() + two.toDouble()).toString())
                }
                else if (tvValue.contains("/")){
                    val splitValue = tvValue.split("/")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput?.text = removeZeroAfterDot((one.toDouble() / two.toDouble()).toString())
                }
                else if (tvValue.contains("*")){
                    val splitValue = tvValue.split("*")
                    var one = splitValue[0]
                    var two = splitValue[1]

                    if (prefix.isNotEmpty()) {
                        one = prefix + one
                    }

                    binding.tvInput?.text = removeZeroAfterDot((one.toDouble() * two.toDouble()).toString())
                }

            }catch(e: ArithmeticException) {
                e.fillInStackTrace()
            }
        }
    }

    private fun removeZeroAfterDot(result : String) : String{
        var value = result
        if (result.contains(".0")) value = result.substring(0, result.length - 2)
        return value
    }

    private fun isOperatorAdded(value: String) : Boolean{
        return if (value.startsWith("-")){
            false
        } else {
            value.contains("/")
                    || value.contains("+")
                    || value.contains("*")
                    || value.contains("-")
        }
    }


}


