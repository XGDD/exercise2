package com.secondriver.sort.impl;

import com.secondriver.sort.Element;
import com.secondriver.sort.Sorter;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 堆排序
 * <p>
 * Author: secondriver
 * Created: 2018/10/13
 */



//有问题，parent=length / 2,while(parent-- >= 0) {heapAdjust()....}
public class HeapSorter implements Sorter {
    
    @Override
//    public void sort(Element[] elements) {
//        //TODO
//        int size = elements.length;
//        int parent = 0;
//        for(int i = 0; i < ((size - 1) >> 1); i++){
//            heapAdjust(elements, parent, size);
//        }
//        for(int j = 0; j < size; j++){
//            heapSort(elements, size, parent);
//            size--;
//        }
//    }
//    public static void heapAdjust(Element[] elements, int parent, int size){
//        int child = (parent << 1) + 1;
//        while(child < size){
//            if((child + 1) < size && (elements[child].compareTo(elements[child+1]) < 0)){
//                child = child + 1;
//            }
//            if(elements[child].compareTo(elements[parent]) > 0){
//                Element temp = elements[child];
//                elements[child] = elements[parent];
//                elements[parent] = temp;
//                parent = child;
//            }
//            else
//                return;
//        }
//    }
//    public static void heapSort(Element[] elements, int size, int parent){
//        Element temp = elements[0];
//        elements[0] = elements[size - 1];
//        elements[size-1] = temp;
//        heapAdjust(elements, size, parent);
//    }
    public void sort(Element[] elements){
        int size = elements.length;
        int parent = (size - 1) >> 1;
        while(parent-- >= 0){
            heapAdjust(elements, parent, size);
        }
        for(int i = 0; i < size; i++){
            Element temp = elements[size-1];
            elements[size-1] = elements[0];
            elements[0] = temp;
            size--;
            heapAdjust(elements, 0, size);
        }
    }
    public static void heapAdjust(Element[] elements, int parent, int size){
        int child = (parent << 1) + 1;
        while(child < size){
            if((child+1 < size) && (elements[child].compareTo(elements[child+1]) < 0)){
                child = child+1;
            }
            if(elements[child].compareTo(elements[parent]) > 0){
                Element temp = elements[child];
                elements[child] = elements[parent];
                elements[parent] = temp;
                parent = child;
            }else{
                return;
            }
        }
    }
}
