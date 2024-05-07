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
import java.util.stream.Collectors;

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

        //1. 조건에 맞춰 soundEffect 가져오기
        for (SoundEffect soundEffect : soundEffectRepository.searchSoundEffects(soundEffectCondition)) {

            //2. tag 가져오기
            List<SoundEffectSoundEffectTagRel> bySoundEffect = soundEffectSoundEffectTagRepository.findBySoundEffect(soundEffect);

            //2-1. 태그 IDS 추출
            List<Long> soundEffectTagIds = bySoundEffect.stream()
                    .map(soundEffectSoundEffectTagRel -> soundEffectSoundEffectTagRel.getSoundEffectTag().getId())
                    .collect(Collectors.toList());

            List<SoundEffectTagDto> soundEffectTagDtos = new ArrayList<>();

            //2-2. 태그 ids 필터링
            if (soundEffectTagIds.containsAll(soundEffectCondition.getSoundEffectTagIds() == null ? new ArrayList<>() : soundEffectCondition.getSoundEffectTagIds())) {
                soundEffectTagDtos = bySoundEffect.stream()
                        .map(soundEffectSoundEffectTagRel -> SoundEffectTagDto.of(soundEffectSoundEffectTagRel.getSoundEffectTag()))
                        .collect(Collectors.toList());
            } else {
                continue;
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
                    .createBy(soundEffect.getDescription())
                    .soundEffectTags(soundEffectTagDtos)
                    .soundEffectTypes(soundEffectTypeDtos)
                    .build());
        }

        return soundEffectDtos;
    }
}
