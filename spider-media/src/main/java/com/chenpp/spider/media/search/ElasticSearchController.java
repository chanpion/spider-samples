package com.chenpp.spider.media.search;

import com.chenpp.spider.media.entity.AppResponse;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author April.Chen
 * @date 2024/6/13 16:45
 */
@RestController
public class ElasticSearchController {

    @Resource
    private ElasticSearchService elasticSearchService;

    @GetMapping("/search")
    public AppResponse<List<ElasticsearchDocument>> search(String keyword) {
        List<ElasticsearchDocument> hists = elasticSearchService.findByTitle(keyword);
        return AppResponse.success(hists);
    }
}
