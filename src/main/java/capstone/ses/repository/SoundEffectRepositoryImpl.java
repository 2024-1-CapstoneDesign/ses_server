package capstone.ses.repository;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.CaseBuilder;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;

import static capstone.ses.domain.soundeffect.QLikeSoundEffect.likeSoundEffect;
import static capstone.ses.domain.soundeffect.QSoundEffect.soundEffect;
import static capstone.ses.domain.soundeffect.QSoundEffectSoundEffectTagRel.soundEffectSoundEffectTagRel;
import static capstone.ses.domain.soundeffect.QSoundEffectTag.soundEffectTag;
import static capstone.ses.domain.soundeffect.QSoundEffectType.soundEffectType;
import static org.springframework.util.StringUtils.hasText;

@Repository
@RequiredArgsConstructor
public class SoundEffectRepositoryImpl implements SoundEffectRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    @Override
    public Page<SoundEffect> searchSoundEffects(SoundEffectCondition soundEffectCondition, Pageable pageable) {
        List<SoundEffect> soundEffects = queryFactory
                .selectFrom(soundEffect).distinct()
                .innerJoin(soundEffectType).on(soundEffect.eq(soundEffectType.soundEffect))
                .innerJoin(soundEffectSoundEffectTagRel).on(soundEffect.eq(soundEffectSoundEffectTagRel.soundEffect))
                .innerJoin(soundEffectTag).on(soundEffectSoundEffectTagRel.soundEffectTag.eq(soundEffectTag))
                .where(
                        soundEffect.id.isNotNull()
                                .and(nameContains(soundEffectCondition.getName()))
                                .and(fromLength(soundEffectCondition.getFromLength()))
                                .and(toLength(soundEffectCondition.getToLength()))
                                .and(sampleRateEq(soundEffectCondition.getSampleRate()))
                                .and(bitDepthEq(soundEffectCondition.getBitDepth()))
                                .and(channelsEq(soundEffectCondition.getChannels()))
                                .and(fromFileSize(soundEffectCondition.getFromFileSize()))
                                .and(toFileSize(soundEffectCondition.getToFileSize()))
                                .and(typeEq(soundEffectCondition.getType()))
                                .and(soundEffectTagEq(soundEffectCondition.getSoundEffectTagIds()))
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long totalCount = queryFactory
                .selectFrom(soundEffect).distinct()
                .innerJoin(soundEffectType).on(soundEffect.eq(soundEffectType.soundEffect))
                .leftJoin(soundEffectSoundEffectTagRel).on(soundEffect.eq(soundEffectSoundEffectTagRel.soundEffect))
                .leftJoin(soundEffectTag).on(soundEffectSoundEffectTagRel.soundEffectTag.eq(soundEffectTag))
                .where(
                        soundEffect.id.isNotNull()
                                .and(nameContains(soundEffectCondition.getName()))
                                .and(fromLength(soundEffectCondition.getFromLength()))
                                .and(toLength(soundEffectCondition.getToLength()))
                                .and(sampleRateEq(soundEffectCondition.getSampleRate()))
                                .and(bitDepthEq(soundEffectCondition.getBitDepth()))
                                .and(channelsEq(soundEffectCondition.getChannels()))
                                .and(fromFileSize(soundEffectCondition.getFromFileSize()))
                                .and(toFileSize(soundEffectCondition.getToFileSize()))
                                .and(typeEq(soundEffectCondition.getType()))
                                .and(soundEffectTagEq(soundEffectCondition.getSoundEffectTagIds()))
                )
                .fetchCount();

        return new PageImpl<>(soundEffects, pageable, totalCount);
    }

    @Override
    public List<SoundEffect> searchRelativeSoundEffects(List<Long> soundEffectTagIds, Long soundEffectId) {
        return queryFactory
                .select(soundEffect)
                .from(soundEffect)
                .leftJoin(soundEffectSoundEffectTagRel).on(soundEffect.eq(soundEffectSoundEffectTagRel.soundEffect))
                .leftJoin(soundEffectTag).on(soundEffectSoundEffectTagRel.soundEffectTag.eq(soundEffectTag))
                .where(soundEffectTag.id.in(soundEffectTagIds)
                        .and(soundEffect.id.ne(soundEffectId)))
                .groupBy(soundEffect.id)
                .orderBy(soundEffectTag.id.count().desc())
                .limit(3)
                .fetch();
    }

    @Override
    public Boolean checkLikedSoundEffecet(SoundEffect bysoundEffect, Long memberId) {
        if (memberId == null) {
            return false;
        } else {
            return queryFactory
                    .select(
                            new CaseBuilder()
                                    .when(likeSoundEffect.isActive.isNull())
                                    .then(false)
                                    .when(likeSoundEffect.isActive.eq(false))
                                    .then(false)
                                    .when(likeSoundEffect.isActive.eq(true))
                                    .then(true)
                                    .otherwise(false)
                    )
                    .from(soundEffect)
                    .leftJoin(likeSoundEffect).on(soundEffect.eq(likeSoundEffect.soundEffect).and(likeSoundEffect.member.id.eq(memberId)))
                    .where(soundEffect.eq(bysoundEffect))
                    .fetchOne();
        }

    }

    @Override
    public List<SoundEffect> searchLikedSoundEffects(Long memberId) {
        return queryFactory
                .selectFrom(soundEffect)
                .innerJoin(likeSoundEffect).on(likeSoundEffect.soundEffect.eq(soundEffect))
                .where(likeSoundEffect.member.id.eq(memberId)
                        .and(likeSoundEffect.isActive.eq(true))
                )
                .fetch();
    }

    private BooleanExpression nameContains(String soundEffecName) {
        return hasText(soundEffecName) ? soundEffect.name.contains(soundEffecName) : null;
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

    private BooleanExpression fromFileSize(BigDecimal fromFileSize) {
        return fromFileSize != null ? soundEffectType.fileSize.goe(fromFileSize) : null;
    }

    private BooleanExpression toFileSize(BigDecimal toFileSize) {
        return toFileSize != null ? soundEffectType.fileSize.loe(toFileSize) : null;
    }

    private BooleanExpression typeEq(String type) {
        return hasText(type) ? soundEffectType.soundEffectTypeName.eq(type) : null;
    }

    private BooleanExpression soundEffectTagEq(List<Long> soundEffectTagIds) {
        return soundEffectTagIds != null ?
                soundEffect.id.in(JPAExpressions.select(soundEffect.id)
                        .from(soundEffect)
                        .leftJoin(soundEffectSoundEffectTagRel).on(soundEffect.eq(soundEffectSoundEffectTagRel.soundEffect))
                        .innerJoin(soundEffectTag).on(soundEffectSoundEffectTagRel.soundEffectTag.eq(soundEffectTag))
                        .where(soundEffectTag.id.in(soundEffectTagIds))
                        .groupBy(soundEffect.id)
                        .having(soundEffect.id.count().intValue().eq(soundEffectTagIds.size())))
                : null;
    }


}
