package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.TeamsApi;
import ch.ubs.m295.generated.v1.dto.Team;
import com.ubs.m295.jdbc.dao.TeamsDao;
import org.slf4j.ILoggerFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.NativeWebRequest;

import java.util.List;
import java.util.Optional;


@RestController
public class TeamsController implements TeamsApi{

    private TeamsDao dao;

    private static Logger Logger = LoggerFactory.getLogger(TeamsDao.class);

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TeamsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Team>> teamsGet() {
        return ResponseEntity.ok(dao.getAllTeams());
    }

    @Override
    public ResponseEntity<Team> teamsPost(Team team) {
        dao.insertTeam(team);
        return ResponseEntity.ok(team);
        //Logger.info("Hat nicht geklappt");
    }

    @Override
    public ResponseEntity<Void> teamsTeamIdDelete(Integer teamId) {
        dao.deleteTeam(teamId);
        return new ResponseEntity<>(HttpStatus.OK);

    }

    @Override
    public ResponseEntity<Team> teamsTeamIdGet(Integer teamId) {
        dao.getTeamById(teamId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Override
    public ResponseEntity<Team> teamsTeamIdPut(Integer teamId, Team team) {
        dao.updateTeam(team);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    public TeamsController(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dao = new TeamsDao(namedParameterJdbcTemplate);
    }
}
