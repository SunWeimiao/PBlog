package com.swm.utils;

import com.swm.domain.entity.Article;
import com.swm.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

public class BeanCopyUtils {
    private BeanCopyUtils(){
    }
    public static <V> V copyBean(Object source, Class<V> target) {
        //创建目标对象V
        V result;
        try {
            result = target.newInstance();
            //实现属性copy
            BeanUtils.copyProperties(source, result);
        } catch (InstantiationException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        //返回结果
        return result;
    }

    public static  <O,V> List<V> copyBeanList(List<O> list,Class<V> target){
        return list.stream()
                .map(o->copyBean(o,target))
                .collect(Collectors.toList());
    }

    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1111L);
        article.setTitle("test1111");
        HotArticleVo hotArticleVo = copyBean(article,HotArticleVo.class);
        System.out.println(hotArticleVo);
    }
}
