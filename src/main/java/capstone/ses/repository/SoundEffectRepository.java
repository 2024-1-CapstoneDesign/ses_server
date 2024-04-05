package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundEffectRepository extends JpaRepository<SoundEffect, Long> {
}
