package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

/**
 * 希尔排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */
public class ShellSorter implements Sorter {
    
    @Override
    public void sort(Element[] elements) {
        //TODO
        int gap = 3;
        while(gap > 0){
            for(int i = 0; i < elements.length - gap; i++){
                int end = i + gap;
                if(elements[i].compareTo(elements[end]) > 0){
                    Element key = elements[i];
                    elements[i] = elements[end];
                    elements[end] = key;
                }
            }
            gap--;
        }
    }
}
