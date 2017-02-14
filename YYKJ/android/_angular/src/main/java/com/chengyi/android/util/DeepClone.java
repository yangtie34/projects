package com.chengyi.android.util;

import android.view.View;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.List;

/**
 * Created by administrator on 2016-11-4.
 */

public class DeepClone {
    public static <T> List<T> listClone(List<T> list1) {
       return  (List<T>)deepClone(list1);
    }
    public static View viewClone(View list1) {
        return  (View)deepClone(list1);
    }
    public static Object deepClone(Object obj) {
        try {
            ByteArrayOutputStream buf = new ByteArrayOutputStream();
            ObjectOutputStream out = new ObjectOutputStream(buf);
            out.writeObject(obj);
            out.close();
            byte[] bytes = buf.toByteArray();
            ByteArrayInputStream byIn = new ByteArrayInputStream(bytes);
            ObjectInputStream in = new ObjectInputStream(byIn);
            Object object = in.readObject();
            return object;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
