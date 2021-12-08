package com.jsxl;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Person {
    // @JSONField 注解，以便实现自定义转换：
    @JSONField(name="AGE", serialize=false)//默认情况下， FastJson 库可以序列化 Java bean 实体， 但我们可以使用 serialize 指定字段不序列化。
    private int age;

    @JSONField(name="LAST NAME", ordinal = 2)
    private String lastName;

    @JSONField(name="FIRST NAME", ordinal = 1)//使用 ordinal 参数指定字段的顺序
    private String firstName;

    @JSONField(name = "DATE OF BIRTH", deserialize=false)
    private Date dateOfBirth;

    public long getFullName() {
        return lastName.length()+firstName.length();
    }
}