package capstone.ses.dto.soundeffect;

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
}
