package com.eyunsoft.app_wasteoil.Model;

import java.io.Serializable;

/**
 * Created by Administrator on 2014-11-28.
 */
public class NameToValue implements Serializable {
    public String InfoName="";
    public String InfoValue="";

    public NameToValue()
    {
    }
    public NameToValue(String InfoName,String InfoValue)
    {this.InfoName=InfoName;this.InfoValue=InfoValue;
    }

    @Override
    public String toString()
    {
        return InfoName.toString();
    }
}
