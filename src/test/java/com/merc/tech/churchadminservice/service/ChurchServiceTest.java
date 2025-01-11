package com.merc.tech.churchadminservice.service;

import com.merc.tech.churchadminservice.model.Church;
import com.merc.tech.churchadminservice.model.Member;
import com.merc.tech.churchadminservice.repository.ChurchRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

class ChurchServiceTest {

    @Mock
    private ChurchRepository churchRepository;

    @InjectMocks
    private ChurchService churchService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findChurch_ShouldReturnChurch_WhenChurchExists() {
        Church church = new Church();
        church.setChurchId(1L);
        when(churchRepository.findById(1L)).thenReturn(Optional.of(church));

        Church foundChurch = churchService.findChurch(1L);

        assertEquals(church, foundChurch);
    }

    @Test
    void findChurch_ShouldReturnNull_WhenChurchDoesNotExist() {
        when(churchRepository.findById(1L)).thenReturn(Optional.empty());

        Church foundChurch = churchService.findChurch(1L);

        assertNull(foundChurch);
    }

    @Test
    void findAllChurches_ShouldReturnAllChurches() {
        Church church1 = new Church();
        Church church2 = new Church();
        when(churchRepository.findAll()).thenReturn(List.of(church1, church2));

        List<Church> churches = churchService.findAllChurches();

        assertEquals(2, churches.size());
        assertEquals(church1, churches.get(0));
        assertEquals(church2, churches.get(1));
    }

    @Test
    void saveChurch_ShouldSaveChurch() {
        Church church = new Church();

        churchService.saveChurch(church);

        verify(churchRepository, times(1)).save(church);
    }

    @Test
    void addChurchMember_ShouldAddMemberToChurch_WhenChurchExists() {
        Church church = new Church();
        church.setChurchId(1L);
        Member member = new Member();
        when(churchRepository.findById(1L)).thenReturn(Optional.of(church));

        churchService.addChurchMember(1L, member);

        assertEquals(church, member.getChurch());
        assertEquals(1, church.getMemberList().size());
        assertEquals(member, church.getMemberList().get(0));
        verify(churchRepository, times(1)).save(church);
    }

    @Test
    void addChurchMember_ShouldLogWarning_WhenChurchDoesNotExist() {
        Member member = new Member();
        when(churchRepository.findById(1L)).thenReturn(Optional.empty());

        churchService.addChurchMember(1L, member);

        verify(churchRepository, never()).save(any(Church.class));
    }
}