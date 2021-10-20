package com.example.calculatorkotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {

    private lateinit var box: EditText
    private lateinit var result: EditText

    var operation = ""
    var oldTxt  = ""
    var oldText = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        val zero = findViewById<Button>(R.id.zero)
        val one = findViewById<Button>(R.id.one)
        val two = findViewById<Button>(R.id.two)
        val three = findViewById<Button>(R.id.three)
        val four = findViewById<Button>(R.id.four)
        val five = findViewById<Button>(R.id.five)
        val six = findViewById<Button>(R.id.six)
        val seven = findViewById<Button>(R.id.seven)
        val eight = findViewById<Button>(R.id.eigth)
        val nine = findViewById<Button>(R.id.nine)

        val reset = findViewById<Button>(R.id.reset)
        val delete = findViewById<ImageButton>(R.id.delete)

        box = findViewById(R.id.inputBox)

        val plus = findViewById<Button>(R.id.plus)
        val minus = findViewById<Button>(R.id.minus)
        val div = findViewById<Button>(R.id.division)
        val multi = findViewById<Button>(R.id.multiply)

        val equal = findViewById<Button>(R.id.equal)

        val percent = findViewById<Button>(R.id.percent)
        val doth = findViewById<Button>(R.id.doth)
        val swap = findViewById<Button>(R.id.swap)

        val toastTxt = resources.getString(R.string.Toast_string)
        val myToast = Toast.makeText(applicationContext, toastTxt, Toast.LENGTH_SHORT)


        //UI off
        box.showSoftInputOnFocus = false

        zero.setOnClickListener {
            updateText("0")
        }
        one.setOnClickListener {
            updateText("1")
        }
        two.setOnClickListener {
            updateText("2")
        }
        three.setOnClickListener {
            updateText("3")
        }
        four.setOnClickListener {
            updateText("4")
        }
        five.setOnClickListener {
            updateText("5")
        }
        six.setOnClickListener {
            updateText("6")
        }
        seven.setOnClickListener {
            updateText("7")
        }
        eight.setOnClickListener {
            updateText("8")
        }
        nine.setOnClickListener {
            updateText("9")
        }

        plus.setOnClickListener {
            operators(plus)
        }
        minus.setOnClickListener {
            operators(minus)
        }
        div.setOnClickListener {
            operators(div)
        }
        multi.setOnClickListener {
            operators(multi)
        }

        percent.setOnClickListener {
            if (box.text.toString().isBlank() || box.text.toString().toRegex()matches("-")){
                if (myToast != null){
                    myToast.cancel()
                }
                myToast.show()
            }else{
                val value = box.text.toString().toDouble() / 100
                box.setText(value.toString())
                val cursorPosition = box.selectionStart
                val leng = box.text.length
                box.setSelection(cursorPosition + leng)
            }
        }

        doth.setOnClickListener {
            if (!box.text.toString().contains(".")){
                if (box.text.toString().toRegex().matches("")){
                    val cursorPosition = box.selectionStart
                    box.setText("0.")
                    box.setSelection(cursorPosition + 2)
                }else if (box.text.toString().toRegex().matches("-")){
                    val cursorPosition = box.selectionStart
                    box.setText("-0.")
                    box.setSelection(cursorPosition + 2)
                }else{
                    val txt = box.text.toString()
                    val cursorPosition = box.selectionStart
                    box.setText(txt + ".")
                    box.setSelection(cursorPosition + 1)
                }
            }
        }

        swap.setOnClickListener {
            val txt = box.text.toString()
            if (box.text.toString().contains("-")){
                box.setText(txt.replace("-",""))
                val cursorPosition = box.selectionStart
                val leng = box.text.length
                box.setSelection(cursorPosition + leng)
            }else{
                box.setText("-" + txt)
                val cursorPosition = box.selectionStart
                val leng = box.text.length
                box.setSelection(cursorPosition + leng)
            }
        }

        result = findViewById(R.id.resultBox)

        reset.setOnClickListener {
            box.setText("")
            result.setText("")
        }

        delete.setOnClickListener {
            if (!box.text.toString().isNullOrBlank()){
                val selected = box.text
                val cursorPosition = box.selectionStart
                selected.replace(cursorPosition - 1,cursorPosition,"")
                box.text = selected
                box.setSelection(cursorPosition - 1)
            }else{
                if (myToast != null){
                    myToast.cancel()
                }
                myToast.show()
            }
        }

        equal.setOnClickListener {
            if (box.text.toString().isBlank() || box.text.toString().contains("-")){
                if (myToast != null){
                    myToast.cancel()
                }
                myToast.show()
            }else{
                calc(box.text.toString())
            }
        }
    }
    /**
     * @param  AddTxt = button value
     * @return Add value of button to EditText
     */
    private fun updateText(AddTxt: String){
        box = findViewById(R.id.inputBox)
        oldText = box.text.toString()
        val cursorPosition = box.selectionStart
        val leftTxt = oldText.substring(0,cursorPosition)
        val rightTxt = oldText.substring(cursorPosition)
        box.setText(String.format("%s%s%s",leftTxt,AddTxt,rightTxt))
        box.setSelection(cursorPosition + 1)
    }

    /**
     * @param  view = type of operator
     * @return operation = sets the selected operator
     * @return result = sets text for first part of the equation
     */
    fun operators(view: View){

        val toastTxt = resources.getString(R.string.Toast_string)
        result = findViewById(R.id.resultBox)
        box = findViewById(R.id.inputBox)
        val myToast = Toast.makeText(applicationContext,toastTxt,Toast.LENGTH_SHORT)

        if (result.text.toString().isNotBlank()){
            oldTxt = result.text.toString().replace(" $operation", "")
        }else{
            oldTxt = box.text.toString()
        }

        if (oldTxt.isBlank() || oldTxt.toRegex().containsMatchIn("-")){
            if (myToast != null){
                myToast.cancel()
            }
            myToast.show()
        }else{
            if (view.id == R.id.plus){
                operation = "+"
            }else if (view.id == R.id.minus){
                operation = "-"
            }else if (view.id == R.id.division){
                operation = "÷"
            }else if (view.id == R.id.multiply){
                operation = "*"
            }
            if (box.text.toString().isNotEmpty() && result.text.toString().isNotEmpty()){
                calc(box.text.toString())
            }else{
                box.setText("")
                result.setText(oldTxt)
            }
        }
    }

    fun calc(string: String){
        result = findViewById(R.id.resultBox)
        box = findViewById(R.id.inputBox)
        val equal: Double
        val newTxt = string

        val decimalFormat= DecimalFormat("#.##")

        if (result.text.toString().isNotBlank() && result.text.toString().isNotEmpty()){
            if (operation == "+"){
                equal = oldTxt.toDouble() + newTxt.toDouble()
                box.setText("")
                if (equal.toString().contains(".0")){
                    result.setText(equal.toInt().toString())
                }else{
                    result.setText(equal.toString())
                }
            } else if (operation == "-"){
                equal = oldTxt.toDouble() - newTxt.toDouble()
                box.setText("")
                if (equal.toString().contains(".0")){
                    result.setText(equal.toInt().toString())
                }else{
                    result.setText(equal.toString())
                }
            }else if (operation == "÷"){
                equal = oldTxt.toDouble() / newTxt.toDouble()
                result.setText(String.format(decimalFormat.format(equal)))
                box.setText("")
            }else if (operation == "*"){
                equal = oldTxt.toDouble() * newTxt.toDouble()
                result.setText(String.format(decimalFormat.format(equal)))
                box.setText("")
            }
        }else{
            result.setText(oldTxt)
        }
    }
}