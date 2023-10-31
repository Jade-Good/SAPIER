#! /bin/bash
# 현재 프로젝트에 있는 docker-compose
sudo docker-compose -f docker-compose-prod.yml pull

echo "pull 완료"

COMPOSE_DOCKER_CLI_BUILD=1 DOCKER_BUILDKIT=1 docker-compose -f docker-compose-prod.yml up --build -d

docker rmi -f $(docker images -f "dangling=true" -q) || true