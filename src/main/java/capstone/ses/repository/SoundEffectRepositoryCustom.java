package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SoundEffectRepositoryCustom {
    Page<SoundEffect> searchSoundEffects(SoundEffectCondition soundEffectCondition, Pageable pageable);
    List<SoundEffect> searchRelativeSoundEffects(List<Long> soundEffectTagIds, Long soundEffectId);
    List<SoundEffect> searchLikedSoundEffects(Long memberId);
    Boolean checkLikedSoundEffecet(SoundEffect bysoundEffect, Long memberId);
}
