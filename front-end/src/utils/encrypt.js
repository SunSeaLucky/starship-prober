import CryptoJS from 'crypto-js';


export const useEncryptUtils = () => {
    const getRandomString = (length) => {
        let result = '';
        const characters = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';
        const charactersLength = characters.length;
        for (let i = 0; i < length; i++) {
            result += characters.charAt(Math.floor(Math.random() * charactersLength));
        }
        return result;
    }

    const getHashSign = (text) => {
        let hash = CryptoJS.SHA256(text);
        return hash.toString(CryptoJS.enc.Hex);
    }

    return { getRandomString, getHashSign }
}

