package capstone.ses.domain.soundeffect;

import capstone.ses.domain.member.Member;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class LikeSoundEffect {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "like_sound_effect_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "sound_effect_id")
    private SoundEffect soundEffect;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private Boolean isActive = true;

    public LikeSoundEffect(SoundEffect soundEffect, Member member) {
        this.soundEffect = soundEffect;
        this.member = member;
    }

    public void updateActive() {
        this.isActive = true;
    }

    public  void updateInactive() {
        this.isActive = false;
    }
}
