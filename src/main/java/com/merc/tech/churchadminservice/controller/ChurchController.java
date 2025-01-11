package com.merc.tech.churchadminservice.controller;

import com.merc.tech.churchadminservice.model.Church;
import com.merc.tech.churchadminservice.model.Member;
import com.merc.tech.churchadminservice.service.ChurchService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
public class ChurchController {

    private final ChurchService churchService;

    @PostMapping("church")
    ResponseEntity<Void> createChurch(@Valid @RequestBody Church church) {
        log.info("Attempting to create a church");
        Church newChurch = churchService.saveChurch(church);
        return ResponseEntity.created(URI.create("/church/" + newChurch.getChurchId())).build();
    }

    @PutMapping("church/{churchId}")
    ResponseEntity<Void> updateChurch(@PathVariable("churchId") Long churchId, @Valid @RequestBody Church church) {
        log.info("Attempting to update a church");
        church.setChurchId(churchId);
        Church newChurch = churchService.saveChurch(church);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("church/{churchId}")
    ResponseEntity<Void> deleteChurch(@PathVariable("churchId") Long churchId) {
        log.info("Attempting to delete a church");
        churchService.deleteChurch(churchId);
        return ResponseEntity.noContent().build();
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
