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
FROM gplane/pnpm:node18-alpine

COPY . .

RUN pnpm install
# RUN npm install --force

RUN pnpm dev
# RUN npm run dev
# RUN pnpm run dev