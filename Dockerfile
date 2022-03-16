FROM balenalib/raspberry-pi-python:3.8-run

WORKDIR /usr/src/app
COPY . .

RUN pip install flask

EXPOSE 5000
CMD ["flask", "run", "--host=0.0.0.0"]
