package capstone.ses.repository;

import capstone.ses.domain.soundeffect.QSoundEffect;
import capstone.ses.domain.soundeffect.QSoundEffectSoundEffectTagRel;
import capstone.ses.dto.soundeffect.QSoundEffectTagDto;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static capstone.ses.domain.soundeffect.QSoundEffect.soundEffect;
import static capstone.ses.domain.soundeffect.QSoundEffectSoundEffectTagRel.soundEffectSoundEffectTagRel;
import static capstone.ses.domain.soundeffect.QSoundEffectTag.soundEffectTag;

@Repository
@RequiredArgsConstructor
public class SoundEffectTagQueryRepository {
    private final JPAQueryFactory queryFactory;

    public List<SoundEffectTagDto> findAll() {
        return queryFactory
                .select(new QSoundEffectTagDto(
                        soundEffectTag.id,
                        soundEffectTag.name
                ))
                .from(soundEffectTag)
                .fetch();
    }

    public List<Long> findAllBySoundEffectId(long soundEffectId) {
        return queryFactory
                .select(soundEffectTag.id)
                .from(soundEffectTag)
                .innerJoin(soundEffectSoundEffectTagRel).on(soundEffectTag.eq(soundEffectSoundEffectTagRel.soundEffectTag))
                .where(soundEffect.id.eq(soundEffectId))
                .fetch();
    }
}
