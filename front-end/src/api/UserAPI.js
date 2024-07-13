import { hostConfig } from '@/config/host';
import { useEncryptUtils } from '@/utils/encrypt';
import { request } from './request';
import { useUserStore } from '@/store/user';

const userStore = useUserStore();
const encryptUtils = useEncryptUtils();
const hostUrl = hostConfig.hostProtocol + '://' + hostConfig.hostIP + ':' + hostConfig.hostPort + '/';
const headers = { 'Content-Type': 'application/x-www-form-urlencoded' };

const getUserParam = async (param) => {
    return await request(hostUrl + 'user/getUserParam?username=' + param, {
        method: 'GET',
        headers: headers
    }).then((res) => {
        return res.json();
    });
};

export const userLogin = async (username, password) => {
    let userParam = await getUserParam(username);
    let time = new Date().getTime();
    let random = encryptUtils.getRandomString(20);
    if (userParam.code != 200) return userParam;
    else userParam = userParam.data;

    return await request(hostUrl + 'user/login', {
        method: 'POST',
        headers: headers,
        body: new URLSearchParams({
            username: username,
            time: time,
            random: random,
            sign: encryptUtils.getHashSign(encryptUtils.getHashSign(password + userParam.time + userParam.random) + time + random)
        }).toString()
    }).then((res) => {
        return res.json();
    });
};

export const userRegister = async (username, password) => {
    let time = new Date().getTime();
    let random = encryptUtils.getRandomString(20);
    return await request(hostUrl + 'user/register', {
        method: 'POST',
        headers: headers,
        body: new URLSearchParams({
            username: username,
            time: time,
            random: random,
            sign: encryptUtils.getHashSign(password + time + random),
            avatar: 'null',
            email: 'null'
        }).toString()
    }).then((res) => {
        return res.json();
    });
};

export const getUser = async (username) => {
    return await request(hostUrl + 'user/getUser?username=' + username, {
        method: 'GET'
    }).then(res => {
        return res.json();
    });
};

export const commitPersonalInformation = async () => {
    let serving_country = '';
    for (let servingCountryKey in userStore.serving_country) {
        let comma = ',';
        if (servingCountryKey == userStore.serving_country.length - 1) comma = '';
        serving_country += userStore.serving_country[servingCountryKey].name + comma;
    }
    return await request(hostUrl + 'user/commitPersonalInformation', {
        method: 'POST',
        body: new URLSearchParams({
            username: userStore.username,
            sex: userStore.sex,
            born_time: userStore.born_time,
            serving_country: serving_country,
            born_country: userStore.born_country.name
        }).toString()
    }).then(res => {
        return res.json();
    });
};
