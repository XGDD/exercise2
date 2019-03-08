package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

/**
 * 快速排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */
public class QuickSorter implements Sorter {
    @Override
    public void sort(Element[] elements) {
        //TODO
        int left = 0;
        int right = elements.length;
        quickSort(elements, left, right);
    }
    public static void quickSort(Element[] elements, int left, int right){
        if(left < right){
            int mid = quickSort_OP(elements, left, right);
            quickSort(elements, left, mid);
            quickSort(elements, mid + 1,right);
        }
    }
    public static int getMid(Element[] elements, int left, int right){
        int mid = left + (right - left) >> 1;
        if(elements[left].compareTo(elements[right]) < 0){
            if(elements[left].compareTo(elements[mid]) > 0){
                return left;
            }else if(elements[right].compareTo(elements[mid]) > 0){
                return right;
            }else{
                return mid;
            }
        }
        else{
            if(elements[left].compareTo(elements[mid]) < 0){
                return left;
            }else if(elements[right].compareTo(elements[mid]) < 0){
                return right;
            }else{
                return mid;
            }
        }
    }
    public static int quickSort_OP(Element[] elements, int left, int right){
        int begin = left;
        int end = right - 1;
        //int mid = getMid(elements, left,right);
        Element key = elements[end];
        while(begin < end){
            while(begin < end && elements[begin].compareTo(key) < 0){
                begin++;
            }
            if(begin < end){
                elements[end--] = elements[begin];
            }
            while(begin < end && elements[end].compareTo(key) > 0){
                end--;
            }
            if(begin < end){
                elements[begin++] = elements[end];
            }
        }
        elements[begin] = key;
        return begin;
    }
}
