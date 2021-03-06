package com.wbz.beanutils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
//注入bean属性的工具类
public class BeanUtils {

    //参数1 bean对象
    //参数2 要获得的bean对象对应的属性名称，比如有一个user对象，得到该user对象,user对象中还有一个name属性，还需要将name属性的名字传递过来，根据这个方法获取到setName()
    public static Method getWriteMethod(Object beanObj, String name) {
        Method method = null;
        //内省技术实现该方法
        try {
            //1.分析bean对象 获得beaninfo
            BeanInfo beanInfo = Introspector.getBeanInfo(beanObj.getClass());

            //2.根据beaninfo 获得所有属性的描述器
            PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

            //3.遍历这些属性描述器
            if (propertyDescriptors!=null){
                for (PropertyDescriptor pd: propertyDescriptors) {
                    //3.1 判断当前遍历的描述器描述的属性是否是要找的属性
                    //获得当前描述器描述的属性名称，
                    String pdName = pd.getName();
                    // 使用要找的属性名称与当前描述器描述的属性名称比对
                    if(pdName.equals(name)){
                        //比对一致则找到了
                       method = pd.getWriteMethod();

                    }
                }
            }
        } catch (IntrospectionException e) {
            e.printStackTrace();
        }
        //4.返回找到的set方法
           //4.1如果没找到，抛出异常提示用户检查是否创建属性对应的set方法
        if (method==null){
            throw new RuntimeException("请检查"+name+"属性的set方法是否创建");
        }
        return method;
    }
}

