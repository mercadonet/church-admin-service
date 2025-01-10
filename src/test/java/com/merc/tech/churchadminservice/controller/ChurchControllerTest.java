package com.merc.tech.churchadminservice.controller;

import com.merc.tech.churchadminservice.model.Church;
import com.merc.tech.churchadminservice.model.Member;
import com.merc.tech.churchadminservice.service.ChurchService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class ChurchControllerTest {

    @Mock
    private ChurchService churchService;

    @InjectMocks
    private ChurchController churchController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createChurch_ShouldReturnOkResponse() {
        Church church = new Church();
        ResponseEntity<String> response = churchController.createChurch(church);

        verify(churchService, times(1)).createChurch(church);
        assertEquals("Church created", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void addChurchMember_ShouldReturnOkResponse() {
        Member member = new Member();
        ResponseEntity<String> response = churchController.addChurchMember(1L, member);

        verify(churchService, times(1)).addChurchMember(1L, member);
        assertEquals("Church Member Added", response.getBody());
        assertEquals(200, response.getStatusCodeValue());
    }

    @Test
    void retrieveChurch_ShouldReturnChurch() {
        Church church = new Church();
        when(churchService.findChurch(1L)).thenReturn(church);

        Church response = churchController.retrieveChurch(1L);

        verify(churchService, times(1)).findChurch(1L);
        assertEquals(church, response);
    }

    @Test
    void retrieveChurches_ShouldReturnAllChurches() {
        Church church1 = new Church();
        Church church2 = new Church();
        when(churchService.findAllChurches()).thenReturn(List.of(church1, church2));

        List<Church> response = churchController.retrieveChurches();

        verify(churchService, times(1)).findAllChurches();
        assertEquals(2, response.size());
        assertEquals(church1, response.get(0));
        assertEquals(church2, response.get(1));
    }
}