package site.metacoding.recipemarket.domain.user;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@EntityListeners(AuditingEntityListener.class)
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 20, nullable = false, unique = true)
    private String username;

    @Column(length = 12, nullable = false, unique = true)
    private String nickname;

    @Column(length = 100, nullable = false)
    private String password;

    @Column(length = 60, nullable = false)
    private String email;

    @Column(nullable = true)
    private String profileImg;

    @CreatedDate // insert 할때만 동작
    private LocalDateTime createDate;
    @LastModifiedDate // update 할때만 동작
    private LocalDateTime updateDate;

    // DB테이블과 상관없음
    // 로그인할 때 유저아이디 Remember me 하려고 추가하려고 했으나
    // 시큐리티로 쿠키 담아 아이디만 기억하는 방법을 아직 찾지 못함
    @Transient
    private String remember;

}