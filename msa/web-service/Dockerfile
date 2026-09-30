
# 운영체제(맥/윈도우)에서는 리눅스 커널이 없는데 컨테이너는 리눅스 커널을 공유해야 돌아간다.
# 그래서 Docker Desktop이 리눅스 VM을 하나 띄운다

# ====== Build stage ======
# 이 FROM부터 다음 FROM 전까지가 "build" 스테이지
# 여기서 만들어지는 모든 것(JDK, Gradle 캐시, 소스, .class)은
# 최종 이미지에 포함되지 않는다. jar 을 뽑아내는 일회용 작업장이다.
FROM eclipse-temurin:25-jdk AS build

# /app 은 빌드 중에만 존재하는 "컨테이너 안의 경로"다.
# 이후 COPY 의 도착지 상대경로 기준점.
WORKDIR /app

# 순서를 나눈 이유:
# 도커는 계층 단위로 캐시한다.
# COPY의 원본 파일이 안바뀌면 그 계층과 그 아래 RUN 결과까지 통째로 재사용한다.
#   - 자바 코드만 수정 -> build.gradle 그대로
#   - build.gradle 수정 -> 의존성부터 다시 받음
# 순서를 나누지 않고 COPY 로 한 번에 전체를 복사했다면 주석 한 줄만 고쳐도 의존성을 전부 다시 받는다.

# 호스트 service/gradlew -> 이미지 /app/gradlew
COPY gradlew ./
# 호스트 service/gradle/통째로 -> 이미지 /app/gradle
COPY gradle gradle
# 호스트 service/settings.gradle -> 이미지 /app/settings.gradle
COPY settings.gradle .
# 호스트 service/build.gradle -> 이미지 /app/build.gradle
COPY build.gradle .

# 의존성만 먼저 받아 캐시화
# ./gradlew는 빌드가 끝나도 안 죽는 백그라운드 JVM을 하나 남겨둔다.
# 다음 빌드를 더 빠르게 하려고, 하지만 지금은 다음 빌드가 없으니 비활성.
# 이 빌드는 한 번 하고 죽을 거니 백그라운드 비활성.
RUN ./gradlew --no-daemon dependencies

# 소스 복사
# 호스트 service/src/통째로 -> 이미지 /app/src/
# 가장 자주 바뀌는 파일이라 맨 뒤에 둔다.
COPY src src

# 빌드
RUN ./gradlew --no-daemon clean bootJar -x test

# ====== Runtime stage ======
# 새 FROM = 앞의 build 스테이지를 통째로 버리고 빈 이미지에서 다시 시작
# 여기서부터 쌓이는 것만 실제로 배포되는 이미지에 들어간다.

# jre = 실행기만(java)만. javac 도 Gradle도 소스도 없다..
# 위 build 스테이지와 달리 이 이미지는 서버에 올라가 계속 살아 있으므로
# "없어도 되는 건 하나도 넣지 않는다."가 원칙이다.
FROM eclipse-temurin:25-jre

WORKDIR /app

# 빌드 산출물 복사
# --from=build 가 붙으면 출발지가 호스트가 아니라 앞선 build 스테이지의 파일시스템이다.
COPY --from=build /app/build/libs/*.jar /app/app.jar

# JVM 메모리 옵션
# 자바가 쓰는 메모리 = 힙 + 부속비용(클래스정보, 스레드 작업공간 등)
# 힙메모리 최대 가용 범위를 75% 제한
# 남긴 25%가 부속비용 자리다.
# 꽉 채우면 컨테이너 전체가 한도를 넘어 커널이 즉사시킨다.(OOM Killer) - 로그가 안 남음.
# 반대로 힙 자체가 모자라면 OutOfMemoryError 가 나는데 이건 로그에 남는다.
ENV JAVA_OPTS="-XX:MaxRAMPercentage=75.0"
EXPOSE 8080

# sh -c 가 $JAVA_OPTS 풀어서 실행 명령어를 만들어서 실행시키고 빠짐
# 그 자리에 java가 떠 있음. exec있어야 종료 신호를 java가 직접 받아서 정리하고 끝냄.
ENTRYPOINT ["sh", "-c", "exec java $JAVA_OPTS -jar /app/app.jar"]
