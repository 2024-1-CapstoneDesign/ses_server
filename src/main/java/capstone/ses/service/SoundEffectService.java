package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.domain.soundeffect.SoundEffectType;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import capstone.ses.dto.soundeffect.SoundEffectDto;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import capstone.ses.dto.soundeffect.SoundEffectTypeDto;
import capstone.ses.repository.SoundEffectRepository;
import capstone.ses.repository.SoundEffectSoundEffectTagRepository;
import capstone.ses.repository.SoundEffectTagRepository;
import capstone.ses.repository.SoundEffectTypeRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectService {
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectSoundEffectTagRepository soundEffectSoundEffectTagRepository;

    public SoundEffectDto searchSoundEffect(Long soundEffectId) {
        SoundEffect soundEffect = soundEffectRepository.findById(soundEffectId).orElseThrow(() -> new EntityNotFoundException("not exist soundeffect."));

        List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

        for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect)) {
            soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
        }

        List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

        for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffect)) {
            soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
        }

        return SoundEffectDto.builder()
                .soundEffectId(soundEffect.getId())
                .soundEffectName(soundEffect.getName())
                .description(soundEffect.getDescription())
//                .createBy(soundEffect.getCreatedBy())
                .createdAt(soundEffect.getCreatedDate())
                .soundEffectTags(soundEffectTagDtos)
                .soundEffectTypes(soundEffectTypeDtos)
                .build();
    }

    public List<SoundEffectDto> searchSoundEffects(SoundEffectCondition soundEffectCondition) {

        List<SoundEffectDto> soundEffectDtos = new ArrayList<>();

        for (SoundEffect soundEffect : soundEffectRepository.searchSoundEffects(soundEffectCondition)) {

            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

            for (SoundEffectSoundEffectTagRel soundEffectSoundEffectTagRel : soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect)) {
                soundEffectTagDtos.add(SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()));
            }

            List<SoundEffectTypeDto> soundEffectTypeDtos = new ArrayList<>();

            for (SoundEffectType soundEffectType : soundEffectTypeRepository.findBySoundEffect(soundEffect)) {
                soundEffectTypeDtos.add(SoundEffectTypeDto.of(soundEffectType));
            }

            soundEffectDtos.add(SoundEffectDto.builder()
                    .soundEffectId(soundEffect.getId())
                    .soundEffectName(soundEffect.getName())
                    .description(soundEffect.getDescription())
                    .summary(soundEffect.getSummary())
//                    .createBy(soundEffect.get)
                    .createdAt(soundEffect.getCreatedDate())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return soundEffectDtos;
    }
}
