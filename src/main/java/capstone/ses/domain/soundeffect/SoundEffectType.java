package capstone.ses.domain.soundeffect;

import capstone.ses.domain.system.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

import static jakarta.persistence.FetchType.LAZY;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoundEffectType extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sound_effect_type_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sound_effect_id")
    private SoundEffect soundEffect;

    private String soundEffectTypeName;
    private Integer length;
    private String url;
    private BigDecimal sampleRate;
    private Integer bitDepth;
    private String channels;
}
