package com.jsxl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.BeanContext;
import com.alibaba.fastjson.serializer.ContextValueFilter;
import com.alibaba.fastjson.serializer.NameFilter;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static junit.framework.TestCase.assertEquals;

public class TestFastJson {
    private List<Person> listOfPersons = new ArrayList<Person>();

    @Before
    public void setUp() {
        listOfPersons.add(new Person(15, "John" ,"Doe", new Date()));
        listOfPersons.add(new Person(20, "Janette","Doe", new Date()));
    }

    @Test
    public void whenJavaList_thanConvertToJsonCorrect() {
//        String jsonOutput= JSON.toJSONString(listOfPersons);
        String jsonOutput= JSON.toJSONString(listOfPersons, SerializerFeature.BeanToArray);
        System.out.println(jsonOutput);
    }
    @Test
    public void whenGenerateJson_thanGenerationCorrect() throws ParseException {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < 2; i++) {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("AGE", 10);
            jsonObject.put("FULL NAME", "Doe " + i);
            jsonObject.put("DATE OF BIRTH", "2016/12/12 12:12:12");
            jsonArray.add(jsonObject);
            System.out.println(jsonObject);
            System.out.println(jsonArray);
        }
        String jsonOutput = jsonArray.toJSONString();
        System.out.println(jsonOutput);
    }

    @Test
    public void whenJson_thanConvertToObjectCorrect() {
        Person person = new Person(20, "John", "Doe", new Date());
        String jsonObject = JSON.toJSONString(person);
        System.out.println(jsonObject);
        Person newPerson = JSON.parseObject(jsonObject, Person.class);//必须要有无参构造，否则异常
        System.out.println(newPerson);
        assertEquals(newPerson.getAge(), 0); // 如果我们设置序列化为 false
        assertEquals(newPerson.getFullName(), listOfPersons.get(0).getFullName());
    }

    @Test
    public void givenContextFilter_whenJavaObject_thanJsonCorrect() {
        ContextValueFilter valueFilter = new ContextValueFilter () {
            @Override
            public Object process(
                    BeanContext context, Object object, String name, Object value) {
                if (name.equals("DATE OF BIRTH")) {
                    return "NOT TO DISCLOSE";
                }
                if (value.equals("John")) {
                    return ((String) value).toUpperCase();
                } else {
                    return null;
                }
            }
        };
        String jsonOutput = JSON.toJSONString(listOfPersons, valueFilter);
        System.out.println(jsonOutput);
    }

    @Test
    public void givenSerializeConfig_whenJavaObject_thanJsonCorrect() {
        NameFilter formatName = new NameFilter() {
            public String process(Object object, String name, Object value) {
                return name.toLowerCase().replace(" ", "_");
            }
        };
        SerializeConfig.getGlobalInstance().addFilter(Person.class,  formatName);
        String jsonOutput =
                JSON.toJSONStringWithDateFormat(listOfPersons, "yyyy-MM-dd");
        System.out.println(listOfPersons);
        System.out.println(jsonOutput);
    }
}
