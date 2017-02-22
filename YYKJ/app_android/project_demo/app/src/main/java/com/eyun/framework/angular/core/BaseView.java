package com.eyun.framework.angular.core;

/**
 * Created by administrator on 2016-11-4.
 */
public interface BaseView extends Cloneable {

     void setModel(String model);

     Scope getScope();
    void setScope(Scope scope);
    void setModelExpression(String modelExpression);
    String getModelExpression();
    String[] getModels();

    public void setModels(String[] models);
    public BaseView clone() ;

}
