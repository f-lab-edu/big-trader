import http from 'k6/http';
import {check} from 'k6';

export const options = {
    stages: [
        {duration: '20s', target: 5000}
    ],
};

const appPushMessage = {
    message: "앱 푸쉬 전송"
}

export default function () {
    let res = http.post(
        "http://localhost:8000/api/v1/messages",
        JSON.stringify(appPushMessage),
        {
            headers: {'Content-Type': 'application/json'},
            timeout: 6000
        });

    check(res, {'status was 200': r => r.status === 200});
}
