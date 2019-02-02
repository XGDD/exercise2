package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

/**
 * 选择排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */
public class SelectSorter implements Sorter {
    
    @Override
    public void sort(Element[] elements) {
       //TODO
        int left = 0;
        int right = elements.length;
        selectSort(elements, left, right);
    }
    public static void selectSort(Element[] elements, int left, int right){
        int begin = left;
        int end = right - 1;
        while(begin < end){
            int min = begin + 1;
            for(int i = begin; i <= end; i++){
                if(elements[i].compareTo(elements[min]) < 0){
                    min = i;
                }
            }
            if(min != begin){
                Element temp = elements[min];
                elements[min] = elements[begin];
                elements[begin] = temp;
            }
            begin++;
        }
    }
}
