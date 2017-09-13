package org.zzy.networkframe;

import java.util.ArrayList;
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

    }

    public static class Builder{
        //name存入前面，value存入后面 初始化容量为20
        final List<String> namesAndValues = new ArrayList<>(20);
    }
}
