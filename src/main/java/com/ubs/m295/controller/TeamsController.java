package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.TeamsApi;
import ch.ubs.m295.generated.v1.dto.Team;
import com.ubs.m295.dao.TeamsDao;
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
public class TeamsController implements TeamsApi{

    private TeamsDao dao;

    private static Logger Logger = LoggerFactory.getLogger(TeamsController.class);

    @Override
    public Optional<NativeWebRequest> getRequest() {
        return TeamsApi.super.getRequest();
    }

    @Override
    public ResponseEntity<List<Team>> teamsGet() {
        try {
            Logger.info("Received request to retrieve all teams");
            return ResponseEntity.ok(dao.getAllTeams());

        } catch (Exception exception) {
            Logger.error("Error occurred while retrieving teams", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Team> teamsPost(Team team) {
        try {
            Logger.info("Received request to insert a team: {}", team.getTeamName());
            dao.insertTeam(team);
            Logger.info("Inserted team: {}", team.getTeamName());
            return ResponseEntity.ok(team);
        } catch (Exception exception) {
            Logger.error("Error occurred while inserting a team", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Override
    public ResponseEntity<Void> teamsTeamIdDelete(Integer teamId) {
        try {
            Logger.info("Received request to delete team with ID: {}", teamId);
            boolean success = dao.deleteTeam(teamId);
            if (success) {
                Logger.info("Deleted team with ID: {}", teamId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Logger.info("Team not found with ID: {}", teamId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            Logger.error("Error occurred while deleting a team", exception);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @Override
    public ResponseEntity<Team> teamsTeamIdGet(Integer teamId) {

        try {
            Logger.info("Received request to retrieve team with ID: {}", teamId);
            Optional<Team> team = dao.getTeamById(teamId);
            if (team.isPresent()) {
                Logger.info("Retrieved team with ID: {}", teamId);
                return ResponseEntity.ok(team.get());
            } else {
                Logger.info("Team not found with ID: {}", teamId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            Logger.error("Failed to retrieve team", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Override
    public ResponseEntity<Team> teamsTeamIdPut(Integer teamId, Team team) {
        try {
            boolean success = dao.updateTeam(team);
            if (success) {
                Logger.info("Updated team with ID: {}", teamId);
                return new ResponseEntity<>(HttpStatus.OK);
            } else {
                Logger.info("Team not found with ID: {}", teamId);
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            Logger.error("Failed to update team", exception);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    public TeamsController(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.dao = new TeamsDao(namedParameterJdbcTemplate);
    }
}
