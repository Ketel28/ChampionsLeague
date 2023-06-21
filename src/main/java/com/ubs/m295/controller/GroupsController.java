package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.GroupsApi;
import ch.ubs.m295.generated.v1.dto.ChampionsLeagueGroup;
import com.ubs.m295.jdbc.dao.GroupsDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;

@RestController
public class GroupsController implements GroupsApi{

    private GroupsDao dao;

    private static Logger Logger = LoggerFactory.getLogger(GroupsDao.class);

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return GroupsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<ChampionsLeagueGroup>> groupsGet() {
        return ResponseEntity.ok(dao.getAllGroups());
    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsPost(ChampionsLeagueGroup group) {
        dao.insertGroup(group);
        return ResponseEntity.ok(group);
        //Logger.info("Hat nicht geklappt");
    }

    @Override
    public ResponseEntity<Void> groupsGroupNameDelete(String groupName) {
        dao.deleteGroup(groupName);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsGroupNameGet(String groupName) {
        dao.getGroupByName(groupName);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsGroupNamePut(String groupName, ChampionsLeagueGroup group) {
        dao.updateGroup(group);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public GroupsController(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dao = new GroupsDao(namedParameterJdbcTemplate);
    }


}
