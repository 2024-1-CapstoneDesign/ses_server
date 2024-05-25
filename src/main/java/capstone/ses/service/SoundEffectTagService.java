package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.domain.soundeffect.SoundEffectTag;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import capstone.ses.dto.soundeffect.TagRelDto;
import capstone.ses.repository.SoundEffectRepository;
import capstone.ses.repository.SoundEffectSoundEffectTagRelRepository;
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
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectSoundEffectTagRelRepository soundEffectSoundEffectTagRelRepository;

    public List<SoundEffectTagDto> getAll() {
        return soundEffectTagQueryRepository.findAll();
    }

    @Transactional
    public void createSoundEffectTag(String tags) {
        String[] split = tags.split("\\|");
        for (String tag : split) {
            System.out.println("tag:" + tag);
            if (soundEffectTagRepository.findByName(tag) == null && !tag.equals("|")) {
                soundEffectTagRepository.save(new SoundEffectTag(tag));
            }
        }
    }
    
    @Transactional
    public void createSoundEffectTagRel(List<TagRelDto> tagRelDtos) {
        for (TagRelDto tagRelDto : tagRelDtos) {
            SoundEffect soundEffect = soundEffectRepository.findById(tagRelDto.getId()).orElseThrow(() -> new IllegalArgumentException("Tag rel not found"));
            String[] split = tagRelDto.getTags().split("\\|");
            for (String tag : split) {
                if (!tag.equals("|") && !tag.isEmpty()) {
                    soundEffectSoundEffectTagRelRepository.save(new SoundEffectSoundEffectTagRel(soundEffect, soundEffectTagRepository.findByName(tag)));
                }
            }
        }
    }
}

