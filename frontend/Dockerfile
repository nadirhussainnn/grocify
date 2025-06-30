FROM node:20

WORKDIR /app
COPY . .

RUN npm install

# Expose dev server port
EXPOSE 4200

CMD ["npm", "run", "start"]