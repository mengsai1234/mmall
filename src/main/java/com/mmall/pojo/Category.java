package com.mmall.pojo;

import lombok.*;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = "id")
public class Category {
    private Integer id;

    private Integer parentId;

    private String name;

    private Boolean status;

    private Integer sortOrder;

    private Date createTime;

    private Date updateTime;

    //重写对象的HashCode()和equals()方法，进行去重
    //简单的方法重写
//    @Override
//    public int hashCode() {
//        return id != null ? id.hashCode() : 0;
//    }
//
//    @Override
//    public boolean equals(Object obj) {
//        if(this == obj){
//            return true;
//        }
//        if(obj == null || obj.getClass() != this.getClass()){
//            return false;
//        }
//        Category category = (Category) obj;
//        return !(id != null ? id.equals(category.id) : category.id != null);
//    }
}