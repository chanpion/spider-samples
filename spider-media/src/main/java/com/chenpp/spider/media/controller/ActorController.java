package com.chenpp.spider.media.controller;

import com.chenpp.spider.media.entity.Actor;
import com.chenpp.spider.media.entity.AppResponse;
import com.chenpp.spider.media.service.ActorService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author April.Chen
 * @date 2024/6/13 14:55
 */
@RestController
public class ActorController {

    @Resource
    private ActorService actorService;

    @GetMapping("/actor/{id}")
    public AppResponse<Actor> getActor(@PathVariable Long id) {
        return new AppResponse<>(actorService.getById(id));
    }

}
