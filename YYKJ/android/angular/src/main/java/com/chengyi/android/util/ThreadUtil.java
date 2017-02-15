package com.chengyi.android.util;

/**
 * Created by administrator on 2017-2-8.
 */

public class ThreadUtil implements Runnable{
        private Thread t;
        private boolean suspended=false;
        private boolean loop=false;
        private PubInterface run;
        public static ThreadUtil getLoopThread(PubInterface run){
            ThreadUtil threadRun=new ThreadUtil();
            threadRun.loop=true;
            threadRun.run=run;
            return threadRun;
        }
        public static ThreadUtil getThread(PubInterface run){
            ThreadUtil threadRun=new ThreadUtil();
            threadRun.loop=false;
            threadRun.run=run;
            return threadRun;
        }
        private ThreadUtil(){}
        @Override
        public void run() {
            if(loop) {
                try {
                    while (true) {
                        run.run();
                        synchronized (this) {
                            while (suspended) {
                                wait();
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }else{
                run.run();
            }
        }
        /**
         * 开始
         */
        public void start(){
            if(t==null){
                t=new Thread(this);
                t.start();
            }
        }
        /**
         * 暂停
         */
        public void suspend(){
            suspended = true;
        }

        /**
         * 继续
         */
        public synchronized void resume(){
            suspended = false;
            notify();
        }
    };
