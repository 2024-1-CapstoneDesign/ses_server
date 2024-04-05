package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectTag;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SoundEffectTagRepository extends JpaRepository<SoundEffectTag, Long> {
}
