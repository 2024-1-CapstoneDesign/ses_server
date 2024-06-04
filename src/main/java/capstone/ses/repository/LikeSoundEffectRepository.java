package capstone.ses.repository;

import capstone.ses.domain.member.Member;
import capstone.ses.domain.soundeffect.LikeSoundEffect;
import capstone.ses.domain.soundeffect.SoundEffect;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeSoundEffectRepository extends JpaRepository<LikeSoundEffect, Long> {
    LikeSoundEffect findBySoundEffectAndMember(SoundEffect soundEffect, Member member);
}
