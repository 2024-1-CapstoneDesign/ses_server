package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.dto.soundeffect.SoundEffectCondition;

import java.util.List;

public interface SoundEffectRepositoryCustom {
    List<SoundEffect> searchSoundEffects(SoundEffectCondition soundEffectCondition);
    List<SoundEffect> searchRelativeSoundEffects(List<Long> soundEffectTagIds, Long soundEffectId);
}
