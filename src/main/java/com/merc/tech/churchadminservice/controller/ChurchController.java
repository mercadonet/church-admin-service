package com.merc.tech.churchadminservice.controller;

import com.merc.tech.churchadminservice.model.Church;
import com.merc.tech.churchadminservice.model.Member;
import com.merc.tech.churchadminservice.service.ChurchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping("church")
    ResponseEntity<String> createChurch(@Valid @RequestBody Church church) {
        log.info("Attempting to create a church");
        churchService.createChurch(church);
        return ResponseEntity.ok("Church created");
    }

    @PostMapping("church/{churchId}/member")
    ResponseEntity<String> addChurchMember(@PathVariable("churchId") Long churchId,
                                           @Valid @RequestBody Member member) {
        log.info("Attempting to add a member to the church");
        churchService.addChurchMember(churchId, member);
        return ResponseEntity.ok("Church Member Added");
    }

    @GetMapping("church/{churchId}")
    @ResponseBody
    Church retrieveChurch(@PathVariable("churchId") Long churchId) {
        log.info("Attempting to retrieve a church {}", churchId);
        return churchService.findChurch(churchId);

    }

    @GetMapping("church")
    @ResponseBody
    List<Church> retrieveChurches() {
        log.info("Attempting to retrieve all churches");
        return churchService.findAllChurches();

    }
}
