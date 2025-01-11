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
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
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
        church.setChurchId(1L);
        when(churchService.saveChurch(church)).thenReturn(church);

        ResponseEntity<Void> response = churchController.createChurch(church);

        verify(churchService, times(1)).saveChurch(church);
        assertTrue(response.getHeaders().containsKey("Location"));
        assertEquals("/church/1", Objects.requireNonNull(response.getHeaders().get("Location")).get(0));
        assertTrue(response.getStatusCode().is2xxSuccessful());
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

    @Test
    void updateChurch_ShouldReturnOkResponse() {
        Church church = new Church();
        church.setChurchId(1L);
        when(churchService.saveChurch(church)).thenReturn(church);

        ResponseEntity<Void> response = churchController.updateChurch(1L, church);

        verify(churchService, times(1)).saveChurch(church);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

    @Test
    void deleteChurch_ShouldReturnOkResponse() {
        ResponseEntity<Void> response = churchController.deleteChurch(1L);

        verify(churchService, times(1)).deleteChurch(1L);
        assertTrue(response.getStatusCode().is2xxSuccessful());
    }

}