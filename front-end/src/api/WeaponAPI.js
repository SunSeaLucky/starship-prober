import { hostConfig } from '@/config/host';
import { request } from '@/api/request';

const hostUrl = hostConfig.hostProtocol + '://' + hostConfig.hostIP + ':' + hostConfig.hostPort + '/';

export class WeaponAPI {
    getAllWarshipWeapon(username) {
        return request(hostUrl + 'weapon/getAllWarshipWeapons', {
            method: 'POST',
            body: new URLSearchParams({
                username: username
            }).toString()
        }).then(res => {
            return res.json();
        });
    }

    saleWeapon(username, warship, weapon) {
        return request(hostUrl
            + 'weapon/saleWeapon?username='
            + username
            + '&warship='
            + warship
            + '&weapon='
            + weapon, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });

    }

    buyWeapon(username, warship, weapon) {
        return request(hostUrl
            + 'weapon/buyWeapon?username='
            + username
            + '&warship='
            + warship
            + '&weapon='
            + weapon, {
            method: 'GET'
        }).then(res => {
            return res.json();
        });

    }
}
