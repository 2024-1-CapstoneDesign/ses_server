package capstone.ses.controller;

import capstone.ses.domain.soundeffect.SoundEffect;
import capstone.ses.dto.system.Error;
import capstone.ses.dto.system.Result;
import capstone.ses.dto.system.ResultCode;
import capstone.ses.service.SoundEffectService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class SoundEffectController {

    private final SoundEffectService soundEffectService;

    @GetMapping("/soundeffect/{soundEffectId}")
    public Result searchSoundEffect(@PathVariable Long soundEffectId) {
//        if (bindingResult.hasErrors()) {
//            List<Error> errors = new ArrayList<>();
//            for (FieldError fieldError : bindingResult.getFieldErrors()) {
//                Error error = new Error(
//                        fieldError.getField(),
//                        fieldError.getRejectedValue() == null ? "" : fieldError.getRejectedValue().toString()
//                );
//                errors.add(error);
//            }
//            return new Result(ResultCode.FAIL, "400", errors);
//        }
        try {
            SoundEffect soundEffect = soundEffectService.searchSoundEffect(soundEffectId);
            return new Result(ResultCode.SUCCESS, );
        } catch (EntityNotFoundException e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "300");
        } catch (Exception e) {
            return new Result(ResultCode.FAIL, e.getMessage(), "400");
        }


    }
}
