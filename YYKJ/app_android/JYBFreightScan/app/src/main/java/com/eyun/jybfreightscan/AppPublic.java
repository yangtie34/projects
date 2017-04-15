package com.eyun.jybfreightscan;


/**
 * Created by Administrator on 2017/3/3.
 */

public class AppPublic {
    public static boolean init=false;

    public static class ScanType{
        public static int SAVE=0;
        public static int FJ=1;
        public static int CRC=2;
    }

    public static class ChangeType{
        public static int in=1;
        public static int out=2;
        public static int move=3;
        public static int take=4;
    }
    public static class TakeRecState{//0-已初盘，1-已复盘，2-已查核，3-已稽核
        public static int first=0;
        public static int two=1;
        public static int check=2;
        public static int ok=3;
    }
}
