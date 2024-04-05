package capstone.ses.dto.soundeffect;

import capstone.ses.domain.soundeffect.SoundEffectType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class SoundEffectTypeDto {
    private String url;
    private String soundEffectTypeName;
    private Integer length;

    public static SoundEffectTypeDto of(SoundEffectType soundEffectType) {
        return SoundEffectTypeDto.builder()
                .url(soundEffectType.getUrl())
                .soundEffectTypeName(soundEffectType.getSoundEffectTypeName())
                .length(soundEffectType.getLength())
                .build();
    }
}
