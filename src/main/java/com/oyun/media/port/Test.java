package com.oyun.media.port;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * @program: epaper
 * @description:
 * @author: changzhen
 * @create: 2018-10-23 14:46
 **/
public class Test {

//    public static void main(String[] args) {
//        PortFunctionService service = new PortFunctionService();
//        PortFunctionDelegate portType = service.getPortFunctionPort();
//        try {
//            System.out.println(portType.function("ᠬᠦᠮᠦᠨ"));
//        } catch (Exception_Exception e) {
//            e.printStackTrace();
//        }
//
//    }


    public static void main(String[] args) {
//        Map<Integer,String> map = new HashMap<>();
//
//
//        map.put(2,"2");
//        map.put(3,"3");
//        map.put(1,"1");
//        Iterator<Map.Entry<Integer, String>> it = map.entrySet().iterator();
//
//        while(it.hasNext()){
//            Map.Entry<Integer, String> et = it.next();
//            System.out.println(et.getKey()+"===="+et.getValue());
//        }

        StringBuffer stringBuffer = new StringBuffer("123");
        StringBuffer stringBuffer1 = new StringBuffer("456");
        stringBuffer.insert(0, stringBuffer1);
        System.out.println(stringBuffer);

    }
}
