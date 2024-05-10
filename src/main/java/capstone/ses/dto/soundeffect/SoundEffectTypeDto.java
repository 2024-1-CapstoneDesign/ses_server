package capstone.ses.dto.soundeffect;

import capstone.ses.domain.soundeffect.SoundEffectType;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
public class SoundEffectTypeDto {
    private String soundEffectTypeName;
    private Integer length;
    private String url;
    private BigDecimal sampleRate;
    private Integer bitDepth;
    private String channels;
    private BigDecimal fileSize;

    public static SoundEffectTypeDto of(SoundEffectType soundEffectType) {
        return SoundEffectTypeDto.builder()
                .url(soundEffectType.getUrl())
                .soundEffectTypeName(soundEffectType.getSoundEffectTypeName())
                .length(soundEffectType.getLength())
                .sampleRate(soundEffectType.getSampleRate())
                .bitDepth(soundEffectType.getBitDepth())
                .channels(soundEffectType.getChannels())
                .fileSize(soundEffectType.getFileSize())
                .build();
    }
}
