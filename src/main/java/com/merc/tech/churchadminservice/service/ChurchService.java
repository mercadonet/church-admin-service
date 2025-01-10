package com.merc.tech.churchadminservice.service;

import com.merc.tech.churchadminservice.model.Church;
import com.merc.tech.churchadminservice.model.Member;
import com.merc.tech.churchadminservice.repository.ChurchRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChurchService {

    private final ChurchRepository churchRepository;

    public Church findChurch(Long churchId) {
        return churchRepository.findById(churchId).orElse(null);
    }

    public List<Church> findAllChurches() {
        return churchRepository.findAll();
    }

    public void createChurch(Church church) {
        churchRepository.save(church);
    }

    public void addChurchMember(Long churchId, Member member) {

        churchRepository.findById(churchId).ifPresentOrElse(church -> {
            member.setChurch(church);
            church.getMemberList().add(member);
            churchRepository.save(church);
        }, () -> log.warn("Church not found, failed to add member"));

    }
}
