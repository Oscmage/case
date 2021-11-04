
# STEP 1, BUILDING
FROM node:17 as build-deps

# set working directory
WORKDIR /usr/src/app

# install app dependencies
COPY package.json yarn.lock  ./
RUN yarn

# add app
COPY . ./

# Workaround for https://github.com/webpack/webpack/issues/14532
ARG NODE_OPTIONS=--openssl-legacy-provider
# Build optimized app
RUN yarn build

# STEP 2, STARTUP
FROM nginx:1.21.3-alpine
COPY --from=build-deps /usr/src/app/build /usr/share/nginx/html
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]