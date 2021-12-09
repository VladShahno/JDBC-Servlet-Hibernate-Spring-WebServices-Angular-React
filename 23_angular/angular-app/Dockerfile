FROM node:16
RUN mkdir /app
WORKDIR /app
RUN npm install -g @angular/cli
RUN yarn add @angular-devkit/build-angular --dev
COPY . .
RUN npm ci
RUN ng build
EXPOSE 4200
ENTRYPOINT ng serve --host 0.0.0.0
