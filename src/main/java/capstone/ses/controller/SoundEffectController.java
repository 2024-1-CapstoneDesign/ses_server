package capstone.ses.controller;

import capstone.ses.domain.soundeffect.SoundEffectType;
import capstone.ses.dto.soundeffect.SoundEffectCondition;
import capstone.ses.dto.soundeffect.SoundEffectDto;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.dto.system.Error;
import capstone.ses.repository.SoundEffectTypeRepository;
import capstone.ses.service.SoundEffectService;
import capstone.ses.service.SoundEffectTagService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;



import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SoundEffectController {

    private final SoundEffectService soundEffectService;
    private final SoundEffectTypeRepository soundEffectTypeRepository;
    private final SoundEffectTagService soundEffectTagService;

    @PostMapping("/test")
    public Result saveSoundEffect(MultipartFile multipartFile) {
        try {
            SoundEffectType testData = soundEffectTypeRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 효과음이 존재하지 않습니다"));
            Base64.Encoder encoder = Base64.getEncoder();
            testData.updateSoundEffectFile(encoder.encode(multipartFile.getBytes()));
            soundEffectTypeRepository.save(testData);

            return new Result(ResultCode.SUCCESS, testData);
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (IOException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }

    //TEST-002
    @GetMapping("/test/download")
    public Result testDownloadByS3() {
        SoundEffectType testData = soundEffectTypeRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 효과음이 존재하지 않습니다"));
        return new Result(ResultCode.SUCCESS, testData.getUrl());
    }

    //TEST-001
    @GetMapping("/test")
    public ResponseEntity<byte[]> testSoundEffect() {
        try {
            SoundEffectType testData = soundEffectTypeRepository.findById(1L).orElseThrow(() -> new EntityNotFoundException("해당 효과음이 존재하지 않습니다"));

            byte[] fileBytes = testData.getSoundEffectFile();

            // base64 인코딩된 파일을 디코딩하여 byte 배열 반환
            byte[] decodedBytes = Base64.getDecoder().decode(fileBytes);

            // 파일을 저장할 임시 파일 생성
            String fileName = "example.wav";
            File tempFile;

            try {
                tempFile = File.createTempFile("temp", ".wav");
                FileOutputStream fos = new FileOutputStream(tempFile);
                fos.write(decodedBytes);
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return ResponseEntity
                    .ok()
                    .header("Content-Disposition", "attachment; filename=" + fileName)
                    .body(decodedBytes);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/soundeffect")
    public Result searchSoundEffects(@ModelAttribute @Valid SoundEffectCondition soundEffectCondition, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            List<Error> errors = new ArrayList<>();
            for (FieldError fieldError : bindingResult.getFieldErrors()) {
                Error error = new Error(
                        fieldError.getField(),
                        fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString()
                );
                errors.add(error);

                return new Result(ResultCode.FAIL, "400", errors);
            }
        }

        try {
            List<SoundEffectDto> soundEffectDtos = soundEffectService.searchSoundEffects(soundEffectCondition);

            if (soundEffectDtos.isEmpty()) {
                throw new EntityNotFoundException("조건에 해당하는 효과음이 존재하지 않습니다.");
            }
            return new Result(ResultCode.SUCCESS, soundEffectDtos);
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

    //SOUNDEFFECT-003: 효과음 태그 조회
    @GetMapping("/soundeffect/tag")
    public Result searchSoundEffectTag() {
        try {
            return new Result(ResultCode.SUCCESS, soundEffectTagService.getAll());
        } catch (IllegalStateException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }
    }
}
