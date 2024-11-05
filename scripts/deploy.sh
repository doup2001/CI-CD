#!/bin/bash
echo "Starting deploy script"

# Docker Compose 실행
sudo /usr/local/bin/docker-compose --env-file .env -f ./docker-compose.yml up -d

# 현재 실행 중인 프로세스를 확인하고 종료
pid=$(pgrep -f gdg-0.0.1-SNAPSHOT.jar)
if [ -n "${pid}" ]; then
    kill -15 ${pid}
    echo "Killed process ${pid}"
else
    echo "No process found"
fi

# JAR 파일에 실행 권한 부여
echo "Setting execute permission for JAR"
chmod +x ./gdg-0.0.1-SNAPSHOT.jar

# JAR 파일 실행
