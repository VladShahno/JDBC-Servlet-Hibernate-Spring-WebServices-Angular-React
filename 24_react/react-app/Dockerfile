FROM node:16.13.1 as build

WORKDIR /home/react

ENV PATH /front/node_modules/.bin:$PATH
COPY package.json ./
RUN npm install
RUN npm install react-scripts@3.4.1 -g --silent
COPY . ./
EXPOSE 3000
CMD ["npm", "start"]