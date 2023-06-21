package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.GroupsApi;
import ch.ubs.m295.generated.v1.dto.ChampionsLeagueGroup;
import com.ubs.m295.dao.GroupsDao;
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

    private static Logger Logger = LoggerFactory.getLogger(GroupsController.class);

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return GroupsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<ChampionsLeagueGroup>> groupsGet() {
        try {
            Logger.info("Received request to retrieve all groups");
            List<ChampionsLeagueGroup> groups = dao.getAllGroups();
            Logger.info("Retrieved {} groups", groups.size());
            return ResponseEntity.ok(groups);
        } catch (Exception exception) {
            Logger.error("Failed to retrieve groups", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsPost(ChampionsLeagueGroup group) {
        try {
            Logger.info("Received request to insert a group: {}", group.getGroupName());
            dao.insertGroup(group);
            Logger.info("Inserted group: {}", group.getGroupName());
            return ResponseEntity.ok(group);
        } catch (Exception exception) {
            Logger.error("Failed to insert group", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Void> groupsGroupNameDelete(String groupName) {
        try {
            Logger.info("Received request to delete group with name: {}", groupName);
            boolean success = dao.deleteGroup(groupName);
            if (success) {
                Logger.info("Deleted group with name: {}", groupName);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Logger.info("Group not found with name: {}", groupName);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            Logger.error("Failed to delete group", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsGroupNameGet(String groupName) {
        try {
            Logger.info("Received request to retrieve group with name: {}", groupName);
            Optional<ChampionsLeagueGroup> group = dao.getGroupByName(groupName);
            if (group.isPresent()) {
                Logger.info("Retrieved group with name: {}", groupName);
                return ResponseEntity.ok(group.get());
            } else {
                Logger.info("Group not found with name: {}", groupName);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            Logger.error("Failed to retrieve group", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<ChampionsLeagueGroup> groupsGroupNamePut(String groupName, ChampionsLeagueGroup group) {
        try {
            Logger.info("Received request to update group with name: {}", groupName);
            group.setGroupName(ChampionsLeagueGroup.GroupNameEnum.valueOf(groupName));
            boolean success = dao.updateGroup(group);
            if (success) {
                Logger.info("Updated group with name: {}", groupName);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Logger.info("Group not found with name: {}", groupName);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            Logger.error("Failed to update group", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public GroupsController(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dao = new GroupsDao(namedParameterJdbcTemplate);
    }


}
