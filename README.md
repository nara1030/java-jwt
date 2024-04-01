JWT
=====

## 목차
1. [요구사항](#요구사항)
2. [구현](#구현)
   * [플로우 차트](#플로우-차트)
   * [기술 스택](#기술-스택)
   * [클래스 다이어그램](#클래스-다이어그램)
   * 결과 스크린샷
3. [기타](#기타)
    * [기록](#기록)
    * [앞으로](#앞으로)

## 요구사항
```txt
회원가입, 로그인 및 조회 서비스를 위한 백엔드 API를 정의하고, Java(Spring Boot) 프로젝트로 구현해주세요.

기능요건
    1. 회원가입 API를 구현해주세요.
        - 회원가입 URI: /v1/member/join
        - 회원가입 시 필요한 정보는 ID, 비밀번호, 사용자 이름입니다.
        - ID는 반드시 EMAIL 형식이어야 합니다.
        - 비밀번호는 영어 대문자, 영어 소문자, 숫자, 특수문자 중
          3종류 이상으로 12자리 이상의 문자열로 생성해야 합니다.
        - 비밀번호가 서버에 저장될 때는 반드시 단방향 해시 처리가 되어야 합니다.
    2. 로그인 API를 구현해주세요.
        - 로그인 URI: /v1/member/login
        - 사용자로부터 ID, 비밀번호를 입력받아 로그인을 처리합니다.
        - ID와 비밀번호가 이미 가입되어 있는 회원의 정보와 일치하면
          로그인이 되었다는 응답으로 AccessToken을 제공합니다.
    3. 회원정보 조회 API를 구현해주세요.
        - 회원정보 조회 URI: /v1/member/info
        - 로그인이 된 사용자에 대해서는 사용자 이름, EMAIL, 직전 로그인 일시
          를 제공합니다(화면에는 아래와 같이 표시됩니다).
          ex. 홍길동(hongildong@naver.com)님, 환영합니다.
              (직전 로그인: 2024/03/31 00:00:00.134234)
        - 로그인이 안된 사용자는 HTTP Status Code를 401(Unauthorized)로 응답합니다.

비기능요건
    1. 데이터는 반드시 DB에 저장해주세요.
    2. Spring MVC 기반으로 전체 Application 설계를 해주세요.
    3. Junit Test 코드를 반드시 작성해주세요.
    4. AccessToken은 특별한 이유가 없다면 JWT를 적용해주세요.
    5. 프론트엔드 개발자를 위한 API 명세를 어떻게 제공할 것인지 제시해주세요.
```

##### [목차로 이동](#목차)

## 구현
### 플로우 차트
처음 생각했던 개략적인 흐름은 다음과 같다.

<img src="/img/jwt_flow.png" width="330" height="400">

##### [목차로 이동](#목차)

### 기술 스택
```txt
JPA, Spring Security 등의 기술을 연습해보고자 하였다.
JPA를 사용함으로써 보다 객체지향적인 코드를 짤 수 있었고,
스프링 시큐리티를 통해 API 접근 제한 등의 구현을 쉽게 할 수 있었다.

- LANGUAGE: Java
- FRAMEWORK: Spring MVC, Spring Security
- LIBRARY: Spring Data JPA
```

##### [목차로 이동](#목차)

### 클래스 다이어그램
시큐리티 내부 동작은 더 공부가 필요하고, 개략적인 흐름은 다음과 같다.

<img src="/img/jwt_classdiagram.png" width="900" height="550">

##### [목차로 이동](#목차)

## 기타

### 기록
```txt
1. 커스텀 로그인, 즉 로그인 컨트롤러를 태우기 위해
   httpBasic, formLogin 인증을 비활성화하였다.
   즉 Filter에서 AuthenticationManager를 호출하지 않고,
   LoginController에서 직접 호출하였다.
2. H2 접속을 위해 다음을 추가하였다.
   .headers(headers -> headers.frameOptions(HeadersConfigurer.FrameOptionsConfig::sameOrigin))
3. 순환 참조 이슈를 피하기 위해 AppConfig 클래스로
   PasswordEncoder 빈을 분리했다.
4. 정상 토큰 발급 불구 API 요청시 403 이슈
   커스텀 토큰(CustomAuthenticationToken)을 사용했기 때문이었는데 다음을 추가해주었다.
   super.setAuthenticated(true);
5. API 응답 형식(body) 관리를 위해 Response 클래스(error, success)를 활용했다.
   success 메소드의 경우 컨트롤러에서 활용하고,
   error 메소드의 경우 예외처리 핸들러(@RestControllerAdvice)에서 활용한다.
6. 계층간 의존성을 줄이기 위해 DTO, ENTITY를 분리하였다.
   특히 RESPONSE DTO를 통해 서버 변경이 클라이언트단에 영향을 미치지 않게끔 하였다.
7. TDD는 할 수가 없었고, JUNIT 테스트의 경우도 검증 로직부터 하려다가 너무 손이 가서 작성하지 못했다.
```

##### [목차로 이동](#목차)

### 앞으로
```txt
1. 사용자 권한 추가(Custom?)
2. Refresh 토큰
3. 로그아웃
4. Oauth2.0
5. 테스트 코드 작성(TDD?)
6. 로그인 필요 타 서비스 추가
```

##### [목차로 이동](#목차)