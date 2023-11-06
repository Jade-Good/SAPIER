# FROM node:16-alpine as build-stage

# WORKDIR /app
# RUN corepack enable

# COPY .npmrc package.json pnpm-lock.yaml ./
# RUN --mount=type=cache,id=pnpm-store,target=/root/.pnpm-store \
#     pnpm install --frozen-lockfile

# COPY . .
# RUN pnpm build

# FROM nginx:stable-alpine as production-stage

# COPY --from=build-stage /app/dist /usr/share/nginx/html
# EXPOSE 80

# CMD ["nginx", "-g", "daemon off;"]
# ----------------------------
# FROM node:alpine as builder
# WORKDIR /usr/src/app
# COPY package.json /usr/src/app/package.json
# RUN npm install --force
# COPY . /usr/src/app
# RUN npm run build

# COPY --from=builder /usr/src/app/build /usr/share/nginx/html

# EXPOSE 80
# CMD ["nginx", "-g", "daemon off;"]

# ------------------------------



FROM node:18-slim
WORKDIR /app

# `package.json`과 `pnpm-lock.yaml` 파일을 작업 디렉토리로 복사
COPY package.json pnpm-lock.yaml ./

# pnpm을 설치하고 의존성을 설치합니다.
RUN npm install -g pnpm && pnpm install

# 나머지 소스 코드를 복사
COPY . .

EXPOSE 3333

RUN pnpm build

# RUN npm run dev
# RUN pnpm run dev