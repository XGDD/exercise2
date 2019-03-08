package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

/**
 * 插入排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */
public class InsertSorter implements Sorter {
    
    @Override
    public void sort(Element[] elements) {
        //TODO
        for(int i = 1; i < elements.length; i++){
            Element key = elements[i];
            int end = i - 1;
            while((end >= 0) && (elements[end].compareTo(key) > 0)){
                elements[end + 1] = elements[end];
                end--;
            }
            elements[end + 1] = key;
        }
    }
}
