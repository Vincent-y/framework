package com.java.py4j;


import com.java.py4j.exception.IllegalPinyinException;

public interface Converter {

    String[] getPinyin(char ch) throws IllegalPinyinException;

    String getPinyin(String chinese) throws IllegalPinyinException;
}
