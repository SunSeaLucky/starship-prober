import { hostConfig } from '@/config/host';
import { request } from '@/api/request';

const hostUrl = hostConfig.hostProtocol + '://' + hostConfig.hostIP + ':' + hostConfig.hostPort + '/';

export class WarshipAPI {
    getWarshipNumber(username) {
        return request(hostUrl + 'user/getWarshipNumber?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getLVCWarshipNumber(username) {
        return request(hostUrl + 'user/getLVCWarshipNumber?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getWarshipBattleRatio(username) {
        return request(hostUrl + 'user/getWarshipBattleRatio?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getLVCWarshipBattleRatio(username) {
        return request(hostUrl + 'user/getLVCWarshipBattleRatio?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getCrewNumber(username) {
        return request(hostUrl + 'user/getCrewNumber?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getLVCCrewNumber(username) {
        return request(hostUrl + 'user/getLVCCrewNumber?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }


    getMessage(username) {
        return request(hostUrl + 'user/getMessage?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getLVCMessage(username) {
        return request(hostUrl + 'user/getLVCMessage?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getAllMyWarships(username) {
        return request(hostUrl + 'warship/getAllMyWarships?username=' + username, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    getAllWarships() {
        return request(hostUrl + 'warship/getAllWarships', {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    buyWarship(username, warshipName) {
        return request(hostUrl + 'warship/buyWarship?username=' + username + '&warshipName=' + warshipName, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    saleWarship(username, warshipName) {
        return request(hostUrl + 'warship/saleWarship?username=' + username + '&warshipName=' + warshipName, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

    updateWarshipAgent(warshipName, agentName) {
        return request(hostUrl + 'warship/updateWarship?warshipName=' + warshipName + '&agentName=' + agentName, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });
    }

}
