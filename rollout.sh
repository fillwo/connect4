#!/bin/bash

cd frontend
npm run build
cd ..
rm -rf src/main/resources/public
mkdir src/main/resources/public
cp -r frontend/build/* src/main/resources/public/
./mvnw clean install
