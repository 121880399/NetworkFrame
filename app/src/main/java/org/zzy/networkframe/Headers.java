package org.zzy.networkframe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Http头的封装，请求头和响应头都用这个
 * 使用建造者模式来构建
 * Builder类中使用ArrayList来存储name和value，而外部Header类中使用数字来存储。
 * 理由：1.在构建请求头的时候，插入删除的操作频繁，链表的效率比数组高
 * 2.在插入的过程中，并不知道用户要插入多少个头字段，链表可以动态分配内存，而数组不可以
 * 项目名称: NetworkFrame
 * 创建人: 周正一
 * 创建时间：2017/9/12
 */

public class Headers {
    //name存入前面，value存入后面
    private final String[] namesAndValues;

    public Headers(Builder builder) {
        this.namesAndValues=builder.namesAndValues.toArray(new String[builder.namesAndValues.size()]);
    }

    /**
     * 通过name得到value
     * */
    public String get(String name){
        for(int i=namesAndValues.length-2; i >0 ; i-=2){
            if(name.equalsIgnoreCase(namesAndValues[i])){
                return namesAndValues[i+1];
            }
        }
        return null;
    }

    public int size(){
        return namesAndValues.length==0 ? 0 : namesAndValues.length/2;
    }

    /**
     * 得到一个Builder对象
     * */
    public Builder newBuilder(){
        Headers.Builder result=new Builder();
        Collections.addAll(result.namesAndValues,namesAndValues);
        return result;
    }


    public static class Builder{
        //name存入前面，value存入后面 初始化容量为20
        final List<String> namesAndValues = new ArrayList<>(20);


        /**
         * 添加头
         * */
        public Builder add(String name,String value){
            namesAndValues.add(name);
            namesAndValues.add(value.trim());
            return this;
        }

        /**
         * 删除所有字段为name的数据
         * */
        public Builder removeAll(String name){
            //因为前面存储name后面存储value，所以2个为一个头字段，增加的时候要加2
            for(int i=0; i<namesAndValues.size();i+=2){
                if(name.equalsIgnoreCase(namesAndValues.get(i))) {
                    //虽然在同一个位置删除了2次，但是删除的却是不同的值
                    namesAndValues.remove(i);//删除name
                    namesAndValues.remove(i);//删除value
                    //删除了2个数据，i值也要减2 才能保证下次检索的数据是现在i的位置
                    i-=2;
                }
            }
            return this;
        }

        /**
         * 更新头字段
         * */
        public Builder update(String name ,String value){
            removeAll(name);
            add(name,value);
            return this;
        }


        public Headers build(){
            return new Headers(this);
        }

    }
}
