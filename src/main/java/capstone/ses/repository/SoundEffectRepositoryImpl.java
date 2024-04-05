package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static capstone.ses.domain.soundeffect.QSoundEffect.soundEffect;
import static capstone.ses.domain.soundeffect.QSoundEffectType.soundEffectType;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class SoundEffectRepositoryImpl implements SoundEffectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public List<SoundEffect> searchSoundEffects(SoundEffectCondition soundEffectCondition) {
        return queryFactory
                .selectFrom(soundEffect)
                .innerJoin(soundEffectType).on(soundEffect.eq(soundEffectType.soundEffect))
                .where(
                        soundEffect.id.isNotNull()
                                .and(fromLength(soundEffectCondition.getFromLength()))
                                .and(toLength(soundEffectCondition.getToLength()))
                                .and(sampleRateEq(soundEffectCondition.getSampleRate()))
                                .and(bitDepthEq(soundEffectCondition.getBitDepth()))
                                .and(channelsEq(soundEffectCondition.getChannels()))

                )
                .fetch();
    }

    private BooleanExpression fromLength(Integer fromLength) {
        return fromLength != null ? soundEffectType.length.goe(fromLength) : null;
    }

    private BooleanExpression toLength(Integer toLength) {
        return toLength != null ? soundEffectType.length.loe(toLength) : null;
    }

    private BooleanExpression sampleRateEq(BigDecimal sampleRate) {
        return sampleRate != null ? soundEffectType.sampleRate.eq(sampleRate) : null;
    }

    private BooleanExpression bitDepthEq(Integer bitDepth) {
        return bitDepth != null ? soundEffectType.bitDepth.eq(bitDepth) : null;
    }

    private BooleanExpression channelsEq(String channels) {
        return hasText(channels) ? (soundEffectType.channels.eq(channels)) : null;
    }
}
