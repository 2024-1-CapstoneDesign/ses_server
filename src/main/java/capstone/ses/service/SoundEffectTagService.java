package capstone.ses.service;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.domain.soundeffect.SoundEffectSoundEffectTagRel;
import capstone.ses.domain.soundeffect.SoundEffectTag;
import capstone.ses.dto.soundeffect.SoundEffectTagDto;
import capstone.ses.dto.soundeffect.TagRelDto;
import capstone.ses.repository.SoundEffectRepository;
import capstone.ses.repository.SoundEffectSoundEffectTagRelRepository;
import capstone.ses.repository.SoundEffectTagQueryRepository;
import capstone.ses.repository.SoundEffectTagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SoundEffectTagService {
    private final SoundEffectTagRepository soundEffectTagRepository;
    private final SoundEffectTagQueryRepository soundEffectTagQueryRepository;
    private final SoundEffectRepository soundEffectRepository;
    private final SoundEffectSoundEffectTagRelRepository soundEffectSoundEffectTagRelRepository;

    public List<SoundEffectTagDto> getAll() {
        return soundEffectTagQueryRepository.findAll();
    }

    @Transactional
    public void createSoundEffectTag(String tags) {
        tags = """
                scream|horror|suspense|fear|thriller
                best trickshot|trickshot|reaction|excitement|celebration
                food|eating|funny|cartoonish|Homer Simpson
                windows|xp|startup|positive|upbeat
                boxing|bell|fight|start|end
                bamboo|slap|hit|thwack|bang
                fail|error|blunder|defeat|disappointment
                lets go|hype|excited|energetic
                fart|quick|poop|awkward|embarrassing
                |kitchen|cooking|pots and pans|blender|restaurant
                |suspense|creepy|horror|eerie|dark
                |mouse click|button click|computer click|digital click|interface click
                |clean|cleaning|glass|window|surface
                |discord|notification|alert|attention|metal
                |emergency|alert|warning|urgent|evacuate
                |high-tech|digital|electronic|sci-fi|futuristic
                |scream|horror|suspense|wow|surprised|joyful|yoo|angry|frustrated
                |squeaky toy|children toy|whining|funny|playful
                |toy|squeeze|fun|lighthearted|happy
                |Wombo Combo|Happy Feet|MLG|Victory|Joy
                |coin|jingle|reward|positive|triumph
                |cartoon|deflate|whoosh|squish|funny
                |positive|success|achievement|satisfaction|reward
                |buzzer|deny|wrong answer
                |honk|car|animal|cute|funny
                |goofy|funny|running|cartoon|footsteps
                |gasp|breath|exhale|nervous|tension
                |metal|hit|clash|impact|clang
                |throw|trash|drop|discard|waste
                |underwater|submerged|watery|peaceful|tranquil
                |suspense|ominous|tension|drums|strings
                |hostage|crime|danger|tension|threat
                |action|game|shooter|achievement|victory
                |hit|impact|dull|combat|action|wow|surprise|astonishment|excitement|shock
        heroic|entrance|piano|power|drama
        buzzer|wrong answer|mistake|failure|quiz
        gibberish|fantasy|alien|eerie|mysterious
        futuristic|digital|electronic|humming|technological
        child|crying|sadness|despair|vulnerability
        censor|beep|alarm|warning|harsh
        squeaky toy|plastic|bouncy|playful|cute
        human|frustration|annoyance|anger|irritation
        Motivational voice|Positive emotion|Human voice|Adventure|Enthusiastic
        sword|clash|metal|swing|battle
        sike|surprise|trick|unexpected
        suspense|eerie|chilling|tension|fear
        clock|ticking|time passing|countdown|urgency
        fast|energetic|lively|humorous|silly
        heartbeat|pulse|tension|excitement|anxiety
        Surprise|Shock|Blunder|Humor|Comic
        scream|fear|pain|horror|tension
        sad|violin|sadness|heartbreak|sympathy
        scream|horror|thriller|fear|pain
        record scratch|dj|mix|surprise|tension
        busy|phone|dialing|engaged|call
        sad|melancholy|loss|sorrow|disappointment
        eating|heavy|nom|food|funny
        running|urgent|alert|footsteps|escape|fatality|powerful|impactful|dramatic|doom
        surprise|shock|scream|meme|comedic
        jump|mario|positive|bouncy|action
        mouse click|computer interface|feedback|visual cue|user experience|online education|software demo|technical tutorial
        drumroll|dramatic|action|thrilling|suspenseful
        horror|suspense|tense|ominous|threatening
        toast|pop|burnt
        beatbox|hiphop|dance|rhythm|upbeat
        laugh|funny|joy|positive|happy
        horror|suspense|sinister|evil|supernatural
        announcer|speech|emphasis|clarity|attention
        shutdown|system down|power off|electronic|machine
        click|button|electronic|interface|device
        computer game|card game|lose|sad|disappointed
        excited|cheerful|cowboy|yeehaw|positive
        obi-wan|star wars|hello there|funny|comic
        battle|explosion|gunfire|crowd|war
        knock|door|arrival|suspense|anticipation|emergency|warning|danger|alarm|alert
        scream|horror|thriller|suspense|fear|eerie|ominous|tension|suspense|anticipation
        cartoon|spin|rotating|fast-paced|whirring
        goodbye|have|great|time
        cluck|funny|humorous|comical|laughter
        balloon|rubbing|slight breeze|eerie|unsettling
        laugh|run|joy|excitement|energy
        noob|failure|pain|disappointment|frustration
        space ship|rocket launch|thrusters|propulsion|epic|majestic
        suspense|tension|anxiety|hurried|breathless
        vent|ventilation|passage|metal|creaking
        shock|gasp|crowd|surprise|effect
        rewind|tape|record|nostalgia|retro
        goofy|impact|cartoon|comedic|slapstick
        clapping|applause|positive|encouraging|approval
        delivery|pizza|funny|excited|surprise
        whoosh|transition|fast|sharp|movement
        water|drop|rain|fresh|nature
        whoosh|transition|movement|speed|action
        rewind|tape|motor|suspense|nostalgia
        alarm|alert|danger|detection|urgent
        metal|pan|hit|action|comedy
        high pitched|metallic|short|sharp|eerie|shock|horror|movie|film|jump scare|tense|supernatural|threatening|unexpected
        flashbang|metal|clang|tension|action
        burger|bite|juicy|meat|food
        camera|shutter|flash|focus|lens
        cartoon|boing|funny|surprise|sound effect
        discord|server join|notification|message arrival|welcome
        hype|cheer|excitement|victory|achievement
        murder|kill|betrayal|suspense|horror
        meme|Uganda Knuckles|funny|british|accent
        drum|punchline|comedy|humour|impact
        metal|disturb|tense|horror|violent
        machine|bell|funny|meme|energy
        dolphin|ocean|marine|communication|sonar
        bruh|surprise|disappointment|funny|reaction
        kitchen|cooking|cleaning|utensils|dinnerware
        scream|horror|fear|pain|surprise
        fast|climbing|suspense|speed|adrenaline
        water|drip|calm|serene|eerie
        fighting|punch|kick|hit|struggle
        surprise|impact|realization|tension
        punch|slap|hit|fight|impact
        sad|melancholy|grief|piano|emotional
        notification|twitter|social media|message
        cute|female character|surprise|joy|funny
        cat|meow|animal|attention|demand
        epic|dramatic|fanfare|grand|majestic
        dial tone|phone engaged|busy signal|call waiting|ringing
        hey|warning|surprise|human voice|action|thriller
        fight|battle|action|weapon clash|hand to hand combat
        death|scream|game over|fail|loss
        notification|alert|email|system|generic
        random noises|machine noises|funny moments|confusion|comedy
        running|cartoon|footsteps|fast|goofy
        chinese|rap|rhythmic|energetic|exciting
        fart|flatulence|funny|comedy|silly
        gunshot|murder|mystery|action|violence
        among us|imposter reveal|tense|suspenseful|suspicious
        horror|monster|creepy|eerie|suspenseful
        funny|laugh|comical|lighthearted|humourous
        suspense|sinister|tension|ominous|fear
        minion|what
        alarm|danger|urgent|warning|alert
        bike horn|siren|sharp|urgent|warning
        cheer|excitement|triumph|victory|success
        chill|playful|funny|positive|cheerful
        bone|crack|snap|painful|violent
        explosion|blast|action|tension|impact
        laugh|comedy|cheerful|humor|funny
        anime|oink|pig|cute|funny
        panting|rapid breath|running|exhaustion|stress
        balloon|boy|hello|cute|friendly
        explosion|loud|abrupt|action|dramatic
        cheer|excitement|celebration|support|crowd
        good|correct|success|answer|quiz
        swoosh|sharp|movement|speed|dynamic
        spit|disgust|tension|awkward|liquid
        surprise|shock|horror|excitement|expletive
        suspenseful|tense|ominous|eerie|foreboding
        cartoon|dizzy|bird|funny|light
        sad|piano|melancholy|mournful|loss
        horse|gallop|run|power|speed
        whistle|sound|effect|sharp|alert
        rage|frustration|anger
        intro|welcome|exciting|playful|energetic
        action|urgent|hurry|tension|emergency
        war|battle|action|intense|excitement
        heart|beat|サスペンス
        funny|positive|playful|warm|cute
        enemy spotted|warning|tension|suspense|alerting
        joke|funny|relief|tension
        typewriter|clack|vintage|office|writing
        audience|laugh|funny|comedy|cheerful
        surprise|playful|punchline|stinger|short
        Laughing|Bright|Comic|Contagious|Funny
        FBI|open up|action|suspense|surprise
        Funny|Laughter|Joy|Comedy|Pleasant
        drumroll|drum|percussion|tension|anticipation
        laugh|joy|comedy|happiness|positive
        comedy|fail|surprise|whistle|fail sound
        Water|Bubbles|Splashing|Trickling|Serene
        epic|dramatic|suspense|choir|orchestra
        yay|cheering|celebrating|excitement|happy
        suspense|tension|anticipation|mystery|horror
        cartoon|flying|whoosh|fast|movement
        punch|hit|impact|sharp|fight
        kick|bass|impact|explosion|action
        cartoon|pop|cheerful|light-hearted|visual effect
        connection|lost|offline|disconnected|static
        water|droplet|rain|dew|nature
        swag|cool|confident|ghetto|gangsta
        dog|bark
        runner|exhaustion|breath|movement|intensity
        creepy|suspense|unease|tension|ominous
        Aww|cute|affection|lovely|heartwarming
        Deez Nuts|funny|humorous|prank|joke
        fall|stairs|crash|impact|panic
        Shush|disappointment|surprise|interrupt|stress
                |water|pour|flow
                |explosion|crash|fall|heavy|impact
        cartoon|xylophone|tiptoe|cheerful|funny
        cartoon|dash|funny|goofy|quick
        engine|acceleration|speed|action|race
        wow|exclamation|surprise|upbeat|positive
        lightsaber|clash|duel|tense|fierce
        pew|metal|impact|clatter|collision
        explosion|firework|disaster
        uptempo|electronic|energetic|excitement|joy
        wait|minute|urgent|attention|action
        suspense|tension|uneasy|unrest|thriller
        disappointment|frustration|heavy object falling|slow motion|defeat
        scream|painful|anguish|despair|loud
        Krispy Kreme guy backflips into sign|backflip|sign|comedy|funny
        virus alert|cyber attack|threatening|urgent| ominous
        deny|fail|negative feedback|warning|disapproval
        chomp|food|mouth|eating|restaurant
        sword|draw|metal|action|weapon
        munch|crunch|swallow|satisfying|delicious
        triumphant|victory|success|achievement|celebration
        fire|burning|crackling|heat|tension
        cartoon|clock|ticking|comedy|urgent
        surprise|alert|attention|news flash|emergency
        suspense|action|drum roll|piano|anticipation
        funny|laugh
        goofy|comedy|sneaking|curious|mischievous
        scream|funny|shock|comedy|laughter
        speech|talk|surprise|huh|curious
        fantasy|magical|supernatural|sparkling|mysterious
        stair fall|falling down|comedy|fail|error
        scream|horror|thriller|suspense|fear
        turntable|scratch|hiss|vinyl|break
                horror|tension|eerie|suspense|jumpscare
        mistake|error|humorous|alert|funny
        water|splash|fishing|cast|reel
        fall|scream|funny|impact|fail
        celebration|cheering|joy|triumph|victory
        whoosh|fast|transition|sharp
        bright|upbeat|musical|popping|achievement
        fail|error|funny|surprise|chaos
        awesome|energetic|intense|excitement|surprise
        supernatural|eerie|mysterious|sci-fi|synth
        crow|caw|ominous|mysterious|animal
        surprise|excitement|humor|shock|cognition
        surprise|shock|dramatic|humor|meme
        troll|song|meme
        follow|damn|train|CJ|GTA
        Hurt|Death|Impact|Battle|Pain
        spring|coil|bounce|funny|lighthearted
        bass boost|loud|rumble|immersive|pulsating
        air horn|honk|emergency|sports|celebration
        applause|cheering|positive|encouraging|happy
        cartoon|running|dash|fast|funny
        restaurant|order|busy|crowded|background noise
        movement|swoosh|speed|action|energy
        scream|horror|pain|fear|distressing
        mouse click|button press|interaction|interface|navigation
        suspense|tension|ominous|eerie|unrest
        damn|boi|thicc|surprised|funny
        fail|error|disappointment|frustration|impactful
        squirt|liquid|water|spray
        BRAP|car|engine|racing|acceleration|power
        ding|metal|short|high pitch|alert
        scratch|noise|record|vinyl|hiss
        funny|low-quality|digital|meme|unexpected
        cartoon|rubber|stretch|squeaky|bounce
        click|unlock|success|notification|achievement
        fog horn|deep|eerie|suspenseful|ominous
        waterphone|suspense|drama|eerie|ominous
        jump|comic|cheerful|exciting|energetic
        chinese|percussion|traditional|oriental|action
        howl|dramatic|eerie|scary|animal
        greeting|friendly|welcoming|familiar|positive
        metal|ding|notification|clear|alert
        alarm|danger
        food|eating|chomp|chew|munch
        Wolf|Howling|Horror|Scary|Thriller
        cartoon|goofy|dash|joy|playful
        footstep|run|determination|speed|pursuit
        balloon|pop|surprise|tension|celebration
        mlg|illuminati|mysterious|conspiracy
        tension|anxiety|horror|uneasiness|suspense
        suspense|anticipation|plot twist|dramatic|climax
        skrra|funny|comedic|lighthearted|playful
        confused|scream|uncertainty|panic|shock
        fail|comic|mistake|funny|error
        Holy shit|Joseph Joestar|Surprise|Dramatic|Intense
        alert|danger|emergency|notify|warning
        eating|mukbang|restaurant|food|chew
        triumph|achievement|victory|satisfaction|joy
        trill|fast|sharp|short|impact
        clock|ticking|time|passage|regular
        dizzy|cartoon|confused|whirling|fast
        tense|suspenseful|uneasy|unsettling|ominous
        lightsaber|swish|humming|weapon|futuristic
        punch|fight|action|impact|fist
        pop|snap|crackle|random
        solving mystery|suspense|pick something|short and sharp|discovery
        dolphin|laugh|joy|fun|happy
        game over|failure|dramatic ending|unexpected death|defeat
        rush|zoom|dash|energy|excitement
        camera|shutter|click|film|photograph
        cricket|night|countryside|peace|calm
        explosion|action|suspense|horror|loud
        victory|completion|positive|reward|achievement
        bouncy|boingy|rubber|spring|elastic
        PS2|startup|console|electronic|technology
        ball|bounce|rubber|cartoon|funny
        door|creaky|close|suspense|horror
        cat|meow|animal|cute|interaction
        water|spray|shower|flow|fresh
        phone|ring|call|communication|technology
        emergency|alarm|warning|suspense|danger
        cave|water drip|bat|footstep|echo
        party|horn|celebration|joy|hype
        cheerful|guitar|strum|positive|upbeat
        triggered|surprise|annoyance|frustration|shock
        scifi|mystical|pumping|whoosh|futuristic
        sword|metal|clashing|battle|fighting
        greeting|introduction|hello|welcome|friendly
        eye|wink|cute|playful|charming
        Arabic|Ringtone|Traditional music|Upbeat|Middle Eastern
        slide whistle|comedy|cartoon|humorous|surprise
        action|movie|quote|Schwarzenegger|tension
        drum|tension|suspense|intense|drama
        flashback|mysterious|dreamlike|suspenseful|time travel
        surprise|shock|reaction|comedy|laugh
        ominous|scary|tense|eerie|suspenseful
        boo|surprise|fear|short|punchy
        footfall|footstep|walking|movement|steady
        action|dramatic|climax|battle|intense
        typing|keys|office|work|productivity
        game|powerup|positive|upbeat|exciting
        cuteness|affection|sympathy|compassion
        heavy|metallic|impact|death|monster
        doorbell|ring|bell|chime|buzz
        laugh|surprise|shock|oh|no
        footsteps|stealth|infiltration|suspense|tension
        treasure|discovery|exploration|excitement|anticipation
        suspense|thriller|unpredictable|tension|eerie
        game|start|okay|go|announce
        drumroll|motivation|triumph|victory|battle
        relax|calm|relief|meditate|comfort
        horror|ghost|scream|supernatural|startle
        distortion|harsh|noise|explosion|industrial
        end credit|orchestra|grand|dramatic|achievement
        bounce|cartoon|elastic|springy|funny
        clock|ticking|time|tension|suspense
        explosion|lightning|creaking|door|thunder
        sad|crying|sorrow|emotional|pathetic
        cartoon|disappointment|frustration|sad|angry
        eating|chewing|food|munching|smacking
        entrance|arrival|sports|match|victory
        creepy|horror|suspense|eerie|disturbing
        action|fight|punch|rapid|repeated
        rising|tense|ominous|danger|suspense
        bell|eerie|ominous|unsettling|internet
        burp|mouth|gas|disgust|comic
        target|neutralized|impact|action|battle
        no|negative|disapproval|rejection|disagreement
        slap|hit|fight|action|impact
        shock|surprise|horror|tension|jump
        sharp|metal|clear|battle|tension
        munch|bite|chewing|eating|crunchy
        suspense|unease|tension|ominous|thriller
        scream|surprised|shocked|funny
        mysterious|ominous|drone|suspenseful|uncertainty
        Owen Wilson|Wow
        metal|alert|alarm|urgent|danger
        ding|leave|exit|Discord|alert
        creepy|wind|eerie|unsettling|suspenseful
        broken|crash|shatter|surprise|oh no
        water|aquatic|dolphins|ocean|underwater
        hit|marker|impact|alert|notification
        success|joy|celebration|triumph|encouragement
        coin|money|chime|success|reward
        sad|lonely|disappoint|human|voice|lament
        joseph joestar|jojo|quote|anime|funny
        mom get the camera|shock|surprised|urgency|tension
        stone|sliding|smooth|surface|movement
        omae wa mou shindeiru|japanese|anime|warning|impactful
        tada|victory|celebration|achievement|success
        human|scream|pain|intense|cry
        heavy breathing|panic|hastening|sweat|tension
        drum|percussion|comedy|punchline|tension release
        victory|success|triumph|achievement
        goofy|cartoon|boink|hit|slapstick
        fast|exciting|dynamic|fun|upbeat
        boing|bounce|cartoon|elastic|funny
        correct|electronic|high-pitched|affirmative|positive
        cute|love|Patrick Star|affection|happiness
        Asian|old|gun|action|thriller
        Arnold Schwarzenegger|Terminator|Do it|Command|Action
        shock|surprise|fear|horror|scream
        desperation|horror|panic|tension|stress
        monkey|animal|nature|wild|creature
        snake|hiss|threat|tension|fear|scream|horror|pain|excitement|action|surprise|shock|human|high-pitched|powerful|fart|toot|embarrassing|funny|laugh|cartoon|funny|joyful|lighthearted|drama|energy release|excitement|tension|surprise|cartoon|tire|screech|funny|comic|falling|crashing|breaking|heavy|weight|bruh|shock|surprise|indifference|comic|mild surprise|mouth|spicy|burn|pain|reaction|boing|springy|elastic|bounce|cartoon|investigation|suspense|crescendo|mystery|eerie|bass|beat|rhythm|action|energy|speedrunning|fast-paced|tense|exciting|thrilling|magic|wand|fantasy|mystical|supernatural|doorbell|door|ring|chime|entry|eat|munch|food|chew|bite|human|scream|intense|emotion|Robert Downey Jr|scream|high-pitched|cartoon|ricochet|bounce|impact|comedy|epic|orchestral|dramatic|suspenseful|climax|cuckoo|bird|nature|spring|calm|bounce|cartoon|boing|cute|funny|error|alert|failure|suspenseful|unexpected|mistake|disappointment|embarrassment|explosion|boom|sudden|startling|impact|error|alert|alarm|error sound|buzzer|stone|sliding|rough|surface|heavy|dial up|modem|connection|technology|communication|loneliness|wind|howling|desolate|ominous|scream|horror|shock|pain|sudden impact|disappointment|embarrassment|frustration|nervous|surprise|taunt|annoy|tease|playful|mocking|horror|surprise|scream|mud|splat|splash|squelch|wet|bell|transition|chime|alert|notification|joy|excitement|triumph|victory|enthusiasm|human|scream|shock|anger|fear|lightsaber|energy|dramatic|futuristic|fight|serious|surprise|disappointment|humour|meme|slip|slide|object|movement|surface|baby|cry|uncomfortable|distress|emotional|goofy|brake|car|funny|cartoon|roblox|inapropiate|offensive|suspense|tension|ominous|eerie|anxiety|slap|comic|action|emphasis|percussion|laugh|comical|happy|funny|party|celebration|fun|excitement|festive|horror chasing|creepy|thrilling| tense|suspenseful|duck|quack|farm|animal|nature|comedy|funny|toink|surprise|laugh|failure|disappointment|frustration|drama|defeat|ting|metal|sharp|alert|alarm|fade|stick bug|depart|disappear|ominous|error|windows|exaggerated|dragon|roar|powerful|majestic|epic|school bell|school|important announcement|test|deadline|action|horror|aggressive|danger|urgent|phone|ring|call|alert|notification|glitch|feedback|noise|electronic|distortion|suspense|eerie|tension|ominous|unease|scream|sharp|short|shock|suspense|oh|shit|not|good|comical|mistake|unexpected|attention|funny|awkward|empathize|laughter|suspense|tension|dramatic|intense|build-up|frustration|tiredness|annoyance|overwhelmed|stress|damn daniel|funny|comic|human voice|excited|energetic|exciting|powerful|action|victorious|scream|wizardry|excitement|anticipation|human|camera|shutter|metal|clear|fast|cartoon|funny|running|fast|playful|suspense|tension|low|continuous|danger|female|scream|shock|horror|disbelief|fart|funny|comedy|gas|flatulence|fart|disgusting|offensive|uncomfortable|embarrassment|bottle|cork|pop|fizz|refresh|gunfire|death|impact|dramatic|action|emotional|piano|melancholic|eerie|reflective|bone|crack|impactful|disturbing|gory|scream|horror|terror|surprise|fright|birdsong|nature|peaceful|calming|relaxing|metal|scratch|irritation|tension|alert|disaster|ship sinking|ocean|titanic|tragic|enemy|aircraft|explosion|danger|action|sneak|stealth|rubber|quiet|suspense|alarm|warning|danger|funny|comedic|stone|slide|harsh|movement|danger|buzzer|wrong|fail|quiz|show|whoosh|movement|swift|flying|rush|horror|scream|suspense|fear|tension|slide up|ascending|positive|transition|entrance|suspense|mystery|thriller|creepy|eerie|suspenseful|mysterious|intriguing|tense|unexpected|error|alarm|warning|buzz|alert|cartoon|funny|bounce|light|fast|energy surge|powerful|intense|epic|dramatic|splat|wet|impact|collision|splash|human|scream|fear|surprise|pain|computer|shutdown|ding|error|device|HEY|construction|attention-grabbing|annoying|fooling|confirmation|affirmation|positive|cute|friendly|elation|joy|success|celebration|achievement|knife|cutting|murder|horror|suspense|knocking|tap|door|window|ticking|race|start|countdown|suspense|excitement|chinese|egghead|singing|snow|funny|victory|finish|resolved|ending|impactful|shut up|meme|funny|iconic|simple|baby|laugh|joy|positive|cute|sorting|algorithm|digital|futuristic|data|cartoon|funny|slap stick|quick slide|death|explosion|impact|video game|tension|stone|slide|rough|surface|impact|running|movement|action|excitement|thrill|funny|silly|fall|fail|disappointment|bloop|water|splash|submerge|underwater|scream|pain|comedy|jump scare|tension|fart|funny|comic|awkward|humorous|diarrhea|toilet|fart|bass boost|disgusting|duck|quack|animal|funny|humorous|Chest|Close|Door|Drawer|Lid|chime|dream|fantasy|magic|suspense|cartoon|transition|funny|short|tune|drum|roll|tension|anticipation|dramatic|TV|static|noise|tension|unease|camera|shutter|click|photograph|lens|telephone|ring|call|incoming|communication|cricket|night|quiet|outdoor|forest|woosh|dramatic|fast|impactful|speed|groan|pain|injury|suffering|anguish|cymbal|clash|percussion|dramatic|attention-grabbing|mario|oof|funny|surprise|pain|beatbox|rhythm|energy|thump|musical|punch|hit|impact|heavy|dull|breath|heavy|anxious|panting|nervous|action|suspense|comedy|surprise|tension|door|open|close|wood|animated|bell|chime|alert|event|notification|exaggerated|surprise|funny|meme|dad|son|gaming|cartoon|teeth|chatter|fear|cold|yoda|death|funny|parody|joke|censored|bip|block|mute|obfuscate|falling|building|kermit|scream|thud|thriller|suspense|alarm|heartbeat|anxiety|joker|laugh|maniac|creepy|eerie|pop|explosion|shot|impact|alert|wind|blowing|outdoor|nature|atmosphere|cheering|crowd|excitement|sports|victory|chest|open|wood|box|lid|blink|wink|flutter|cute|comedic|whistle|sharp|piercing|startling|suspenseful|crowd|cheer|sports|stadium|victory|crying|sob|sad|despair|grief|swoosh|movement|action|energy|tension|jump|bounce|action|energetic|arcade|uganda knuckles|slurp|funny|taunting|sarcastic|thunder|storm|rain|lightning|heavy wind|chicken|rooster|farm|morning|dawn|cartoon|funny|goofy|trumpet|cheerful|battle cry|war|fight|scream|intensity|upbeat|funny|lighthearted|comedic|stoner|ukulele|male|voice|peaceful|happy|metal|sword|clank|action|fight|choir|angels|heavenly|serene|ethereal|bonk|impact|metal|wood|punch|dramatic|tense|suspenseful|action|thrilling|race start|racing|Mario Kart|start|countdown|footstep|indoor|hurry|intense|tension|heh|laugh|funny|joking|tease|drum|dramatic|tension|anticipation|impact|suspense|tension|thriller|horror|ominous|cash register|bell|sale|money|transaction|spooky|laughter|eerie|ominous|scary|eerie|anxiety|fear|supernatural|sorrow|sussy|baka|comic|awkard|funny|human|sweat|run|gasp|chase|spaghetti|touch|funny|comical|laugh|dark|power out|electricity|silence|fading|kick|explosion|punch|sudden impact|dramatic|horror|creepy|ambient|bass boosted|growl|yes|positive response|cheerful|dialogue|positive atmosphere|human voice|angry|upset|aggressive|confrontation|kitchen|gun|cleaning|dirty|greasy|despair|pain|scream|cry|horror|scream|female|sharp|high-pitched|horror|suspenseful|eerie|menacing|spooky|ominous|metal|impact|surprise|percussion|shock|sad|emotional|melancholic|introspective|heartbreak|food|eating|bite|hunger|appetite|Woohoo|Cheering|Victory|Triumph|Celebration|headshot|fortnite|action|impact|tension|photo|snap|camera|shutter|capture|computer|nostalgia|digital|technology|familiar|water|flow|sparkle|calm|tranquil
        """;

        String[] split = tags.split("\\|");
        for (String tag : split) {
            if (!soundEffectTagRepository.existsByName(tag)) {
                System.out.println("tag:" + tag);
                soundEffectTagRepository.save(new SoundEffectTag(tag));
            }
        }
    }
    
    @Transactional
    public void createSoundEffectTagRel(List<TagRelDto> tagRelDtos) {
        for (TagRelDto tagRelDto : tagRelDtos) {
            SoundEffect soundEffect = soundEffectRepository.findById(tagRelDto.getId()).orElseThrow(() -> new IllegalArgumentException("Tag rel not found"));
            String[] split = tagRelDto.getTags().split("\\|");
            for (String tag : split) {
                if (!tag.equals("|") && !tag.isEmpty()) {
                    soundEffectSoundEffectTagRelRepository.save(new SoundEffectSoundEffectTagRel(soundEffect, soundEffectTagRepository.findByName(tag)));
                }
            }
        }
    }
}

