package com.jhkj.mosdc.framework.scheduling;


public class MyTask extends Task{
	public static int num = 100;
	public String name;
	
	public void setName(String name) {
		this.name = name;
	}

	public void run(){
		System.out.println("firstrun"+"-"+num+++"----------"+name+this.getLastEndTime());
	}
	public void secondRun(){
		System.out.println("secondRun"+"-"+num+++"----------"+name+this.getLastEndTime());
	}
	public void thirdRun(){
		System.out.println("thirdRun"+"-"+num+++"----------"+name+this.getLastEndTime());
	}
}
