package capstone.ses.dto.soundeffect;

import jakarta.validation.constraints.Negative;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class SoundEffectCondition {
    private String name;
    private Integer fromLength;
    private Integer toLength;
    private BigDecimal sampleRate;
    private Integer bitDepth;
    private String channels;
    private BigDecimal fromFileSize;
    private BigDecimal toFileSize;
    private String type;
    private List<Long> soundEffectTagIds;

    @Builder
    public SoundEffectCondition(
            String name,
            Integer fromLength,
            Integer toLength,
            BigDecimal sampleRate,
            Integer bitDepth,
            String channels,
            BigDecimal fromFileSize,
            BigDecimal toFileSize,
            String type,
            List<Long> soundEffectTagIds) {
        this.name = name;
        this.fromLength = fromLength;
        this.toLength = toLength;
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
        this.channels = channels;
        this.fromFileSize = fromFileSize;
        this.toFileSize = toFileSize;
        this.type = type;
        this.soundEffectTagIds = soundEffectTagIds;
    }
}
