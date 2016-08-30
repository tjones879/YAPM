package me.tdjones.main.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;

/**
 * Created by Tyler on 08/30/2016.
 */
public class LengthAdapter extends XmlAdapter<String, Integer> {

    @Override
    public Integer unmarshal(String value){
        int length = 0;
        if (value.contains(":")){
            length = TimeUtil.parseTimeStamp(value);
        } else {
            length = Integer.parseInt(value);
        }
        return length;
    }

    @Override
    public String marshal(Integer length){
        return Integer.toString(length);
    }
}
