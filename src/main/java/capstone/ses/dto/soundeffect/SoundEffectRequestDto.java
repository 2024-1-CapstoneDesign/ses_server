package capstone.ses.dto.soundeffect;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SoundEffectRequestDto {
    private Integer length;
    private BigDecimal sampleRate;
    private Integer bitDepth;
    private String channels;
    private Long soundEffectTagId;
}
