package capstone.ses.controller;

import capstone.ses.dto.soundeffect.*;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.repository.SoundEffectTypeRepository;
import capstone.ses.service.SoundEffectService;
import capstone.ses.service.SoundEffectTagService;
import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SoundEffectController {

    private final SoundEffectService soundEffectService;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectTagService soundEffectTagService;

    //SOUNDEFFECT-001: 효과음 리스트 조회
    @GetMapping("/soundeffect")
    public Result searchSoundEffects(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestParam(required = false, value = "fromLength") Integer fromLength,
            @RequestParam(required = false, value = "toLength") Integer toLength,
            @RequestParam(required = false, value = "sampleRate") BigDecimal sampleRate,
            @RequestParam(required = false, value = "bitDepth") Integer bitDepth,
            @RequestParam(required = false, value = "channels") String channels,
            @RequestParam(required = false, value = "fromFileSize") BigDecimal fromFileSize,
            @RequestParam(required = false, value = "toFileSize") BigDecimal toFileSize,
            @RequestParam(required = false, value = "type") String type,
            @RequestParam(required = false, value = "name") String name,
            @RequestParam(required = false, value = "soundEffectTagId") List<Long> soundEffectTagId,
            Pageable pageable
    ) {

        try {
            if(accessToken != null){
                log.info("accessToken: " + accessToken);
                log.info("accessToken(Bearer 제거 버전): " + accessToken.substring("Bearer ".length()));
            }

            SoundEffectPaginationDto soundEffectPaginationDto = soundEffectService.searchSoundEffects(SoundEffectCondition.builder()
                    .fromLength(fromLength)
                    .toLength(toLength)
                    .sampleRate(sampleRate)
                    .bitDepth(bitDepth)
                    .channels(channels)
                    .fromFileSize(fromFileSize)
                    .toFileSize(toFileSize)
                    .type(type)
                    .name(name)
                    .soundEffectTagIds(soundEffectTagId)
                    .build(), accessToken != null ? accessToken.replaceFirst("Bearer ", "") : null, pageable);
            return new Result(ResultCode.SUCCESS, soundEffectPaginationDto);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "401");
        }
    }

    //SOUNDEFFECT-002: 효과음 세부 조회
    @GetMapping("/soundeffect/{soundEffectId}")
    public Result searchSoundEffect(@PathVariable(value = "soundEffectId") Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchSoundEffect(soundEffectId));
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-007: 효과음 수정
    @PutMapping("/soundeffect/{soundEffectId}")
    public Result updateSoundEffect(@PathVariable Long soundEffectId, @RequestBody @Valid SoundEffectForm soundEffectForm, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return new Result(ResultCode.FAIL, bindingResult.getFieldError().getDefaultMessage());
        }

        try {
            soundEffectService.updateSoundEffect(soundEffectId, soundEffectForm);
            return new Result(ResultCode.SUCCESS, null);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-003: 효과음 태그 조회
    @GetMapping("/soundeffect/tag")
    public Result searchSoundEffectTag() {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectTagService.getAll());
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    @PostMapping("/soundeffect/search")
    public Result searchSoundEffectDirect(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @ModelAttribute @Valid MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(ResultCode.FAIL, "not found", "300");
        }

        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchByDirect(file, accessToken != null ? accessToken.replaceFirst("Bearer ", "") : null));
        } catch (IOException | UnsupportedAudioFileException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-005: 효과음 유튜브 구간 검색
    @GetMapping("/soundeffect/youtube")
    public Result searchSoundEffectByYouTude(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @RequestParam String url,
            @RequestParam String from,
            @RequestParam String to,
            @RequestParam String poToken,
            @RequestParam String visitorData
    ) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.getYoutudeAudio(url, from, to, poToken, visitorData, accessToken != null ? accessToken.replaceFirst("Bearer ", "") : null));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }

    }

    //SOUNDEFFECT-009: 유사한 효과음 조회
    @GetMapping("/soundeffect/{soundEffectId}/relative")
    public Result searchSoundEffectRelative(
            @RequestHeader(value = "Authorization", required = false) String accessToken,
            @PathVariable(value = "soundEffectId") Long soundEffectId
    ) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchRelativeSoundEffects(soundEffectId, accessToken != null ? accessToken.replaceFirst("Bearer ", "") : null));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    //SOUNDEFFECT-010: 좋아요 효과음 조회
    @GetMapping("/soundeffect/like")
    public Result searchLikedSoundEffect(@RequestHeader("Authorization") String accessToken) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchLikedSoundEffects(accessToken.replaceFirst("Bearer ", "")));
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-011: 효과음 좋아요 update
    @PutMapping("/soundeffect/{soundEffectId}/like")
    public Result updateLikedSoundEffect(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.updateLikedSoundEffect(soundEffectId, accessToken.replaceFirst("Bearer ", "")));
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-012: 효과음 좋아요 취소 update
    @PutMapping("/soundeffect/{soundEffectId}/unlike")
    public Result updateUnlikedSoundEffect(
            @RequestHeader("Authorization") String accessToken,
            @PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.updateUnlikedSoundEffect(soundEffectId, accessToken.replaceFirst("Bearer ", "")));
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //create tag
    @PostMapping("/soundeffect/tag")
    public Result createTag(@RequestBody String tags) {
        try {
            soundEffectTagService.createSoundEffectTag(tags);
            return new Result(ResultCode.SUCCESS, null);
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //create tag-rel
    @PostMapping("/soundeffect/tag/rel")
    public Result createTagRel(@RequestBody List<TagRelDto> tagsRelDto) {
        try {
            soundEffectTagService.createSoundEffectTagRel(tagsRelDto);
            return new Result(ResultCode.SUCCESS, null);
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    @PostMapping("/soundeffect")
    public Result createSoundEffect(@RequestBody SoundEffectRequest soundEffectRequest) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.createSoundEffect(soundEffectRequest));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "500");
        }
    }

}


