package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.GroupsApi;
import ch.ubs.m295.generated.v1.dto.ChampionsLeagueGroup;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class GroupController extends AbstractRestController implements GroupsApi {

    private final List<ChampionsLeagueGroup> groups = new ArrayList<>();

    @Override
    public ResponseEntity<List<ChampionsLeagueGroup>> groupsGet() {
        return getRespond(groups);
    }

    @Override
    public ResponseEntity<Void> groupsPost(ChampionsLeagueGroup group) {
        groups.add(group);
        return postRespond();
    }
}
