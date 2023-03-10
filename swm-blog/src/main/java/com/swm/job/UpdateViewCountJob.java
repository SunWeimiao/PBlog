package com.swm.job;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.swm.constants.SystemConstants;
import com.swm.domain.entity.Article;
import com.swm.service.ArticleService;
import com.swm.utils.RedisCache;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component

public class UpdateViewCountJob {
    @Autowired
    private RedisCache redisCache;
    @Autowired
    private ArticleService articleService;
    @Scheduled(cron = "0 0/15 * * * ?")//从0分0秒开始，每隔15分钟执行一次。
    public void updateViewCount(){
        //获取redis中的文章浏览量
        Map<String, Integer> viewCountMap = redisCache.getCacheMap(SystemConstants.REDISKEY_VIEWCOUNT);
        List<Article> articles = viewCountMap.entrySet()
                .stream()
                .map(entry -> new Article(Long.valueOf(entry.getKey()), entry.getValue().longValue()))
                .collect(Collectors.toList());
        //更新浏览量
            for (Article article : articles) {
                LambdaUpdateWrapper<Article> updateWrapper = new LambdaUpdateWrapper<>();
                updateWrapper.eq(Article :: getId, article.getId());
                updateWrapper.set(Article :: getViewCount, article.getViewCount());
                articleService.update(updateWrapper);
            }
    }
}
