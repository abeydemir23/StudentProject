package com.alicanbeydemir.studentproject;

import java.util.ArrayList;

class ParanthesesStack<T> {


    ArrayList<T> stackArray;
    int top = -1;


    private boolean isInitialState;

    ParanthesesStack() {

        this.isInitialState = true;
        this.stackArray = new ArrayList<T>();
    }


    void push(T X) {
        this.isInitialState = false;
        stackArray.add(X);
    }

    T top() {
            return stackArray.get(stackArray.size()-1);
    }

    void pop() {
        stackArray.remove(stackArray.size()-1);
    }

    boolean empty() {
        return stackArray.isEmpty();
    }

    boolean isInitialState() {
        return this.isInitialState;
    }
}

public class ParenthesesValidator {

    public static boolean isValid(String paranthesesString)  {
        ParanthesesStack stack = new ParanthesesStack();
        for (char st : paranthesesString.toCharArray()) {
            if (st == '(' || st == '{' || st == '[') {
                stack.push(st);
            }
            else {
                if(stack.empty()) {
                    System.out.println(paranthesesString +
                            " contains invalid parentheses.");
                    return false;
                }
                else{
                    char top = (Character) stack.top();
                    if(st == ')' && top == '(' ||
                            st == '}' && top == '{' ||
                            st == ']' && top == '[') {
                        stack.pop();
                    }
                    else{
                        System.out.println(paranthesesString +
                                " contains invalid parentheses.");
                        return false;
                    }
                }
            }
        }
        if(stack.empty() && !stack.isInitialState()) {
            System.out.println(paranthesesString +
                    " contains valid parentheses.");
        return true;
        }
        else{
            System.err.println(paranthesesString +
                    " contains invalid parentheses.");
            return false;
        }
    }

    public static void main(String[] args) {
        String input_str1 = "{{}}()[()]";
        String input_str2 = "(][)";
        String input_str3 = ")";
        isValid(input_str1);
        isValid(input_str2);
        isValid(input_str3);

    }

    }
