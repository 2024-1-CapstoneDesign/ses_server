package capstone.ses.dto.soundeffect;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class SoundEffectForm {
    @NotBlank
    private String name;
    @NotBlank
    private String description;
    @NotBlank
    private String summary;
    @NotBlank
    private String soundEffectTypeName;
    @NotNull
    private Integer length;
    @NotBlank
    private String url;
    @NotNull
    private BigDecimal sampleRate;
    @NotNull
    private Integer bitDepth;
    @NotBlank
    private String channels;
    @NotNull
    private BigDecimal fileSize;
}
