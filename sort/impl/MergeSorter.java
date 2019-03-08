package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

import javax.lang.model.util.Elements;

/**
 * 归并排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */
public class MergeSorter implements Sorter {
    
    @Override
    public void sort(Element[] elements) {
        //TODO
        Element[] temp = java.util.Arrays.copyOf(elements, elements.length);
        int right = elements.length;
        mergeSort(0,right,temp,elements);
    }
    public static void mergeSort(int left,int right,Element[] temp,Element[] elements){
        if(right - left > 1){
            int mid = left + ((right - left) >> 1);
            mergeSort(0,mid,temp,elements);
            mergeSort(mid,right,temp,elements);
            mergeData(left, mid, right,temp,elements);
            System.arraycopy(temp, left, elements, left, right - left);
        }
    }
    public static void mergeData(int left, int mid, int right, Element[] temp, Element[] elements){
        int begin1 = left;
        int end1 = mid;
        int begin2 = mid;
        int end2 = right;
        int index = left;
        while(begin1 < end1 && begin2 < end2){
            if(elements[begin1].compareTo(elements[begin2]) > 0){
                temp[index++] = elements[begin2++];
            }else{
                temp[index++] = elements[begin1++];
            }
        }
        while(begin1 < end1){
            temp[index++] = elements[begin1++];
        }
        while(begin2 < end2){
            temp[index++] = elements[begin2++];
        }
    }
}
