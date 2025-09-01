package team.bridgers.backend.domain.email.application;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.assertj.core.api.Assertions.assertThat;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.test.context.support.WithMockUser;
import team.bridgers.backend.domain.email.domain.VerificationCode;
import team.bridgers.backend.domain.email.frastructure.VerificationCodeRepository;
import team.bridgers.backend.domain.email.presentation.request.SendVerificationCodeRequest;
import team.bridgers.backend.domain.email.presentation.request.VerifyCodeRequest;
import team.bridgers.backend.domain.email.presentation.response.DeleteVerificationCodeResponse;
import team.bridgers.backend.domain.email.presentation.response.SendVerificationCodeResponse;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc(addFilters = false)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class EmailServiceTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private VerificationCodeRepository verificationCodeRepository;

    @AfterEach
    void tearDown() {
        verificationCodeRepository.deleteAllInBatch();
    }

    @Test
    void sendVerificationCode() {
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .email("test@test.com")
                .build();

        ResponseEntity<SendVerificationCodeResponse> response =
                restTemplate.postForEntity("/emails/send", request, SendVerificationCodeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().Id()).isNotNull();

        boolean exists = verificationCodeRepository.findByEmail("test@test.com").isPresent();
        assertThat(exists).isTrue();
    }

    @Test
    void saveVerificationCode() {
        String email = "test@test.com";
        SendVerificationCodeRequest request = SendVerificationCodeRequest.builder()
                .email(email)
                .build();

        ResponseEntity<SendVerificationCodeResponse> response =
                restTemplate.postForEntity("/emails/send", request, SendVerificationCodeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);

        Optional<VerificationCode> saved = verificationCodeRepository.findByEmail(email);
        assertThat(saved).isPresent();
        assertThat(saved.get().getEmail()).isEqualTo(email);
    }

    @Test
    void verifyCodeSuccess() {

        String email = "test@test.com";
        String code = "123456";
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(code)
                .build();
        verificationCodeRepository.save(verificationCode);

        VerifyCodeRequest request = VerifyCodeRequest.builder()
                .email(email)
                .code(code)
                .build();

        ResponseEntity<DeleteVerificationCodeResponse> response =
                restTemplate.postForEntity("/emails/verify-code", request, DeleteVerificationCodeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isNotNull();
        assertThat(verificationCodeRepository.findByEmail(email)).isEmpty();
    }

    @Test
    void verifyCodeFailCauseByWrongCode() {

        String email = "test@test.com";
        String correctCode = "654321";
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code(correctCode)
                .build();
        verificationCodeRepository.save(verificationCode);

        VerifyCodeRequest request = VerifyCodeRequest.builder()
                .email(email)
                .code("000000")
                .build();

        ResponseEntity<String> response =
                restTemplate.postForEntity("/emails/verify-code", request, String.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(response.getBody()).isEqualTo("인증코드가 불일치합니다.");
    }

    @Test
    void deleteVerificationCode() {

        String email = "test@test.com";
        VerificationCode verificationCode = VerificationCode.builder()
                .email(email)
                .code("000000")
                .build();
        verificationCodeRepository.save(verificationCode);

        VerifyCodeRequest request = VerifyCodeRequest.builder()
                .email(email)
                .code("000000")
                .build();

        ResponseEntity<DeleteVerificationCodeResponse> response =
                restTemplate.postForEntity("/emails/verify-code", request, DeleteVerificationCodeResponse.class);

        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(verificationCodeRepository.findByEmail(email)).isEmpty();
    }
}