package capstone.ses.repository;

import capstone.ses.dto.soundeffect.QSoundEffectTagDto;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

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
}
