from flask import Flask, render_template, request
import datetime

app = Flask(__name__)


@app.route("/calculate")
def get_start_time():
    now = datetime.datetime.fromisoformat(request.args.get('localtime'))
    dst = request.args.get('dst')

    finish_time_hour = 5 + (0 if dst else 1)
    finish_time_minute = 15

    finish_time = (now + datetime.timedelta(days=1) if now.hour > 6 else now)\
        .replace(hour=finish_time_hour, minute=finish_time_minute, second=0)

    duration = request.args.get('runtime').split(':')

    start_time = finish_time - datetime.timedelta(hours=int(duration[0]), minutes=int(duration[1]))
    delta = start_time - now

    return {'startTime': start_time.strftime("%Y-%m-%d %H:%M"),
            'timeToStart': str(delta.seconds // 3600) + ':' + str((delta.seconds // 60) % 60).rjust(2, '0')}


@app.route("/")
def hello_world():
    return render_template('index.html')
