package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.dto.soundeffect.SoundEffectResponseDto;
import capstone.ses.repository.SoundEffectRepository;
import capstone.ses.repository.SoundEffectSoundEffectTagRepository;
import capstone.ses.repository.SoundEffectTagRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class SoundEffectService {

    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectSoundEffectTagRepository soundEffectSoundEffectTagRepository;

    public SoundEffectResponseDto searchSoundEffect(Long soundEffectId) {
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("효과음이 존재하지 않습니다."));

        List<SoundEffectSoundEffectTagRel> bySoundEffect = soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect);
        for ()
        soundEffectTagRepository.findBy

    }
}
