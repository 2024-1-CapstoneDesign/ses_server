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
    private Integer fromLength;
    private Integer toLength;
    private BigDecimal sampleRate;
    private Integer bitDepth;
    private String channels;
    private List<Long> soundEffectTagIds;

    @Builder
    public SoundEffectCondition(Integer fromLength, Integer toLength, BigDecimal sampleRate, Integer bitDepth, String channels, List<Long> soundEffectTagIds) {
        this.fromLength = fromLength;
        this.toLength = toLength;
        this.sampleRate = sampleRate;
        this.bitDepth = bitDepth;
        this.channels = channels;
        this.soundEffectTagIds = soundEffectTagIds;
    }
}
