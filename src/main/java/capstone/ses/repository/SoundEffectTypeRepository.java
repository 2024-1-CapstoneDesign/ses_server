package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoundEffectTypeRepository extends JpaRepository<SoundEffectType, Long> {
    List<SoundEffectType> findBySoundEffect(SoundEffect soundEffect);

}
