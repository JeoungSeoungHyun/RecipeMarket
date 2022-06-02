package site.metacoding.recipemarket.web.api;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.RequiredArgsConstructor;
import site.metacoding.recipemarket.handler.ex.CustomException;
import site.metacoding.recipemarket.service.UserService;
import site.metacoding.recipemarket.web.dto.user.JoinReqDto;

@RequiredArgsConstructor
@RestController
public class UserApiController {

    // DI
    private final UserService userService;

    // 회원가입
    @PostMapping("/join")
    public String join(@Valid JoinReqDto joinReqDto, BindingResult bindingResult) {

        if (bindingResult.hasErrors()) {
            Map<String, String> errorMap = new HashMap<>();
            for (FieldError fe : bindingResult.getFieldErrors()) {
                errorMap.put(fe.getField(), fe.getDefaultMessage());
            }
            // 이부분에서 data리턴인지 html 리턴인지 이것만 구분해서 터트려줘!!
            throw new CustomException(errorMap.toString());
        }
        
        return "redirect:/login-form";
    }

    // 프로필 이미지 등록하기
    @PutMapping("/join/profile-img")
    public ResponseEntity<?> profileImg(JoinReqDto joinReqDto, MultipartFile profileImgFile){
        UUID uuid = UUID.randomUUID();
        String requestFileName = joinReqDto.getFile().getOriginalFilename();
        System.out.println("전송받은 파일명 : " + requestFileName);

        String imgurl = uuid + "_" + requestFileName;
        try {
            // jar 파일로 구우면 안돌아감
            Path filePath = Paths.get("src/main/resources/static/upload/" + imgurl);
            Files.write(filePath, joinReqDto.getFile().getBytes());

            userService.회원가입(joinReqDto.toEntity(imgurl));
            
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
