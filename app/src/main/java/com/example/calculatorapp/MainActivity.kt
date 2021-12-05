package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.calculatorapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var operator = ""
    private var leftOperand = ""
    private var rightOperand = ""
    private var expression = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // The calculator should be able to handle addition, subtraction, multiplication, and division of two numbers
        binding.btnAdd.setOnClickListener{ operatorPress("+") }
        binding.btnSub.setOnClickListener{ operatorPress ("-") }
        binding.btnMultiply.setOnClickListener{ operatorPress ("*") }
        binding.btnDivision.setOnClickListener{ operatorPress("/") }

        binding.btn0.setOnClickListener{ numPress(0) }
        binding.btn1.setOnClickListener{ numPress(1) }
        binding.btn2.setOnClickListener{ numPress(2) }
        binding.btn3.setOnClickListener{ numPress(3) }
        binding.btn4.setOnClickListener{ numPress(4) }
        binding.btn5.setOnClickListener{ numPress(5) }
        binding.btn6.setOnClickListener{ numPress(6) }
        binding.btn7.setOnClickListener{ numPress(7) }
        binding.btn8.setOnClickListener{ numPress(8) }
        binding.btn9.setOnClickListener{ numPress(9) }

        binding.btnEqual.setOnClickListener{ equalPress() }

        // Include a clear button to reset the calculator
        binding.btnClear.setOnClickListener{ clear() }

        // Include a +/- button to allow for negative numbers
        binding.btnSign.setOnClickListener { plusMinusPress() }

        // Include a decimal button
        binding.btnDut.setOnClickListener{ decimalPress() }

        // Include a delete button to delete the last entered number
        binding.btnDelete.setOnClickListener{ delete() }


    }
    private fun numPress(num: Int) {
        expression += num.toString()
        binding.tvResult.text = expression

        if (operator == "") {
            leftOperand += num.toString()
        }
        else {
            rightOperand += num.toString()
        }
    }
    private fun operatorPress(op: String) {
        operator = op

        expression += op
        binding.tvResult.text = expression
    }
    private fun equalPress() {
        if (leftOperand.isNotEmpty() && rightOperand.isNotEmpty()) {

            if ( leftOperand.endsWith(".") )
                leftOperand = "${leftOperand}0"
            if ( rightOperand.endsWith(".") )
                rightOperand = "${rightOperand}0"

            val num1 = leftOperand.toFloat()
            val num2 = rightOperand.toFloat()
            val result: Float = when (operator) {
                "+" -> num1 + num2
                "-" -> num1 - num2
                "*" -> num1 * num2
                "/"-> {
                    // Make sure the user cannot divide by zero
                    if (num2 == 0.0f)
                        0.0f
                    else
                        num1 / num2
                }
                else -> 0.0f
            }
            expression = result.toString()
            if(expression.endsWith(".0")) {
                expression = result.toInt().toString()
            }
            binding.tvResult.text = expression

            leftOperand = expression
            operator = ""
            rightOperand = ""
        }


    }
    private fun clear() {
        leftOperand = ""
        operator = ""
        rightOperand = ""
        expression = ""
        binding.tvResult.text = expression
    }
    private fun plusMinusPress() {
        if (operator =="") {
            leftOperand = if(leftOperand.startsWith("-"))
                "+$leftOperand"
            else if(leftOperand.startsWith("+"))
                "-$leftOperand"
            else
                "-$leftOperand"
            expression = leftOperand
        }
        else {
            rightOperand = if(rightOperand.startsWith("-"))
                "+$rightOperand"
            else if(rightOperand.startsWith("+"))
                "-$rightOperand"
            else
                "-$rightOperand"
            expression = "$leftOperand$operator$rightOperand"
        }
        binding.tvResult.text = expression
    }
    private fun decimalPress() {
        if (operator == "") {
            if( leftOperand.isNotEmpty()) {
                leftOperand = "$leftOperand."
                expression = leftOperand
            }
        } else {
            if( rightOperand.isNotEmpty()) {
                rightOperand = "$rightOperand."
                expression = "$leftOperand$operator$rightOperand"
            }
        }
        binding.tvResult.text = expression
    }
    private fun delete() {
        if (operator == "") {
            if(leftOperand.isNotEmpty()) {
                leftOperand = leftOperand.substring(0, leftOperand.length-1)
                expression = leftOperand
            }
        } else {
            if(rightOperand.isNotEmpty()) {
                rightOperand = rightOperand.substring(0, rightOperand.length-1)
                expression = "$leftOperand$operator$rightOperand"
            }
            else {
                operator =""
                expression = leftOperand
            }
        }
        binding.tvResult.text = expression
    }

}