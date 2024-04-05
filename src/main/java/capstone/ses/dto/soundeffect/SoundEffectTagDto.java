package capstone.ses.dto.soundeffect;

import capstone.ses.domain.soundeffect.SoundEffectTag;
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
}
