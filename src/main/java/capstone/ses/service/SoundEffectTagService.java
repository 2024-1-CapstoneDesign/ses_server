package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffectTag;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import capstone.ses.repository.SoundEffectTagQueryRepository;
import capstone.ses.repository.SoundEffectTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectTagService {
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectTagQueryRepository soundEffectTagQueryRepository;

    public List<SoundEffectTagDto> getAll() {
        return soundEffectTagQueryRepository.findAll();
    }
}
