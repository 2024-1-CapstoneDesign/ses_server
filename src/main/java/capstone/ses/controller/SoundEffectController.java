package capstone.ses.controller;

import capstone.ses.dto.soundeffect.*;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.repository.SoundEffectTypeRepository;
import capstone.ses.service.SoundEffectService;
import capstone.ses.service.SoundEffectTagService;
import capstone.ses.service.YoutubeDownloadService;
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
    private final YoutubeDownloadService youtubeDownloadService;

    //SOUNDEFFECT-001: 효과음 리스트 조회
    @GetMapping("/soundeffect")
    public Result searchSoundEffects(
            @RequestParam(required = false) Integer fromLength,
            @RequestParam(required = false) Integer toLength,
            @RequestParam(required = false) BigDecimal sampleRate,
            @RequestParam(required = false) Integer bitDepth,
            @RequestParam(required = false) String channels,
            @RequestParam(required = false) BigDecimal fromFileSize,
            @RequestParam(required = false) BigDecimal toFileSize,
            @RequestParam(required = false) String type,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) List<Long> soundEffectTagId,
            Pageable pageable
    ) {

        try {

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
                    .build(), pageable);

            return new Result(ResultCode.SUCCESS, soundEffectPaginationDto);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-002: 효과음 세부 조회
    @GetMapping("/soundeffect/{soundEffectId}")
    public Result searchSoundEffect(@PathVariable Long soundEffectId) {
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
    public Result searchSoundEffectDirect(@ModelAttribute @Valid MultipartFile file) {
        if (file.isEmpty()) {
            return new Result(ResultCode.FAIL, "not found", "300");
        }

        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchByDirect(file));
        } catch (IOException | UnsupportedAudioFileException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-005: 효과음 유튜브 구간 검색
    @GetMapping("/soundeffect/youtube")
    public Result searchSoundEffectByYouTude(
            @RequestParam String url,
            @RequestParam String from,
            @RequestParam String to
    ) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.getYoutudeAudio(url, from, to));
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }

    }

    //SOUNDEFFECT-009: 유사한 효과음 조회
    @GetMapping("/soundeffect/{soundEffectId}/relative")
    public Result searchSoundEffectRelative(@PathVariable Long soundEffectId) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchRelativeSoundEffects(soundEffectId));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-010: 좋아요 효과음 조회
    @GetMapping("/soundeffect/like")
    public Result searchLikedSoundEffect(@RequestParam String accessToken) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.searchLikedSoundEffects(accessToken));
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-011: 효과음 좋아요 update
    @PutMapping("/soundeffect/{soundEffectId}/like")
    public Result updateLikedSoundEffect(@PathVariable Long soundEffectId, @RequestParam String accessToken) {
        try {
           return new Result(ResultCode.SUCCESS, soundEffectService.updateLikedSoundEffect(soundEffectId, accessToken));
        } catch (JsonProcessingException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //SOUNDEFFECT-012: 효과음 좋아요 취소 update
    @PutMapping("/soundeffect/{soundEffectId}/unlike")
    public Result updateUnlikedSoundEffect(@PathVariable Long soundEffectId, @RequestParam String accessToken) {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectService.updateUnlikedSoundEffect(soundEffectId, accessToken));
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

}


