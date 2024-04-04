package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoundEffectSoundEffectTagRepository extends JpaRepository<SoundEffectSoundEffectTagRel, Long> {
    List<SoundEffectSoundEffectTagRel> findBySoundEffect(SoundEffect soundEffect);
}
