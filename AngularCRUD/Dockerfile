FROM node:16.14.0-alpine as build
WORKDIR /app
COPY / ./
COPY package*.json ./

RUN npm install -g @angular/cli && \
    npm install && \
    npm run build
COPY . .

FROM nginx:alpine
WORKDIR /app
COPY --from=build /app/dist/angular-crud /usr/share/nginx/html
