package com.example.wes.calculator;

import android.content.res.Configuration;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private EditText result;
    private EditText newNumber;
    private TextView displayOperation;

    private Double operand1 = null;
    private String pendingOperation = "";

    private static final String STATE_PENDING_OPERATION = "Pending Operation";
    private static final String STATE_OPERAND1 = "Operand1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        result = (EditText) findViewById(R.id.result);
        newNumber = (EditText) findViewById(R.id.newNumber);
        displayOperation = (TextView) findViewById(R.id.operation);



        Button button0 = findViewById(R.id.button0);
        Button button1 = findViewById(R.id.button1);
        Button button2 = findViewById(R.id.button2);
        Button button3 = findViewById(R.id.button3);
        Button button4 = findViewById(R.id.button4);
        Button button5 = findViewById(R.id.button5);
        Button button6 = findViewById(R.id.button6);
        Button button7 = findViewById(R.id.button7);
        Button button8 = findViewById(R.id.button8);
        Button button9 = findViewById(R.id.button9);
        Button buttonDot = findViewById(R.id.buttonDot);

        Button buttonNeg = findViewById(R.id.buttonNeg);
        Button buttonEqual = findViewById(R.id.buttonEquals);
        Button buttonDiv = findViewById(R.id.buttonDivide);
        Button buttonMul = findViewById(R.id.buttonMultiply);
        Button buttonAdd = findViewById(R.id.buttonPlus);
        Button buttonSub = findViewById(R.id.buttonMinus);

        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                newNumber.append(b.getText().toString());
            }
        };

        buttonNeg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String value = newNumber.getText().toString();
                if(value.length() == 0) {
                    newNumber.setText("-");
                } else {
                    try {
                        Double doubleValue = Double.valueOf(value);
                        doubleValue *= -1;
                        newNumber.setText(doubleValue.toString());
                    } catch (NumberFormatException e) {
                        //Number was "-" or "."
                        newNumber.setText("");
                    }
                }
            }
        });

        button0.setOnClickListener(listener);
        button1.setOnClickListener(listener);
        button2.setOnClickListener(listener);
        button3.setOnClickListener(listener);
        button4.setOnClickListener(listener);
        button5.setOnClickListener(listener);
        button6.setOnClickListener(listener);
        button7.setOnClickListener(listener);
        button8.setOnClickListener(listener);
        button9.setOnClickListener(listener);
        buttonDot.setOnClickListener(listener);

        View.OnClickListener opListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Button b = (Button) v;
                String op = b.getText().toString();
                String value = newNumber.getText().toString();
                try {
                    Double doubleValue = Double.valueOf(value);
                    performOperation(doubleValue, op);
                } catch (NumberFormatException e) {
                    newNumber.setText("");
                }
                pendingOperation = op;
                displayOperation.setText(pendingOperation);
            }
        };

        buttonEqual.setOnClickListener(opListener);
        buttonDiv.setOnClickListener(opListener);
        buttonMul.setOnClickListener(opListener);
        buttonAdd.setOnClickListener(opListener);
        buttonSub.setOnClickListener(opListener);
    }

    private void performOperation(Double value, String operation) {
        if(operand1 == null) {
            operand1 = value;
            value = null;
            result.setText(operand1.toString());
            newNumber.setText("");
            return;
        } else {
            value = Double.valueOf(value);
            if(pendingOperation.equals("=")) {
                pendingOperation = operation;
                if(pendingOperation.equals("=")) {
                    operand1 = Double.valueOf(value);
                    value = null;
                    result.setText(operand1.toString());
                    newNumber.setText("");
                    return;
                } else {
                    operandHandle(value, pendingOperation);
                }
            } else {
                operandHandle(value ,pendingOperation);
            }
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putString(STATE_PENDING_OPERATION, pendingOperation);
        if(operand1 != null) {
            outState.putDouble(STATE_OPERAND1, operand1);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        setContentView(R.layout.activity_main);
        pendingOperation = savedInstanceState.getString(STATE_PENDING_OPERATION);
        operand1 = savedInstanceState.getDouble(STATE_OPERAND1);
        displayOperation.setText(pendingOperation);
    }

    private void operandHandle(Double value, String operation) {
        if(value != 0) {
            if(operation.equals("+")) {
                operand1 += value;
            } else if(operation.equals("-")) {
                operand1 -= value;
            } else if(operation.equals("*")) {
                operand1 = operand1 * value;
            } else {
                operand1 = operand1 / value;
            }
        }
        value = null;
        result.setText(operand1.toString());
        newNumber.setText("");
        return;
    }

}
