package capstone.ses.domain.soundeffect;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SoundEffectSoundEffectTagRel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sound_effect_sound_effect_tag_rel_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sound_effect_id")
    private SoundEffect soundEffect;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sound_effect_tag_id")
    private SoundEffectTag soundEffectTag;

    public SoundEffectSoundEffectTagRel(SoundEffect soundEffect, SoundEffectTag soundEffectTag) {
        this.soundEffect = soundEffect;
        this.soundEffectTag = soundEffectTag;
    }
}
