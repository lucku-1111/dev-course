#!/bin/bash 
# #!/bin/bash : 셔뱅이라고 한다. "이 파일을 /bin/bash"로 실행하라는 의미이다.
# 실행 방법 : ./start.sh

# -e : 명령어 하나라도 실패하면 즉시 중단한다.
# data가 실패했는데 service를 실행하면 안되기 때문에 -e 옵션을 사용한다.
set -e

# 쉘 스크립트란 : 터미널에 하나씩 치던 명령어를 파일에 모아 한 번에 실행하는 것.

# 쉘의 위치를 이 스크립트가 있는 폴더로 이동한다.
# $( ) : 명령어 치환. 안의 명령어를 실행하고, 그 자리를 안의 명령어의 결과로 대체한다.
# $0 : 실행된 스크립트 경로 -> /Users/kdw/Desktop/.../docker/start.sh
# dirname : 경로에서 디렉토리만 추출한다. -> /Users/kdw/Desktop/.../docker
cd "$(dirname "$0")" # => cd "/Users/kdw/Desktop/.../docker"

echo "[1/3] 데이터 : mas-network 생성 + MySQL"
docker compose -f docker-compose.data.yml up -d

echo
echo "[2/3] 서비스 : config -> auth/board/edge"
docker compose -f docker-compose.service.yml up -d --build

echo
echo "[3/3] 프론트 : web"
docker compose -f docker-compose.front.yml up -d --build

# 실행 중인 컨테이너를 표로 출력한다.
# - --format : 출력 형식을 직접 지정한다.
# - "table" : 맨 앞에 쓰면 머리글(Names, Status, Ports)을 붙이고 열을 맞춰준다.
# - {{}} : GO 템플릿 문법. 컨테이너 정보를 꺼낸다.
# .Names 이름 / .Status 상태 / .Ports 포트 매핑
# -> docker psxw
echo
echo "=== 실행 상태 ==="
docker ps --format "table {{.Names}}\t{{.Status}}\t{{.Ports}}"

