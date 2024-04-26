package capstone.ses.dto.soundeffect;

import capstone.ses.domain.soundeffect.SoundEffectTag;
import com.querydsl.core.annotations.QueryProjection;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SoundEffectTagDto {
    private Long tagId;
    private String tagName;

    public static SoundEffectTagDto of(SoundEffectTag soundEffectTag) {
        return SoundEffectTagDto.builder()
                .tagId(soundEffectTag.getId())
                .tagName(soundEffectTag.getName())
                .build();
    }

    @QueryProjection
    public SoundEffectTagDto(Long tagId, String tagName) {
        this.tagId = tagId;
        this.tagName = tagName;
    }
}
