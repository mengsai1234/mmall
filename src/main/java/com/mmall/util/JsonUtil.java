package com.mmall.util;

import com.alipay.api.internal.util.StringUtils;
import com.google.common.collect.Lists;
import com.mmall.pojo.User;
import lombok.extern.slf4j.Slf4j;
import org.codehaus.jackson.map.DeserializationConfig;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.SerializationConfig;
import org.codehaus.jackson.map.annotate.JsonSerialize.Inclusion;
import org.codehaus.jackson.type.JavaType;
import org.codehaus.jackson.type.TypeReference;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.List;

/**
 * Created by Administrator on 2018/1/18 0018.
 */
@Slf4j
public class JsonUtil {

    private static ObjectMapper objectMapper = new ObjectMapper();

    /**
     * 初始化objectMapper
     */
    static{

        /*--------序列化设置开始---------*/
        //设置序列化时，对象的所有字段全部列入序列化范围
        objectMapper.setSerializationInclusion(Inclusion.ALWAYS);

        //取消默认转换timestamps形式
        objectMapper.configure(SerializationConfig.Feature.WRITE_DATES_AS_TIMESTAMPS,false);

        //忽略bean转json的错误
        objectMapper.configure(SerializationConfig.Feature.FAIL_ON_EMPTY_BEANS,false);

        //所有的日期格式都统一为yyyy-MM-dd HH:mm:ss
        objectMapper.setDateFormat(new SimpleDateFormat(DateTimeUtil.STANDARD_FORMAT));

        /*----------------------序列化设置结束-------------------*/


        /*-----------------反序列化开始------------------*/
        //忽略在json字符串中存在、但在java对象中不存在对应属性的情况，防止错误
        objectMapper.configure(DeserializationConfig.Feature.FAIL_ON_UNKNOWN_PROPERTIES,false);
        /*-----------------反序列化结束-----------------*/
    }

    /**
     * 将对象格式化成字符串，没有进行样式设置
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2String(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse obj to String error",e);
            return null;
        }
    }

    /**
     * 返回格式化好的json字符串，样式优美
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> String obj2StringPretty(T obj){
        if(obj == null){
            return null;
        }
        try {
            return obj instanceof String ? (String) obj : objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (IOException e) {
            log.warn("Parse obj to String error",e);
            return null;
        }
    }

    /**
     * 将json格式的字符串转换成泛型<T>对象
     * @param str
     * @param clazz
     * @param <T>
     * @return
     */
    public static <T> T String2Obj(String str,Class<T> clazz){
        if(StringUtils.isEmpty(str) || clazz == null){
            return null;
        }
        try {
            return clazz.equals(String.class)?(T)str:objectMapper.readValue(str,clazz);
        } catch (IOException e) {
            log.warn("Sring to Obj error",e);
            return null;
        }
    }

    /**
     * 重载，反序列化任意类型的对象成字符串
     * @param str
     * @param typeReference
     * @param <T>
     * @return
     */
    public static <T> T String2Obj(String str, TypeReference<T> typeReference){
        if(StringUtils.isEmpty(str) || typeReference == null){
            return null;
        }
        try {
            return (T)(typeReference.getType().equals(String.class)? str:objectMapper.readValue(str,typeReference));
        } catch (IOException e) {
            log.warn("Sring to Obj error",e);
            return null;
        }
    }

    /**
     * 反序列化，
     * @param str
     * @param collectionClass 反序列化成集合
     * @param elementClasses 集合中存放的对象类型
     * @param <T>
     * @return
     */
    public static <T> T String2Obj(String str, Class<?> collectionClass,Class<?>... elementClasses){
        JavaType javaType = objectMapper.getTypeFactory().constructParametricType(collectionClass,elementClasses);
        try {
            return objectMapper.readValue(str,javaType);
        } catch (IOException e) {
            log.warn("Sring to Obj error",e);
            return null;
        }
    }

    public static void main(String[] args) {
        User u1 = new User();
        u1.setId(1);
        u1.setEmail("happymmall.com");
        User u2 = new User();
        u2.setId(2);
        u2.setEmail("mengs.com");

        String user1Json = JsonUtil.obj2String(u1);
        log.info("user1Json:{}",user1Json);
        String user1JsonPretty = JsonUtil.obj2StringPretty(u1);
        log.info("user2Json:{}",user1JsonPretty);

        User user = JsonUtil.String2Obj(user1Json,User.class);

        List<User> userList = Lists.newArrayList();
        userList.add(u1);
        userList.add(u2);

        //将List序列化成String
        String userListStr = JsonUtil.obj2StringPretty(userList);
        log.info("===============");
        log.info(userListStr);

        List<User> userListObj = JsonUtil.String2Obj(userListStr, new TypeReference<List<User>>() {
        });

        List<User> userListObj2 = JsonUtil.String2Obj(userListStr,List.class,User.class);


        System.out.println("end");
    }
}
