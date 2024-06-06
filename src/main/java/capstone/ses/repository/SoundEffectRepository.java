package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SoundEffectRepository extends JpaRepository<SoundEffect, Long>, SoundEffectRepositoryCustom {
    @Query("SELECT soundeffect FROM SoundEffect soundeffect WHERE soundeffect.name = ?1")
    SoundEffect findByName(String name);
}
