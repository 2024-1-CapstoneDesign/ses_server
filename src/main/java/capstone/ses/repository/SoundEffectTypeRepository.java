package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffectType;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundEffectTypeRepository extends JpaRepository<SoundEffectType, Long> {
}
