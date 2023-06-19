package com.ubs.m295.controller;

import ch.ubs.m295.generated.v1.controller.TeamsApi;
import ch.ubs.m295.generated.v1.dto.Team;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@RestController
public class TeamsController extends AbstractRestController implements TeamsApi {

    private final List<Team> teams = new ArrayList<>();

    @Override
    public ResponseEntity<List<Team>> teamsGet(){
        return getRespond(teams);
    }
}
