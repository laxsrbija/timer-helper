FROM mielune/alpine-python3-arm:latest

WORKDIR /usr/src/app
COPY . .

RUN pip install flask

EXPOSE 5000
CMD ["flask", "run", "--host=0.0.0.0"]
