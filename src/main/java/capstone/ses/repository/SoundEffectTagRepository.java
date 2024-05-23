package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffectTag;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SoundEffectTagRepository extends JpaRepository<SoundEffectTag, Long> {
    SoundEffectTag findByName(String name);
}
