package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.domain.soundeffect.SoundEffectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundEffectSoundEffectTagRelRepository extends JpaRepository<SoundEffectSoundEffectTagRel, Long> {
    SoundEffectSoundEffectTagRel findBySoundEffectAndSoundEffectTag(SoundEffect soundEffect, SoundEffectTag soundEffectTag);
}
